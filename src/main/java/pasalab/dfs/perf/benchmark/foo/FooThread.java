package pasalab.dfs.perf.benchmark.foo;

import pasalab.dfs.perf.basic.PerfThread;
import pasalab.dfs.perf.basic.TaskConfiguration;

public class FooThread extends PerfThread {
  private int interval;

  @Override
  public void run() {
    // TODO Auto-generated method stub
    try {
      Thread.sleep(interval);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public boolean setupThread(TaskConfiguration taskConf) {
    interval = taskConf.getIntProperty("foo.interval");
    return true;
  }

  @Override
  public boolean cleanupThread(TaskConfiguration taskConf) {
    taskConf.addProperty("foo.end", "true");
    return true;
  }

}
