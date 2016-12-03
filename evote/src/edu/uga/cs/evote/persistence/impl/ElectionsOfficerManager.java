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
        String               insertUserSql = "insert into User ( fname, lname, userName, password, email, address ) values ( ?, ?, ?, ?, ?, ? )";              
        String               updateUserSql = "update User set fname = ?, lname = ?, userName = ?, password = ?, email = ?, address = ? where userId = ?";              
        
        String				 insertOfficerSql = "insert into ElectionsOfficer (userId) values ( ? )";
        
        PreparedStatement    stmt;
        int                  inscnt;
        long                 officerId;
        
        try {
            
            if( !officer.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );
            

            if( officer.getFirstName() != null )
                stmt.setString( 1, officer.getFirstName() );
            else 
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: fname undefined" );

            if( officer.getLastName() != null )
                stmt.setString( 2, officer.getLastName() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: last name undefined" );

            if( officer.getUserName() != null )
                stmt.setString( 3, officer.getUserName() );
            else 
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: username undefined" );
            
            if( officer.getPassword() != null )
                stmt.setString( 4, officer.getPassword() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: password undefined" );

            if( officer.getEmailAddress() != null )
                stmt.setString( 5,  officer.getEmailAddress() );
            else
                throw new EVException( "ElectionsOfficerManager.save: can't save a User: email undefined" );
            
            if( officer.getAddress() != null )
                stmt.setString( 6, officer.getAddress() );
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);

            
            if( officer.isPersistent() )
                stmt.setLong( 7, officer.getId() );

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
                            officerId = r.getLong( 1 );
                            if( officerId > 0 ){
                                officer.setId( officerId ); // set this person's db id (proxy object)
                                stmt = (PreparedStatement) conn.prepareStatement( insertOfficerSql );
                                stmt.setLong(1,officerId);
                                inscnt = stmt.executeUpdate();
                            }
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
        String       selectOfficerSql = "select User.userId, fname, lname, userName, password, email, address from User, ElectionsOfficer where User.userId = ElectionsOfficer.userId";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<ElectionsOfficer> officers = new ArrayList<ElectionsOfficer>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelOfficer != null ) {
            if( modelOfficer.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and ElectionsOfficer.userId = " + modelOfficer.getId() );
            if( modelOfficer.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and userName = '" + modelOfficer.getUserName() + "'" );
            if( modelOfficer.getFirstName() != null ) {
                condition.append( " and " );
                condition.append( " fname = '" + modelOfficer.getFirstName() + "'" );
            }

                if( modelOfficer.getLastName() != null ) {
                    condition.append( " and " );
                    condition.append( " lname = '" + modelOfficer.getLastName() + "'" );
                }
            	
                if( modelOfficer.getPassword() != null ){
                    condition.append( " and " );
                    condition.append( " password = '" + modelOfficer.getPassword() + "'" );
                }
                    
                if( modelOfficer.getEmailAddress() != null ) {
                    condition.append( " and " );
                    condition.append( " email = '" + modelOfficer.getEmailAddress() + "'" );
                }

                if( modelOfficer.getAddress() != null ) {
                    condition.append( " and " );
                    condition.append( " address = '" + modelOfficer.getAddress() + "'" );
                }


                if( condition.length() > 0 ) {
                    //query.append(  " and " );
                    query.append( condition );
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
        String               deleteUserSql = "delete t1 from User as t1 where t1.userId = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !officer.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
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
