---
layout: global
title: Workloads
---

This guide briefly introduce the workloads built in DFS-Perf. Now we have x workloads in DFS-Perf. They are [Metadata Operations Workload](Workloads.html#metadata-operations-workload), [Sequential/Random Read/Write Workload](Workloads.html#sequentialrandom-readwrite-workload), [Mix Read/Write Workload](Workloads.html#mix-readwrite-workload), [Iterative Read/Wirte Workload](Workloads.html#iterative-readwirte-workload) and [Irregular Massive Access Workload](Workloads.html#irregular-massive-access-workload).

## Metadata Operations Workload
This workload performs metadata operations like creating, existing, renaming or deleting files and do not read/write any bytes from/to the files. Actually, it repeats performing metadata operations for a while and measures the performance as ops-per-second.

## Sequential/Random Read/Write Workload
This workload performs the basic throughput. It has three sub-workloads, which are *Sequential Write*, *Sequential Read* and *Random Read*. The sequential read/write workloads read/write a set of files byte-by-byte, and the random read workload reads files in a `skip->read` pattern.

## Mix Read/Write Workload
This workload mixes the read and write operations in a configurable ratio. It is much closer to the real-world applications with heavy read (e.g., hot data storage like online videos) or heavy write (e.g., historical data storage like trading information).

## Iterative Read/Wirte Workload
This workload represents the applications in which the output of the former iteration is the input of the next one. In more details, we provide two modes called *Shuffle* and *Non-Shuffle* respectively. In the *Shuffle* mode, it reads files from the whole workspace, which may lead to remote reading across the cluster network. In the *Non-Shuffle* mode, it only reads the files written by itself, which keeps good locality.

## Irregular Massive Access Workload
In this irregular massive access workload, files are read or written randomly and concurrently. This can reflect the throughput performance of a DFS cluster that is close to the reality. Again, similar to the Iterative Workload, there are *Shuffle* and *Non-Shuffle* modes.

    [large data]|read|read|read|...
                |write|write|write|...
                |write|read|read|...
                |read|read|write|...
                |read|write|read|...
                |write|write|read|...
                ...
