// Gnu Emacs C++ mode:  -*- Java -*-
//
// Interface:	Person
//
// K.J. Kochut
//
//
//


package edu.uga.clubs.entity;

import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.persistence.Persistable;



// Interface to Person representing the Person class from the UML object model
//
public interface Person
    extends Persistable
{  
    // basic getters and setters 
    public String           getUserName();
    public void             setUserName( String userName );
    public String           getPassword();
    public void             setPassword( String password );
    public String           getEmail();
    public void             setEmail( String password );
    public String           getFirstName();
    public void             setFirstName( String firstName );
    public String	    getLastName();
    public void             setLastName( String lastName );
    public String	    getAddress();
    public void             setAddress( String address );
    public String	    getPhone();
    public void	            setPhone( String phone );
    
    // association traversals
    public List<Membership> getClubsMembership() throws ClubsException;

    public List<Club>       getClubsFounded() throws ClubsException;
    // the following two methods are not needed, since these links will be
    // modified in the Club interface by changing a Club's founder
    // public void           addFoundedClub( Club club );
    // public void           deleteFoundedClub( Club club );
    
};
