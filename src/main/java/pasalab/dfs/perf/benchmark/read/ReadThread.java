package pasalab.dfs.perf.benchmark.read;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import pasalab.dfs.perf.PerfConstants;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

/**
 * Thread to read files from DFS.
 */
public class ReadThread implements Runnable {
  protected static final Logger LOG = Logger.getLogger(PerfConstants.PERF_LOGGER_TYPE);

  public final int ID;

  private byte[] mContent;
  private List<String> mReadFileList;
  private int mReadGrainBytes;
  private String mReadType;
  private ReadThreadStatistic mStatistic;
  private String mDfsAddress;

  public ReadThread(int id, List<String> readFileList, String readType, int grainBytes) {
    ID = id;
    mContent = new byte[grainBytes];
    mReadFileList = readFileList;
    mReadGrainBytes = grainBytes;
    mReadType = readType;
    mStatistic = new ReadThreadStatistic();
    mDfsAddress = PerfConf.get().DFS_ADDRESS;
  }

  public ReadThreadStatistic getStatistic() {
    return mStatistic;
  }

  @Override
  public void run() {
    mStatistic.setStartTimeMs(System.currentTimeMillis());
    PerfFileSystem fs;
    try {
      fs = PerfFileSystem.get(mDfsAddress);
    } catch (IOException e) {
      LOG.error("Read Thread " + ID + " falied to connect DFS");
      throw new RuntimeException(e);
    }

    mStatistic.setSuccess(true);
    for (String filePath : mReadFileList) {
      try {
        InputStream is = fs.open(filePath, mReadType);
        int readLen;
        while ((readLen = is.read(mContent)) > 0) {
          mStatistic.addSuccessBytes(readLen);
        }
        is.close();
      } catch (Exception e) {
        LOG.error("Read thread " + ID + "failed to read file, File: " + filePath, e);
        mStatistic.setSuccess(false);
        break;
      }
      mStatistic.addSuccessFiles(1);
    }
    mStatistic.setFinishTimeMs(System.currentTimeMillis());
  }
}
