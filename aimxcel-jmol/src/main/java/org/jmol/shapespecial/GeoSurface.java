
package org.jmol.shapespecial;

public class GeoSurface extends Dots {
  
 @Override
public void initShape() {
   super.initShape();
   isSurface = true;
   translucentAllowed = true;
  }

}
