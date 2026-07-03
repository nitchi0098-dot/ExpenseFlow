#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a symlink
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls -ld "$PRG"
    LINK=`ls -l "$PRG" | awk '{print $NF}'`
    case $LINK in
      /*) PRG="$LINK" ;;
      *) PRG=`dirname "$PRG"`"/$LINK" ;;
    esac
done
SAVED="$(cd "$(dirname "$PRG")" >/dev/null 2>&1 && pwd)"
APP_HOME="$(cd "$(dirname "$SAVED")" >/dev/null 2>&1 && pwd)"

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='-Xmx64m -Xms64m'

# Use the maximum available, or set MAX_FD != unlimited.
MAX_FD="maximum"

warn () {
    echo "$*" >&2
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
darwin=false
msys=false
cygwin=false
nativewin=false
case "$(uname)" in
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  CYGWIN* )
    cygwin=true
    ;;
  NATIVEWIN )
    nativewin=true
    ;;
esac

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if ! "$cygwin" && ! "$darwin" && ! "$msys" && ! "$nativewin" ; then
    case $(ulimit -n) in
      ''|unlimited) ;;
      *)
        ulimit -n $(getconf ARG_MAX) 2>/dev/null || ulimit -n 65536
        ;;
    esac
fi

# Escape application args
save () {
    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/" ; done
    echo " "
}
APP_ARGS=$(save "$@")

# Collect all arguments for the java command, stacking in reverse order:
#   * _script_ args
#   * java.lang.System.getProperties() does not include JAVA_TOOL_OPTIONS
#   * JVM_ARGS environment variable
#   * Gradle options
# The leading space in GRADLE_OPTS is intentional to allow for leading `-D` or `-X` to override the defaults.
eval set -- "$APP_ARGS" \
        -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
        org.gradle.wrapper.GradleWrapperMain \
        "$@"

exec "$JAVACMD" "$@"
