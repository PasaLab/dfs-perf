---
layout: global
title: DFS-Perf Overview
---

DFS-Perf is a unified benchmarking framework for evaluating DFS' performance. DFS-Perf offers excellent scalability by supporting multi-node, multi-process, and multi-thread testing modes. These modes can work in a combination way in DFS-Perf. Morevoer, DFS-Perf provides various workloads to test on a series of typical underlying file systems, e.g. GlusterFS, GPFS, HDFS and Tachyon. DFS-Perf is an excellent tool to choose the most suitable DFS for applications and to improve DFS itself.

[Gitbucket Repository](http://pasa-bigdata.nju.edu.cn:60011/dongqianhao/DFS-Perf) |
[Releases and Downloads](Download.html) |
[User Documentation](#user-documentation) |
[JIRA](http://pasa-bigdata.nju.edu.cn:61111/jira/browse/DFSPERF/?selectedTab=com.atlassian.jira.jira-projects-plugin:summary-panel) |
[User Mailing List](https://groups.google.com/forum/?fromgroups#!forum/dfs-perf-users)

# Current Features

* **Excellent Scalability**: DFS-Perf is designed to be scalable to run multi-node, multi-process and multi-thread benchmarks, which reflects different application parallelism characteristics scenarios. To be convenient for users, the numbers of nodes, processes and threads are part of configurations and can be directly modified in the configuration files.
In addition, the scalability of DFS-Perf is well designed that it only has to introduce a little overhead on the performance of DFS, which results in good scalability.

* **Various Workloads**: There are a variety of applications running on DFS. In order to comprehensively test the performance of the DFS for the various applications, we designed and implemented a series of typical workloads for the file access patterns from the real-world applications. 

* **Support Widely Used DFS**: With the extensibility, DFS-Perf aims to benchmark different DFS. Thus, we make DFS-Perf support several widely used DFS, e.g. GlusterFS, GPFS, HDFS, Tachyon, also with the Local File System (LocalFS). 

# User Documentation

[Running DFS-Perf Locally](Running-DFS-Perf-Locally.html): Get DFS-Perf up and running on a single node for a quick spin in ~ 2 minutes.

[Running DFS-Perf on a Cluster](Running-DFS-Perf-on-a-Cluster.html): Get DFS-Perf up and running on your own cluster.

[Running DFS-Perf on GlusterFS](Running-DFS-Perf-on-GlusterFS.html): Get DFS-Perf up and running on GlusterFS.

[Running DFS-Perf on GPFS](Running-DFS-Perf-on-GPFS.html): Get DFS-Perf up and running on GPFS.

[Running DFS-Perf on HDFS](Running-DFS-Perf-on-HDFS.html): Get DFS-Perf up and running on HDFS.

[Running DFS-Perf on Tachyon](Running-DFS-Perf-on-Tachyon.html): Get DFS-Perf up and running on Tachyon.

[Workloads](Workloads.html): A brief introduction of those workloads in DFS-Perf.

[Configuration Settings](Configuration-Settings.html): How to configure DFS-Perf.

[Perf Report](Perf-Report.html): A performance report of a testing cluster.

# Support or Contact

If you are interested in trying out DFS-Perf in your cluster, please contact [Rong Gu](mailto:gurongwalker@gmail.com) and [Qianhao Dong](mailto:09122swat@gmail.com). Users are welcome to join our
[mailing list](https://groups.google.com/forum/?fromgroups#!forum/dfs-perf-users) to discuss
questions and make suggestions. We use [JIRA](http://pasa-bigdata.nju.edu.cn:61111/jira/browse/DFSPERF/?selectedTab=com.atlassian.jira.jira-projects-plugin:summary-panel) to track development and issues. 

# Acknowledgement

DFS-Perf is a research project started at the
[Nanjing University PASA Lab](http://pasa-bigdata.nju.edu.cn/) and currently led by [Rong Gu](http://pasa-bigdata.nju.edu.cn/people/ronggu/index.html) & [Qianhao Dong](http://pasa-bigdata.nju.edu.cn/people/dongqianhao/index.html). We would also like to thank to our initial project contributors in PASALab.