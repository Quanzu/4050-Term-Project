package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.object.ObjectLayer;

class ElectionsOfficerManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public ElectionsOfficerManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( ElectionsOfficer officer ) 
            throws EVException
    {
        String               insertOfficerSql = "insert into User ( officer, fname, lname, userName, password, email, address ) values ( ?, ?, ?, ?, ?, ?, ? )";              
        String               updateOfficerSql = "update User  set officer = ?, fname = ?, lname = ?, userName = ?, password = ?, email = ?, address = ? where userId = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 userId;
        
        try {
            
            if( !officer.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertOfficerSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateOfficerSql );
            
            stmt.setInt(1, 1); //this user is an officer

            if( officer.getFirstName() != null )
                stmt.setString( 2, officer.getFirstName() );
            else 
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: fname undefined" );

            if( officer.getLastName() != null )
                stmt.setString( 3, officer.getLastName() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: last name undefined" );

            if( officer.getUserName() != null )
                stmt.setString( 4, officer.getUserName() );
            else 
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: username undefined" );
            
            if( officer.getPassword() != null )
                stmt.setString( 5, officer.getPassword() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: password undefined" );

            if( officer.getEmailAddress() != null )
                stmt.setString( 6,  officer.getEmailAddress() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: email undefined" );
            
            if( officer.getAddress() != null )
                stmt.setString( 7, officer.getAddress() );
            else
                stmt.setNull(7, java.sql.Types.VARCHAR);

            
            if( officer.isPersistent() )
                stmt.setLong( 8, officer.getId() );

            inscnt = stmt.executeUpdate();

            if( !officer.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( inscnt == 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            userId = r.getLong( 1 );
                            if( userId > 0 )
                                officer.setId( userId ); // set this person's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "ElectionsOfficerManager.save: failed to save an Officer" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectionsOfficerManager.save: failed to save an Officer: " + e );
        }
    }

    public List<ElectionsOfficer> restore( ElectionsOfficer modelOfficer ) 
            throws EVException
    {
        String       selectOfficerSql = "select id, fname, lname, userName, password, email, address from User";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<ElectionsOfficer> officers = new ArrayList<ElectionsOfficer>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelOfficer != null ) {
            if( modelOfficer.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " where id = " + modelOfficer.getId() );
            else if( modelOfficer.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " where userName = '" + modelOfficer.getUserName() + "'" );
            else {
            	condition.append("officer = 1"); //this value has to be true for officers
            	
                if( modelOfficer.getFirstName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " fname = '" + modelOfficer.getFirstName() + "'" );
                }

                if( modelOfficer.getLastName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " lname = '" + modelOfficer.getLastName() + "'" );
                }
            	
                if( modelOfficer.getPassword() != null )
                    condition.append( " password = '" + modelOfficer.getPassword() + "'" );

                if( modelOfficer.getEmailAddress() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " email = '" + modelOfficer.getEmailAddress() + "'" );
                }

                if( modelOfficer.getAddress() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " address = '" + modelOfficer.getAddress() + "'" );
                }


                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String fname;
                String lname;
                String userName;
                String password;
                String email;

                String address;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    fname = rs.getString( 2 );
                    lname = rs.getString( 3 );
                    userName = rs.getString( 4 );
                    password = rs.getString( 5 );
                    email = rs.getString( 6 );
                    address = rs.getString( 7 );

                    ElectionsOfficer officer = objectLayer.createElectionsOfficer( fname, lname, userName, password, email, address );
                    officer.setId( id );

                    officers.add( officer );

                }
                
                return officers;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectionsOfficerManager.restore: Could not restore persistent Officer object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectionsOfficerManager.restore: Could not restore persistent Officer objects" );
    }
    
    
    public void delete( ElectionsOfficer officer ) 
            throws EVException
    {
        String               deleteUserSql = "delete from person where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !officer.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );
            
            stmt.setLong( 1, officer.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "ElectionsOfficerManager.delete: failed to delete this User" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "ElectionsOfficerManager.delete: failed to delete this User: " + e.getMessage() );
        }
    }
}
