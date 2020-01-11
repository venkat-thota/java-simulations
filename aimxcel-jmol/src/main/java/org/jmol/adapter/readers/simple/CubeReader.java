
package org.jmol.adapter.readers.simple;

import org.jmol.adapter.smarter.*;


public class CubeReader extends AtomSetCollectionReader {
    
  private int atomCount;
  private boolean isAngstroms = false;
  
  @Override
  public void initializeReader() throws Exception {
    atomSetCollection.newAtomSet();
    readTitleLines();
    readAtomCountAndOrigin();
    discardLines(3);
    readAtoms();
    applySymmetryAndSetTrajectory();
    continuing = false;
  }

  private void readTitleLines() throws Exception {
    if (readLine().indexOf("#JVXL") == 0)
      while (readLine().indexOf("#") == 0) {
      }
    atomSetCollection.setAtomSetName(line.trim() + " - " + readLine().trim());
  }

  private void readAtomCountAndOrigin() throws Exception {
    readLine();
    isAngstroms = (line.indexOf("ANGSTROMS") >= 0); //JVXL flag for Angstroms
    String[] tokens = getTokens();
    if (tokens[0].charAt(0) == '+') //Jvxl progressive reader -- ignore and consider negative
      tokens[0] = tokens[0].substring(1);
    atomCount = Math.abs(parseInt(tokens[0]));
  }
  
  private void readAtoms() throws Exception {
    float f = (isAngstroms ? 1 : ANGSTROMS_PER_BOHR);
    for (int i = 0; i < atomCount; ++i) {
      readLine();
      Atom atom = atomSetCollection.addNewAtom();
      atom.elementNumber = (short)parseInt(line); //allowing atomicAndIsotope for JVXL format
      atom.partialCharge = parseFloat();
      setAtomCoord(atom, parseFloat() * f, parseFloat() * f, parseFloat());
    }
  }

}
