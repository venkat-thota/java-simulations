
package com.aimxcel.abclearn.aimxceljmol;

import org.jmol.api.JmolViewer;

public class JmolUtil {

    /*
     * Unbinds the mouse from all Jmol actions.
     * Actions are enumerated at http://chemapps.stolaf.edu/jmol/docs/#bind
     * Jmol does not provide a way to unbind all actions, or to specify only those actions to bind.
     * So we're stuck with this brute-force method of unbinding each action separately.
     */
    public static void unbindMouse( JmolViewer viewer ) {
        String[] actions = {
                "_clickFrank",
                "_depth",
                "_dragDrawObject",
                "_dragDrawPoint",
                "_dragLabel",
                "_dragSelected",
                "_navTranslate",
                "_pickAtom",
                "_pickIsosurface",
                "_pickLabel",
                "_pickMeasure",
                "_pickNavigate",
                "_pickPoint",
                "_popupMenu",
                "_reset",
                "_rotate",
                "_rotateSelected",
                "_rotateZ",
                "_rotateZorZoom",
                "_select",
                "_selectAndNot",
                "_selectNone",
                "_selectOr",
                "_selectToggle",
                "_selectToggleOr",
                "_setMeasure",
                "_slab",
                "_slabAndDepth",
                "_slideZoom",
                "_spinDrawObjectCCW",
                "_spinDrawObjectCW",
                "_swipe",
                "_translate",
                "_wheelZoom",
        };
        for ( String action : actions ) {
            viewer.scriptWait( "unbind \"" + action + "\"" );
        }
    }

    // Binds the left mouse button to Jmol's rotate action.
    public static void bindRotateLeft( JmolViewer viewer ) {
        viewer.scriptWait( "bind \"LEFT\" \"_rotate\"" );
    }
}
