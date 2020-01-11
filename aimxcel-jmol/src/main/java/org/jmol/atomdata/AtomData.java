
package org.jmol.atomdata;

import java.util.BitSet;

import javax.vecmath.Point3f;



public class AtomData {
  public AtomData() {    
  }
 
  final static public int MODE_FILL_COORDS = 1;
  final static public int MODE_FILL_COORDS_AND_RADII = 2;
  final static public int MODE_GET_ATTACHED_HYDROGENS = 3;
 
  public String programInfo;
  public String fileName;
  public String modelName;
  public int modelIndex;
  
  public BitSet bsSelected;
  public BitSet bsIgnored;
  
  public RadiusData radiusData;
  
  /// to be filled:
  
  public int firstAtomIndex;
  public int firstModelIndex; 
  public int lastModelIndex; 

  // if modelIndex < 0, this gets filled with the model of the first atom
  // for now we do NOT include indexes to model for each atom, because we do not need them.
  
  public float hAtomRadius;
  
  public Point3f[] atomXyz;
  public float[] atomRadius;
  public int[] atomicNumber;
  public Point3f[][] hAtoms;
  public int atomCount;
  public int hydrogenAtomCount;
  public int adpMode;
}

