

package com.aimxcel.abclearn.magnetsandelectromagnets.control.dialog;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JDialog;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ColorChooserFactory;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsStrings;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsSimSharing.Components;
import com.aimxcel.abclearn.magnetsandelectromagnets.module.ICompassGridModule;



public class BackgroundColorHandler implements ColorChooserFactory.Listener {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private AimxcelApplication _app;
    private JDialog _dialog;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param app the application
     */
    public BackgroundColorHandler( AimxcelApplication app ) {
        super();
        _app = app;
        String title = MagnetsAndElectromagnetsStrings.TITLE_BACKGROUND_COLOR;
        Component parent = app.getAimxcelFrame();

        // Start with the active module's background color.
        Color initialColor = app.getActiveModule().getSimulationPanel().getBackground();

        _dialog = ColorChooserFactory.createDialog( title, parent, initialColor, this );
    }

    //----------------------------------------------------------------------------
    // Setters and getters
    //----------------------------------------------------------------------------

    public JDialog getDialog() {
        return _dialog;
    }

    //----------------------------------------------------------------------------
    // ColorChooserFactory.Listener implementation
    //----------------------------------------------------------------------------

    /*
     * @see edu.colorado.phet.magnets-and-electromagnets.control.ColorChooserFactory.Listener#colorChanged(java.awt.Color)
     */
    public void colorChanged( Color color ) {
        handleColorChange( color );
    }

    /*
     * @see edu.colorado.phet.magnets-and-electromagnets.control.ColorChooserFactory.Listener#ok(java.awt.Color)
     */
    public void ok( Color color ) {
        SimSharingManager.sendUserMessage( UserComponentChain.chain( Components.backgroundColorDialog, Components.okButton ), UserComponentTypes.button, UserActions.pressed );
        handleColorChange( color );
    }

    /*
     * @see edu.colorado.phet.magnets-and-electromagnets.control.ColorChooserFactory.Listener#cancelled(java.awt.Color)
     */
    public void cancelled( Color originalColor ) {
        SimSharingManager.sendUserMessage( UserComponentChain.chain( Components.backgroundColorDialog, Components.cancelButton ), UserComponentTypes.button, UserActions.pressed );
        handleColorChange( originalColor );
    }

    /*
     * Sets the background color for all apparatus panels in all modules.
     * If the module has a compass grid, sets whether it uses alpha.
     *
     * @param color the color
     */
    private void handleColorChange( Color color ) {
        int numberOfModules = _app.numModules();
        for ( int i = 0; i < numberOfModules; i++ ) {
            Module module = _app.getModule( i );
            module.getSimulationPanel().setBackground( color );
            if ( module instanceof ICompassGridModule ) {
                ( (ICompassGridModule) module ).setGridBackground( color );
            }
        }
        _app.getAimxcelFrame().repaint();
    }
}
