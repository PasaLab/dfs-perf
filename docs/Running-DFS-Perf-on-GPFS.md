---
layout: global
title: Running DFS-Perf on GPFS
---

To run DFS-Perf on GPFS, you must install DFS-Perf in [cluster mode](Running-DFS-Perf-on-a-Cluster.html)

# Configurations
Before running DFS-Perf, you should set these GPFS-related variables in `conf/dfs-perf-env.sh`:

    export DFS_PERF_DFS_ADRESS="file://"
    export DFS_PERF_WORKSPACE="{where.you.mount.GPFS}/tmp/dfs-perf-workspace"
    
Note that here we treat GPFS as a local file system since it supports to use Linux FUSE Module. So we can set the workspace directory to where you mount the GPFS.

# Usages
Now you can run DFS-Perf on GPFS. Actually the usages of all the DFS are the same, e.g.

    $ bin/dfs-perf-clean
    $ bin/dfs-perf Metadata
    $ bin/dfs-perf-collect Metadata
    
See more examples on [Examples](Examples.html).