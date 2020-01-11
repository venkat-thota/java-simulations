
package org.jmol.jvxl.data;


import java.util.BitSet;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Point4f;

public class JvxlData {
  public JvxlData() {    
  }
 
  public boolean wasJvxl;
  public boolean wasCubic;
  
  public String jvxlFileTitle;
  public String jvxlFileMessage;
  public String jvxlFileHeader;
  public String jvxlSurfaceData;
  public String jvxlEdgeData;
  public String jvxlColorData;
  public String jvxlVolumeDataXml;
  public BitSet[] jvxlExcluded = new BitSet[4];
  
  public Point4f jvxlPlane;

  public boolean isJvxlPrecisionColor;
  public boolean jvxlDataIsColorMapped;
  public boolean jvxlDataIs2dContour;
  public boolean isColorReversed;
  
  public int edgeFractionBase = JvxlCoder.defaultEdgeFractionBase;
  public int edgeFractionRange = JvxlCoder.defaultEdgeFractionRange;
  public int colorFractionBase = JvxlCoder.defaultColorFractionBase;
  public int colorFractionRange = JvxlCoder.defaultColorFractionRange;

  public boolean dataXYReversed;
  public boolean insideOut;
  public boolean isXLowToHigh;
  public boolean isContoured;
  public boolean isBicolorMap;
  public boolean isTruncated;
  public boolean isCutoffAbsolute;
  public boolean vertexDataOnly;
  public float mappedDataMin;
  public float mappedDataMax;
  public float valueMappedToRed;
  public float valueMappedToBlue;
  public float cutoff;
  public float pointsPerAngstrom; 
  public int nPointsX, nPointsY, nPointsZ;
  public long nBytes;
  public int nContours;
  public int nEdges;
  public int nSurfaceInts;
  public int vertexCount;

  // contour data is here instead of in MeshData because
  // sometimes it comes from the file or marching squares
  // directly. 
  
  public List<Object>[] vContours;
  public short[] contourColixes;
  public String contourColors;
  public float[] contourValues;
  public float[] contourValuesUsed;
  public float scale3d;

  public short minColorIndex = -1;
  public short maxColorIndex = 0;

  public String[] title;
  public String version;
  public Point3f[] boundingBox;
  public int excludedTriangleCount;
  public int excludedVertexCount;
  public boolean colorDensity;
  public String moleculeXml;
  public float dataMin, dataMax;
  public int saveVertexCount;
  
  public void setSurfaceInfo(Point4f thePlane, int nSurfaceInts, String surfaceData) {
    jvxlSurfaceData = surfaceData;
    if (jvxlSurfaceData.indexOf("--") == 0)
      jvxlSurfaceData = jvxlSurfaceData.substring(2);
    jvxlPlane = thePlane;
    this.nSurfaceInts = nSurfaceInts;
  }

  public void setSurfaceInfoFromBitSet(BitSet bs, Point4f thePlane) {
    StringBuffer sb = new StringBuffer();
    int nPoints = nPointsX * nPointsY * nPointsZ;
    int nSurfaceInts = (thePlane != null ? 0 : JvxlCoder.jvxlEncodeBitSet(bs, nPoints, sb));
    setSurfaceInfo(thePlane, nSurfaceInts, sb.toString());
  }
    
  public void jvxlUpdateInfo(String[] title, long nBytes) {
    this.title = title;
    this.nBytes = nBytes;
  }

  public static String updateSurfaceData(String edgeData, float[] vertexValues,
                                         int vertexCount, int vertexIncrement,
                                         char isNaN) {
    if (edgeData.length() == 0)
      return "";
    char[] chars = edgeData.toCharArray();
    for (int i = 0, ipt = 0; i < vertexCount; i += vertexIncrement, ipt++)
      if (Float.isNaN(vertexValues[i]))
        chars[ipt] = isNaN;
    return String.copyValueOf(chars);
  }
  
}

