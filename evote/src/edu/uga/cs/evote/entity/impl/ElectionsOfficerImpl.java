package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.entity.ElectionsOfficer;

public class ElectionsOfficerImpl extends UserImpl implements ElectionsOfficer {
	
	public ElectionsOfficerImpl() {
		super.setFirstName(null);
		super.setLastName(null);
		super.setUserName(null);
		super.setPassword(null);
		super.setEmailAddress(null);
		super.setAddress(null);
	}
	
	public ElectionsOfficerImpl(String fname, String lname, String userName, String password, String email, String address) {
		super.setFirstName(fname);
		super.setLastName(lname);
		super.setUserName(userName);
		super.setPassword(password);
		super.setEmailAddress(email);
		super.setAddress(address);
	}
	
	@Override
	public String getFirstName() {
		return super.getFirstName();
	}

	@Override
	public void setFirstName(String firstName) {
		super.setFirstName(firstName);
	}

	@Override
	public String getLastName() {
		return super.getLastName();
	}

	@Override
	public void setLastName(String lastName) {
		super.setLastName(lastName);
	}

	@Override
	public String getUserName() {
		return super.getUserName();
	}

	@Override
	public void setUserName(String userName) {
		super.setUserName(userName);;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(password);;
	}

	@Override
	public String getEmailAddress() {
		return super.getEmailAddress();
	}

	@Override
	public void setEmailAddress(String emailAddress) {
		super.setEmailAddress(emailAddress);;
	}

	@Override
	public String getAddress() {
		return super.getAddress();
	}

	@Override
	public void setAddress(String address) {
		super.setAddress(address);
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
