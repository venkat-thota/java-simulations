package com.sparshui.gestures;


public class GestureType {

  public String sType = null;
  public int iType = Integer.MAX_VALUE;
  public GestureType(String type) {
    // class name, such as "org.jmol.multitouch.sparshui.TwoPointGesture"
    sType = type;    
  }
  
  public GestureType(int type) {
    // built-in SparshUI gestures; not implemented for Jmol
    iType = type;
  }
  
  public final static int DRAG_GESTURE = 0;
  public final static int MULTI_POINT_DRAG_GESTURE = 1;
  public final static int ROTATE_GESTURE = 2;
  public final static int SPIN_GESTURE = 3;
  public final static int TOUCH_GESTURE = 4;
  public final static int ZOOM_GESTURE = 5;
  public final static int DBLCLK_GESTURE = 6;
  public final static int FLICK_GESTURE = 7;
  public final static int RELATIVE_DRAG_GESTURE = 8;

  public static final int DRAG_EVENT = 0;
  public static final int ROTATE_EVENT = 1;
  public static final int SPIN_EVENT = 2;
  public final static int TOUCH_EVENT = 3;
  public final static int ZOOM_EVENT = 4;
  public final static int DBLCLK_EVENT = 5;
  public final static int FLICK_EVENT = 6;
  public final static int RELATIVE_DRAG_EVENT = 7;

}
