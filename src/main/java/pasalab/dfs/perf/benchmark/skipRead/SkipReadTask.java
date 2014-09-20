package pasalab.dfs.perf.benchmark.skipRead;

import java.io.IOException;

import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.benchmark.SimpleTask;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class SkipReadTask extends SimpleTask {
  @Override
  protected boolean setupTask(TaskContext taskContext) {
    try {
      PerfFileSystem fs = PerfFileSystem.get(PerfConf.get().DFS_ADDRESS);
      // use the SimpleWrite test data
      String readDir = PerfConf.get().DFS_DIR + "/simple-read-write/" + mId;
      if (!fs.exists(readDir)) {
        throw new IOException("No data to read at " + readDir);
      }
      mTaskConf.addProperty("read.dir", readDir);
      LOG.info("Read dir " + readDir);
    } catch (IOException e) {
      LOG.error("Failed to setup task " + mId, e);
      return false;
    }
    return true;
  }
}
