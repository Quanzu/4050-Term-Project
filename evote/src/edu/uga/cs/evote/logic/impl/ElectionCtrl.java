package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class ElectionCtrl {

private ObjectLayer objectLayer = null;
	
	public ElectionCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long createElection(String electionOffice, String isPartisan)
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
        {
       	 temp = false;
       	 modelElection.setIsPartisan(temp);
        }
        else
        {
        temp = true;
       	modelElection.setIsPartisan(true);
        }
        elections = objectLayer.findElection( modelElection );
        if( elections.size() > 0 )
            election = elections.get( 0 );
        
        // check if the issue actually exists, and if so, throw an exception
        if( election != null )
            throw new EVException( "An Issue with the same Question already exists" );
        
        else
        {
        	
             	election = objectLayer.createElection(electionOffice, temp);
             	
             	objectLayer.storeElection(election);
        }
             
		
		return election.getId();
    }

	
	
	public long updateElection(String electionOffice)
			throws EVException
	{
		
		return 0;
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