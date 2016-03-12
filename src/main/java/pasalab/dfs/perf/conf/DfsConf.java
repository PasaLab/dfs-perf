package pasalab.dfs.perf.conf;

import com.google.common.collect.ImmutableList;

public class DfsConf extends Utils {
  private static DfsConf sDfsConf = null;

  public static synchronized DfsConf get() {
    if (sDfsConf == null) {
      sDfsConf = new DfsConf();
    }
    return sDfsConf;
  }

  // TODO: Add and get from properties
  public final ImmutableList<String> GLUSTER_PREFIX = ImmutableList.of("glusterfs://");
  public final ImmutableList<String> HDFS_PREFIX = ImmutableList.of("hdfs://");
  public final ImmutableList<String> LFS_PREFIX = ImmutableList.of("file://");
  public final ImmutableList<String> TFS_PREFIX = ImmutableList.of("alluxio://");
  public final ImmutableList<String> TFS_HADOOP_PREFIX = ImmutableList.of("alluxio-tfs://");

  public final String GLUSTERFS_IMPL;
  public final String GLUSTERFS_VOLUMES;
  public final String GLUSTERFS_MOUNTS;

  public final String HDFS_IMPL;

  private DfsConf() {
    GLUSTERFS_IMPL =
        getProperty("pasalab.dfs.glusterfs.impl",
            "org.apache.hadoop.fs.glusterfs.GlusterFileSystem");
    GLUSTERFS_VOLUMES = getProperty("pasalab.dfs.perf.glusterfs.volumes", null);
    GLUSTERFS_MOUNTS = getProperty("pasalab.dfs.perf.glusterfs.mounts", null);

    HDFS_IMPL =
        getProperty("pasalab.dfs.perf.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
  }
}
