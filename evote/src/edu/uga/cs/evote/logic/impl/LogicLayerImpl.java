package edu.uga.cs.evote.logic.impl;


import java.sql.Connection;
import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
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
			String address, int age, String district) throws EVException {
		VoterRegCtrl ctrlVerifyVoter = new VoterRegCtrl(objectLayer);
		return ctrlVerifyVoter.addVoter(session, fname, lname, uname, pword, email, address, age, district);
	}

	@Override
	public long updateVoter(String fname, String lname, String userName, String password, String emailAddress, String address, int age) throws EVException{
		VoterUpdateCtrl ctrlVoterUpdateCtrl = new VoterUpdateCtrl(objectLayer);
		return ctrlVoterUpdateCtrl.updateVoter(fname, lname, userName, password, emailAddress, address, age);
	}
	@Override
	public List<Voter>findAllVoter() throws EVException{
		VoterUpdateCtrl ctrlFindAllVoter = new VoterUpdateCtrl(objectLayer);
		return ctrlFindAllVoter.findAllVoter();
	}
	@Override
	public long deleteVoter(String userName) throws EVException{
		VoterUpdateCtrl ctrlVoterUpdateCtrl = new VoterUpdateCtrl(objectLayer);
		return ctrlVoterUpdateCtrl.deleteVoter(userName);
	}
	
	@Override
	public ElectionsOfficer updateElectionsOfficer(String fname, String lname, String userName, String password, String emailAddress,
			String address) throws EVException{
		ElectionsOfficerCtrl ctrlEOUpdateCtrl = new ElectionsOfficerCtrl(objectLayer);
		return ctrlEOUpdateCtrl.updateElectionsOfficer(fname, lname, userName, password, emailAddress, address);
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
		PoliticalPartyCtrl ctrlUpdatePP = new PoliticalPartyCtrl(objectLayer);
		return ctrlUpdatePP.deletePP(partyName);
	}

	@Override
	public List<Candidate> findAllCandidate() throws EVException {
		CandidateCtrl ctrlFindAllCandidate = new CandidateCtrl(objectLayer);
		return ctrlFindAllCandidate.findAllCandidate();
	}

	@Override
	public long createCand(String candidateName, String partyName,
			String electionName, String isPartisan) throws EVException {
		CandidateCtrl createCandCtrl = new CandidateCtrl(objectLayer);
		return createCandCtrl.createCand(candidateName, partyName, electionName, isPartisan);
	}

	@Override
	public long updateCand(String candidateName, String newName)
			throws EVException {
		CandidateCtrl updateCandCtrl = new CandidateCtrl(objectLayer);
		return updateCandCtrl.updateCand(candidateName, newName);
	}

	@Override
	public long deleteCand(String candidateName) throws EVException {
		CandidateCtrl deleteCandCtrl = new CandidateCtrl(objectLayer);
		return deleteCandCtrl.deleteCand(candidateName);
	}

	@Override

	public List<Issue> findAllIssue() throws EVException {
		IssueCtrl ctrlFindAllIssue = new IssueCtrl(objectLayer);
		return ctrlFindAllIssue.findAllIssue();
	}

	@Override
	public long updateIssue(long issueId, String newQuestion, int newYesCount) throws EVException {
		IssueCtrl updateIssueCtrl = new IssueCtrl(objectLayer);
		return updateIssueCtrl.updateIssue(issueId, newQuestion, newYesCount);
	}

	@Override
	public long createIssue(String question) throws EVException{
		IssueCtrl createIssueCtrl = new IssueCtrl(objectLayer);
		return createIssueCtrl.createIssue(question);
		
	}
	
	@Override
	public long deleteIssue(long issueId) throws EVException{
		IssueCtrl deleteIssueCtrl = new IssueCtrl(objectLayer);
		return deleteIssueCtrl.deleteIssue(issueId);
		
	}
	
	@Override
	public long createElection(String electionOffice, String isPartisan) throws EVException {
		ElectionCtrl createElectionCtrl = new ElectionCtrl(objectLayer);
		return createElectionCtrl.createElection(electionOffice, isPartisan);
	}

	@Override
	public long updateElection(String electionOffice) throws EVException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long deleteElection(String electionOffice) throws EVException {
		ElectionCtrl deleteElectionCtrl = new ElectionCtrl(objectLayer);
		return deleteElectionCtrl.deleteElection(electionOffice);
	}

	@Override
	public List<Election> findAllElection() throws EVException {
		ElectionCtrl ctrlElectionCtrl = new ElectionCtrl(objectLayer);
		return ctrlElectionCtrl.findAllElection();
	}

	@Override
	public long createBallot(Date openDate, Date closeDate, String district) throws EVException {
		BallotCtrl createBallotCtrl = new BallotCtrl(objectLayer);
		return createBallotCtrl.createBallot(openDate, closeDate, district);
	}

	@Override
	public List<Ballot> findAllBallot() throws EVException {
		BallotCtrl ctrlBallotCtrl = new BallotCtrl(objectLayer);
		return ctrlBallotCtrl.findAllBallot();
	}

	@Override
	public long deleteBallot(String theId) throws EVException {
		BallotCtrl deleteBallotCtrl = new BallotCtrl(objectLayer);
		return deleteBallotCtrl.deleteBallot(theId);
	}

	@Override
	public long updateBallot(Date openDate, Date closeDate, String id)
			throws EVException {
		BallotCtrl updateBallotCtrl = new BallotCtrl(objectLayer);
		return updateBallotCtrl.updateBallot(openDate, closeDate, id);
	}

	@Override
	public void addIssue(String id, String[] theIssues) throws EVException {
		BallotCtrl addIssueCtrl = new BallotCtrl(objectLayer);
		addIssueCtrl.addIssue(id, theIssues);
	}

	@Override
	public void addElection(String id, String[] theElections) throws EVException
	{
		BallotCtrl addElectionCtrl = new BallotCtrl(objectLayer);
		addElectionCtrl.addElection(id, theElections);
		
	}

	@Override
	public Ballot findBallot(long id) throws EVException {
		BallotCtrl addElectionCtrl = new BallotCtrl(objectLayer);
		return addElectionCtrl.findBallot(id);
	}

	@Override
	public List<BallotItem> findBallotItems(Ballot ballot) throws EVException {
		return objectLayer.getPersistence().restoreBallotIncludesBallotItem(ballot);
	}

	@Override
	public PoliticalParty getPoliticalPartyFromCandidate(Candidate candidate) throws EVException {
		return objectLayer.getPersistence().restoreCandidateIsMemberOfPoliticalParty(candidate);
	}




	

}
