
package org.jmol.minimize;

import java.util.ArrayList;
import java.util.List;

import org.jmol.modelset.Atom;

public class MinAtom {

  int index;
  public Atom atom;
  public double[] coord = new double[3];
  public double[] force = new double[3];
  public List<MinBond> bonds = new ArrayList<MinBond>();
  public int nBonds;
  public int hCount;
  
  public String type;
  int[] bondedAtoms;
  
  @Override
  public String toString() {
    return "#" + index + " " + type;
  }
  MinAtom(int index, Atom atom, double[] coord, String type) {
    this.index = index;
    this.atom = atom;
    this.coord = coord;
    this.type = type;
    hCount = atom.getCovalentHydrogenCount();    
  }

  void set() {
    coord[0] = atom.x;
    coord[1] = atom.y;
    coord[2] = atom.z;
  }

  public MinBond getBondTo(int iAtom) {
    getBondedAtomIndexes();
    for (int i = 0; i < nBonds; i++)
      if (bondedAtoms[i] == iAtom)
        return bonds.get(i);
    return null;
  }

  public int[] getBondedAtomIndexes() {
    if (bondedAtoms == null) {
      bondedAtoms = new int[nBonds];
      for (int i = nBonds; --i >= 0;)
        bondedAtoms[i] = bonds.get(i).getOtherAtom(index);
    }
    return bondedAtoms;
  }

  public String getIdentity() {
    return atom.getInfo();
  }

}
