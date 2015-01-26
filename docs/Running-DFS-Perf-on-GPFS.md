---
layout: global
title: Running Octopus on Spark
---

To run Octopus on Spark, you must install Octopus in [cluster mode](Running-Octopus-on-a-Cluster.html)

# Configurations
Before installing the OctMatrix R package, you should set these Spark-related variables in `conf/octopus-env.R`:

    # Make sure Spark has been started
    OCTOPUS_SPARK_START=TRUE
    
    # Set the master URL for the Spark cluster
    OCTOPUS_SPARK_MASTER="spark://ip:port"
    
    # Set the maximum amount of CPU cores of the application from the cluster. If not set, the default will be spark.deploy.defaultCores on Spark's standalone cluster manager.
    OCTOPUS_SPARK_CORES_MAX="4"
    
    # Set the amount of memory to use per executor process, in the same format as JVM memory strings (e.g. 512m, 2g). 
    OCTOPUS_SPARK_EXECUTOR_MEMORY="8g"
    
    # Set the default task number of shuffle process
    OCTOPUS_SPARK_DEFAULT_PARALLELISM=4

# Usages
When using OctMatrix package in R script, set the engineType to be "Spark" then the matrix will be handled by Spark.

    a <- OctMatrix(data, nrow, ncol, "Spark", byrow)