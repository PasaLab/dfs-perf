package pasalab.dfs.perf;

import org.apache.thrift.TException;

import pasalab.dfs.perf.thrift.MasterService;
import pasalab.dfs.perf.thrift.SlaveAlreadyRegisterException;
import pasalab.dfs.perf.thrift.SlaveNotRegisterException;

public class MasterServiceHandler implements MasterService.Iface {
  private final SlaveStatus mSlaveStatus;

  public MasterServiceHandler(SlaveStatus slaveStatus) {
    mSlaveStatus = slaveStatus;
  }

  public boolean slave_canRun(int taskId, String nodeName) throws SlaveNotRegisterException,
      TException {
    return mSlaveStatus.allReady(taskId + "@" + nodeName);
  }

  public void slave_finish(int taskId, String nodeName, boolean successFinish)
      throws SlaveNotRegisterException, TException {
    mSlaveStatus.slaveFinish(taskId + "@" + nodeName, successFinish);
  }

  public void slave_ready(int taskId, String nodeName, boolean successSetup)
      throws SlaveNotRegisterException, TException {
    mSlaveStatus.slaveReady(taskId + "@" + nodeName, successSetup);
  }

  public boolean slave_register(int taskId, String nodeName, String cleanupDir)
      throws SlaveAlreadyRegisterException, TException {
    return mSlaveStatus.slaveRegister(taskId + "@" + nodeName, cleanupDir);
  }
}
