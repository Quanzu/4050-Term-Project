// Gnu Emacs C++ mode:  -*- Java -*-
//
// Class:	ClubImpl
//
// K.J. Kochut
//
//
//

package edu.uga.clubs.entity.impl;


import java.util.Date;
import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.persistence.impl.Persistent;


public class ClubImpl
    extends Persistent
    implements Club
{
    // Club attributes
    private String           name;  
    private String           address;
    private Date             establishedOn;
    private Person           founder;
    private List<Membership> personMemberships;

    // when creating a brand new Club object, the founder must already be persistent
    //
    public ClubImpl() 
    {
        super( -1 );
        this.name = null;
        this.address = null;
        this.establishedOn = null;
        this.founder = null;
        this.personMemberships = null;
    }
    
    // when creating a brand new Club object, the founder must already be persistent
    //
    public ClubImpl( String name, String address, Date established, Person founder ) 
    {
        super( -1 );
        /*
        if( founder == null )
            throw new ClubsException( "The club's founder is null" );
        if( !founder.isPersistent() )
            throw new ClubsException( "The club's founder is not persistent" );
            */
        this.name = name;
        this.address = address;
        this.establishedOn = established;
        this.founder = founder;
        this.personMemberships = null;
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
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

    public Date getEstablishedOn()
    {
        return establishedOn;
    }

    public void setEstablishedOn( Date establishedOn )
    {
        this.establishedOn = establishedOn;
    }

    public Person getPersonFounder() 
            throws ClubsException
    {
        if( founder == null )
            if( isPersistent() ) {
                founder = getPersistencaLayer().restoreClubEstablishedByFounder( this );
                //System.out.println(  "Club.getPersonFounder: lazy traversal" );
            }
            else
                throw new ClubsException( "This club object is not persistent" );

        return founder;
    }

    public void setPersonFounder( Person founder )
            throws ClubsException
    {
        /*
        if( founder == null )
            throw new ClubsException( "The founder is null" );
        if( !founder.isPersistent() )
            throw new ClubsException( "The founder is not persistent" );
        */
        this.founder = founder;
    }

    public List<Membership> getPersonsMembership() 
            throws ClubsException
    {
        if( personMemberships == null )
            if( isPersistent() ) {
                Membership membership = new MembershipImpl();
                membership.setClub( this );
                personMemberships = getPersistencaLayer().restoreMembership( membership );
                //System.out.println(  "Club.getPersonsMembership: lazy traversal" );
            }
            else
                throw new ClubsException( "This club object is not persistent" );
        
        return personMemberships;
    }

    public String toString()
    {
        return "Club[" + getId() + "] " + getName() + " " + getAddress() + " " + getEstablishedOn();
    }
    
    public boolean equals( Object otherClub )
    {
        if( otherClub == null )
            return false;
        if( otherClub instanceof Club ) // name is a unique attribute
            return getName().equals( ((Club)otherClub).getName() );
        return false;        
    }

};
