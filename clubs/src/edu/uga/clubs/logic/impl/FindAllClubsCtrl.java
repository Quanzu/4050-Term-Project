//
// A control class to implement the 'List all clubs' use case
//
//


package edu.uga.clubs.logic.impl;




import java.util.List;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.entity.Club;
import edu.uga.clubs.object.ObjectLayer;



public class FindAllClubsCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public FindAllClubsCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }

    public List<Club> findAllClubs()
            throws ClubsException
    {
        List<Club> 	clubs  = null;
        
        // retrieve all Club objects
        //
        clubs = objectLayer.findClub( null );

        return clubs;
    }
}
