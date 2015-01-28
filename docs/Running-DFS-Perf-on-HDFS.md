---
layout: global
title: Running DFS-Perf on HDFS
---

To run DFS-Perf on HDFS, you must install DFS-Perf in [cluster mode](Running-DFS-Perf-on-a-Cluster.html)

# Configurations
Before running DFS-Perf, you should set these HDFS-related variables in `conf/dfs-perf-env.sh`:

    export DFS_PERF_DFS_ADRESS="hdfs://ip:port"
    
    export DFS_PERF_DFS_OPTS="
      -Dpasalab.dfs.perf.hdfs.impl=org.apache.hadoop.hdfs.DistributedFileSystem
    "

# Usages
Now you can run DFS-Perf on HDFS. Actually the usages of all the DFS are the same, e.g.

    $ bin/dfs-perf-clean
    $ bin/dfs-perf Metadata
    $ bin/dfs-perf-collect Metadata
    
See more examples on [Examples](Examples.html).