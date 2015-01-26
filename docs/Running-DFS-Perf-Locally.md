---
layout: global
title: Running Octopus Locally
---
This guide describes how to get Octopus running on local R for a quick spin in ~ 2 minutes.

# Prerequisites

The prerequisites for this part is that you have Java and R. RStudio is recommended to run your R script in a browser.

The OctMatrix package requires rJava package, so you also need to install rJava in your R. The very simple way is to run the following script in R shell:

    install.packages("rJava")

# Configurations
Prepare the binary distribution of Octopus:

    $ tar xvfz octopus-0.1-bin.tar.gz
    $ cd octopus-0.1

Before installing Octopus, requisite environment variables must be specified in `conf/octopus-env.R`

To run locally, these four variables in `conf/octopus-env.R` should be set as follows:

    # To run locally, set this to FALSE
    OCTOPUS_SPARK_START=FALSE
 
    # Set the home directory path of Octopus
    OCTOPUS_HOME="{where.your.octopus-0.1}"
     
    # To run locally, set this to local file system
    OCTOPUS_UNDERFS_ADDRESS="file://"
     
    # Set the data folder's path of Octopus in underlying file system
    OCTOPUS_WAREHOUSE="/tmp/octopus_warehouse"

Then, the following command should be added to make R be able to load the configurations. It can be added to `Rprofile.site` in the installation path of R, e.g. `/usr/lib64/R/etc/Rprofile.site`. Or added to `.Rprofile` in the user's home path, e.g. `~/.Rprofile`.

    source("{where.your.octopus-0.1}/conf/octopus-env.R")

Now, you can install Octopus by running:

    $ R CMD INSTALL {where.your.octopus-0.1}/R/pkg

Before using Octopus, you can format your Octopus Warehouse by running:

    $ sbin/octopus-format.sh

# Example
Now you can write a few R script to verify if the installed package `OctMatrix` works well.

    require(OctMatrix)
    a <- OctMatrix(1:4, 2, 2, "R")
    b <- OctMatrix(5:8, 2, 2, "R")
    c <- a + b
    c
    WriteOctMatrix(c, "file:///tmp/octoput-test-c")

You can see the correct result of `a + b` and the result `c` is output to the target path.
