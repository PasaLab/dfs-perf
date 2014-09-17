package pasalab.dfs.perf;

import org.apache.log4j.Logger;

import pasalab.dfs.perf.basic.PerfTask;
import pasalab.dfs.perf.basic.TaskConfiguration;
import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.basic.TaskType;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

/**
 * Entry point for a DFS-Perf process
 */
public class DfsPerf {
  private static final Logger LOG = Logger.getLogger(PerfConstants.PERF_LOGGER_TYPE);

  public static void main(String[] args) {
    if (args.length < 3) {
      LOG.error("Wrong program arguments. Should be <NODENAME> <NODEID> <TaskType>"
          + "See more in bin/dfs-perf");
      System.exit(-1);
    }

    String nodeName = null;
    int nodeId = -1;
    String taskType = null;
    try {
      nodeName = args[0];
      nodeId = Integer.parseInt(args[1]);
      taskType = args[2];
    } catch (Exception e) {
      LOG.error("Failed to parse the input args", e);
      System.exit(-1);
    }

    try {
      PerfFileSystem fs = PerfFileSystem.get(PerfConf.get().DFS_ADDRESS);
      while (!fs.exists(PerfConf.get().DFS_DIR + "/SYNC_START_SIGNAL")) {
        Thread.sleep(500);
      }
      fs.close();

      TaskConfiguration taskConf = TaskConfiguration.get(taskType, true);
      PerfTask task = TaskType.get().getTaskClass(taskType);
      task.initialSet(nodeId, nodeName, taskConf, taskType);
      TaskContext taskContext = TaskType.get().getTaskContextClass(taskType);
      taskContext.initialSet(nodeId, nodeName, taskType);
      if (!task.setup(taskContext)) {
        LOG.error("Failed to setup task");
        System.exit(-1);
      }
      if (!task.run(taskContext)) {
        LOG.error("Failed to start task");
        taskContext.setSuccess(false);
      }
      if (!task.cleanup(taskContext)) {
        LOG.error("Failed to cleanup the task");
        System.exit(-1);
      }
    } catch (Exception e) {
      LOG.error("Error in task", e);
    }
  }
}
