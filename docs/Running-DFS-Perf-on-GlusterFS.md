---
layout: global
title: Running DFS-Perf on GlusterFS--
---

To run Octopus on MPI, you must install Octopus in [cluster mode](Running-Octopus-on-a-Cluster.html)

# Configurations
Before installing the OctMatrix R package, you should set these MPI-related variables in `conf/octopus-env.R`:

    # Set the the home directory path of  MPI
    # Ignore this if you never about to using MPI as a underlying executing engine.
    OCTOPUS_MPI_HOME="{where.your.MPI}"

# Usages
When using OctMatrix package in R script, set the engineType to be "Mpi" then the matrix will be handled by MPI.

    a <- OctMatrix(data, nrow, ncol, "Mpi", byrow)