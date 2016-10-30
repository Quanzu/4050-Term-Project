CLASSESDIR=./lib

javac -d classes -cp ${CLASSESDIR}/mysql-connector-java-5.1.33-bin.jar:${CLASSESDIR}/freemarker.jar:${CLASSESDIR}/jboss-servlet-api_3.1_spec-1.0.0.Final.jar src/edu/uga/clubs/ClubsException.java src/edu/uga/clubs/logic/*.java src/edu/uga/clubs/logic/impl/*.java src/edu/uga/clubs/entity/*.java src/edu/uga/clubs/entity/impl/*.java src/edu/uga/clubs/object/*.java src/edu/uga/clubs/object/impl/*.java src/edu/uga/clubs/persistence/*.java src/edu/uga/clubs/persistence/impl/*.java src/edu/uga/clubs/presentation/*.java src/edu/uga/clubs/session/*.java src/edu/uga/clubs/test/object/*.java
