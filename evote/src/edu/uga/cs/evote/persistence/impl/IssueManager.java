package edu.uga.cs.evote.persistence.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;


import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;

public class IssueManager {
	
	private ObjectLayer objectLayer = null;
	private Connection conn = null;
	
	public IssueManager(Connection conn, ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
		this.conn = conn;
	}
	
	public void store(Issue issue) throws EVException{
        String               insertIssueSql = "insert into Issue (question, yesCount) values ( ?, ?)";              
        String               updateIssueSql = "update issue set question = ?, yesCount = ? where issueId = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 issueId;
        
        try {
            
            if( !issue.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertIssueSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateIssueSql );
            
            if( issue.getQuestion()!= null )
                stmt.setString( 1, issue.getQuestion() );
            else 
                throw new EVException( "IssueManager.save: can't save a Issue: question undefined" );
           
            	
            if (issue.getYesCount() > -1)
            	stmt.setInt(2, issue.getYesCount());
            else 
                throw new EVException( "IssueManager.save: can't save a Issue: question undefined" );
            
            if( issue.isPersistent() )
                stmt.setLong( 3, issue.getId() );

            inscnt = stmt.executeUpdate();
            if( !issue.isPersistent() ) {
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
                            issueId = r.getLong( 1 );
                            if( issueId > 0 )
                                issue.setId( issueId ); // set this party's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "IssueManager.save: failed to save an Issue" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "Issue.save: failed to save an Issue: " + e );
        }
    }
	
	public List<Issue> restore (Issue modelIssue) throws EVException{
		String       selectIssueSql = "select issueId, question, yesCount from Issue";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Issue> issues = new ArrayList<Issue>();
        
        // form the query based on the given Person object instance
        query.append( selectIssueSql );
        
        if( modelIssue != null ) {
            if( modelIssue.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                condition.append( " issueId = " + modelIssue.getId() );
            if( modelIssue.getQuestion() != null ){
            	if(condition.length() > 0)
            		condition.append(" and ");
                condition.append( " question = '" + modelIssue.getQuestion() + "'" );
            }
            
            if( modelIssue.getYesCount() > 0 ){
                if(condition.length() > 0)
                	condition.append(" and ");
            	condition.append( " yesCount = " + modelIssue.getYesCount() );
            }
            
            if(condition.length() > 0){
            	query.append(" where ");
            	query.append(condition);
            }
            	
        }         
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent of Issueobjects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   issueId;
                String question;
                int yesCount;
                
                
                while( rs.next() ) {

                    issueId = rs.getLong( 1 );
                    question = rs.getString( 2 );
                    yesCount = rs.getInt(3);
                    

                    Issue issue = objectLayer.createIssue();
                    issue.setId( issueId );
                    issue.setQuestion(question);
                    issue.setYesCount(yesCount);
                    issue.setVoteCount(yesCount);
                    issues.add( issue );

                }
                
                return issues;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "IssueManager.restore: Could not restore persistent issue object; Root cause: " + e );
        }

        // if we get to this point, it's an error
        throw new EVException( "IssueManager.restore: Could not restore persistent voter objects" );
    
}	
	 public void delete( Issue issue ) throws EVException
	    {
	        String               deleteIssueSql = "delete from Issue " 
	        									+ "where issueId = ?";              
	        PreparedStatement    stmt = null;
	        int                  inscnt;
	        
	        // form the query based on the given Person object instance
	        if( !issue.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
	            return;
	        
	        try {
	            stmt = (PreparedStatement) conn.prepareStatement( deleteIssueSql );
	            
	            stmt.setLong( 1, issue.getId() );
	            
	            inscnt = stmt.executeUpdate();
	            
	            if( inscnt == 0 ) {
	                throw new EVException( "IssueManager.delete: failed to delete this Issue" );
	            }
	        }
	        catch( SQLException e ) {
	            throw new EVException( "IssueManager.delete: failed to delete this issue: " + e.getMessage() );
	        }
	    }
}


