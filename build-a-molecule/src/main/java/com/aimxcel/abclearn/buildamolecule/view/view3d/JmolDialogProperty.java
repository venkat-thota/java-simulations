//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.view.view3d;

import com.aimxcel.abclearn.aimxceljmol.JmolDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option.None;

/**
 * Represents an optional dialog, with a convenience method for hiding the dialog if it is shown
 */
public class JmolDialogProperty extends Property<Option<JmolDialog>> {
    public JmolDialogProperty() {
        super( new None<JmolDialog>() );
    }

    public void hideDialogIfShown() {
        if ( get().isSome() ) {
            get().get().dispose();
            set( new Option.None<JmolDialog>() );
        }
    }
}
