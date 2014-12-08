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

public class PerfFileSystemGlusterfs extends PerfFileSystem {
  private static final int MAX_TRY = 5;

  public static PerfFileSystem getClient(String path, TaskConfiguration taskConf) {
    DfsConf dfsConf = DfsConf.get();
    return new PerfFileSystemGlusterfs(path, dfsConf.GLUSTERFS_IMPL, dfsConf.GLUSTERFS_VOLUMES,
        dfsConf.GLUSTERFS_MOUNTS);
  }

  private final Configuration mConf;
  private FileSystem mGlusterfs;

  private PerfFileSystemGlusterfs(String path, String glusterfsImpl, String glusterfsVolumes,
      String glusterfsMounts) {
    mConf = new Configuration();
    mConf.set("fs.defaultFS", path);
    mConf.set("fs.glusterfs.impl", glusterfsImpl);
    mConf.set("mapred.system.dir", "glusterfs:///mapred/system");
    mConf.set("fs.glusterfs.volumes", glusterfsVolumes);
    mConf.set("fs.glusterfs.volume.fuse." + glusterfsVolumes, glusterfsMounts);
  }

  @Override
  public void close() throws IOException {
    mGlusterfs.close();
  }

  @Override
  public void connect() throws IOException {
    try {
      mGlusterfs = FileSystem.get(mConf);
    } catch (IOException e) {
      LOG.error("Failed to connect Glusterfs", e);
      Throwables.propagate(e);
    }
  }

  @Override
  public boolean create(String path) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        mGlusterfs.create(new Path(path)).close();
        return true;
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public boolean delete(String path, boolean recursive) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        return mGlusterfs.delete(new Path(path), recursive);
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public boolean exists(String path) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        return mGlusterfs.exists(new Path(path));
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public InputStream getInputStream(String path) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        return mGlusterfs.open(new Path(path));
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public long getLength(String path) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        FileStatus fileStatus = mGlusterfs.getFileStatus(new Path(path));
        if (fileStatus != null) {
          return fileStatus.getLen();
        } else {
          throw new FileNotFoundException("File not exists " + path);
        }
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public long getModificationTime(String path) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        FileStatus fileStatus = mGlusterfs.getFileStatus(new Path(path));
        if (fileStatus != null) {
          return fileStatus.getModificationTime();
        } else {
          throw new FileNotFoundException("File not exists " + path);
        }
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public OutputStream getOutputStream(String path) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        Path p = new Path(path);
        if (!mGlusterfs.exists(p)) {
          return mGlusterfs.create(p);
        } else {
          return mGlusterfs.append(p);
        }
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
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
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        return mGlusterfs.isDirectory(new Path(path));
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public boolean isFile(String path) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        return mGlusterfs.isFile(new Path(path));
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public List<String> list(String path) throws IOException {
    if (isFile(path)) {
      ArrayList<String> list = new ArrayList<String>(1);
      list.add(path);
      return list;
    } else if (isDirectory(path)) {
      IOException te = null;
      int cnt = 0;
      while (cnt < MAX_TRY) {
        try {
          FileStatus[] fs = mGlusterfs.listStatus(new Path(path));
          int len = fs.length;
          ArrayList<String> list = new ArrayList<String>(len);
          Path[] listpath = FileUtil.stat2Paths(fs);
          for (int i = 0; i < len; i ++) {
            list.add(listpath[i].toString());
          }
          return list;
        } catch (IOException e) {
          cnt ++;
          te = e;
        }
      }
      throw te;
    }
    return null;
  }

  @Override
  public boolean mkdir(String path, boolean createParent) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        Path p = new Path(path);
        if (mGlusterfs.exists(p)) {
          return false;
        }
        return mGlusterfs.mkdirs(p);
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }

  @Override
  public boolean rename(String src, String dst) throws IOException {
    IOException te = null;
    int cnt = 0;
    while (cnt < MAX_TRY) {
      try {
        Path srcPath = new Path(src);
        Path dstPath = new Path(dst);
        Path dstParentPath = dstPath.getParent();
        if (!mGlusterfs.exists(dstParentPath)) {
          mGlusterfs.mkdirs(dstParentPath);
        }
        return mGlusterfs.rename(srcPath, dstPath);
      } catch (IOException e) {
        cnt ++;
        te = e;
      }
    }
    throw te;
  }
}
