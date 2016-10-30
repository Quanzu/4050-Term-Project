package edu.uga.clubs.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.object.ObjectLayer;

// This is a manager for an association class.
// This manager is a bit different than regular class managers, as
// each instance represents a link between two (or possibly more)
// object instances.  It uses eager association traversals, and the
// related objects are created right away ("eager" traversal is used).
public class MembershipManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public MembershipManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( Membership membership ) 
            throws ClubsException
    {
        String               insertMembershipSql = "insert into membership ( personid, clubid, joined ) values ( ?, ?, ? )";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 membershipId;
        
        if( membership.getPerson() == null || membership.getClub() == null )
            throw new ClubsException( "MembershipManager.save: Attempting to save a Membership with no Person or Club defined" );
        if( !membership.getPerson().isPersistent() || !membership.getClub().isPersistent() )
            throw new ClubsException( "MembershipManager.save: Attempting to save a Membership where either Person or Club are not persistent" );
                              
        try {
            stmt = (PreparedStatement) conn.prepareStatement( insertMembershipSql );
            
            stmt.setLong( 1, membership.getPerson().getId() );
            stmt.setLong( 2, membership.getClub().getId() );

            if( membership.getJoinedOn() != null ) {
                java.util.Date jDate = membership.getJoinedOn();
                java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                stmt.setDate( 3, sDate );
            }
            else
                stmt.setNull(3, java.sql.Types.DATE);
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt >= 1 ) {
                String sql = "select last_insert_id()";
                if( stmt.execute( sql ) ) { // statement returned a result

                    // retrieve the result
                    ResultSet r = stmt.getResultSet();

                    // we will use only the first row!
                    //
                    while( r.next() ) {

                        // retrieve the last insert auto_increment value
                        membershipId = r.getLong( 1 );
                        if( membershipId > 0 )
                            membership.setId( membershipId ); // set this membership's db id (proxy object)
                    }
                }
            }
            else
                throw new ClubsException( "MembershipManager.save: failed to save a Membership" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new ClubsException( "MembershipManager.save: failed to save a Membership: " + e );
        }

    }

    // for association class objects, we don't use the "lazy" association traversal
    // since each object's only purpose is to represent a link between two (or more) related
    // objects;  hence, these related objects are created right away (eager traversals).
    public List<Membership> restore( Membership membership ) 
            throws ClubsException
    {
        String selectMembershipSql = "select c.name, c.address, c.established, m.id, m.clubid, m.personid, m.joined, "
                                     + "p.username, p.userpass, p.email, p.firstname, p.lastname, p.address, p.phone "
                                     + "from club c, membership m, person p where c.id = m.clubid and m.personid = p.id";              
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );
        List<Membership>  memberships = new ArrayList<Membership>();

        if( membership.getPerson() != null && !membership.getPerson().isPersistent() )
            throw new ClubsException( "MembershipManager.restore: the argument membership includes a non-persistent Person object" );
        if( membership.getClub() != null && !membership.getClub().isPersistent() )
            throw new ClubsException( "MembershipManager.restore: the argument membership includes a non-persistent Club object" ); 
        
        condition.setLength( 0 );
        
        // form the query based on the given Club object instance
        query.append( selectMembershipSql );
        
        if( membership != null ) {
            if( membership.isPersistent() ) // id is unique, so it is sufficient to get a membership
                query.append( " where id = " + membership.getId() );
            else {

                if( membership.getPerson() != null ) {
                    condition.append( " and m.personid = " + membership.getPerson().getId() ); 
                }

                if( membership.getClub() != null ) {
                    condition.append( " and m.clubid = " + membership.getClub().getId() ); 
                }
                
                if( membership.getJoinedOn() != null ) {
                    // fix the date conversion
                    condition.append( " and m.joined = '" + membership.getJoinedOn() + "'" );
                }

                if( condition.length() > 0 )
                    query.append( condition );
            }
        }
        
        try {
            stmt = conn.createStatement();

            // retrieve the persistent Person object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                
                ResultSet rs = stmt.getResultSet();
                              
                long   id;
                Date   joined;
                long   personid;
                String userName;
                String password;
                String email;
                String firstName;
                String lastName;
                String personaddress;
                String phone;
                long   clubid;
                String clubname;
                String clubaddress;
                Date   establishedOn;
                Person person = null;
                Club   club = null;
                Membership nextMembership = null;

                while( rs.next() ) {

                    clubname = rs.getString( 1 );
                    clubaddress = rs.getString( 2 );
                    establishedOn = rs.getDate( 3 );
                    id = rs.getLong( 4 );
                    clubid = rs.getLong( 5 );
                    personid = rs.getLong( 6 );
                    joined = rs.getDate( 7 );
                    userName = rs.getString( 8 );
                    password = rs.getString( 9 );
                    email = rs.getString( 10 );
                    firstName = rs.getString( 11 );
                    lastName = rs.getString( 12 );
                    personaddress = rs.getString( 13 );
                    phone = rs.getString( 14 );
                    
                    // create a Person proxy object
                    person = objectLayer.createPerson( userName, password, email, firstName, lastName, personaddress, phone );
                    person.setId( personid );  
                    
                    // create a proxy club object    
                    club = objectLayer.createClub(); 
                    // and now set its attributes
                    club.setId( clubid );
                    club.setName( clubname );
                    club.setAddress( clubaddress );
                    club.setEstablishedOn( establishedOn );
                    // set this to null for the "lazy" association traversal
                    club.setPersonFounder( null );

                    // now, create a Membership object
                    nextMembership = objectLayer.createMembership( person, club, joined );
                    nextMembership.setId( id );
                    
                    memberships.add( nextMembership );
                }
                    
                return memberships;
 
            }
        }
        catch( Exception e ) {      // just in case...
            throw new ClubsException( "MembershipManager.restore: Could not restore persistent Membership objects; Root cause: " + e );
        }

        // if we reach this point, it's an error
        throw new ClubsException( "MembershipManager.restore: Could not restore persistent Membership objects" );
    }

    public void delete(Membership membership) 
            throws ClubsException
    {
        String               deleteMembershipSql = "delete from membership where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
             
        if( !membership.isPersistent() ) // is the Membership object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteMembershipSql );          
            stmt.setLong( 1, membership.getId() );
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 1 ) {
                return;
            }
            else
                throw new ClubsException( "MembershipManager.delete: failed to delete a Membership" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new ClubsException( "MembershipManager.delete: failed to delete a Membership: " + e );        }
    }
}
