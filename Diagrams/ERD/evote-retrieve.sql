SELECT fname, lname
FROM User
WHERE officer=1;

SELECT districtName
FROM District;

SELECT fname, lname, districtName
FROM User
INNER JOIN UserDistrict
ON UserDistrict.userId = User.userId
INNER JOIN District
ON UserDistrict.districtId = District.districtId
WHERE officer=0;

SELECT partyName
FROM Party;

SELECT Ballot.ballotId, name
FROM Election, Ballot, ElectionBallot
WHERE Ballot.ballotId = ElectionBallot.ballotId
AND ElectionBallot.electionId = Election.electionId;

SELECT Ballot.ballotId, question
FROM Ballot, Issue, IssueBallot
WHERE Ballot.ballotId = IssueBallot.ballotId
AND IssueBallot.issueId = Issue.issueId;

SELECT name, Candidate.fname, Candidate.lname
FROM Election
INNER JOIN CandidateElection
ON Election.electionId = CandidateElection.electionId
INNER JOIN Candidate
ON CandidateElection.candidateId = Candidate.candidateId
WHERE isPartisan = 0;

SELECT name, Candidate.fname, Candidate.lname, partyName
FROM Election
INNER JOIN CandidateElection
ON Election.electionId = CandidateElection.electionId
INNER JOIN Candidate
ON CandidateElection.candidateId = Candidate.candidateId
INNER JOIN CandidateParty
ON Candidate.candidateId = CandidateParty.candidateId
INNER JOIN Party
ON CandidateParty.partyId = Party.partyId
WHERE isPartisan = 1;

SELECT question, yesCount+noCount, yesCount
FROM Issue
GROUP BY question;

SELECT name, Election.voteCount, fname, lname, Candidate.voteCount
FROM Election
INNER JOIN CandidateElection
ON Election.electionId = CandidateElection.electionId
INNER JOIN Candidate
ON CandidateElection.candidateId = Candidate.candidateId;
