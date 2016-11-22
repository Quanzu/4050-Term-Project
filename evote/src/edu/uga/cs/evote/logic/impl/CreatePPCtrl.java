package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;

public class CreatePPCtrl {
	private ObjectLayer objectLayer = null;
	
	public CreatePPCtrl(ObjectLayer objectLayer){
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
        parties = objectLayer.findPoliticalParty( modelParty );
        if( parties.size() > 0 )
            party = parties.get( 0 );
        
        // check if the person actually exists, and if so, throw an exception
        if( party != null )
            throw new EVException( "A district with this name already exists" );
        
        party = objectLayer.createPoliticalParty(partyName);
        objectLayer.storePoliticalParty( party );
		
		return party.getId();
	}
}
