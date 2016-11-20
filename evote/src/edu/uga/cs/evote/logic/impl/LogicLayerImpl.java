package edu.uga.cs.evote.logic.impl;


import java.sql.Connection;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.logic.LogicLayer;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.evote.session.Session;

public class LogicLayerImpl implements LogicLayer{

	private ObjectLayer objectLayer = null;
	
	public LogicLayerImpl( Connection conn )
    {
        objectLayer = new ObjectLayerImpl();
        PersistenceLayer persistenceLayer = new PersistenceLayerImpl( conn, objectLayer );
        objectLayer.setPersistence( persistenceLayer );
        System.out.println( "LogicLayerImpl.LogicLayerImpl(conn): initialized" );
    }
    
    public LogicLayerImpl( ObjectLayer objectLayer )
    {
        this.objectLayer = objectLayer;
        System.out.println( "LogicLayerImpl.LogicLayerImpl(objectLayer): initialized" );
    }
    
	@Override
	public String eoLogin(Session session, String userName, String password) throws EVException {
		EOLoginCtrl ctrlVerifyOfficer = new EOLoginCtrl(objectLayer);
		return ctrlVerifyOfficer.login(session, userName, password);
	}

	@Override
	public String voterLogin(Session session, String userName, String password) throws EVException {
		VoterLoginCtrl ctrlVerifyVoter = new VoterLoginCtrl(objectLayer);
		return ctrlVerifyVoter.login(session, userName, password);
	}

	@Override
	public long addVoter(Session session, String fname, String lname, String uname, String pword, String email,
			String address, int age) throws EVException {
		VoterRegCtrl ctrlVerifyVoter = new VoterRegCtrl(objectLayer);
		return ctrlVerifyVoter.addVoter(session, fname, lname, uname, pword, email, address, age);
	}

}
