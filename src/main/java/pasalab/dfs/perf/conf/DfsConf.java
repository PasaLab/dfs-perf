package pasalab.dfs.perf.conf;

import com.google.common.collect.ImmutableList;

public class DfsConf extends Utils {
  private static DfsConf DFS_CONF = null;

  public static synchronized DfsConf get() {
    if (DFS_CONF == null) {
      DFS_CONF = new DfsConf();
    }
    return DFS_CONF;
  }

  // TODO: Add and get from properties
  public final ImmutableList<String> HDFS_PREFIX = ImmutableList.of("hdfs://");
  public final ImmutableList<String> LFS_PREFIX = ImmutableList.of("file://");
  public final ImmutableList<String> TFS_PREFIX = ImmutableList.of("tachyon://");

  public final String HDFS_IMPL;

  private DfsConf() {
    HDFS_IMPL =
        getProperty("pasalab.dfs.perf.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
  }
}
