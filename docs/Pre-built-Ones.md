---
layout: global
title: Environment Setup
---

# Prerequisites
To run Octopus, several prerequisite softwares are needed on your single node or cluster. The more detailed prerequisites can be found in [Running Octopus Locally](Running-Octopus-Locally.html) and [Running Octopus on a Cluster](Running-Octopus-on-a-Cluster.html).

In local mode, you need Java (JDK 6 or above), R (3.0 or above) with rJava package.

In cluster mode, you need Java (JDK 6 or above), R (3.0 or above) with rJava and Rserve package. And you can choose one Distributed File System and at least one Distributed Computing Framework from Spark, Hadoop, MPI.

# Pre-built Ones
We have pre-built Octopus with different dependencies versions. Actually, Octopus uses the stable APIs of the underlying computing frameworks such as Spark/Hadoop. Thus, our pre-built Octopus can run well on various Spark/Hadoop versions. If you are interested in using Octopus, please refer [Download](Download.html).

Octopus-0.1--Spark-1.0.1--Hadoop-2.3.0--Tachyon-0.6.0-SNAPSHOT--OpenMPI-1.8.3

## Native Library Dependencies
Octopus-Spark uses the linear algebra package [Breeze](http://www.scalanlp.org/), which depends on [netlib-java](https://github.com/fommil/netlib-java), and [jblas](https://github.com/mikiobraun/jblas). `netlib-java` and `jblas` depend on native Fortran routines. You need to install the [gfortran runtime library](https://github.com/mikiobraun/jblas/wiki/Missing-Libraries) if it is not already present on your nodes. Octopus-Spark will throw a linking error if it cannot detect these libraries automatically. Due to license issues, we do not include `netlib-java`’s native libraries in Octopus-Spark's dependency set under default settings. If no native library is available at runtime, you will see a warning message. To use native libraries from `netlib-java`, please build Spark with `-Pnetlib-lgpl` or include `com.github.fommil.netlib:all:1.1.2` as a dependency of your project. If you want to use optimized BLAS/LAPACK libraries such as [OpenBLAS](http://www.openblas.net/), please link its shared libraries to `/usr/lib/libblas.so.3` and `/usr/lib/liblapack.so.3`, respectively. BLAS/LAPACK libraries on worker nodes should be built without multithreading. In more detail, you can refer this [page](https://github.com/PasaLab/marlin/wiki/How-to-load-native-linear-algebra-library) to see how to enable Spark to load native linear algebra library successfully.