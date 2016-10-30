//
// A control class to implement the 'Join a club' use case
//
//


package edu.uga.clubs.logic.impl;




import java.util.Date;
import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.object.ObjectLayer;



public class JoinClubCtrl {
    
    private ObjectLayer objectLayer = null;

    public JoinClubCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }

    public long joinClub( long personId, String clubName )
            throws ClubsException
    {
        Person               person = null;
        Person               modelPerson = null;
        List<Person>         persons = null;
        Club                 club = null;
        Club                 modelClub = null;
        List<Club>           clubs = null;
        Membership           membership = null;
        Membership           modelMembership = null;
        List<Membership>     memberships = null;

        modelClub = objectLayer.createClub();
        modelClub.setName( clubName );
        clubs = objectLayer.findClub( modelClub );
        if( clubs.size() > 0 ) {
            club = clubs.get( 0 );
            System.out.println( "CtrlJoinClub.joinClub: found club: " + club );
        }
        if( club == null )
            throw new ClubsException( "Club does not exist; name: " + clubName );

        modelPerson = objectLayer.createPerson();
        modelPerson.setId( personId );
        persons = objectLayer.findPerson( modelPerson );
        if( persons.size() > 0 ) {
            person = persons.get( 0 );
            System.out.println( "CtrlJoinClub.joinClub: found person: " + person );
        }
        if( person == null )
            throw new ClubsException( "Person does not exist; id: " + personId );

        modelMembership = objectLayer.createMembership();
        modelMembership.setPerson( person );
        modelMembership.setClub( club );
        memberships = objectLayer.findMembership( modelMembership );
        if( memberships.size() > 0 )
            throw new ClubsException( "This person is already a member of this club" );

        membership = objectLayer.createMembership( person, club, new Date() );
        objectLayer.storeMembership( membership );

        return membership.getId();
    }
}
