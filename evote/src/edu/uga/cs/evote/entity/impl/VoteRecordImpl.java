package edu.uga.cs.evote.entity.impl;

import java.util.Date;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class VoteRecordImpl extends Persistent implements VoteRecord {

	private Ballot ballot;
	private Voter voter;
	private Date date;
	
	public VoteRecordImpl() {
		super(-1);
		ballot = null;
		voter = null;
		date = null;
	}

	public VoteRecordImpl(Ballot ballot, Voter voter, Date date){
		super(-1);
		this.ballot = ballot;
		this.voter = voter;
		this.date = date;
	}
	
	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Voter getVoter() throws EVException {
		return voter;
	}

	@Override
	public void setVoter(Voter voter) throws EVException {
		this.voter = voter;
	}

	@Override
	public Ballot getBallot() throws EVException {
		return ballot;
	}

	@Override
	public void setBallot(Ballot ballot) throws EVException {
		this.ballot = ballot;
	}

}
