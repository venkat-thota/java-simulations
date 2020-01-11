
package org.jmol.minimize;

public class MinBond {
  //Bond bond;
  public int[] atomIndexes = new int[3]; //third index is bondOrder
  public boolean isAromatic;
  public boolean isAmide;
  
  MinBond(int[] atomIndexes, boolean isAromatic, boolean isAmide) {
    this.atomIndexes = atomIndexes; // includes bond order
    this.isAromatic = isAromatic;
    this.isAmide = isAmide;
  }
  
  public int getOtherAtom(int index) {
    return (atomIndexes[0] == index ? atomIndexes[1] : atomIndexes[0]);    
  }
}
