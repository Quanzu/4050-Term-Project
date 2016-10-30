// Gnu Emacs C++ mode:  -*- Java -*-
//
// Class:	PersonImpl
//
// K.J. Kochut
//
//
//


package edu.uga.clubs.entity.impl;


import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.persistence.impl.Persistent;


// Implementation of the Person class from the UML object model
//
public class PersonImpl
    extends Persistent
    implements Person
{
    // Person attributes
    private String           userName;
    private String           password;
    private String           email;
    private String           firstName;
    private String           lastName;
    private String           address;
    private String           phone;
    private List<Club>       foundedClubs;
    private List<Membership> clubMemberships;

    public PersonImpl()
    {
        super( -1 );
        this.userName = null;
        this.password = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.phone = null;
    }
    
    public PersonImpl( String userName,
                       String password,
                       String email,
                       String firstName, 
                       String lastName,
                       String address, 
                       String phone )
    {
        super( -1 );
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }
    
    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public List<Club> getClubsFounded() 
            throws ClubsException
    {
        if( foundedClubs == null )
            if( isPersistent() ) {
                foundedClubs = getPersistencaLayer().restoreClubEstablishedByFounder( this );
                //System.out.println(  "Person.getClubsFounded: lazy traversal" );
            }
            else
                throw new ClubsException( "This person object is not persistent" );
     
        return foundedClubs;
    }

    public List<Membership> getClubsMembership() 
            throws ClubsException
    {
        if( clubMemberships  == null )
            if( isPersistent() ) {
                Membership membership = new MembershipImpl();
                membership.setPerson( this );
                clubMemberships = getPersistencaLayer().restoreMembership( membership );
                //System.out.println(  "Person.getClubsMembership: lazy traversal" );
            }

        return clubMemberships;
    }

    public String toString()
    {
        return "Person[" + getId() + "] " + getUserName() + " " + getFirstName() + " " + getLastName() + " " 
                + getEmail() + " " + getAddress() + " " + getPhone();
    }

    public boolean equals( Object otherPerson )
    {
        if( otherPerson == null )
            return false;
        if( otherPerson instanceof Person ) 
            return getUserName().equals( ((Person)otherPerson).getUserName() );
        return false;        
    }

};
