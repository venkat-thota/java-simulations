
package org.jmol.export.image;

import java.io.*;
import java.awt.Image;
import java.awt.image.*;


public class PpmEncoder extends ImageEncoder {


  public static byte[] getBytes(Image image) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      write(image, os);
      os.flush();
      os.close();
    } catch (IOException e) {
      // ignore
    }
    return os.toByteArray();
  }

  public static void write(Image image, OutputStream os) throws IOException {
    (new PpmEncoder(image, os)).encode();
  }

  /// Constructor.
  // @param img The image to encode.
  // @param out The stream to write the PPM to.
  public PpmEncoder(Image img, OutputStream out) {
    super(img, out);
  }

  /// Constructor.
  // @param prod The ImageProducer to encode.
  // @param out The stream to write the PPM to.
  public PpmEncoder(ImageProducer prod, OutputStream out) {
    super(prod, out);
  }

  @Override
  void encodeStart(int width, int height) throws IOException {
    writeString(out, "P6\n");
    writeString(out, width + " " + height + "\n");
    writeString(out, "255\n");
  }

  static void writeString(OutputStream out, String str) throws IOException {
    byte[] buf = str.getBytes();
    out.write(buf);
  }

  @Override
  void encodePixels(int x, int y, int w, int h, int[] rgbPixels, int off,
                    int scansize) throws IOException {
    byte[] ppmPixels = new byte[w * 3];
    for (int row = 0; row < h; ++row) {
      int rowOff = off + row * scansize;
      for (int col = 0; col < w; ++col) {
        int i = rowOff + col;
        int j = col * 3;
        ppmPixels[j] = (byte) ((rgbPixels[i] & 0xff0000) >> 16);
        ppmPixels[j + 1] = (byte) ((rgbPixels[i] & 0x00ff00) >> 8);
        ppmPixels[j + 2] = (byte) (rgbPixels[i] & 0x0000ff);
      }
      out.write(ppmPixels);
    }
  }

  @Override
  void encodeDone() throws IOException {
    // Nothing.
  }
}
