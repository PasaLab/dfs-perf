package pasalab.dfs.perf.benchmark.write;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pasalab.dfs.perf.basic.PerfTask;
import pasalab.dfs.perf.basic.Supervisible;
import pasalab.dfs.perf.basic.TaskContext;
import pasalab.dfs.perf.benchmark.ListGenerator;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

/**
 * The write test task. It will write files to DFS in multi-thread.
 */
public class WriteTask extends PerfTask implements Supervisible {
  private WriteThread[] mWriteThreads;
  private List<Thread> mWriteThreadsList;
  private String mWriteType;

  @Override
  protected boolean cleanupTask(TaskContext taskContext) {
    taskContext.setSuccess(true);
    ((WriteTaskContext) taskContext).setFromWriteThreads(mWriteThreads);
    return true;
  }

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    PerfConf perfConf = PerfConf.get();
    try {
      mWriteType = mTaskConf.getProperty("write.type");
      ((WriteTaskContext) taskContext).initial(mWriteType);
      PerfFileSystem fs = PerfFileSystem.get(perfConf.DFS_ADDRESS);
      String writeDir = perfConf.DFS_DIR + "/" + mId;
      if (fs.exists(writeDir)) {
        fs.delete(writeDir, true);
        LOG.warn("The write dir " + writeDir + " already exists, delete it");
      }
      fs.mkdirs(writeDir, true);
      LOG.info("Create the write dir " + writeDir);
      fs.close();

      int threadsNum = mTaskConf.getIntProperty("threads.num");
      List<String>[] writeFileList =
          ListGenerator.generateWriteFiles(threadsNum,
              mTaskConf.getIntProperty("files.per.thread"), writeDir);
      mWriteThreads = new WriteThread[threadsNum];
      for (int i = 0; i < threadsNum; i ++) {
        mWriteThreads[i] =
            new WriteThread(i, writeFileList[i], mWriteType,
                mTaskConf.getIntProperty("block.size.bytes"),
                mTaskConf.getLongProperty("file.length.bytes"),
                mTaskConf.getIntProperty("grain.bytes"));
      }
      LOG.info("Create " + threadsNum + " write threads");
    } catch (IOException e) {
      LOG.error("Error when setup write task", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean runTask(TaskContext taskContext) {
    mWriteThreadsList = new ArrayList<Thread>(mWriteThreads.length);
    for (int i = 0; i < mWriteThreads.length; i ++) {
      Thread writeThread = new Thread(mWriteThreads[i]);
      mWriteThreadsList.add(writeThread);
      writeThread.start();
    }
    try {
      for (Thread thread : mWriteThreadsList) {
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
    return false;
  }

  @Override
  public String getDfsFailedPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "/FAILED";
  }

  @Override
  public String getDfsReadyPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "/READY";
  }

  @Override
  public String getDfsSuccessPath() {
    return PerfConf.get().DFS_DIR + "/" + mId + "/SUCCESS";
  }
}
