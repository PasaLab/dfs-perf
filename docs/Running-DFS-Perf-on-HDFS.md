---
layout: global
title: Running DFS-Perf on HDFS
---

To run Octopus on Hadoop, you must install Octopus in [cluster mode](Running-Octopus-on-a-Cluster.html)

# Configurations
Before installing the OctMatrix R package, you should set these Hadoop-related variables in `conf/octopus-env.R`:

    # Set the home directory path of Hadoop
    # Ignore this if you never about to using Hadoop as a underlying executing engine.
    OCTOPUS_HADOOP_HOME="{where.your.hadoop}"

# Usages
When using OctMatrix package in R script, set the engineType to be "Hadoop" then the matrix will be handled by Hadoop.

    a <- OctMatrix(data, nrow, ncol, "Hadoop", byrow)