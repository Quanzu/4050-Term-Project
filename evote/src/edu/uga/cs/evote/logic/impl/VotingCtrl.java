package edu.uga.cs.evote.logic.impl;

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
   
    	if(vote.equalsIgnoreCase("yes")){
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
        
        
        issue.setQuestion(newQuestion);
        issue.setYesCount(newYesCount+1);
        objectLayer.storeIssue( issue );
		
		return issue.getId();}
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
}}
