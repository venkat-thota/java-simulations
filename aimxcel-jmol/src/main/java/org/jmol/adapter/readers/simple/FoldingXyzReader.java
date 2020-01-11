
package org.jmol.adapter.readers.simple;

import org.jmol.adapter.smarter.*;

import java.util.StringTokenizer;

import org.jmol.util.ArrayUtil;


public class FoldingXyzReader extends AtomSetCollectionReader {

  // Enable / Disable features of the reader
  private final static boolean useAutoBond = false;
  
  @Override
  protected boolean checkLine() throws Exception {
      StringTokenizer tokens = new StringTokenizer(line, " \t");
      if (tokens.hasMoreTokens()) {
        atomSetCollection.newAtomSet();
      	int modelAtomCount = Integer.parseInt(tokens.nextToken());
      	if (tokens.hasMoreTokens())
      	  atomSetCollection.setAtomSetName("Protein " + tokens.nextToken());
      	readAtoms(modelAtomCount);
      }
      return true;
  }
	    
  /**
   * @param modelAtomCount
   * @throws Exception
   */
  void readAtoms(int modelAtomCount) throws Exception {
  	// Stores bond informations
  	int[][] bonds = new int[modelAtomCount + 1][];
  	for (int i = 0; i <= modelAtomCount; ++i) {
  	  bonds[i] = null;
  	}
  	
    for (int i = 0; i <= modelAtomCount; ++i) {
      readLine();
      if (line != null && line.length() == 0) {
      	readLine();
      }
      if (line != null && line.length() > 0) {
        //Logger.debug("Line: " + line);
        Atom atom = atomSetCollection.addNewAtom();
        parseInt(line);
        atom.atomName = parseToken();
        if (atom.atomName != null) {
          int carCount = 1;
          if (atom.atomName.length() >= 2) {
          	char c1 = atom.atomName.charAt(0);
          	char c2 = atom.atomName.charAt(1);
          	if (Character.isUpperCase(c1) && Character.isLowerCase(c2) &&
          	    Atom.isValidElementSymbol(c1, c2)) {
          	  carCount = 2;
          	}
          	if ((c1 == 'C') && (c2 =='L')) {
              carCount = 2;
          	}
          }
          atom.elementSymbol = atom.atomName.substring(0, carCount);
        }
        setAtomCoord(atom, parseFloat(), parseFloat(), parseFloat());

        // Memorise bond informations
        int bondCount = 0;
        bonds[i] = new int[5];
        int bondNum = Integer.MIN_VALUE;
        while ((bondNum = parseInt()) > 0) {
          if (bondCount == bonds[i].length) {
          	bonds[i] = ArrayUtil.setLength(bonds[i], bondCount + 1); 
          }
          bonds[i][bondCount++] = bondNum - 1;
        }
        if (bondCount < bonds[i].length) {
          bonds[i] = ArrayUtil.setLength(bonds[i], bondCount);
        }
      }
    }
    
    // Bonds
    if (!useAutoBond) {
    
      // Decide if first bond is relevant
      int incorrectBonds = 0;
      for (int origin = 0; origin < bonds.length; origin++) {
      	if ((bonds[origin] != null) && (bonds[origin].length > 0)) {
          boolean correct = false;
          int destination = bonds[origin][0];
          if ((destination >= 0) && (destination < bonds.length) && (bonds[destination] != null)) {
            for (int j = 0; j < bonds[destination].length; j++) {
              if (bonds[destination][j] == origin) {
                correct = true;
              }
            }
          }
          if (!correct) {
            incorrectBonds++;
          }
      	}
      }
      
      // Create bond
      int start = (incorrectBonds * 5) > bonds.length ? 1 : 0;
      for (int origin = start; origin < bonds.length; origin++) {
        if (bonds[origin] != null) {
          for (int i = 0; i < bonds[origin].length; i++) {
            boolean correct = false;
            int destination = bonds[origin][i];
            if ((destination >= 0) && (destination < bonds.length) && (bonds[destination] != null)) {
              for (int j = start; j < bonds[destination].length; j++) {
                if (bonds[destination][j] == origin) {
          	      correct = true;
                }
              }
            }
            if (correct && (destination > origin)) {
            	atomSetCollection.addNewBond(origin, destination);
            }
          }
        }
      }
    }
  }
}
