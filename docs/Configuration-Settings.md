---
layout: global
title: Configuration Settings
---

DFS-Perf has two categories of configuration parameters. One is the *Environment Configurations* which is for the DFS-Perf framework. Another is the *Workloads Configurations* which is used to custom the workloads.

# Environment Configurations

These configuration contains the environment settings to run DFS-Perf. First, in `conf/slaves`, you can configure the nodes to launch DFS-Perf process, and you can repeat the node name to launch multi-process, e.g.

    slaveA
    slaveA
    slaveB
    slaveB
    
means to launch two DFS-Perf processes on `slaveA` and `slaveB`.

Then, the environment configurations are set in `conf/dfs-perf-env.sh`

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>JAVA</td>
  <td>/usr/lib/jvm/bin/java</td>
  <td>Java installation path.</td>
</tr>
<tr>
  <td>DFS_PERF_DFS_ADRESS</td>
  <td>alluxio://master:19998</td>
  <td>The URI address of the targeted DFS.</td>
</tr>
<tr>
  <td>DFS_PERF_WORKSPACE</td>
  <td>/tmp/dfs-perf-workspace</td>
  <td>The workspace folder's path of DFS-Perf in targeted DFS.</td>
</tr>
<tr>
  <td>DFS_PERF_OUT_DIR</td>
  <td>$DFS_PERF_HOME/result</td>
  <td>The performance result output dir.</td>
</tr>
<tr>
  <td>DFS_PERF_MASTER_HOSTNAME</td>
  <td>master</td>
  <td>The DFS-Perf master service address.</td>
</tr>
<tr>
  <td>DFS_PERF_MASTER_PORT</td>
  <td>23333</td>
  <td>The DFS-Perf master service port.</td>
</tr>
<tr>
  <td>DFS_PERF_THREADS_NUM</td>
  <td>4</td>
  <td>The thread num of each DFS-Perf process.</td>
</tr>
<tr>
  <td>DFS_PERF_UNREGISTER_TIMEOUT_MS</td>
  <td>10000</td>
  <td>The slave is considered to be failed if not register in this time.</td>
</tr>
<tr>
  <td>DFS_PERF_STATUS_DEBUG</td>
  <td>false</td>
  <td>If true, it will print the names of those running and remaining nodes.</td>
</tr>
<tr>
  <td>DFS_PERF_FAILED_ABORT</td>
  <td>true</td>
  <td>If true, the test will abort when the number of failed nodes more than the threshold.</td>
</tr>
<tr>
  <td>DFS_PERF_FAILED_PERCENTAGE</td>
  <td>1</td>
  <td>The threshold percentage for failure abort.</td>
</tr>
</table>

# Workloads Configurations

Each workload has its own configurations and can be modified in `conf/testsuite/{workload.name}`

## Metadata

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>clients.per.thread</td>
  <td>20</td>
  <td>the number of clients to connect to the file system for each thread</td>
</tr>
<tr>
  <td>op.second.per.thread</td>
  <td>5</td>
  <td>the metadata operations time for each thread, in seconds</td>
</tr>
</table>

## SimpleWrite

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>files.per.thread</td>
  <td>2</td>
  <td>the number of files to write for each write thread</td>
</tr>
<tr>
  <td>block.size.bytes</td>
  <td>134217728</td>
  <td>the block size of a file</td>
</tr>
<tr>
  <td>file.length.bytes</td>
  <td>134217728</td>
  <td>the file size of write test, in bytes</td>
</tr>
<tr>
  <td>buffer.size.bytes</td>
  <td>4194304</td>
  <td>the size of the buffer write once</td>
</tr>
<tr>
  <td>write.type</td>
  <td>THROUGH</td>
  <td>the WriteType of the write operation, now only used for Alluxio</td>
</tr>
</table>

## SimpleRead

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>files.per.thread</td>
  <td>2</td>
  <td>the number of files to read for each read thread</td>
</tr>
<tr>
  <td>read.mode</td>
  <td>SEQUENCE</td>
  <td>the read mode of read test, should be RANDOM or SEQUENCE. This is used to choose which files to read</td>
</tr>
<tr>
  <td>buffer.size.bytes</td>
  <td>4194304</td>
  <td>the size of the buffer read once</td>
</tr>
<tr>
  <td>read.type</td>
  <td>CACHE</td>
  <td>the ReadType of the write operation, now only used for Alluxio</td>
</tr>
</table>

## SkipRead

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>files.per.thread</td>
  <td>2</td>
  <td>the number of files to read for each read thread</td>
</tr>
<tr>
  <td>read.mode</td>
  <td>SEQUENCE</td>
  <td>the read mode of read test, should be RANDOM or SEQUENCE. This is used to choose which files to read</td>
</tr>
<tr>
  <td>buffer.size.bytes</td>
  <td>4194304</td>
  <td>the size of the buffer read once</td>
</tr>
<tr>
  <td>read.type</td>
  <td>CACHE</td>
  <td>the ReadType of the write operation, now only used for Alluxio</td>
</tr>
<tr>
  <td>read.bytes</td>
  <td>16777216</td>
  <td>the read bytes for each skip-and-read operator</td>
</tr>
<tr>
  <td>skip.bytes</td>
  <td>16777216</td>
  <td>the skip bytes for each skip-and-read operator</td>
</tr>
<tr>
  <td>skip.mode</td>
  <td>FORWARD</td>
  <td>the skip mode, should be FORWARD or RANDOM</td>
</tr>
<tr>
  <td>skip.times.per.file</td>
  <td>2</td>
  <td>the skip-and-read times for each read file</td>
</tr>
</table>

## Mixture

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>basic.files.per.thread</td>
  <td>2</td>
  <td>the number of basic files to write at the beginning for each thread</td>
</tr>
<tr>
  <td>read.files.per.thread</td>
  <td>4</td>
  <td>the number of files to read for each thread</td>
</tr>
<tr>
  <td>write.files.per.thread</td>
  <td>1</td>
  <td>the number of files to write for each thread</td>
</tr>
<tr>
  <td>block.size.bytes</td>
  <td>134217728</td>
  <td>the block size of a file</td>
</tr>
<tr>
  <td>file.length.bytes</td>
  <td>134217728</td>
  <td>the file size in bytes</td>
</tr>
<tr>
  <td>buffer.size.bytes</td>
  <td>4194304</td>
  <td>the size of the buffer read and write once</td>
</tr>
<tr>
  <td>read.type</td>
  <td>CACHE</td>
  <td>the ReadType of the read operation, now only used for Alluxio</td>
</tr>
<tr>
  <td>write.type</td>
  <td>THROUGH</td>
  <td>the WriteType of the write operation, now only used for Alluxio</td>
</tr>
</table>

## Iterate

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>iterations</td>
  <td>2</td>
  <td>the number of the write-read iteration</td>
</tr>
<tr>
  <td>shuffle.mode</td>
  <td>false</td>
  <td>shuffle mode means it may read remotely</td>
</tr>
<tr>
  <td>read.files.per.thread</td>
  <td>2</td>
  <td>the number of files to read for each thread</td>
</tr>
<tr>
  <td>write.files.per.thread</td>
  <td>2</td>
  <td>the number of files to write for each thread</td>
</tr>
<tr>
  <td>block.size.bytes</td>
  <td>134217728</td>
  <td>the block size of a file</td>
</tr>
<tr>
  <td>file.length.bytes</td>
  <td>134217728</td>
  <td>the file size in bytes</td>
</tr>
<tr>
  <td>buffer.size.bytes</td>
  <td>4194304</td>
  <td>the size of the buffer read and write once</td>
</tr>
<tr>
  <td>read.type</td>
  <td>CACHE</td>
  <td>the ReadType of the read operation, now only used for Alluxio</td>
</tr>
<tr>
  <td>write.type</td>
  <td>THROUGH</td>
  <td>the WriteType of the write operation, now only used for Alluxio</td>
</tr>
</table>

## Massive

<table class="table">
<tr><th>Property Name</th><th>Example</th><th>Meaning</th></tr>
<tr>
  <td>time.seconds</td>
  <td>5</td>
  <td>the time to do the global read/write</td>
</tr>
<tr>
  <td>shuffle.mode</td>
  <td>false</td>
  <td>shuffle mode means it may read remotely</td>
</tr>
<tr>
  <td>basic.files.per.thread</td>
  <td>2</td>
  <td>the number of global files to write at the beginning for each thread</td>
</tr>
<tr>
  <td>block.size.bytes</td>
  <td>134217728</td>
  <td>the block size of a file</td>
</tr>
<tr>
  <td>file.length.bytes</td>
  <td>134217728</td>
  <td>the file size in bytes</td>
</tr>
<tr>
  <td>buffer.size.bytes</td>
  <td>4194304</td>
  <td>the size of the buffer read and write once</td>
</tr>
<tr>
  <td>read.type</td>
  <td>CACHE</td>
  <td>the ReadType of the read operation, now only used for Alluxio</td>
</tr>
<tr>
  <td>write.type</td>
  <td>THROUGH</td>
  <td>the WriteType of the write operation, now only used for Alluxio</td>
</tr>
</table>
