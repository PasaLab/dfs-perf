package pasalab.dfs.perf.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import pasalab.dfs.perf.basic.TaskConfiguration;

import com.google.common.base.Throwables;

import tachyon.hadoop.TFS;

public class PerfFileSystemTfsHadoop extends PerfFileSystem {
  public static PerfFileSystem getClient(String path, TaskConfiguration taskConf) {
    return new PerfFileSystemTfsHadoop(path, taskConf);
  }

  private final URI mUri;

  private TFS mTfsHadoop;

  private PerfFileSystemTfsHadoop(String path, TaskConfiguration taskConf) {
    mUri = URI.create(path);
    mTfsHadoop = new TFS();
  }

  @Override
  public void close() throws IOException {
    mTfsHadoop.close();
  }

  @Override
  public void connect() throws IOException {
    try {
      mTfsHadoop.initialize(mUri, new Configuration());
    } catch (IOException e) {
      LOG.error("Failed to get TFS", e);
      Throwables.propagate(e);
    }
  }

  @Override
  public boolean create(String path) throws IOException {
    mTfsHadoop.create(new Path(path)).close();
    return true;
  }

  @Override
  public boolean delete(String path, boolean recursive) throws IOException {
    return mTfsHadoop.delete(new Path(path), recursive);
  }

  @Override
  public boolean exists(String path) throws IOException {
    return mTfsHadoop.exists(new Path(path));
  }

  @Override
  public InputStream getInputStream(String path) throws IOException {
    return mTfsHadoop.open(new Path(path));
  }

  @Override
  public long getLength(String path) throws IOException {
    FileStatus fileStatus = mTfsHadoop.getFileStatus(new Path(path));
    if (fileStatus != null) {
      return fileStatus.getLen();
    } else {
      throw new FileNotFoundException("File not exists " + path);
    }
  }

  @Override
  public long getModificationTime(String path) throws IOException {
    FileStatus fileStatus = mTfsHadoop.getFileStatus(new Path(path));
    if (fileStatus != null) {
      return fileStatus.getModificationTime();
    } else {
      throw new FileNotFoundException("File not exists " + path);
    }
  }

  @Override
  public OutputStream getOutputStream(String path) throws IOException {
    Path p = new Path(path);
    if (!mTfsHadoop.exists(p)) {
      return mTfsHadoop.create(p);
    } else {
      return mTfsHadoop.append(p);
    }
  }

  @Override
  public String getParent(String path) throws IOException {
    Path p = new Path(path).getParent();
    if (p == null) {
      return null;
    } else {
      return p.toString();
    }
  }

  @Override
  public boolean isDirectory(String path) throws IOException {
    FileStatus fileStatus = mTfsHadoop.getFileStatus(new Path(path));
    if (fileStatus != null) {
      return fileStatus.isDirectory();
    } else {
      throw new FileNotFoundException("File not exists " + path);
    }
  }

  @Override
  public boolean isFile(String path) throws IOException {
    FileStatus fileStatus = mTfsHadoop.getFileStatus(new Path(path));
    if (fileStatus != null) {
      return fileStatus.isFile();
    } else {
      throw new FileNotFoundException("File not exists " + path);
    }
  }

  @Override
  public List<String> list(String path) throws IOException {
    if (isFile(path)) {
      ArrayList<String> list = new ArrayList<String>(1);
      list.add(path);
      return list;
    } else if (isDirectory(path)) {
      FileStatus[] fs = mTfsHadoop.listStatus(new Path(path));
      int len = fs.length;
      ArrayList<String> list = new ArrayList<String>(len);
      Path[] listpath = FileUtil.stat2Paths(fs);
      for (int i = 0; i < len; i ++) {
        list.add(listpath[i].toString());
      }
      return list;
    }
    return null;
  }

  @Override
  public boolean mkdir(String path, boolean createParent) throws IOException {
    Path p = new Path(path);
    if (mTfsHadoop.exists(p)) {
      return false;
    }
    return mTfsHadoop.mkdirs(p);
  }

  @Override
  public boolean rename(String src, String dst) throws IOException {
    Path srcPath = new Path(src);
    Path dstPath = new Path(dst);
    Path dstParentPath = dstPath.getParent();
    if (!mTfsHadoop.exists(dstParentPath)) {
      mTfsHadoop.mkdirs(dstParentPath);
    }
    return mTfsHadoop.rename(srcPath, dstPath);
  }
}
