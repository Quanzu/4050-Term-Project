package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;

class ElectoralDistrictManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public ElectoralDistrictManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( ElectoralDistrict electoralDistrict ) 
            throws EVException
    {
        String               insertDistrictSql = "insert into ElectoralDistrict ( districtName ) values ( ? )";              
        String               updateDistrictSql = "update ElectoralDistrict set districtName = ? where districtId = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 districtId;
        
        try {
            
            if( !electoralDistrict.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertDistrictSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateDistrictSql );
            
            if( electoralDistrict.getName() != null )
                stmt.setString( 1, electoralDistrict.getName() );
            else 
                throw new EVException( "ElectoralDistrictManager.save: can't save a District: name undefined" );
            
            if( electoralDistrict.isPersistent() )
                stmt.setLong( 2, electoralDistrict.getId() );

            inscnt = stmt.executeUpdate();

            if( !electoralDistrict.isPersistent() ) {
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
                            districtId = r.getLong( 1 );
                            if( districtId > 0 )
                                electoralDistrict.setId( districtId ); // set this party's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "ElectoralDistrictManager.save: failed to save a district" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectoralDistrictManager.save: failed to save a district: " + e );
        }
    }

    public List<ElectoralDistrict> restore( ElectoralDistrict modelElectoralDistrict ) 
            throws EVException
    {
        String       selectDistrictSql = "select districtId, districtName from ElectoralDistrict";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        List<ElectoralDistrict> districts = new ArrayList<ElectoralDistrict>();
        
      	query.append( selectDistrictSql );
        
        if( modelElectoralDistrict != null ) {
            if( modelElectoralDistrict.getId() >= 0 ) 
                query.append( " where districtId = " + modelElectoralDistrict.getId() );
            else if( modelElectoralDistrict.getName() != null ) 
                query.append( " where districtName = '" + modelElectoralDistrict.getName() + "'" );
        }
        
        try {
            stmt = conn.createStatement();

            // retrieve the persistent Person objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String districtName;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    districtName = rs.getString( 2 );

                    ElectoralDistrict district = objectLayer.createElectoralDistrict( districtName );
                    district.setId( id );

                    districts.add( district );
                }
                return districts;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent district object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent district objects" );
    }

    public List<Ballot> restoreElectoralDistrictHasBallotBallot( ElectoralDistrict electoralDistrict ) throws EVException{
    	
    	//This seems to be the way club does restore 
      	String       selectDistrictSql = "select b.ballotId, b.openDate, b.closeDate from ballot as b, electoralDistrict as e, ballotDistrict bd "
      								   + "where bd.ballotId = b.ballotId and bd.districtId = e.districtId";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        List<Ballot> ballots = new ArrayList<Ballot>();
        
      	query.append( selectDistrictSql );
        
        if( electoralDistrict != null ) {
            if( electoralDistrict.getId() >= 0 ) 
                query.append( " and e.districtId = " + electoralDistrict.getId() );
            else if( electoralDistrict.getName() != null ) 
                query.append( " and e.districtName = '" + electoralDistrict.getName() + "'" );
            
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Ballot objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                
                long   id;
                Date   openDate;
              	Date   closeDate;
              	Ballot nextBallot = null;
              
                ResultSet rs = stmt.getResultSet();
              
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    openDate = rs.getDate( 2 );
					closeDate = rs.getDate( 3 );	
                  
                  	nextBallot = objectLayer.createBallot();
                    nextBallot.setId( id );
                  	nextBallot.setOpenDate( openDate );
                  	nextBallot.setCloseDate( closeDate );
                  	
                  	nextBallot.setElectoralDistrict( restore(electoralDistrict).get(0) );

                    ballots.add( nextBallot );

                }
                
                return ballots;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectoralDistrictManager.restoreElectoralDistrictHasBallotBallot: Could not restore persistent district object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectoralDistrictManager.restoreElectoralDistrictHasBallotBallot: Could not restore persistent district objects" );
    }
    
    public List<Voter> restoreVoterBelongsToElectoralDistrict( ElectoralDistrict electoralDistrict ) throws EVException{
    		String       selectDistrictSql = "select t1.userId, t1.fname, t1.lname, t1.userName, t1.password, t1.email, t1.address, t2.age from User as t1 "
    									   + "inner join Voter as t2 on t1.userId = t2.userId "
    									   + "inner join VoterDistrict as t3 on t1.userId = t3.userId "
    									   + "inner join ElectoralDistrict as t4 on t4.districtId = t3.districtId ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        List<Voter> voters = new ArrayList<Voter>();
        
        //condition.setLength(0); //necessary for this code?
      	query.append( selectDistrictSql );
        
        if( electoralDistrict != null ) {
            if( electoralDistrict.getId() >= 0 ) 
                query.append( " where t4.districtId = " + electoralDistrict.getId() );
            else if( electoralDistrict.getName() != null ) 
                query.append( " where t4.districtName = '" + electoralDistrict.getName() + "'" );
            
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Ballot objects
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
            throw new EVException( "ElectoralDistrictManager.restoreVoterBelongsToElectoralDistrict: Could not restore persistent district object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectoralDistrictManager.restoreVoterBelongsToElectoralDistrict: Could not restore persistent district objects" );
 
    }
    
    public void delete( ElectoralDistrict electoralDistrict ) 
            throws EVException
    {
        String               deleteDistrictSql = "delete t1 from ElectoralDistrict as t1 "
        					+ "where districtId = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !electoralDistrict.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteDistrictSql );
            
            stmt.setLong( 1, electoralDistrict.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "ElectoralDistrictManager.delete: failed to delete this district" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "ElectoralDistrictManager.delete: failed to delete this district: " + e.getMessage() );
        }
    }
}
