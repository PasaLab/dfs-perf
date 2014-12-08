package pasalab.dfs.perf.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import pasalab.dfs.perf.basic.TaskConfiguration;

import com.google.common.base.Throwables;

import tachyon.TachyonURI;
import tachyon.client.ReadType;
import tachyon.client.TachyonFS;
import tachyon.client.TachyonFile;
import tachyon.client.WriteType;
import tachyon.thrift.ClientFileInfo;

public class PerfFileSystemTfs extends PerfFileSystem {
  public static PerfFileSystem getClient(String path, TaskConfiguration taskConf) {
    return new PerfFileSystemTfs(path, taskConf);
  }

  private final int mBlockSizeByte;
  private final ReadType mReadType;
  private final TachyonURI mTfsURI;
  private final WriteType mWriteType;

  private TachyonFS mTfs;

  private PerfFileSystemTfs(String path, TaskConfiguration taskConf) {
    mTfsURI = new TachyonURI(path);
    if (taskConf == null) {
      mBlockSizeByte = 1024 * 1024 * 1024;
      mReadType = ReadType.NO_CACHE;
      mWriteType = WriteType.TRY_CACHE;
    } else {
      mBlockSizeByte =
          taskConf.hasProperty("block.size.bytes") ? taskConf.getIntProperty("block.size.bytes")
              : 1024 * 1024 * 1024;
      mReadType =
          taskConf.hasProperty("read.type") ? ReadType.valueOf(taskConf.getProperty("read.type"))
              : ReadType.NO_CACHE;
      mWriteType =
          taskConf.hasProperty("write.type") ? WriteType
              .valueOf(taskConf.getProperty("write.type")) : WriteType.TRY_CACHE;
    }
  }

  @Override
  public void close() throws IOException {
    mTfs.close();
  }

  @Override
  public void connect() throws IOException {
    try {
      mTfs = TachyonFS.get(mTfsURI);
    } catch (IOException e) {
      LOG.error("Failed to get TachyonFS", e);
      Throwables.propagate(e);
    }
  }

  @Override
  public boolean create(String path) throws IOException {
    TachyonURI uri = new TachyonURI(path);
    return (mTfs.createFile(uri, mBlockSizeByte) != -1);
  }

  @Override
  public boolean delete(String path, boolean recursive) throws IOException {
    return mTfs.delete(new TachyonURI(path), recursive);
  }

  @Override
  public boolean exists(String path) throws IOException {
    return mTfs.exist(new TachyonURI(path));
  }

  @Override
  public InputStream getInputStream(String path) throws IOException {
    TachyonURI uri = new TachyonURI(path);
    TachyonFile tachyonFile = mTfs.getFile(uri);
    if (tachyonFile != null) {
      return tachyonFile.getInStream(mReadType);
    }
    throw new FileNotFoundException("File not exists " + path);
  }

  @Override
  public long getLength(String path) throws IOException {
    TachyonURI uri = new TachyonURI(path);
    TachyonFile tachyonFile = mTfs.getFile(uri);
    if (tachyonFile != null) {
      return tachyonFile.length();
    }
    throw new FileNotFoundException("File not exists " + path);
  }

  @Override
  public long getModificationTime(String path) throws IOException {
    TachyonURI uri = new TachyonURI(path);
    if (!mTfs.exist(uri)) {
      return 0;
    }
    return mTfs.getFileStatus(-1, uri).lastModificationTimeMs;
  }

  @Override
  public OutputStream getOutputStream(String path) throws IOException {
    TachyonURI uri = new TachyonURI(path);
    if (!mTfs.exist(uri)) {
      mTfs.createFile(uri, mBlockSizeByte);
    }
    return mTfs.getFile(uri).getOutStream(mWriteType);
  }

  @Override
  public String getParent(String path) throws IOException {
    TachyonURI uri = new TachyonURI(path).getParent();
    if (uri == null) {
      return null;
    } else {
      return uri.getPath();
    }
  }

  @Override
  public boolean isDirectory(String path) throws IOException {
    TachyonURI uri = new TachyonURI(path);
    if (!mTfs.exist(uri)) {
      return false;
    }
    return mTfs.getFile(uri).isDirectory();
  }

  @Override
  public boolean isFile(String path) throws IOException {
    TachyonURI uri = new TachyonURI(path);
    if (!mTfs.exist(uri)) {
      return false;
    }
    return mTfs.getFile(uri).isFile();
  }

  @Override
  public List<String> list(String path) throws IOException {
    List<ClientFileInfo> files = mTfs.listStatus(new TachyonURI(path));
    if (files == null) {
      return null;
    }
    ArrayList<String> ret = new ArrayList<String>(files.size());
    for (ClientFileInfo fileInfo : files) {
      ret.add(fileInfo.path);
    }
    return ret;
  }

  @Override
  public boolean mkdir(String path, boolean createParent) throws IOException {
    TachyonURI uri = new TachyonURI(path);
    if (mTfs.exist(uri)) {
      return false;
    }
    return mTfs.mkdirs(uri, createParent);
  }

  @Override
  public boolean rename(String src, String dst) throws IOException {
    TachyonURI srcURI = new TachyonURI(src);
    TachyonURI dstURI = new TachyonURI(dst);
    return mTfs.rename(srcURI, dstURI);
  }
}
