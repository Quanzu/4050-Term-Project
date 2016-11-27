package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.object.ObjectLayer;

public class FindAllElectoralDistrictCtrl {
	
	private ObjectLayer objectLayer = null;
	
	public FindAllElectoralDistrictCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public List<ElectoralDistrict> findAllElectoralDistrict() throws EVException{
		List<ElectoralDistrict> electoralDistricts = null;
		
		electoralDistricts = objectLayer.findElectoralDistrict(null);
		return electoralDistricts;
	}
}
