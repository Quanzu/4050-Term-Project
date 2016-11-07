package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.object.ObjectLayer;

public class ElectionManager {
    
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
	public ElectionManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
	
	public void store( Election election ) 
            throws EVException
    {
        String               insertCandidateSql = "insert into Election ( office, isPartisan, alternateAllowed, voteCount ) values ( ?, ?, ?, ? )";              
        String               updateCandidateSql = "update Candidate set  office = ?, isPartisan = ?, alternateAllowed = ?, voteCount = ? where electionId = ?";
        PreparedStatement    stmt;
        int                  inscnt;
        long                 electionId;
        
        try {
            
            if( !election.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertCandidateSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateCandidateSql );
            
            
            if( election.getOffice() != null )
                stmt.setString( 1, election.getOffice() );
            else 
                throw new EVException( "ElectionManager.save: can't save an Election: name undefined" );

            if( election.getIsPartisan())
                stmt.setInt( 2, 1 );
            else 
            	stmt.setInt(2, 0);
            	
            if (election.getAlternateAllowed())
            	stmt.setInt(3, 1);
            else
            	stmt.setInt(3, 0);
            	
            if( election.getVoteCount() >= 0 )
                stmt.setLong( 2, election.getVoteCount() );
            else 
                throw new EVException( "ElectionManager.save: can't save an Election: voteCount undefined" );
            
            if( election.isPersistent() )
                stmt.setLong( 5, election.getId() );

            inscnt = stmt.executeUpdate();
            if( !election.isPersistent() ) {
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
                            electionId = r.getLong( 1 );
                            if( electionId > 0 )
                                election.setId( electionId ); // set this person's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "ElectionManager.save: failed to save an election" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectionManager.save: failed to save an election: " + e );
        }
    }
	
	
	  public List<Election> restore( Election modelElection ) 
	            throws EVException
	    {
	        String       selectElectionSql = "select electionId, office, isPartisan, alternateAllowed, voteCount from Election";
	        Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	        StringBuffer condition = new StringBuffer( 100 );
	        List<Election> elections = new ArrayList<Election>();
	        
	        
	        // form the query based on the given Person object instance
	        query.append( selectElectionSql );
	        
	        if( modelElection != null ) {
	            if( modelElection.getId() >= 0 ) // id is unique, so it is sufficient to get a person
	                query.append( " where electionId = " + modelElection.getId() );
	            else if( modelElection.getOffice() != null ) // Name is unique, so it is sufficient to get a person
	                query.append( " where office = '" + modelElection.getOffice() + "'" );
	            else {	            	
	            	if(modelElection.getAlternateAllowed())
	            		condition.append(" isPartisan = 1 ");
	            	else
	            		condition.append(" isPartisan = 0 ");
	            	
	            	if(modelElection.getAlternateAllowed())
	            		condition.append(" and alternateAllowed = 1");
	            	else
	            		condition.append(" and alternateAllowed = 0");
	            	
	            	condition.append(" and voteCount = '" + modelElection.getVoteCount() + "'");
	                    
	            	query.append(  " where " );
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
	                String office;
	                int isPartisan;
	                int alternateAllowed;
	                int voteCount;
	                
	                while( rs.next() ) {
	                    id = rs.getLong( 1 );
	                    office = rs.getString( 2 );
	                    isPartisan = rs.getInt(3);
	                    alternateAllowed = rs.getInt( 4 );
	                    voteCount = rs.getInt( 5 );

	                    Election election = objectLayer.createElection();
	                    election.setId( id );
	                    election.setOffice(office);
	                    
	                    if(isPartisan == 1)
	                    	election.setIsPartisan(true);
	                    else
	                    	election.setIsPartisan(false);
	     
	                    if(alternateAllowed == 1)
	                    	election.setAlternateAllowed(true);
	                    else
	                    	election.setAlternateAllowed(false);	  
	                    
	                    election.setVoteCount(voteCount);
	                    elections.add( election );
	                }
	                
	                return elections;
	            }
	        }
	        catch( Exception e ) {      // just in case...
	            throw new EVException( "ElectionManager.restore: Could not restore persistent election object; Root cause: " + e );
	        }
	        
	        // if we get to this point, it's an error
	        throw new EVException( "ElectionManager.restore: Could not restore persistent election objects" );
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
