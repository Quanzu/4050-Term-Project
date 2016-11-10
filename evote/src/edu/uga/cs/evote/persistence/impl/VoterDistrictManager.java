package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;

public class VoterDistrictManager {

	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
	public VoterDistrictManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
	
    public void storeVoterBelongsToElectoralDistrict( Voter voter, ElectoralDistrict electoralDistrict ) throws EVException{
    	String               insertVoterDistrictSql = "insert into VoterDistrict (voterId, districtId ) values ( ?, ?)";              

        PreparedStatement    stmt = null;
        int                  inscnt;
        
        try {       
            stmt = (PreparedStatement) conn.prepareStatement( insertVoterDistrictSql );          
            
            if(voter.isPersistent() )
                stmt.setLong( 1, voter.getVoterId());
            else
                throw new EVException( "VoterDistrictMananger.save: voter is not persistent" );
            
            
            if(electoralDistrict.isPersistent() )
                stmt.setLong( 2, electoralDistrict.getId());
            else
            	throw new EVException( "VoterDistrictMananger.save: electoral district is not persistent" );
            	

            inscnt = stmt.executeUpdate();
            if( inscnt < 1 )
            	throw new EVException( "VoterDistrict.save: failed to save to Voter District" ); 
            
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "VoterDistrict.save: failed to Voter District: " + e );
        }
    }
    
    public void deleteVoterBelongsToElection( Voter voter, ElectoralDistrict electoralDistrict ) throws EVException{
    	String         deleteVoterDistrictSql = "delete t1 from VoterDistrict as t1 "
				   + "where voterId = ? and districtId = ?";              
 	PreparedStatement    stmt = null;
 	int                  inscnt;

//form the query based on the given Person object instance
 	if( !voter.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
 		return;
 	if (!electoralDistrict.isPersistent())
 		return;

 	try {
 		stmt = (PreparedStatement) conn.prepareStatement( deleteVoterDistrictSql );
 		stmt.setLong( 1, voter.getVoterId() );
 		stmt.setLong( 2, electoralDistrict.getId());

 		inscnt = stmt.executeUpdate();

 		if( inscnt == 0 ) {
 			throw new EVException( "VoterDistrictManager.delete: failed to delete this Voter District" );
 		}
 	}
 	catch( SQLException e ) {
 		throw new EVException( "VoterDistrictManager.delete: failed to delete this Voter District: " + e.getMessage() );
 	}
 }
    
}
