package pasalab.dfs.perf.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import pasalab.dfs.perf.basic.TaskConfiguration;

/**
 * This only works for local test.
 */
public class PerfFileSystemLocal extends PerfFileSystem {
  public static PerfFileSystem getClient(String path, TaskConfiguration taskConf) {
    return new PerfFileSystemLocal();
  }

  @Override
  public void close() throws IOException {}

  @Override
  public void connect() throws IOException {}

  @Override
  public boolean create(String path) throws IOException {
    File file = new File(path);
    File parent = file.getParentFile();
    if (parent != null) {
      if (!parent.exists()) {
        parent.mkdirs();
      }
    }
    new FileOutputStream(path).close();
    return true;
  }

  @Override
  public boolean delete(String path, boolean recursive) throws IOException {
    File file = new File(path);
    boolean success = true;
    if (recursive && file.isDirectory()) {
      String[] files = file.list();
      for (String child : files) {
        success = success && delete(path + "/" + child, true);
      }
    }
    return success && file.delete();
  }

  @Override
  public boolean exists(String path) throws IOException {
    return new File(path).exists();
  }

  @Override
  public InputStream getInputStream(String path) throws IOException {
    return new FileInputStream(path);
  }

  @Override
  public long getLength(String path) throws IOException {
    return new File(path).length();
  }

  @Override
  public long getModificationTime(String path) throws IOException {
    return new File(path).lastModified();
  }

  @Override
  public OutputStream getOutputStream(String path) throws IOException {
    File file = new File(path);
    if (!file.exists()) {
      File parent = file.getParentFile();
      if (parent != null) {
        if (!parent.exists()) {
          parent.mkdirs();
        }
      }
      return new FileOutputStream(file);
    } else {
      return new FileOutputStream(file, true);
    }
  }

  @Override
  public String getParent(String path) throws IOException {
    File file = new File(path);
    File parent = file.getParentFile();
    if (parent == null) {
      return null;
    } else {
      return parent.getAbsolutePath();
    }
  }

  @Override
  public boolean isDirectory(String path) throws IOException {
    return new File(path).isDirectory();
  }

  @Override
  public boolean isFile(String path) throws IOException {
    return new File(path).isFile();
  }

  @Override
  public List<String> list(String path) throws IOException {
    File file = new File(path);
    if (file.isFile()) {
      ArrayList<String> ret = new ArrayList<String>(1);
      ret.add(file.getAbsolutePath());
      return ret;
    } else if (file.isDirectory()) {
      File[] files = file.listFiles();
      if (files != null) {
        ArrayList<String> ret = new ArrayList<String>(files.length);
        for (File child : files) {
          ret.add(child.getAbsolutePath());
        }
        return ret;
      }
    }
    return null;
  }

  @Override
  public boolean mkdir(String path, boolean createParent) throws IOException {
    File file = new File(path);
    return (createParent ? file.mkdirs() : file.mkdir());
  }

  @Override
  public boolean rename(String src, String dst) throws IOException {
    File srcFile = new File(src);
    File dstFile = new File(dst);
    File pFile = dstFile.getParentFile();
    if (pFile != null) {
      pFile.mkdirs();
    }
    return srcFile.renameTo(dstFile);
  }
}
