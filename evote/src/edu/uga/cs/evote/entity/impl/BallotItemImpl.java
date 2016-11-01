package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.persistence.impl.Persistent;

public abstract class BallotItemImpl extends Persistent implements BallotItem {

	private int voteCount;
	private Ballot ballot;

	@Override
	public int getVoteCount() {
		return this.voteCount;
	}

	@Override
	public void setVoteCount(int voteCount) throws EVException {
		this.voteCount = voteCount;
	}

	@Override
	public void addVote() {
		this.voteCount++;
	}

	@Override
	public Ballot getBallot() throws EVException {
		return this.ballot;
	}

	@Override
	public void setBallot(Ballot ballot) throws EVException {
		this.ballot = ballot;
	}

}
