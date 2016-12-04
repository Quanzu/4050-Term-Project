package edu.uga.cs.evote.logic.impl;

import java.sql.*;
import java.util.List;
import com.mysql.jdbc.PreparedStatement;
import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.persistence.impl.VoterDistrictManager;
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
    	
    		String ssid = null;
    		Voter voter = null;
	        Voter modelVoter = null;
	        List<Voter> voters = null;
			Statement statement = null;
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
				statement=conn.createStatement();
				String sql ="SELECT * FROM electoraldistrict where districtName = '" + district + "'";
				resultSet = statement.executeQuery(sql);
				resultSet.getInt("districtId");
				resultSet.getString("districtName");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			VoterDistrictManager vdm = new VoterDistrictManager(conn, objectLayer);
			
			ssid = SessionManager.storeSession(session);

	        return ssid;
	    }
}
