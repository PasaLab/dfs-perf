package pasalab.dfs.perf.benchmark.skipread;

import java.io.IOException;
import java.util.List;

import pasalab.dfs.perf.basic.PerfThread;
import pasalab.dfs.perf.basic.TaskConfiguration;
import pasalab.dfs.perf.benchmark.ListGenerator;
import pasalab.dfs.perf.benchmark.Operators;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class SkipReadThread extends PerfThread {
  private int mBufferSize;
  private PerfFileSystem mFileSystem;
  private long mReadBytes;
  private List<String> mReadFiles;
  private long mSkipBytes;
  private String mSkipMode;
  private int mSkipTimes;

  private boolean mSuccess;
  private double mThroughput; // in MB/s

  public boolean getSuccess() {
    return mSuccess;
  }

  public double getThroughput() {
    return mThroughput;
  }

  @Override
  public void run() {
    long timeMs = System.currentTimeMillis();
    long readBytes = 0;
    mSuccess = true;
    for (String fileName : mReadFiles) {
      try {
        if ("FORWARD".equals(mSkipMode)) {
          readBytes +=
              Operators.forwardSkipRead(mFileSystem, fileName, mBufferSize, mSkipBytes, mReadBytes,
                  mSkipTimes);
        } else {
          readBytes +=
              Operators.randomSkipRead(mFileSystem, fileName, mBufferSize, mReadBytes, mSkipTimes);
        }
      } catch (IOException e) {
        LOG.error("Failed to read file " + fileName, e);
        mSuccess = false;
      }
    }
    timeMs = System.currentTimeMillis() - timeMs;
    mThroughput = (readBytes / 1024.0 / 1024.0) / (timeMs / 1000.0);
  }

  @Override
  public boolean setupThread(TaskConfiguration taskConf) {
    mBufferSize = taskConf.getIntProperty("buffer.size.bytes");
    mReadBytes = taskConf.getLongProperty("read.bytes");
    mSkipBytes = taskConf.getLongProperty("skip.bytes");
    mSkipMode = taskConf.getProperty("skip.mode");
    mSkipTimes = taskConf.getIntProperty("skip.times.per.file");
    try {
      mFileSystem = Operators.connect(PerfConf.get().DFS_ADDRESS, taskConf);
      String readDir = taskConf.getProperty("read.dir");
      List<String> candidates = mFileSystem.list(readDir);
      if (candidates == null || candidates.isEmpty()) {
        throw new IOException("No file to read");
      }
      boolean isRandom = "RANDOM".equals(taskConf.getProperty("read.mode"));
      int filesNum = taskConf.getIntProperty("files.per.thread");
      if (isRandom) {
        mReadFiles = ListGenerator.generateRandomReadFiles(filesNum, candidates);
      } else {
        mReadFiles =
            ListGenerator.generateSequenceReadFiles(mId, PerfConf.get().THREADS_NUM, filesNum,
                candidates);
      }
    } catch (IOException e) {
      LOG.error("Failed to setup thread, task " + mTaskId + " - thread " + mId, e);
      return false;
    }
    mSuccess = false;
    mThroughput = 0;
    return true;
  }

  @Override
  public boolean cleanupThread(TaskConfiguration taskConf) {
    try {
      Operators.close(mFileSystem);
    } catch (IOException e) {
      LOG.warn("Error when close file system, task " + mTaskId + " - thread " + mId, e);
    }
    return true;
  }
}
