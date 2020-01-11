package org.jmol.jvxl.readers;

import java.io.BufferedReader;

import org.jmol.util.Parser;

class ApbsReader extends VolumeFileReader {

  
  ApbsReader(SurfaceGenerator sg, BufferedReader br) {
    super(sg, br);
    // data are HIGH on the inside and LOW on the outside
    if (params.thePlane == null)
      params.insideOut = !params.insideOut;
    isAngstroms = true;
    nSurfaces = 1;
  }
  
  @Override
  protected void readParameters() throws Exception {
    jvxlFileHeaderBuffer = new StringBuffer(skipComments(false));
    while (line != null && line.length() == 0)
      readLine();
    jvxlFileHeaderBuffer.append("APBS OpenDx DATA ").append(line).append("\n");
    jvxlFileHeaderBuffer.append("see http://apbs.sourceforge.net\n");
    String atomLine = readLine();
    String[] tokens = Parser.getTokens(atomLine);
    if (tokens.length >= 4) {
      volumetricOrigin.set(parseFloat(tokens[1]), parseFloat(tokens[2]),
          parseFloat(tokens[3]));
    }
    VolumeFileReader.checkAtomLine(isXLowToHigh, isAngstroms, tokens[0],
        atomLine, jvxlFileHeaderBuffer);
    readVoxelVector(0);
    readVoxelVector(1);
    readVoxelVector(2);
    readLine();
    tokens = getTokens();
    /* see http://apbs.sourceforge.net/doc/user-guide/index.html#opendx-format
     object 2 class gridconnections counts nx ny nz
     object 3 class array type double rank 0 times n data follows
     * 
     */
    for (int i = 0; i < 3; i++)
      voxelCounts[i] = parseInt(tokens[i + 5]);
    readLine();
  }
}
