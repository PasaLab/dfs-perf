---
layout: global
title: DFS-Perf FAQ
---

This page lists the frequent asked general questions about the DFS-Perf project. If you have some problems during usage, please put forward your questions in the [user mailing list](https://groups.google.com/forum/?fromgroups#!forum/dfs-perf-users).

- **How does DFS-Perf relate to HDFS/Tachyon ...?**  
DFS-Perf is a benchmark framework on top of Distributed File Systems(DFS) such as HDFS, Tachyon, etc. It allows users to run workloads to test the performance of DFS.

- **How can I run DFS-Perf on a cluster?**  
To run on a cluster, you need a DFS as test target. Then, you can refer [Running DFS-Perf on a Cluster](Running-DFS-Perf-on-a-Cluster.html) to get DFS-Perf up and running on your own cluster.

- **Can I run DFS-Perf on different file systems?**  
Of courese yes. DFS-Perf is designed to support different widely used DFS. The very simple way to switch to another DFS is to modify the path schema in the configuration file, e.g. `hdfs://master:9000` to `tachyon://master:19998`.

- **What's the difference between DFS-Perf and YCSB, HiBench, ...?**  
Now there exists benchmark frameworks for big data computing, such as [YCSB](https://github.com/brianfrankcooper/YCSB), [HiBench](https://github.com/intel-hadoop/HiBench), etc. However, they are not targeted on DFS. YCSB is a benchmark framework for evaluating the performance of the new generation "key-value" and "cloud" serving stores. And HiBench is a Hadoop Benchmark Suite which contains a series of typical Hadoop MapReduce workloads. Indeed, there is no testing framework that can execute different workloads on different DFS. Our goal is to design such a general benchmarking framework for different DFS with the guidance of those existing benchmarks. That is DFS-Perf. 

- **Where can I get more help?**  
Please post on the DFS-Perf [user mailing list](https://groups.google.com/forum/?fromgroups#!forum/dfs-perf-users). We'll be glad to help there!