package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;

public class FindAllPoliticalPartyCtrl {
	private ObjectLayer objectLayer = null;
	
	public FindAllPoliticalPartyCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public List<PoliticalParty> findAllPoliticalParty() throws EVException{
		List<PoliticalParty> politicalParties = null;
		
		politicalParties = objectLayer.findPoliticalParty(null);
		return politicalParties;
	}
}
