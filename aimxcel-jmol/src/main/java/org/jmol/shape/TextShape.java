
package org.jmol.shape;

import org.jmol.g3d.*;

import java.util.BitSet;
import java.util.Iterator;

public abstract class TextShape extends Object2dShape {

  // echo, hover
  
  @Override
  public void setProperty(String propertyName, Object value, BitSet bsSelected) {

    if ("text" == propertyName) {
      String text = (String) value;
      if (currentObject == null) {
        if (isAll) {
          Iterator<Text> e = objects.values().iterator();
          while (e.hasNext()) {
            e.next().setText(text);
          }
        }
        return;
      }
      ((Text) currentObject).setText(text);
      return;
    }

    if ("font" == propertyName) {
      currentFont = (Font3D) value;
      if (currentObject == null) {
        if (isAll) {
          Iterator<Text> e = objects.values().iterator();
          while (e.hasNext()) {
            e.next().setFont(currentFont);
          }
        }
        return;
      }
      ((Text) currentObject).setFont(currentFont);
      ((Text) currentObject).setFontScale(0);
      return;
    }
    
    super.setProperty(propertyName, value, bsSelected);
  }
}

