package edu.uga.cs.evote.logic.impl;

import java.util.Date;
import java.util.List;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.BallotItem;
import edu.uga.cs.evote.entity.Election;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.Ballot;
import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.Issue;
import edu.uga.cs.evote.entity.PoliticalParty;
import edu.uga.cs.evote.object.ObjectLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class BallotCtrl {

private ObjectLayer objectLayer = null;
	
	public BallotCtrl(ObjectLayer objectLayer){
		this.objectLayer = objectLayer;
	}
	
	public long createBallot(Date openDate, Date closeDate, String districtName)
			throws EVException
	{
		
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
        ElectoralDistrict district = null;
        ElectoralDistrict modelDistrict = null;
        List<ElectoralDistrict> districts = null;

        
        modelBallot = objectLayer.createBallot();
        modelBallot.setOpenDate(openDate);
        modelBallot.setCloseDate(closeDate);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
        	ballot = ballots.get( 0 );
        
        if( ballot != null )
        {
        	throw new EVException( "A district with this name already exists" );
        	
        }
        else	
        {
        	
            // check if the name already exists
            modelDistrict = objectLayer.createElectoralDistrict();
            modelDistrict.setName(districtName);
            districts = objectLayer.findElectoralDistrict( modelDistrict );
            if( districts.size() > 0 )
                district = districts.get( 0 );
            
            // check if the person actually exists, and if so, throw an exception
            if( district != null )
            {
            	ballot = objectLayer.createBallot(openDate, closeDate, district);
            	
            }
            
        	
        }
        objectLayer.storeBallot(ballot);
        objectLayer.getPersistence().storeElectoralDistrictHasBallotBallot(district, ballot);
        return ballot.getId();
	}
	
	public void addIssue(long id, String[] theIssues) throws EVException
	{
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
        Date openDate = null;
        Date closeDate = null;
        
        //Ballot Items
        BallotItem issue = null;
        Issue modelIssue = null;
        List<Issue> issues = null;
        
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        modelBallot.setId(id);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
            ballot = ballots.get( 0 );
        
        //If ballot exists, add issues to it
        if (ballot != null)
        {
        	
        	//check issues exist. if so add
        	
        	for (int i = 0; i < theIssues.length; i++)
        	{
        		modelIssue = objectLayer.createIssue();
            	modelIssue.setQuestion(theIssues[i]);
            	issues = objectLayer.findIssue(modelIssue);
            	if( issues.size() > 0 )
                    issue = issues.get( 0 );
            	if (issue != null)
            		objectLayer.getPersistence().storeBallotIncludesBallotItem(ballot, issue);
        	}
        	
        }
	
	}
	
	public void addElection(long id, String[] theElections) throws EVException
	{
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
       
        
        //Ballot Items
        BallotItem election = null;
        Election modelElection = null;
        List<Election> elections = null;
        
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        modelBallot.setId(id);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
            ballot = ballots.get( 0 );
        
        //If ballot exists, add issues to it
        if (ballot != null)
        {
        	
        	//check election exist. if so add
        	
        	for (int i = 0; i < theElections.length; i++)
        	{
        		modelElection = objectLayer.createElection();
            	modelElection.setOffice(theElections[i]);
            	elections = objectLayer.findElection(modelElection);
            	if( elections.size() > 0 )
                    election = elections.get( 0 );
            	if (election != null)
            		objectLayer.getPersistence().storeBallotIncludesBallotItem(ballot, election);
        	}
        	
        }
	
	}
	
	public Ballot findBallot(long id) throws EVException 
	{
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
        
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        modelBallot.setId(id);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
            ballot = ballots.get( 0 );
     
        return ballot;
	}
	
	public long updateBallot(Date openDate, Date closeDate, String id)
			throws EVException
	{
		
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
        int tempid = Integer.parseInt(id);
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        modelBallot.setId(tempid);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
            ballot = ballots.get( 0 );
        
        // check if the ballot actually exists
        if( ballot != null )
        {
        	if (openDate != null)
        	{
        		ballot.setOpenDate(openDate);
        	}
        	
        	if (closeDate != null)
        	{
        		ballot.setCloseDate(closeDate);
        	}
        	
        	objectLayer.storeBallot( ballot );
        }
            //throw new EVException( "A district with this name already exists" );
        
        //district = objectLayer.createElectoralDistrict(districtName);
        
		
		return ballot.getId();
	}
	
	public List<Ballot> findAllBallot() throws EVException{
		List<Ballot> ballots = null;
		
		ballots = objectLayer.findBallot(null);
		return ballots;
	}
	
	public List<Ballot> getCurrentBallot() throws EVException{
		List<Ballot> currentBallots = null;
		List<Ballot> allBallots = this.findAllBallot();
		
		Date closeDate;
		Date date1 = new Date();
	
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	      // Date dateobj = new Date();
	       df.format(date1);
	       //System.out.println(df.format(dateobj));
		
		for (int i = 0; i < allBallots.size(); i++)
		{
			closeDate = allBallots.get(i).getCloseDate();
			if (closeDate.after(date1))
			{
				currentBallots.add(allBallots.get(i));
			}
		}
		
		//getting current date and time using Date class
	       

	       /*getting current date time using calendar class 
	        * An Alternative of above*/
	       
	       
		
		
		return currentBallots;
	}
	
	public long deleteBallot(String theId) throws EVException
	{
		Ballot ballot = null;
		Ballot modelBallot = null;
        List<Ballot> ballots = null;
        int tempid = Integer.parseInt(theId);
        
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        //gets the candidate in candidate
        modelBallot.setId(tempid);
        
        ballots = objectLayer.findBallot(modelBallot);
        if( ballots.size() > 0 )
            ballot = ballots.get(0);
        
        // check if the candidate actually exists, and if so, throw an exception
        if( ballot != null )
        {
        	
        	objectLayer.deleteBallot( ballot );
        }
		return ballot.getId();
	}
	
	public void removeElection(long id, String[] theElections) throws EVException
	{
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
       
        
        //Ballot Items
        BallotItem election = null;
        Election modelElection = null;
        List<Election> elections = null;
        
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        modelBallot.setId(id);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
            ballot = ballots.get( 0 );
        
        //If ballot exists, add issues to it
        if (ballot != null)
        {
        	
        	//check election exist. if so add
        	
        	for (int i = 0; i < theElections.length; i++)
        	{
        		modelElection = objectLayer.createElection();
            	modelElection.setOffice(theElections[i]);
            	elections = objectLayer.findElection(modelElection);
            	if( elections.size() > 0 )
                    election = elections.get( 0 );
            	if (election != null)
            		objectLayer.getPersistence().deleteBallotIncludesBallotItem(ballot, election);
        	}
        	
        }
	
	}
	
	
	public void removeIssue(long id, String[] theIssues) throws EVException
	{
		Ballot ballot = null;
        Ballot modelBallot = null;
        List<Ballot> ballots = null;
        Date openDate = null;
        Date closeDate = null;
        
        //Ballot Items
        BallotItem issue = null;
        Issue modelIssue = null;
        List<Issue> issues = null;
        
        // check if the name already exists
        modelBallot = objectLayer.createBallot();
        modelBallot.setId(id);
        ballots = objectLayer.findBallot( modelBallot );
        if( ballots.size() > 0 )
            ballot = ballots.get( 0 );
        
        //If ballot exists, add issues to it
        if (ballot != null)
        {
        	
        	//check issues exist. if so add
        	
        	for (int i = 0; i < theIssues.length; i++)
        	{
        		modelIssue = objectLayer.createIssue();
            	modelIssue.setQuestion(theIssues[i]);
            	issues = objectLayer.findIssue(modelIssue);
            	if( issues.size() > 0 )
                    issue = issues.get( 0 );
            	if (issue != null)
            		objectLayer.getPersistence().deleteBallotIncludesBallotItem(ballot, issue);
        	}
        	
        }
	
	}
}
