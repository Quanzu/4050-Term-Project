package edu.uga.cs.evote.entity.impl;

import java.util.ArrayList;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.persistence.impl.Persistent;

public class ElectoralDistrictImpl extends Persistent implements ElectoralDistrict {

	private String name;
	private List<Ballot> ballots;
	private List<Voter> voters;
	
	public ElectoralDistrictImpl() {
		super(-1);
		name = null;
		ballots = null;
		voters = null;
	}
	
	public ElectoralDistrictImpl(String name) {
		super(-1);
		this.name = name;
		ballots = null;
		voters = null;
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
		if( isPersistent() ) {
			Voter voter = new VoterImpl();
			voter.setElectoralDistrict(this);
            voters = getPersistencaLayer().restoreVoter( voter );
        }
        else
            throw new EVException( "This electoral district object is not persistent" );
		
		return voters;
	}

	@Override
	public List<Ballot> getBallots() throws EVException {
		return ballots;
	}

	@Override
	public void addBallot(Ballot ballot) throws EVException {
		ballots.add(ballot);
	}

	@Override
	public void deleteBallot(Ballot ballot) throws EVException {
		ballots.remove(ballot);
	}

}
