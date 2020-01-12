package com.aimxcel.abclearn.glaciers.dialog;

import java.awt.Dimension;
import java.awt.Frame;

import com.aimxcel.abclearn.glaciers.control.EvolutionStatePanel;
import com.aimxcel.abclearn.glaciers.model.Glacier;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class EvolutionStateDialog extends PaintImmediateDialog {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final EvolutionStatePanel _panel;

    public EvolutionStateDialog( Frame owner, Glacier glacier, String moduleName ) {
        super( owner, "Glacier Evolution State (" + moduleName + ")" );
        setModal( false );
        setResizable( false );
        _panel = new EvolutionStatePanel( glacier );
        getContentPane().add( _panel );
        setSize( new Dimension( 450, (int) _panel.getPreferredSize().getHeight() + 50 ) ); // instead of pack() because displayed values will grow
        SwingUtils.centerDialogInParent( this );
    }
    
    public void dispose() {
        _panel.cleanup();
        super.dispose();
    }
}
