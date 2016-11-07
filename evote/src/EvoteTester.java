import java.sql.Connection;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.DbUtils;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;

public class EvoteTester {

	public static void main(String[] args) throws EVException {
		Connection conn = null;
		PersistenceLayer persistence;
		
		try{
			conn = DbUtils.connect();
		}
		catch(Exception seq){
			System.err.println("DeleteTest: Unable to obtain a database connection");
			
		}
		if (conn == null)
		{
			System.out.println("DeleteTest: failed to conccect to the database");
			return;
		}
		
		ObjectLayer test = new ObjectLayerImpl();
		persistence = new PersistenceLayerImpl(conn, test);
		test.setPersistence(persistence);
		
		
		ElectoralDistrict ec;
		Ballot b1;
		Ballot b2;
		
		//Create 2 political parties
		PoliticalParty p1 = test.createPoliticalParty("republican");
		PoliticalParty p2 = test.createPoliticalParty("democrat");
		
		//creating elections
		Election elect1 = test.createElection("Judge1", false);
		Election elect2 = test.createElection("UGA SGA President", true);
		Election elect3 = test.createElection("HomeComing", false);
		Election elect4 = test.createElection("Judge3", false);
		Election elect5 = test.createElection("President", true);
		Election elect6 = test.createElection("Student Council", false);
		
		//Creates issues
		Issue i1 = test.createIssue("Would you like a new CS building?");
		Issue i2 = test.createIssue("Should we ban knives at the MLC");
		Issue i3 = test.createIssue("Should the drinking age be lowered?");
		Issue i4 = test.createIssue("Should Bolton be open later than 8?");
		Issue i5 = test.createIssue("Should UGA enforce a dress code?");
		Issue i6 = test.createIssue("Should teachers make attendance mandatory?");
		
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
		
		//Create 2 elections officers
		test.createElectionsOfficer();
		test.createElectionsOfficer();
		
		//create 1 electoral district
		ec = test.createElectoralDistrict();
		
		//create voter and add to a electoral district
		Voter voter1 = test.createVoter("Danielle", "Lee", "dlee", "dlee", "dlee@gmail.com", "123 D road", 43);
		Voter voter2 = test.createVoter("Sammy", "Bro", "SBro", "SBro", "sammy@gmail.com", "456 Simon Street", 31);
		voter1.setElectoralDistrict(ec);
		voter2.setElectoralDistrict(ec);
		
		
		//create 2 ballots with 3 issues and 3 elections
		b1 = test.createBallot();
		b2 = test.createBallot();
		
		//add to ballot 1
		b1.addBallotItem(elect1);
		b1.addBallotItem(elect2);
		b1.addBallotItem(elect3);
		b1.addBallotItem(i1);
		b1.addBallotItem(i2);
		b1.addBallotItem(i3);

		
		//add to ballot2
		b1.addBallotItem(elect4);
		b1.addBallotItem(elect5);
		b1.addBallotItem(elect6);
		b1.addBallotItem(i4);
		b1.addBallotItem(i5);
		b1.addBallotItem(i6);
		
		
		//Creating Data
		VoteRecord vr = test.createVoteRecord(b1, voter1, null);
		VoteRecord vr2 = test.createVoteRecord(b2, voter1, null);
		
		VoteRecord vr3 = test.createVoteRecord(b1, voter2, null);
		VoteRecord vr4 = test.createVoteRecord(b2, voter2, null);
		
		//Voter 1 
		//c1-3 in elect1
		//c4-6 in elect3
		//c13-15 in elect 2
		//c7-9 in elect 4
		//c16-18 in elect 5
		//c10-12 in elect 6
		c1.addVote(); //election1
		c13.addVote(); //election2
		c6.addVote();
		c7.addVote(); //election 4
		c18.addVote(); //election5
		c10.addVote(); //election 6
		
		i1.addYesVote();
		i2.addYesVote();
		i3.addNoVote();
		i4.addNoVote();
		i5.addYesVote();
		i6.addNoVote();
		
		//voter 2
		c3.addVote(); //election1
		c12.addVote(); //election2
		c6.addVote();
		c9.addVote(); //election 4
		c17.addVote(); //election5
		c11.addVote(); //election 6
		
		i1.addYesVote();
		i2.addNoVote();
		i3.addYesVote();
		i4.addYesVote();
		i5.addYesVote();
		i6.addYesVote();
		
		//delete
		
	}

}
