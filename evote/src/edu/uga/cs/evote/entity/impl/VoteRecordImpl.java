package edu.uga.cs.evote.entity.impl;

import java.util.Date;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class VoteRecordImpl extends Persistent implements VoteRecord {

	public VoteRecordImpl() {
		// TODO Auto-generated constructor stub
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
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public Voter getVoter() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVoter(Voter voter) throws EVException {
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

}
