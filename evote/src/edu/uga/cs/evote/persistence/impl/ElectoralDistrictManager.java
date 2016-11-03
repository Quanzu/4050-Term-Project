package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.object.ObjectLayer;

class ElectoralDistrictManager
{
    private ObjectLayer objectLayer = null;
    private Connection  conn = null;
    
    public ElectoralDistrictManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void store( ElectoralDistrict district ) 
            throws EVException
    {
        String               insertDistrictSql = "insert into ElectoralDistrict ( districtName ) values ( ? )";              
        String               updateDistrictSql = "update ElectoralDistrict set districtName = ? where districtId = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 districtId;
        
        try {
            
            if( !district.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertDistrictSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateDistrictSql );
            
            if( district.getName() != null )
                stmt.setString( 1, district.getName() );
            else 
                throw new EVException( "ElectoralDistrictManager.save: can't save a District: name undefined" );
            
            if( district.isPersistent() )
                stmt.setLong( 2, district.getId() );

            inscnt = stmt.executeUpdate();

            if( !district.isPersistent() ) {
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
                            districtId = r.getLong( 1 );
                            if( districtId > 0 )
                                district.setId( districtId ); // set this party's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new EVException( "ElectoralDistrictManager.save: failed to save a district" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new EVException( "ElectoralDistrictManager.save: failed to save a district: " + e );
        }
    }

    public List<ElectoralDistrict> restore( ElectoralDistrict modelElectoralDistrict ) 
            throws EVException
    {
        String       selectDistrictSql = "select districtName from ElectoralDistrict";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        List<ElectoralDistrict> districts = new ArrayList<ElectoralDistrict>();
        
        query.append( selectDistrictSql );
        
        if( modelElectoralDistrict != null ) {
            if( modelElectoralDistrict.getId() >= 0 ) 
                query.append( " where districtId = " + modelElectoralDistrict.getId() );
            else if( modelElectoralDistrict.getName() != null ) 
                query.append( " where districtName = '" + modelElectoralDistrict.getName() + "'" );
            
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person objects
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet rs = stmt.getResultSet();
                long   id;
                String districtName;
                
                while( rs.next() ) {

                    id = rs.getLong( 1 );
                    districtName = rs.getString( 2 );

                    ElectoralDistrict district = objectLayer.createElectoralDistrict( districtName );
                    district.setId( id );

                    districts.add( district );

                }
                
                return districts;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent district object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new EVException( "ElectoralDistrictManager.restore: Could not restore persistent district objects" );
    }

    public List<Ballot> restoreElectoralDistrictHasBallotBallot( ElectoralDistrict electoralDistrict ) throws EVException{
    	//TODO
    	return null;
    }
    
    public List<Voter> restoreVoterBelongsToElectoralDistrict( ElectoralDistrict electoralDistrict ) throws EVException{
    	//TODO
    	return null;
    }
    
    public void delete( ElectoralDistrict district ) 
            throws EVException
    {
        String               deleteDistrictSql = "delete from ElectoralDistrict where districtId = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given Person object instance
        if( !district.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteDistrictSql );
            
            stmt.setLong( 1, district.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new EVException( "ElectoralDistrictManager.delete: failed to delete this district" );
            }
        }
        catch( SQLException e ) {
            throw new EVException( "ElectoralDistrictManager.delete: failed to delete this district: " + e.getMessage() );
        }
    }
}
