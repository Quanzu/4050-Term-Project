package edu.uga.cs.evote.logic.impl;


import java.sql.Connection;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.logic.LogicLayer;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

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
	public void logout(String ssid) throws EVException {
		SessionManager.logout(ssid);
	}

	@Override
	public String addVoter(Session session, String fname, String lname, String uname, String pword, String email,
			String address, int age) throws EVException {
		VoterRegCtrl ctrlVerifyVoter = new VoterRegCtrl(objectLayer);
		return ctrlVerifyVoter.addVoter(session, fname, lname, uname, pword, email, address, age);
	}

	@Override

	public long createED(String districtName) throws EVException{
		ElectoralDistrictCtrl ctrlCreateED = new ElectoralDistrictCtrl(objectLayer);
		return ctrlCreateED.createED(districtName);
	}

	@Override
	public List<ElectoralDistrict> findAllElectoralDistrict() throws EVException {
		ElectoralDistrictCtrl ctrlFindAllElectoralDistrict = new ElectoralDistrictCtrl(objectLayer);
		return ctrlFindAllElectoralDistrict.findAllElectoralDistrict();
	}
	

	@Override
	public long updateED(String districtName, String newName) throws EVException {
		ElectoralDistrictCtrl ctrlUpdateEDCtrl = new ElectoralDistrictCtrl(objectLayer);
		return ctrlUpdateEDCtrl.updateED(districtName, newName);
	}

	@Override
	public long deleteED(String districtName) throws EVException {
		ElectoralDistrictCtrl ctrlUpdateEDCtrl = new ElectoralDistrictCtrl(objectLayer);
		return ctrlUpdateEDCtrl.deleteED(districtName);
	}
	
	
	@Override

	public long createPP(String partyName) throws EVException {
		PoliticalPartyCtrl ctrlCreatePP = new PoliticalPartyCtrl(objectLayer);
		return ctrlCreatePP.createPP(partyName);
	}

	@Override
	public List<PoliticalParty> findAllPoliticalParty() throws EVException {
		PoliticalPartyCtrl ctrlFindAllPoliticalParty = new PoliticalPartyCtrl(objectLayer);
		return ctrlFindAllPoliticalParty.findAllPoliticalParty();
	}

	@Override
	public long updatePP(String partyName, String newName)
			throws EVException {
		PoliticalPartyCtrl ctrlUpdatePPCtrl = new PoliticalPartyCtrl(objectLayer);
		return ctrlUpdatePPCtrl.updatePP(partyName, newName);
	}

	@Override
	public long deletePP(String partyName) throws EVException {
		PoliticalPartyCtrl ctrlUpdatePPCtrl = new PoliticalPartyCtrl(objectLayer);
		return ctrlUpdatePPCtrl.deletePP(partyName);
	}



	

	

}
