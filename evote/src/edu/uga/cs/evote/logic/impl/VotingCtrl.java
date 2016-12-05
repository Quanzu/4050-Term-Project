 package edu.uga.cs.evote.logic.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class VotingCtrl {

	private ObjectLayer objectLayer = null;
    
    public VotingCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
	
	
    public long recordIssue( long issueId, String newQuestion, int newYesCount, String vote ) throws EVException{
   
    	
    	Issue issue = null;
		Issue modelIssue = null;
        List<Issue> issues = null;

        // check if the name already exists
        modelIssue = objectLayer.createIssue();
        modelIssue.setId(issueId);
        issues = objectLayer.findIssue( modelIssue );
        if( issues.size() > 0 ){
            issue = issues.get( 0 );
        }
        
        if (issue != null)
        {
        	if(vote.equalsIgnoreCase("yes")){
        		issue.setYesCount(newYesCount+1);
        		objectLayer.storeIssue( issue );
        	}
        	//objectLayer.getPersistence().store
        
		return issue.getId();
        }
    	else
    		return issueId;
}
public long recordElection(String candidateName)
		throws EVException
{
	
	Candidate candidate = null;
    Candidate modelCandidate = null;
    List<Candidate> candidates = null;
    int voteCount = 0;
    // check if the name already exists
    modelCandidate = objectLayer.createCandidate();
    modelCandidate.setName(candidateName);
    candidates = objectLayer.findCandidate( modelCandidate );
    if( candidates.size() > 0 )
        candidate = candidates.get( 0 );
    
    // check if the person actually exists, and if so, throw an exception
    if( candidate != null )
    {
    	voteCount = candidate.getVoteCount();
    	voteCount = voteCount + 1;
    	candidate.setVoteCount(voteCount);
    }
        
    objectLayer.storeCandidate( candidate );
	
	return candidate.getId();
	}

	
	public VoteRecord createVoteRecord(long ballotId, String voterUserName, Date date) throws EVException
	{
		Ballot ballot = null;
		Ballot modelBallot = null;
       		List<Ballot> ballots = null;
        
		Voter voter = null;
		Voter modelVoter = null;
		List<Voter> voters = null;
		
		VoteRecord voteRecord = null;
		
		
		
        
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        modelBallot.setId(ballotId);
        
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
            ballot = ballots.get( 0 );
		
	modelVoter = objectLayer.createVoter();
        modelVoter.setUserName(voterUserName);
        
        voters = objectLayer.findVoter( modelVoter );
        if( voters.size() > 0 )
            voter = voters.get( 0 );
        
        // check if the issue actually exists, and if so, throw an exception
        if( ballot != null && voter != null)
            throw new EVException( "A voteRecord with the same name already exists" );
        
        else
        { 	
           voteRecord = objectLayer.createVoteRecord(ballot, voter, date);  	
           objectLayer.storeVoteRecord(voteRecord);
            
        }
             
		
		return voteRecord.getId();
		
	}



}





