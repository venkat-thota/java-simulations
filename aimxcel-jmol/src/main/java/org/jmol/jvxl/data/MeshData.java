
package org.jmol.jvxl.data;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jmol.util.MeshSurface;

public class MeshData extends MeshSurface {
  
  public final static int MODE_GET_VERTICES = 1;
  public final static int MODE_GET_COLOR_INDEXES = 2;
  public final static int MODE_PUT_SETS = 3;
  public final static int MODE_PUT_VERTICES = 4;

  private boolean setsSuccessful;
  public int vertexIncrement = 1;

  public String polygonColorData;

  public int addVertexCopy(Point3f vertex, float value, int assocVertex) {
    if (assocVertex < 0)
      vertexIncrement = -assocVertex;  //3 in some cases
    return addVertexCopy(vertex, value);
  }

  public BitSet[] getSurfaceSet() {
    return (surfaceSet == null ? getSurfaceSet(0) : surfaceSet);
  }
  
  public BitSet[] getSurfaceSet(int level) {
    if (level == 0) {
      surfaceSet = new BitSet[100];
      nSets = 0;
    }
    setsSuccessful = true;
    for (int i = 0; i < polygonCount; i++)
      if (polygonIndexes[i] != null) {
        int[] p = polygonIndexes[i];
        int pt0 = findSet(p[0]);
        int pt1 = findSet(p[1]);
        int pt2 = findSet(p[2]);
        if (pt0 < 0 && pt1 < 0 && pt2 < 0) {
          createSet(p[0], p[1], p[2]);
          continue;
        }
        if (pt0 == pt1 && pt1 == pt2)
          continue;
        if (pt0 >= 0) {
          surfaceSet[pt0].set(p[1]);
          surfaceSet[pt0].set(p[2]);
          if (pt1 >= 0 && pt1 != pt0)
            mergeSets(pt0, pt1);
          if (pt2 >= 0 && pt2 != pt0 && pt2 != pt1)
            mergeSets(pt0, pt2);
          continue;
        }
        if (pt1 >= 0) {
          surfaceSet[pt1].set(p[0]);
          surfaceSet[pt1].set(p[2]);
          if (pt2 >= 0 && pt2 != pt1)
            mergeSets(pt1, pt2);
          continue;
        }
        surfaceSet[pt2].set(p[0]);
        surfaceSet[pt2].set(p[1]);
      }
    int n = 0;
    for (int i = 0; i < nSets; i++)
      if (surfaceSet[i] != null)
        n++;
    BitSet[] temp = new BitSet[100];
    n = 0;
    for (int i = 0; i < nSets; i++)
      if (surfaceSet[i] != null)
        temp[n++] = surfaceSet[i];
    nSets = n;
    surfaceSet = temp;
    if (!setsSuccessful && level < 2)
      getSurfaceSet(level + 1);
    if (level == 0) {
      sortSurfaceSets();
      setVertexSets(false);      
    }
    return surfaceSet;
  }

  private class SSet {
    BitSet bs;
    int n;
    
    protected SSet (BitSet bs) {
      this.bs = bs;
      n = bs.cardinality();
    }
  }
  
  protected class SortSet implements Comparator<SSet> {

    public int compare(SSet o1, SSet o2) {
      return (o1.n > o2.n ? -1 : o1.n < o2.n ? 1 : 0);
    }  
  }

  private void sortSurfaceSets() {
    SSet[] sets = new SSet[nSets];
    for (int i = 0; i < nSets; i++)
      sets[i] = new SSet(surfaceSet[i]);
    Arrays.sort(sets, new SortSet());
    for (int i = 0; i < nSets; i++)
      surfaceSet[i] = sets[i].bs;
  }

  public void setVertexSets(boolean onlyIfNull) {
    if (surfaceSet == null)
      return;
    int nNull = 0;
    for (int i = 0; i < nSets; i++) {
      if (surfaceSet[i] != null && surfaceSet[i].cardinality() == 0)
        surfaceSet[i] = null;
      if (surfaceSet[i] == null)
        nNull++;
    }
    if (nNull > 0) {
      BitSet[] bsNew = new BitSet[nSets - nNull];
      for (int i = 0, n = 0; i < nSets; i++)
        if (surfaceSet[i] != null)
          bsNew[n++] = surfaceSet[i];
      surfaceSet = bsNew;
      nSets -= nNull;
    } else if (onlyIfNull) {
      return;
    }
    vertexSets = new int[vertexCount];
    for (int i = 0; i < nSets; i++)
      for (int j = surfaceSet[i].nextSetBit(0); j >= 0; j = surfaceSet[i]
          .nextSetBit(j + 1))
        vertexSets[j] = i;
  }

  private int findSet(int vertex) {
    for (int i = 0; i < nSets; i++)
      if (surfaceSet[i] != null && surfaceSet[i].get(vertex))
        return i;
    return -1;
  }

  private void createSet(int v1, int v2, int v3) {
    int i;
    for (i = 0; i < nSets; i++)
      if (surfaceSet[i] == null)
        break;
    if (i >= 100) {
      setsSuccessful = false;
      return;
    }
    surfaceSet[i] = new BitSet();
    surfaceSet[i].set(v1);
    surfaceSet[i].set(v2);
    surfaceSet[i].set(v3);
    if (i == nSets)
      nSets++;
  }

  private void mergeSets(int a, int b) {
    surfaceSet[a].or(surfaceSet[b]);
    surfaceSet[b] = null;
  }
  
  public void invalidateSurfaceSet(int i) {
    for (int j = surfaceSet[i].nextSetBit(0); j >= 0; j = surfaceSet[i].nextSetBit(j + 1))
      vertexValues[j] = Float.NaN;
    surfaceSet[i] = null;
  }
  
  public static boolean checkCutoff(int iA, int iB, int iC, float[] vertexValues) {
    // never cross a +/- junction with a triangle in the case of orbitals, 
    // where we are using |psi| instead of psi for the surface generation.
    // note that for bicolor maps, where the values are all positive, we 
    // check this later in the meshRenderer
    if (iA < 0 || iB < 0 || iC < 0)
      return false;

    float val1 = vertexValues[iA];
    float val2 = vertexValues[iB];
    float val3 = vertexValues[iC];
    return (val1 >= 0 && val2 >= 0 && val3 >= 0 
        || val1 <= 0 && val2 <= 0 && val3 <= 0);
  }

  public Object calculateVolumes() {
    // TODO
    return null;
  }
  
  
  
  public Object calculateVolumeOrArea(int thisSet, boolean isArea, boolean getSets) {
    if (getSets)
      getSurfaceSet();
    boolean justOne = (nSets == 0 || thisSet >= 0);
    int n = (justOne ? 1 : nSets);
    double[] v = new double[n];
    Vector3f vAB = new Vector3f();
    Vector3f vAC = new Vector3f();
    Vector3f vTemp = new Vector3f();
    for (int i = polygonCount; --i >= 0;) {
      if (!setABC(i))
        continue;
      int iSet = (nSets == 0 ? 0 : vertexSets[iA]);
      if (thisSet >= 0 && iSet != thisSet)
        continue;
      if (isArea) {
        vAB.sub(vertices[iB], vertices[iA]);
        vAC.sub(vertices[iC], vertices[iA]);
        vTemp.cross(vAB, vAC);
        v[justOne ? 0 : iSet] += vTemp.length();
      } else {
        // volume
        vAB.set(vertices[iB]);
        vAC.set(vertices[iC]);
        vTemp.cross(vAB, vAC);
        vAC.set(vertices[iA]);
        v[justOne ? 0 : iSet] += vAC.dot(vTemp);
      }
    }
    double factor = (isArea ? 2 : 6);
    for (int i = 0; i < n; i++)
      v[i] /= factor;
    if (justOne && thisSet != Integer.MIN_VALUE)
      return Float.valueOf((float) v[0]);
    return v;
  }

  public void updateInvalidatedVertices(BitSet bs) {
    for (int i = 0, ipt = 0; i < vertexCount; i += vertexIncrement, ipt++)
      if (Float.isNaN(vertexValues[i]))
        bs.set(i);
  }

  public void invalidateVertices(BitSet bsInvalid) {
    for (int i = bsInvalid.nextSetBit(0); i >= 0; i = bsInvalid.nextSetBit(i + 1))
      vertexValues[i] = Float.NaN;
  }
}

