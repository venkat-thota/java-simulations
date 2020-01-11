
package org.jmol.api;

public interface JmolEdge {

 
  
  public final static int BOND_STEREO_MASK   = 0x400; // 1 << 10
  public final static int BOND_STEREO_NEAR   = 0x401; // for JME reader
  public final static int BOND_STEREO_FAR    = 0x411; // for JME reader
  public final static int BOND_AROMATIC_MASK   = 0x200; // 1 << 9
  public final static int BOND_AROMATIC_SINGLE = 0x201; // same as single
  public final static int BOND_AROMATIC_DOUBLE = 0x202; // same as double
  public final static int BOND_AROMATIC        = 0x203; // same as partial 2.1
  public final static int BOND_SULFUR_MASK   = 0x100; // 1 << 8; will be incremented
  public final static int BOND_PARTIAL_MASK  = 0xE0;  // 7 << 5;
  public final static int BOND_PARTIAL01     = 0x21;
  public final static int BOND_PARTIAL12     = 0x42;
  public final static int BOND_PARTIAL23     = 0x61;
  public final static int BOND_PARTIAL32     = 0x64;
  public final static int BOND_COVALENT_MASK = 0x3FF; // MUST be numerically correct in 0x7 if not partial
  public final static int BOND_COVALENT_SINGLE = 1;   // and in 0xE0 if partial
  public final static int BOND_COVALENT_DOUBLE = 2;
  public final static int BOND_COVALENT_TRIPLE = 3;
  public final static int BOND_COVALENT_QUADRUPLE = 4;
  public final static int BOND_ORDER_UNSPECIFIED = 0x11;
  public final static int BOND_ORDER_ANY     = 0xFFFF;
  public final static int BOND_ORDER_NULL    = 0x1FFFF;
  public final static int BOND_NEW           = 0x20000;
  public static final int BOND_STRUT         = 0x8000;
  public final static int BOND_HBOND_SHIFT   = 11;
  public final static int BOND_HYDROGEN_MASK = 0xF << 11;
  public final static int BOND_H_REGULAR     = 1 << 11;
  public final static int BOND_H_CALC_MASK   = 0xE << 11; // excludes regular
  public final static int BOND_H_CALC        = 2 << 11;
  public final static int BOND_H_PLUS_2      = 3 << 11;
  public final static int BOND_H_PLUS_3      = 4 << 11;
  public final static int BOND_H_PLUS_4      = 5 << 11;
  public final static int BOND_H_PLUS_5      = 6 << 11;
  public final static int BOND_H_MINUS_3     = 7 << 11;
  public final static int BOND_H_MINUS_4     = 8 << 11;
  public final static int BOND_H_NUCLEOTIDE  = 9 << 11;

  public int getIndex();
  
  public int getAtomIndex1();

  public int getAtomIndex2();

  public int getCovalentOrder();

  public int getOrder();

  public JmolNode getOtherAtom(JmolNode atom);

  public boolean isCovalent();

  public boolean isHydrogen();
  
}
