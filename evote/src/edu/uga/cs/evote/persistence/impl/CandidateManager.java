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
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;

class CandidateManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public CandidateManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Candidate candidate ) 
            throws EVException
    {
        String               insertCandidateSql = "insert into Candidate ( name, voteCount, isAlternate ) values ( ?, ?, ? )";              
        String               updateCandidateSql = "update Candidate set  name = ?, voteCount = ?, isAlternate = ? where candidateId = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 candidateId;
        List<Candidate> candidates = new ArrayList<Candidate>();
        
        try {
            
            if( !candidate.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertCandidateSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateCandidateSql );
            
            
            if( candidate.getName() != null )
                stmt.setString( 1, candidate.getName() );
            else 
                throw new EVException( "CandidateManager.save: can't save a Candidate: name undefined" );

            if( candidate.getVoteCount() >= 0 )
                stmt.setLong( 2, candidate.getVoteCount() );
            else 
                throw new EVException( "CandidateManager.save: can't save a Candidate: name undefined" );

            if (candidate.getIsAlternate())
            	stmt.setInt(3, 1);
            else
            	stmt.setInt(3, 0);
            
            	
            
            if( candidate.isPersistent() )
                stmt.setLong( 8, candidate.getId() );

            inscnt = stmt.executeUpdate();

            if( !candidate.isPersistent() ) {
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
                            candidateId = r.getLong( 1 );
                            if( candidateId > 0 )
                                candidate.setId( candidateId ); // set this person's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "CandidateManager.save: failed to save a Candidate" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "CandidateManager.save: failed to save a Candidate: " + e );
        }
    }

    public List<Candidate> restore( Candidate modelCandidate ) 
            throws EVException
    {
        String       selectOfficerSql = "select name, voteCount, isAlternate from Candidate";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Candidate> voters = new ArrayList<Candidate>();
        
        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectOfficerSql );
        
        if( modelCandidate != null ) {
            if( modelCandidate.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " where id = " + modelCandidate.getId() );
            else if( modelCandidate.getName() != null ) // Name is unique, so it is sufficient to get a person
                query.append( " where name = '" + modelCandidate.getName() + "'" );
            else {
            	condition.append("officer = 0"); //this value has to be true for voters
            	


                if( condition.length() > 0 ) {
                    query.append(  " where " );
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
                long   id;
                String name;
                int voteCount;
                boolean isAlternate;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    name = rs.getString( 2 );
                    voteCount = rs.getInt( 3 );
                    isAlternate = rs.getBoolean( 4 );
            

                    Candidate candidate = objectLayer.createCandidate(name, voteCount, isAlternate);
                    candidate.setId( id );

                    candidates.add( candidate );

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
    
    
    public void delete( Candidate candidate ) 
            throws EVException
    {
        String               deleteCandidateSql = "delete from Candidate where candidateId = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !candidate.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteCandidateSql );
            
            stmt.setLong( 1, candidate.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "CandidateManager.delete: failed to delete this candidate" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "CandidateManager.delete: failed to delete this candidate: " + e.getMessage() );
        }
    }
}
