
package com.aimxcel.abclearn.common.abclearncommon.view.menu;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.view.controls.ColorControl;
import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;

/**
 * Controls for properties related to a Module's standard control panels.
 * These are developer controls and are therefore not internationalized.
 *
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
public class ControlPanelPropertiesDialog extends PaintImmediateDialog {

    private AbcLearnApplication app;
    private final ColorControl colorControl;

    public ControlPanelPropertiesDialog( final AbcLearnApplication app ) {
        super( app.getAbcLearnFrame() );
        setTitle( "Control Panel properties" );

        this.app = app;

        colorControl = new ColorControl( app.getAbcLearnFrame(), "background color: ", Color.WHITE );
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
