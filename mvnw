#!/bin/sh
# Maven Wrapper script for Unix/Linux
MAVEN_OPTS="${MAVEN_OPTS:--Xmx512m}"
exec java $MAVEN_OPTS -jar "$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar" "$@"
