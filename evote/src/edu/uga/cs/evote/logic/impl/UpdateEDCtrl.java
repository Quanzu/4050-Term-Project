package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.object.ObjectLayer;

public class UpdateEDCtrl {

private ObjectLayer objectLayer = null;
	
	public UpdateEDCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long updateED(String districtName)
			throws EVException
	{
		ElectoralDistrict district = null;
        ElectoralDistrict modelDistrict = null;
        List<ElectoralDistrict> districts = null;

        // check if the name already exists
        modelDistrict = objectLayer.createElectoralDistrict();
        modelDistrict.setName(districtName);
        districts = objectLayer.findElectoralDistrict( modelDistrict );
        if( districts.size() > 0 )
            district = districts.get( 0 );
        
        // check if the person actually exists, and if so, throw an exception
        if( district != null )
            throw new EVException( "A district with this name already exists" );
        
        district = objectLayer.createElectoralDistrict(districtName);
        objectLayer.storeElectoralDistrict( district );
		
		return district.getId();
	}
}
