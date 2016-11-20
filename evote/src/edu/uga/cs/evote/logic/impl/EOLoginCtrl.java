package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class EOLoginCtrl {
	
	private ObjectLayer objectLayer = null;
	
	public EOLoginCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public String login(Session session, String userName, String password)
		throws EVException
	{
		String ssid = null;
		
		ElectionsOfficer modelElectionsOfficer = objectLayer.createElectionsOfficer();
		modelElectionsOfficer.setUserName(userName);
		modelElectionsOfficer.setPassword(password);
		List<ElectionsOfficer> officers = objectLayer.findElectionsOfficer(modelElectionsOfficer);
		if(officers.size() > 0){
			ElectionsOfficer officer = officers.get(0);
			session.setUser(officer);
			ssid = SessionManager.storeSession(session);
		}else
			throw new EVException("SessionManager.login: Invalid UserName or Password");
		
		return ssid;
	}
		
}
