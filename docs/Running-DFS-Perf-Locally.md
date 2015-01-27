---
layout: global
title: Running DFS-Perf Locally
---
This guide describes how to get DFS-Perf running locally for a quick spin in ~ 2 minutes.

# Prerequisites
The prerequisites for this part is that you have Java (JDK 6 or above).

# Configurations
Prepare the binary distribution of DFS-Perf:

    $ tar xvfz dfs-perf-0.1-bin.tar.gz
    $ cd dfs-perf-0.1

Before running DFS-Perf, requisite environment variables must be specified in `conf/dfs-perf-env.sh`

    $ cp conf/dfs-perf-env.sh.template conf/dfs-perf-env.sh

To run locally, these variables in `conf/dfs-perf-env.sh` should be set as follows:

    export JAVA={where.your.java}
 
    # Set to the local file system
    export DFS_PERF_DFS_ADRESS="file://"
     
    # Set master to localhost
    DFS_PERF_MASTER_HOSTNAME="localhost"
    
Then, set the slaves in `conf/slaves`. In local mode it's recommend to use 'localhost' as slave.

# Example
Now you can run DFS-Perf on the local file system. For example, run the metadata workload.

    $ bin/dfs-perf-clean
    $ bin/dfs-perf Metadata
    $ bin/dfs-perf-collect Metadata

See more examples on [Examples](Examples.html).
