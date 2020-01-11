package com.aimxcel.abclearn.aimxcel2dextra.pswing;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.RepaintManager;

import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;


public class PSwingRepaintManager extends RepaintManager {

    // The components that are currently painting
    // This needs to be a vector for thread safety
    private final Vector paintingComponents = new Vector();

    /**
     * Locks repaint for a particular (Swing) component displayed by PCanvas.
     * 
     * @param c The component for which the repaint is to be locked
     */
    public void lockRepaint(final JComponent c) {
        paintingComponents.addElement(c);
    }

    /**
     * Unlocks repaint for a particular (Swing) component displayed by PCanvas.
     * 
     * @param c The component for which the repaint is to be unlocked
     */
    public void unlockRepaint(final JComponent c) {
        paintingComponents.remove(c);
    }

    /**
     * Returns true if repaint is currently locked for a component and false
     * otherwise.
     * 
     * @param c The component for which the repaint status is desired
     * @return Whether the component is currently painting
     */
    public boolean isPainting(final JComponent c) {
        return paintingComponents.contains(c);
    }

    /**
     * This is the method "repaint" now calls in the Swing components.
     * Overridden to capture repaint calls from those Swing components which are
     * being used as Core visual components and to call the Core repaint
     * mechanism rather than the traditional Component hierarchy repaint
     * mechanism. Otherwise, behaves like the superclass.
     * 
     * @param component Component to be repainted
     * @param x X coordinate of the dirty region in the component
     * @param y Y coordinate of the dirty region in the component
     * @param width Width of the dirty region in the component
     * @param height Height of the dirty region in the component
     */
    public synchronized void addDirtyRegion(final JComponent component, final int x, final int y, final int width, final int height) {
        boolean captureRepaint = false;
        JComponent childComponent = null;
        int captureX = x;
        int captureY = y;

        // We have to check to see if the PCanvas (ie. the SwingWrapper) is in the components ancestry. If so, we will
        // want to capture that repaint. However, we also will need to translate the repaint request since the component
        // may be offset inside another component.
        for (Component comp = component; comp != null && comp.isLightweight(); comp = comp.getParent()) {
            if (comp.getParent() instanceof PSwingCanvas.ChildWrapper) {
                captureRepaint = true;
                childComponent = (JComponent) comp;
                break;
            }
            else {
                // Adds to the offset since the component is nested
                captureX += comp.getLocation().getX();
                captureY += comp.getLocation().getY();
            }
        }

        // Now we check to see if we should capture the repaint and act accordingly
        if (captureRepaint) {
            if (!isPainting(childComponent)) {
                final double repaintW = Math.min(childComponent.getWidth() - captureX, width);
                final double repaintH = Math.min(childComponent.getHeight() - captureY, height);

                //Schedule a repaint for the dirty part of the PSwing
                getPSwing(childComponent).repaint(new PBounds(captureX, captureY, repaintW, repaintH));
            }
        }
        else {
            super.addDirtyRegion(component, x, y, width, height);
        }
    }

    /**
     * This is the method "invalidate" calls in the Swing components. Overridden
     * to capture invalidation calls from those Swing components being used as
     * Core visual components and to update Core's visual component
     * wrapper bounds (these are stored separately from the Swing component).
     * Otherwise, behaves like the superclass.
     * 
     * @param invalidComponent The Swing component that needs validation
     */
    public synchronized void addInvalidComponent(final JComponent invalidComponent) {
        if (invalidComponent.getParent() == null || !(invalidComponent.getParent() instanceof PSwingCanvas.ChildWrapper)) {
            super.addInvalidComponent(invalidComponent);
        }
        else {
            invalidComponent.validate();
            getPSwing(invalidComponent).updateBounds();
        }
    }

    /**
     * Obtains the PSwing associated with the specified component.
     * @param component the component for which to return the associated PSwing
     * @return the associated PSwing
     */
    private PSwing getPSwing(JComponent component) {
        return (PSwing) component.getClientProperty( PSwing.PSWING_PROPERTY );
    }
}