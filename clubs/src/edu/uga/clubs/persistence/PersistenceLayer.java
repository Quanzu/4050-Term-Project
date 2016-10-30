// Gnu Emacs C++ mode:  -*- Java -*-
//
// Interface:	PersistenceModule.java
//
// K.J. Kochut
//
//
//

package edu.uga.clubs.persistence;


import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;

// This class is a version of a Repository Pattern
public interface PersistenceLayer {

  public void                storeClub( Club club ) throws ClubsException;
  public List<Club>          restoreClub( Club club ) throws ClubsException;
  public void		     deleteClub( Club c ) throws ClubsException;
  
  public void                storePerson( Person person ) throws ClubsException;
  public List<Person>        restorePerson( Person person ) throws ClubsException;
  public void                deletePerson( Person person ) throws ClubsException;

  public void                storeMembership( Membership membership ) throws ClubsException;
  public List<Membership>    restoreMembership( Membership membership ) throws ClubsException;
  public void                deleteMembership( Membership membership ) throws ClubsException;

  public void                storeClubEstablishedByFounder( Club club, Person person ) throws ClubsException;
  public Person              restoreClubEstablishedByFounder( Club club ) throws ClubsException;
  public List<Club>          restoreClubEstablishedByFounder( Person person ) throws ClubsException;
  //  the delete operation for the isFounderOf association is not provided, since it is not possible to
  //  have a club with no founder
  //  public void            deleteFounderFoundedClub( Club club, Person person ) throws ClubsException;

};
