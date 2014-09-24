package pasalab.dfs.perf.benchmark;

import pasalab.dfs.perf.basic.PerfTask;
import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.conf.PerfConf;

public class SimpleTask extends PerfTask {
  @Override
  public String getCleanupDir() {
    return null;
  }

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    String workspacePath = PerfConf.get().DFS_DIR;
    LOG.info("DFS workspace " + workspacePath);
    return true;
  }

  @Override
  protected boolean cleanupTask(TaskContext taskContext) {
    return true;
  }
}
