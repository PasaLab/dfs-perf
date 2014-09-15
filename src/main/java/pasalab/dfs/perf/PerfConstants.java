package pasalab.dfs.perf;

/**
 * DFS-Perf contants
 */
public class PerfConstants {
  public static final String PERF_LOGGER_TYPE = System.getProperty("pasalab.dfs.perf.logger.type",
      "");
  public static final String[] PERF_MEMORY_UNITS = {"B", "KB", "MB", "GB", "TB", "PB", "EB"};
  public static final String PERF_CONTEXT_FILE_NAME_PREFIX = "context";

  public static final String HDFS_PREFIX = "hdfs://";
  public static final String LFS_PREFIX = "file://";
  public static final String TFS_PREFIX = "tachyon://";

  public static String parseSizeByte(long bytes) {
    float ret = bytes;
    int index = 0;
    while ((ret >= 1024) && (index < PERF_MEMORY_UNITS.length - 1)) {
      ret /= 1024;
      index++;
    }
    return ret + PERF_MEMORY_UNITS[index];
  }
}
