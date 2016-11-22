package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;

public class UpdatePPCtrl {

	private ObjectLayer objectLayer = null;
	
	public UpdatePPCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
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
        
        objectLayer.storePoliticalParty( party );
		
		return party.getId();
	}
}
