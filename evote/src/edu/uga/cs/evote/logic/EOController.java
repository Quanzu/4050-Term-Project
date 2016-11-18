package edu.uga.cs.evote.logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.impl.ElectionsOfficerImpl;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.DbUtils;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;

public class EOController {

	static Connection conn = null;
	static PersistenceLayer persistence;
	
	
	public EOController(){
		
	}
	
	public static ElectionsOfficerImpl login(ElectionsOfficerImpl eo) throws EVException{
		String username = eo.getUserName();
		String password = eo.getPassword();
		
		//testing purposes
		System.out.println("Your user name is " + username);          
	    System.out.println("Your password is " + password);
	      
	    try{
	  		conn =	DbUtils.connect();
	  	}
	  	catch(Exception seq){
	  		System.err.println("DeleteTest: Unable to obtain a database connection " + seq);
	  		
	  	}
	  	if (conn == null)
	  	{
	  		System.out.println("DeleteTest: failed to connect to the database");
	  		//return;
	  	}
	  	
	 
	  	
	  	ObjectLayer test = new ObjectLayerImpl();
	  	persistence = new PersistenceLayerImpl(conn, test);
	  	test.setPersistence(persistence);
	  	/*
	 // if user does not exist set the isValid variable to false
        if (!eo.isPersistent()) 
        {
           System.out.println("Sorry, you are not a registered user! Please sign up first");
        } 
	        
        
        else if (eo.isPersistent()) 
        {
           String firstName = eo.getFirstName();
           String lastName = eo.getLastName();
	     	
           System.out.println("Welcome " + firstName);
           eo.setFirstName(firstName);
           eo.setLastName(lastName);
        }
      	*/
	  	
	  	List<ElectionsOfficer> officer = test.findElectionsOfficer(eo);
	  	if(officer.size() > 0){
	  		System.out.println(officer.get(0).getAddress());
	  	}
	  	return eo;
	}
	
	//need createED method
	public static ElectoralDistrict createED(ElectoralDistrict ed){
		
		//gonna need to actually create the ED by using obj to call the method?
		return ed;
	}
	
}
