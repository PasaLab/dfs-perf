package pasalab.dfs.perf.benchmark.massive;

import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.benchmark.SimpleTask;
import pasalab.dfs.perf.conf.PerfConf;

public class MassiveTask extends SimpleTask {
  @Override
  public String getCleanupDir() {
    return PerfConf.get().DFS_DIR + "/massive";
  }

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    String workDir = PerfConf.get().DFS_DIR + "/massive";
    mTaskConf.addProperty("work.dir", workDir);
    LOG.info("Work dir " + workDir);
    return true;
  }
}
