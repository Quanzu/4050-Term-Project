package edu.uga.cs.evote.entity.impl;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;

public class ElectionImpl extends BallotItemImpl implements Election {

	private String office;
	private boolean isPartisan;
	List<Candidate> candidates = new ArrayList<Candidate>();
	
	public ElectionImpl() {
		office = null;
		isPartisan = false;
	}
	
	public ElectionImpl(String office, boolean isPartisan){
		this.office = office;
		this.isPartisan = isPartisan;
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
