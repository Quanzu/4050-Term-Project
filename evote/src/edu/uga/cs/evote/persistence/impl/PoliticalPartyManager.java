package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Candidate;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;

class PoliticalPartyManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public PoliticalPartyManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( PoliticalParty party ) 
            throws EVException
    {
        String               insertPartySql = "insert into Party ( partyName ) values ( ? )";              
        String               updatePartySql = "update Party set partyName = ? where partyId = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 partyId;
        
        try {
            
            if( !party.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertPartySql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updatePartySql );
            
            if( party.getName() != null )
                stmt.setString( 1, party.getName() );
            else 
                throw new EVException( "PoliticalPartyManager.save: can't save a Party: name undefined" );
            
            if( party.isPersistent() )
                stmt.setLong( 2, party.getId() );

            inscnt = stmt.executeUpdate();

            if( !party.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( inscnt == 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            partyId = r.getLong( 1 );
                            if( partyId > 0 )
                                party.setId( partyId ); // set this party's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "PoliticalPartyManager.save: failed to save a party" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "PoliticalParty.save: failed to save a party: " + e );
        }
    }

    public List<PoliticalParty> restore( PoliticalParty modelPoliticalParty ) 
            throws EVException
    {
        String       selectPartySql = "select partyName from Party";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        List<PoliticalParty> parties = new ArrayList<PoliticalParty>();
                
        // form the query based on the given Person object instance
        query.append( selectPartySql );
        
        if( modelPoliticalParty != null ) {
            if( modelPoliticalParty.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " where partyId = " + modelPoliticalParty.getId() );
            else if( modelPoliticalParty.getName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " where partyName = '" + modelPoliticalParty.getName() + "'" );
            
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String partyName;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    partyName = rs.getString( 2 );

                    PoliticalParty party = objectLayer.createPoliticalParty( partyName );
                    party.setId( id );

                    parties.add( party );

                }
                
                return parties;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "PoliticalPartyManager.restore: Could not restore persistent party object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "PoliticalPartyManager.restore: Could not restore persistent voter objects" );
    }
    
    public List<Candidate> restoreCandidateIsMemberOfPoliticalParty( PoliticalParty politicalParty ) throws EVException{
    	//TODO
    	return null;
    }
    
    public void delete( PoliticalParty party ) 
            throws EVException
    {
        String               deletePartySql = "delete from person where partyId = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !party.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deletePartySql );
            
            stmt.setLong( 1, party.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "PoliticalPartyManager.delete: failed to delete this party" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "PoliticalPartyManager.delete: failed to delete this party: " + e.getMessage() );
        }
    }
}
