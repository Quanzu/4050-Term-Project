import java.sql.*;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.DbUtils;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class DriverTest {

	public static void main(String[] args) throws EVException {
		
		Connection conn = null;
		PersistenceLayer persistence;
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "abcd1234");
		prop.setProperty("useSSL", "false");
		prop.setProperty("autoReconnect", "true");
		try{
			conn = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/eVote", prop);
					//"jdbc:mysql://localhost:3306/evote","root","sdafsd");
					//DbUtils.connect();
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

		
		//creating elections officer
		ElectionsOfficer officer1 = test.createElectionsOfficer("Phi", "Nguyen", "pnguyen", "abcd1234", "philong@uga.edu", "123 Main St");
		ElectionsOfficer officer2 = test.createElectionsOfficer("John", "Smith", "jsmith", "1234abcd", "jsmith@uga.edu", "321 Main St");
		//storing eo
		test.storeElectionsOfficer(officer1);
		test.storeElectionsOfficer(officer2);
		
		//creating electoral district
		ElectoralDistrict electoralDistrict1 = test.createElectoralDistrict("District 9");
		//storing district
		test.storeElectoralDistrict(electoralDistrict1);
		
		//creating two voters
		Voter voter1 = test.createVoter("Michael", "Jordan", "MJ23", "mjpass", "mj@jumpman.com", "123 Jordan Ave", 60);
		Voter voter2 = test.createVoter("Steve", "Jobs", "appleguy", "apple123", "steve@apple.com", "123 Silicon Valley", 60);
		//storing voters
		test.storeVoter(voter1);
		test.storeVoter(voter2);
		
		//storing voter district associations
		test.getPersistence().storeVoterBelongsToElectoralDistrict(voter1, electoralDistrict1);
		test.getPersistence().storeVoterBelongsToElectoralDistrict(voter2, electoralDistrict1);
		
		//creating two political parties
		PoliticalParty p1 = test.createPoliticalParty("Republican");
		PoliticalParty p2 = test.createPoliticalParty("Democrat");
		//storing political party
		test.storePoliticalParty(p1);
		test.storePoliticalParty(p2);
		
		//creating ballots
		Ballot b1 = test.createBallot(utilDate, utilDate, electoralDistrict1);
		Ballot b2 = test.createBallot(utilDate, utilDate, electoralDistrict1);		
		//storing ballots and storing district ballot associations
		test.storeBallot(b1);
		test.storeBallot(b2);
		test.getPersistence().deleteElectoralDistrictHasBallotBallot(electoralDistrict1, b1);
		test.getPersistence().deleteElectoralDistrictHasBallotBallot(electoralDistrict1, b2);

		//creating elections
		Election elect1 = test.createElection("Judge1", false);
		Election elect2 = test.createElection("Pants", true);
		Election elect3 = test.createElection("HomeComing", false);	
		Election elect4 = test.createElection("Judge3", false);
		Election elect5 = test.createElection("President", true);
		Election elect6 = test.createElection("Student Council", false);
		//storing elections
		persistence.storeElection(elect1);
		persistence.storeElection(elect2);
		persistence.storeElection(elect3);
		persistence.storeElection(elect4);
		persistence.storeElection(elect5);
		persistence.storeElection(elect6);

		//Creates candidates and puts them in a ballot for each election
		Candidate c1 = test.createCandidate("sara", null, elect1);
		Candidate c2 = test.createCandidate("Beth", null, elect1);
		Candidate c3 = test.createCandidate("Nick", null, elect1);
		Candidate c4 = test.createCandidate("Thomas", null, elect3);
		Candidate c5 = test.createCandidate("David", null, elect3);
		Candidate c6 = test.createCandidate("Kaylee", null, elect3);
		Candidate c7 = test.createCandidate("Toby", null, elect4);
		Candidate c8 = test.createCandidate("Tillery", null, elect4);
		Candidate c9 = test.createCandidate("Abbey", null, elect4);
		Candidate c10 = test.createCandidate("Brad", null, elect6);
		Candidate c11 = test.createCandidate("Michael", null, elect6);
		Candidate c12 = test.createCandidate("Ian", null, elect6);
		
		//The three partisan candidates per ballot
		Candidate c13 = test.createCandidate("Charlie",p1, elect2);
		Candidate c14 = test.createCandidate("Jack", p1, elect2);
		Candidate c15 = test.createCandidate("Katie", p2, elect2);
		Candidate c16 = test.createCandidate("Lillian", p1, elect5);
		Candidate c17 = test.createCandidate("James", p1, elect5);
		Candidate c18 = test.createCandidate("Danielle", p2, elect5);
		//storing political party partisan candidates is from
		persistence.storeCandidateIsMemberOfPoliticalParty(c13, p1);
		persistence.storeCandidateIsMemberOfPoliticalParty(c14, p1);
		persistence.storeCandidateIsMemberOfPoliticalParty(c15, p2);
		persistence.storeCandidateIsMemberOfPoliticalParty(c16, p1);
		persistence.storeCandidateIsMemberOfPoliticalParty(c17, p1);
		persistence.storeCandidateIsMemberOfPoliticalParty(c18, p2);
		
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
		Issue i1 = test.createIssue("Would you like a new CS building?");
		Issue i2 = test.createIssue("Should we ban knives at the MLC");
		Issue i3 = test.createIssue("Should the drinking age be lowered?");	
		Issue i4 = test.createIssue("Should Bolton be open later than 8?");
		Issue i5 = test.createIssue("Should UGA enforce a dress code?");
		Issue i6 = test.createIssue("Should teachers make attendance mandatory?");
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
		
		VoteRecord vr1 = test.createVoteRecord(b1, voter1, utilDate);
		VoteRecord vr2 = test.createVoteRecord(b2, voter1, utilDate);
		VoteRecord vr3 = test.createVoteRecord(b1, voter2, utilDate);
		VoteRecord vr4 = test.createVoteRecord(b2, voter2, utilDate);
		persistence.storeVoteRecord(vr1);
		persistence.storeVoteRecord(vr2);
		persistence.storeVoteRecord(vr3);
		persistence.storeVoteRecord(vr4);

		
		
	}
}