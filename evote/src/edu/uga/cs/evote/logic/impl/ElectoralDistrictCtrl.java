package edu.uga.cs.evote.logic.impl;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class ElectoralDistrictCtrl {

private ObjectLayer objectLayer = null;
	
	public ElectoralDistrictCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long createED(String districtName)
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
	
	
	public long updateED(String districtName, String newName)
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
        {
        	district.setName(newName);
        }
            //throw new EVException( "A district with this name already exists" );
        
        //district = objectLayer.createElectoralDistrict(districtName);
        objectLayer.storeElectoralDistrict( district );
		
		return district.getId();
	}
	
	public List<ElectoralDistrict> findAllElectoralDistrict() throws EVException{
		List<ElectoralDistrict> electoralDistricts = null;
		
		electoralDistricts = objectLayer.findElectoralDistrict(null);
		return electoralDistricts;
	}
	
	public long deleteED(String districtName) throws EVException
	{
		ElectoralDistrict district = null;
		ElectoralDistrict modelDistrict = null;
        List<ElectoralDistrict> districts = null;

        // check if the name already exists
        modelDistrict = objectLayer.createElectoralDistrict();
        modelDistrict.setName(districtName);
        districts = objectLayer.findElectoralDistrict(modelDistrict);
        if( districts.size() > 0 )
            district = districts.get(0);
        
        // check if the party actually exists, and if so, throw an exception
        if( district != null )
        {
            objectLayer.deleteElectoralDistrict( district );
        }
        
		return district.getId();
	}
}
