
package com.aimxcel.abclearn.common.aimxcelcommon.view.menu;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.ColorControl;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;


public class ControlPanelPropertiesDialog extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AimxcelApplication app;
    private final ColorControl colorControl;

    public ControlPanelPropertiesDialog( final AimxcelApplication app ) {
        super( app.getAimxcelFrame() );
        setTitle( "Control Panel properties" );

        this.app = app;

        colorControl = new ColorControl( app.getAimxcelFrame(), "background color: ", Color.WHITE );
        colorControl.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent event ) {
                app.setControlPanelBackground( colorControl.getColor() );
            }
        } );

        JPanel panel = new JPanel();
        panel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        panel.add( colorControl );

        setContentPane( panel );
        pack();
        SwingUtils.centerDialogInParent( this );
    }

    /**
     * When this dialog is made visible, use the color of the app's first module to
     * initial the color control. We can't do this initialization in the constructor,
     * because the app may not have any modules when this dialog is constructed.
     */
    @Override
    public void setVisible( boolean visible ) {
        if ( app.getModules().length > 0 ) {
            Module module = app.getModule( 0 );
            if ( module.getControlPanel() != null ) {
                colorControl.setColor( module.getControlPanel().getBackground() );
            }
        }
        super.setVisible( visible );
    }
}
