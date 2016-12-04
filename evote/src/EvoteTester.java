import java.sql.*;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.DbUtils;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;

public class EvoteTester {

	
	public static void main(String[] args) throws EVException {
		
		/* MODIFY THESE TWO VALUES TO TEST */
		boolean toCreateAndStore = true;
		boolean toDelete = false;
		
		
		Connection conn = null;
		PersistenceLayer persistence;

		try{
			conn =	DbUtils.connect();
		}
		catch(Exception seq){
			System.err.println("DeleteTest: Unable to obtain a database connection");
			
		}
		if (conn == null)
		{
			System.out.println("DeleteTest: failed to connect to the database");
			return;
		}
		
		ObjectLayer test = new ObjectLayerImpl();
		persistence = new PersistenceLayerImpl(conn, test);
		test.setPersistence(persistence);
		java.util.Date utilDate = new java.util.Date(1478649600);

		
		ElectionsOfficer officer1 = null, officer2 = null;
		ElectoralDistrict electoralDistrict1 = null;
		Voter voter1 = null, voter2 = null;
		PoliticalParty p1 = null, p2 = null;
		Ballot b1 = null, b2 = null;
		Election elect1 = null, elect2 = null, elect3 = null, elect4 = null, elect5 = null, elect6 = null;
		Candidate c1 = null, c2 = null, c3 = null, c4 = null, c5 = null, c6 = null, c7 = null, c8 = null, c9 = null, c10 = null, c11 = null, c12 = null, c13 = null, c14 = null, c15 = null, c16 = null, c17 = null, c18 = null;
		Issue i1 = null, i2 = null, i3 = null, i4 = null, i5 = null, i6 = null;
		VoteRecord vr1 = null, vr2 = null, vr3 = null, vr4 = null;
		
		if(toCreateAndStore){
			//creating elections officer
			officer1 = test.createElectionsOfficer("Phi", "Nguyen", "pnguyen", "abcd1234", "philong@uga.edu", "123 Main St");
			officer2 = test.createElectionsOfficer("John", "Smith", "jsmith", "1234abcd", "jsmith@uga.edu", "321 Main St");
			//storing eo
			test.storeElectionsOfficer(officer1);
			test.storeElectionsOfficer(officer2);
			
			//creating electoral district
			electoralDistrict1 = test.createElectoralDistrict("District 9");
			//storing district
			test.storeElectoralDistrict(electoralDistrict1);
			
			//creating two voters
			voter1 = test.createVoter("Michael", "Jordan", "MJ23", "mjpass", "mj@jumpman.com", "123 Jordan Ave", 60);
			voter2 = test.createVoter("Steve", "Jobs", "appleguy", "apple123", "steve@apple.com", "123 Silicon Valley", 60);
			//storing voters
			test.storeVoter(voter1);
			test.storeVoter(voter2);
			
			//storing voter district associations
			test.getPersistence().storeVoterBelongsToElectoralDistrict(voter1, electoralDistrict1);
			test.getPersistence().storeVoterBelongsToElectoralDistrict(voter2, electoralDistrict1);
			
			//creating two political parties
			p1 = test.createPoliticalParty("Republican");
			p2 = test.createPoliticalParty("Democrat");
			//storing political party
			test.storePoliticalParty(p1);
			test.storePoliticalParty(p2);
			
			//creating ballots
			b1 = test.createBallot(utilDate, utilDate, electoralDistrict1);
			b2 = test.createBallot(utilDate, utilDate, electoralDistrict1);		
			//storing ballots and storing district ballot associations
			test.storeBallot(b1);
			test.storeBallot(b2);
			test.getPersistence().storeElectoralDistrictHasBallotBallot(electoralDistrict1, b1);
			test.getPersistence().storeElectoralDistrictHasBallotBallot(electoralDistrict1, b2);
	
			//creating elections
			elect1 = test.createElection("Judge1", false);
			elect2 = test.createElection("Pants", true);
			elect3 = test.createElection("HomeComing", false);	
			elect4 = test.createElection("Judge3", false);
			elect5 = test.createElection("President", true);
			elect6 = test.createElection("Student Council", false);
			//storing elections
			persistence.storeElection(elect1);
			persistence.storeElection(elect2);
			persistence.storeElection(elect3);
			persistence.storeElection(elect4);
			persistence.storeElection(elect5);
			persistence.storeElection(elect6);
	
			//Creates candidates and puts them in a ballot for each election
			c1 = test.createCandidate("sara", null, elect1);
			c2 = test.createCandidate("Beth", null, elect1);
			c3 = test.createCandidate("Nick", null, elect1);
			c4 = test.createCandidate("Thomas", null, elect3);
			c5 = test.createCandidate("David", null, elect3);
			c6 = test.createCandidate("Kaylee", null, elect3);
			c7 = test.createCandidate("Toby", null, elect4);
			c8 = test.createCandidate("Tillery", null, elect4);
			c9 = test.createCandidate("Abbey", null, elect4);
			c10 = test.createCandidate("Brad", null, elect6);
			c11 = test.createCandidate("Michael", null, elect6);
			c12 = test.createCandidate("Ian", null, elect6);
			
			//The three partisan candidates per ballot
			c13 = test.createCandidate("Charlie",p1, elect2);
			c14 = test.createCandidate("Jack", p1, elect2);
			c15 = test.createCandidate("Katie", p2, elect2);
			c16 = test.createCandidate("Lillian", p1, elect5);
			c17 = test.createCandidate("James", p1, elect5);
			c18 = test.createCandidate("Danielle", p2, elect5);
			//Storing candidate
			persistence.storeCandidate(c1);
			persistence.storeCandidate(c2);
			persistence.storeCandidate(c3);
			persistence.storeCandidate(c4);
			persistence.storeCandidate(c5);
			persistence.storeCandidate(c6);
			persistence.storeCandidate(c7);
			persistence.storeCandidate(c8);
			persistence.storeCandidate(c9);
			persistence.storeCandidate(c10);
			persistence.storeCandidate(c11);
			persistence.storeCandidate(c12);
			persistence.storeCandidate(c13);
			persistence.storeCandidate(c14);
			persistence.storeCandidate(c15);
			persistence.storeCandidate(c16);
			persistence.storeCandidate(c17);
			persistence.storeCandidate(c18);
			
			
			//adds candidates to election
			elect1.addCandidate(c1);
			elect1.addCandidate(c2);
			elect1.addCandidate(c3);
			elect2.addCandidate(c13);
			elect2.addCandidate(c14);
			elect2.addCandidate(c15);
			elect3.addCandidate(c4);
			elect3.addCandidate(c5);
			elect3.addCandidate(c6);
			elect4.addCandidate(c7);
			elect4.addCandidate(c8);
			elect4.addCandidate(c9);
			elect5.addCandidate(c16);
			elect5.addCandidate(c17);
			elect5.addCandidate(c18);
			elect6.addCandidate(c10);
			elect6.addCandidate(c11);
			elect6.addCandidate(c12);
			
			//storing political party partisan candidates is from
			persistence.storeCandidateIsMemberOfPoliticalParty(c13, p1);
			persistence.storeCandidateIsMemberOfPoliticalParty(c14, p1);
			persistence.storeCandidateIsMemberOfPoliticalParty(c15, p2);
			persistence.storeCandidateIsMemberOfPoliticalParty(c16, p1);
			persistence.storeCandidateIsMemberOfPoliticalParty(c17, p1);
			persistence.storeCandidateIsMemberOfPoliticalParty(c18, p2);
			//storing candidate election association
			persistence.storeCandidateIsCandidateInElection(c1, elect1);
			persistence.storeCandidateIsCandidateInElection(c2, elect1);
			persistence.storeCandidateIsCandidateInElection(c3, elect1);
			persistence.storeCandidateIsCandidateInElection(c4, elect3);
			persistence.storeCandidateIsCandidateInElection(c5, elect3);
			persistence.storeCandidateIsCandidateInElection(c6, elect3);
			persistence.storeCandidateIsCandidateInElection(c7, elect4);
			persistence.storeCandidateIsCandidateInElection(c8, elect4);
			persistence.storeCandidateIsCandidateInElection(c9, elect4);
			persistence.storeCandidateIsCandidateInElection(c10, elect6);
			persistence.storeCandidateIsCandidateInElection(c11, elect6);
			persistence.storeCandidateIsCandidateInElection(c12, elect6);
			persistence.storeCandidateIsCandidateInElection(c13, elect2);
			persistence.storeCandidateIsCandidateInElection(c14, elect2);
			persistence.storeCandidateIsCandidateInElection(c15, elect2);
			persistence.storeCandidateIsCandidateInElection(c16, elect5);
			persistence.storeCandidateIsCandidateInElection(c17, elect5);
			persistence.storeCandidateIsCandidateInElection(c18, elect5);
			
			//creating issues
			i1 = test.createIssue("Would you like a new CS building?");
			i2 = test.createIssue("Should we ban knives at the MLC");
			i3 = test.createIssue("Should the drinking age be lowered?");	
			i4 = test.createIssue("Should Bolton be open later than 8?");
			i5 = test.createIssue("Should UGA enforce a dress code?");
			i6 = test.createIssue("Should teachers make attendance mandatory?");
			//storing issues
			persistence.storeIssue(i1);
			persistence.storeIssue(i2);
			persistence.storeIssue(i3);
			persistence.storeIssue(i4);
			persistence.storeIssue(i5);
			persistence.storeIssue(i6);
	
			//storing ballot and issue link
			persistence.storeBallotIncludesBallotItem(b1, i1);
			persistence.storeBallotIncludesBallotItem(b1, i2);
			persistence.storeBallotIncludesBallotItem(b1, i3);
			persistence.storeBallotIncludesBallotItem(b2, i4);
			persistence.storeBallotIncludesBallotItem(b2, i5);
			persistence.storeBallotIncludesBallotItem(b2, i6);
			//storing ballot and election
			persistence.storeBallotIncludesBallotItem(b1, elect1);
			persistence.storeBallotIncludesBallotItem(b1, elect2);
			persistence.storeBallotIncludesBallotItem(b1, elect3);
			persistence.storeBallotIncludesBallotItem(b2, elect4);
			persistence.storeBallotIncludesBallotItem(b2, elect5);
			persistence.storeBallotIncludesBallotItem(b2, elect6);
			
			vr1 = test.createVoteRecord(b1, voter1, utilDate);
			vr2 = test.createVoteRecord(b2, voter1, utilDate);
			vr3 = test.createVoteRecord(b1, voter2, utilDate);
			vr4 = test.createVoteRecord(b2, voter2, utilDate);
			persistence.storeVoteRecord(vr1);
			persistence.storeVoteRecord(vr2);
			persistence.storeVoteRecord(vr3);
			persistence.storeVoteRecord(vr4);
		}
		
		List<BallotItem> items =persistence.restoreBallotIncludesBallotItem(b1);
		int countI = 0;
		int countE = 0;
		for (int i = 0; i<items.size(); i++)
		{
			if(items.get(i) instanceof Issue )
			{
				Issue issue = (Issue)items.get(i);
				countI++;
				System.out.println(issue.getQuestion());
			}
			
			if(items.get(i) instanceof Election )
			{
				Election election = (Election)items.get(i);
				countE++;
				System.out.println(election.getOffice());
			}
		}
		System.out.println(items.size());
		System.out.println(countI);
		System.out.println(countE);
		
		if(toDelete){
			//delete voter
			persistence.deleteVoter(voter1);
			persistence.deleteVoter(voter2);
			
			//delete parties
			persistence.deletePoliticalParty(p1);
			persistence.deletePoliticalParty(p2);
			
			//delete Issues
			persistence.deleteIssue(i1);
			persistence.deleteIssue(i2);
			persistence.deleteIssue(i3);
			persistence.deleteIssue(i4);
			persistence.deleteIssue(i5);
			persistence.deleteIssue(i6);
			
			//delete elections
			persistence.deleteElection(elect1);
			persistence.deleteElection(elect2);
			persistence.deleteElection(elect3);
			persistence.deleteElection(elect4);
			persistence.deleteElection(elect5);
			persistence.deleteElection(elect6);
			
			//delete ballot
			persistence.deleteBallot(b1);
			persistence.deleteBallot(b2);
			
			//delete electoral district
			persistence.deleteElectoralDistrict(electoralDistrict1);
			
			//Delete candidates
			persistence.deleteCandidate(c1);
			persistence.deleteCandidate(c2);
			persistence.deleteCandidate(c3);
			persistence.deleteCandidate(c4);
			persistence.deleteCandidate(c5);
			persistence.deleteCandidate(c6);
			persistence.deleteCandidate(c7);
			persistence.deleteCandidate(c8);
			persistence.deleteCandidate(c9);
			persistence.deleteCandidate(c10);
			persistence.deleteCandidate(c11);
			persistence.deleteCandidate(c12);
			persistence.deleteCandidate(c13);
			persistence.deleteCandidate(c14);
			persistence.deleteCandidate(c15);
			persistence.deleteCandidate(c16);
			persistence.deleteCandidate(c17);
			persistence.deleteCandidate(c18);
			
			//delete eo
			persistence.deleteElectionsOfficer(officer1);
			persistence.deleteElectionsOfficer(officer2);

		}
	}
}