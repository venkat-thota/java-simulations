
package org.jmol.adapter.readers.simple;

import org.jmol.adapter.smarter.*;
import org.jmol.api.JmolAdapter;


public class AlchemyReader extends AtomSetCollectionReader {

  private int atomCount;
  private int bondCount;

  @Override
  public void initializeReader() throws Exception {
    atomSetCollection.newAtomSet();
    String[] tokens = getTokens(readLine());
    atomCount = parseInt(tokens[0]);
    bondCount = parseInt(tokens[2]);
    readAtoms();
    readBonds();
    continuing = false;
 }

  /*
   * 
   11 ATOMS,    12 BONDS,     0 CHARGES
   1 C3     -2.4790   5.3460   0.0000     0.0000
   2 NPL3   -1.2910   4.4980   0.0000     0.0000
   3 C2      0.0240   4.8970   0.0000     0.0000
   4 NPL3    0.8770   3.9020   0.0000     0.0000
   5 C2      0.0710   2.7710   0.0000     0.0000
   6 C2      0.3690   1.3980   0.0000     0.0000
   7 NPL3    1.6110   0.9090   0.0000     0.0000
   8 NPL3   -0.6680   0.5320   0.0000     0.0000
   9 C2     -1.9120   1.0230   0.0000     0.0000
   10 NPL3   -2.3200   2.2900   0.0000     0.0000
   11 C2     -1.2670   3.1240   0.0000     0.0000
   1     1     2  SINGLE
   2     2     3  SINGLE
   3     2    11  SINGLE
   4     3     4  DOUBLE
   5     4     5  SINGLE
   6     5     6  DOUBLE
   7     5    11  SINGLE
   8     6     7  SINGLE
   9     6     8  SINGLE
   10     8     9  DOUBLE
   11     9    10  SINGLE
   12    10    11  DOUBLE
   
   or  3DNA: 
   
   12 ATOMS,    12 BONDS
   1 N      -2.2500   5.0000   0.2500
   2 N      -2.2500  -5.0000   0.2500
   3 N      -2.2500  -5.0000  -0.2500
   4 N      -2.2500   5.0000  -0.2500
   5 C       2.2500   5.0000   0.2500
   6 C       2.2500  -5.0000   0.2500
   7 C       2.2500  -5.0000  -0.2500
   8 C       2.2500   5.0000  -0.2500
   9 C      -2.2500   5.0000   0.2500
   10 C      -2.2500  -5.0000   0.2500
   11 C      -2.2500  -5.0000  -0.2500
   12 C      -2.2500   5.0000  -0.2500
   1     1     2
   2     2     3
   3     3     4
   4     4     1
   5     5     6
   6     6     7
   7     7     8
   8     5     8
   9     9     5
   10    10     6
   11    11     7
   12    12     8

   */
  private void readAtoms() throws Exception {
    for (int i = atomCount; --i >= 0;) {
      String[] tokens = getTokens(readLine());
      Atom atom = new Atom();
      atom.atomSerial = parseInt(tokens[0]);
      String name = atom.atomName = tokens[1];
      atom.elementSymbol = name.substring(0, 1);
      char c1 = name.charAt(0);
      char c2 = ' ';
      // any name > 2 characters -- just read first character
      // any name = 2 characters -- check for known atom or "Du"
      int nChar = (name.length() == 2
          && (Atom.isValidElementSymbol(c1, 
              c2 = Character.toLowerCase(name.charAt(1)))
              || name.equals("Du"))
           ? 2 : 1);
      atom.elementSymbol = (nChar == 1 ? "" + c1 : "" + c1 + c2);
      setAtomCoord(atom, parseFloat(tokens[2]), parseFloat(tokens[3]),
          parseFloat(tokens[4]));
      atom.partialCharge = (tokens.length >= 6 ? parseFloat(tokens[5]) : 0);
      atomSetCollection.addAtomWithMappedSerialNumber(atom);
    }
  }

  private void readBonds() throws Exception {
    for (int i = bondCount; --i >= 0;) {
      String[] tokens = getTokens(readLine());
      int atomSerial1 = parseInt(tokens[1]);
      int atomSerial2 = parseInt(tokens[2]);
      String sOrder = (tokens.length < 4 ? "1" : tokens[3].toUpperCase());
      int order = 0;
      switch (sOrder.charAt(0)) {
      default:
      case '1':
      case 'S':
        order = JmolAdapter.ORDER_COVALENT_SINGLE;
        break;
      case '2':
      case 'D':
        order = JmolAdapter.ORDER_COVALENT_DOUBLE;
        break;
      case '3':
      case 'T':
        order = JmolAdapter.ORDER_COVALENT_TRIPLE;
        break;
      case 'A':
        order = JmolAdapter.ORDER_AROMATIC;
        break;
      case 'H':
        order = JmolAdapter.ORDER_HBOND;
        break;
      }
      atomSetCollection.addNewBondWithMappedSerialNumbers(atomSerial1,
          atomSerial2, order);
    }
  }
}
