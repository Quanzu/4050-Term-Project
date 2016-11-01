package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class IssueImpl extends Persistent implements Issue {

	public IssueImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getVoteCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVoteCount(int voteCount) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addVote() {
		// TODO Auto-generated method stub

	}

	@Override
	public Ballot getBallot() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBallot(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setQuestion(String question) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getYesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setYesCount(int yesCount) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNoCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addYesVote() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNoVote() {
		// TODO Auto-generated method stub

	}

}
