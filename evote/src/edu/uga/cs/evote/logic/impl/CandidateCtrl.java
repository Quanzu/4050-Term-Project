package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class CandidateCtrl {

private ObjectLayer objectLayer = null;
	
	public CandidateCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long createCand(String candidateName, String partyName, String electionName, String isPartisan)
			throws EVException
	{
		Candidate candidate = null;
        Candidate modelCandidate = null;
        List<Candidate> candidates = null;
        PoliticalParty party = null;
        PoliticalParty modelParty = null;
        List<PoliticalParty> parties = null;
        
        Election election = null;
        Election modelElection = null;
        List<Election> elections = null;

        // check if the name already exists
        modelCandidate = objectLayer.createCandidate();
        modelCandidate.setName(candidateName);
        candidates = objectLayer.findCandidate( modelCandidate );
        if( candidates.size() > 0 )
            candidate = candidates.get( 0 );
        
        // check if the candidate actually exists, and if so, throw an exception
        if( candidate != null )
            throw new EVException( "A candidate with this name already exists" );
        
        else
        {
        	//check to see election exists
        	 modelElection = objectLayer.createElection();
             modelElection.setOffice(electionName);
             if (isPartisan.equalsIgnoreCase("false"))
             {
            	 boolean temp = false;
            	 modelElection.setIsPartisan(temp);
             }
             else
             {
            	modelElection.setIsPartisan(true);
            	modelParty = objectLayer.createPoliticalParty();
                modelParty.setName(partyName);
                //checking to make sure political party actually exists
                parties = objectLayer.findPoliticalParty(modelParty);
                if( parties.size() > 0 )
                    party = parties.get(0);
                if( party == null )
                    throw new EVException( "A party with this name does not exist" );
             }
             elections = objectLayer.findElection( modelElection );
             if( elections.size() > 0 )
                 election = elections.get( 0 );
             if( election == null )
                 throw new EVException( "An election with this name does not exist" );
        	//then check to see political party exists.
        
        //THIS IS THE ISSUE. MUST PASS IN POLITICAL PARTY AND ELECTION
             if (isPartisan.equalsIgnoreCase("false"))
             {
            	 
             	candidate = objectLayer.createCandidate(candidateName, null, election);
             	objectLayer.storeCandidate( candidate );
             	objectLayer.getPersistence().storeCandidateIsCandidateInElection(candidate, election);

             }
             else
             {
            	 
            	 candidate = objectLayer.createCandidate(candidateName, party, election);
            	 objectLayer.storeCandidate( candidate );
            	 objectLayer.getPersistence().storeCandidateIsMemberOfPoliticalParty(candidate, party);
            	 objectLayer.getPersistence().storeCandidateIsCandidateInElection(candidate, election);
             }
             
		
		return candidate.getId();
        }
	}
	
	
	public long updateCand(String candidateName, String newName)
			throws EVException
	{
		
		Candidate candidate = null;
        Candidate modelCandidate = null;
        List<Candidate> candidates = null;

        // check if the name already exists
        modelCandidate = objectLayer.createCandidate();
        modelCandidate.setName(candidateName);
        candidates = objectLayer.findCandidate( modelCandidate );
        if( candidates.size() > 0 )
            candidate = candidates.get( 0 );
        
        // check if the person actually exists, and if so, throw an exception
        if( candidate != null )
        {
        	candidate.setName(newName);
        }
            //throw new EVException( "A district with this name already exists" );
        
        //district = objectLayer.createElectoralDistrict(districtName);
        objectLayer.storeCandidate( candidate );
		
		return candidate.getId();
	}
	
	public List<Candidate> findAllCandidate() throws EVException{
		List<Candidate> candidates = null;
		
		candidates = objectLayer.findCandidate(null);
		return candidates;
	}
	
	public long deleteCand(String candidateName) throws EVException
	{
		Candidate candidate = null;
		Candidate modelCandidate = null;
        List<Candidate> candidates = null;

       
        // check if the name already exists
        modelCandidate = objectLayer.createCandidate();
        //gets the candidate in candidate
        modelCandidate.setName(candidateName);
        
        candidates = objectLayer.findCandidate(modelCandidate);
        if( candidates.size() > 0 )
            candidate = candidates.get(0);
        
        // check if the candidate actually exists, and if so, throw an exception
        if( candidate != null )
        {
        	objectLayer.deleteCandidate( candidate );
        }
		return candidate.getId();
	}
}
