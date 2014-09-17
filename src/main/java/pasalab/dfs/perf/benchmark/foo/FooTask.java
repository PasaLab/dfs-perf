package pasalab.dfs.perf.benchmark.foo;

import pasalab.dfs.perf.basic.PerfTask;
import pasalab.dfs.perf.basic.Supervisible;
import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.conf.PerfConf;

public class FooTask extends PerfTask implements Supervisible {

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    mTaskConf.addProperty(mNodeName, mNodeName);
    return true;
  }

  @Override
  protected boolean cleanupTask(TaskContext taskContext) {
    return "true".equals(mTaskConf.getProperty("foo.end"));
  }

  @Override
  public String cleanupWorkspace() {
    return PerfConf.get().DFS_DIR;
  }

  @Override
  public String getDfsFailedPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-failed";
  }

  @Override
  public String getDfsReadyPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-ready";
  }

  @Override
  public String getDfsSuccessPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-success";
  }

}
