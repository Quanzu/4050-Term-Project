package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.object.ObjectLayer;

public class CandidateElectionManager {
    
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
	public CandidateElectionManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

	
	public void storeCandidateIsCandidateInElection( Candidate candidate, Election election ) throws EVException{
		String               insertCandidateElectionSql = "insert into CandidateElection (candidateId, electionId ) values ( ?, ?)";              

        PreparedStatement    stmt = null;
        int                  inscnt;
        
        try {       
            stmt = (PreparedStatement) conn.prepareStatement( insertCandidateElectionSql );          
            
            if(election.isPersistent() )
                stmt.setLong( 1, election.getId());
            else
                throw new EVException( "CandidateElectionMananger.save: election is not persistent" );
            
            if(candidate.isPersistent() )
                stmt.setLong( 2, candidate.getId());
            else
            	throw new EVException( "CandidateElectionMananger.save: candidate is not persistent" );

            inscnt = stmt.executeUpdate();
            if( inscnt < 1 )
            	throw new EVException( "CandidateElection.save: failed to save a to Candidate Election" ); 
            
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "CandidateElection.save: failed to Candidate Election: " + e );
        }
    }
    
    public void deleteCandidateIsCandidateInElection( Candidate candidate, Election election ) throws EVException{
    	String         deleteCandidateElectionSql = "delete t1 from CandidateElection as t1 "
				   + "where candidateId = ? and electionId = ?";              
    	PreparedStatement    stmt = null;
    	int                  inscnt;

// form the query based on the given Person object instance
    	if( !candidate.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
    		return;
    	if (!election.isPersistent())
    		return;

    	try {
    		stmt = (PreparedStatement) conn.prepareStatement( deleteCandidateElectionSql );
    		stmt.setLong( 1, election.getId() );
    		stmt.setLong( 2, candidate.getId());

    		inscnt = stmt.executeUpdate();

    		if( inscnt == 0 ) {
    			throw new EVException( "CandidateElectionManager.delete: failed to delete this Candidate Election" );
    		}
    	}
    	catch( SQLException e ) {
    		throw new EVException( "CandidateElectionManager.delete: failed to delete this Candidate Election: " + e.getMessage() );
    	}
    }
    
}
