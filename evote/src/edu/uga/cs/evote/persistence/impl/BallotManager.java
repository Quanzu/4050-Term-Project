package edu.uga.cs.evote.persistence.impl;

import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
	
	public void store(Ballot ballot)
		throws EVException
	{
		String               insertBallotSql = "insert into Ballot ( openDate , closeDate ) values ( ?, ?)";              
        String               updateBallotSql = "update Ballot set  openDate = ?, closeDate = ? where ballotId = ?";
        PreparedStatement    stmt;
        int                  inscnt;
        long                 ballotId;
        
        try {
            
            if( !ballot.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertBallotSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateBallotSql );
             
            if( ballot.getOpenDate() != null ){
                java.util.Date jDate = ballot.getOpenDate();
                java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                stmt.setDate( 1,  sDate );
            }
            else 
                throw new EVException( "BallotManager.save: can't save a Ballot: name undefined" );

            if( ballot.getCloseDate() != null){
                java.util.Date jDate = ballot.getCloseDate();
                java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                stmt.setDate( 2,  sDate );
            }
            else 
            	throw new EVException( "BallotManager.save: can't save a Ballot: name undefined" );
            	
            if( ballot.isPersistent() )
                stmt.setLong( 3, ballot.getId() );

            inscnt = stmt.executeUpdate();
            if( !ballot.isPersistent() ) {
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
                            ballotId = r.getLong( 1 );
                            if( ballotId > 0 )
                                ballot.setId( ballotId ); // set this person's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "BallotManager.save: failed to save a ballot" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "BallotManager.save: failed to save a ballot: " + e );
        }	
	}
	
    public void storeBallotIncludesBallotItem( Ballot ballot, BallotItem ballotItem ) throws EVException{
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
    
    public List<Ballot> restore(Ballot modelBallot) throws EVException{
    	String       selectBallotSql = "select ballotId, openDate, closeDate from Ballot";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Ballot> ballots = new ArrayList<Ballot>();
        
        condition.setLength(0);
        
      	query.append( selectBallotSql );
        
        if( modelBallot != null ) {
            if( modelBallot.getId() >= 0 ) 
                query.append( " where ballotId = " + modelBallot.getId() );
            else{
            	if( modelBallot.getOpenDate() != null )        
            		condition.append( " openDate = '" + modelBallot.getOpenDate() + "'" );
            	if( modelBallot.getCloseDate() != null ){
            		if(condition.length() > 0)
            			condition.append(" and ");
            		condition.append( " closeDate = '" + modelBallot.getCloseDate() + "'" );
            	}
            	if(condition.length() > 0){
            		query.append(" where ");
            		query.append(condition);
            	}
            		
            }
        }
        
        try {
            stmt = conn.createStatement();

            // retrieve the persistent Person objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long    id;
                Date	openDate;
                Date	closeDate;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    openDate = rs.getDate( 2 );
                    closeDate = rs.getDate( 3 );
                    Ballot ballot = objectLayer.createBallot( openDate,closeDate, null );
                    ballot.setId( id );
                    ballots.add( ballot );
                }
                return ballots;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.restore: Could not restore persistent ballot object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent district objects" );
    	
    }
    
    public Ballot restoreBallotIncludesBallotItem( BallotItem ballotItem ) throws EVException{  	
       if(ballotItem instanceof Issue){
	      	String       selectIssueBallotSql = "select b.ballotId, b.openDate, b.closeDate from Ballot as b, Issue i, issueBallot ib "
	      								      + "where b.ballotId = ib.ballotId and i.issueId = ib.issueId";              
	        Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	
	        // form the query based on the given Ballot object instance
	        query.append( selectIssueBallotSql );
	        //Should it be like this ? or change to issueBallot instead of ballotItem
	        if( ballotItem != null ) {
	            if( ballotItem.getId() >= 0 ) // id is unique, so it is sufficient to get a person
	                query.append( " and i.issueId = " + ballotItem.getId() );
	        }
	        
	        try {
	            stmt = conn.createStatement();
	            // retrieve the persistent Ballot object
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
	
	                    ballot = objectLayer.createBallot(openDate,closeDate, null);
	                    ballot.setId( id );
	                }
	                
	                return ballot;
	            }
	        }
	        catch( Exception e ) {      // just in case...
	            throw new EVException( "BallotManager.restoreBallotIncludesBallotItem: Could not restore persistent elec object; Root cause: " + e );
	        }
       }
       if(ballotItem instanceof Election){
	    	String       selectElectionBallotSql = "select b.ballotId, b.openDate, b.closeDate from Ballot as b, Election e, electionBallot eb "
						   + "where b.ballotId = eb.ballotId and e.electionId = eb.electionId";                
	        Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	        
	        // form the query based on the given Ballot object instance
	        query.append( selectElectionBallotSql );
	        
	        if( ballotItem != null ) {
	            if( ballotItem.getId() >= 0 ) // id is unique, so it is sufficient to get a person
	                query.append( " and e.electionId = " + ballotItem.getId() );
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
	
	                    ballot = objectLayer.createBallot(openDate,closeDate, null);
	                    ballot.setId( id );
	                }
	                
	                return ballot;
	            }
	        }
	        catch( Exception e ) {      // just in case...
	            throw new EVException( "BallotManager.restoreBallotIncludesBallotItem: Could not restore persistent elec object; Root cause: " + e );
	        }
       }
       return null;
    }

    public List<BallotItem> restoreBallotIncludesBallotItem( Ballot ballot ) throws EVException{
    	String       selectIssueBallotSql = "select i.issueId, i.question, i.yesCount from Issue as i, Ballot b, issueBallot ib "
					   + "where i.issueId = ib.issueId and b.ballotId = ib.ballotId";
    	String		 selectElectionBallotSql = "select e.electionId, e.office, e.isPartisan, e.alternateAllowed, e.voteCount from Election as e, Ballot b, electionBallot eb "
				   + "where e.electionId = eb.electionId and b.ballotId = eb.ballotId";
        Statement    stmt = null;
        Statement    stmt2 = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer query2 = new StringBuffer( 100 );
        List<BallotItem> ballotItems = new ArrayList<BallotItem>();
        
      	query.append( selectIssueBallotSql );
        query2.append( selectElectionBallotSql );
        
        if( ballot != null ) {
            if( ballot.getId() >= 0 ){
                query.append( " and b.ballotId = " + ballot.getId() );
                query2.append( " and b.ballotId = " + ballot.getId() );

            }
            else if( ballot.getOpenDate() != null ){
                query.append( " and b.openDate = '" + ballot.getOpenDate() + "'" );
                query2.append( " and b.openDate = '" + ballot.getOpenDate() + "'" );
            }
            else if(ballot.getCloseDate() !=null ){
              	query.append( " and b.closeDate = '" + ballot.getCloseDate() + "'");
              	query2.append( " and b.closeDate = '" + ballot.getCloseDate() + "'");
            }           
        }

        try {
            stmt = conn.createStatement();
            // retrieve the persistent BallotItem objects
            if( stmt.execute( query.toString() ) ) { // statement returned a result
               
            	long   issueId;
                String question;
                int	   yesCount;
              	Issue nextBallotItem = null;
              
                ResultSet rs = stmt.getResultSet();
              
                while( rs.next() ) {

                    issueId = rs.getLong( 1 );
                    question = rs.getString( 2 );
                    yesCount = rs.getInt( 3 );
                  
                  	nextBallotItem = objectLayer.createIssue();
                    nextBallotItem.setId( issueId );
                    nextBallotItem.setQuestion( question );
                  	nextBallotItem.setYesCount( yesCount );

                    ballotItems.add( nextBallotItem );
                }                    
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.restoreBallotIncludesBallotItem: Could not restore persistent elec object; Root cause: " + e );
        }
        try {
            stmt2 = conn.createStatement();

            // retrieve the persistent BallotItem objects
            if( stmt2.execute( query2.toString() ) ) { // statement returned a result
                
                long   electionId;
                String office;
                int	   isPartisan;
                int    alternateAllowed;
                int    voteCount;
                boolean   isPartisanb;
                boolean	  alternateAllowedb;
              	Election nextBallotItem = null;
              
                ResultSet rs = stmt2.getResultSet();
              
                while( rs.next() ) {

                    electionId = rs.getLong( 1 );
                    office = rs.getString( 2 );
                    isPartisan = rs.getInt( 3 );
                    if(isPartisan == 1)
                    	isPartisanb = true;
                    else
                    	isPartisanb = false;
                    alternateAllowed = rs.getInt( 4 );
                    if(alternateAllowed == 1)
                    	alternateAllowedb = true;
                    else
                    	alternateAllowedb = false;
                    voteCount = rs.getInt( 5 );
                    
                  	nextBallotItem = objectLayer.createElection();
                    nextBallotItem.setId( electionId );
                    nextBallotItem.setOffice( office );
                  	nextBallotItem.setIsPartisan( isPartisanb );
                  	nextBallotItem.setAlternateAllowed( alternateAllowedb );
                  	nextBallotItem.setVoteCount( voteCount );

                    ballotItems.add( nextBallotItem );
                }                
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.restoreBallotIncludesBallotItem: Could not restore persistent elec object; Root cause: " + e );
        }
        return ballotItems;
    }
  
    //returns electoral district
    public ElectoralDistrict restoreElectoralDistrictHasBallotBallot( Ballot ballot ) throws EVException{
    	String       selectDistrictSql = "select e.districtId, e.districtName from electoralDistrict as e, ballot as b, ballotDistrict bd "
          							   + "where bd.districtId = e.districtId and bd.ballotId = b.ballotId";              
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectDistrictSql );
        
        if( ballot != null ) {
            if( ballot.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and b.ballotId = " + ballot.getId() );
            else if( ballot.getOpenDate() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and b.openDate = '" + ballot.getOpenDate() + "'" );
            else if( ballot.getCloseDate() != null )
                query.append( " and b.closeDate = '" + ballot.getCloseDate() + "'" );          
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
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "BallotManager.ElectoralDistrictHasBallotBallot: Could not restore persistent elec object; Root cause: " + e );
        }
        return null;
    }
	
    
    public void delete(Ballot ballot) throws EVException {
	    String deleteBallotSql = "delete t1 from ballot as t1 "
					   		   + "where t1.ballotId = ?";         
	    PreparedStatement    stmt = null;
	    int inscnt;
	
		// form the query based on the given Person object instance
		if( !ballot.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
			return;
	
		try {
			stmt = (PreparedStatement) conn.prepareStatement( deleteBallotSql );
		
			stmt.setLong( 1, ballot.getId() );
		
			inscnt = stmt.executeUpdate();
		
			if( inscnt == 0 ) {
				throw new EVException( "BallotManager.delete: failed to delete this ballot" );
			}
		}
		catch( SQLException e ) {
			throw new EVException( "BallotManager.delete: failed to delete this ballot: " + e.getMessage() );
		}
	}

    
    public void deleteBallotIncludesBallotItem( Ballot ballot, BallotItem ballotItem ) throws EVException{
    	if(ballotItem instanceof Issue){
    		String         deleteIssueBallotSql = "delete t1 from IssueBallot as t1 "
    											+ "where ballotId = ? and issueId = ?";              
    		PreparedStatement    stmt = null;
    		int                  inscnt;
    		//form the query based on the given object instance
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
      else if(ballotItem instanceof Election){
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
