---
layout: global
title: Running DFS-Perf on a Cluster
---
This guide describes how to get DFS-Perf running on a cluster.

# Prerequisites
The prerequisites for this part contains all the prerequisites of [Running DFS-Perf Locally](Running-DFS-Perf-Locally.html). In addition, to run on a cluster, you need a Distributed File System from GlusterFS, GPFS, HDFS and Tachyon. 

# Configurations
Prepare the binary distribution of DFS-Perf:

    $ tar xvfz dfs-perf-0.1-bin.tar.gz
    $ cd dfs-perf-0.1

Before running DFS-Perf, requisite environment variables must be specified in `conf/dfs-perf-env.sh`

    $ cp conf/dfs-perf-env.sh.template conf/dfs-perf-env.sh

To run on a cluster, these variables in `conf/dfs-perf-env.sh` should be set as follows:

    export JAVA={where.your.java}
 
    # Set to the distributed file system, here take hdfs as example
    export DFS_PERF_DFS_ADRESS="hdfs://master:9000"
     
    # Set master address
    DFS_PERF_MASTER_HOSTNAME="master"
    
For different DFS, some specific variables need to be set. You can see the details in [Running DFS-Perf on GlusterFS](Running-DFS-Perf-on-GlusterFS.html), [Running DFS-Perf on GPFS](Running-DFS-Perf-on-GPFS.html), [Running DFS-Perf on HDFS](Running-DFS-Perf-on-HDFS.html) and [Running DFS-Perf on Tachyon](Running-DFS-Perf-on-Tachyon.html).
    
Then, set the slaves in `conf/slaves`. Each line represents a slave (actually a process).

# Example
Now you can run DFS-Perf on DFS. For example, run the metadata workload.

    $ bin/dfs-perf-clean
    $ bin/dfs-perf Metadata
    $ bin/dfs-perf-collect Metadata
    
See more examples on [Examples](Examples.html).
