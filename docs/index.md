---
layout: global
title: Octopus Overview
---

Octopus is a high-level and unified programming model and platform for big data analytics and mining. It can transparently work on top of various distributed computing frameworks, allowing data analysts and big data application programmers to easily design and implement machine learning and data mining algorithms for big data analytics. It offers an R package that provides easy-to-use scalable matrix operations from R and seamlessly executes computation on the single-node R or distributed computing frameworks such as Spark, Hadoop, MPI, etc. It allows users to run R scripts or shell commands interactively on a cluster without the distributed programming knowledge such as Hadoop MapReduce or Spark RDD.

[Gitbucket Repository](http://pasa-bigdata.nju.edu.cn:60011/gurong/octopus) |
[Releases and Downloads](Download.html) |
[User Documentation](#user-documentation) |
[JIRA](http://pasa-bigdata.nju.edu.cn:61111/jira/browse/OCT/?selectedTab=com.atlassian.jira.jira-projects-plugin:issues-panel) |
[User Mailing List](https://groups.google.com/forum/#!forum/octopus-user) |
[Developer Documentation](Development.html)

# Current Features

* **Ease-to-use/High-level User APIs**: Octopus provides users with a set of R-based APIs, called `OctMatrix`. The APIs are similar to that of the Matrix/Vector operation APIs in the standard R language. The APIs are high-level matrix operators and operations and a user with the basic knowledge of R can easily program with Octopus to design and implement a variety of machine learning and data mining algorithms for big data. It does not require the low-level knowledge for the distributed system knowledge or programming skills.

* **Write Once, Run Everywhere**: The programs written with Octopus can run on different computing engines. Users can write algorithms using `OctMatrix` APIs with small data running on a single-node R engine for test and then run the program on large scale data without modifying the codes. Users only need to switch the underlying computing engines such as Spark, Hadoop MapReduce, or MPI. Also, Octopus has a generic interface to easily implement plugins for different underlying file systems. We currently support a number of I/O sources including Tachyon, HDFS, and single-node local file systems.

* **Distributed R `apply` Functions**: Besides the conventional Matrix/Vector functions, Octopus also offers the `apply` function on `OctMatrix`.  The parameter functions passed to `apply` can be any R functions include the UDFs. When the data of the `OctMatrix` in a distributed environment, the parameter function will be executed on each element/row/column of the `OctMatrix` on the cluster. In more detail, they are executed on the local RServer daemon process on each node of the cluster in parallel.  

* **Machine Learning Algorithm Library**: Besides providing the `OctMatrix` and its APIs, Octopus also carries a bunch of scalable machine learning algorithms and demo applications which are built on top of `OctMatrix`.  Currently, the library contains logistic regression, linear Support Vector Machines(SVMs), linear regression, KMeans, softmax, deep neural network(auto encoder), and support for many other Machine Learning algorithms is coming.

* **Seamlessly Integration With R Ecosystem**: Octopus offers its features in a R package called OctMatrix. Therefore, it naturally takes advantage of the rich resources of the R ecosystem, such as various third-party R packages. Also, it can be imported and used at many [friendly R IDEs](R-IDEs.html)

# User Documentation

[Running Octopus Locally](Running-Octopus-Locally.html): Get Octopus up and running on a single node for a quick spin in ~ 2 minutes.

[Running Octopus on a Cluster](Running-Octopus-on-a-Cluster.html): Get Octopus up and running on your own cluster.

[Running Octopus on Spark](Running-Octopus-on-Spark.html): Get Octopus running on Spark. 

[Running Octopus on Hadoop](Running-Octopus-on-Hadoop.html): Get Octopus running on Hadoop MapReduce.

[Running Octopus on MPI](Running-Octopus-on-MPI.html): Get Octopus running on MPI. 

[Configuration Settings](Configuration-Settings.html): How to configure Octopus.

[Octopus User API (R)](API-Docs.html)

[Octopus Machine Learning Library (R)](ML-Library.html)

Octopus Presentations:

* A very brief introduction to Octopus (Jan. 2015) [pdf](resources/Octopus-short-intro.pdf)
* A video demo (Jan. 2015) [Chinese](http://v.youku.com/v_show/id_XODcxNjE0OTIw.html?qq-pf-to=pcqq.group)

# Support or Contact

If you are interested in trying out Octopus in your cluster, please contact [Yihua Huang](mailto:yhuang@nju.edu.cn) and [Rong Gu](mailto:gurongwalker@gmail.com). Users are welcome to join our
[mailing list](https://groups.google.com/forum/#!forum/octopus-user) to discuss
questions and make suggestions. We use [JIRA](http://pasa-bigdata.nju.edu.cn:61111/jira/browse/OCT/?selectedTab=com.atlassian.jira.jira-projects-plugin:issues-panel) to track development and issues. 

# Acknowledgement

Ocotpus is a research project started at the
[Nanjing University PASA Lab](http://pasa-bigdata.nju.edu.cn/) and currently led by [Yihua Huang](http://cs.nju.edu.cn/yhuang/) & [Rong Gu](http://pasa-bigdata.nju.edu.cn/people/ronggu/index.html). This research and development is funded in part by Jiangsu Province Industry Support Program (BE2014131) and China NSF Grants (No.61223003). We would also like to thank to our initial project contributors in PASALab.