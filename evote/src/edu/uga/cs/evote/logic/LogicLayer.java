package edu.uga.cs.evote.logic;

import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.session.Session;

public interface LogicLayer {
	public String eoLogin(Session session, String userName, String password) throws EVException;
	public String voterLogin(Session session, String userName, String password) throws EVException;
	public void logout(String ssid) throws EVException;
	public String addVoter(Session session, String fname, String lname, String uname, String pword, 
		String email, String address, int age ) throws EVException;
	public long createED(String districtName) throws EVException;
	public List<ElectoralDistrict> findAllElectoralDistrict() throws EVException;

}
