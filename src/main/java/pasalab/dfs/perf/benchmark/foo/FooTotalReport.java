package pasalab.dfs.perf.benchmark.foo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pasalab.dfs.perf.basic.PerfTotalReport;
import pasalab.dfs.perf.basic.TaskContext;

public class FooTotalReport extends PerfTotalReport {
  private int num;

  @Override
  public void initialFromTaskContexts(TaskContext[] taskContexts) throws IOException {
    num = taskContexts.length;
  }

  @Override
  public void writeToFile(File file) throws IOException {
    BufferedWriter fout = new BufferedWriter(new FileWriter(file));
    fout.write(mTaskType + "~" + num);
    fout.close();
  }

}
