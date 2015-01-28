---
layout: global
title: Running DFS-Perf on Tachyon
---

To run DFS-Perf on Tachyon, you must install DFS-Perf in [cluster mode](Running-DFS-Perf-on-a-Cluster.html)

# Configurations
Before running DFS-Perf, you should set these Tachyon-related variables in `conf/dfs-perf-env.sh`:

    export DFS_PERF_DFS_ADRESS="tachyon://ip:port"
    
    export DFS_PERF_DFS_OPTS="
      -Dtachyon.user.master.client.timeout.ms=600000
    "

# Usages
Now you can run DFS-Perf on Tachyon. Actually the usages of all the DFS are the same, e.g.

    $ bin/dfs-perf-clean
    $ bin/dfs-perf Metadata
    $ bin/dfs-perf-collect Metadata
    
See more examples on [Examples](Examples.html).