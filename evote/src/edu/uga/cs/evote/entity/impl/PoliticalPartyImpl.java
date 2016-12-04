package edu.uga.cs.evote.entity.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class PoliticalPartyImpl extends Persistent implements PoliticalParty {

	private String name;
	private List<Candidate> candidates;
	
	public PoliticalPartyImpl() {
		super(-1);
		name = null;
		candidates = null;
	}
	
	public PoliticalPartyImpl(String name) {
		super(-1);
		this.name = name;
		this.candidates = null;
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
		if(candidates == null)
			if( isPersistent() ) {
	            candidates = getPersistenceLayer().restoreCandidateIsMemberOfPoliticalParty(this);
	        }
	        else
	            throw new EVException( "This political party object is not persistent" );
		
		return candidates;
	}

}
