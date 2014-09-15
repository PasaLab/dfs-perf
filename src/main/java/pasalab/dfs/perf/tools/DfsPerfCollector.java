package pasalab.dfs.perf.tools;

import java.io.File;
import java.io.IOException;

import pasalab.dfs.perf.basic.PerfTotalReport;
import pasalab.dfs.perf.basic.TaskType;
import pasalab.dfs.perf.conf.PerfConf;

/**
 * Generate a total report for the specified test.
 */
public class DfsPerfCollector {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Wrong program arguments. Should be <TaskType> <reports dir>");
      System.exit(-1);
    }

    try {
      PerfTotalReport summaryReport = TaskType.get().getTotalReportClass(args[0]);
      summaryReport.initialSet(args[0]);
      File contextsDir = new File(args[1]);
      File[] contextFiles = contextsDir.listFiles();
      if (contextFiles == null || contextFiles.length == 0) {
        throw new IOException("No task context files exists under " + args[1]);
      }
      summaryReport.initialFromTaskContexts(contextFiles);
      String outputFileName = PerfConf.get().OUT_FOLDER + "/DfsPerfReport-" + args[0];
      summaryReport.writeToFile(new File(outputFileName));
      System.out.println("Report generated at " + outputFileName);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Failed to generate Dfs-Perf-Report");
      System.exit(-1);
    }
  }
}
