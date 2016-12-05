package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.object.ObjectLayer;

public class ElectionCtrl {

private ObjectLayer objectLayer = null;
	
	public ElectionCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long createElection(String electionOffice, String isPartisan, String[] candidates)
			throws EVException
	{
		Election election = null;
        Election modelElection = null;
        List<Election> elections = null;
        
        boolean temp;
        // check if the name already exists
        modelElection = objectLayer.createElection();
        modelElection.setOffice(electionOffice);
        if (isPartisan.equalsIgnoreCase("false"))
       	 temp = false;
        else
        	temp = true;
        
        elections = objectLayer.findElection( modelElection );
        if( elections.size() > 0 )
            election = elections.get( 0 );
        
        // check if the issue actually exists, and if so, throw an exception
        if( election != null )
            throw new EVException( "An election with the same office name already exists" );
        
        else
        { 	
        	election = objectLayer.createElection(electionOffice, temp);  	
            objectLayer.storeElection(election);
            
            for(int i=0; i<candidates.length; i++){
            	String tempName = candidates[0];
            	Candidate modelCandidate = objectLayer.createCandidate();
            	modelCandidate.setName(tempName);
            	Candidate tempCand = objectLayer.findCandidate(modelCandidate).get(0);
            	objectLayer.getPersistence().storeCandidateIsCandidateInElection(tempCand, election);
            }
        }
             
		
		return election.getId();
    }

	
	
	public long updateElection(String electionName, String newElectionName, String[] removeCandidates, String[] addCandidates) throws EVException
	{
		//changes the name
		Election election = null;
        Election modelElection = null;
        List<Election> elections = null;
		
        modelElection = objectLayer.createElection();
        modelElection.setOffice(electionName);
        
    	elections = objectLayer.findElection( modelElection );
    	if( elections.size() > 0 )
    		election = elections.get( 0 );
    	
        if (newElectionName != null)
        {
         
        // check if the issue actually exists, and if so, throw an exception
        	if( election != null )
        		election.setOffice(newElectionName);
        	objectLayer.storeElection( election );
        }
        
        
        //removes Candidates
        Candidate candidate = null;
        Candidate modelCandidate = null;
        List<Candidate> candidates = null;
        if (election != null)
        {
        	
        	for (int i = 0; i < removeCandidates.length; i++)
        	{
        		 modelCandidate = objectLayer.createCandidate();
                 modelCandidate.setName(removeCandidates[i]);
                 candidates = objectLayer.findCandidate( modelCandidate );
                 if( candidates.size() > 0 )
                     candidate = candidates.get( 0 );
                 if (candidate != null)
                 {
                	 objectLayer.getPersistence().deleteCandidateIsCandidateInElection(candidate, election);
                 }
        	}
            //adds Candidates
        	
        	for (int i = 0; i < removeCandidates.length; i++)
        	{
        		 modelCandidate = objectLayer.createCandidate();
                 modelCandidate.setName(removeCandidates[i]);
                 candidates = objectLayer.findCandidate( modelCandidate );
                 if( candidates.size() > 0 )
                     candidate = candidates.get( 0 );
                 if (candidate != null)
                 {
                	 objectLayer.getPersistence().storeCandidateIsCandidateInElection(candidate, election);
                 }
        	}
        }
        
        
        
		return election.getId();
	}
	public List<Election> findAllElection() throws EVException{
		List<Election> elections = null;
		
		elections = objectLayer.findElection(null);
		return elections;
	}
	
	public long deleteElection(String electionOffice) throws EVException
	{
		Election election = null;
		Election modelElection = null;
        List<Election> elections = null;

       
        // check if the name already exists
        modelElection = objectLayer.createElection();
        //gets the candidate in candidate
        modelElection.setOffice(electionOffice);
        
        elections = objectLayer.findElection(modelElection);
        if( elections.size() > 0 )
            election = elections.get(0);
        
        // check if the candidate actually exists, and if so, throw an exception
        if( election != null )
        {
        	objectLayer.deleteElection( election );
        }
		return election.getId();
	}
}
