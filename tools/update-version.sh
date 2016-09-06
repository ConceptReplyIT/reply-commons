#!/bin/bash

# This script will automatically updated the project version through Maven Version Plugin
# Just pass the new version number along

MAVEN="mvn"
BASE_PATH="../"

echo "Reply automatic Maven version update"
echo "----------------------------------"
echo

set -e
# Any subsequent command which fails will cause the shell script to exit immediately 

usage ()
{
  echo 'Usage : Script <VERSION> [-mvn <maven_path>]'
  exit -1
}

if [ "$#" -le 0 ]
then
  usage
fi
if [ "$#" -ge 4 ]
then
  usage
fi

while [ "$1" != "" ]; do
case $1 in
		-mvn )         shift
                       MAVEN=$1
                       ;;
        * )            VERSION=$1
    esac
    shift
done

# extra validation 
if [ "$VERSION" = "" ]
then
    usage
fi
if [ "$MAVEN" = "" ]
then
    usage
fi

echo
echo "Updating reply-commons version to $VERSION:"
echo

$MAVEN versions:set -DnewVersion=$VERSION -DgenerateBackupPoms=false -f $BASE_PATH"pom.xml"
$MAVEN install -f $BASE_PATH"pom.xml"

echo
echo "--Update complete--"