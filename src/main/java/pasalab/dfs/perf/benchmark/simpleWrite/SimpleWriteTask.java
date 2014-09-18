package pasalab.dfs.perf.benchmark.simpleWrite;

import java.io.IOException;

import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.benchmark.SimpleTask;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class SimpleWriteTask extends SimpleTask {
  @Override
  protected boolean setupTask(TaskContext taskContext) {
    ((SimpleWriteTaskContext) taskContext).initial(mTaskConf);
    try {
      PerfFileSystem fs = PerfFileSystem.get(PerfConf.get().DFS_ADDRESS);
      String writeDir = PerfConf.get().DFS_DIR + "/" + mId;
      if (fs.exists(writeDir)) {
        fs.delete(writeDir, true);
        LOG.info("Write dir " + writeDir + "already exists, delete if");
      }
      fs.mkdirs(writeDir, true);
      mTaskConf.addProperty("write.dir", writeDir);
      LOG.info("Write dir " + writeDir);
    } catch (IOException e) {
      LOG.error("Failed to setup task " + mId, e);
      return false;
    }
    return true;
  }
}
