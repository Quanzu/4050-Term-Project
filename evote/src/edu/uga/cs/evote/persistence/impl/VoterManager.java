package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;

class VoterManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public VoterManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Voter voter ) 
            throws EVException
    {
        String               insertUserSql = "insert into User ( fname, lname, userName, password, email, address ) values ( ?, ?, ?, ?, ?, ? )";              
        String               updateUserSql = "update User  set fname = ?, lname = ?, userName = ?, password = ?, email = ?, address = ? where userId = ?";              
        
        String				 insertVoterSql = "insert into Voter ( userId, age ) values ( ?, ? )";
        
        PreparedStatement    stmt;
        int                  inscnt;
        long                 userId, voterId;
        
        try {
            
            if( !voter.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );
            
            if( voter.getFirstName() != null )
                stmt.setString( 1, voter.getFirstName() );
            else 
                throw new EVException( "VoterManager.save: can't save a User: fname undefined" );

            if( voter.getLastName() != null )
                stmt.setString( 2, voter.getLastName() );
            else
                throw new EVException( "VoterManager.save: can't save a User: last name undefined" );

            if( voter.getUserName() != null )
                stmt.setString( 3, voter.getUserName() );
            else 
                throw new EVException( "VoterManager.save: can't save a User: username undefined" );
            
            if( voter.getPassword() != null )
                stmt.setString( 4, voter.getPassword() );
            else
                throw new EVException( "VoterManager.save: can't save a User: password undefined" );

            if( voter.getEmailAddress() != null )
                stmt.setString( 5,  voter.getEmailAddress() );
            else
                throw new EVException( "VoterManager.save: can't save a User: email undefined" );
            
            if( voter.getAddress() != null )
                stmt.setString( 6, voter.getAddress() );
            else
                stmt.setNull(6, java.sql.Types.VARCHAR);

            
            if( voter.isPersistent() )
                stmt.setLong( 7, voter.getId() );

            inscnt = stmt.executeUpdate();

            if( !voter.isPersistent() ) {
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
                            if( userId > 0 ){
                                voter.setId( userId ); // set this person's db id (proxy object)
                                stmt = (PreparedStatement) conn.prepareStatement( insertVoterSql );
                                stmt.setLong(1,userId);
                                stmt.setInt(2, voter.getAge());
                                inscnt = stmt.executeUpdate();
                                if( inscnt == 1 ) {
                                    sql = "select last_insert_id()";
                                    if( stmt.execute( sql ) ) { // statement returned a result
                                        // retrieve the result
                                        ResultSet r2 = stmt.getResultSet();
                                        // we will use only the first row!
                                        while( r2.next() ) {
                                            // retrieve the last insert auto_increment value
                                            voterId = r2.getLong( 1 );
                                            if( voterId > 0 ){
                                                voter.setVoterId( voterId ); // set this person's db id (proxy object)                                               
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "VoterManager.save: failed to save an voter" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "VoterManager.save: failed to save an voter: " + e );
        }
    }

    public List<Voter> restore( Voter modelVoter ) 
            throws EVException
    {
        String       selectOfficerSql = "select User.userId, fname, lname, userName, password, email, address, age from User, Voter where User.userId = Voter.userId";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Voter> voters = new ArrayList<Voter>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelVoter != null ) {
            if( modelVoter.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and User.userId = " + modelVoter.getId() );
            else if( modelVoter.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and userName = '" + modelVoter.getUserName() + "'" );
            else {            	
                if( modelVoter.getFirstName() != null ) {
                    condition.append( " fname = '" + modelVoter.getFirstName() + "'" );
                }

                if( modelVoter.getLastName() != null ) {
                    condition.append( " and" );
                    condition.append( " lname = '" + modelVoter.getLastName() + "'" );
                }
            	
                if( modelVoter.getPassword() != null ){
                    condition.append( " and" );
                    condition.append( " password = '" + modelVoter.getPassword() + "'" );
                }

                if( modelVoter.getEmailAddress() != null ) {
                    condition.append( " and" );
                    condition.append( " email = '" + modelVoter.getEmailAddress() + "'" );
                }

                if( modelVoter.getAddress() != null ) {
                    condition.append( " and" );
                    condition.append( " address = '" + modelVoter.getAddress() + "'" );
                }


                if( condition.length() > 0 ) {
                    query.append(  " and " );
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
                long   userId;
                String fname;
                String lname;
                String userName;
                String password;
                String email;
                String address;
                int age;
                
                while( rs.next() ) {

                    userId = rs.getLong( 1 );
                    fname = rs.getString( 2 );
                    lname = rs.getString( 3 );
                    userName = rs.getString( 4 );
                    password = rs.getString( 5 );
                    email = rs.getString( 6 );
                    address = rs.getString( 7 );
                    age = rs.getInt(8);

                    Voter voter = objectLayer.createVoter( fname, lname, userName, password, email, address, age );
                    voter.setId( userId );
                    voters.add( voter );

                }
                
                return voters;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "VoterManager.restore: Could not restore persistent voter object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "VoterManager.restore: Could not restore persistent voter objects" );
    }
    
    public ElectoralDistrict restoreVoterBelongsToElectoralDistrict( Voter voter ) throws EVException{
        String       selectPersonSql = "select e.districtId, e.districtName from ElectoralDistrict e, Voter v, VoterDistrict vd where e.districtId = vd.districtId and v.voterId = vd.voterId";              
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        
        // form the query based on the given Person object instance
        query.append( selectPersonSql );
        
        if( voter != null ) {
            if( voter.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and c.id = " + voter.getId() );
            else {
            	return null;  
            }
        }else
        	return null;
                
        try {
            stmt = conn.createStatement();

            // retrieve the persistent Person object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                
                long   districtId;
                String districtName;
                ElectoralDistrict electoralDistrict = null;
                
                while( rs.next() ) {
                    districtId = rs.getLong( 1 );
                    districtName = rs.getString( 2 );

                    electoralDistrict = objectLayer.createElectoralDistrict( districtName );
                    electoralDistrict.setId( districtId );
                }
                
                return electoralDistrict;
            }
            else
                return null;
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "VoterManager.restoreVoterBelongsToElectoralDistrict: Could not restore persistent Electoral object; Root cause: " + e );
        }	
    }
    
    public void delete( Voter voter ) 
            throws EVException
    {
        String               deleteUserSql = "delete t1 from User as t1 "
        								   + "where t1.userId = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !voter.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );        
            stmt.setLong( 1, voter.getId() );          
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "VoterManager.delete: failed to delete this voter from User" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "VoterManager.delete: failed to delete this Voter: " + e.getMessage() );
        }
    }
}
