
package org.jmol.symmetry;

import javax.vecmath.Point3f;

import org.jmol.util.SimpleUnitCell;

import java.util.Map;

class SymmetryInfo {

  boolean coordinatesAreFractional;
  boolean isMultiCell;
  String spaceGroup;
  String[] symmetryOperations;
  String symmetryInfoString;
  int modelIndex;
  int[] cellRange;
  private Point3f periodicOriginXyz;

  boolean isPeriodic() {
    return periodicOriginXyz != null;
  }

  SymmetryInfo() {    
  }
  
  float[] setSymmetryInfo(int modelIndex, Map<String, Object> info) {
    this.modelIndex = modelIndex;
    cellRange = (int[]) info.get("unitCellRange");
    periodicOriginXyz = (Point3f) info.get("periodicOriginXyz");
    spaceGroup = (String) info.get("spaceGroup");
    if (spaceGroup == null || spaceGroup == "")
      spaceGroup = "spacegroup unspecified";
    int symmetryCount = info.containsKey("symmetryCount") ? 
        ((Integer) info.get("symmetryCount")).intValue() 
        : 0;
    symmetryOperations = (String[]) info.get("symmetryOperations");
    symmetryInfoString = "Spacegroup: " + spaceGroup;
    if (symmetryOperations == null) {
      symmetryInfoString += "\nNumber of symmetry operations: ?"
          + "\nSymmetry Operations: unspecified\n";
    } else {
      symmetryInfoString += "\nNumber of symmetry operations: "
          + (symmetryCount == 0 ? 1 : symmetryCount) + "\nSymmetry Operations:";
      for (int i = 0; i < symmetryCount; i++)
        symmetryInfoString += "\n" + symmetryOperations[i];
    }
    symmetryInfoString += "\n";
    float[] notionalUnitcell = (float[]) info.get("notionalUnitcell");
    if (SimpleUnitCell.isValid(notionalUnitcell)) 
      coordinatesAreFractional = info.containsKey("coordinatesAreFractional") ? 
          ((Boolean) info.get("coordinatesAreFractional")).booleanValue() 
          : false;    
    else
      notionalUnitcell = null;
    isMultiCell = (coordinatesAreFractional && symmetryOperations != null);
    return notionalUnitcell;
  }
}

