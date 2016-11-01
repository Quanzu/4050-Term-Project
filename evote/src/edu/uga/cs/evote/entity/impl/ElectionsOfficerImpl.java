package edu.uga.cs.evote.entity.impl;

import edu.uga.cs.evote.entity.ElectionsOfficer;

public class ElectionsOfficerImpl extends UserImpl implements ElectionsOfficer {
	
	public ElectionsOfficerImpl() {
		super();
	}
	
	public ElectionsOfficerImpl(String fname, String lname, String userName, String password, String email, String address) {
		super(fname, lname, userName, password, email, address);
	}
}
