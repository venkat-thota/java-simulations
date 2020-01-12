
package org.jmol.smiles;

import java.util.BitSet;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jmol.api.JmolEdge;
import org.jmol.api.JmolNode;

public class SmilesAromatic {
 
  public final static boolean isFlatSp2Ring(JmolNode[] atoms,
                                            BitSet bsSelected, BitSet bs,
                                            float cutoff) {
    

    if (cutoff <= 0)
      cutoff = 0.01f;

    Vector3f vTemp = new Vector3f();
    Vector3f vA = new Vector3f();
    Vector3f vB = new Vector3f();
    Vector3f vMean = null;
    int nPoints = bs.cardinality();
    Vector3f[] vNorms = new Vector3f[nPoints * 2];
    int nNorms = 0;
    float maxDev = (1 - cutoff * 5);
    //System.out.println("using maxDev=" + maxDev);
    for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
      JmolNode ringAtom = atoms[i];
      JmolEdge[] bonds = ringAtom.getEdges();
      if (bonds.length < 3)
        continue;
      if (bonds.length > 3)
        return false;
/*      
      // uncomment these next lines to exclude quinone and nucleic acid bases
      for (int k = bonds.length; --k >= 0;) {
        int iAtom = ringAtom.getBondedAtomIndex(k);
        if (!bsSelected.get(iAtom))
          continue;
        if (!bs.get(iAtom) && bonds[k].getOrder() == JmolConstants.BOND_COVALENT_DOUBLE)
          return false;
      }
*/
    }
    for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
      JmolNode ringAtom = atoms[i];
      JmolEdge[] bonds = ringAtom.getEdges();
      // if more than three connections, ring cannot be fully conjugated
      // identify substituent and two ring atoms
      int iSub = -1;
      int r1 = -1;
      int r2 = -1;
      for (int k = bonds.length; --k >= 0;) {
        int iAtom = ringAtom.getBondedAtomIndex(k);
        if (!bsSelected.get(iAtom))
          continue;
        if (!bs.get(iAtom))
          iSub = iAtom;
        else if (r1 < 0)
          r1 = iAtom;
        else
          r2 = iAtom;
      }
      // get the normals through r1 - k - r2 and r1 - iSub - r2
      getNormalThroughPoints(atoms[r1], atoms[i], atoms[r2], vTemp, vA, vB);
      if (vMean == null)
        vMean = new Vector3f();
      if (!addNormal(vTemp, vMean, maxDev))
        return false;
      vNorms[nNorms++] = new Vector3f(vTemp);
      if (iSub >= 0) {
        getNormalThroughPoints(atoms[r1], atoms[iSub], atoms[r2], vTemp, vA, vB);
        if (!addNormal(vTemp, vMean, maxDev))
          return false;
        vNorms[nNorms++] = new Vector3f(vTemp);
      }
    }
    boolean isFlat = checkStandardDeviation(vNorms, vMean, nNorms, cutoff);
    //System.out.println(Escape.escape(bs) + " aromatic ? " + isAromatic);
    return isFlat;
  }

  private final static boolean addNormal(Vector3f vTemp, Vector3f vMean,
                                         float maxDev) {
    float similarity = vMean.dot(vTemp);
    if (similarity != 0 && Math.abs(similarity) < maxDev)
      return false;
    if (similarity < 0)
      vTemp.scale(-1);
    vMean.add(vTemp);
    vMean.normalize();
    return true;
  }

  private final static boolean checkStandardDeviation(Vector3f[] vNorms,
                                                      Vector3f vMean, int n,
                                                      float cutoff) {
    double sum = 0;
    double sum2 = 0;
    for (int i = 0; i < n; i++) {
      float v = vNorms[i].dot(vMean);
      sum += v;
      sum2 += ((double) v) * v;
    }
    sum = Math.sqrt((sum2 - sum * sum / n) / (n - 1));
    //System.out.println("stdev = " + sum);
    return (sum < cutoff);
  }

  static float getNormalThroughPoints(JmolNode pointA, JmolNode pointB,
                                      JmolNode pointC, Vector3f vNorm,
                                      Vector3f vAB, Vector3f vAC) {
    vAB.sub((Point3f) pointB, (Point3f) pointA);
    vAC.sub((Point3f) pointC, (Point3f) pointA);
    vNorm.cross(vAB, vAC);
    vNorm.normalize();
    // ax + by + cz + d = 0
    // so if a point is in the plane, then N dot X = -d
    vAB.set((Point3f) pointA);
    return -vAB.dot(vNorm);
  }

}
