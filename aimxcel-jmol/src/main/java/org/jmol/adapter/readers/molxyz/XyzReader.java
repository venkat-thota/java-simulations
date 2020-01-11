
package org.jmol.adapter.readers.molxyz;

import org.jmol.adapter.smarter.*;
import org.jmol.api.JmolAdapter;

import org.jmol.util.Logger;



public class XyzReader extends AtomSetCollectionReader {

  @Override
  protected boolean checkLine() throws Exception {
    int modelAtomCount = parseInt(line);
    if (modelAtomCount == Integer.MIN_VALUE) {
      continuing = false;
      return false;
    }

    // models and vibrations are the same for XYZ files
    vibrationNumber = ++modelNumber;
    if (desiredVibrationNumber <= 0 ? doGetModel(modelNumber)
        : doGetVibration(vibrationNumber)) {
      readLine();
      checkLineForScript();
      atomSetCollection.newAtomSet();
      atomSetCollection.setAtomSetName(line);
      readAtoms(modelAtomCount);
      applySymmetryAndSetTrajectory();
      if (isLastModel(modelNumber)) {
        continuing = false;
        return false;
      }
    } else {
      skipAtomSet(modelAtomCount);
    }
    discardLinesUntilNonBlank();
    return false;
  }

  private void skipAtomSet(int modelAtomCount) throws Exception {
    readLine(); //comment
    for (int i = modelAtomCount; --i >= 0;)
      readLine(); //atoms
  }

  private void readAtoms(int modelAtomCount) throws Exception {
    for (int i = 0; i < modelAtomCount; ++i) {
      readLine();
      String[] tokens = getTokens();
      if (tokens.length < 4) {
        Logger.warn("line cannot be read for XYZ atom data: " + line);
        continue;
      }
      Atom atom = atomSetCollection.addNewAtom();
      String str = tokens[0];
      int isotope = parseInt(str);
      // xyzI
      if (isotope == Integer.MIN_VALUE) {
        atom.elementSymbol = str;
      } else {
        str = str.substring(("" + isotope).length());
        atom.elementNumber = (short) (str.length() == 0 ? isotope
            : ((isotope << 7) + JmolAdapter.getElementNumber(str)));
      }
      atom.x = parseFloat(tokens[1]);
      atom.y = parseFloat(tokens[2]);
      atom.z = parseFloat(tokens[3]);
      if (Float.isNaN(atom.x) || Float.isNaN(atom.y) || Float.isNaN(atom.z)) {
        Logger.warn("line cannot be read for XYZ atom data: " + line);
        atom.set(0, 0, 0);
      }
      int vpt = 4;
      setAtomCoord(atom);
      switch (tokens.length) {
      case 4:
        continue;
      case 5:
      case 6:
      case 8:
      case 9:
        // accepts  sym x y z c
        // accepts  sym x y z c r
        // accepts  sym x y z c vx vy vz
        // accepts  sym x y z c vx vy vz atomno
        if ((str = tokens[4]).indexOf(".") >= 0) {
          atom.partialCharge = parseFloat(str);
        } else {
          int charge = parseInt(str);
          if (charge != Integer.MIN_VALUE)
            atom.formalCharge = charge;
        }
        switch (tokens.length) {
        case 5:
          continue;
        case 6:
          atom.radius = parseFloat(tokens[5]);
          continue;
        case 9:
          atom.atomSerial = parseInt(tokens[8]);
        }
        vpt++;
        //fall through:
      default:
        // or       sym x y z vx vy vz
        float vx = parseFloat(tokens[vpt++]);
        float vy = parseFloat(tokens[vpt++]);
        float vz = parseFloat(tokens[vpt++]);
        if (Float.isNaN(vx) || Float.isNaN(vy) || Float.isNaN(vz))
          continue;
        atomSetCollection.addVibrationVector(atom.atomIndex, vx, vy, vz);
      }
    }
  }
}
