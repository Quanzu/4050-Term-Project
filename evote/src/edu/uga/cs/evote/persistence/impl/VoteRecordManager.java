package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;

public class VoteRecordManager {

	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public VoteRecordManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
	
	//Store a given VoteRecord object in the persistent data store. 
	//If the VoteRecord object to be stored is already persistent, 
	//the persistent object in the data store is updated.
	public void storeVoteRecord(VoteRecord voteRecord) throws EVException
	{
		String               insertVoteRecordSql = "insert into VoteRecord (date, ballotId, voterId) values ( ?, ?, ?)";              
        String               updateVoteRecordSql = "update VoteRecord  set date = ?, ballotId = ?, voterId = ? where voteRecordId = ?";              
                
        PreparedStatement    stmt;
        int                  inscnt;
        long                 voteRecordId;
        
        if(voteRecord.getBallot() == null || voteRecord.getVoter() == null )
        	throw new EVException( "VoteReocrdManager.save: Attempting to save a Vote Record with no ballot or voter defined" );
        if( !voteRecord.getBallot().isPersistent() || !voteRecord.getVoter().isPersistent() )
            throw new EVException( "VoteRecordManager.save: Attempting to save a Vote Record where either Ballot or Voter are not persistent" );

        try { 
        	 if( !voteRecord.isPersistent() )
                 stmt = (PreparedStatement) conn.prepareStatement( insertVoteRecordSql );
             else
                 stmt = (PreparedStatement) conn.prepareStatement( updateVoteRecordSql );
            
        	 
            if( voteRecord.getDate() != null )
            {
                    java.util.Date jDate = voteRecord.getDate();
                    java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                    stmt.setDate( 1, sDate );
            }
            else
            	stmt.setNull(1, java.sql.Types.DATE);  //THERE IS NO DATE
            
        	stmt.setLong( 2, voteRecord.getBallot().getId() );
            stmt.setLong( 3, voteRecord.getVoter().getId() );
            inscnt = stmt.executeUpdate();
        
            if(!voteRecord.isPersistent()){
            	if( inscnt >= 1 ) {
	                String sql = "select last_insert_id()";
	                if( stmt.execute( sql ) ) { // statement returned a result	
	                    // retrieve the result
	                    ResultSet r = stmt.getResultSet();
	                    // we will use only the first row!
	                    //
	                    while( r.next() ) {
	                        // retrieve the last insert auto_increment value
	                        voteRecordId = r.getLong( 1 );
	                        if(voteRecordId > 0 )
	                            voteRecord.setId(voteRecordId); // set this vote Record's db id (proxy object)
	                    }
	                }
	            }
            }
            else
                throw new EVException( "VoteRecordManager.save: failed to save a Vote Record" );
        
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "VoteRecordManager.save: failed to save a vote record: " + e );
        }

	}
	
	//Restore all VoteRecord objects that match attributes of the model VoteRecord.
	public List<VoteRecord> restoreVoteRecord(VoteRecord modelVoteRecord) throws EVException
	{
		String selectVoteRecordSql = "select voteRecordId, date, voterId, ballotId from VoteRecord";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );
		List<VoteRecord>  records = new ArrayList<VoteRecord>();

        condition.setLength( 0 );
        
        // form the query based on the given Club object instance
        query.append( selectVoteRecordSql );
        
        if(modelVoteRecord != null ) {
            if( modelVoteRecord.isPersistent() ) // id is unique, so it is sufficient to get a membership
                query.append( " where voteRecordId = '" + modelVoteRecord.getId() );
            else {
                if(modelVoteRecord.getBallot() != null && modelVoteRecord.getBallot().isPersistent()) {
                    condition.append( " ballotId = '" + modelVoteRecord.getBallot().getId() ); 
                }

                if(modelVoteRecord.getVoter() != null && modelVoteRecord.getVoter().isPersistent()) {
                    if( condition.length() > 0 )
                    	condition.append(" and ");
                	condition.append( " voterId = '" + modelVoteRecord.getVoter().getId() ); 
                }
                
                if(modelVoteRecord.getDate() != null ) {
                    if( condition.length() > 0 )
                    	condition.append(" and ");
                    condition.append( " date = '" + modelVoteRecord.getDate() + "'" );
                }

                if( condition.length() > 0 ){
                	query.append(" where ");
                    query.append( condition );
                }
            }
        }

        try {
            stmt = conn.createStatement();

            // retrieve the persistent ballot object
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                
                long voteRecordId;
                Date date;
                long ballotId;
                long voterId;

                while( rs.next() ) {

                    voteRecordId = rs.getLong( 1 );
                    date = rs.getDate(2);
                    ballotId = rs.getLong( 3 );
                    voterId = rs.getLong( 4 );
                    
                    Voter voter = objectLayer.createVoter();
                    voter.setId(voterId);
                    Ballot ballot = objectLayer.createBallot();
                    ballot.setId(ballotId);
                    VoteRecord voteRecordNext = objectLayer.createVoteRecord(objectLayer.getPersistence().restoreBallot(ballot).get(0), objectLayer.getPersistence().restoreVoter(voter).get(0), date);
                    
                    records.add(voteRecordNext);
                }
                    
                return records;
 
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "VoteRecordManager.restore: Could not restore persistent Vote Record objects; Root cause: " + e );
        }

        // if we reach this point, it's an error
        throw new EVException( "VoteRecordManager.restore: Could not restore persistent Vote Record objects" );
	}
	
	//Delete a given VoteRecord object from the persistent data store.
	public void deleteVoteRecord(VoteRecord voteRecord) throws EVException
	{
		//TODO
		String         deleteVoteRecordSql = "delete t1 from VoteRecord as t1 "
				   						   + "where voterRecordId = ?";              
		PreparedStatement    stmt = null;
		int                  inscnt;

		// form the query based on the given Person object instance
		if( !voteRecord.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
			return;

		try {
			stmt = (PreparedStatement) conn.prepareStatement( deleteVoteRecordSql );
			stmt.setLong( 1, voteRecord.getId() );
			
			inscnt = stmt.executeUpdate();

			if( inscnt == 0 ) {
				throw new EVException( "VoteRecordManager.delete: failed to delete this Vote Record" );
			}
		}
		catch( SQLException e ) {
			throw new EVException( "VoteRecordManager.delete: failed to delete this Vote Record: " + e.getMessage() );
		}
	}
}
//need store, restore and delete
