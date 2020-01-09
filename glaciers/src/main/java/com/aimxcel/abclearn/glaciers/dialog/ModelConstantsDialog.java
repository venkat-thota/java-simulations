// Copyright 2002-2011, University of Colorado

package com.aimxcel.abclearn.glaciers.dialog;

import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JDialog;

import com.aimxcel.abclearn.glaciers.control.ModelConstantsPanel;
import com.aimxcel.abclearn.glaciers.model.Glacier;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class ModelConstantsDialog extends PaintImmediateDialog {
    
    private final ModelConstantsPanel _panel;

    public ModelConstantsDialog( Frame owner, Glacier glacier, String moduleName ) {
        super( owner, "Model Constants (" + moduleName + ")" );
        setModal( false );
        setResizable( false );
        _panel = new ModelConstantsPanel( glacier );
        getContentPane().add( _panel );
        setSize( new Dimension( 300, (int) _panel.getPreferredSize().getHeight() + 50 ) );
        SwingUtils.centerDialogInParent( this );
    }
    
    public void dispose() {
        _panel.cleanup();
        super.dispose();
    }
}
