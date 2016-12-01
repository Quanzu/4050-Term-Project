package edu.uga.cs.evote.logic.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class BallotCtrl {

private ObjectLayer objectLayer = null;
	
	public BallotCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long createBallot(Date openDate, Date closeDate)
			throws EVException
	{
		
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
        
        modelBallot = objectLayer.createBallot();
        modelBallot.setOpenDate(openDate);
        modelBallot.setCloseDate(closeDate);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
        	ballot = ballots.get( 0 );
        
        if( ballot != null )
        {
        	throw new EVException( "A district with this name already exists" );
        	
        }
        else	
        {
        	ballot = objectLayer.createBallot(openDate, closeDate, null);
        	
        }
        objectLayer.storeBallot(ballot);
        return ballot.getId();
	}
	
	
	public long updateBallot(Date openDate, Date closeDate, String id)
			throws EVException
	{
		
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
        int tempid = Integer.parseInt(id);
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        modelBallot.setId(tempid);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
            ballot = ballots.get( 0 );
        
        // check if the ballot actually exists
        if( ballot != null )
        {
        	if (openDate != null)
        	{
        		ballot.setOpenDate(openDate);
        	}
        	
        	if (closeDate != null)
        	{
        		ballot.setCloseDate(closeDate);
        	}
        	
        	objectLayer.storeBallot( ballot );
        }
            //throw new EVException( "A district with this name already exists" );
        
        //district = objectLayer.createElectoralDistrict(districtName);
        
		
		return ballot.getId();
	}
	
	public List<Ballot> findAllBallot() throws EVException{
		List<Ballot> ballots = null;
		
		ballots = objectLayer.findBallot(null);
		return ballots;
	}
	
	public long deleteBallot(String theId) throws EVException
	{
		Ballot ballot = null;
		Ballot modelBallot = null;
        List<Ballot> ballots = null;
        int tempid = Integer.parseInt(theId);
        
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        //gets the candidate in candidate
        modelBallot.setId(tempid);
        
        ballots = objectLayer.findBallot(modelBallot);
        if( ballots.size() > 0 )
            ballot = ballots.get(0);
        
        // check if the candidate actually exists, and if so, throw an exception
        if( ballot != null )
        {
        	
        	objectLayer.deleteBallot( ballot );
        }
		return ballot.getId();
	}
}
