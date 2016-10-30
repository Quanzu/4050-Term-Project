package edu.uga.clubs.test.object;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.entity.Membership;
import edu.uga.clubs.entity.Person;
import edu.uga.clubs.object.ObjectLayer;
import edu.uga.clubs.object.impl.ObjectLayerImpl;
import edu.uga.clubs.persistence.PersistenceLayer;
import edu.uga.clubs.persistence.impl.DbUtils;
import edu.uga.clubs.persistence.impl.PersistenceLayerImpl;

public class ReadTest
{
    public static void main(String[] args)
    {
         Connection  conn = null;
         ObjectLayer objectLayer = null;
         PersistenceLayer persistence = null;

         // get a database connection
         try {
             conn = DbUtils.connect();
         } 
         catch(Exception seq) {
             System.err.println( "ReadTest: Unable to obtain a database connection" );
         }
         
         if( conn == null ) {
             System.out.println( "ReadTest: failed to connect to the database" );
             return;
         }
         
         // obtain a reference to the ObjectModel module      
         objectLayer = new ObjectLayerImpl();
         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceLayerImpl( conn, objectLayer ); 
         // connect the ObjectModel module to the Persistence module
         objectLayer.setPersistence( persistence );   
                  
         try {
             
             System.out.println( "Club objects:" );
             List<Club> clubs = objectLayer.findClub( null );
             for( Club club : clubs ) {
                 System.out.println( club );
                 Person founder = club.getPersonFounder();
                 System.out.println( "   Founded by: " + founder );
                 System.out.println( "   Members: " );
                 List<Membership> memberships = club.getPersonsMembership();
                 for( Membership membership : memberships )
                     System.out.println( "      " + membership.getPerson() );
             }
             
             System.out.println( "Person objects:" );
             List<Person> persons = objectLayer.findPerson( null );
             for( Person person : persons ) {
                 System.out.println( person );
                 System.out.print( "   Founder of: " );
                 clubs = person.getClubsFounded();
                 for( Club club : clubs ) {
                     System.out.print( club + " " );                     
                 }
                 System.out.println();System.out.flush();
                 System.out.println( "   Member of: " );
                 List<Membership> memberships = person.getClubsMembership();
                 for( Membership membership : memberships ) {
                     System.out.println( "      " + membership.getClub() );
                 }                 
             }

         }
         catch( ClubsException ce)
         {
             System.err.println( "ClubsException: " + ce );
         }
         catch( Exception e)
         {
             System.out.flush();
             System.err.println( "Exception: " + e );
         }
         finally {
             // close the connection!!!
             try {
                 conn.close();
             }
             catch( Exception e ) {
                 System.err.println( "Exception: " + e );
             }
         }
    }   
}
