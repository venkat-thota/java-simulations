package com.sparshui.server;

import org.jmol.util.Logger;

import com.sparshui.gestures.Gesture;
import com.sparshui.gestures.GestureType;


class GestureFactory {

	
  static Gesture createGesture(GestureType gType) {
   if (gType.sType != null) {
     try {
       return (Gesture) Class.forName(gType.sType).newInstance();
     } catch (Exception e) {
       Logger.error("[GestureFactory] Error creating instance for " + gType.sType + ": \n" + e.getMessage());
     }
     return null;
   }
   /* unused in Jmol
	  switch (gType.iType) {
	  case GestureType.DRAG_GESTURE:
			return new SinglePointDragGesture();
		case GestureType.MULTI_POINT_DRAG_GESTURE:
			return new MultiPointDragGesture();
		case GestureType.ROTATE_GESTURE:
			return new RotateGesture();
		case GestureType.SPIN_GESTURE:
			return new SpinGesture();
		case GestureType.TOUCH_GESTURE:
			return new TouchGesture();
		case GestureType.ZOOM_GESTURE:
			return new ZoomGesture();
		case GestureType.DBLCLK_GESTURE:
			return new DblClkGesture();
		case GestureType.FLICK_GESTURE:
			return new Flick();
		case GestureType.RELATIVE_DRAG_GESTURE:
			return new RelativeDragGesture();
	  }
	  */
   Logger.error("[GestureFactory] Gesture not recognized: " + gType.iType);
   return null;
	}

}
