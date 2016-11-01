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

	@Override
	public long getId() {
		return super.getId();
	}

	@Override
	public void setId(long id) {
		super.setId(id);
	}

	@Override
	public boolean isPersistent() {
		return getId() >= 0;
	}

}
