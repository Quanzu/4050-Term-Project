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
		
<<<<<<< HEAD
<<<<<<< HEAD
		//delete ballot and item link (issues and elections)
		persistence.deleteBallotIncludesBallotItem(b1, elect1);
		persistence.deleteBallotIncludesBallotItem(b1, elect2);
		persistence.deleteBallotIncludesBallotItem(b1, elect3);
		persistence.deleteBallotIncludesBallotItem(b2, i1);
		persistence.deleteBallotIncludesBallotItem(b2, i2);
		persistence.deleteBallotIncludesBallotItem(b2, i3);
		persistence.deleteBallotIncludesBallotItem(b1, elect4);
		persistence.deleteBallotIncludesBallotItem(b1, elect5);
		persistence.deleteBallotIncludesBallotItem(b1, elect6);
		persistence.deleteBallotIncludesBallotItem(b2, i4);
		persistence.deleteBallotIncludesBallotItem(b2, i5);
		persistence.deleteBallotIncludesBallotItem(b2, i6);
		
		//delete ballotdistrict link
		persistence.deleteElectoralDistrictHasBallotBallot(ec, b1);
		persistence.deleteElectoralDistrictHasBallotBallot(ec, b2);
		
		//delete voter district link
		persistence.deleteVoterBelongsToElection(voter1, ec);
		persistence.deleteVoterBelongsToElection(voter2, ec);
		
		//Need delete vote record?
		
		//delete ballot
		persistence.deleteBallot(b1);
		persistence.deleteBallot(b2);
		
		//delete electoral district
		persistence.deleteElectoralDistrict(ec);
		
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
				
		
		//delete candidate and party llink
		persistence.deleteCandidateIsMemberOfElection(c13, p1);
		persistence.deleteCandidateIsMemberOfElection(c14, p1);
		persistence.deleteCandidateIsMemberOfElection(c15, p2);
		persistence.deleteCandidateIsMemberOfElection(c16, p1);
		persistence.deleteCandidateIsMemberOfElection(c17, p1);
		persistence.deleteCandidateIsMemberOfElection(c18, p2);
		
		//delete parties
		persistence.deletePoliticalParty(p1);
		persistence.deletePoliticalParty(p2);
		
		//delete candidate - election
		persistence.deleteCandidateIsCandidateInElection(c1, elect1);
		persistence.deleteCandidateIsCandidateInElection(c2, elect1);
		persistence.deleteCandidateIsCandidateInElection(c3, elect1);
		persistence.deleteCandidateIsCandidateInElection(c13, elect2);
		persistence.deleteCandidateIsCandidateInElection(c14, elect2);
		persistence.deleteCandidateIsCandidateInElection(c15, elect2);
		persistence.deleteCandidateIsCandidateInElection(c4, elect3);
		persistence.deleteCandidateIsCandidateInElection(c5, elect3);
		persistence.deleteCandidateIsCandidateInElection(c6, elect3);
		persistence.deleteCandidateIsCandidateInElection(c7, elect4);
		persistence.deleteCandidateIsCandidateInElection(c8, elect4);
		persistence.deleteCandidateIsCandidateInElection(c9, elect4);
		persistence.deleteCandidateIsCandidateInElection(c16, elect5);
		persistence.deleteCandidateIsCandidateInElection(c17, elect5);
		persistence.deleteCandidateIsCandidateInElection(c18, elect5);
		persistence.deleteCandidateIsCandidateInElection(c10, elect6);
		persistence.deleteCandidateIsCandidateInElection(c11, elect6);
		persistence.deleteCandidateIsCandidateInElection(c12, elect6);
		
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
		
		//delete voter
		persistence.deleteVoter(voter1);
		persistence.deleteVoter(voter2);

=======
		//delete
>>>>>>> origin/master
=======
		//delete
>>>>>>> origin/master
	}

}
