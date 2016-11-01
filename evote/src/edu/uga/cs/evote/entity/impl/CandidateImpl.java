package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class CandidateImpl extends Persistent implements Candidate {

	private String name;
	private PoliticalParty politicalParty;
	private Election election;
	
	private int voteCount = 0;
	
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
	public int getVoteCount() {
		return this.voteCount;
	}

	@Override
	public void setVoteCount(int voteCount) throws EVException {
		this.voteCount = voteCount;
	}

	@Override
	public void addVote() {
		this.voteCount++;
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
