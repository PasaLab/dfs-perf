package pasalab.dfs.perf.basic;

import java.io.File;
import java.io.IOException;

/**
 * The abstract class of DFS-Perf Total Report. For new test, if you want DfsPerfCollector to
 * generate a total report for you, you should create a new class which extends this.
 */
public abstract class PerfTotalReport {

  protected String mTestCase;

  public void initialSet(String testCase) {
    mTestCase = testCase;
  }

  /**
   * Load the contexts of all the task slaves and initial this total report.
   * 
   * @param taskContexts the contexts for all the task slaves
   * @throws IOException
   */
  public abstract void initialFromTaskContexts(PerfTaskContext[] taskContexts) throws IOException;

  /**
   * Output this total report to file.
   * 
   * @param file the output file
   * @throws IOException
   */
  public abstract void writeToFile(File file) throws IOException;
}
