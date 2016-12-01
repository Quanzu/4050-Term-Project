package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.object.ObjectLayer;

public class IssueCtrl {

	private ObjectLayer objectLayer = null;
	
	public IssueCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long updateIssue(long issueId, String newQuestion, int newYesCount)
			throws EVException
	{
		Issue issue = null;
		Issue modelIssue = null;
        List<Issue> issues = null;

        // check if the name already exists
        modelIssue = objectLayer.createIssue();
        modelIssue.setId(issueId);
        issues = objectLayer.findIssue( modelIssue );
        if( issues.size() > 0 ){
        	System.out.print("calling\n");
            issue = issues.get( 0 );
        }
        
        
        issue.setQuestion(newQuestion);
        issue.setYesCount(newYesCount);
        objectLayer.storeIssue( issue );
		
		return issue.getId();
	}
	
	public List<Issue> findAllIssue() throws EVException{
		List<Issue> issues = null;	
		issues = objectLayer.findIssue(null);
		return issues;
	}
}
