package pasalab.dfs.perf.benchmark.simpleread;

import java.io.IOException;

import pasalab.dfs.perf.basic.PerfTaskContext;
import pasalab.dfs.perf.benchmark.SimpleTask;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class SimpleReadTask extends SimpleTask {
  @Override
  protected boolean setupTask(PerfTaskContext taskContext) {
    try {
      PerfFileSystem fs = PerfFileSystem.get(PerfConf.get().DFS_ADDRESS, null);
      fs.connect();
      String readDir = PerfConf.get().DFS_DIR + "/simple-read-write/" + mId;
      if (!fs.exists(readDir)) {
        throw new IOException("No data to read at " + readDir);
      }
      fs.close();
      mTaskConf.addProperty("read.dir", readDir);
      LOG.info("Read dir " + readDir);
    } catch (IOException e) {
      LOG.error("Failed to setup task " + mId, e);
      return false;
    }
    return true;
  }
}
