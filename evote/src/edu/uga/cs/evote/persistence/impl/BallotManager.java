package edu.uga.cs.evote.persistence.impl;

import java.util.List;
import edu.uga.cs.evote.entity.VoteRecord;
import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.ElectoralDistrict;


public class BallotManager {
    public void storeBallotIncludesBallotItem( Ballot ballot, BallotItem ballotItem ) throws EVException{
    	//TODO
    }
    
    public Ballot restoreBallotIncludesBallotItem( BallotItem ballotItem ) throws EVException{
    	//TODO
    	return null;
    }

    public List<BallotItem> restoreBallotIncludesBallotItem( Ballot ballot ) throws EVException{
    	//TODO
    	return null;
    }
    
    public ElectoralDistrict restoreElectoralDistrictHasBallotBallot( Ballot ballot ) throws EVException{
    	//TODO
    	return null;
    }

    
    public void deleteBallotIncludesBallotItem( Ballot ballot, BallotItem ballotItem ) throws EVException{
    	//TODO
    }

}
