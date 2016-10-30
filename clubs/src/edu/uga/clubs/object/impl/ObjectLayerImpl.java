package edu.uga.clubs.object.impl;

import java.util.Date;
import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.entity.impl.ClubImpl;
import edu.uga.clubs.entity.impl.MembershipImpl;
import edu.uga.clubs.entity.impl.PersonImpl;
import edu.uga.clubs.object.ObjectLayer;
import edu.uga.clubs.persistence.PersistenceLayer;


public class ObjectLayerImpl 
    implements ObjectLayer
{
    PersistenceLayer persistence = null;
    
    public ObjectLayerImpl()
    {
        this.persistence = null;
        System.out.println( "ObjectLayerImpl.ObjectLayerImpl(): initialized" );
    }
    
    public ObjectLayerImpl( PersistenceLayer persistence )
    {
        this.persistence = persistence;
        System.out.println( "ObjectLayerImpl.ObjectLayerImpl(persistence): initialized" );
    }
    
    public Club createClub()
    {
        ClubImpl club = new ClubImpl( null, null, null, null );
        club.setId( -1 );
        club.setPersistencaLayer( persistence);
        return club;
    } 
    
    public Club createClub(String name, String address, Date establishedOn, Person founder)
    {
        ClubImpl club = new ClubImpl( name, address, establishedOn, founder );
        club.setPersistencaLayer( persistence);
        return club;
    }
    
    public List<Club> findClub(Club club)
            throws ClubsException
    {
        return persistence.restoreClub( club );
    }

    public void storeClub(Club club)
            throws ClubsException
    {
        persistence.storeClub( club );
    }

    public void deleteClub(Club club)
            throws ClubsException
    {
        persistence.deleteClub( club );
    }
    
    public Person createPerson()
    {
        PersonImpl person = new PersonImpl( null, null, null, null, null, null, null );
        person.setId( -1 );
        person.setPersistencaLayer( persistence);
        return person;
    }

    public Person createPerson( String userName, String password, String email,
                                String firstName, String lastName, String address, String phone)
    {
        PersonImpl person = new PersonImpl( userName, password, email, firstName, lastName, address, phone);
        person.setPersistencaLayer( persistence);
        return person;
    }
    
    public List<Person> findPerson(Person person)
            throws ClubsException
    {
        return persistence.restorePerson( person );
    }

    public void storePerson(Person person)
            throws ClubsException
    {
        persistence.storePerson( person );
    }

    public void deletePerson(Person person)
            throws ClubsException
    {
        persistence.deletePerson( person );
    }
    
    public Membership createMembership()
    {
        MembershipImpl membership = new MembershipImpl(null, null, null );
        membership.setPersistencaLayer( persistence);
        return membership;
    }
    
    public Membership createMembership(Person person, Club club, Date date)
            throws ClubsException
    {
        MembershipImpl membership = new MembershipImpl(person, club, date );
        membership.setPersistencaLayer( persistence);
        return membership;
    }

    public List<Membership> findMembership(Membership modelMembership)
        throws ClubsException
    {
        return persistence.restoreMembership( modelMembership );
    }

    public void storeMembership(Membership membership)
            throws ClubsException
    {
        persistence.storeMembership( membership );
    }

    public void deleteMembership(Membership membership)
            throws ClubsException
    {
        persistence.deleteMembership( membership );
    }
    
    public void setPersistence( PersistenceLayer persistence )
    {
        this.persistence = persistence;        
    }
    
    /*
    public void createFounderFoundedClub( Club club, Person person )
            throws ClubsException
    {
        persistence.storeClubEstablishedByFounder( club, person );
    }
    
    public Person findFounderFoundedClub( Club club ) throws ClubsException
    {
        return persistence.restoreClubEstablishedByFounder( club );
    }

    public Iterator<Club> findFounderFoundedClub( Person person )
            throws ClubsException
    {
        return persistence.restoreClubEstablishedByFounder( person );
    }
    */

}
