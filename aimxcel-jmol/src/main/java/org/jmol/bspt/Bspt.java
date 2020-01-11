package org.jmol.bspt;

import javax.vecmath.Point3f;

import org.jmol.util.Logger;

public final class Bspt {

  final static int leafCountMax = 2;
  // this corresponds to the max height of the tree
  final static int MAX_TREE_DEPTH = 100;
  int treeDepth;
  int dimMax;
  Element eleRoot;

  /*
    static float distance(int dim, Point3f t1, Point3f t2) {
    return Math.sqrt(distance2(dim, t1, t2));
    }

    static float distance2(int dim, Point3f t1, Point3f t2) {
    float distance2 = 0.0;
    while (--dim >= 0) {
    float distT = t1.getDimensionValue(dim) - t2.getDimensionValue(dim);
    distance2 += distT*distT;
    }
    return distance2;
    }
  */

  /**
   * Create a bspt with the specified number of dimensions. For a 3-dimensional
   * tree (x,y,z) call new Bspt(3).
   * @param dimMax
   */
  public Bspt(int dimMax) {
    this.dimMax = dimMax;
    this.eleRoot = new Leaf(this);
    treeDepth = 1;
  }

  /**
   * Iterate through all of your data points, calling addTuple
   * @param tuple
   */
  public void addTuple(Point3f tuple) {
    eleRoot = eleRoot.addTuple(0, tuple);
  }

  /**
   * prints some simple stats to stdout
   */
  public void stats() {
//    if (Logger.debugging) {
//      Logger.debug(
//          "bspt treeDepth=" + treeDepth +
//          " count=" + eleRoot.count);
//    }
  }

 
  public void dump() {
    StringBuffer sb = new StringBuffer();
    eleRoot.dump(0, sb);
    Logger.info(sb.toString());
    }

    @Override
    public String toString() {
      return eleRoot.toString();
    }
 

  /*
    Enumeration enum() {
    return new EnumerateAll();
    }

    class EnumerateAll implements Enumeration {
    Node[] stack;
    int sp;
    int i;
    Leaf leaf;

    EnumerateAll() {
    stack = new Node[stackDepth];
    sp = 0;
    Element ele = eleRoot;
    while (ele instanceof Node) {
    Node node = (Node) ele;
    if (sp == stackDepth)
    Logger.debug("Bspt.EnumerateAll tree stack overflow");
    stack[sp++] = node;
    ele = node.eleLE;
    }
    leaf = (Leaf)ele;
    i = 0;
    }

    boolean hasMoreElements() {
    return (i < leaf.count) || (sp > 0);
    }

    Object nextElement() {
    if (i == leaf.count) {
    //Logger.debug("-->" + stack[sp-1].splitValue);
    Element ele = stack[--sp].eleGE;
    while (ele instanceof Node) {
    Node node = (Node) ele;
    stack[sp++] = node;
    ele = node.eleLE;
    }
    leaf = (Leaf)ele;
    i = 0;
    }
    return leaf.tuples[i++];
    }
    }

    Enumeration enumNear(Point3f center, float distance) {
    return new EnumerateNear(center, distance);
    }

    class EnumerateNear implements Enumeration {
    Node[] stack;
    int sp;
    int i;
    Leaf leaf;
    float distance;
    Point3f center;

    EnumerateNear(Point3f center, float distance) {
    this.distance = distance;
    this.center = center;

    stack = new Node[stackDepth];
    sp = 0;
    Element ele = eleRoot;
    while (ele instanceof Node) {
    Node node = (Node) ele;
    if (center.getDimensionValue(node.dim) - distance <= node.splitValue) {
    if (sp == stackDepth)
    Logger.debug("Bspt.EnumerateNear tree stack overflow");
    stack[sp++] = node;
    ele = node.eleLE;
    } else {
    ele = node.eleGE;
    }
    }
    leaf = (Leaf)ele;
    i = 0;
    }

    boolean hasMoreElements() {
    if (i < leaf.count)
    return true;
    if (sp == 0)
    return false;
    Element ele = stack[--sp];
    while (ele instanceof Node) {
    Node node = (Node) ele;
    if (center.getDimensionValue(node.dim) + distance < node.splitValue) {
    if (sp == 0)
    return false;
    ele = stack[--sp];
    } else {
    ele = node.eleGE;
    while (ele instanceof Node) {
    Node nodeLeft = (Node) ele;
    stack[sp++] = nodeLeft;
    ele = nodeLeft.eleLE;
    }
    }
    }
    leaf = (Leaf)ele;
    i = 0;
    return true;
    }

    Object nextElement() {
    return leaf.tuples[i++];
    }
    }
  */

/*  public SphereIterator allocateSphereIterator() {
    return new SphereIterator(this);
  }
*/
  public CubeIterator allocateCubeIterator() {
    return new CubeIterator(this);
  }

}

