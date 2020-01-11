
package org.jmol.api;


import java.util.BitSet;
import java.util.List;

public interface JmolNode {
  
  // abstracts out the essential pieces for SMILES processing
  
  public void set(float x, float y, float z);
  public int getAtomSite();
  public int getBondedAtomIndex(int j);
  public int getCovalentHydrogenCount();
  public JmolEdge[] getEdges();
  public short getElementNumber();
  public int getFormalCharge();
  public int getIndex();
  public short getIsotopeNumber();
  public int getModelIndex();
  public int getValence();
  public int getCovalentBondCount();
  public int getImplicitHydrogenCount();
  public short getAtomicAndIsotopeNumber();
  
  // BIOSMILES/BIOSMARTS
  
  public String getAtomName();
  public String getGroupType();
  public String getGroup1(char c0);
  public String getGroup3(boolean allowNull);
  public int getResno();
  public char getChainID();
  public int getOffsetResidueAtom(String name, int offset);
  public boolean getCrossLinkLeadAtomIndexes(List<Integer> vReturn);
  public void setGroupBits(BitSet bs);
  public boolean isLeadAtom();
  public boolean isCrossLinked(JmolNode node);
  public boolean isProtein();
  public boolean isNucleic();
  public boolean isDna();
  public boolean isRna();
  public boolean isPurine();
  public boolean isPyrimidine();
  public boolean isDeleted();
}
