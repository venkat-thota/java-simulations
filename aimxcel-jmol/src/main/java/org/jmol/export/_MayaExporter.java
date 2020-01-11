
package org.jmol.export;


import java.util.BitSet;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;


public class _MayaExporter extends __CartesianExporter {
  
  public _MayaExporter() {
    commentChar = "// ";
  }

 
  private int nBalls = 0;
  private int nCyl = 0;
  private String name;
  private String id;

  @Override
  protected void outputHeader() {
    output("//  Maya ASCII 8.5 scene\n");
    output("//  Name: ball_stripped.ma\n");
    //    output("//  CreatedBy: Jmol");
    output("//  Last modified: Thu, Jul 5, 2007 10:25:55 PM\n");
    output("//  Codeset: UTF-8\n");
    output("requires maya \"8.5\";\n");
    output("currentUnit -l centimeter -a degree -t film;\n");
    output("fileInfo \"application\" \"maya\";\n");
    output("fileInfo \"product\" \"Maya Unlimited 8.5\";\n");
    output("fileInfo \"version\" \"8.5\";\n");
    output("fileInfo \"cutIdentifier\" \"200612170012-692032\";\n");
    output("fileInfo \"osv\" \"Mac OS X 10.4.9\";  \n");
  }

  private void addAttr() {
    output(" setAttr -k off \".v\";\n");
    output(" setAttr \".vir\" yes;\n");
    output(" setAttr \".vif\" yes;\n");
    output(" setAttr \".tw\" yes;\n");
    output(" setAttr \".covm[0]\"  0 1 1;\n");
    output(" setAttr \".cdvm[0]\"  0 1 1;\n");
  }

  private void addConnect() {
    output(" connectAttr \"make" + name + ".os\" \"" + id + ".cr\";\n");
    output("connectAttr \"" + id
        + ".iog\" \":initialShadingGroup.dsm\" -na;\n");
  }

  private void setAttr(String attr, float val) {
    output(" setAttr \"." + attr + "\" " + val + ";\n");
  }

  private void setAttr(String attr, int val) {
    output(" setAttr \"." + attr + "\" " + val + ";\n");
  }

  private void setAttr(String attr, Tuple3f pt) {
    output(" setAttr \"." + attr + "\" -type \"double3\" " + pt.x + " "
        + pt.y + " " + pt.z + ";\n");
  }

  @Override
  protected boolean outputCylinder(Point3f ptCenter, Point3f pt1, Point3f pt2, short colix,
                      byte endcaps, float radius, Point3f ptX, Point3f ptY) {
    if (ptX != null)
      return false;
    nCyl++;
    name = "nurbsCylinder" + nCyl;
    id = "nurbsCylinderShape" + nCyl;
    output(" createNode transform -n \"" + name + "\";\n");
    float length = pt1.distance(pt2);
    tempV1.set(pt2);
    tempV1.add(pt1);
    tempV1.scale(0.5f);
    setAttr("t", tempV1);
    tempV1.sub(pt1);
    tempV2.set(tempV1);
    tempV2.normalize();
    float r = tempV1.length();
    float rX = (float) Math.acos(tempV1.y / r) * degreesPerRadian;
    if (tempV1.x < 0)
      rX += 180;
    float rY = (float) Math.atan2(tempV1.x, tempV1.z) * degreesPerRadian;
    tempV2.set(rX, rY, 0);
    setAttr("r", tempV2);
    output(" createNode nurbsSurface -n \"" + id + "\" -p \"" + name
        + "\";\n");
    addAttr();
    output("createNode makeNurbCylinder -n \"make" + name + "\";\n");
    output(" setAttr \".ax\" -type \"double3\" 0 1 0;\n");
    setAttr("r", radius);
    setAttr("s", 4);
    setAttr("hr", length / radius);
    addConnect();
    return true;
  }

  @Override
  protected void outputSphere(Point3f pt, float radius, short colix) {
    //String color = rgbFromColix(colix);
    nBalls++;
    name = "nurbsSphere" + nBalls;
    id = "nurbsSphereShape" + nBalls;

    output("createNode transform -n \"" + name + "\";\n");
    setAttr("t", pt);
    output("createNode nurbsSurface -n \"" + id + "\" -p \"" + name
        + "\";\n");
    addAttr();
    output("createNode makeNurbSphere -n \"make" + name + "\";\n");
    output(" setAttr \".ax\" -type \"double3\" 0 1 0;\n");
    setAttr("r", radius);
    setAttr("s", 4);
    setAttr("nsp", 3);
    addConnect();
  }

  // not implemented: 
  
  @Override
  void drawTextPixel(int argb, int x, int y, int z) {
    // override __CartesianExporter
  }

  @Override
  protected void outputTextPixel(Point3f pt, int argb) {
  }
  
  @Override
  protected void outputSurface(Point3f[] vertices, Vector3f[] normals,
                                  short[] colixes, int[][] indices,
                                  short[] polygonColixes,
                                  int nVertices, int nPolygons, int nFaces, BitSet bsPolygons,
                                  int faceVertexMax, short colix,
                                  List<Short> colorList, Map<Short, Integer> htColixes, Point3f offset) {
  }

  @Override
  protected void outputTriangle(Point3f pt1, Point3f pt2, Point3f pt3,
                                short colix) {
    // TODO
    
  }

  @Override
  protected void outputCircle(Point3f pt1, Point3f pt2, float radius,
                              short colix, boolean doFill) {
    // TODO
    
  }

  @Override
  protected void outputCone(Point3f ptBase, Point3f ptTip, float radius,
                            short colix) {
    // TODO
    
  }

  @Override
  protected void outputEllipsoid(Point3f center, Point3f[] points, short colix) {
    // TODO
    
  }

  @Override
  protected void outputFace(int[] is, int[] coordMap, int faceVertexMax) {
    // TODO
    
  }

  @Override
  protected void output(Tuple3f pt) {
    // TODO
    
  }

}
