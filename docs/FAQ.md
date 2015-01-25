---
layout: global
title: Octopus FAQ
---

This page lists the frequent asked general questions about the Octopus project. If you have some problems during usage, please put forward your questions in the [user mailing list](https://groups.google.com/forum/#!forum/octopus-user).

- **How does Octopus relate to Spark/Hadoop?**  
Octopus is a high-level analytical programming platform backended with various distributed computing frameworks such as Spark, Hadoop, MPI, etc. It allows users to run R scripts or shell commands interactively on a cluster without the distributed programming knowledge such as Hadoop MapReduce or Spark RDD, etc.

- **How can I run Octopus on a cluster?**  
 To run on a cluster, you need a Distributed File System, e.g. HDFS or Tachyon, at least one Computing Framework, e.g. Spark, Hadoop MapReduce or MPI. Then, you can refer [Running Octopus on a Cluster](Running-Octopus-on-a-Cluster.html) to get Octopus up and running on your own cluster.

- **Do I need Hadoop/Spark to run Octopus?**  
 Not always. Octopus can runs locally without Hadoop/Spark. It's a good way to have a quick experience with Octopus.

- **Does the machine installed OctMatrix package need to have high performance?**  
No. Actually, in the cluster mode, the matrix data are stored and processed in the underlying distributed engines across cluster, but not in the single-node machine.

- **Can I take advantage of the native linear algebra library to accelerate Octopus's computation speed?**  
Yes, please refer [here](Pre-built-Ones.html#native-library-dependencies) to see how. As the computation of OctMatrix are mostly numerical operations, we do strongly encourage you to do so. Experiment results show that enabling native linear algebra library can get around 10 times performance improvement for large datasets.

- **What's the difference between Octopus and SparkR?**  
Accroding [SparkR website](http://amplab-extras.github.io/SparkR-pkg/), SparkR is an R package that provides a light-weight frontend to use Apache Spark from R. SparkR exposes the Spark API through the RDD class and allows users to run jobs from the R on a cluster. From the user's perspective, SparkR supports various Spark RDD transformations/actions for general processing, while the Octopus's APIs are high-level matrix operators and operations, which are similar to the Matrix/Vector operation APIs in the standard R language. A user with the basic knowledge of R can use Octopus to design and implement a variety of machine learning and data mining algorithms. It does not require the low-level knowledge of the distributed system knowledge or programming skills, such as Spark RDD. From the computing engine's perspective, instead of only using Spark, Octopus is backended with various computing frameworks, including Spark, Hadoop, MPI and single-node R.  

- **Which languages does Octopus expose to end-users now?**  
Currently, Octopus exposes R to end-users.

- **What are good resources for learning R?**  
Check out the [Inside-R](http://www.inside-r.org/) web site,[CRAN](http://cran.r-project.org/) (particularly the [task views](http://cran.r-project.org/web/views/)) and [crantastic](http://crantastic.org/). Also, you can download R and a GUI-based integrated development environment (IDE) to practise there.

- **Where can I get more help?**  
Please post on the Octopus [user mailing list](https://groups.google.com/forum/#!forum/octopus-user). We'll be glad to help there!