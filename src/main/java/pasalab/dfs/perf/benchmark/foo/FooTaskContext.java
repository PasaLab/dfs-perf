package pasalab.dfs.perf.benchmark.foo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pasalab.dfs.perf.basic.PerfThread;
import pasalab.dfs.perf.basic.TaskContext;

public class FooTaskContext extends TaskContext {

  @Override
  public void loadFromFile(File file) throws IOException {
    return;
  }

  @Override
  public void setFromThread(PerfThread[] threads) {
    LOG.info(threads.length);
  }

  @Override
  public void writeToFile(File file) throws IOException {
    BufferedWriter fout = new BufferedWriter(new FileWriter(file));
    fout.write(mId + "@" + mNodeName);
    fout.close();
  }

}
