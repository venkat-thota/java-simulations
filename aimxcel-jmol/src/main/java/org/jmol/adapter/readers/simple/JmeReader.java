
package org.jmol.adapter.readers.simple;

import org.jmol.adapter.smarter.*;

import org.jmol.api.JmolAdapter;
import org.jmol.util.TextFormat;

public class JmeReader extends AtomSetCollectionReader {

  @Override
  public void initializeReader() throws Exception {
    atomSetCollection.setCollectionName("JME");
    atomSetCollection.newAtomSet();
    line = readLine().replace('\t', ' ');
    addJmolScript("jmeString='" + line + "'");
    int atomCount = parseInt();
    int bondCount = parseInt();
    readAtoms(atomCount);
    readBonds(bondCount);
    set2D();
    continuing = false;
  }

  private void readAtoms(int atomCount) throws Exception {
    for (int i = 0; i < atomCount; ++i) {
      String strAtom = parseToken();
      Atom atom = atomSetCollection.addNewAtom();
      setAtomCoord(atom, parseFloat(), parseFloat(), 0);
      int indexColon = strAtom.indexOf(':');
      String elementSymbol = (indexColon > 0 ? strAtom.substring(0, indexColon)
          : strAtom);
      if (elementSymbol.indexOf("+") >= 0) {
        elementSymbol = TextFormat.trim(elementSymbol, "+");
        atom.formalCharge = 1;
      } else if (elementSymbol.indexOf("-") >= 0) {
        elementSymbol = TextFormat.trim(elementSymbol, "-");
        atom.formalCharge = -1;
      }
      atom.elementSymbol = elementSymbol;
    }
    /*
    if (!doMinimize)
      return;
    Atom[] atoms = atomSetCollection.getAtoms();
    for (int i = 0; i < atomCount; i++) {
      atoms[i].z += ((i % 2) == 0 ? 0.05f : -0.05f);
    }
    */
  }

  private void readBonds(int bondCount) throws Exception {
    for (int i = 0; i < bondCount; ++i) {
      int atomIndex1 = parseInt() - 1;
      int atomIndex2 = parseInt() - 1;
      int order = parseInt();
      switch (order) {
      default:
        continue;
      case 1:
      case 2:
      case 3:
        break;
      case -1:
        order = JmolAdapter.ORDER_STEREO_NEAR;
        break;
      case -2:
        order = JmolAdapter.ORDER_STEREO_FAR;
        break;
      }
      atomSetCollection.addBond(new Bond(atomIndex1, atomIndex2, order));
    }
  }
}
