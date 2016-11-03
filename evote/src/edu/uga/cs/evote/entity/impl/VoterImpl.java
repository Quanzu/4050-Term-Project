package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;

public class VoterImpl extends UserImpl implements Voter {

	private int age;
	private ElectoralDistrict electoralDistrict;
	
	public VoterImpl() {
		super();
		age = 0;
		electoralDistrict = null;
	}
	
	public VoterImpl(String fname, String lname, String userName, String password, String email,
			String address, int age){
		super(fname, lname, userName, password, email, address);
		this.age = age;
		electoralDistrict = null;
	}

	@Override
	public String getVoterId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVoterId(String voterId) {
		// TODO Auto-generated method stub
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
		//TODO
		return null;
	}

}
