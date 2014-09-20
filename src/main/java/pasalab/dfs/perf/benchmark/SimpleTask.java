package pasalab.dfs.perf.benchmark;

import java.io.IOException;

import pasalab.dfs.perf.basic.PerfTask;
import pasalab.dfs.perf.basic.Supervisible;
import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public abstract class SimpleTask extends PerfTask implements Supervisible {
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
    try {
      PerfFileSystem fs = PerfFileSystem.get(PerfConf.get().DFS_ADDRESS);
      String workspacePath = PerfConf.get().DFS_DIR;
      if (!fs.exists(workspacePath)) {
        fs.mkdirs(workspacePath, true);
      }
      LOG.info("DFS workspace " + workspacePath);
    } catch (IOException e) {
      LOG.error("Failed to setup task " + mId, e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean cleanupTask(TaskContext taskContext) {
    return true;
  }
}
