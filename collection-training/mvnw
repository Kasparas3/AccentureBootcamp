#!/bin/sh
BASE_DIR=$(cd "$(dirname "$0")"; pwd -P)
WRAPPER_JAR="$BASE_DIR/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_MAIN=org.apache.maven.wrapper.MavenWrapperMain

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Maven wrapper JAR not found at $WRAPPER_JAR"
  exit 1
fi

exec java -classpath "$WRAPPER_JAR" "$WRAPPER_MAIN" "$@"
