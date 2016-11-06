package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;

public class CandidatePartyManager {

	
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
	public CandidatePartyManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
	
    public void storeCandidateIsMemberOfPoliticalParty( Candidate candidate, PoliticalParty politicalParty ) throws EVException{
    	String               insertCandidatePartySql = "insert into CandidateParty (candidateId, partyId ) values ( ?, ?)";              

        PreparedStatement    stmt = null;
        int                  inscnt;
        
        try {       
            stmt = (PreparedStatement) conn.prepareStatement( insertCandidatePartySql );          
            
            if(candidate.isPersistent() )
                stmt.setLong( 1, candidate.getId());
            else
                throw new EVException( "CandidatePartyMananger.save: candidate is not persistent" );
            
            if(politicalParty.isPersistent() )
                stmt.setLong( 2, politicalParty.getId());
            else
            	throw new EVException( "CandidatePartyMananger.save: political party is not persistent" );

            inscnt = stmt.executeUpdate();
            if( inscnt < 1 )
            	throw new EVException( "CandidateParty.save: failed to save a to Candidate Party" ); 
            
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "CandidateParty.save: failed to Candidate Party: " + e );
        }
    }
    
    public void deleteCandidateIsMemberOfElection( Candidate candidate, PoliticalParty politicalParty ) throws EVException{
    	
    	String         deleteCandidatePartySql = "delete t1 from CandidateParty as t1 "
				   + "where candidateId = ? and partyId = ?";              
 	PreparedStatement    stmt = null;
 	int                  inscnt;

//form the query based on the given Person object instance
 	if( !candidate.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
 		return;
 	if (!politicalParty.isPersistent())
 		return;

 	try {
 		stmt = (PreparedStatement) conn.prepareStatement( deleteCandidatePartySql );
 		stmt.setLong( 1, candidate.getId() );
 		stmt.setLong( 2, politicalParty.getId());

 		inscnt = stmt.executeUpdate();

 		if( inscnt == 0 ) {
 			throw new EVException( "CandidatePartyManager.delete: failed to delete this Candidate Party" );
 		}
 	}
 	catch( SQLException e ) {
 		throw new EVException( "CandidatePartyManager.delete: failed to delete this Candidate Party: " + e.getMessage() );
 	}
 }
    

}
