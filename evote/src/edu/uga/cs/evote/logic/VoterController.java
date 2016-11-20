package edu.uga.cs.evote.logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.impl.ElectionsOfficerImpl;
import edu.uga.cs.evote.entity.impl.VoterImpl;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.DbUtils;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;

public class VoterController {
	
	static Connection conn = null;
	static PersistenceLayer persist;
	
	public static void add(VoterImpl voter) throws EVException{
		
		
		try{
			conn = DbUtils.connect();
		}
		catch(Exception seq){
			System.err.println("Fail to connect to database");
		}
		
		ObjectLayer test = new ObjectLayerImpl();
		test.createVoter(voter.getFirstName(), voter.getLastName(), voter.getUserName(), 
				voter.getPassword(), voter.getEmailAddress(), voter.getAddress(), voter.getAge());
		
	}
}
