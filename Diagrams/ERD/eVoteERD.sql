CREATE TABLE Ballot (
 ballotId INT NOT NULL AUTO_INCREMENT,
 openDate DATE,
 closeDate DATE,
 PRIMARY KEY (ballotId)
);

CREATE TABLE Candidate (
 candidateId INT NOT NULL AUTO_INCREMENT,
 name CHAR(20),
 voteCount INT,
 isAlternate INT,
 PRIMARY KEY (candidateId)
);

CREATE TABLE Election (
 electionId INT NOT NULL AUTO_INCREMENT,
 office CHAR(15),
 isPartisan INT,
 alternateAllowed INT,
 voteCount CHAR(10),
 PRIMARY KEY (electionId)
);

CREATE TABLE ElectionBallot (
 electionId INT NOT NULL,
 ballotId INT NOT NULL
);

ALTER TABLE ElectionBallot ADD CONSTRAINT PK_ElectionBallot PRIMARY KEY (electionId,ballotId);


CREATE TABLE ElectoralDistrict (
 districtId INT NOT NULL AUTO_INCREMENT,
 districtName CHAR(15),
 PRIMARY KEY (districtId)
);

CREATE TABLE Issue (
 issueId INT NOT NULL AUTO_INCREMENT,
 question CHAR(100),
 yesCount INT,
 PRIMARY KEY (issueId)
);

CREATE TABLE IssueBallot (
 issueId INT NOT NULL,
 ballotId INT NOT NULL
);

ALTER TABLE IssueBallot ADD CONSTRAINT PK_IssueBallot PRIMARY KEY (issueId,ballotId);


CREATE TABLE Party (
 partyId INT NOT NULL AUTO_INCREMENT,
 partyName CHAR(20),
 PRIMARY KEY (partyId)
);

CREATE TABLE User (
 userId INT NOT NULL AUTO_INCREMENT,
 fname CHAR(20),
 lname CHAR(20),
 userName CHAR(20) NOT NULL UNIQUE,
 password CHAR(50),
 email CHAR(25),
 address CHAR(50),
 PRIMARY KEY (userId)
);

CREATE TABLE Voter (
 voterId INT NOT NULL AUTO_INCREMENT,
 userId INT NOT NULL,
 age INT,
 PRIMARY KEY (voterId)
);

CREATE TABLE VoterDistrict (
 districtId INT NOT NULL,
 voterId INT NOT NULL
);

ALTER TABLE VoterDistrict ADD CONSTRAINT PK_VoterDistrict PRIMARY KEY (districtId,voterId);


CREATE TABLE VoteRecord (
 voteRecordId INT NOT NULL AUTO_INCREMENT,
 date TIMESTAMP(6) NOT NULL,
 ballotId INT NOT NULL,
 voterId INT NOT NULL,
 PRIMARY KEY (voteRecordId)
);


CREATE TABLE BallotDistrict (
 districtId INT NOT NULL,
 ballotId INT NOT NULL
);

ALTER TABLE BallotDistrict ADD CONSTRAINT PK_BallotDistrict PRIMARY KEY (districtId,ballotId);


CREATE TABLE CandidateElection (
 electionId INT NOT NULL,
 candidateId INT NOT NULL
);

ALTER TABLE CandidateElection ADD CONSTRAINT PK_CandidateElection PRIMARY KEY (electionId,candidateId);


CREATE TABLE CandidateParty (
 candidateId INT NOT NULL,
 partyId INT NOT NULL
);

ALTER TABLE CandidateParty ADD CONSTRAINT PK_CandidateParty PRIMARY KEY (candidateId,partyId);


CREATE TABLE ElectionsOfficer (
 userId INT NOT NULL
);

ALTER TABLE ElectionsOfficer ADD CONSTRAINT PK_ElectionsOfficer PRIMARY KEY (userId);


ALTER TABLE ElectionBallot ADD CONSTRAINT FK_ElectionBallot_0 FOREIGN KEY (electionId) REFERENCES Election (electionId) ON DELETE CASCADE;
ALTER TABLE ElectionBallot ADD CONSTRAINT FK_ElectionBallot_1 FOREIGN KEY (ballotId) REFERENCES Ballot (ballotId) ON DELETE CASCADE;


ALTER TABLE IssueBallot ADD CONSTRAINT FK_IssueBallot_0 FOREIGN KEY (issueId) REFERENCES Issue (issueId) ON DELETE CASCADE;
ALTER TABLE IssueBallot ADD CONSTRAINT FK_IssueBallot_1 FOREIGN KEY (ballotId) REFERENCES Ballot (ballotId) ON DELETE CASCADE;


ALTER TABLE Voter ADD CONSTRAINT FK_Voter_0 FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE;


/*ALTER TABLE VoterDistrict ADD CONSTRAINT FK_VoterDistrict_0 FOREIGN KEY (districtId) REFERENCES ElectoralDistrict (districtId) ON DELETE CASCADE;
ALTER TABLE VoterDistrict ADD CONSTRAINT FK_VoterDistrict_1 FOREIGN KEY (voterId) REFERENCES Voter (voterId) ON DELETE CASCADE;
*/

ALTER TABLE VoteRecord ADD CONSTRAINT FK_VoteRecord_0 FOREIGN KEY (ballotId) REFERENCES Ballot (ballotId) ON DELETE CASCADE;
ALTER TABLE VoteRecord ADD CONSTRAINT FK_VoteRecord_1 FOREIGN KEY (voterId) REFERENCES Voter (voterId) ON DELETE CASCADE;


ALTER TABLE BallotDistrict ADD CONSTRAINT FK_BallotDistrict_0 FOREIGN KEY (districtId) REFERENCES ElectoralDistrict (districtId) ON DELETE CASCADE;
ALTER TABLE BallotDistrict ADD CONSTRAINT FK_BallotDistrict_1 FOREIGN KEY (ballotId) REFERENCES Ballot (ballotId) ON DELETE CASCADE;


ALTER TABLE CandidateElection ADD CONSTRAINT FK_CandidateElection_0 FOREIGN KEY (electionId) REFERENCES Election (electionId) ON DELETE CASCADE;
ALTER TABLE CandidateElection ADD CONSTRAINT FK_CandidateElection_1 FOREIGN KEY (candidateId) REFERENCES Candidate (candidateId) ON DELETE CASCADE;


ALTER TABLE CandidateParty ADD CONSTRAINT FK_CandidateParty_0 FOREIGN KEY (candidateId) REFERENCES Candidate (candidateId) ON DELETE CASCADE;
ALTER TABLE CandidateParty ADD CONSTRAINT FK_CandidateParty_1 FOREIGN KEY (partyId) REFERENCES Party (partyId) ON DELETE CASCADE;


ALTER TABLE ElectionsOfficer ADD CONSTRAINT FK_ElectionsOfficer_0 FOREIGN KEY (userId) REFERENCES User (userId) ON DELETE CASCADE;
