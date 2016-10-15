SET SQL_MODE = 'ALLOW_INVALID_DATES';

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS Address, Ballot, Candidate, District, Election, ElectionBallot,
Issue, IssueBallot, Party, User, UserAddress, UserDistrict, VoteRecord, BallotDistrict,
CandidateElection, CandidateParty;
SET foreign_key_checks = 1;


CREATE TABLE Address (
 addressId INT NOT NULL,
 street CHAR(30),
 city CHAR(20),
 state CHAR(20),
 zip INT
);

ALTER TABLE Address ADD CONSTRAINT PK_Address PRIMARY KEY (addressId);


CREATE TABLE Ballot (
 ballotId INT NOT NULL,
 startTime TIMESTAMP,
 endTime TIMESTAMP
);

ALTER TABLE Ballot ADD CONSTRAINT PK_Ballot PRIMARY KEY (ballotId);


CREATE TABLE Candidate (
 candidateId INT NOT NULL,
 fname CHAR(20),
 lname CHAR(20),
 voteCount INT,
 isAlternate INT
);

ALTER TABLE Candidate ADD CONSTRAINT PK_Candidate PRIMARY KEY (candidateId);


CREATE TABLE District (
 districtId INT NOT NULL,
 districtName CHAR(15)
);

ALTER TABLE District ADD CONSTRAINT PK_District PRIMARY KEY (districtId);


CREATE TABLE Election (
 electionId INT NOT NULL,
 name CHAR(15),
 isPartisan INT,
 alternateAllowed INT,
 voteCount CHAR(10)
);

ALTER TABLE Election ADD CONSTRAINT PK_Election PRIMARY KEY (electionId);


CREATE TABLE ElectionBallot (
 electionId INT NOT NULL,
 ballotId INT NOT NULL
);

ALTER TABLE ElectionBallot ADD CONSTRAINT PK_ElectionBallot PRIMARY KEY (electionId,ballotId);


CREATE TABLE Issue (
 issueId INT NOT NULL,
 question CHAR(100),
 yesCount INT,
 noCount INT
);

ALTER TABLE Issue ADD CONSTRAINT PK_Issue PRIMARY KEY (issueId);


CREATE TABLE IssueBallot (
 issueId INT NOT NULL,
 ballotId INT NOT NULL
);

ALTER TABLE IssueBallot ADD CONSTRAINT PK_IssueBallot PRIMARY KEY (issueId,ballotId);


CREATE TABLE Party (
 partyId INT NOT NULL,
 partyName CHAR(20)
);

ALTER TABLE Party ADD CONSTRAINT PK_Party PRIMARY KEY (partyId);


CREATE TABLE User (
 userId INT NOT NULL,
 officer INT,
 fname CHAR(20),
 lname CHAR(20),
 userName CHAR(20),
 password CHAR(50),
 email CHAR(25)
);

ALTER TABLE User ADD CONSTRAINT PK_User PRIMARY KEY (userId);


CREATE TABLE UserAddress (
 userId INT NOT NULL,
 addressId INT NOT NULL
);

ALTER TABLE UserAddress ADD CONSTRAINT PK_UserAddress PRIMARY KEY (userId,addressId);


CREATE TABLE UserDistrict (
 districtId INT NOT NULL,
 userId INT NOT NULL
);

ALTER TABLE UserDistrict ADD CONSTRAINT PK_UserDistrict PRIMARY KEY (districtId,userId);


CREATE TABLE VoteRecord (
 date TIMESTAMP NOT NULL,
 userId INT NOT NULL,
 ballotId INT NOT NULL,
 electionId INT,
 candidateId INT,
 issueId INT,
 issueYes INT
);

ALTER TABLE VoteRecord ADD CONSTRAINT PK_VoteRecord PRIMARY KEY (date,userId,ballotId);


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


ALTER TABLE ElectionBallot ADD CONSTRAINT FK_ElectionBallot_0 FOREIGN KEY (electionId) REFERENCES Election (electionId);
ALTER TABLE ElectionBallot ADD CONSTRAINT FK_ElectionBallot_1 FOREIGN KEY (ballotId) REFERENCES Ballot (ballotId);


ALTER TABLE IssueBallot ADD CONSTRAINT FK_IssueBallot_0 FOREIGN KEY (issueId) REFERENCES Issue (issueId);
ALTER TABLE IssueBallot ADD CONSTRAINT FK_IssueBallot_1 FOREIGN KEY (ballotId) REFERENCES Ballot (ballotId);


ALTER TABLE UserAddress ADD CONSTRAINT FK_UserAddress_0 FOREIGN KEY (userId) REFERENCES User (userId);
ALTER TABLE UserAddress ADD CONSTRAINT FK_UserAddress_1 FOREIGN KEY (addressId) REFERENCES Address (addressId);


ALTER TABLE UserDistrict ADD CONSTRAINT FK_UserDistrict_0 FOREIGN KEY (districtId) REFERENCES District (districtId);
ALTER TABLE UserDistrict ADD CONSTRAINT FK_UserDistrict_1 FOREIGN KEY (userId) REFERENCES User (userId);


ALTER TABLE VoteRecord ADD CONSTRAINT FK_VoteRecord_0 FOREIGN KEY (userId) REFERENCES User (userId);
ALTER TABLE VoteRecord ADD CONSTRAINT FK_VoteRecord_1 FOREIGN KEY (ballotId) REFERENCES Ballot (ballotId);
ALTER TABLE VoteRecord ADD CONSTRAINT FK_VoteRecord_2 FOREIGN KEY (electionId) REFERENCES Election (electionId);
ALTER TABLE VoteRecord ADD CONSTRAINT FK_VoteRecord_3 FOREIGN KEY (candidateId) REFERENCES Candidate (candidateId);
ALTER TABLE VoteRecord ADD CONSTRAINT FK_VoteRecord_4 FOREIGN KEY (issueId) REFERENCES Issue (issueId);


ALTER TABLE BallotDistrict ADD CONSTRAINT FK_BallotDistrict_0 FOREIGN KEY (districtId) REFERENCES District (districtId);
ALTER TABLE BallotDistrict ADD CONSTRAINT FK_BallotDistrict_1 FOREIGN KEY (ballotId) REFERENCES Ballot (ballotId);


ALTER TABLE CandidateElection ADD CONSTRAINT FK_CandidateElection_0 FOREIGN KEY (electionId) REFERENCES Election (electionId);
ALTER TABLE CandidateElection ADD CONSTRAINT FK_CandidateElection_1 FOREIGN KEY (candidateId) REFERENCES Candidate (candidateId);


ALTER TABLE CandidateParty ADD CONSTRAINT FK_CandidateParty_0 FOREIGN KEY (candidateId) REFERENCES Candidate (candidateId);
ALTER TABLE CandidateParty ADD CONSTRAINT FK_CandidateParty_1 FOREIGN KEY (partyId) REFERENCES Party (partyId);
