package edu.uga.cs.evote.entity.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class BallotImpl extends Persistent implements Ballot {

	private Date openDate;
	private Date closeDate;
	private ElectoralDistrict electoralDistrict;
	private List<BallotItem> ballotItems = new ArrayList<BallotItem>();
	
	public BallotImpl() {
		openDate = null;
		closeDate = null;
		electoralDistrict = null;
		ballotItems = null;
	}
	
	public BallotImpl(Date openDate, Date closeDate, ElectoralDistrict electoralDistrict){
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.electoralDistrict = electoralDistrict;
	}

	@Override
	public Date getOpenDate() {
		return openDate;
	}

	@Override
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@Override
	public Date getCloseDate() {
		return closeDate;
	}

	@Override
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@Override
	public ElectoralDistrict getElectoralDistrict() throws EVException {
		return electoralDistrict;
	}

	@Override
	public void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		this.electoralDistrict = electoralDistrict;
	}

	@Override
	public List<BallotItem> getBallotItems() throws EVException {
		return ballotItems;
	}

	@Override
	public void addBallotItem(BallotItem ballotItem) throws EVException {
		ballotItems.add(ballotItem);
	}

	@Override
	public void deleteBallotItem(BallotItem ballotItem) throws EVException {
		ballotItems.remove(ballotItem);
	}

	@Override
	public List<VoteRecord> getVoterVoteRecords() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

}
