---
layout: global
title: Perf Report
---

We have evaluated the performance of widely-used representative DFS under different workloads with DFS-Perf. Here is a short report.

# Experimental Setup

    One master node -- two Intel Xeon X5660 CPUs with 64GB memory
    16 slave nodes -- two Intel Xeon E5620 CPUs with 64GB memory
    1Gb/s Ethernet
    
    RHEL 6.0 with Linux 2.6.32, Ext3 file system and Java 1.6.0.
    GlusterFS-3.5.0
    HDFS-2.3.0
    Tachyon-0.6.0-SNAPSHOT

# Basic Performance
## Metadata Performance

![Metadata multi-thread](./img/metadata-a.png)
<br/>

![Metadata multi-process](./img/metadata-b.png)
<br/>

## Read/Write Throughput

![RW multi-thread](./img/rw-a.png)
<br/>

![RW multi-process](./img/rw-b.png)
<br/>

# Scalability

![Scalability single-node multi-thread](./img/scalability-a.png)
<br/>

![Scalability single-node multi-process](./img/scalability-b.png)
<br/>

![Scalability multi-node multi-thread](./img/scalability-c.png)
<br/>

![Scalability multi-node multi-process](./img/scalability-d.png)
<br/>