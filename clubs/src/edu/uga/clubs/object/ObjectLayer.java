// Gnu Emacs C++ mode:  -*- Java -*-
//
// Interface:	ObjectModel
//
// K.J. Kochut
//
//
//

package edu.uga.clubs.object;

import java.util.Date;
import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.persistence.PersistenceLayer;


public interface ObjectLayer {

    // manage a Person
    public Person                     createPerson( String userName, String password, String email, String firstName, 
                                                    String lastName, String address, String phone );
    public Person                     createPerson();
    public List<Person>               findPerson( Person modelPerson ) throws ClubsException;
    public void                       storePerson( Person person ) throws ClubsException;
    public void                       deletePerson( Person person ) throws ClubsException;
    
    // manage a Club
    public Club                       createClub( String name, String address, Date establishedOn, Person founder ) throws ClubsException;
    public Club                       createClub();
    public List<Club>                 findClub( Club modelClub ) throws ClubsException;
    public void                       storeClub( Club club ) throws ClubsException;
    public void                       deleteClub( Club club ) throws ClubsException;

    // manage a Membership association class
    public Membership                 createMembership( Person person, Club club, Date date ) throws ClubsException;
    public Membership                 createMembership();
    public List<Membership>           findMembership( Membership modelMembership ) throws ClubsException;
    public void                       storeMembership( Membership membership ) throws ClubsException;
    public void                       deleteMembership( Membership membership ) throws ClubsException;

    // manage an EstablishedBy association link (one-to-many association)
    //public void                       createFounderFoundedClub( Club club, Person person ) throws ClubsException;
    //public Person                     findFounderFoundedClub( Club club ) throws ClubsException;
    //public Iterator<Club>             findFounderFoundedClub( Person person ) throws ClubsException;
    // the delete method should not be provided -- a Club must have a founder (the multiplicity is 1)
    //public void                     deleteFounderFoundedClub( Club club, Person person ) throws ClubsException;
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // these are intended for use by the persistence subsystem only
    public void                       setPersistence( PersistenceLayer persistence );
  
};

  
