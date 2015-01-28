---
layout: global
title: Examples
---

This short tutorial provides an introduction to how to run DFS-Perf. 

To follow along with this guide, first install the DFS-Perf in either [local mode](Running-DFS-Perf-Locally.html) or [cluster mode](Running-DFS-Perf-on-a-Cluster.html).

# Check the workload configurations
DFS-Perf puts the workload configuration file at `conf/testsuite/{workload.name}`. Before running the workload you can custom it by modifying the configuration file. See [Configuration Settings](Configuration-Settings.html) for more details.

# Clean up the workspace
Before running a workload, if you want to clean up the DFS-Perf workspace by the following command.

    $ bin/dfs-perf-clean

# Run a workload
After setting up all the configurations, you can run a workload with the very simple command `bin/dfs-perf {workload.name}`, e.g.

    $ bin/dfs-perf Metadata

# Abort a workload
When a workload is running, you can abort it by this command in another terminal.

    $ bin/dfs-perf-abort

# Generate result
After finishing a workload succesfully, the command `bin/dfs-perf-collect {workload.name}` is used to collect the context on each node and generate a total result.

    $ bin/dfs-perf-collect Metadata