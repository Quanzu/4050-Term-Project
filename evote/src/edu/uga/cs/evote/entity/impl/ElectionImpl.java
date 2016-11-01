package edu.uga.cs.evote.entity.impl;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class ElectionImpl extends BallotItemImpl implements Election {

	private String office;
	private boolean isPartisan;
	List<Candidate> candidates = new ArrayList<Candidate>();
	
	public ElectionImpl() {
		this.office = null;
	}

	@Override
	public String getOffice() {
		return this.office;
	}

	@Override
	public void setOffice(String office) {
		this.office = office;
	}

	@Override
	public boolean getIsPartisan() {
		return isPartisan;
	}

	@Override
	public void setIsPartisan(boolean isPartisan) {
		this.isPartisan = isPartisan;
	}

	@Override
	public List<Candidate> getCandidates() throws EVException {
		return candidates;
	}

	@Override
	public void addCandidate(Candidate candidate) throws EVException {
		candidates.add(candidate);
	}

	@Override
	public void deleteCandidate(Candidate candidate) throws EVException {
		candidates.remove(candidate);
	}

}
