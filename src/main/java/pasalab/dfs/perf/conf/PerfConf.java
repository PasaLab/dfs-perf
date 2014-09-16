package pasalab.dfs.perf.conf;

import java.io.File;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableList;

/**
 * DFS-Perf Configurations
 */
public class PerfConf extends Utils {
  private static final Logger LOG = Logger.getLogger("");

  private static PerfConf PERF_CONF = null;

  public static synchronized PerfConf get() {
    if (PERF_CONF == null) {
      PERF_CONF = new PerfConf();
    }
    return PERF_CONF;
  }

  public final String DFS_PERF_HOME;

  public final String OUT_FOLDER;
  public final boolean STATUS_DEBUG;
  public final String DFS_ADDRESS;
  public final String DFS_DIR;

  public final boolean FAILED_THEN_ABORT;
  public final int FAILED_PERCENTAGE;

  // TODO: Add and get from properties
  public final ImmutableList<String> HDFS_PREFIX = ImmutableList.of("hdfs://");
  public final ImmutableList<String> LFS_PREFIX = ImmutableList.of("file://");
  public final ImmutableList<String> TFS_PREFIX = ImmutableList.of("tachyon://");

  private PerfConf() {
    if (System.getProperty("pasalab.dfs.perf.home") == null) {
      LOG.warn("pasalab.dfs.perf.home is not set. Using /tmp/dfs_perf_default_home as the default value.");
      File file = new File("/tmp/dfs_perf_default_home");
      if (!file.exists()) {
        file.mkdirs();
      }
    }
    DFS_PERF_HOME = getProperty("pasalab.dfs.perf.home", "/tmp/dfs_perf_default_home");
    STATUS_DEBUG = getBooleanProperty("pasalab.dfs.perf.status.debug", false);
    DFS_ADDRESS = getProperty("pasalab.dfs.perf.dfs.address", "dfs://master:port");
    DFS_DIR = getProperty("pasalab.dfs.perf.dfs.dir", "/tmp/dfs-perf-workspace");
    OUT_FOLDER = getProperty("pasalab.dfs.perf.out.dir", DFS_PERF_HOME + "/result");

    FAILED_THEN_ABORT = getBooleanProperty("pasalab.dfs.perf.failed.abort", true);
    FAILED_PERCENTAGE = getIntProperty("pasalab.dfs.perf.failed.percentage", 1);
  }
}
