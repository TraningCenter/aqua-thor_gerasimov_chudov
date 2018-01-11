@ECHO OFF
call mvn clean
call mvn install
call cd target
call javaw -jar aqua-thor-1.0-SNAPSHOT-jar-with-dependencies.jar
pause