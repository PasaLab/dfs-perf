package pasalab.dfs.perf.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import pasalab.dfs.perf.basic.TaskConfiguration;
import pasalab.dfs.perf.conf.DfsConf;

import com.google.common.base.Throwables;


public class PerfFileSystemHdfs extends PerfFileSystem {
  public static PerfFileSystem getClient(String path, TaskConfiguration taskConf) {
    return new PerfFileSystemHdfs(path, DfsConf.get().HDFS_IMPL);
  }

  private final Configuration mConf;
  private FileSystem mHdfs;

  private PerfFileSystemHdfs(String path, String hdfsImpl) {
    mConf = new Configuration();
    mConf.set("fs.defaultFS", path);
    mConf.set("fs.hdfs.impl", hdfsImpl);

    // To disable the instance cache for hdfs client, otherwise it causes the FileSystem closed
    // exception.
    // conf.set("fs.hdfs.impl.disable.cache", "true");
  }

  @Override
  public void close() throws IOException {
    mHdfs.close();
  }

  @Override
  public void connect() throws IOException {
    try {
      mHdfs = FileSystem.get(mConf);
    } catch (IOException e) {
      LOG.error("Failed to connect HDFS", e);
      Throwables.propagate(e);
    }
  }

  @Override
  public boolean create(String path) throws IOException {
    mHdfs.create(new Path(path)).close();
    return true;
  }

  @Override
  public boolean delete(String path, boolean recursive) throws IOException {
    return mHdfs.delete(new Path(path), recursive);
  }

  @Override
  public boolean exists(String path) throws IOException {

    return mHdfs.exists(new Path(path));
  }

  @Override
  public InputStream getInputStream(String path) throws IOException {
    return mHdfs.open(new Path(path));
  }

  @Override
  public long getLength(String path) throws IOException {
    FileStatus fileStatus = mHdfs.getFileStatus(new Path(path));
    if (fileStatus != null) {
      return fileStatus.getLen();
    } else {
      throw new FileNotFoundException("File not exists " + path);
    }
  }

  @Override
  public long getModificationTime(String path) throws IOException {
    FileStatus fileStatus = mHdfs.getFileStatus(new Path(path));
    if (fileStatus != null) {
      return fileStatus.getModificationTime();
    } else {
      throw new FileNotFoundException("File not exists " + path);
    }
  }

  @Override
  public OutputStream getOutputStream(String path) throws IOException {
    Path p = new Path(path);
    if (!mHdfs.exists(p)) {
      return mHdfs.create(p);
    } else {
      return mHdfs.append(p);
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
    return mHdfs.isDirectory(new Path(path));

  }

  @Override
  public boolean isFile(String path) throws IOException {
    return mHdfs.isFile(new Path(path));
  }

  @Override
  public List<String> list(String path) throws IOException {
    if (isFile(path)) {
      ArrayList<String> list = new ArrayList<String>(1);
      list.add(path);
      return list;
    } else if (isDirectory(path)) {
      FileStatus[] fs = mHdfs.listStatus(new Path(path));
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
    if (mHdfs.exists(p)) {
      return false;
    }
    return mHdfs.mkdirs(p);
  }

  @Override
  public boolean rename(String src, String dst) throws IOException {
    Path srcPath = new Path(src);
    Path dstPath = new Path(dst);
    Path dstParentPath = dstPath.getParent();
    if (!mHdfs.exists(dstParentPath)) {
      mHdfs.mkdirs(dstParentPath);
    }
    return mHdfs.rename(srcPath, dstPath);
  }
}
