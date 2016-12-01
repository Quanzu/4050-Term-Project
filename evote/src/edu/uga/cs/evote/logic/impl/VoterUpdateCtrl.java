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

public class VoterUpdateCtrl {

private ObjectLayer objectLayer = null;
	
	public VoterUpdateCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long updateVoter(Session session, String fname, String lname, String userName, String password, String emailAddress,
			String address, int age)
			throws EVException
	{
		
		Voter voter = null;
        Voter modelVoter = null;
        List<Voter> voters = null;

        // check if the name already exists
        modelVoter = objectLayer.createVoter();
        modelVoter.setFirstName(fname);
        modelVoter.setLastName(lname);
        modelVoter.setUserName(userName);
        modelVoter.setPassword(password);
        modelVoter.setEmailAddress(emailAddress);
        modelVoter.setAddress(address);
        modelVoter.setAge(age);
        voters = objectLayer.findVoter( modelVoter );
        if( voters.size() > 0 )
            voter = voters.get( 0 );
        
        // check if the person actually exists, and if so, throw an exception
        if( voter != null )
        {
        	voter.setUserName(userName);
        }
            //throw new EVException( "A district with this name already exists" );
        
        //district = objectLayer.createElectoralDistrict(districtName);
        objectLayer.storeVoter( voter );
		
		return voter.getId();
	}
	
	public List<Voter> findAllVoter() throws EVException{
		List<Voter> voters = null;
		
		voters = objectLayer.findVoter(null);
		return voters;
	}
	
	public long deleteVoter(String userName) throws EVException
	{
		Voter voter = null;
		Voter modelVoter = null;
        List<Voter> Voters = null;

        // check if the name already exists
        modelVoter = objectLayer.createVoter();
        modelVoter.setUserName(userName);
        Voters = objectLayer.findVoter(modelVoter);
        if( Voters.size() > 0 )
            voter = Voters.get(0);
        
        // check if the party actually exists, and if so, throw an exception
        if( voter != null )
        {
            objectLayer.deleteVoter( voter );
        }
        
		return voter.getId();
	}
}
