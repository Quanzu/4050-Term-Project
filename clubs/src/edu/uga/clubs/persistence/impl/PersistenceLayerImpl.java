package edu.uga.clubs.persistence.impl;

import java.sql.Connection;
import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.object.ObjectLayer;
import edu.uga.clubs.persistence.PersistenceLayer;

public class PersistenceLayerImpl 
    implements PersistenceLayer
{
    private PersonManager personManager = null;
    private ClubManager clubManager = null;
    private MembershipManager membershipManager = null;
    
    public PersistenceLayerImpl( Connection conn, ObjectLayer objectLayer )
    {
        personManager = new PersonManager( conn, objectLayer );
        clubManager = new ClubManager( conn, objectLayer );
        membershipManager = new MembershipManager( conn, objectLayer );
        System.out.println( "PersistenceLayerImpl.PersistenceLayerImpl(conn,objectLayer): initialized" );
    }
    
    public void storeClub(Club club) throws ClubsException
    {
        clubManager.store( club );
    }

    public List<Club> restoreClub(Club modelClub) throws ClubsException
    {
        return clubManager.restore( modelClub );
    }

    public void deleteClub(Club club) throws ClubsException
    {
        clubManager.delete( club );
    }

    public void storePerson(Person person) throws ClubsException
    {
        personManager.store( person );
    }

    public List<Person> restorePerson(Person modelPerson) 
            throws ClubsException
    {
        return personManager.restore( modelPerson );
    }

    public void deletePerson(Person person) throws ClubsException
    {
        personManager.delete( person );
    }

    public void storeMembership(Membership membership) throws ClubsException
    {
        membershipManager.store( membership );
    }

    public List<Membership> restoreMembership(Membership modelMembership)
            throws ClubsException
    {
        return membershipManager.restore( modelMembership );
    }

    public void deleteMembership(Membership membership) throws ClubsException
    {
        membershipManager.delete( membership );
    }

    public Person restoreClubEstablishedByFounder(Club club) throws ClubsException
    {
        return clubManager.restoreClubEstablishedByPerson( club );
    }

    public List<Club> restoreClubEstablishedByFounder(Person person)
            throws ClubsException
    {
        return personManager.restoreClubEstablishedByPerson( person );
    }

    public void storeClubEstablishedByFounder(Club club, Person person)
            throws ClubsException
    {
        if( person == null )
            throw new ClubsException( "The club's founder is null" );
        if( !person.isPersistent() )
            throw new ClubsException( "The club's founder is not persistent" );
        // create a new proxy with the new founder
        club.setPersonFounder( person );
        //club = objectModel.createClub( club.getName(), club.getAddress(), club.getEstablishedOn(), person );
        //club.setId( club.getId() );
        clubManager.store( club );
    }

}
