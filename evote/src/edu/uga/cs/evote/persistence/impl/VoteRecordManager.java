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
        String               updateVoteRecordSql = "update VoteRecord  set date = ?, ballotId = ?, voterId = ? where ballotId = ?";              
        
        //String				 insertOfficerSql = "insert into ElectionsOfficer (userId) values ( ? )";
        
        PreparedStatement    stmt;
        int                  inscnt;
        long                 ballotId;
        long				 voteRecordId;
        
        if(voteRecord.getBallot() == null || voteRecord.getVoter() == null )
        	throw new EVException( "VoteReocrdManager.save: Attempting to save a Vote Record with no ballot or voter defined" );
        if( !voteRecord.getBallot().isPersistent() || !voteRecord.getVoter().isPersistent() )
            throw new EVException( "VoteRecordManager.save: Attempting to save a Vote Record where either Ballot or Voter are not persistent" );

        try {
            
        	 if( !voteRecord.isPersistent() )
                 stmt = (PreparedStatement) conn.prepareStatement( insertVoteRecordSql );
             else
                 stmt = (PreparedStatement) conn.prepareStatement( updateVoteRecordSql );
            
        	 
        	 stmt.setLong( 1, voteRecord.getBallot().getId() );
             stmt.setLong( 2, voteRecord.getVoter().getId() );
        	 
            if( voteRecord.getDate() != null )
            {
                    java.util.Date jDate = voteRecord.getDate();
                    java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                    stmt.setDate( 3, sDate );
            }
                else
                    stmt.setNull(3, java.sql.Types.DATE);  //THERE IS NO DATE
            

            inscnt = stmt.executeUpdate();

            
            //Do we need this part?
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
            else
                throw new EVException( "VoteRecordManager.save: failed to save a Vote Record" );
        
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "VoteRecordManager.save: failed to save an Officer: " + e );
        }

	}
	
	//Restore all VoteRecord objects that match attributes of the model VoteRecord.
	public List<VoteRecord> restoreVoteRecord(VoteRecord voteRecord) throws EVException
	{
		String selectVoteRecordSql = "select vr.date, vr.voterId, vr.ballotId"
				+ "b.ballotId, b.openDate" + "v.voterId, v.userId, v.age"
                + "from VoteRecord vr, Ballot b, voter v where vr.voterId == v.voterId and vr.ballotId == b.ballotId and vr.date = b.openDate";              
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );
		List<VoteRecord>  records = new ArrayList<VoteRecord>();

		
		if(voteRecord.getBallot() == null && !voteRecord.getBallot().isPersistent())
        	throw new EVException( "VoteReocrdManager.restore: the argument vote record includes a non-persistent ballot object" );
        if( voteRecord.getVoter() == null && !voteRecord.getVoter().isPersistent() )
            throw new EVException( "VoteRecordManager.restore: the argument vote record includes a non-persistent voter object" );

		//TODO
        condition.setLength( 0 );
        
        // form the query based on the given Club object instance
        query.append( selectVoteRecordSql );
        
        if(voteRecord != null ) {
            if( voteRecord.isPersistent() ) // id is unique, so it is sufficient to get a membership
                query.append( " where id = " + voteRecord.getId() );
            else {

                if(voteRecord.getBallot() != null ) {
                    condition.append( " and vr.ballotId = " + voteRecord.getBallot().getId() ); 
                }

                if(voteRecord.getVoter() != null ) {
                	condition.append( " and vr.voterId = " + voteRecord.getVoter().getId() ); 
                }
                
                if(voteRecord.getDate() != null ) {
                    // fix the date conversion
                    condition.append( " and vr.date = '" + voteRecord.getDate() + "'" );
                }

                if( condition.length() > 0 )
                    query.append( condition );
            }
        }

        try {
            stmt = conn.createStatement();

            // retrieve the persistent ballot object
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                
            	//I may have duplicate variables that hold the same thing...
                ResultSet rs = stmt.getResultSet();
                              
                long   ballotId;
                Date   open;
                
                int 	age;
                long   voterId;
                long	userId;
                long vrVoterId;
                long vrBallotId;
                Date vrDate;
                
                Voter voter = null;
                Ballot ballot = null;
                VoteRecord voteRecordNext = null;

                while( rs.next() ) {

                    vrDate = rs.getDate( 1 );
                    vrVoterId = rs.getLong( 2 );
                    vrBallotId = rs.getLong( 3 );
                    ballotId = rs.getLong( 4 );
                    open = rs.getDate( 5 );
                    voterId = rs.getLong( 6 );
                    userId = rs.getLong( 7 );
                    age = rs.getInt( 8 );
                    
                    // create a Voter proxy object
                    voter = objectLayer.createVoter();
                    voter.setAge(age);
                    voter.setVoterId(voterId);
                    voter.setId(userId);
                    
                    
                    // create a proxy Ballot object    
                    ballot = objectLayer.createBallot(); 
                    // and now set its attributes
                    ballot.setId(ballotId);
                    ballot.setOpenDate(open);
                    
                   /* DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    Date dateobj = new Date();
                    df.format(dateobj);
                    */
                    // now, create a Vote Record object
                    
                    //uses ballot opendate or could use vrDate, but they are the same...
                    voteRecordNext = objectLayer.createVoteRecord(ballot, voter, open);
                    //voteRecordNext.setId( id );
                    
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
				   + "where voterId = ? and ballotId = ? date = ?";              
		PreparedStatement    stmt = null;
		int                  inscnt;

		// form the query based on the given Person object instance
		if( !voteRecord.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
			return;

		try {
			stmt = (PreparedStatement) conn.prepareStatement( deleteVoteRecordSql );
			stmt.setLong( 1, voteRecord.getVoter().getId() );
			stmt.setLong( 2, voteRecord.getBallot().getId() );
			stmt.setDate( 3, (Date) voteRecord.getDate() );
			

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