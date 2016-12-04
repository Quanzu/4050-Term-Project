package edu.uga.cs.evote.logic;

import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.session.Session;

public interface LogicLayer {
	public String eoLogin(Session session, String userName, String password) throws EVException;
	public String voterLogin(Session session, String userName, String password) throws EVException;
	public void logout(String ssid) throws EVException;
	public String addVoter(Session session, String fname, String lname, String uname, String pword, 
		String email, String address, int age, String district ) throws EVException;
	
	public long updateVoter(String fname, String lname, String userName, String password, String emailAddress, String address, int age) throws EVException;
	public List<Voter>findAllVoter() throws EVException;
	public long deleteVoter(String userName) throws EVException;
	
	public ElectionsOfficer updateElectionsOfficer(String fname, String lname, String userName, String password, String emailAddress,
			String address) throws EVException;
	
	public List<Issue> findAllIssue() throws EVException;
	public long updateIssue(long issueId, String newQuestion, int newYesCount) throws EVException;
	public long deleteIssue(long issueId) throws EVException;
	public long createIssue(long issueId, String newQuestion, int newYesCount) throws EVException;
	
	public List<ElectoralDistrict> findAllElectoralDistrict() throws EVException;
	public long createED(String districtName) throws EVException;
	public long updateED(String districtName, String newName) throws EVException;
	public long deleteED(String districtName) throws EVException;

	public List<PoliticalParty> findAllPoliticalParty() throws EVException;
	public long createPP(String partyName) throws EVException;
	public long updatePP(String partyName, String newName) throws EVException;
	public long deletePP(String partyName) throws EVException;
	
	public List<Candidate> findAllCandidate() throws EVException;
	public long createCand(String candidateName, String partyName, String electionName, String isPartisan) throws EVException;
	public long updateCand(String candidateName, String newName) throws EVException;
	public long deleteCand(String candidateName) throws EVException;
	
	public List<Election> findAllElection() throws EVException;
	public long createElection(String electionOffice, String isPartisan) throws EVException;
	public long updateElection(String electionOffice) throws EVException;
	public long deleteElection(String electionOffice) throws EVException;
	
	public long createBallot(Date openDate, Date closeDate, String district) throws EVException;
	public List<Ballot> findAllBallot() throws EVException;
	public List<BallotItem> findBallotItems(Ballot ballot) throws EVException;
	public long deleteBallot(String theId) throws EVException;
	public long updateBallot(Date openDate, Date closeDate, String id) throws EVException;
	public Ballot findBallot(long id) throws EVException;
	
	public void addIssue(String id, String[] theIssues) throws EVException;
	public void addElection(String id, String[] theElections) throws EVException;
}
