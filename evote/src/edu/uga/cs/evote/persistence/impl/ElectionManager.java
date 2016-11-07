package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.object.ObjectLayer;

public class ElectionManager {
    
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
	public ElectionManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
	
	//Return Candidates running in a given Election.
    public List<Candidate> restoreCandidateIsCandidateInElection( Election election ) throws EVException{
    	
    	String       selectCandidateSql = "select c.candidateId, c.name, c.voteCount c.isAlternate " 
    									+ "from Election e, Candidate c, CandidateElection ce " 
    									+ "where e.electionId = ce.electionId and c.candidateId = ce.candidateId";              
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
    	List<Candidate>  candidates = new ArrayList<Candidate>();
        
        // form the query based on the given Person object instance
        query.append( selectCandidateSql );   	
    
    	
    	if(!election.isPersistent())
            throw new EVException( "ElectionManager.restore: the argument election includes a non-persistent Election object" );
                
        query.append( selectCandidateSql);
        if(election != null ) {
            if(election.isPersistent() ) // id is unique, so it is sufficient to get an election
                query.append( " and e.electionId = " + election.getId() );
            else {
                if(election.getOffice() != null ) {
                    condition.append( " and e.name = " + election.getOffice()); 
                }

                if( election.getIsPartisan()) 
                    condition.append( " and e.isPartisan = 1"); 
                else
                    condition.append( " and e.isPartisan = 0");
                
                if(election.getAlternateAllowed())
                    condition.append( " and e.alternateAllowed = 1");
                else
                    condition.append( " and e.alternateAllowed = 0");
         
                if (election.getVoteCount() >= 0)
                	condition.append(" and e.voteCount = " + election.getVoteCount());

                if( condition.length() > 0 )
                    query.append( condition );
            }
        }

        try {
            stmt = conn.createStatement();

            // retrieve the persistent Candidate object
            //IDK HOW TO MAKE SURE THAT ALL CANDIDATES FROM THIS CERTAIN ELECTION IS PUT IN LIST.
            if( stmt.execute( query.toString() ) ) { // statement returned a result               
                ResultSet rs = stmt.getResultSet();
                
                long candidateId;
                String name;
                int voteCount;
                int isAlternate;
                Candidate candidate = null; 
                
                while( rs.next() ) {
                    candidateId = rs.getLong( 1 );
                    name = rs.getString( 2 );                 
                    voteCount = rs.getInt( 3 );
                    isAlternate = rs.getInt( 4 );
                    
                    // create a Candidate proxy object
                    candidate = objectLayer.createCandidate();
                    candidate.setId(candidateId);
                    candidate.setName(name);
                    candidate.setVoteCount(voteCount);
                    if(isAlternate == 1)
                    	candidate.setIsAlternate(true);
                    else
                    	candidate.setIsAlternate(false);
                    
                    candidates.add(candidate);
                }
                    
                return candidates;
            }
        }            
        catch( Exception e ) {      // just in case...
        	throw new EVException( "ElectionManager.restore: Could not restore persistent Election objects; Root cause: " + e );
        }

        // if we reach this point, it's an error
        throw new EVException( "ElectionManager.restore: Could not restore persistent Election objects" );
    }
}
