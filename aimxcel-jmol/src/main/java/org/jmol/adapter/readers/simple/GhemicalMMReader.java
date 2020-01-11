package org.jmol.adapter.readers.simple;

import org.jmol.adapter.smarter.*;

import org.jmol.api.JmolAdapter;


public class GhemicalMMReader extends AtomSetCollectionReader {
    
  @Override
  protected boolean checkLine() throws Exception {
    if (line.startsWith("!Header")) {
      processHeader();
      return true;
    }
    if (line.startsWith("!Info")) {
      processInfo();
      return true;
    }
    if (line.startsWith("!Atoms")) {
      processAtoms();
      return true;
    }
    if (line.startsWith("!Bonds")) {
      processBonds();
      return true;
    }
    if (line.startsWith("!Coord")) {
      processCoord();
      return true;
    }
    if (line.startsWith("!Charges")) {
      processCharges();
      return true;
    }
    return true;
  }

  void processHeader() {
  }

  void processInfo() {
  }

  void processAtoms() throws Exception {
    int atomCount = parseInt(line, 6);
    //Logger.debug("atomCount=" + atomCount);
    for (int i = 0; i < atomCount; ++i) {
      if (atomSetCollection.getAtomCount() != i)
        throw new Exception("GhemicalMMReader error #1");
      readLine();
      int atomIndex = parseInt(line);
      if (atomIndex != i)
        throw new Exception("bad atom index in !Atoms" +
                            "expected: " + i + " saw:" + atomIndex);
      int elementNumber = parseInt();
      Atom atom = atomSetCollection.addNewAtom();
      atom.elementNumber = (short)elementNumber;
    }
  }

  void processBonds() throws Exception {
    int bondCount = parseInt(line, 6);
    for (int i = 0; i < bondCount; ++i) {
      readLine();
      int atomIndex1 = parseInt(line);
      int atomIndex2 = parseInt();
      String orderCode = parseToken();
      int order = 0;
      //bug found using FindBugs -- was not considering changes in bond order numbers
      switch(orderCode.charAt(0)) {
      case 'C': // Conjugated (aromatic)
        order = JmolAdapter.ORDER_AROMATIC;
        break;
      case 'T':
        order = JmolAdapter.ORDER_COVALENT_TRIPLE;
        break;
      case 'D':
        order = JmolAdapter.ORDER_COVALENT_DOUBLE;
        break;
      case 'S':
      default:
        order = JmolAdapter.ORDER_COVALENT_SINGLE;
      }
      atomSetCollection.addNewBond(atomIndex1, atomIndex2, order);
    }
  }

  void processCoord() throws Exception {
    Atom[] atoms = atomSetCollection.getAtoms();
    int atomCount = atomSetCollection.getAtomCount();
    for (int i = 0; i < atomCount; ++i) {
      readLine();
      int atomIndex = parseInt(line);
      if (atomIndex != i)
        throw new Exception("bad atom index in !Coord" + "expected: " + i
            + " saw:" + atomIndex);
      setAtomCoord(atoms[i], parseFloat() * 10, parseFloat() * 10, parseFloat() * 10);
    }
  }

  void processCharges() throws Exception {
    Atom[] atoms = atomSetCollection.getAtoms();
    int atomCount = atomSetCollection.getAtomCount();
    for (int i = 0; i < atomCount; ++i) {
      readLine();
      int atomIndex = parseInt(line);
      if (atomIndex != i)
        throw new Exception("bad atom index in !Charges" +
                            "expected: " + i + " saw:" + atomIndex);
      atoms[i].partialCharge = parseFloat();
    }
  }
}
