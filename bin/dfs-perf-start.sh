#!/usr/bin/env bash

function printUsage {
  echo "Usage: dfs-perf-start.sh <NodeName> <TaskId> <TaskType>"
  echo "This is used to start dfs-perf on each node, see more in ./dfs-perf"
}

# if less than 3 args specified, show usage
if [ $# -le 2 ]; then
  printUsage
  exit 1
fi

bin=`cd "$( dirname "$0" )"; pwd`

DEFAULT_PERF_LIBEXEC_DIR="$bin"/../libexec
DFS_PERF_LIBEXEC_DIR=${DFS_PERF_LIBEXEC_DIR:-$DEFAULT_PERF_LIBEXEC_DIR}
. $DFS_PERF_LIBEXEC_DIR/dfs-perf-config.sh

if [ ! -d "$DFS_PERF_LOGS_DIR" ]; then
  echo "DFS_PERF_LOGS_DIR: $DFS_PERF_LOGS_DIR"
  mkdir -p $DFS_PERF_LOGS_DIR
fi

JAVACOMMAND="$JAVA -cp $DFS_PERF_CONF_DIR/:$DFS_PERF_JAR -Dpasalab.dfs.perf.home=$DFS_PERF_HOME -Dpasalab.dfs.perf.logger.type=PERF_SLAVE_LOGGER -Dlog4j.configuration=file:$DFS_PERF_CONF_DIR/log4j.properties $DFS_PERF_JAVA_OPTS pasalab.dfs.perf.DfsPerfSlave"

echo "Starting dfs-perf task-$2 @ `hostname -f`"
(nohup $JAVACOMMAND $* > /dev/null 2>&1 ) &

sleep 2
