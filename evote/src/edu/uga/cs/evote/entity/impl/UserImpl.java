package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.entity.User;
import edu.uga.cs.evote.persistence.impl.Persistent;

public abstract class UserImpl extends Persistent implements User {

	private String 		fname;
	private String 		lname;
	private String		userName;
	private String		password;
	private String		email;
	private String		address;
	
	public UserImpl(){
		super(-1);
		this.fname = null;
		this.lname = null;
		this.userName = null;
		this.password = null;
		this.email = null;
		this.address = null;
	}
	
	public UserImpl(String fname, String lname, String userName, String password, String email, String address){
		super(-1);
		this.fname = fname;
		this.lname = lname;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.address = address;
	}
	
	@Override
	public String getFirstName() {
		return fname;
	}

	@Override
	public void setFirstName(String firstName) {
		fname = firstName;
	}

	@Override
	public String getLastName() {
		return lname;
	}

	@Override
	public void setLastName(String lastName) {
		lname = lastName;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getEmailAddress() {
		return email;
	}

	@Override
	public void setEmailAddress(String emailAddress) {
		email = emailAddress;
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
	}
}
