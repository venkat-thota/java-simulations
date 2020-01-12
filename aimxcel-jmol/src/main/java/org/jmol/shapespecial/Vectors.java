
package org.jmol.shapespecial;

import java.util.BitSet;

import org.jmol.shape.AtomShape;

public class Vectors extends AtomShape {

 @Override
protected void initModelSet() {
    if (!(isActive = modelSet.modelSetHasVibrationVectors()))
      return;
    super.initModelSet();
  }

 @Override
public void setProperty(String propertyName, Object value, BitSet bsSelected) {
    if (!isActive)
      return;
    super.setProperty(propertyName, value, bsSelected);
  }
  
 @Override
public Object getProperty(String propertyName, int param) {
   if (propertyName == "mad")
     return Integer.valueOf(mads == null || param < 0 || mads.length <= param ? 0 : mads[param]);
   return super.getProperty(propertyName, param);
 }

 @Override
public String getShapeState() {
    return (isActive ? super.getShapeState() : "");
  }
}
