
package edu.colorado.phet.eatingandexercise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationLauncher;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrame;
import com.aimxcel.abclearn.common.aimxcelcommon.view.AimxcelFrameWorkaround;
import com.aimxcel.abclearn.core.aimxcelcore.PiccoloAimxcelApplication;

import edu.colorado.phet.eatingandexercise.developer.DeveloperFrame;
import edu.colorado.phet.eatingandexercise.module.eatingandexercise.EatingAndExerciseModule;

public class EatingAndExerciseApplication extends PiccoloAimxcelApplication {

    private EatingAndExerciseModule eatingAndExerciseModule;

    public EatingAndExerciseApplication( AimxcelApplicationConfig config ) {
        super( config );
        eatingAndExerciseModule = new EatingAndExerciseModule( getAimxcelFrame() );
        addModule( eatingAndExerciseModule );
        initMenubar();
    }

    protected AimxcelFrame createAimxcelFrame() {
        return new AimxcelFrameWorkaround( this );
    }

    private void initMenubar() {
        final AimxcelFrame frame = getAimxcelFrame();

        // Developer menu
        JMenu developerMenu = getAimxcelFrame().getDeveloperMenu();
        JMenuItem menuItem = new JMenuItem( "Show Model Controls..." );
        menuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                new DeveloperFrame().setVisible( true );
            }
        } );
        developerMenu.add( menuItem );

        if ( developerMenu.getMenuComponentCount() > 0 && isDeveloperControlsEnabled() ) {
            frame.addMenu( developerMenu );
        }
    }

    public void startApplication() {
        super.startApplication();
        eatingAndExerciseModule.applicationStarted();
    }

    public static void main( final String[] args ) {
        AimxcelApplicationConfig config = new AimxcelApplicationConfig( args, EatingAndExerciseConstants.PROJECT_NAME );
        config.setLookAndFeel( new EatingAndExerciseLookAndFeel() );
        new AimxcelApplicationLauncher().launchSim( config, EatingAndExerciseApplication.class );
    }
}