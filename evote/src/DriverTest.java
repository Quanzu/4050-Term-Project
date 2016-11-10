import java.sql.*;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.*;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.object.impl.ObjectLayerImpl;
import edu.uga.cs.evote.persistence.PersistenceLayer;
import edu.uga.cs.evote.persistence.impl.DbUtils;
import edu.uga.cs.evote.persistence.impl.PersistenceLayerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
public class DriverTest {

	public static void main(String[] args) throws EVException {
		
		System.out.println("HI");
		Connection conn = null;
		PersistenceLayer persistence;
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "abcd1234");
		prop.setProperty("useSSL", "false");
		prop.setProperty("autoReconnect", "true");
		try{
			conn = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/eVote", prop);
					//"jdbc:mysql://localhost:3306/evote","root","sdafsd");
					//DbUtils.connect();
		}
		catch(Exception seq){
			System.err.println("DeleteTest: Unable to obtain a database connection");
			
		}
		if (conn == null)
		{
			System.out.println("DeleteTest: failed to connect to the database");
			return;
		}
		
		ObjectLayer test = new ObjectLayerImpl();
		persistence = new PersistenceLayerImpl(conn, test);
		test.setPersistence(persistence);
		
		ElectionsOfficer officer1 = test.createElectionsOfficer("Phi", "Nguyen", "pnguyen", "abcd1234", "philong@uga.edu", "123 Main St");
		ElectionsOfficer officer2 = test.createElectionsOfficer("John", "Smith", "jsmith", "1234abcd", "jsmith@uga.edu", "321 Main St");
		test.storeElectionsOfficer(officer1);
		test.storeElectionsOfficer(officer2);
		
		
		ElectoralDistrict electoralDistrict1 = test.createElectoralDistrict("District 9");
		test.storeElectoralDistrict(electoralDistrict1);
		
		Voter voter1 = test.createVoter("Michael", "Jordan", "MJ23", "mjpass", "mj@jumpman.com", "123 Jordan Ave", 60);
		Voter voter2 = test.createVoter("Steve", "Jobs", "appleguy", "apple123", "steve@apple.com", "123 Silicon Valley", 60);
		test.storeVoter(voter1);
		test.storeVoter(voter2);
		
		
		test.getPersistence().storeVoterBelongsToElectoralDistrict(voter1, electoralDistrict1);
		test.getPersistence().storeVoterBelongsToElectoralDistrict(voter2, electoralDistrict1);
		
		
		PoliticalParty party1 = test.createPoliticalParty("Republican");
		PoliticalParty party2 = test.createPoliticalParty("Democrat");
		test.storePoliticalParty(party1);
		test.storePoliticalParty(party2);
		
		

		
		
		
	}
}