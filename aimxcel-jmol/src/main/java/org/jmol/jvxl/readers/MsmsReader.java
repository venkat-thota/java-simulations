package org.jmol.jvxl.readers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.jmol.util.Logger;
import org.jmol.util.TextFormat;



class MsmsReader extends PmeshReader {

  private String fileName;
  MsmsReader(SurfaceGenerator sg, String fileName, BufferedReader br) {
    super(sg, br);
    this.fileName = fileName;
    type = "msms";
    onePerLine = true;
    fixedCount = 3;
    vertexBase = 1;
    setHeader();
  }

  @Override
  protected boolean readVertices() throws Exception {
    skipHeader();
    return super.readVertices();
  }

  @Override
  protected boolean readPolygons() throws Exception {
    br.close();
    fileName = TextFormat.simpleReplace(fileName, ".vert", ".face");
    Logger.info("reading from file " + fileName);
    try {
    br = new BufferedReader(new InputStreamReader(sg.getAtomDataServer().getBufferedInputStream(
        fileName)));
    } catch (Exception e) {
      Logger.info("Note: file " + fileName + " was not found");
      br = null;
      return true;
    }
    sg.addRequiredFile(fileName);
    skipHeader();
    return super.readPolygons();
  }

  private void skipHeader() throws Exception {
    while (readLine() != null && line.indexOf("#") >= 0) {      
      // skip header
    }
    tokens = getTokens();
    iToken = 0;
  }

}
