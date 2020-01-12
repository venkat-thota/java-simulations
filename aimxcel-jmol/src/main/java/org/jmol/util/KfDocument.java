package org.jmol.util;

public class KfDocument {
/*
  private boolean debug;

  private DataInputStream stream;
  private long nBytes;
  
  private Hashtable htData = new Hashtable();

  public Hashtable getData() {
    return htData;
  }

  public KfDocument(BufferedInputStream bis) {
    stream = new DataInputStream(bis);
    stream.mark(Integer.MAX_VALUE);
    debug = Logger.debugging;
    readSuperBlock();
  }

  public static boolean isKfDocument(InputStream is) throws Exception {
    byte[] abMagic = new byte[8];
    is.mark(9);
    is.read(abMagic, 0, 8);
    is.reset();
    return isKfDocument(abMagic);
  }

  public static boolean isKfDocument(byte[] bytes) {
    return (bytes.length >= 8 && bytes[0] == (byte) 'S'
        && bytes[1] == (byte) 'U' && bytes[2] == (byte) 'P'
        && bytes[3] == (byte) 'E' && bytes[4] == (byte) 'R'
        && bytes[5] == (byte) 'I' && bytes[6] == (byte) 'N' && bytes[7] == (byte) 'D');
  }

  public int readByteArray(byte[] b) throws IOException {
    int n = stream.read(b);
    nBytes += n;
    return n;
  }

  public void seek(long offset) {
    // slower, but all that is available using the applet
    try {
      if (offset == nBytes)
        return;
      if (offset < nBytes) {
        stream.reset();
        nBytes = 0;
      } else {
        offset -= nBytes;
      }
      stream.skipBytes((int)offset);
      nBytes += offset;
    } catch (Exception e) {
      Logger.error(null, e);
    }
  }

  private Hashtable sections;

  private void readSuperBlock() {
  }


  private int readBlock(int recordNum, byte[] bytes) throws IOException {
    if (recordNum <= 0) {
      throw new IllegalArgumentException("Negative record number in readBlock");
    }
    if (bytes == null) {
      throw new IllegalArgumentException(
          "Null pointer for `bytes' in readBlock");
    }
    seek(recordOffset(recordNum));
    return readByteArray(bytes);
  }

  private long recordOffset(int record) {
    return ((long) record - 1) * BLOCKLENGTH;
  }

  public final static int BLOCKLENGTH = 4096;
*/
}
