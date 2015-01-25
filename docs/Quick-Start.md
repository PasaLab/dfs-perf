---
layout: global
title: Quick Start
---

This short tutorial provides a quick introduction to how to use Octopus. We will first introduce some basic APIs, then show how to write applications. See the [API Docs](API-Docs.html) for a more complete reference.

To follow along with this guide, first install the Octopus in either [local mode](Running-Octopus-Locally.html) or [cluster mode](Running-Octopus-on-a-Cluster.html).

# Interactive Analysis with the R Shell

Octopus provides a R package `OctMatrix` so you can easily use with R shell. Here we define `engineType <- "R"`, which means Octopus runs in local R. You can change it to "Spark", "Hadoop" or "Mpi" in cluster mode as your wish.

    $ R
    > require(OctMatrix)
    > engineType <- "R"

## Creating OctMatrix

The constructor of OctMatrix is `OctMatrix(data, nrow = 1, ncol = 1, engineType = "R", byrow = FALSE)`, which is similar with R-Matrix. `data` can be a vector or matrix; `nrow` and `ncol` determine the size; `engineType` represents the under computing framework; `byrow` means the matrix is filled by rows or columns.

Creating a very simple 2x2 OctMatrix:

    > m <- OctMatrix(1:4, 2, 2, engineType)
    
Creating a simple 2x2 OctMatrix by row:

    > d <- c(2, 0, 1, 5)
    > m <- OctMatrix(d, 2, 2, engineType, TRUE)
    
Creating a OctMatrix by R-Matrix:

    > rm <- matrix(1:4, 2, 2)
    > m <- OctMatrix(rm, engineType = engineType)
    
Creating a OctMatrix from csv data:

    > d <- read.csv("mydata", header = 0)
    > m <- OctMatrix(as.matrix(d), engineType = engineType)
    
Creating ones/zeros OctMatrix:

    > o <- ones(2, 2, engineType)
    > z <- zeros(2, 2, engineType)

Output and Input OctMatrix. Here we take local file system as example, and you can change it to HDFS (hdfs://ip:port/somewhere) or Tachyon (tachyon://ip:port/somewhere) in cluster mode as your wish:

    > storePath <- "file:///tmp/octopus-test/matrix-a"
    > a <- OctMatrix(1:4, 2, 2, engineType)
    > WriteOctMatrix(a, storePath)
    > b <- ReadOctMatrix(storePath, engineType)

## More on OctMatrix Operations

OctMatrix have a set of operations that can be used for more complex computations.

Get element(s):

    > a <- OctMatrix(1:6, 2, 3, engineType)
    > b <- a[1,]
    > c <- a[,2:3]
    > d <- a[2,1]
    > e <- a[1,2:3]

The numerical/elemwise arithmetic operations:

    > a <- OctMatrix(1:4, 2, 2, engineType)
    > b <- OctMatrix(5:8, 2, 2, engineType)
    > c1 <- a + b
    > c2 <- 1 + b
    > c3 <- a - b
    > c4 <- a - 2
    > c5 <- a * b
    > c6 <- 3 * a
    > c7 <- a / b
    > c8 <- b / 4
    
Matrix Multiplication:

    > a <- OctMatrix(1:4, 1, 4, engineType)
    > b <- OctMatrix(5:8, 4, 1, engineType)
    > c <- a %*% b

Statistics of OctMatrix:

    > a <- OctMatrix(1:6, 2, 3, engineType)
    > dim(a)
    > length(a)
    > max(a)
    > min(a)
    > mean(a)
    > sum(a)

Transform OctMatrix to R-Matrix:

    > a <- OctMatrix(1:4, 2, 2, engineType)
    > ra <- as.matrix(a)
    
Bind two OctMatrices via column:

    > a <- OctMatrix(1:4, 2, 2, engineType)
    > b <- OctMatrix(5:8, 2, 2, engineType)
    > c <- cbind2(a, b)
    
The transpose of a OctMatrix:

    > a <- OctMatrix(1:4, 2, 2, engineType)
    > ta <- t(a)
    
The inv of a square OctMatrix:

    > a <- OctMatrix(1:4, 2, 2, engineType)
    > ia <- inv(a)
    
More operations such as `repeat`, `split`, etc. are listed in the [API doc](API-Docs.html).

## Apply

Octopus provides `apply` function which is similar with the one of R-Matrix. The form is `apply(X, MARGIN, FUN)`. `X` is an OctMatrix; `MARGIN` should be one of `1`, `2` or `c(1, 2)`, indicates rows, columns or rows and columns; `FUN` is the function applied.

    > a <- OctMatrix(1:4, 2, 2, engineType)
    > b <- apply(a, 1, sum)
    > c <- apply(a, 2, sum)
    > d <- apply(a, c(1, 2), sin)

# Self-Contained Applications

Now say we wanted to write a self-contained application using the OctMatrix API. We’ll create a very simple OctMatrix application in R script to compute the linear regression of two variables.

First, we read data from csv files, where "x.csv" contains the data of `x` and "y.csv" contains the data of `y`. Of course you can generate your data in other ways, e.g. `data <- read.csv("data.csv", header = 0)`, `x <- OctMatrix(as.matrix(datax[,1]))`, `y <- OctMatrix(as.matrix(datax[,2]))`. Then, we use `cbind2`, `inv`, `t` and the matrix multiplication functions to calculate `beta` parameter of linear regression.

    require(OctMatrix)
    engineType <- "Spark"
    
    datax <- read.csv("x.csv", header = 0)
    datay <- read.csv("y.csv", header = 0)
    x <- OctMatrix(as.matrix(datax[,1]), engineType = engineType)
    y <- OctMatrix(as.matrix(datay[,1]), engineType = engineType)
    
    x <- cbind2(x, ones(ncol = 1, nrow = dim(x)[1]))
    beta <- inv(t(x) %*% x) %*% t(x) %*% y
    beta

# Where to Go from Here

Congratulations on running your first Octopus application!

-    For an in-depth overview of the API, see “Programming Guides” menu for other components.
-    For running applications on a cluster, head to the [Running Octopus on a Cluster](Running-Octopus-on-a-Cluster.html).
-    Finally, Octopus includes several samples in the examples directory "$OCTOPUS_HOME/R/examples".

