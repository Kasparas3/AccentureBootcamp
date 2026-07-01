@ECHO OFF
SETLOCAL
SET WRAPPER_JAR="%~dp0.mvn\wrapper\maven-wrapper.jar"
SET WRAPPER_MAIN=org.apache.maven.wrapper.MavenWrapperMain

IF NOT EXIST %WRAPPER_JAR% (
  ECHO Maven wrapper JAR not found at %WRAPPER_JAR%
  EXIT /B 1
)

java -classpath %WRAPPER_JAR% %WRAPPER_MAIN% %*
ENDLOCAL
