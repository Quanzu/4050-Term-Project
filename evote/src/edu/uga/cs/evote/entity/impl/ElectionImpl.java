package edu.uga.cs.evote.entity.impl;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;

public class ElectionImpl extends BallotItemImpl implements Election {

	private String office;
	private boolean isPartisan;
	private boolean alternateAllowed;

	List<Candidate> candidates = new ArrayList<Candidate>();
	
	public ElectionImpl() {
		office = null;
		isPartisan = false;
		alternateAllowed = false;
	}
	
	public ElectionImpl(String office, boolean isPartisan){
		this.office = office;
		this.isPartisan = isPartisan;
		alternateAllowed = false;
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

	public void setIsPartisan(boolean isPartisan) {
		this.isPartisan = isPartisan;
	}
	
	@Override
	public boolean getAlternateAllowed() {
		return alternateAllowed;
	}

	@Override
	public void setAlternateAllowed(boolean alternateAllowed) {
		this.alternateAllowed = alternateAllowed;
	}
	
	@Override
	public List<Candidate> getCandidates() throws EVException {
		if(candidates == null)
			if(isPersistent()){
				candidates = getPersistenceLayer().restoreCandidateIsCandidateInElection(this);
			} else
	            throw new EVException( "This election object is not persistent" );
			
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
