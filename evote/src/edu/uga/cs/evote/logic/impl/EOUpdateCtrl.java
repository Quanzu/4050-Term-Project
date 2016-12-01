package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class EOUpdateCtrl {

private ObjectLayer objectLayer = null;
	
	public EOUpdateCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long updateElectionsOfficer(Session session, String fname, String lname, String userName, String password, String emailAddress,
			String address)
			throws EVException
	{
		
		ElectionsOfficer electionsOfficer = null;
        ElectionsOfficer modelElectionsOfficer = null;
        List<ElectionsOfficer> electionsOfficers = null;

        // check if the name already exists
        modelElectionsOfficer = objectLayer.createElectionsOfficer();
       	if(userName != null)
	modelElectionsOfficer.setUserName(userName);
		
	if(fname != null)
        modelElectionsOfficer.setFirstName(fname);
		
	if(lname != null)
        modelElectionsOfficer.setLastName(lname);
		
	if(password != null)
        modelElectionsOfficer.setPassword(password);
		
	if(emailAddress != null)
        modelElectionsOfficer.setEmailAddress(emailAddress);
		
	if(address != null)
        modelElectionsOfficer.setAddress(address);
        
        electionsOfficers = objectLayer.findElectionsOfficer( modelElectionsOfficer );
        if( electionsOfficers.size() > 0 )
            electionsOfficer = electionsOfficers.get( 0 );
        
        // check if the person actually exists, and if so, throw an exception
        if( electionsOfficer != null )
        {
		if(userName != null)
        	electionsOfficer.setUserName(userName);
        }
            
        
        //district = objectLayer.createElectoralDistrict(districtName);
        objectLayer.storeElectionsOfficer( electionsOfficer );
		
		return electionsOfficer.getId();
	}
	
	public List<ElectionsOfficer> findAllElectionsOfficer() throws EVException{
		List<ElectionsOfficer> electionsOfficers = null;
		
		electionsOfficers = objectLayer.findElectionsOfficer(null);
		return electionsOfficers;
	}
	
	public long deleteElectionsOfficer(String userName) throws EVException
	{
		ElectionsOfficer electionsOfficer = null;
		ElectionsOfficer modelElectionsOfficer = null;
        List<ElectionsOfficer> ElectionsOfficers = null;

        // check if the name already exists
        modelElectionsOfficer = objectLayer.createElectionsOfficer();
        modelElectionsOfficer.setUserName(userName);
        ElectionsOfficers = objectLayer.findElectionsOfficer(modelElectionsOfficer);
        if( ElectionsOfficers.size() > 0 )
        	electionsOfficer = ElectionsOfficers.get(0);
        
        // check if the party actually exists, and if so, throw an exception
        if( electionsOfficer != null )
        {
            objectLayer.deleteElectionsOfficer( electionsOfficer );
        }
        
		return electionsOfficer.getId();
	}
}

