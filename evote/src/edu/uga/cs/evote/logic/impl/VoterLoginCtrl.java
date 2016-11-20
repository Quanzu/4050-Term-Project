package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class VoterLoginCtrl {

	private ObjectLayer objectLayer = null;
	
	public VoterLoginCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public String login(Session session, String userName, String password)
			throws EVException
	{
		String ssid = null;
		
		Voter modelVoter = objectLayer.createVoter();
		modelVoter.setUserName(userName);
		modelVoter.setPassword(password);
		List<Voter> voters = objectLayer.findVoter(modelVoter);
		if(voters.size() > 0){
			Voter voter = voters.get(0);
			session.setUser(voter);
			ssid = SessionManager.storeSession(session);
		}else
			throw new EVException("SessionManager.login: Invalid UserName or Password");
			
		return ssid;
	}
}
