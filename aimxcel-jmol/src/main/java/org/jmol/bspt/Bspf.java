package org.jmol.bspt;

import java.util.BitSet;

import javax.vecmath.Point3f;

import org.jmol.util.Logger;


public final class Bspf {

  int dimMax;
  Bspt bspts[];
  //SphereIterator[] sphereIterators;
  CubeIterator[] cubeIterators;
  
  public Bspf(int dimMax) {
    this.dimMax = dimMax;
    bspts = new Bspt[0];
    cubeIterators = new CubeIterator[0];
  }

  public int getBsptCount() {
    return bspts.length;
  }
  
  public void clearBspt(int bsptIndex) {
    bspts[bsptIndex] = null;
  }
  
  public boolean isInitialized(int bsptIndex) {
    return bspts.length > bsptIndex && bspts[bsptIndex] != null;
  }
  
  public void addTuple(int bsptIndex, Point3f tuple) {
    if (bsptIndex >= bspts.length) {
      Bspt[] t = new Bspt[bsptIndex + 1];
      System.arraycopy(bspts, 0, t, 0, bspts.length);
      bspts = t;
    }
    Bspt bspt = bspts[bsptIndex];
    if (bspt == null)
      bspt = bspts[bsptIndex] = new Bspt(dimMax);
    bspt.addTuple(tuple);
  }

  public void stats() {
    for (int i = 0; i < bspts.length; ++i)
      if (bspts[i] != null)
        bspts[i].stats();
  }

  
  public void dump() {
    for (int i = 0; i < bspts.length; ++i) {
      Logger.info(">>>>\nDumping bspt #" + i + "\n>>>>");
      bspts[i].dump();
    }
    Logger.info("<<<<");
  }
  
/*
  public SphereIterator getSphereIterator(int bsptIndex) {
    if (bsptIndex >= sphereIterators.length) {
      SphereIterator[] t = new SphereIterator[bsptIndex + 1];
      System.arraycopy(sphereIterators, 0, t, 0, sphereIterators.length);
      sphereIterators = t;
    }
    if (sphereIterators[bsptIndex] == null &&
        bspts[bsptIndex] != null)
      sphereIterators[bsptIndex] = bspts[bsptIndex].allocateSphereIterator();
    return sphereIterators[bsptIndex];
  }
*/  
  /**
   * @param bsptIndex  a model index
   * @return           either a cached or a new CubeIterator
   * 
   */
  public CubeIterator getCubeIterator(int bsptIndex) {
    if (bsptIndex < 0)
      return getNewCubeIterator(-1 - bsptIndex);
    if (bsptIndex >= cubeIterators.length) {
      CubeIterator[] t = new CubeIterator[bsptIndex + 1];
      System.arraycopy(cubeIterators, 0, t, 0, cubeIterators.length);
      cubeIterators = t;
    }
    if (cubeIterators[bsptIndex] == null &&
        bspts[bsptIndex] != null)
      cubeIterators[bsptIndex] = getNewCubeIterator(bsptIndex);
    return cubeIterators[bsptIndex];
  }

  public CubeIterator getNewCubeIterator(int bsptIndex) {
      return bspts[bsptIndex].allocateCubeIterator();
  }

  public void initialize(int modelIndex, Point3f[] atoms, BitSet modelAtomBitSet) {
    for (int i = modelAtomBitSet.nextSetBit(0); i >= 0; i = modelAtomBitSet.nextSetBit(i + 1))
      addTuple(modelIndex, atoms[i]);
  }

}
