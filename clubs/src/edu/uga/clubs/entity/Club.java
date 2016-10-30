// Gnu Emacs C++ mode:  -*- Java -*-
//
// Interface:	Club
//
// K.J. Kochut
//
//
//

package edu.uga.clubs.entity;



import java.util.Date;
import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.persistence.Persistable;



public interface Club
    extends Persistable
{  
    // basic getters and setters
    public String           getName();
    public void             setName( String name );
    public String           getAddress();
    public void             setAddress( String address );
    public Date		    getEstablishedOn();
    public void             setEstablishedOn( Date established );

    // association traversals
    public Person           getPersonFounder() throws ClubsException;
    public void             setPersonFounder( Person founder ) throws ClubsException;  
    
    public List<Membership> getPersonsMembership() throws ClubsException;
    //public void             setPersonsMembership( List<Membership> personMemberships ) throws ClubsException;
};
