package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class PoliticalPartyCtrl {

private ObjectLayer objectLayer = null;
	
	public PoliticalPartyCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long createPP(String partyName)
			throws EVException
	{
		PoliticalParty party = null;
		PoliticalParty modelParty = null;
        List<PoliticalParty> parties = null;

        // check if the name already exists
        modelParty = objectLayer.createPoliticalParty();
        modelParty.setName(partyName);
        parties = objectLayer.findPoliticalParty(modelParty);
        if( parties.size() > 0 )
            party = parties.get(0);
        
        // check if the person actually exists, and if so, throw an exception
        if( party != null )
            throw new EVException( "A parties with this name already exists" );
        
        party = objectLayer.createPoliticalParty(partyName);
        objectLayer.storePoliticalParty( party );
		
		return party.getId();
	}
	
	public long updatePP(String partyName, String newName)
			throws EVException
	{
		
		PoliticalParty party = null;
		PoliticalParty modelParty = null;
        List<PoliticalParty> parties = null;

        // check if the name already exists
        modelParty = objectLayer.createPoliticalParty();
        modelParty.setName(partyName);
        parties = objectLayer.findPoliticalParty( modelParty );
        if( parties.size() > 0 )
            party = parties.get( 0 );
        
        // check if the person actually exists, and if so, throw an exception
        if( party != null )
        {
        	party.setName(newName);
        }
            //throw new EVException( "A district with this name already exists" );
        
        //district = objectLayer.createElectoralDistrict(districtName);
        objectLayer.storePoliticalParty( party );
		
		return party.getId();
	}
	
	public List<PoliticalParty> findAllPoliticalParty() throws EVException{
		List<PoliticalParty> politicalParties = null;	
		politicalParties = objectLayer.findPoliticalParty(null);
		return politicalParties;
	}
	
	public long deletePP(String partyName) throws EVException
	{
		PoliticalParty party = null;
		PoliticalParty modelParty = null;
        List<PoliticalParty> parties = null;

        // check if the name already exists
        modelParty = objectLayer.createPoliticalParty();
        modelParty.setName(partyName);
        parties = objectLayer.findPoliticalParty(modelParty);
        if( parties.size() > 0 )
            party = parties.get(0);
        
        // check if the party actually exists, and if so, throw an exception
        if( party != null )
        {
            objectLayer.deletePoliticalParty( party );
        }
        
		return party.getId();
	}
}
