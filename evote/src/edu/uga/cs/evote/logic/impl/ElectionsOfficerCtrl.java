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

public class ElectionsOfficerCtrl {

private ObjectLayer objectLayer = null;
	
	public ElectionsOfficerCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public ElectionsOfficer updateElectionsOfficer(String fName, String lName, String userName, String password, String emailAddress,
			String address)
			throws EVException
	{
		
		ElectionsOfficer electionsOfficer = null;
        ElectionsOfficer modelElectionsOfficer = null;
        List<ElectionsOfficer> electionsOfficers = null;

        // check if the name already exists
        modelElectionsOfficer = objectLayer.createElectionsOfficer();
       	modelElectionsOfficer.setUserName(userName);

		
        
        electionsOfficers = objectLayer.findElectionsOfficer( modelElectionsOfficer );
        if( electionsOfficers.size() > 0 )
            electionsOfficer = electionsOfficers.get( 0 );
        else
        	return null; //elections officer does not exist
        
        electionsOfficer.setFirstName(fName);
        electionsOfficer.setLastName(lName);
        
        String passwordLength = "";
        for(int n=0; n<electionsOfficer.getPassword().length(); n++){
			passwordLength = passwordLength + "*";
        }
        if(!password.equals(passwordLength))
        	electionsOfficer.setPassword(password);
        electionsOfficer.setEmailAddress(emailAddress);
        electionsOfficer.setAddress(address);
                
        objectLayer.storeElectionsOfficer( electionsOfficer );
		
		return electionsOfficer;
	}
}

