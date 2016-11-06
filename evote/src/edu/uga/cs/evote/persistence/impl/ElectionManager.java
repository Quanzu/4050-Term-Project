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
	
    public List<Candidate> restoreCandidateIsCandidateInElection( Election election ) throws EVException{
    	
    	
    	String selectElectionSql = "select c.candidateId, c.name, c.voteCount, c.isAlternate"
                + "e.electionId, e.name, e.isPartisan, e.alternateAllowed, e.voteCount" + "ce.electionId, ce.candidateId"
                + "from candidate c, election e, candidateElection ce where election.id = e.electionId";              
    	Statement    stmt = null;
    	StringBuffer query = new StringBuffer( 100 );
    	StringBuffer condition = new StringBuffer( 100 );
    	List<Candidate>  candidates = new ArrayList<Candidate>();
    	
    	if(election.getId() >= 0 && !election.isPersistent() )
            throw new EVException( "ElectionManager.restore: the argument election includes a non-persistent Election object" );
        
        condition.setLength( 0 );
        
        query.append( selectElectionSql);
        if(election != null ) {
            if(election.isPersistent() ) // id is unique, so it is sufficient to get an election
                query.append( " where id = " + election.getId() );
            else {

                if(election.getName() != null ) {
                    condition.append( " and e.name = " + election.getName().getId() ); 
                }

                if( election.getIsPartisan() == true ||  election.getIsPartisan() == false) {
                    condition.append( " and e.isPartisan = " + election.getIsPartisan()); 
                }
                
                if(election.getIsAlternateAllowed() == true || election.getIsAlternateAllowed() == false ) {
                    // fix the date conversion
                    condition.append( " and e.alternateAllowed = " + election.getIsAlternateAllowed());
                }
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
                              
                long   id;
                //Date   joined;
                //long   personid;
                String name;
                int voteCount;
                int isAlternate;
                
                Candidate candidate = null;
                Election nextElection = null;
                
                while( rs.next() ) {

                    name = rs.getString( 1 );
                    voteCount = rs.getInt( 2 );
                    isAlternate = rs.getInt( 3 );
                    id = rs.getLong( 4 );
                    
                    
                    // create a Candidate proxy object
                    candidate = objectLayer.createCandidate();
                    candidate.setName(name);
                    candidate.setVoteCount(voteCount);
                    candidate.setId(id);
                    //candidate.setIsAlternate(isAlternate);  CAN'T CAUSE THERE IS NO .getBoolean();?
                    
                    
                    candidates.add(candidate);
                }
                    
                return candidates;
            }
            catch( Exception e ) {      // just in case...
                throw new EVException( "MembershipManager.restore: Could not restore persistent Membership objects; Root cause: " + e );
            }

            // if we reach this point, it's an error
            throw new EVException( "MembershipManager.restore: Could not restore persistent Membership objects" );

    	
    	
 //IDK
/*
    	String       selectElectionSql = "select electionId from CandidateElection";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        List<Candidate> candidates = new ArrayList<Candidate>();
        List<Election> elections = new ArrayList<Election>();
      	query.append( selectElectionSql );
        
        if( election != null ) {
            if( election.getId() >= 0 ) 
                query.append( " where electionId = " + election.getId() );
   
        }
        
        try {
            stmt = conn.createStatement();

            // retrieve the persistent Person objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                long candidateId;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    candidateId = rs.getLong( 2 );

                    //ElectoralDistrict district = objectLayer.createElectoralDistrict( districtName );
                    elections = objectLayer.findElection(election);
                    election.setId( id );

                    candidates.add(cand);
                }
                return candidates;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectionManager.restore: Could not restore persistent candidate object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectionManager.restore: Could not restore persistent candidate objects" );
    */
    }
    

}
