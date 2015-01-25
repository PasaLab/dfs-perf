---
layout: global
title: Configuration Settings
---

Octopus configuration parameters are those variables in `conf/octopus-env.R`, and will be loaded when starting a new R session.

# Configurations

These configuration contains the environment settings to run Octopus.

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>OCTOPUS_HOME</td>
  <td>/home/user/octopus</td>
  <td>Octopus installation folder.</td>
</tr>
<tr>
  <td>OCTOPUS_UNDERFS_ADDRESS</td>
  <td>hdfs://master:9000</td>
  <td>The URI address of the underlying file system.</td>
</tr>
<tr>
  <td>OCTOPUS_WAREHOUSE</td>
  <td>/tmp/octopus-warehouse</td>
  <td>The data folder's path of Octopus in underlying file system.</td>
</tr>
<tr>
  <td>OCTOPUS_R_JAVA_MEMORY</td>
  <td>8g</td>
  <td>the amount of memory to use for R-JAVA.</td>
</tr>
<tr>
  <td>OCTOPUS_SPARK_START</td>
  <td>TRUE</td>
  <td>If use Spark, set it to TRUE, otherwise FALSE.</td>
</tr>
<tr>
  <td>OCTOPUS_SPARK_MASTER</td>
  <td>spark://master:7077</td>
  <td>The master URL for the Spark cluster.</td>
</tr>
<tr>
  <td>OCTOPUS_SPARK_CORES_MAX</td>
  <td>4</td>
  <td>The maximum amount of CPU cores of the application from the cluster, which is equivalent to <code>spark.deploy.defaultCores</code></td>
</tr>
<tr>
  <td>OCTOPUS_SPARK_EXECUTOR_MEMORY</td>
  <td>8g</td>
  <td>The amount of memory to use per executor process, which is equivalent to <code>spark.executor.memory</code></td>
</tr>
<tr>
  <td>OCTOPUS_SPARK_DEFAULT_PARALLELISM</td>
  <td>4</td>
  <td>The default task number of shuffle process, which is equivalent to <code>spark.default.parallelism</code></td>
</tr>
<tr>
  <td>OCTOPUS_HADOOP_HOME</td>
  <td>/home/user/hadoop</td>
  <td>The home directory path of Hadoop.</td>
</tr>
<tr>
  <td>OCTOPUS_MPI_HOME</td>
  <td>/home/user/mpi</td>
  <td>The home directory path of MPI.</td>
</tr>
</table>
