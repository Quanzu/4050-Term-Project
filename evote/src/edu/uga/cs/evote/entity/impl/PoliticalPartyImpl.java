package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class PoliticalPartyImpl extends Persistent implements PoliticalParty {

	private String name;
	
	public PoliticalPartyImpl() {
		name = null;
	}
	
	public PoliticalPartyImpl(String name) {
		this.name = name;
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
	public List<Candidate> getCandidates() throws EVException {
		// TODO Auto-generated method stub
		return null;
	}

}
