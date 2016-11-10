package edu.uga.cs.evote.persistence.impl;

import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Issue;


public class BallotManager {
	
	private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
	public BallotManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
	
	public void store(Ballot ballot){
		//TODO
	}
	
    public void storeBallotIncludesBallotItem( Ballot ballot, BallotItem ballotItem ) throws EVException{
    	//TODO
      //Check if Issue or Election and then store in the IssueBallot or ElectionBallot, instanceOf(classname)
      if(ballotItem instanceof Issue){
        String               insertIssueBallotSql = "insert into IssueBallot (issueId, ballotId ) values ( ?, ?)";              

        PreparedStatement    stmt = null;
        int                  inscnt;
        
        try {       
            stmt = (PreparedStatement) conn.prepareStatement( insertIssueBallotSql );          
            
            if(ballotItem.isPersistent() )
                stmt.setLong( 1, ballotItem.getId());
            else
                throw new EVException( "BallotMananger.save: ballotItem is not persistent" );
            
            if(ballot.isPersistent() )
                stmt.setLong( 2, ballot.getId());
            else
            	throw new EVException( "BallotMananger.save: ballot is not persistent" );

            inscnt = stmt.executeUpdate();
            if( inscnt < 1 )
            	throw new EVException( "Ballot.save: failed to save a to IssueBallot" ); 
            
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot.save: failed to IssueBallot: " + e );
        }
      }
       else if(ballotItem instanceof Election){
        String               insertElectionBallotSql = "insert into ElectionBallot (electionId, ballotId ) values ( ?, ?)";              

        PreparedStatement    stmt = null;
        int                  inscnt;
        
        try {       
            stmt = (PreparedStatement) conn.prepareStatement( insertElectionBallotSql );          
            
            if(ballotItem.isPersistent() )
                stmt.setLong( 1, ballotItem.getId());
            else
                throw new EVException( "BallotMananger.save: ballotItem is not persistent" );
            
            if(ballot.isPersistent() )
                stmt.setLong( 2, ballot.getId());
            else
            	throw new EVException( "BallotMananger.save: ballot is not persistent" );

            inscnt = stmt.executeUpdate();
            if( inscnt < 1 )
            	throw new EVException( "Ballot.save: failed to save a to ElectionBallot" ); 
            
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Ballot.save: failed to ElectionBallot: " + e );
        }
      }   
    }
    
    public List<Ballot> restore(Ballot modelBallot){
    	//TODO
    	return null;
    }
    
    public Ballot restoreBallotIncludesBallotItem( BallotItem ballotItem ) throws EVException{
    	
       if(ballotItem instanceof Issue){
      	String       selectIssueBallotSql = "select i.ballotId, i.voteCount from ballotItem as i, ballot as b, issueBallot ib "
      								   + "where ib.issueId = i.ballotItemId and ib.ballotId = b.ballotId";              
        PreparedStatement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Ballot object instance
        query.append( selectIssueBallotSql );
        //Should it be like this ? or change to issueBallot instead of ballotItem
        if( ballotItem != null ) {
            if( ballotItem.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and i.id = " + ballotItem.getId() );
            else if( ballotItem.getVoteCount() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and i.voteCount = '" + ballotItem.getVoteCount() + "'" );
            else {

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent ElectoralDistrict object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                
                long   id;
                Date   openDate;
              	Date   closeDate;
                Ballot ballot = null;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    openDate = rs.getDate( 2 );
                  	closeDate = rs.getDate( 3 );

                    b = objectLayer.createBallot(openDate,closeDate);
                    ballot.setId( id );
                }
                
                return ballot;
            }
            else
                return null;
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.restoreBallotIncludesBallotItem: Could not restore persistent elec object; Root cause: " + e );
        }
       }
       if(ballotItem instanceof Election){
      	String       selectElectionBallotSql = "select i.ballotId, i.voteCount from ballotItem as i, ballot as b, electionBallot eb "
      								   + "where eb.electionId = i.ballotItemId and eb.ballotId = b.ballotId";              
        PreparedStatement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Ballot object instance
        query.append( selectElectionBallotSql );
        
        if( ballotItem != null ) {
            if( ballotItem.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and i.id = " + ballotItem.getId() );
<<<<<<< Updated upstream
            else if( ballotItem.getVoteCount() != null ) // userName is unique, so it is sufficient to get a person
=======
            else if( ballotItem.getVoteCount() >= 0 ) // userName is unique, so it is sufficient to get a person
>>>>>>> Stashed changes
                query.append( " and i.voteCount = '" + ballotItem.getVoteCount() + "'" );
            else {

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent ElectoralDistrict object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                
                long   id;
                Date   openDate;
              	Date   closeDate;
                Ballot ballot = null;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    openDate = rs.getDate( 2 );
                  	closeDate = rs.getDate( 3 );

                    b = objectLayer.createBallot(openDate,closeDate);
                    ballot.setId( id );
                }
                
                return ballot;
            }
            else
                return null;
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.restoreBallotIncludesBallotItem: Could not restore persistent elec object; Root cause: " + e );
        }
       }
    }

    public List<BallotItem> restoreBallotIncludesBallotItem( Ballot ballot ) throws EVException{
      if(ballotItem instanceof Issue){	
      String       selectIssueBallotSql = "select i.ballotItemId, i.voteCount from ballotItem as i, ballot as b, issueBallot ib "
      								   + "where ib.issueId = i.ballotItemId and ib.ballotId = b.ballotId";
        PreparedStatement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        List<BallotItem> ballotItems = new ArrayList<BallotItem>();
        
      	query.append( selectIssueBallotSql );
        
        if( ballot != null ) {
            if( ballot.getId() >= 0 ) 
                query.append( " and b.districtId = " + ballot.getId() );
            else if( ballot.getOpenDate() != null ) 
                query.append( " and b.openDate = '" + ballot.getOpenDate() + "'" );
            else if(ballot.getCloseDate() !=null )
              	query.append( " and b.closeDate = '" + ballot.getCloseDate() + "'");
            
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent BallotItem objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                
                long   id;
                int   voteCount;
              	BallotItem nextBallotItem = null;
              
                ResultSet rs = stmt.getResultSet();
              
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    voteCount = rs.getInt( 2 );	
                  
                  	nextBallotItem = objectLayer.createBallotItem();
                    nextBallotItem.setId( id );
                  	nextBallotItem.setVoteCount( voteCount );
                  	
                  	nextBallotItem.setElectoralDistrict( restore(ballot).get(0) );

                    ballotItems.add( nextBallotItem );

                }
                
                return ballotItems;
            }
      if(ballotItem instanceof Election){	
      String       selectElectionBallotSql = "select i.ballotItemId, i.voteCount from ballotItem as i, ballot as b, electionBallot eb "
      								   + "where eb.electionId = i.ballotItemId and eb.ballotId = b.ballotId";
        PreparedStatement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        List<BallotItem> ballotItems = new ArrayList<BallotItem>();
        
      	query.append( selectElectionBallotSql );
        
        if( ballot != null ) {
            if( ballot.getId() >= 0 ) 
                query.append( " and b.districtId = " + ballot.getId() );
            else if( ballot.getOpenDate() != null ) 
                query.append( " and b.openDate = '" + ballot.getOpenDate() + "'" );
            else if(ballot.getCloseDate() !=null )
              	query.append( " and b.closeDate = '" + ballot.getCloseDate() + "'");
            
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent BallotItem objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                
                long   id;
                int   voteCount;
              	BallotItem nextBallotItem = null;
              
                ResultSet rs = stmt.getResultSet();
              
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    voteCount = rs.getInt( 2 );	
                  
                  	nextBallotItem = objectLayer.createBallotItem();
                    nextBallotItem.setId( id );
                  	nextBallotItem.setVoteCount( voteCount );
                  	
                  	nextBallotItem.setElectoralDistrict( restore(ballot).get(0) );

                    ballotItems.add( nextBallotItem );

                }
                
                return ballotItems;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.restoreBallotIncludesBallotItem: Could not restore persistent district object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "BallotManager.restoreBallotIncludesBallotItem: Could not restore persistent district objects" );
      }
    }
    //returns electoral district
    public ElectoralDistrict restoreElectoralDistrictHasBallotBallot( Ballot ballot ) throws EVException{
    	String       selectBallotSql = "select e.districtId, e.districtName from electoralDistrict as e, ballot as b, ballotDistrict bd"
          										+"where bd.districtId = e.districtId and bd.ballotId";              
        PreparedStatement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectDistrictSql );
        
        if( ballot != null ) {
            if( ballot.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and b.id = " + ballot.getId() );
            else if( ballot.getOpenDate() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and b.openDate = '" + ballot.getOpenDate() + "'" );
            else {
                if( ballot.getCloseDate() != null ) {
                    condition.append( " and b.established = '" + ballot.getCloseDate() + "'" );
                }

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent ElectoralDistrict object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                
                long   id;
                String name;
                ElectoralDistrict electoralDistrict = null;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    name = rs.getString( 2 );

                    electoralDistrict = objectLayer.createElectoralDistrict(name);
                    electoralDistrict.setId( id );
                }
                
                return electoralDistrict;
            }
            else
                return null;
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.ElectoralDistrictHasBallotBallot: Could not restore persistent elec object; Root cause: " + e );
        }
    }

    public void delete(Ballot ballot){
    	//TODO
    }
    
    public void deleteBallotIncludesBallotItem( Ballot ballot, BallotItem ballotItem ) throws EVException{
    	if(ballotItem instanceOf IssueBallot){
       String         deleteIssueBallotSql = "delete t1 from IssueBallot as t1 "
				   + "where ballotId = ? and issueId = ?";              
    	PreparedStatement    stmt = null;
    	int                  inscnt;
// form the query based on the given Person object instance
    	if( !ballot.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
    		return;
    	if (!ballotItem.isPersistent())
    		return;
    	try {
    		stmt = (PreparedStatement) conn.prepareStatement( deleteIssueBallotSql );
    		stmt.setLong( 1, ballotItem.getId() );
    		stmt.setLong( 2, ballot.getId());
    		inscnt = stmt.executeUpdate();
    		if( inscnt == 0 ) {
    			throw new EVException( "BallotManager.delete: failed to delete this IssueBallot" );
    		}
    	}
    	catch( SQLException e ) {
    		throw new EVException( "BallotManager.delete: failed to delete this IssueBallot: " + e.getMessage() );
    	} 
      }
      else if(ballotItem instanceOf ElectionBallot){
       String         deleteElectionBallotSql = "delete t1 from ElectionBallot as t1 "
				   + "where electionId = ? and issueId = ?";              
    	PreparedStatement    stmt = null;
    	int                  inscnt;
// form the query based on the given Person object instance
    	if( !ballot.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
    		return;
    	if (!ballotItem.isPersistent())
    		return;
    	try {
    		stmt = (PreparedStatement) conn.prepareStatement( deleteElectionBallotSql );
    		stmt.setLong( 1, ballotItem.getId() );
    		stmt.setLong( 2, ballot.getId());
    		inscnt = stmt.executeUpdate();
    		if( inscnt == 0 ) {
    			throw new EVException( "BallotManager.delete: failed to delete this Electionallot" );
    		}
    	}
    	catch( SQLException e ) {
    		throw new EVException( "BallotManager.delete: failed to delete this ElectionBallot: " + e.getMessage() );
    	} 
      }
    }
    }

}
