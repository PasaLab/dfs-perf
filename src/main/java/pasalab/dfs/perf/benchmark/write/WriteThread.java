package pasalab.dfs.perf.benchmark.write;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import pasalab.dfs.perf.PerfConstants;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

/**
 * Thread to write files to DFS.
 */
public class WriteThread implements Runnable {
  protected static final Logger LOG = Logger.getLogger(PerfConstants.PERF_LOGGER_TYPE);

  public final int ID;
  private int mBlockSize;
  private byte[] mContent;
  private long mFileLength;
  private String mDfsAddress;
  private List<String> mWriteFileList;
  private int mWriteGrainBytes;
  private WriteThreadStatistic mStatistic;
  private String mWriteType;

  public WriteThread(int id, List<String> writeFileList, String writeType, int blockSize,
      long fileLength, int grainBytes) {
    ID = id;
    mWriteGrainBytes = grainBytes;
    mBlockSize = blockSize;
    mContent = new byte[mWriteGrainBytes];
    mFileLength = fileLength;
    mDfsAddress = PerfConf.get().DFS_ADDRESS;
    mWriteFileList = writeFileList;
    mStatistic = new WriteThreadStatistic();
    mWriteType = writeType;
    // TODO: content initialize?
  }

  public WriteThreadStatistic getStatistic() {
    return mStatistic;
  }

  @Override
  public void run() {
    mStatistic.setStartTimeMs(System.currentTimeMillis());
    PerfFileSystem fs;
    try {
      fs = PerfFileSystem.get(mDfsAddress);
    } catch (IOException e) {
      LOG.error("Write Thread " + ID + " falied to connect DFS");
      throw new RuntimeException(e);
    }

    mStatistic.setSuccess(true);
    for (String fileName : mWriteFileList) {
      try {
        OutputStream os = fs.create(fileName, mBlockSize, mWriteType);
        if (os == null) {
          throw new IOException("Failed to create file " + fileName);
        }

        long remainLength = mFileLength;
        while (remainLength >= mWriteGrainBytes) {
          os.write(mContent);
          mStatistic.addSuccessBytes(mWriteGrainBytes);
          remainLength -= mWriteGrainBytes;
        }
        if (remainLength > 0) {
          os.write(mContent, 0, (int) remainLength);
          mStatistic.addSuccessBytes(remainLength);
        }
        os.close();
      } catch (Exception e) {
        LOG.error("Write thread " + ID + "failed to write file " + fileName, e);
        mStatistic.setSuccess(false);
        break;
      }
      mStatistic.addSuccessFiles(1);
    }
    mStatistic.setFinishTimeMs(System.currentTimeMillis());
  }
}
