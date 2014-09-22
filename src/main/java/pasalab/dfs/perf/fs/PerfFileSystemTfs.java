package pasalab.dfs.perf.fs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Throwables;

import tachyon.client.ReadType;
import tachyon.client.TachyonFS;
import tachyon.client.WriteType;
import tachyon.org.apache.thrift.TException;
import tachyon.thrift.ClientFileInfo;

public class PerfFileSystemTfs extends PerfFileSystem {
  public static PerfFileSystem getClient(String path) {
    return new PerfFileSystemTfs(path);
  }

  private TachyonFS mTfs;

  private PerfFileSystemTfs(String path) {
    try {
      mTfs = TachyonFS.get(path);
    } catch (IOException e) {
      LOG.error("Failed to get TachyonFS", e);
      Throwables.propagate(e);
    }
  }

  @Override
  public void close() throws IOException {
    try {
      mTfs.close();
    } catch (TException e) {
      LOG.warn("Failed to close TachyonFS", e);
    }
  }

  @Override
  public OutputStream create(String path) throws IOException {
    if (!mTfs.exist(path)) {
      mTfs.createFile(path);
    }
    return mTfs.getFile(path).getOutStream(WriteType.TRY_CACHE);
  }

  @Override
  public OutputStream create(String path, int blockSizeByte) throws IOException {
    if (!mTfs.exist(path)) {
      mTfs.createFile(path, blockSizeByte);
    }
    return mTfs.getFile(path).getOutStream(WriteType.TRY_CACHE);
  }

  @Override
  public OutputStream create(String path, int blockSizeByte, String writeType) throws IOException {
    WriteType type = WriteType.getOpType(writeType);
    if (!mTfs.exist(path)) {
      mTfs.createFile(path, blockSizeByte);
    }
    return mTfs.getFile(path).getOutStream(type);
  }

  @Override
  public boolean createEmptyFile(String path) throws IOException {
    if (mTfs.exist(path)) {
      return false;
    }
    return (mTfs.createFile(path) != -1);
  }

  @Override
  public boolean delete(String path, boolean recursive) throws IOException {
    return mTfs.delete(path, recursive);
  }

  @Override
  public boolean exists(String path) throws IOException {
    return mTfs.exist(path);
  }

  @Override
  public long getLength(String path) throws IOException {
    if (!mTfs.exist(path)) {
      return 0;
    }
    return mTfs.getFile(path).length();
  }

  @Override
  public boolean isDirectory(String path) throws IOException {
    if (!mTfs.exist(path)) {
      return false;
    }
    return mTfs.getFile(path).isDirectory();
  }

  @Override
  public boolean isFile(String path) throws IOException {
    if (!mTfs.exist(path)) {
      return false;
    }
    return mTfs.getFile(path).isFile();
  }

  @Override
  public List<String> listFullPath(String path) throws IOException {
    List<ClientFileInfo> files = mTfs.listStatus(path);
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
  public boolean mkdirs(String path, boolean createParent) throws IOException {
    if (mTfs.exist(path)) {
      return false;
    }
    return mTfs.mkdir(path);
  }

  @Override
  public InputStream open(String path) throws IOException {
    if (!mTfs.exist(path)) {
      throw new FileNotFoundException("File not exists " + path);
    }
    return mTfs.getFile(path).getInStream(ReadType.NO_CACHE);
  }

  @Override
  public InputStream open(String path, String readType) throws IOException {
    ReadType type = ReadType.getOpType(readType);
    if (!mTfs.exist(path)) {
      throw new FileNotFoundException("File not exists " + path);
    }
    return mTfs.getFile(path).getInStream(type);
  }

  @Override
  public boolean rename(String src, String dst) throws IOException {
    return mTfs.rename(src, dst);
  }

}
