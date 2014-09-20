package pasalab.dfs.perf.benchmark;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pasalab.dfs.perf.fs.PerfFileSystem;

public class Operators {
  /**
   * Close the connect to the file system.
   * 
   * @param fs
   * @throws IOException
   */
  public static void close(PerfFileSystem fs) throws IOException {
    fs.close();
  }

  /**
   * Connect to the file system.
   * 
   * @param fsPath
   * @return
   * @throws IOException
   */
  public static PerfFileSystem connect(String fsPath) throws IOException {
    return PerfFileSystem.get(fsPath);
  }

  /**
   * Do metadata operations.
   * 
   * @param fs
   * @param filePath
   * @return
   * @throws IOException
   */
  public static int metadataSample(PerfFileSystem fs, String filePath) throws IOException {
    if (!fs.mkdirs(filePath, true)) {
      return 0;
    }
    if (!fs.exists(filePath)) {
      return 1;
    }
    if (!fs.rename(filePath, filePath + "-__-__-")) {
      return 2;
    }
    if (!fs.delete(filePath + "-__-__-", true)) {
      return 3;
    }
    return 4;
  }

  /**
   * Read a file from begin to the end.
   * 
   * @param fs
   * @param filePath
   * @param bufferSize
   * @return
   * @throws IOException
   */
  public static long readSingleFile(PerfFileSystem fs, String filePath, int bufferSize)
      throws IOException {
    return readSingleFile(fs, filePath, bufferSize, "NO_CACHE");
  }

  /**
   * Read a file from begin to the end.
   * 
   * @param fs
   * @param filePath
   * @param bufferSize
   * @param readType
   * @return
   * @throws IOException
   */
  public static long readSingleFile(PerfFileSystem fs, String filePath, int bufferSize,
      String readType) throws IOException {
    long readLen = 0;
    byte[] content = new byte[bufferSize];
    InputStream is = fs.open(filePath, readType);
    int onceLen = is.read(content);
    while (onceLen != -1) {
      readLen += (long) onceLen;
      onceLen = is.read(content);
    }
    is.close();
    return readLen;
  }

  private static long readSpecifiedBytes(InputStream is, byte[] content, long readBytes)
      throws IOException {
    long remainBytes = readBytes;
    int readLen = 0;
    while (remainBytes >= content.length) {
      int once = is.read(content);
      if (once == -1) {
        return readLen;
      }
      readLen += once;
      remainBytes -= once;
    }
    if (remainBytes > 0) {
      int once = is.read(content, 0, (int) remainBytes);
      if (once == -1) {
        return readLen;
      }
      readLen += once;
    }
    return readLen;
  }

  /**
   * Read a file. Skip once and read once.
   * 
   * @param fs
   * @param filePath
   * @param bufferSize
   * @param skipBytes
   * @param readBytes
   * @return
   * @throws IOException
   */
  public static long skipReadOnce(PerfFileSystem fs, String filePath, int bufferSize,
      long skipBytes, long readBytes) throws IOException {
    return skipReadOnce(fs, filePath, bufferSize, skipBytes, readBytes, "NO_CACHE");
  }

  /**
   * Read a file. Skip once and read once.
   * 
   * @param fs
   * @param filePath
   * @param bufferSize
   * @param skipBytes
   * @param readBytes
   * @param readType
   * @return
   * @throws IOException
   */
  public static long skipReadOnce(PerfFileSystem fs, String filePath, int bufferSize,
      long skipBytes, long readBytes, String readType) throws IOException {
    byte[] content = new byte[bufferSize];
    InputStream is = fs.open(filePath, readType);
    is.skip(skipBytes);
    long readLen = readSpecifiedBytes(is, content, readBytes);
    is.close();
    return readLen;
  }

  /**
   * Read a file. Skip and read until the end of the file.
   * 
   * @param fs
   * @param filePath
   * @param bufferSize
   * @param skipBytes
   * @param readBytes
   * @return
   * @throws IOException
   */
  public static long skipReadToEnd(PerfFileSystem fs, String filePath, int bufferSize,
      long skipBytes, long readBytes) throws IOException {
    return skipReadToEnd(fs, filePath, bufferSize, skipBytes, readBytes, "NO_CACHE");
  }

  /**
   * Read a file. Skip and read until the end of the file.
   * 
   * @param fs
   * @param filePath
   * @param bufferSize
   * @param skipBytes
   * @param readBytes
   * @param readType
   * @return
   * @throws IOException
   */
  public static long skipReadToEnd(PerfFileSystem fs, String filePath, int bufferSize,
      long skipBytes, long readBytes, String readType) throws IOException {
    byte[] content = new byte[bufferSize];
    long readLen = 0;
    InputStream is = fs.open(filePath, readType);
    is.skip(skipBytes);
    long once = readSpecifiedBytes(is, content, readBytes);
    while (once > 0) {
      readLen += once;
      is.skip(skipBytes);
      once = readSpecifiedBytes(is, content, readBytes);
    }
    is.close();
    return readLen;
  }

  private static void writeContentToFile(OutputStream os, long fileSize, int bufferSize)
      throws IOException {
    byte[] content = new byte[bufferSize];
    long remain = fileSize;
    while (remain >= bufferSize) {
      os.write(content);
      remain -= bufferSize;
    }
    if (remain > 0) {
      os.write(content, 0, (int) remain);
    }
  }

  /**
   * Create a file and write to it.
   * 
   * @param fs
   * @param filePath
   * @param fileSize
   * @param bufferSize
   * @throws IOException
   */
  public static void writeSingleFile(PerfFileSystem fs, String filePath, long fileSize,
      int bufferSize) throws IOException {
    OutputStream os = fs.create(filePath);
    writeContentToFile(os, fileSize, bufferSize);
    os.close();
  }

  /**
   * Create a file and write to it.
   * 
   * @param fs
   * @param filePath
   * @param fileSize
   * @param blockSize
   * @param bufferSize
   * @throws IOException
   */
  public static void writeSingleFile(PerfFileSystem fs, String filePath, long fileSize,
      int blockSize, int bufferSize) throws IOException {
    OutputStream os = fs.create(filePath, blockSize);
    writeContentToFile(os, fileSize, bufferSize);
    os.close();
  }

  /**
   * Create a file and write to it.
   * 
   * @param fs
   * @param filePath
   * @param fileSize
   * @param blockSize
   * @param bufferSize
   * @param writeType
   * @throws IOException
   */
  public static void writeSingleFile(PerfFileSystem fs, String filePath, long fileSize,
      int blockSize, int bufferSize, String writeType) throws IOException {
    OutputStream os = fs.create(filePath, blockSize, writeType);
    writeContentToFile(os, fileSize, bufferSize);
    os.close();
  }
}