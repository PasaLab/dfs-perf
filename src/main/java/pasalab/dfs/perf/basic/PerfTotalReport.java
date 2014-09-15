package pasalab.dfs.perf.basic;

import java.io.File;
import java.io.IOException;

/**
 * The abstract class of DFS-Perf Total Report. For new test, if you want DfsPerfCollector to
 * generate a total report for you, you should create a new class which extends this.
 */
public abstract class PerfTotalReport {

  protected String mTaskType;

  public void initialSet(String taskType) {
    mTaskType = taskType;
  }

  /**
   * Load the statistics of all the nodes and initial this total report.
   * 
   * @param taskContextFiles the statistics files for all the nodes
   * @throws IOException
   */
  public abstract void initialFromTaskContexts(File[] taskContextFiles) throws IOException;

  /**
   * Output this total report to file.
   * 
   * @param file the output file
   * @throws IOException
   */
  public abstract void writeToFile(File file) throws IOException;
}
