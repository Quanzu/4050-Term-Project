package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;

public class VoterImpl extends UserImpl implements Voter {

	private int age;
	
	public VoterImpl() {
		super.setFirstName(null);
		super.setLastName(null);
		super.setUserName(null);
		super.setPassword(null);
		super.setEmailAddress(null);
		super.setAddress(null);
		age = 0;
	}
	
	public VoterImpl(String fname, String lname, String userName, String password, String email,
			String address, int age){
		super.setFirstName(fname);
		super.setLastName(lname);
		super.setUserName(userName);
		super.setPassword(password);
		super.setEmailAddress(email);
		super.setAddress(address);
		this.age = age;
	}

	@Override
	public long getId() {
		return super.getId();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<VoteRecord> getBallotVoteRecords() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

}
