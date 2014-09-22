package pasalab.dfs.perf.benchmark.metadata;

import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.benchmark.SimpleTask;
import pasalab.dfs.perf.conf.PerfConf;

public class MetadataTask extends SimpleTask {
  @Override
  public String cleanupWorkspace() {
    return PerfConf.get().DFS_DIR + "/metadata";
  }

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    String workDir = PerfConf.get().DFS_DIR + "/metadata/" + mId;
    mTaskConf.addProperty("work.dir", workDir);
    LOG.info("Work dir " + workDir);
    return true;
  }
}
