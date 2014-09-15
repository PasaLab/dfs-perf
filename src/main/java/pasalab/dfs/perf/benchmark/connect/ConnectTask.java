package pasalab.dfs.perf.benchmark.connect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pasalab.dfs.perf.basic.PerfTask;
import pasalab.dfs.perf.basic.Supervisible;
import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class ConnectTask extends PerfTask implements Supervisible {
  private ConnectThread[] mConnectThreads;
  private List<Thread> mConnectThreadsList;

  @Override
  protected boolean cleanupTask(TaskContext taskContext) {
    taskContext.setSuccess(true);
    ((ConnectTaskContext) taskContext).setFromConnectThreads(mConnectThreads);
    return true;
  }

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    PerfConf perfConf = PerfConf.get();
    try {
      PerfFileSystem fs = PerfFileSystem.get(perfConf.DFS_ADDRESS);
      String workDir = perfConf.DFS_DIR + "/" + mId;
      if (fs.exists(workDir)) {
        fs.delete(workDir, true);
        LOG.warn("The work dir " + workDir + " already exists, delete it");
      }
      fs.mkdirs(workDir, true);
      LOG.info("Create the write dir " + workDir);
      fs.close();

      int threadsNum = mTaskConf.getIntProperty("threads.num");
      int clientsPerThread = mTaskConf.getIntProperty("clients.per.thread");
      int opsPerThread = mTaskConf.getIntProperty("ops.per.thread");
      mConnectThreads = new ConnectThread[threadsNum];
      for (int i = 0; i < threadsNum; i++) {
        mConnectThreads[i] = new ConnectThread(i, clientsPerThread, opsPerThread, workDir);
      }
      LOG.info("Create " + threadsNum + " connect threads");
    } catch (IOException e) {
      LOG.error("Error when setup connect task", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean runTask(TaskContext taskContext) {
    mConnectThreadsList = new ArrayList<Thread>(mConnectThreads.length);
    for (int i = 0; i < mConnectThreads.length; i++) {
      Thread connectThread = new Thread(mConnectThreads[i]);
      mConnectThreadsList.add(connectThread);
      connectThread.start();
    }
    try {
      for (Thread thread : mConnectThreadsList) {
        thread.join();
      }
    } catch (InterruptedException e) {
      LOG.error("Error when wait all threads", e);
      return false;
    }
    return true;
  }

  @Override
  public boolean cleanupWorkspace() {
    return true;
  }

  @Override
  public String getDfsFailedPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-FAILED";
  }

  @Override
  public String getDfsReadyPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-READY";
  }

  @Override
  public String getDfsSuccessPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "-SUCCESS";
  }
}
