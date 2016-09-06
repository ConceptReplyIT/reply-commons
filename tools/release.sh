#!/bin/bash

# This script will perform automatic code releasing operations for the OCP platform,
# including making a release, preparing for a stable branch, increasing Maven version

# Set current dir to script's home
cd "${0%/*}"

MAVEN="mvn"
MASTER_BRANCH="master"
RED=`tput setaf 1`
GREEN=`tput setaf 2`
YELLOW=`tput setaf 3`
RESET=`tput sgr0`
IGNORE_SNAPSHOTS=false
BASE_PATH="../"

echo "Reply-commons automatic release"
echo "----------------------------------"
echo

set -e
# Any subsequent command which fails will cause the shell script to exit immediately 

usage ()
{
  echo 'Usage: release.sh [--release <version> | --stable [<version>] | --increase [<version>]] [--ignore-snapshots] [-mvn <maven_path>]'
  echo 'Usage: release.sh [-r <version> | -s [<version>] | -i [<version>]] [--ignore-snapshots] [-mvn <maven_path>]'
  echo 
  echo 'Arguments:'
  echo '--release, -r	<version>		Makes a release to the given version, performing the necessary checks'
  echo 
  echo '--stable, -s	[<version>]		Prepares for a stable branch with the current version and increments the version to the given one (if not given, the *minor* will be increased)'
  echo 
  echo '--increase, -i	[<version>]		Increments the version to the given one (if not given, the *patch* will be increased)'
  echo '						This option can be specified along with --release, -r, to sum the behaviors'
  echo 
  echo '--ignore-snapshots 				If specified, the release process will allow to release the code with snapshot dependencies (*NOT SAFE FOR REAL RELEASES!*)'
  echo
  echo '-mvn 		<maven_path>		Can be used to specify a Maven executable or to add debug parameters to Maven (i.e. -e for stack trace or -X for full debug log)'
  echo '-base-path	<base_path>			Can be used to specify path to the code, if invoking the script from another directory.'
  exit -1
}

function semverParseInto() {
    local RE='[^0-9]*\([0-9]*\)[.]\([0-9]*\)[.]\([0-9]*\)\([0-9A-Za-z-]*\)'

    #if [[ "$1" =~ $RE ]]; then
    	if [ "$#" -gt 1 ]; then
	    	#MAJOR
		    eval $2=`echo $1 | sed -e "s#$RE#\1#"`
		    #MINOR
		    eval $3=`echo $1 | sed -e "s#$RE#\2#"`
		    #MINOR
		    eval $4=`echo $1 | sed -e "s#$RE#\3#"`
		    #SPECIAL
		    eval $5=`echo $1 | sed -e "s#$RE#\4#"`
		fi
	#else
	#	echo "$1 does not match Semantic Versioning!"
	#	exit -2
	#fi
}

function warningMsg() {
	echo
	echo "${YELLOW} > $1 ${RESET}"
	echo
}

function errorMsg() {
	echo
	echo "${RED} > $1 ${RESET}"
	echo
}

PHASE=1
function phaseMsg() {
	echo
	echo "${GREEN}$PHASE) $1:${RESET}"
	echo "------------------------------------------------------------"
	echo
	PHASE=$((PHASE+1))
}

function checkVersionSnapshot() {
	if [[ "$CURRENT_VERSION" != *-SNAPSHOT ]]
	then
		errorMsg "Current version is not a snapshot; you cannot release anything but a snapshot (version must end with -SNAPSHOT)!"
		exit -2
	fi
}

function checkVersionsDifferent() {
	if [ "$CURRENT_VERSION" = "$VERSION" ] || [ "$CURRENT_VERSION" = "$INCREASE_VERSION" ] || [ "$INCREASE_VERSION" = "$VERSION" ]; then
		errorMsg "New version cannot be the same as current version!"
		exit -2
	fi
}

if [ "$#" -le 0 ]
then
  usage
fi
if [ "$#" -ge 7 ]
then
  usage
fi

while [ "$1" != "" ]; do
case $1 in
		--increase|-i )	INCREASE=true
					   	if [ "$MODE" = "" ]; then 
					   		MODE="i"
					   	fi
					   	if [ "$#" -gt 1 ]; then
							shift
	                       	INCREASE_VERSION=$1
	                       	if [[ "$INCREASE_VERSION" = -* ]]; then continue; fi
	                    fi
                       	;;
        --release|-r )  MODE="r"
						if [ "$#" -le 1 ]; then
							usage
						fi
					   	shift
                       	VERSION=$1
                       	;;
		--stable|-s )   MODE="s"
						if [ "$#" -gt 1 ]; then
						   	shift
	                       	VERSION=$1
	                       	if [[ "$VERSION" = -* ]]; then continue; fi
	                    fi
                       	;;                       
		-mvn )         	shift
                       	MAVEN=$1
                       	;;
        -base-path )    shift
                       	BASE_PATH=$1
                       	;;
        --ignore-snapshots ) IGNORE_SNAPSHOTS=true
						;;
        * ) usage
    esac
    shift
done

# extra validation 
if [ "$MODE" = "" ]; then
    usage
fi
if [ "$MAVEN" = "" ]; then
    usage
fi
if [ "$MODE" = "r" ]; then
	if [ "$VERSION" = "" ]; then
	    usage
	fi
fi

# Logic

function getMavenVersion() {
	local POM_PATH=$1
	if [ "$POM_PATH" != "" ]; then
		POM_PATH="-f $POM_PATH"
	fi
	local MAVEN_VERSION=$(printf 'VERSION=${project.version}\n0\n' | $MAVEN org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate $POM_PATH | grep '^VERSION')
	MAVEN_VERSION="${MAVEN_VERSION/VERSION=/}"
	echo "$MAVEN_VERSION"
}

function increase() {

	if [ "$INCREASE_VERSION" = "" ]; then
		semverParseInto $CURRENT_VERSION MAJOR MINOR PATCH SPECIAL
		PATCH=$((PATCH+1))
		PATCH_VERSION="$MAJOR.$MINOR.$PATCH$SPECIAL"
		INCREASE_VERSION=$PATCH_VERSION
		warningMsg "Increasing *PATCH* automatically"
	fi

	phaseMsg "Increasing version to $INCREASE_VERSION"
	
	sh update-version.sh $INCREASE_VERSION -mvn "$MAVEN"

	phaseMsg "Committing new version"

	git commit -am "Bump Maven version to $INCREASE_VERSION (prepare for next development iteration)"
}

function release(){

	phaseMsg "Updating Reply version from $CURRENT_VERSION to $VERSION"

	if [ "$CURRENT_VERSION" = "$VERSION" ] 
	then
		warningMsg "Already at $VERSION version... Doing nothing"
	#else
		#sh update-version.sh $VERSION -mvn "$MAVEN"
	fi

	phaseMsg "Preparing the release (after preconditions check)"

	if [ "$IGNORE_SNAPSHOTS" = true ]; then
		warningMsg "Allowing to release the code with snapshot dependencies (*NOT SAFE FOR REAL RELEASES!*)"
	fi

	# WARNING: Maven release only allows -SNAPSHOT releases!
	GIT_REV=$(git rev-parse HEAD)
	TAG_NAME="v$VERSION"
	 

	RELEASE_CMD="$MAVEN --batch-mode release:prepare -DtagNameFormat=$TAG_NAME -Dresume=false \
	 -DallowTimestampedSnapshots=false -DignoreSnapshots=$IGNORE_SNAPSHOTS \
	 -DremoteTagging=false -DsuppressCommitBeforeTag=false \
	 -DpushChanges=false -DdryRun=false -DautoVersionSubmodules=true \
	 -DpreparationGoals=clean,install \
	 -DdevelopmentVersion=$CURRENT_VERSION -DreleaseVersion=$VERSION \
	 -DcheckModificationExcludeList=**/pom.xml"

	INSTALL_CMD="$MAVEN --batch-mode clean install"
	declare -a PROJECTS=($RELATIVE_POM_PATH)

	for i in "${PROJECTS[@]}"
	do
		echo "Updating: $i"
		echo

		# Update parent dependencies because that stupid Maven Release plugin does not!
		#$MAVEN versions:update-parent -DparentVersion=[$VERSION] -DgenerateBackupPoms=false -f "$i"
	    # Prepare release on project (update versions, check for snapshot dependencies, etc)
		$RELEASE_CMD -f "$i"
		# Undo unwanted git modifications
		git tag -d $TAG_NAME
		git reset --hard HEAD~1
	done

	# Squash update commits together (we just want a single release commit!)
	phaseMsg "Squashing update commits together"

	git reset --soft HEAD~1
	git commit -F- <<EOF
Release $VERSION: update prj and dependencies version to stable ones

- Changed project version to $VERSION
EOF

	phaseMsg "Tagging the release"

	git tag -a $TAG_NAME -m $TAG_NAME
	echo "$TAG_NAME: $(git rev-list -n 1 $TAG_NAME)"

	phaseMsg "Cleaning up Git workspace"

	git reset --hard $GIT_REV
}

function stable() {
	semverParseInto $CURRENT_VERSION MAJOR MINOR PATCH SPECIAL
	STABLE_BRANCH="$MAJOR-$MINOR-stable"

	phaseMsg "Creating stable branch $STABLE_BRANCH"

	git branch $STABLE_BRANCH

	if [ "$VERSION" = "" ]; then
		MINOR=$((MINOR+1))
		MINOR_VERSION="$MAJOR.$MINOR.$PATCH$SPECIAL"
		INCREASE_VERSION=$MINOR_VERSION
		warningMsg "Increasing *MINOR* automatically"
	else
		INCREASE_VERSION=$VERSION
	fi
	increase 

	# if [ $PUSH ]; then
	# 	echo
	# 	echo "5) Pushing changes to Git:"
	# 	echo "--------------------------"
	# 	echo

	# 	git push origin $MASTER_BRANCH
	# 	git push origin $STABLE_BRANCH
	# 	echo
	# 	echo "-- Stable ready to use --"
	# else
	# 	echo
	# 	echo "-- Stable ready to be pushed... --"
	# fi
}

# --- End Logic ---

# Execution

PROJECT_POM_PATH="pom.xml"
RELATIVE_POM_PATH=$BASE_PATH$PROJECT_POM_PATH

if [ "$VERSION" != "" ]; then
	semverParseInto $VERSION
fi
 
if [ "$INCREASE_VERSION" != "" ]; then
	semverParseInto $INCREASE_VERSION
fi

phaseMsg "Getting current version"

echo "Looking for POM in: $RELATIVE_POM_PATH"

CURRENT_VERSION=$(getMavenVersion $RELATIVE_POM_PATH)
echo "Current version: $CURRENT_VERSION"

if [ "$VERSION" != "" ]; then
	checkVersionsDifferent
fi

case $MODE in
	"r" )
		phaseMsg "RELEASE MODE"

		# Check snapshot
		checkVersionSnapshot

		# Check final -> increase
		if [ $INCREASE=false ]; then
			if [[ "$VERSION" == *-FINAL ]]
			then
				warningMsg "FINAL version will trigger current *patch* version increase"
				INCREASE=true
			fi
		fi

		release

		if [ $INCREASE ]; then
			increase
		fi
		;;

	"s" )
		phaseMsg "STABLE MODE"

		#Check snapshot
		checkVersionSnapshot

		stable
		;;

	"i" )
		phaseMsg "INCREASE MODE"

		increase
		;;

	* ) usage
esac


echo
echo "-- Release ready --"