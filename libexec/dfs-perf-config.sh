#!/usr/bin/env bash

# resolve links - $0 may be a softlink
this="${BASH_SOURCE-$0}"
common_bin=$(cd -P -- "$(dirname -- "$this")" && pwd -P)
script="$(basename -- "$this")"
this="$common_bin/$script"

# convert relative path to absolute path
config_bin=`dirname "$this"`
script=`basename "$this"`
config_bin=`cd "$config_bin"; pwd`
this="$config_bin/$script"

export DFS_PERF_PREFIX=`dirname "$this"`/..
export DFS_PERF_HOME=${DFS_PERF_PREFIX}
export DFS_PERF_CONF_DIR="$DFS_PERF_HOME/conf"
export DFS_PERF_LOGS_DIR="$DFS_PERF_HOME/logs"
export DFS_PERF_JAR=$DFS_PERF_HOME/target/dfs-perf-0.1.0-SNAPSHOT-jar-with-dependencies.jar
export JAVA="$JAVA_HOME/bin/java"

if [ -e $DFS_PERF_CONF_DIR/dfs-perf-env.sh ] ; then
  . $DFS_PERF_CONF_DIR/dfs-perf-env.sh
fi
