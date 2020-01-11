package org.jmol.jvxl.readers;

import javax.vecmath.Point3f;

import org.jmol.util.Logger;

import org.jmol.api.AtomIndexIterator;
import org.jmol.api.Interface;
import org.jmol.api.MepCalculationInterface;

class AtomPropertyMapper extends AtomDataReader {

  private MepCalculationInterface m;
  private String mepType;
  private int calcType = 0;
  AtomPropertyMapper(SurfaceGenerator sg, String mepType) {
    super(sg);
    this.mepType = mepType;
  }
  //// maps property data ////
  
  private boolean doSmoothProperty;
  private AtomIndexIterator iter;
  private float smoothingPower;

  
  @Override
  protected void setup() {
    super.setup();
    // MAP only
    volumeData.sr = this;
    volumeData.doIterate = false;
    point = params.point;
    doSmoothProperty = params.propertySmoothing;
    doUseIterator = true;
    if (doSmoothProperty) {
      smoothingPower = params.propertySmoothingPower;
      if (smoothingPower < 0)
        smoothingPower = 0;
      else if (smoothingPower > 10)
        smoothingPower = 10;
      if (smoothingPower == 0)
        doSmoothProperty = false;
      smoothingPower = (smoothingPower - 11) / 2f;
      // 0 to 10 becomes d^-10 to d^-1, and we'll be using distance^2
    }
    maxDistance = params.propertyDistanceMax;
    if (mepType != null) {
      doSmoothProperty = true;
      if (params.mep_calcType >= 0)
        calcType = params.mep_calcType;
      m = (MepCalculationInterface) Interface.getOptionInterface("quantum." + mepType + "Calculation");
    }
    if (!doSmoothProperty && maxDistance == Integer.MAX_VALUE)
      maxDistance = 5.0f; // usually just local to a group
    getAtoms(Float.NaN, false, true);    
    setHeader("property", params.calculationType);
    // for plane mapping
    setRangesAndAddAtoms(params.solvent_ptsPerAngstrom, params.solvent_gridMax, 0); 
    params.cutoff = 0;
  }

  @Override
  protected void initializeMapping() {
    if (Logger.debugging)
      Logger.startTimer();
    if (bsNearby != null)
      bsMySelected.or(bsNearby);
    iter = atomDataServer.getSelectedAtomIterator(bsMySelected, false, false);
  }
  
  @Override
  protected void finalizeMapping() {
    iter.release();
    iter = null;
    if (Logger.debugging)
      Logger.checkTimer("property mapping time");
  }
  
  //////////// meshData extensions ////////////

  /////////////// calculation methods //////////////
    
  @Override
  protected void generateCube() {
    // not applicable
  }

  @Override
  public float getValueAtPoint(Point3f pt) {
    float dmin = Float.MAX_VALUE;
    float dminNearby = Float.MAX_VALUE;
    float value = (doSmoothProperty ? 0 : Float.NaN);
    float vdiv = 0;
    atomDataServer.setIteratorForPoint(iter, modelIndex, pt, maxDistance);
    while (iter.hasNext()) {
      int iAtom = myIndex[iter.next()];
      boolean isNearby = (iAtom >= firstNearbyAtom);
      Point3f ptA = atomXyz[iAtom];
      float p = atomProp[iAtom];
      if (Float.isNaN(p))
        continue;
      float d2 = pt.distanceSquared(ptA);
      if (isNearby) {
        if (d2 < dminNearby) {
          dminNearby = d2;
          if (!doSmoothProperty && dminNearby < dmin) {
            dmin = d2;
            value = Float.NaN;
          }
        }
      } else if (d2 < dmin) {
        dmin = d2;
        if (!doSmoothProperty)
          value = p;
      }
      if (m != null) {
        value += m.valueFor(p, d2, calcType);
      } else if (doSmoothProperty) {
        d2 = (float) Math.pow(d2, smoothingPower);
        vdiv += d2;
        value += d2 * p;
      }
    }
    //System.out.println(pt + " " + value + " " + vdiv + " " + value / vdiv);
    return (m != null ? value : doSmoothProperty ? (vdiv == 0
        || dminNearby < dmin ? Float.NaN : value / vdiv) : value);
  }

}
