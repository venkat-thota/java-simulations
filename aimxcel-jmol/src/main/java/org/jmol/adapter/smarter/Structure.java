
package org.jmol.adapter.smarter;

import org.jmol.viewer.JmolConstants;

public class Structure {
  public int structureType;
  public int substructureType;
  public String structureID;
  public int serialID;
  public int strandCount;
  public char startChainID = ' ';
  public int startSequenceNumber;
  public char startInsertionCode = ' ';
  public char endChainID = ' ';
  public int endSequenceNumber;
  public char endInsertionCode = ' ';
  public int modelIndex;

  public final static byte PROTEIN_STRUCTURE_NONE = JmolConstants.PROTEIN_STRUCTURE_NONE;
  public final static byte PROTEIN_STRUCTURE_TURN = JmolConstants.PROTEIN_STRUCTURE_TURN;
  public final static byte PROTEIN_STRUCTURE_SHEET = JmolConstants.PROTEIN_STRUCTURE_SHEET;
  public final static byte PROTEIN_STRUCTURE_HELIX = JmolConstants.PROTEIN_STRUCTURE_HELIX;
  public final static byte PROTEIN_STRUCTURE_HELIX_310 = JmolConstants.PROTEIN_STRUCTURE_HELIX_310;
  public final static byte PROTEIN_STRUCTURE_HELIX_ALPHA = JmolConstants.PROTEIN_STRUCTURE_HELIX_ALPHA;
  public final static byte PROTEIN_STRUCTURE_HELIX_PI = JmolConstants.PROTEIN_STRUCTURE_HELIX_PI;

  public static int getHelixType(int type) {
    switch (type) {
    case 1:
      return PROTEIN_STRUCTURE_HELIX_ALPHA;
    case 3:
      return PROTEIN_STRUCTURE_HELIX_PI;
    case 5:
      return PROTEIN_STRUCTURE_HELIX_310;
    }
    return PROTEIN_STRUCTURE_HELIX;
  }
  

  public Structure(int type) {
    structureType = substructureType = type;
  }

  public Structure(int modelIndex, int structureType, int substructureType,
            String structureID, int serialID, int strandCount,
            char startChainID, int startSequenceNumber, char startInsertionCode,
            char endChainID, int endSequenceNumber, char endInsertionCode) {
    this.modelIndex = modelIndex;
    this.structureType = structureType;
    this.substructureType = substructureType;
    this.structureID = structureID;
    this.strandCount = strandCount; // 1 for sheet initially; 0 for helix or turn
    this.serialID = serialID;
    this.startChainID = startChainID;
    this.startSequenceNumber = startSequenceNumber;
    this.startInsertionCode = startInsertionCode;
    this.endChainID = endChainID;
    this.endSequenceNumber = endSequenceNumber;
    this.endInsertionCode = endInsertionCode;
  }

}
