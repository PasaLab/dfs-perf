package pasalab.dfs.perf.fs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import pasalab.dfs.perf.PerfConstants;
import pasalab.dfs.perf.basic.TaskConfiguration;
import pasalab.dfs.perf.conf.DfsConf;

public abstract class PerfFileSystem {
  protected static final Logger LOG = Logger.getLogger(PerfConstants.PERF_LOGGER_TYPE);

  public static PerfFileSystem get(String path, TaskConfiguration taskConf) throws IOException {
    if (isGlusterfs(path)) {
      return PerfFileSystemGlusterfs.getClient(path, taskConf);
    } else if (isHdfs(path)) {
      return PerfFileSystemHdfs.getClient(path, taskConf);
    } else if (isLocalFS(path)) {
      return PerfFileSystemLocal.getClient(path, taskConf);
    } else if (isAlluxio(path)) {
      return PerfFileSystemAlluxioFS.getClient(path, taskConf);
    } else if (isTfsHadoop(path)) {
      return PerfFileSystemAlluxioHadoop.getClient(path, taskConf);
    }
    throw new IOException("Unknown file system scheme " + path);
  }

  private static boolean isGlusterfs(final String path) {
    for (final String prefix : DfsConf.get().GLUSTER_PREFIX) {
      if (path.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isHdfs(final String path) {
    for (final String prefix : DfsConf.get().HDFS_PREFIX) {
      if (path.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isLocalFS(final String path) {
    for (final String prefix : DfsConf.get().LFS_PREFIX) {
      if (path.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isAlluxio(final String path) {
    for (final String prefix : DfsConf.get().ALLUXIO_PREFIX) {
      if (path.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isTfsHadoop(final String path) {
    for (final String prefix : DfsConf.get().ALLUXIO_HADOOP_PREFIX) {
      if (path.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Close the connection to the file system
   * 
   * @throws IOException
   */
  public abstract void close() throws IOException;

  /**
   * Create the connection to the file system
   * 
   * @throws IOException
   */
  public abstract void connect() throws IOException;

  /**
   * Create a file.
   * 
   * @param path the file's full path
   * @return true if success, false otherwise
   * @throws IOException
   */
  public boolean create(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Delete the file. If recursive and the path is a directory, it will delete all the files under
   * the path.
   * 
   * @param path the file's full path
   * @param recursive It true, delete recursively
   * @return true if success, false otherwise.
   * @throws IOException
   */
  public boolean delete(String path, boolean recursive) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Check whether the file exists or not.
   * 
   * @param path the file's full path
   * @return true if exists, false otherwise
   * @throws IOException
   */
  public boolean exists(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Get the input stream of the file to read content.
   * 
   * @param path the file's full path
   * @return the input stream of the file
   * @throws IOException
   */
  public InputStream getInputStream(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Get the length of the file, in bytes.
   * 
   * @param path the file's full path
   * @return the length of the file in bytes
   * @throws IOException
   */
  public long getLength(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Get the modification time of the file.
   * 
   * @param path the file's full path
   * @return the modification time
   * @throws IOException
   */
  public long getModificationTime(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Get the output stream of the file to write content. If the file does not exist, create a new
   * one.
   * 
   * @param path the file's full path
   * @return the output stream of the file
   * @throws IOException
   */
  public OutputStream getOutputStream(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Get the full path of the file's parent.
   * 
   * @param path the file's full path
   * @return the full path of the file's parent, null if it has no parent
   * @throws IOException
   */
  public String getParent(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Check if the path is a directory.
   * 
   * @param path the file's full path
   * @return true if it's a directory, false otherwise
   * @throws IOException
   */
  public boolean isDirectory(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Check if the path is a file.
   * 
   * @param path the file's full path
   * @return true if it's a file, false otherwise
   * @throws IOException
   */
  public boolean isFile(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * List the files under the path. If the path is a file, return the full path of the file. if the
   * path is a directory, return the full paths of all the files under the path. Otherwise return
   * null.
   * 
   * @param path the file's full path
   * @return the list contains the full paths of the listed files
   * @throws IOException
   */
  public List<String> list(String path) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Creates the directory named by the path. If the folder already exists, the method returns
   * false.
   * 
   * @param path the file's full path
   * @param createParent If true, the method creates any necessary but nonexistent parent
   *        directories. Otherwise, the method does not create nonexistent parent directories.
   * @return true if success, false otherwise
   * @throws IOException
   */
  public boolean mkdir(String path, boolean createParent) throws IOException {
    throw new IOException("Not Implement");
  }

  /**
   * Rename the file.
   * 
   * @param src the src full path
   * @param dst the dst full path
   * @return true if success, false otherwise
   * @throws IOException
   */
  public boolean rename(String src, String dst) throws IOException {
    throw new IOException("Not Implement");
  }
}
