package pasalab.dfs.perf.benchmark.iterate;

import pasalab.dfs.perf.basic.PerfTaskContext;
import pasalab.dfs.perf.benchmark.SimpleTask;
import pasalab.dfs.perf.conf.PerfConf;

public class IterateTask extends SimpleTask {
  @Override
  public String getCleanupDir() {
    return PerfConf.get().DFS_DIR + "/iterate";
  }

  @Override
  protected boolean setupTask(PerfTaskContext taskContext) {
    String workDir = PerfConf.get().DFS_DIR + "/iterate";
    mTaskConf.addProperty("work.dir", workDir);
    LOG.info("Work dir " + workDir);
    return true;
  }
}
