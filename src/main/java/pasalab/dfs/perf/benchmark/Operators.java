package pasalab.dfs.perf.benchmark;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import pasalab.dfs.perf.fs.PerfFileSystem;

public class Operators {
  public static void close(PerfFileSystem fs) throws IOException {
    fs.close();
  }

  public static PerfFileSystem connect(String fsPath) throws IOException {
    return PerfFileSystem.get(fsPath);
  }

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

  public static long readSingleFile(PerfFileSystem fs, String filePath, int bufferSize)
      throws IOException {
    return readSingleFile(fs, filePath, bufferSize, "NO_CACHE");
  }

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

  public static void writeSingleFile(PerfFileSystem fs, String filePath, long fileSize,
      int bufferSize) throws IOException {
    OutputStream os = fs.create(filePath);
    writeContentToFile(os, fileSize, bufferSize);
    os.close();
  }

  public static void writeSingleFile(PerfFileSystem fs, String filePath, long fileSize,
      int blockSize, int bufferSize) throws IOException {
    OutputStream os = fs.create(filePath, blockSize);
    writeContentToFile(os, fileSize, bufferSize);
    os.close();
  }

  public static void writeSingleFile(PerfFileSystem fs, String filePath, long fileSize,
      int blockSize, int bufferSize, String writeType) throws IOException {
    OutputStream os = fs.create(filePath, blockSize, writeType);
    writeContentToFile(os, fileSize, bufferSize);
    os.close();
  }
}
