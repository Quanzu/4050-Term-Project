package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class ElectoralDistrictImpl extends Persistent implements ElectoralDistrict {

	private String name;
	
	public ElectoralDistrictImpl() {
		name = null;
	}
	
	public ElectoralDistrictImpl(String name) {
		this.name = name;
	}

	@Override
	public long getId() {
		return super.getId();
	}

	@Override
	public void setId(long id) {
		super.setId(id);
	}

	@Override
	public boolean isPersistent() {
		return getId() >= 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Voter> getVoters() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ballot> getBallots() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBallot(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBallot(Ballot ballot) throws EVException {
		// TODO Auto-generated method stub

	}

}
