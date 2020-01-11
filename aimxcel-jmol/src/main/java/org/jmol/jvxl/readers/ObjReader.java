package org.jmol.jvxl.readers;

import java.io.BufferedReader;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Point3f;

import org.jmol.g3d.Graphics3D;
import org.jmol.util.Parser;


class ObjReader extends PmeshReader {

  /**
   * 
   * @param sg
   * @param br
   */
  ObjReader(SurfaceGenerator sg, BufferedReader br) {
    super(sg, br);
    type = "obj";
    setHeader();
  }

  @Override
  protected boolean readVertices() throws Exception {
    // also reads polygons
    pmeshError = "pmesh ERROR: invalid vertex/face list";
    Point3f pt = new Point3f();
    int color = 0;
    int ia, ib, ic, id = 0;
    int i = 0;
    int nPts = 0;
    Map<String, Integer> htPymol = new Hashtable<String, Integer>();
    Integer ipt = null;
    String spt = null;
    int[] pymolMap = new int[3];
    // pymol writes a crude file with much re-writing of vertices

    BitSet bsOK = new BitSet();
    while (readLine() != null) {
      if (line.length() < 2 || line.charAt(1) != ' ') {
        if (params.readAllData && line.startsWith("usemtl"))
          // usemtl k00FF00
            color = Graphics3D.getArgbFromString("[x" + line.substring(8) + "]");
        continue;
      }
      switch (line.charAt(0)) {
      case 'v':
        next[0] = 2;
        pt.set(Parser.parseFloat(line, next), Parser.parseFloat(line, next),
            Parser.parseFloat(line, next));
        boolean addHt = false;
        if (htPymol == null) {
          i = nVertices;
        } else if ((ipt = htPymol.get(spt = "" + pt)) == null) {
          addHt = true;
          i = nVertices;
        } else {
          i = ipt.intValue();
        }

        int j = i;
        if (i == nVertices) {
          if (isAnisotropic)
            setVertexAnisotropy(pt);
          j = addVertexCopy(pt, 0, nVertices++);
          if (j >= 0)
            bsOK.set(i);
        }
        pymolMap[nPts % 3] = j;
        if (addHt)
          htPymol.put(spt, Integer.valueOf(i));
        nPts++;
        if (htPymol != null && nPts > 3)
          htPymol = null;
        break;
      case 'f':
        if (nPts == 3 && line.indexOf("//") < 0)
          htPymol = null;
        nPts = 0;
        nPolygons++;
        String[] tokens = Parser.getTokens(line);
        int vertexCount = tokens.length - 1;
        if (vertexCount == 4)
          htPymol = null;
        if (htPymol == null) {
          ia = Parser.parseInt(tokens[1]) - 1;
          ib = Parser.parseInt(tokens[2]) - 1;
          ic = Parser.parseInt(tokens[3]) - 1;
          pmeshError = " " + ia + " " + ib + " " + ic + " " + line;
          if (!bsOK.get(ia) || !bsOK.get(ib) || !bsOK.get(ic))
            continue;
          if (vertexCount == 4) {
            id = Parser.parseInt(tokens[4]) - 1;
            boolean isOK = (bsOK.get(id));
            nTriangles = addTriangleCheck(ia, ib, ic, (isOK ? 3 : 7), 0, false, color);
            if (isOK)
              nTriangles = addTriangleCheck(ia, ic, id, 6, 0, false, color);
            continue;
          }
        } else {
          ia = pymolMap[0];
          ib = pymolMap[1];
          ic = pymolMap[2];
          if (ia < 0 || ib < 0 || ic < 0)
            continue;
        }
        nTriangles = addTriangleCheck(ia, ib, ic, 7, 0, false, color);
        break;
      case 'g':
        htPymol = null;
        if (params.readAllData)
          color = Graphics3D.getArgbFromString("[x" + line.substring(3) + "]");
        break;
      }
    }
    pmeshError = null;
    return true;
  }

  @Override
  protected boolean readPolygons() {
    return true;
  }
}
