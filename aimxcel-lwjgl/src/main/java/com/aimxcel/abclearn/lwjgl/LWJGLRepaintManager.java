// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.lwjgl;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwingRepaintManager;

/**
 * Repaint manager that checks for ComponentImage repaint flags and forwards events to the ComponentImage. Compatible
 * with PSwingRepaintManager, since it needs to be a global instance.
 */
public class LWJGLRepaintManager extends PSwingRepaintManager {
    @Override public void addDirtyRegion( JComponent component, int x, int y, int width, int height ) {
        // notify on any region for now
        notifyComponent( component );
        super.addDirtyRegion( component, x, y, width, height );
    }

    @Override public void addInvalidComponent( JComponent invalidComponent ) {
        // notify our component
        notifyComponent( invalidComponent );
        super.addInvalidComponent( invalidComponent );
    }

    private void notifyComponent( JComponent component ) {
        VoidFunction0 callback = (VoidFunction0) component.getClientProperty( SwingImage.ON_REPAINT_CALLBACK );
        if ( callback != null ) {
            callback.apply();
        }
        else {
            Container parent = component.getParent();
            if ( parent != null && parent instanceof JComponent ) {
                notifyComponent( (JComponent) parent );
            }
        }
    }
}
