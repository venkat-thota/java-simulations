
package org.jmol.atomdata;

import org.jmol.script.Token;
import org.jmol.viewer.JmolConstants;

public class RadiusData {
  public String info;
  public final static int TYPE_ABSOLUTE = 0;
  public final static int TYPE_OFFSET = 1;
  public final static int TYPE_FACTOR = 2;
  public static final int TYPE_SCREEN = 3;
  //private static final String[] typeNames = new String[] { "=", "+", "*", "." };
  public int type;
  public int vdwType = JmolConstants.VDW_AUTO;
  public float value = Float.NaN;
  public float valueExtended = 0;
  public float[] values;
  
  public RadiusData() {
  }

  public RadiusData(float value, int type, int vdwType) {
    this.type = type;
    this.value = value;
    this.vdwType = vdwType;
  }

  @Override
  public String toString() {
    if (Float.isNaN(value))
      return "";
    StringBuffer sb = new StringBuffer("");
    switch (type) {
    case TYPE_ABSOLUTE:
      sb.append(value);
      break;
    case TYPE_OFFSET:
      sb.append(value > 0 ? "+" : "").append(value);
      break;
    case TYPE_FACTOR:
      sb.append((int)(value * 100)).append("%");
      switch (vdwType) {
      case Token.adpmax:
      case Token.adpmin:
      case Token.ionic:
      case Token.temperature:
        sb.append(Token.nameOf(vdwType));
        break;
      default:
        if (vdwType != JmolConstants.VDW_AUTO)
          sb.append(JmolConstants.getVdwLabel(vdwType));
      }
      break;
    case TYPE_SCREEN:
      sb.append((int) value);
    }
    return sb.toString();
  }
}

