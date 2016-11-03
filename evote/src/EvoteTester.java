import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;

public class EvoteTester {

	public static void main(String[] args) throws EVException {
		
		ObjectLayer test = new ObjectLayerImpl();
		ElectoralDistrict ec;
		Ballot b1;
		Ballot b2;
		
		//Create 2 political parties
		PoliticalParty p1 = test.createPoliticalParty("republican");
		PoliticalParty p2 = test.createPoliticalParty("democrat");
		
		//creating elections
		Election elect1 = test.createElection("yes", false);
		Election elect2 = test.createElection("yes", true);
		Election elect3 = test.createElection("yes", false);
		Election elect4 = test.createElection("yes", false);
		Election elect5 = test.createElection("yes", true);
		Election elect6 = test.createElection("yes", false);
		
		//Creates issues
		Issue i1 = test.createIssue("What is my name?");
		Issue i2 = test.createIssue("What is my name?");
		Issue i3 = test.createIssue("What is my name?");
		Issue i4 = test.createIssue("What is my name?");
		Issue i5 = test.createIssue("What is my name?");
		Issue i6 = test.createIssue("What is my name?");
		
		//Creates candidates and puts them in a ballot for each election
		Candidate c1 = test.createCandidate("sara", null, elect1);
		Candidate c2 = test.createCandidate("sara", null, elect1);
		Candidate c3 = test.createCandidate("sara", null, elect1);
		Candidate c4 = test.createCandidate("sara", null, elect3);
		Candidate c5 = test.createCandidate("sara", null, elect3);
		Candidate c6 = test.createCandidate("sara", null, elect3);
		Candidate c7 = test.createCandidate("sara", null, elect4);
		Candidate c8 = test.createCandidate("sara", null, elect4);
		Candidate c9 = test.createCandidate("sara", null, elect4);
		Candidate c10 = test.createCandidate("sara", null, elect6);
		Candidate c11 = test.createCandidate("sara", null, elect6);
		Candidate c12 = test.createCandidate("sara", null, elect6);
		
		//The three partisan candidates per ballot
		Candidate c13 = test.createCandidate("sara",p1, elect2);
		
		Candidate c14 = test.createCandidate("sara", p1, elect2);
		
		Candidate c15 = test.createCandidate("sara", p2, elect2);
		
		Candidate c16 = test.createCandidate("sara", p1, elect5);
		
		Candidate c17 = test.createCandidate("sara", p1, elect5);
		
		Candidate c18 = test.createCandidate("sara", p2, elect5);
		
		
		//Create 2 elections officers
		test.createElectionsOfficer();
		test.createElectionsOfficer();
		
		//create 1 electoral district
		ec = test.createElectoralDistrict();
		
		//create voter and add to a electoral district
		Voter voter1 = test.createVoter("Danielle", "Lee", "dlee", "dlee", "dlee@gmail.com", "123 D road", 43);
		Voter voter2 = test.createVoter("Sammy", "Bro", null, null, null, null, 0);
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
		
		
		//delete
	}

}
