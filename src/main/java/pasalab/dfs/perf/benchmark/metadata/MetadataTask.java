package pasalab.dfs.perf.benchmark.metadata;

import java.io.IOException;

import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.benchmark.SimpleTask;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class MetadataTask extends SimpleTask {
  @Override
  public String cleanupWorkspace() {
    return PerfConf.get().DFS_DIR + "/metadata";
  }

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    try {
      PerfFileSystem fs = PerfFileSystem.get(PerfConf.get().DFS_ADDRESS);
      String workDir = PerfConf.get().DFS_DIR + "/metadata/" + mId;
      if (fs.exists(workDir)) {
        fs.delete(workDir, true);
        LOG.info("Work dir " + workDir + "already exists, delete it");
      }
      fs.mkdirs(workDir, true);
      mTaskConf.addProperty("work.dir", workDir);
      LOG.info("Work dir " + workDir);
    } catch (IOException e) {
      LOG.error("Failed to setup task " + mId, e);
      return false;
    }
    return true;
  }
}
