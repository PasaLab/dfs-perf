---
layout: global
title: Running DFS-Perf on GlusterFS
---

To run DFS-Perf on GlusterFS, you must install DFS-Perf in [cluster mode](Running-DFS-Perf-on-a-Cluster.html)

# Configurations
Before running DFS-Perf, you should set these GlusterFS-related variables in `conf/dfs-perf-env.sh`:

    export DFS_PERF_DFS_ADRESS="glusterfs://"
    
    export DFS_PERF_DFS_OPTS="
      -Dpasalab.dfs.glusterfs.impl=org.apache.hadoop.fs.glusterfs.GlusterFileSystem
      -Dpasalab.dfs.perf.glusterfs.volumes=glusterfs_vol
      -Dpasalab.dfs.perf.glusterfs.mounts=/vol
    "

# Usages
Now you can run DFS-Perf on GlusterFS. Actually the usages of all the DFS are the same, e.g.

    $ bin/dfs-perf-clean
    $ bin/dfs-perf Metadata
    $ bin/dfs-perf-collect Metadata
    
See more examples on [Examples](Examples.html).