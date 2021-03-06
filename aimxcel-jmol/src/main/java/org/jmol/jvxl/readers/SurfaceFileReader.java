package org.jmol.jvxl.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import org.jmol.util.BinaryDocument;
import org.jmol.util.Parser;

abstract class SurfaceFileReader extends SurfaceReader {

  protected BufferedReader br;
  protected BinaryDocument binarydoc;
  protected OutputStream os;
 
  SurfaceFileReader(SurfaceGenerator sg, BufferedReader br) {
    super(sg);
    this.br = br; 
  }
  
  @Override
  protected void setOutputStream(OutputStream os) {
    if (binarydoc == null)
      this.os = os; 
    else
      sg.setOutputStream(binarydoc, os);
  }
  
  @Override
  protected void closeReader() {
    if (br != null)
      try {
        br.close();
      } catch (IOException e) {
        // ignore
      }
    if (os != null)
      try {
        os.flush();
        os.close();
      } catch (IOException e) {
        // ignore
      }
    if (binarydoc != null)
      binarydoc.close();
  }
  
  @Override
  void discardTempData(boolean discardAll) {
    closeReader();
    super.discardTempData(discardAll);
  }
     
  protected String line;
  protected int[] next = new int[1];
  
  protected String[] getTokens() {
    return Parser.getTokens(line, 0);
  }

  protected float parseFloat() {
    return Parser.parseFloat(line, next);
  }

  protected float parseFloat(String s) {
    next[0] = 0;
    return Parser.parseFloat(s, next);
  }
/*
  float parseFloatNext(String s) {
    return Parser.parseFloat(s, next);
  }
*/
  protected int parseInt() {
    return Parser.parseInt(line, next);
  }
  
  protected int parseInt(String s) {
    next[0] = 0;
    return Parser.parseInt(s, next);
  }
  
  protected int parseIntNext(String s) {
    return Parser.parseInt(s, next);
  }
    
  protected float[] parseFloatArray(String s) {
    next[0] = 0;
    return Parser.parseFloatArray(s, next);
  }

  protected float[] parseFloatArray() {
    return Parser.parseFloatArray(line, next);
  }

  protected String getNextQuotedString() {
    return Parser.getNextQuotedString(line, next);
  }

  protected void skipTo(String info, String what) throws Exception {
    if (info != null)
      while (readLine().indexOf(info) < 0) {
      }
    if (what != null)
      next[0] = line.indexOf(what) + what.length() + 2;
  }

  protected String readLine() throws Exception {
    line = br.readLine();
    if (line != null) {
      nBytes += line.length();
      if (os != null) {
        os.write(line.getBytes());
        os.write('\n');
      }
    }
    return line;
  } 
}

