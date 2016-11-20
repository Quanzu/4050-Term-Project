package edu.uga.cs.evote.logic.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.impl.ElectionsOfficerImpl;
import edu.uga.cs.evote.entity.impl.VoterImpl;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.DbUtils;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.evote.session.Session;

public class VoterRegCtrl {

	private ObjectLayer objectLayer = null;
    
    public VoterRegCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
	
	
    public long addVoter( Session session, String fname, String lname, String uname, String pword, 
    		String email, String address, int age ) throws EVException{
    	
    		String ssid = null;
	        Voter voter = null;
	        Voter modelVoter = null;
	        List<Voter> voters = null;

	        // check if the uname already exists
	        modelVoter = objectLayer.createVoter();
	        modelVoter.setUserName(uname);
	        voters = objectLayer.findVoter( modelVoter );
	        if( voters.size() > 0 )
	            voter = voters.get( 0 );
	        
	        // check if the person actually exists, and if so, throw an exception
	        if( voter != null )
	            throw new EVException( "A person with this user name already exists" );
	        
	        voter = objectLayer.createVoter( fname, lname, uname, pword, email, address, age);
	        objectLayer.storeVoter( voter );

	        return voter.getId();
	    }
		
		
		
		
		
		
		
		
		
		
/*		try{
			conn = DbUtils.connect();
		}
		catch(Exception seq){
			System.err.println("Fail to connect to database");
		}
		
		ObjectLayer test = new ObjectLayerImpl();
		Voter asdf = test.createVoter(voter.getFirstName(), voter.getLastName(), voter.getUserName(), 
				voter.getPassword(), voter.getEmailAddress(), voter.getAddress(), voter.getAge());
		test.storeVoter(asdf);
	}
	*/
}
