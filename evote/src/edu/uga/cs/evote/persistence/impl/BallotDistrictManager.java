package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.object.ObjectLayer;

public class BallotDistrictManager {
	
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public BallotDistrictManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public void storeElectoralDistrictHasBallotBallot( ElectoralDistrict electoralDistrict, Ballot ballot ) throws EVException{
    	//TODO
    	String               insertBallotDistrictSql = "insert into BallotDistrict (districtId, ballotId ) values ( ?, ?)";  
    	//String               insertBallotSql = "insert into ballot (ballotId, startTime, endTime ) values ( ?, ?, ?)";
       // String               updateBallotDistrictSql = "update BallotDistrict  set districtId= ?,  ballotId = ? where districtId = ?";              
       // String               updateBallotSql = "update ballot  set ballotId= ?,  startTime = ? , endTime = ? where ballotId = ?";              

        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 ballotId;
        long				 districtId;
        
        try {
            
            if( !electoralDistrict.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertBallotDistrictSql );
            if( !ballot.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertBallotDistrictSql );
            //else
                //stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );
            

            
            if(electoralDistrict.getId() >= 0 )
                stmt.setLong( 1, electoralDistrict.getId());
            else
                throw new EVException( "BallotDistrictMananger.save: can't save a electoral district: Id undefined" );
            
            if(ballot.getId() >= 0 )
                stmt.setLong( 2, ballot.getId());
            else
            	throw new EVException( "BallotDistrictMananger.save: can't save to ballot: Id undefined" );

            inscnt = stmt.executeUpdate();

            if( !electoralDistrict.isPersistent()) {
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
                            if( districtId > 0 ){
                                electoralDistrict.setId( districtId ); // set this person's db id (proxy object)
                                stmt = (PreparedStatement) conn.prepareStatement( insertBallotDistrictSql );
                                stmt.setLong(1,districtId);
                                inscnt = stmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "BallotDistrictManager.save: failed to save a to Ballot District" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "BallotDistrictManager.save: failed to Ballot District: " + e );
        }
    }
    

    public void deleteElectoralDistrictHasBallotBallot( ElectoralDistrict electoralDistrict, Ballot ballot ) throws EVException{
    	{
            String               deleteBallotDistrictSql = "delete t1, t2, t3 from ElectoralDistrict as t1 "
            									   + "inner join BallotDistrict as t2 on t1.districtId = t2.districtId "
            									   + "inner join VoterDistrict as t3 on t1.districtId = t3.districtId "
            									   + "where districtId = ?";              
            PreparedStatement    stmt = null;
            int                  inscnt;
            
            // form the query based on the given Person object instance
            if( !ballot.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
                return;
            if (!electoralDistrict.isPersistent())
            	return;
            
            try {
                stmt = (PreparedStatement) conn.prepareStatement( deleteBallotDistrictSql );
                
                stmt.setLong( 1, electoralDistrict.getId() );
                
                inscnt = stmt.executeUpdate();
                
                if( inscnt == 0 ) {
                    throw new EVException( "BallotDistrictManager.delete: failed to delete this Ballot District" );
                }
            }
            catch( SQLException e ) {
                throw new EVException( "ElectoralDistrictManager.delete: failed to delete this Ballot District: " + e.getMessage() );
            }
        }
    }
}

