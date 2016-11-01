package edu.uga.cs.evote.entity.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class BallotImpl extends Persistent implements Ballot {

	
	
	public BallotImpl() {
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
	public Date getOpenDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOpenDate(Date openDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getCloseDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCloseDate(Date closeDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public ElectoralDistrict getElectoralDistrict() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BallotItem> getBallotItems() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBallotItem(BallotItem ballotItem) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBallotItem(BallotItem ballotItem) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<VoteRecord> getVoterVoteRecords() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

}
