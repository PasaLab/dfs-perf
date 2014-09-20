package pasalab.dfs.perf.benchmark.iterate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pasalab.dfs.perf.basic.PerfThread;
import pasalab.dfs.perf.benchmark.SimpleTaskContext;

public class IterateTaskContext extends SimpleTaskContext {
  @Override
  public void setFromThread(PerfThread[] threads) {
    mAdditiveStatistics = new HashMap<String, List<Double>>(1);
    List<Double> readThroughputs = new ArrayList<Double>(threads.length);
    List<Double> writeThroughputs = new ArrayList<Double>(threads.length);
    for (PerfThread thread : threads) {
      if (!((IterateThread) thread).getSuccess()) {
        mSuccess = false;
      }
      readThroughputs.add(((IterateThread) thread).getReadThroughput());
      writeThroughputs.add(((IterateThread) thread).getWriteThroughput());
    }
    mAdditiveStatistics.put("ReadThroughput(MB/s)", readThroughputs);
    mAdditiveStatistics.put("WriteThroughput(MB/s)", writeThroughputs);
  }
}
