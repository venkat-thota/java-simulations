
package org.jmol.minimize.forcefield;

import org.jmol.minimize.MinAtom;

abstract class Calculation {

  
  double dE;
  
  FFParam parA, parB, parC;  
  MinAtom a, b, c, d;
  int ia, ib, ic, id;

  int[] iData;
  double[] dData;

  double delta, rab, theta;
  double energy;

  //! Compute the energy and gradients for this OBFFCalculation

  abstract double compute(Object[] dataIn);
  
  //! \return Energy for this OBFFCalculation (call Compute() first)
  double getEnergy() {
    return energy;
  }

  //! \return Setup pointers to atom positions and forces (To be called while setting up calculations). Sets optimized to true.
  void getPointers(Object[] dataIn) {
    iData = (int[])dataIn[0];
    dData = (double[])dataIn[1];
  }
}
