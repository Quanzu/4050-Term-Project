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

	private Date openDate;
	private Date closeDate;
	private ElectoralDistrict electoralDistrict;
	private List<BallotItem> ballotItems;
	private List<VoteRecord> voteRecords;
	
	public BallotImpl() {
		super(-1);
		openDate = null;
		closeDate = null;
		electoralDistrict = null;
		ballotItems = null;
	}
	
	public BallotImpl(Date openDate, Date closeDate, ElectoralDistrict electoralDistrict){
		super(-1);
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.electoralDistrict = electoralDistrict;
		ballotItems = null;
		voteRecords = null;
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
		if(electoralDistrict == null)
			if(isPersistent()){
				electoralDistrict = getPersistencaLayer().restoreElectoralDistrictHasBallotBallot(this);
			} else
	            throw new EVException( "This ballot object is not persistent" );
		
		return electoralDistrict;
	}

	@Override
	public void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		this.electoralDistrict = electoralDistrict;
	}

	@Override
	public List<BallotItem> getBallotItems() throws EVException {
		if(ballotItems == null)
			if(isPersistent()){
				ballotItems = getPersistencaLayer().restoreBallotIncludesBallotItem(this);
			} else
	            throw new EVException( "This ballot object is not persistent" );
		
		return ballotItems;	}

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
		if(voteRecords == null)
			if( isPersistent() ) {
				VoteRecord voteRecord = new VoteRecordImpl();
				voteRecord.setBallot(this);
				voteRecords = getPersistencaLayer().restoreVoteRecord( voteRecord );
			}
	        else
	            throw new EVException( "This ballot object is not persistent" );
		
		return voteRecords;
	}

}
