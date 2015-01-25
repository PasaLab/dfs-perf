---
layout: global
title: Running Octopus on a Cluster
---
This guide describes how to get Octopus running on a cluster.

# Prerequisites
The prerequisites for this part contains all the prerequisites of [Running Octopus Locally](Running-Octopus-Locally.html). In addition, to run on a cluster, you need a Distributed File System, e.g. HDFS or Tachyon, at least one Computing Framework, e.g. Spark, Hadoop MapReduce or MPI. 

More a step, if you want to use the `apply` method, you should have Rserve package on each of your cluster nodes. You can install the Rserve package with following steps (Note that this should be executed on each node):

    1. start R shell with root permission
    2. > install.packages("Rserve")
    3. quit R shell and root
    4. $ R CMD Rserve

# Configurations
Prepare the binary distribution of Octopus:

    $ tar xvfz octopus-0.1-bin.tar.gz
    $ cd octopus-0.1

Before installing Octopus, requisite environment variables must be specified in `conf/octopus-env.R`

To run on a cluster, these variables in `conf/octopus-env.R` should be set as follows:

    # If you has Spark as a computing framework, set this to TRUE
    OCTOPUS_SPARK_START=TRUE
 
    # Set the home directory path of Octopus
    OCTOPUS_HOME="{where.your.octopus-0.1}"
     
    # To run on a cluster, set this to a distributed file system, e.g. hdfs or tachyon
    OCTOPUS_UNDERFS_ADDRESS="hdfs://ip:port"
     
    # Set the data folder's path of Octopus in underlying file system
    OCTOPUS_WAREHOUSE="/tmp/octopus_warehouse"
    
Moreover, set those variables correspond to your computing framework. For different computing frameworks, you can distinguish those configurations with their prefixes, e.g. `OCTOPUS_SPARK_`, `OCTOPUS_HADOOP_` or `OCTOPUS_MPI_`.

Then, the following command should be added to make R be able to load the configurations. It can be added to `Rprofile.site` in the installation path of R, e.g. `/usr/lib64/R/etc/Rprofile.site`. Or added to `.Rprofile` in the user's home path, e.g. `~/.Rprofile`.

    source("{where.your.octopus-0.1}/conf/octopus-env.R")

Now, you can install Octopus by running:

    $ R CMD INSTALL {where.your.octopus-0.1}/R/pkg
    
Before using Octopus, you can format your Octopus Warehouse by running:

    $ sbin/octopus-format.sh

# Example
Now you can write a few R script to verify if the installed package `OctMatrix` works well.

    require(OctMatrix)
    
    #Here we take Spark and HDFS as example
    engineType <- "Spark"
    outputPath <- "hdfs://master:9000/tmp/octoput-test-c"
    
    a <- OctMatrix(1:4, 2, 2, engineType)
    b <- OctMatrix(5:8, 2, 2, engineType)
    c <- a + b
    c
    WriteOctMatrix(c, outputPath)

You can see the correct result of `a + b` and the result `c` is output to the target path.