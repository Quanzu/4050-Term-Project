package edu.uga.cs.evote.logic;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.session.Session;

public interface LogicLayer {
	public String eoLogin(Session session, String userName, String password) throws EVException;
	public String voterLogin(Session session, String userName, String password) throws EVException;

}
