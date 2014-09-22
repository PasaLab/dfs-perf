package pasalab.dfs.perf.tools;

import java.io.IOException;

import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class DfsPerfCleaner {
  public static void main(String[] args) {
    try {
      PerfFileSystem fs = PerfFileSystem.get(PerfConf.get().DFS_ADDRESS);
      fs.delete(PerfConf.get().DFS_DIR, true);
      fs.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Failed to clean workspace " + PerfConf.get().DFS_DIR);
    }
    System.out.println("Clean the workspace " + PerfConf.get().DFS_DIR);
  }
}
