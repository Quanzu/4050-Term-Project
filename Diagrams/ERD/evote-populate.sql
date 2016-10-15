SET foreign_key_checks = 0;

INSERT INTO User(userId, officer, fname, lname, userName, password, email)
VALUES (1, 1, 'Phi', 'Nguyen', 'phi', 'abcd1234', 'phi@mail.com');

INSERT INTO User(userId, officer, fname, lname, userName, password, email)
VALUES (2, 1, 'John', 'Doe', 'jdoe', 'abcd1234', 'joe@mail.com');


INSERT INTO District(districtId, districtName)
VALUES (1, 'District 9');

INSERT INTO User(userId, officer, fname, lname, userName, password, email)
VALUES (3, 0, 'Voter1', 'Guy', 'v1', 'abcd1234', 'v1@mail.com');
INSERT INTO User(userId, officer, fname, lname, userName, password, email)
VALUES (4, 0, 'Voter2', 'Guy', 'v2', 'abcd1234', 'v2@mail.com');
INSERT INTO UserDistrict(districtId, userId)
VALUES (1, 3);
INSERT INTO UserDistrict(districtId, userId)
VALUES (1, 4);

INSERT INTO Party(partyId, partyName)
VALUES (1, 'Democrat');
INSERT INTO Party(partyId, partyName)
VALUES (2, 'Republican');


INSERT INTO Ballot(ballotId, startTime, endTime)
VALUES (1, '2016-10-08 12:00:00', '2016-10-18 12:00:00');

INSERT INTO Election (electionId, name, isPartisan, alternateAllowed, voteCount)
Values (1, 'Election 1', 1, 0, 0);
INSERT INTO ElectionBallot(electionId, ballotId)
VALUES (1,1);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (1, 'Donald', 'Trump', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (2, 'Hillary', 'Clinton', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (3, 'Barrack', 'Obama', 0, 0);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (1,1);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (1,2);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (1,3);
INSERT INTO CandidateParty(candidateId, partyId)
VALUES (1, 2);
INSERT INTO CandidateParty(candidateId, partyId)
VALUES (2, 1);
INSERT INTO CandidateParty(candidateId, partyId)
VALUES (3, 1);

INSERT INTO Election (electionId, name, isPartisan, alternateAllowed, voteCount)
Values (2, 'Election 2', 0, 0, 0);
INSERT INTO ElectionBallot(electionId, ballotId)
VALUES (2,1);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (4, 'John', 'McCain', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (5, 'Mitt', 'Romney', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (6, 'George', 'Bush', 0, 0);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (2,4);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (2,5);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (2,6);

INSERT INTO Election (electionId, name, isPartisan, alternateAllowed, voteCount)
Values (3, 'Election 3', 0, 0, 0);
INSERT INTO ElectionBallot(electionId, ballotId)
VALUES (3,1);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (7, 'Walt', 'Disney', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (8, 'Steve', 'Jobs', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (9, 'Steve', 'Woziak', 0, 0);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (3,7);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (3,8);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (3,9);

INSERT INTO Issue (issueId, question, yesCount, noCount)
VALUES (1, 'Do you like pie?', 0, 0);
INSERT INTO Issue (issueId, question, yesCount, noCount)
VALUES (2, 'Do you like cake?', 0, 0);
INSERT INTO Issue (issueId, question, yesCount, noCount)
VALUES (3, 'Do you like candy?', 0, 0);
INSERT INTO IssueBallot (issueId, ballotId)
VALUES (1, 1);
INSERT INTO IssueBallot (issueId, ballotId)
VALUES (2, 1);
INSERT INTO IssueBallot (issueId, ballotId)
VALUES (3, 1);


INSERT INTO Ballot(ballotId, startTime, endTime)
VALUES (2, '2016-10-08 12:00:00', '2016-10-18 12:00:00');

INSERT INTO Election (electionId, name, isPartisan, alternateAllowed, voteCount)
Values (4, 'Election 4', 1, 0, 0);
INSERT INTO ElectionBallot(electionId, ballotId)
VALUES (4,2);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (10, 'Bill', 'Clinton', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (11, 'George HW', 'Bush', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (12, 'Al', 'Gore', 0, 0);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (4,10);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (4,11);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (4,12);
INSERT INTO CandidateParty(candidateId, partyId)
VALUES (10, 1);
INSERT INTO CandidateParty(candidateId, partyId)
VALUES (11, 2);
INSERT INTO CandidateParty(candidateId, partyId)
VALUES (12, 1);

INSERT INTO Election (electionId, name, isPartisan, alternateAllowed, voteCount)
Values (5, 'Election 5', 0, 0, 0);
INSERT INTO ElectionBallot(electionId, ballotId)
VALUES (5,2);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (13, 'Donald', 'Duck', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (14, 'Spongebob', 'Squarepants', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (15, 'Patrick', 'Star', 0, 0);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (5,13);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (5,14);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (5,15);

INSERT INTO Election (electionId, name, isPartisan, alternateAllowed, voteCount)
Values (6, 'Election 6', 0, 0, 0);
INSERT INTO ElectionBallot(electionId, ballotId)
VALUES (6,2);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (16, 'Scooby', 'Doo', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (17, 'Bat', 'Man', 0, 0);
INSERT INTO Candidate (candidateId, fname, lname, voteCount, isAlternate)
VALUES (18, 'King', 'Kong', 0, 0);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (6,16);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (6,17);
INSERT INTO CandidateElection(electionId, candidateId)
VALUES (6,18);

INSERT INTO Issue (issueId, question, yesCount, noCount)
VALUES (4, 'Do you like games?', 0, 0);
INSERT INTO Issue (issueId, question, yesCount, noCount)
VALUES (5, 'Do you like music?', 0, 0);
INSERT INTO Issue (issueId, question, yesCount, noCount)
VALUES (6, 'Do you like food?', 0, 0);
INSERT INTO IssueBallot (issueId, ballotId)
VALUES (4, 2);
INSERT INTO IssueBallot (issueId, ballotId)
VALUES (5, 2);
INSERT INTO IssueBallot (issueId, ballotId)
VALUES (6, 2);


INSERT INTO BallotDistrict(districtId, ballotId)
VALUES(1, 1);
INSERT INTO BallotDistrict(districtId, ballotId)
VALUES(1, 2);





INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 13:00:00', 3, 1, 1, 3, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 1;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 3;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 14:00:00', 3, 1, 2, 5, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 2;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 5;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 15:00:00', 3, 1, 3, 8, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 3;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 8;


INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 16:00:00', 3, 1, NULL, NULL, 1, 1);
UPDATE Issue
SET yesCount = yesCount+1
WHERE issueId = 1;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 17:00:00', 3, 1, NULL, NULL, 2, 0);
UPDATE Issue
SET noCount = noCount+1
WHERE issueId = 2;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 18:00:00', 3, 1, NULL, NULL, 3, 1);
UPDATE Issue
SET yesCount = yesCount+1
WHERE issueId = 3;


INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 19:00:00', 3, 2, 4, 11, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 4;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 11;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 20:00:00', 3, 2, 5, 15, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 5;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 15;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 21:00:00', 3, 2, 6, 18, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 6;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 18;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 22:00:00', 3, 2, NULL, NULL, 4, 1);
UPDATE Issue
SET yesCount = yesCount+1
WHERE issueId = 4;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 23:00:00', 3, 2, NULL, NULL, 5, 0);
UPDATE Issue
SET noCount = noCount+1
WHERE issueId = 5;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 23:30:00', 3, 2, NULL, NULL, 6, 0);
UPDATE Issue
SET noCount = noCount+1
WHERE issueId = 6;




INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 13:00:00', 4, 1, 1, 3, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 1;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 3;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 14:00:00', 4, 1, 2, 5, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 2;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 5;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 15:00:00', 4, 1, 3, 8, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 3;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 8;


INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 16:00:00', 4, 1, NULL, NULL, 1, 1);
UPDATE Issue
SET yesCount = yesCount+1
WHERE issueId = 1;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 17:00:00', 4, 1, NULL, NULL, 2, 0);
UPDATE Issue
SET noCount = noCount+1
WHERE issueId = 2;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 18:00:00', 4, 1, NULL, NULL, 3, 1);
UPDATE Issue
SET yesCount = yesCount+1
WHERE issueId = 3;


INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 19:00:00', 4, 2, 4, 11, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 4;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 11;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 20:00:00', 4, 2, 5, 15, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 5;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 15;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 21:00:00', 4, 2, 6, 18, NULL, NULL);
UPDATE Election
SET voteCount = voteCount+1
WHERE electionId = 6;
UPDATE Candidate
SET voteCount = voteCount+1
WHERE candidateId = 18;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 22:00:00', 4, 2, NULL, NULL, 4, 1);
UPDATE Issue
SET yesCount = yesCount+1
WHERE issueId = 4;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 23:00:00', 4, 2, NULL, NULL, 5, 0);
UPDATE Issue
SET noCount = noCount+1
WHERE issueId = 5;

INSERT INTO VoteRecord(date, userId, ballotId, electionId, candidateId, issueId, issueYes)
VALUES ('2016-10-09 23:30:00', 4, 2, NULL, NULL, 6, 0);
UPDATE Issue
SET noCount = noCount+1
WHERE issueId = 6;

SET foreign_key_checks = 1;
