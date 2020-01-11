package org.jmol.jvxl.readers;

import javax.vecmath.Point3f;

class IsoPlaneReader extends AtomDataReader {

  IsoPlaneReader(SurfaceGenerator sg) {
    super(sg);
    precalculateVoxelData = false;
  }

  @Override
  protected void setup() {
    super.setup();
    doAddHydrogens = false;
    getAtoms(params.mep_marginAngstroms, false, false);
    if (xyzMin == null) {
      setBoundingBox(new Point3f(-10, -10, -10), 0);
      setBoundingBox(new Point3f(10, 10, 10), 0);
    }

    setHeader("PLANE", params.thePlane.toString());
    setRangesAndAddAtoms(params.solvent_ptsPerAngstrom, params.solvent_gridMax, Math.min(myAtomCount, 100)); 
    params.cutoff = 0;
  }

  @Override
  public float getValue(int x, int y, int z, int ptyz) {    
    return  volumeData.calcVoxelPlaneDistance(x, y, z);
  }

}
