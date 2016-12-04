package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;

public class VoterImpl extends UserImpl implements Voter {

	private int age;
	private long voterId;
	private ElectoralDistrict electoralDistrict;
	private List<VoteRecord> voteRecords;
	
	public VoterImpl() {
		super();
		this.age = 0;
		this.electoralDistrict = null;
		this.voteRecords = null;
	}
	
	public VoterImpl(String fname, String lname, String userName, String password, String email,
			String address, int age){
		super(fname, lname, userName, password, email, address);
		this.age = age;
		this.electoralDistrict = null;
		this.voteRecords = null;
	}

	@Override
	public long getVoterId() {
		return voterId;
	}

	@Override
	public void setVoterId(long voterId) {
		this.voterId = voterId;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
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
	public List<VoteRecord> getBallotVoteRecords() throws EVException {
		if( isPersistent() ) {
			VoteRecord voteRecord = new VoteRecordImpl();
			voteRecord.setVoter(this);
            voteRecords = getPersistenceLayer().restoreVoteRecord( voteRecord );
        }
        else
            throw new EVException( "This voter object is not persistent" );
		
		return voteRecords;
	}

}
