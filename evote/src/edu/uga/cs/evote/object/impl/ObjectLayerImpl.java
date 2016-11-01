package edu.uga.cs.evote.object.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.entity.impl.CandidateImpl;
import edu.uga.cs.evote.entity.impl.ElectionsOfficerImpl;
import edu.uga.cs.evote.entity.impl.ElectoralDistrictImpl;
import edu.uga.cs.evote.entity.impl.PoliticalPartyImpl;
import edu.uga.cs.evote.entity.impl.VoterImpl;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.persistence.PersistenceLayer;

public class ObjectLayerImpl implements ObjectLayer {

	PersistenceLayer persistence = null;
    
    public ObjectLayerImpl()
    {
        this.persistence = null;
        System.out.println( "ObjectLayerImpl.ObjectLayerImpl(): initialized" );
    }
    
    public ObjectLayerImpl( PersistenceLayer persistence )
    {
        this.persistence = persistence;
        System.out.println( "ObjectLayerImpl.ObjectLayerImpl(persistence): initialized" );
    }
	
	@Override
	public ElectionsOfficer createElectionsOfficer(String firstName, String lastName, String userName, String password,
			String emailAddress, String address) throws EVException {
		ElectionsOfficerImpl officer = new ElectionsOfficerImpl( firstName, lastName, userName, password, emailAddress, address);
		return officer;
	}

	@Override
	public ElectionsOfficer createElectionsOfficer() {
		ElectionsOfficerImpl officer = new ElectionsOfficerImpl();
		return officer;
	}

	@Override
	public List<ElectionsOfficer> findElectionsOfficer(ElectionsOfficer modelElectionsOfficer) throws EVException {
		return persistence.restoreElectionsOfficer(modelElectionsOfficer);
	}

	@Override
	public void storeElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
		persistence.storeElectionsOfficer(electionsOfficer);
	}

	@Override
	public void deleteElectionsOfficer(ElectionsOfficer electionsOfficer) throws EVException {
		persistence.deleteElectionsOfficer(electionsOfficer);
	}

	@Override
	public Voter createVoter(String firstName, String lastName, String userName, String password, String emailAddress,
			String address, int age) throws EVException {
		VoterImpl voter = new VoterImpl(firstName, lastName, userName, password, emailAddress, address, age);
		return voter;
	}

	@Override
	public Voter createVoter() {
		VoterImpl voter = new VoterImpl();
		return voter;
	}

	@Override
	public List<Voter> findVoter(Voter modelVoter) throws EVException {
		return persistence.restoreVoter(modelVoter);
	}

	@Override
	public void storeVoter(Voter voter) throws EVException {
		persistence.storeVoter(voter);
	}

	@Override
	public void deleteVoter(Voter voter) throws EVException {
		persistence.deleteVoter(voter);
	}

	@Override
	public PoliticalParty createPoliticalParty(String name) throws EVException {
		PoliticalPartyImpl party = new PoliticalPartyImpl(name);
		return party;
	}

	@Override
	public PoliticalParty createPoliticalParty() {
		PoliticalPartyImpl party = new PoliticalPartyImpl();
		return party;
	}

	@Override
	public List<PoliticalParty> findPoliticalParty(PoliticalParty modelPoliticalParty) throws EVException {
		return persistence.restorePoliticalParty(modelPoliticalParty);
	}

	@Override
	public void storePoliticalParty(PoliticalParty politicalParty) throws EVException {
		persistence.storePoliticalParty(politicalParty);
	}

	@Override
	public void deletePoliticalParty(PoliticalParty politicalParty) throws EVException {
		persistence.deletePoliticalParty(politicalParty);
	}

	@Override
	public ElectoralDistrict createElectoralDistrict(String name) throws EVException {
		ElectoralDistrictImpl district = new ElectoralDistrictImpl(name);
		return district;
	}

	@Override
	public ElectoralDistrict createElectoralDistrict() {
		ElectoralDistrictImpl district = new ElectoralDistrictImpl();
		return district;
	}

	@Override
	public List<ElectoralDistrict> findElectoralDistrict(ElectoralDistrict modelElectoralDistrict) throws EVException {
		return persistence.restoreElectoralDistrict(modelElectoralDistrict);
	}

	@Override
	public void storeElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		persistence.storeElectoralDistrict(electoralDistrict);
	}

	@Override
	public void deleteElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		persistence.deleteElectoralDistrict(electoralDistrict);
	}

	@Override
	public Ballot createBallot(Date openDate, Date closeDate, boolean approved, ElectoralDistrict electoralDistrict)
			throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ballot createBallot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ballot> findBallot(Ballot modelBallot) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeBallot(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBallot(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public Candidate createCandidate(String name, PoliticalParty politicalParty, Election election) throws EVException {
		CandidateImpl candidate = new CandidateImpl(name, politicalParty, election);
		return candidate;
	}

	@Override
	public Candidate createCandidate() {
		CandidateImpl candidate = new CandidateImpl();
		return candidate;
	}

	@Override
	public List<Candidate> findCandidate(Candidate modelCandidate) throws EVException {
		return persistence.restoreCandidate(modelCandidate);
	}

	@Override
	public void storeCandidate(Candidate candidate) throws EVException {
		persistence.storeCandidate(candidate);
	}

	@Override
	public void deleteCandidate(Candidate candidate) throws EVException {
		persistence.deleteCandidate(candidate);
	}

	@Override
	public Issue createIssue(String question) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Issue createIssue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Issue> findIssue(Issue modelIssue) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeIssue(Issue issue) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteIssue(Issue issue) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public Election createElection(String office, boolean isPartisan) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Election createElection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Election> findElection(Election modelElection) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeElection(Election election) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElection(Election election) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public VoteRecord createVoteRecord(Ballot ballot, Voter voter, Date date) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoteRecord createVoteRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VoteRecord> findVoteRecord(VoteRecord modelVoteRecord) throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void storeVoteRecord(VoteRecord voteRecord) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteVoteRecord(VoteRecord voteRecord) throws EVException {
		// TODO Auto-generated method stub

	}

}
