package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class IssueImpl extends BallotItemImpl implements Issue {

	private String question;
	private int yesCount;
	
	public IssueImpl() {
		question = null;
		yesCount = 0;
	}
	
	public IssueImpl(String question){
		this.question = question;
		yesCount = 0;
	}

	@Override
	public String getQuestion() {
		return question;
	}

	@Override
	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public int getYesCount() {
		return yesCount;
	}

	@Override
	public void setYesCount(int yesCount) throws EVException {
		this.yesCount = yesCount;
	}

	@Override
	public int getNoCount() {
		return getVoteCount() - getYesCount();
	}

	@Override
	public void addYesVote() {
		this.yesCount++;
		addVote();
	}

	@Override
	public void addNoVote() {
		addVote();
	}

}
