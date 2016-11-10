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
    	String               insertBallotDistrictSql = "insert into BallotDistrict (districtId, ballotId ) values ( ?, ?)";              

        PreparedStatement    stmt = null;
        int                  inscnt;
        
        try {       
            stmt = (PreparedStatement) conn.prepareStatement( insertBallotDistrictSql );          
            
            if(electoralDistrict.isPersistent() )
                stmt.setLong( 1, electoralDistrict.getId());
            else
                throw new EVException( "BallotDistrictMananger.save: Electoral District is not persistent" );
            
            if(ballot.isPersistent() )
                stmt.setLong( 2, ballot.getId());
            else
            	throw new EVException( "BallotDistrictMananger.save: Ballot is not persistent" );

            inscnt = stmt.executeUpdate();
            if( inscnt < 1 )
            	throw new EVException( "BallotDistrictManager.save: failed to save a to Ballot District" ); 
            
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "BallotDistrictManager.save: failed to Ballot District: " + e );
        }
    }
    

    public void deleteElectoralDistrictHasBallotBallot( ElectoralDistrict electoralDistrict, Ballot ballot ) throws EVException{
    	{
            String         deleteBallotDistrictSql = "delete t1 from BallotDistrict as t1 "
            									   + "where districtId = ? and ballotId = ?";              
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
                stmt.setLong( 2, ballot.getId());
                
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

