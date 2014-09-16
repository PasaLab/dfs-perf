package pasalab.dfs.perf.benchmark.createfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pasalab.dfs.perf.basic.TaskContext;

public class CreateFileTaskContext extends TaskContext {
  public static CreateFileTaskContext loadFromFile(File contextFile) throws IOException {
    CreateFileTaskContext ret = new CreateFileTaskContext();
    ret.initial();
    BufferedReader fin = new BufferedReader(new FileReader(contextFile));
    ret.mId = Integer.parseInt(fin.readLine());
    ret.mNodeName = fin.readLine();
    ret.mCores = Integer.parseInt(fin.readLine());
    ret.mStartTimeMs = Long.parseLong(fin.readLine());
    ret.mFinishTimeMs = Long.parseLong(fin.readLine());
    ret.mSuccess = Boolean.parseBoolean(fin.readLine());
    int n = Integer.parseInt(fin.readLine());
    for (int i = 0; i < n; i ++) {
      ret.mSuccessFiles.add(Integer.parseInt(fin.readLine()));
    }
    n = Integer.parseInt(fin.readLine());
    for (int i = 0; i < n; i ++) {
      ret.mTimeStamps.add(Long.parseLong(fin.readLine()));
    }
    fin.close();
    return ret;
  }

  private int mCores;

  private List<Integer> mSuccessFiles;
  private List<Long> mTimeStamps;

  public void initial() {
    mCores = Runtime.getRuntime().availableProcessors();
    mSuccessFiles = new ArrayList<Integer>();
    mTimeStamps = new ArrayList<Long>();
  }

  public int getCores() {
    return mCores;
  }

  public List<Integer> getSuccessFiles() {
    return mSuccessFiles;
  }

  public List<Long> getTimeStamps() {
    return mTimeStamps;
  }

  public void UpdateSuccessFiles(int successFiles) {
    mSuccessFiles.add(successFiles);
    mTimeStamps.add(System.currentTimeMillis());
  }

  @Override
  public void writeToFile(File file) throws IOException {
    BufferedWriter fout = new BufferedWriter(new FileWriter(file));
    fout.write(mId + "\n");
    fout.write(mNodeName + "\n");

    fout.write(mCores + "\n");

    fout.write(mStartTimeMs + "\n");
    fout.write(mFinishTimeMs + "\n");
    fout.write(mSuccess + "\n");
    fout.write(mSuccessFiles.size() + "\n");
    for (int files : mSuccessFiles) {
      fout.write(files + "\n");
    }
    fout.write(mTimeStamps.size() + "\n");
    for (long timeStamp : mTimeStamps) {
      fout.write(timeStamp + "\n");
    }
    fout.close();
  }
}
