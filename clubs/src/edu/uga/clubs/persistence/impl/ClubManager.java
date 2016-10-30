package edu.uga.clubs.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.object.ObjectLayer;


class ClubManager
{
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;
    
    public ClubManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Club club ) 
            throws ClubsException
    {
        String               insertClubSql = "insert into club ( name, address, established, founderid ) values ( ?, ?, ?, ? )";
        String               updateClubSql = "update club set name = ?, address = ?, established = ?, founderid = ? where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 clubId;

        /*
        if( club.getFounderId() == -1 )
            throw new ClubsException( "ClubManager.save: Attempting to save a Club without a founder" );
            */
                 
        try {

            if( !club.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertClubSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateClubSql );

            if( club.getName() != null ) // name is unique unique and non null
                stmt.setString( 1, club.getName() );
            else 
                throw new ClubsException( "ClubManager.save: can't save a Club: name undefined" );

            if( club.getAddress() != null )
                stmt.setString( 2, club.getAddress() );
            else
                stmt.setNull( 2, java.sql.Types.VARCHAR );

            if( club.getEstablishedOn() != null ) {
                java.util.Date jDate = club.getEstablishedOn();
                java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                stmt.setDate( 3,  sDate );
            }
            else
                stmt.setNull(3, java.sql.Types.DATE);

            if( club.getPersonFounder() != null && club.getPersonFounder().isPersistent() )
                stmt.setLong( 4, club.getPersonFounder().getId() );
            else 
                throw new ClubsException( "ClubManager.save: can't save a Club: founder is not set or not persistent" );
            
            if( club.isPersistent() )
                stmt.setLong( 5, club.getId() );

            inscnt = stmt.executeUpdate();

            if( !club.isPersistent() ) {
                if( inscnt >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            clubId = r.getLong( 1 );
                            if( clubId > 0 )
                                club.setId( clubId ); // set this person's db id (proxy object)
                        }
                    }
                }
                else
                    throw new ClubsException( "ClubManager.save: failed to save a Club" );
            }
            else {
                if( inscnt < 1 )
                    throw new ClubsException( "ClubManager.save: failed to save a Club" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new ClubsException( "ClubManager.save: failed to save a Club: " + e );
        }
    }

    public List<Club> restore( Club club ) 
            throws ClubsException
    {
        String       selectClubSql = "select id, name, address, established from club";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Club>   clubs = new ArrayList<Club>();

        condition.setLength( 0 );
        
        // form the query based on the given Club object instance
        query.append( selectClubSql );
        
        if( club != null ) {
            if( club.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " where id = " + club.getId() );
            else if( club.getName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " where name = '" + club.getName() + "'" );
            else {

                if( club.getAddress() != null )
                    condition.append( " where address = '" + club.getAddress() + "'" );   

                if( club.getEstablishedOn() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    else
                        condition.append( " where" );
                    condition.append( " established = '" + club.getEstablishedOn() + "'" );
                }

            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Club objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                
                long   id;
                String name;
                String address;
                Date   establishedOn;
                Club   nextClub = null;
                
                ResultSet rs = stmt.getResultSet();
                
                // retrieve the retrieved clubs
                while( rs.next() ) {
                    
                    id = rs.getLong( 1 );
                    name = rs.getString( 2 );
                    address = rs.getString( 3 );
                    establishedOn = rs.getDate( 4 );
                    
                    nextClub = objectLayer.createClub(); // create a proxy club object
                    // and now set its retrieved attributes
                    nextClub.setId( id );
                    nextClub.setName( name );
                    nextClub.setAddress( address );
                    nextClub.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    nextClub.setPersonFounder( null );
                    
                    clubs.add( nextClub );
                }
                
                return clubs;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new ClubsException( "ClubManager.restore: Could not restore persistent Club objects; Root cause: " + e );
        }

        throw new ClubsException( "ClubManager.restore: Could not restore persistent Club objects" );
    }

    public Person restoreClubEstablishedByPerson( Club club ) 
            throws ClubsException
    {
        String       selectPersonSql = "select p.id, p.username, p.userpass, p.email, p.firstname, p.lastname, p.address, p.phone from person p, club c where p.id = c.founderid";              
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectPersonSql );
        
        if( club != null ) {
            if( club.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and c.id = " + club.getId() );
            else if( club.getName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and c.name = '" + club.getName() + "'" );
            else {

                if( club.getAddress() != null )
                    condition.append( " and c.address = '" + club.getAddress() + "'" );   

                if( club.getEstablishedOn() != null ) {
                    condition.append( " and c.established = '" + club.getEstablishedOn() + "'" );
                }

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                
                long   id;
                String userName;
                String password;
                String email;
                String firstName;
                String lastName;
                String address;
                String phone;  
                Person person = null;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    userName = rs.getString( 2 );
                    password = rs.getString( 3 );
                    email = rs.getString( 4 );
                    firstName = rs.getString( 5 );
                    lastName = rs.getString( 6 );
                    address = rs.getString( 7 );
                    phone = rs.getString( 8 );

                    person = objectLayer.createPerson( userName, password, email, firstName, lastName, address, phone );
                    person.setId( id );
                }
                
                return person;
            }
            else
                return null;
        }
        catch( Exception e ) {      // just in case...
            throw new ClubsException( "ClubManager.restoreEstablishedBy: Could not restore persistent Person object; Root cause: " + e );
        }
    }
    
    public void delete( Club club ) 
            throws ClubsException
    {
        String               deleteClubSql = "delete from club where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
             
        if( !club.isPersistent() ) // is the Club object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteClubSql );         
            stmt.setLong( 1, club.getId() );
            inscnt = stmt.executeUpdate();          
            if( inscnt == 1 ) {
                return;
            }
            else
                throw new ClubsException( "ClubManager.delete: failed to delete a Club" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new ClubsException( "ClubManager.delete: failed to delete a Club: " + e );        }
    }
}
