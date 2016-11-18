package edu.uga.cs.evote.persistence.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import edu.uga.cs.evote.EVException;;

/**
 * This class performs database operations.
 */
public class DbUtils {

    // private static Logger log = SessionManager.getLog();

    /**
     * Disables the auto commit for the current connection  
     * @param conn  the open connection
     */
    public static void disableAutoCommit( Connection conn ) 
            throws EVException 
    {
        try {
            conn.setAutoCommit(false);
        } 
        catch( SQLException ex ) {
            // log.error( "DbUtils.disableAutoCommit: SQLException on setting auto-commit false ", ex );
            throw new EVException( "DbUtils.disableAutoCommit: Transaction error. " + ex.getMessage() );
        }
    }

    /**
     * Enable the auto commit for the current connection  
     * @param conn  the open connection
     */
    public static void enableAutoCommit( Connection conn ) 
            throws EVException 
    {
        try {
            conn.setAutoCommit(true);
        } 
        catch( SQLException ex ) {
            // log.error( "DbUtils.enableAutoCommit: SQLException on setting auto-commit true ", ex );
            throw new EVException( "DbUtils.enableAutoCommit: Transaction error. " + ex.getMessage() );
        }
    }

    /**
     * Commit the transactions for the current connection  
     * @param conn  the open connection
     */
    public static void commit(Connection conn) 
            throws EVException 
    {
        try {
            conn.commit();
        } catch (SQLException ex) {
            // log.error("DbUtils.commit: SQLException on commit ", ex);
            throw new EVException( "DbUtils.commit: SQLException on commit " + ex.getMessage() );
        }
    }

    /**
     * rollback the transactions for the current connection  
     * @param conn  the open connection
     */
    public static void rollback(Connection conn) 
            throws EVException 
    {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            // log.error( "DbUtils.rollback: SQLException on rollback ", ex );
            throw new EVException( "DbUtils.rollback: Unable to rollback transaction. " + ex.getMessage() );
        }
    }

    /**
     * Establish a JDBC connection to the database.
     * @return  a database connection object
     * @throws  GVException
     */
    public static Connection connect() 
            throws EVException 
    {
    	/*
    	try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}

        try {
            Class.forName(DbAccessConfig.DB_DRIVE_NAME);
        } 
        catch (ClassNotFoundException ex) {
            // log.error( "DbUtils.connect:  unable to find JDBC Driver", ex );
            throw new EVException( "DbUtils.connect: Unable to find Driver" );
        }
*/
        try {
    		Properties prop = new Properties();
    		prop.setProperty("user", DbAccessConfig.DB_CONNECTION_USERNAME);
    		prop.setProperty("password", DbAccessConfig.DB_CONNECTION_PWD);
    		prop.setProperty("useSSL", "false");
    		prop.setProperty("autoReconnect", "true");
            return DriverManager.getConnection( DbAccessConfig.DB_CONNECTION_URL,
                                                prop );
        } 
        catch (SQLException ex) {
            // log.error( "DbUtils.connect: Unable to connect to database", ex );
            throw new EVException( "DbUtils.connect: Unable to connect to database " + ex.getMessage() );
        }
    }

}
