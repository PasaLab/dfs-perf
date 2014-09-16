package pasalab.dfs.perf.benchmark.createfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pasalab.dfs.perf.basic.PerfTask;
import pasalab.dfs.perf.basic.Supervisible;
import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class CreateFileTask extends PerfTask implements Supervisible {
  private CreateFileThread[] mCreateFileThreads;
  private List<Thread> mCreateFileThreadsList;

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    PerfConf perfConf = PerfConf.get();
    ((CreateFileTaskContext) taskContext).initial();
    try {
      PerfFileSystem fs = PerfFileSystem.get(perfConf.DFS_ADDRESS);
      String workDir = perfConf.DFS_DIR + "/CreateFile";
      if (!fs.exists(workDir)) {
        fs.mkdirs(workDir, true);
        LOG.info("Create the work dir " + workDir);
      }
      fs.close();

      int threadsNum = mTaskConf.getIntProperty("threads.num");
      mCreateFileThreads = new CreateFileThread[threadsNum];
      for (int i = 0; i < threadsNum; i ++) {
        mCreateFileThreads[i] =
            new CreateFileThread(mId * threadsNum + i,
                mTaskConf.getIntProperty("files.per.thread"),
                mTaskConf.getIntProperty("file.length.bytes"), workDir);
      }
      LOG.info("Create " + threadsNum + " create file threads");
    } catch (IOException e) {
      LOG.error("Error when setup create file task", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean runTask(TaskContext taskContext) {
    mCreateFileThreadsList = new ArrayList<Thread>(mCreateFileThreads.length);
    for (int i = 0; i < mCreateFileThreads.length; i ++) {
      Thread createFileThread = new Thread(mCreateFileThreads[i]);
      mCreateFileThreadsList.add(createFileThread);
      createFileThread.start();
    }
    try {
      boolean running = true;
      int interval = mTaskConf.getIntProperty("interval.seconds");
      int count = 0;
      ((CreateFileTaskContext) taskContext).UpdateSuccessFiles(0);
      while (running) {
        Thread.sleep(1000);
        running = false;
        count ++;
        for (Thread thread : mCreateFileThreadsList) {
          if (thread.isAlive()) {
            running = true;
          }
        }
        if (!running || count % interval == 0) {
          int files = 0;
          for (CreateFileThread createFileThread : mCreateFileThreads) {
            files += createFileThread.getSuccessFiles();
          }
          ((CreateFileTaskContext) taskContext).UpdateSuccessFiles(files);
          count = 0;
        }
      }
    } catch (InterruptedException e) {
      LOG.error("Error when wait all threads", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean cleanupTask(TaskContext taskContext) {
    taskContext.setSuccess(true);
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
