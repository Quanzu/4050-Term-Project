This is the main directory of a small example system for
maintaining Clubs, Persons, and Club memberships.

The directory structure is as follows:

  src		contains all sources of the system; 
                all sources are in a few sub-packages of edu.uga.clubs
                and include:

		edu/uga/clubs/session          includes the session management package
		edu/uga/clubs/presentation     includes the presentation layer 
                                                 subsystem (boundaries, i.e., servlets)
		edu/uga/clubs/logic            includes the logic layer subsystem code
		edu/uga/clubs/object           includes the object model layer subsystem
		edu/uga/clubs/entity           includes the entity classes (data model)
		edu/uga/clubs/persistence      includes the persistence layer subsystem code
		edu/uga/clubs/test             includes a few test programs

                edu/uga/clubs/ClubsException.java 
                                               is the main exception class

  db		database schema

  lib		includes the necessary jar files (libraries); these
                are used only by the build process and are not
                deployed as part of the Web application.  The
                libraries which are deployed as part of the Web
                application are in the directory WebContent/WEB-INF/lib.

  WebContent	directory for the assembly of the Web Application

  build.xml     Ant build script for the system

  compile.sh    a Shell script to compile the project;  however, it does not build the WAR file

  run-read-test.sh
		a Shell script to run one of the test programs (listing existing objects)


1. Installing and running the complete Web Application

IMPORTANT:  If you would like to experiment with the entity classes, and the
object and persistence layers, as well as executing some test
programs, skip this part and go the point 2 below.

The instructions are for a Unix-based system (or Mac OS X).  To deploy
on MS Windows, you would need to modify some steps.

The system utilizes an Ant build script (build.xml) to build and install this
application.  In order to be able to compile the whole system, examine 
the macro definitions at the top of the build.xml file and update them
as needed.

IMPORTANT: before you can compile and install this example on your
own system, you will have to:

0. Install the Java JDK, the WildFly AppServer, MySQL database, and
   Ant;  if you'd like to experiment on uml, you do not need to
   install these systems, as they are already available on uml.
1. Create a database and install the schema using the SQL script in
   the db directory. 
2. If needed, modify build.xml to update the macro definitions; 
   make sure you change the WAR file name, as it must be different than clubs.war
   and adhere to the naming conventions described on our Web site, 
   if you plan to deply on uml.cs.uga.edu.
3. If needed, modify the HTML forms and templates (with suffix .ftl).
   These files are in in the directory WebContent and in 
   WebContent/WEB-INF/templates.  
4. Update the file src/edu/uga/clubs/persistence/impl/DbAccessConfig.java
   to set the name of your database, database user, and the password, as
   selected in 1. above.


In order to compile and install the system, type:

  $ ant build
  $ ant deploy

Check in the JBoss (WildFly) log file if the application has been
deployed porperly.

To start using the deployed system, connect your browser to
(substitute 'deploymenthost' with the name of the system where you
have the JBoss (WildFly) running and where you deployed the WebApp;
possibly, it may just be localhost, if you are running everything on
your own laptop or desktop):

http://deploymenthost:8080/clubs

or, if you changed the name of the WAR file from clubs.war to
myclubs.war, connect to:

http://deploymenthost:8080/myclubs

The user name for testing is admin and the password is also admin.




2.  Experimenting with the Object and Persistence layers

The instructions are for a Unix-based system (or Mac OS X).  To deploy
on MS Windows, you would need to modify some steps.

You will not need to create a WAR file with the Clubs Web application,
or deploy it to an application server.  However, you will need to
compile the whole system, including the test programs.

Here are the steps to perform:

0. Install the Java JDK and the MySQL database.
   If you'd like to experiment on uml, you do not need to
   install these systems, as they are already available on uml.
1. Create a database and install the schema using the SQL script in
   the db directory. 
2. Compile the system using the compile.sh script
   You may need to modify the compile.sh script to change the setting
   of the CLASSESDIR variable to point to a directoy where you have the
   necessary JAR files (perhaps newer jars).
   Execute:

   $ sh compile.sh

3. Run the test programs
   A simple script to execute one of the test programs is called
   run-read-test.sh.   Execute:

   $ sh run-read-test.sh
