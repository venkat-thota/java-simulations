
package org.jmol.adapter.readers.more;

import org.jmol.adapter.smarter.*;

import org.jmol.api.JmolAdapter;

public class Mol2Reader extends ForceFieldReader {

  private int nAtoms = 0;
  private int atomCount = 0;
  private boolean isPDB = false;

  @Override
  protected void initializeReader() throws Exception {
    setUserAtomTypes();
  }

  @Override
  public boolean checkLine() throws Exception {
    if (line.equals("@<TRIPOS>MOLECULE")) {
      if (doGetModel(++modelNumber)) {
        processMolecule();
        continuing = !isLastModel(modelNumber);
        return false;
      }
      return true;
    }
    if (line.length() != 0 && line.charAt(0) == '#') {
      /*
       * Comment lines (starting with '#' as per Tripos spec) may contain an
       * inline Jmol script.
       */
      checkLineForScript();
    }
    return true;
  }

  private void processMolecule() throws Exception {
    /* 4-6 lines:
     ZINC02211856
     55    58     0     0     0
     SMALL
     USER_CHARGES
     2-diethylamino-1-[2-(2-naphthyl)-4-quinolyl]-ethanol

     mol_name
     num_atoms [num_bonds [num_subst [num_feat [num_sets]]]]
     mol_type
     charge_type
     [status_bits
     [mol_comment]]

     */

    isPDB = false;
    String thisDataSetName = readLine().trim();
    lastSequenceNumber = Integer.MAX_VALUE;
    chainID = 'A' - 1;
    readLine();
    line += " 0 0 0 0 0 0";
    atomCount = parseInt(line);
    int bondCount = parseInt();
    int resCount = parseInt();
    readLine();//mol_type
    readLine();//charge_type
    //boolean iHaveCharges = (line.indexOf("NO_CHARGES") != 0);
    //optional SYBYL status
    if (readLine() != null && (line.length() == 0 || line.charAt(0) != '@')) {
      //optional comment -- but present if comment is present
      if (readLine() != null && line.length() != 0 && line.charAt(0) != '@') {
        /* The MOLECULE's comment line may contain an inline Jmol script.
            (But don't expect it to be applied just to this molecule/model/frame.)
            Note: '#' is not needed here, but it is for general comments (out of the MOLECULE data structure), 
            so for consistency we'll allow both 'jmolscript:' as such or preceded by # (spaces are ignored).
            Any comments written before the 'jmolscript:' will be preserved (and added to the model's title).
        */
        if (line.indexOf("jmolscript:") >= 0) {
          checkLineForScript();
          if (line.equals("#")) {
            line = "";
          }
        }
        if (line.length() != 0) {
          thisDataSetName += ": " + line.trim();
        }
      }
    }
    newAtomSet(thisDataSetName);
    while (line != null && !line.equals("@<TRIPOS>MOLECULE")) {
      if (line.equals("@<TRIPOS>ATOM")) {
        readAtoms(atomCount);
        atomSetCollection.setAtomSetName(thisDataSetName);
      } else if (line.equals("@<TRIPOS>BOND")) {
        readBonds(bondCount);
      } else if (line.equals("@<TRIPOS>SUBSTRUCTURE")) {
        readResInfo(resCount);
      } else if (line.equals("@<TRIPOS>CRYSIN")) {
        readCrystalInfo();
      }
      readLine();
    }
    nAtoms += atomCount;
    if (isPDB) {
      atomSetCollection
          .setAtomSetCollectionAuxiliaryInfo("isPDB", Boolean.TRUE);
      atomSetCollection.setAtomSetAuxiliaryInfo("isPDB", Boolean.TRUE);
    }
    applySymmetryAndSetTrajectory();
  }

  private int lastSequenceNumber = Integer.MAX_VALUE;
  private char chainID = 'A' - 1;

  private void readAtoms(int atomCount) throws Exception {
    //     1 Cs       0.0000   4.1230   0.0000   Cs        1 RES1   0.0000
    //  1 C1          7.0053   11.3096   -1.5429 C.3       1 <0>        -0.1912
    // free format, but no blank lines
    for (int i = 0; i < atomCount; ++i) {
      Atom atom = atomSetCollection.addNewAtom();
      String[] tokens = getTokens(readLine());
      //Logger.debug(tokens.length + " -" + tokens[5] + "- " + line);
      String atomType = tokens[5];
      atom.atomName = tokens[1] + '\0' + atomType;
      atom.set(parseFloat(tokens[2]), parseFloat(tokens[3]),
          parseFloat(tokens[4]));
      boolean deduceSymbol = !getElementSymbol(atom, atomType);
      // apparently "NO_CHARGES" is not strictly enforced
      //      if (iHaveCharges)
      if (tokens.length > 6) {
        atom.sequenceNumber = parseInt(tokens[6]);
        if (atom.sequenceNumber < lastSequenceNumber) {
          if (chainID == 'Z')
            chainID = 'a' - 1;
          chainID++;
        }
        lastSequenceNumber = atom.sequenceNumber;
        atom.chainID = chainID;
      }
      if (tokens.length > 7) {
        atom.group3 = tokens[7];
        atom.isHetero = JmolAdapter.isHetero(atom.group3);
        if (!isPDB && atom.group3.length() <= 3
            && JmolAdapter.lookupGroupID(atom.group3) >= 0) {
          isPDB = true;
        }
        if (isPDB && deduceSymbol)
          atom.elementSymbol = deducePdbElementSymbol(atom.isHetero, atomType,
              atom.group3);
        //System.out.print(atom.atomName + "/" + atom.elementSymbol + " " );
      }
      if (tokens.length > 8)
        atom.partialCharge = parseFloat(tokens[8]);
    }
  }

  private void readBonds(int bondCount) throws Exception {
    //     6     1    42    1
    // free format, but no blank lines
    for (int i = 0; i < bondCount; ++i) {
      String[] tokens = getTokens(readLine());
      int atomIndex1 = parseInt(tokens[1]);
      int atomIndex2 = parseInt(tokens[2]);
      int order = parseInt(tokens[3]);
      if (order == Integer.MIN_VALUE)
        order = (tokens[3].equals("ar") ? JmolAdapter.ORDER_AROMATIC
            : JmolAdapter.ORDER_UNSPECIFIED);
      atomSetCollection.addBond(new Bond(nAtoms + atomIndex1 - 1, nAtoms
          + atomIndex2 - 1, order));
    }
  }

  private void readResInfo(int resCount) throws Exception {
    // free format, but no blank lines
    for (int i = 0; i < resCount; ++i) {
      readLine();
      //to be determined -- not implemented
    }
  }

  private void readCrystalInfo() throws Exception {
    //    4.1230    4.1230    4.1230   90.0000   90.0000   90.0000   221     1
    readLine();
    String[] tokens = getTokens();
    if (tokens.length < 6)
      return;
    String name = "";
    for (int i = 6; i < tokens.length; i++)
      name += " " + tokens[i];
    if (name == "")
      name = " P1";
    else
      name += " *";
    name = name.substring(1);
    setSpaceGroupName(name);
    if (ignoreFileUnitCell)
      return;
    for (int i = 0; i < 6; i++)
      setUnitCellItem(i, parseFloat(tokens[i]));
    Atom[] atoms = atomSetCollection.getAtoms();
    for (int i = 0; i < atomCount; ++i)
      setAtomCoord(atoms[nAtoms + i]);
  }
}
