// Gnu Emacs C++ mode:  -*- Java -*-
//
// Interface:	IsMemberOf
//
// K.J. Kochut
//
//
//

package edu.uga.clubs.entity;

import java.util.Date;

import edu.uga.clubs.persistence.Persistable;



// this is an association class;  it has methods to access/modify an object at
// each end of the association this class describes
public interface Membership
  extends Persistable
{  
    // basic getters and setters
    public Date         getJoinedOn();
    public void         setJoinedOn( Date joinedOn );
    
    // association traversals
    public Person	getPerson();
    public void         setPerson( Person person );
    public Club	        getClub();
    public void         setClub( Club cclub );
};


