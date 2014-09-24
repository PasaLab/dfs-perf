package pasalab.dfs.perf.benchmark.simpleWrite;

import pasalab.dfs.perf.basic.PerfTaskContext;
import pasalab.dfs.perf.benchmark.SimpleTask;
import pasalab.dfs.perf.conf.PerfConf;

public class SimpleWriteTask extends SimpleTask {
  @Override
  protected boolean setupTask(PerfTaskContext taskContext) {
    String writeDir = PerfConf.get().DFS_DIR + "/simple-read-write/" + mId;
    mTaskConf.addProperty("write.dir", writeDir);
    LOG.info("Write dir " + writeDir);
    return true;
  }
}
