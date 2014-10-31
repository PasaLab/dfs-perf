package pasalab.dfs.perf.conf;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * DFS-Perf Configurations
 */
public class PerfConf extends Utils {
  private static final Logger LOG = Logger.getLogger("");

  private static PerfConf sPerfConf = null;

  public static synchronized PerfConf get() {
    if (sPerfConf == null) {
      sPerfConf = new PerfConf();
    }
    return sPerfConf;
  }

  public final String DFS_PERF_HOME;

  public final String OUT_FOLDER;
  public final boolean STATUS_DEBUG;
  public final String DFS_ADDRESS;
  public final String DFS_DIR;
  public final int THREADS_NUM;

  public final boolean FAILED_THEN_ABORT;
  public final int FAILED_PERCENTAGE;

  public final String DFS_PERF_MASTER_HOSTNAME;
  public final int DFS_PERF_MASTER_PORT;
  public final long UNREGISTER_TIMEOUT_MS;

  private PerfConf() {
    if (System.getProperty("pasalab.dfs.perf.home") == null) {
      LOG.warn("pasalab.dfs.perf.home is not set."
          + "Using /tmp/dfs_perf_default_home as the default value.");
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
    THREADS_NUM = getIntProperty("pasalab.dfs.perf.threads.num", 1);

    FAILED_THEN_ABORT = getBooleanProperty("pasalab.dfs.perf.failed.abort", true);
    FAILED_PERCENTAGE = getIntProperty("pasalab.dfs.perf.failed.percentage", 1);

    DFS_PERF_MASTER_HOSTNAME = getProperty("pasalab.dfs.perf.master.hostname", "localhost");
    DFS_PERF_MASTER_PORT = getIntProperty("pasalab.dfs.perf.master.port", 23333);
    UNREGISTER_TIMEOUT_MS = getLongProperty("pasalab.dfs.perf.unregister.timeout.ms", 10000);
  }
}
