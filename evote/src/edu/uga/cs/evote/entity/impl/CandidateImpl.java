package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class CandidateImpl extends BallotItemImpl implements Candidate {

	private String name;
	private boolean isAlternate;
	private PoliticalParty politicalParty;
	private Election election;
		
	public CandidateImpl() {
		name = null;
		politicalParty = null;
		election = null;
	}
	
	public CandidateImpl(String name, PoliticalParty politicalParty, Election election){
		this.name = name;
		this.politicalParty = politicalParty;
		this.election = election;
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
	public boolean getIsAlternate(){
		return this.isAlternate;
	}
	
	@Override
	public void setIsAlternate(boolean isAlternate){
		this.isAlternate = isAlternate;
	}
	
	@Override
	public Election getElection() throws EVException {
		return election;
	}

	@Override
	public void setElection(Election election) throws EVException {
		this.election = election;
	}

	@Override
	public PoliticalParty getPoliticalParty() throws EVException {
		return politicalParty;
	}

	@Override
	public void setPoliticalParty(PoliticalParty politicalParty) throws EVException {
		this.politicalParty = politicalParty;
	}

}
