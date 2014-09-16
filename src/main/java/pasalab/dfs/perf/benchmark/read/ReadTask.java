package pasalab.dfs.perf.benchmark.read;

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
 * The read test task. It will read files from DFS in multi-thread.
 */
public class ReadTask extends PerfTask implements Supervisible {
  private ReadThread[] mReadThreads;
  private List<Thread> mReadThreadsList;
  private String mReadType;

  @Override
  protected boolean cleanupTask(TaskContext taskContext) {
    taskContext.setSuccess(true);
    ((ReadTaskContext) taskContext).setFromReadThreads(mReadThreads);
    return true;
  }

  @Override
  protected boolean setupTask(TaskContext taskContext) {
    PerfConf perfConf = PerfConf.get();
    try {
      mReadType = mTaskConf.getProperty("read.type");
      ((ReadTaskContext) taskContext).initial(mReadType);
      PerfFileSystem fs = PerfFileSystem.get(perfConf.DFS_ADDRESS);
      String readDir = perfConf.DFS_DIR + "/" + mId;
      if (!fs.exists(readDir)) {
        LOG.error("The read dir " + readDir + " is not exist. " + "Do the write test fisrt");
        return false;
      }
      if (!fs.isDirectory(readDir)) {
        LOG.error("The read dir " + readDir + " is not a directory. " + "Do the write test fisrt");
        return false;
      }
      List<String> readFileCandidates = fs.listFullPath(readDir);
      fs.close();
      if (readFileCandidates.isEmpty()) {
        LOG.error("The read dir " + readDir + " is empty");
        return false;
      }
      LOG.info("The read dir " + readDir + ", contains " + readFileCandidates.size() + " files");

      ReadMode readMode = ReadMode.getReadMode(mTaskConf.getProperty("mode"));
      int threadsNum = mTaskConf.getIntProperty("threads.num");
      int grainBytes = mTaskConf.getIntProperty("grain.bytes");
      List<String>[] readFileList =
          ListGenerator.generateReadFiles(threadsNum, mTaskConf.getIntProperty("files.per.thread"),
              readFileCandidates, readMode, mTaskConf.getBooleanProperty("indentical"));
      mReadThreads = new ReadThread[threadsNum];
      for (int i = 0; i < threadsNum; i ++) {
        mReadThreads[i] = new ReadThread(i, readFileList[i], mReadType, grainBytes);
      }
      LOG.info("Create " + threadsNum + " read threads");
    } catch (IOException e) {
      LOG.error("Error when setup read task", e);
      return false;
    }
    return true;
  }

  @Override
  protected boolean runTask(TaskContext taskContext) {
    mReadThreadsList = new ArrayList<Thread>(mReadThreads.length);
    for (int i = 0; i < mReadThreads.length; i ++) {
      Thread readThread = new Thread(mReadThreads[i]);
      mReadThreadsList.add(readThread);
      readThread.start();
    }
    try {
      for (Thread thread : mReadThreadsList) {
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
