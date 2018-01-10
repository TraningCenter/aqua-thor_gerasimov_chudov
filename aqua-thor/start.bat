@ECHO OFF
call mvn clean
call mvn install
call cd target
call java -jar aqua-thor-1.0-SNAPSHOT.jar
pause