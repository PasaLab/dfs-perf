package pasalab.dfs.perf.benchmark.createfile;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import pasalab.dfs.perf.PerfConstants;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class CreateFileThread implements Runnable {
  protected static final Logger LOG = Logger.getLogger(PerfConstants.PERF_LOGGER_TYPE);

  private static final int WRITE_GRAIN = 1024 * 1024;

  public final int ID;

  private byte[] mContent;
  private long mFileLength;
  private int mFileNum;
  private String mDfsAddress;
  private String mTargetDir;

  private int mSuccessFiles;

  public CreateFileThread(int id, int fileNum, long fileLength, String workDir) {
    ID = id;
    mFileLength = fileLength;
    mFileNum = fileNum;
    mDfsAddress = PerfConf.get().DFS_ADDRESS;
    mTargetDir = workDir;
    if (fileLength > 0) {
      mContent = new byte[WRITE_GRAIN];
    }
    mSuccessFiles = 0;
  }

  public synchronized int getSuccessFiles() {
    return mSuccessFiles;
  }

  @Override
  public void run() {
    try {
      PerfFileSystem fs = PerfFileSystem.get(mDfsAddress);
      for (int i = 0; i < mFileNum; i++) {
        String fileName = mTargetDir + "/" + ID + "-" + i;
        if (mFileLength > 0) {
          OutputStream os = fs.create(fileName, 1024 * 1024 * 1024, "TRY_CACHE");
          long remainLength = mFileLength;
          while (remainLength >= WRITE_GRAIN) {
            os.write(mContent);
            remainLength -= WRITE_GRAIN;
          }
          if (remainLength > 0) {
            os.write(mContent, 0, (int) remainLength);
          }
          os.close();
        } else {
          fs.createEmptyFile(fileName);
        }
        synchronized (this) {
          mSuccessFiles = i;
        }
      }
      synchronized (this) {
        mSuccessFiles = mFileNum;
      }
    } catch (IOException e) {
      LOG.error("CreateFile Thread " + ID + " falied");
      throw new RuntimeException(e);
    }
  }
}
