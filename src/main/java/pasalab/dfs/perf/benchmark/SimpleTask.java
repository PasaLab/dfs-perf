package pasalab.dfs.perf.benchmark;

import pasalab.dfs.perf.basic.PerfTask;
import pasalab.dfs.perf.basic.Supervisible;
import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.conf.PerfConf;

public class SimpleTask extends PerfTask implements Supervisible {
  @Override
  public String cleanupWorkspace() {
    return null;
  }

  @Override
  public String getDfsFailedPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-Failed";
  }

  @Override
  public String getDfsReadyPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-Ready";
  }

  @Override
  public String getDfsSuccessPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-Success";
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
