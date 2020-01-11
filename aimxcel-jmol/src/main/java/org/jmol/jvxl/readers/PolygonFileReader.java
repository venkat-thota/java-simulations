package org.jmol.jvxl.readers;

import java.io.BufferedReader;
import java.util.Date;



abstract class PolygonFileReader extends SurfaceFileReader {

  protected int nVertices;
  protected int nTriangles;

  PolygonFileReader(SurfaceGenerator sg, BufferedReader br) {
    super(sg, br);
    jvxlFileHeaderBuffer = new StringBuffer();
    jvxlFileHeaderBuffer.append("#created ").append(new Date()).append("\n");
    vertexDataOnly = true;
  }

  @Override
  protected boolean readVolumeParameters() {
    // required by SurfaceReader
    return true;
  }
  
  @Override
  protected boolean readVolumeData(boolean isMapData) {
    // required by SurfaceReader
    return true;
  }

  @Override
  protected void readSurfaceData(boolean isMapData) throws Exception {
    getSurfaceData();
    // required by SurfaceReader
  }

  abstract void getSurfaceData() throws Exception;
  
}
