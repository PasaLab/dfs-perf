package pasalab.dfs.perf.benchmark.connect;

import java.io.IOException;

import org.apache.log4j.Logger;

import pasalab.dfs.perf.PerfConstants;
import pasalab.dfs.perf.conf.PerfConf;
import pasalab.dfs.perf.fs.PerfFileSystem;

public class ConnectThread implements Runnable {
  protected static final Logger LOG = Logger.getLogger(PerfConstants.PERF_LOGGER_TYPE);

  private static final int OP_DELETE = 1;
  private static final int OP_GET = 2;
  private static final int OP_MKDIR = 3;
  private static final int OP_RENAME = 4;

  public final int ID;

  private PerfFileSystem[] mClients;
  private int mOps;
  private ConnectThreadStatistic mStatistic;
  private String mDfsAddress;
  private String mTargetDir;

  public ConnectThread(int id, int clients, int ops, String workDir) {
    ID = id;
    mClients = new PerfFileSystem[clients];
    mOps = ops;
    mStatistic = new ConnectThreadStatistic();
    mDfsAddress = PerfConf.get().DFS_ADDRESS;
    mTargetDir = workDir + "/" + ID;
  }

  public ConnectThreadStatistic getStatistic() {
    return mStatistic;
  }

  @Override
  public void run() {
    mStatistic.setStartTimeMs(System.currentTimeMillis());
    try {
      for (int i = 0; i < mClients.length; i ++) {
        mClients[i] = PerfFileSystem.get(mDfsAddress);
      }
    } catch (IOException e) {
      LOG.error("Connect Thread " + ID + " falied to connect DFS", e);
      mStatistic.setFinishTimeMs(System.currentTimeMillis());
      return;
    }
    int nextClient = 0;
    int nextOp = OP_MKDIR;
    String fileName = "";
    try {
      for (int i = 0; i < mOps; i ++) {
        if (nextOp == OP_DELETE) {
          mClients[nextClient].delete(fileName, true);
          nextOp = OP_MKDIR;
        } else if (nextOp == OP_GET) {
          mClients[nextClient].exists(fileName);
          nextOp = OP_RENAME;
        } else if (nextOp == OP_MKDIR) {
          mClients[nextClient].mkdirs(mTargetDir, true);
          fileName = mTargetDir;
          nextOp = OP_GET;
        } else if (nextOp == OP_RENAME) {
          mClients[nextClient].rename(fileName, mTargetDir + "-");
          fileName = mTargetDir + "-";
          nextOp = OP_DELETE;
        }
        nextClient = (nextClient + 1) % mClients.length;
        mStatistic.addOps(1);
      }
    } catch (IOException e) {
      LOG.error("Connect thread " + ID + " failed to operate on DFS", e);
      mStatistic.setFinishTimeMs(System.currentTimeMillis());
      return;
    }
    for (int i = 0; i < mClients.length; i ++) {
      try {
        mClients[i].close();
      } catch (IOException e) {
        LOG.warn("Connect Thread " + ID + " falied to close DFS", e);
      }
    }
    mStatistic.setSuccess(true);
    mStatistic.setFinishTimeMs(System.currentTimeMillis());
  }
}
