package edu.uga.cs.evote.logic.impl;

import java.sql.*;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class VoterRegCtrl {

	private ObjectLayer objectLayer = null;
    
    public VoterRegCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
	
	
    public String addVoter( Session session, String fname, String lname, String userName, String password, 
    		String email, String address, int age, String district ) throws EVException{
    	 	int inscnt;
    		String ssid = null;
    		Voter voter = null;
	        Voter modelVoter = null;
	        List<Voter> voters = null;
			Statement statement = null;
			PreparedStatement prep = null;
			ResultSet resultSet = null;
	        Connection conn = session.getConnection();
	        
	        // check if the uname already exists
	        modelVoter = objectLayer.createVoter();
	        modelVoter.setUserName(userName);
	        voters = objectLayer.findVoter( modelVoter );
	        if( voters.size() > 0 )
	            voter = voters.get( 0 );
	        
	        // check if the person actually exists, and if so, throw an exception
	        if( voter != null )
	            throw new EVException( "A person with this user name already exists" );
	        
	        voter = objectLayer.createVoter( fname, lname, userName, password, email, address, age);
	        objectLayer.storeVoter( voter );
			session.setUser(voter);
			

			try{ 
				statement = conn.createStatement();
				String electoralDistrictSql ="SELECT * FROM electoraldistrict where districtName = '" + district + "'";
				resultSet = statement.executeQuery(electoralDistrictSql);
				if(resultSet.next()){
					String insertVoterDistrictSql = "insert into VoterDistrict (districtId, voterId ) values ( ?, ?)";
					prep = (PreparedStatement) conn.prepareStatement(insertVoterDistrictSql);
					int electoralDistrictId = resultSet.getInt(1);
					prep.setInt(1, electoralDistrictId);
					prep.setInt(2, (int) voter.getId());
					inscnt = prep.executeUpdate();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			

			
			
			
			
			ssid = SessionManager.storeSession(session);

	        return ssid;
	    }
}
