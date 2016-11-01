package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class ElectionImpl extends BallotItemImpl implements Election {

	private String office;
	private boolean isPartisan;
	
	public ElectionImpl() {
		this.office = null;
	}

	@Override
	public String getOffice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOffice(String office) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getIsPartisan() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsPartisan(boolean isPartisan) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Candidate> getCandidates() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCandidate(Candidate candidate) throws EVException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCandidate(Candidate candidate) throws EVException {
		// TODO Auto-generated method stub

	}

}
