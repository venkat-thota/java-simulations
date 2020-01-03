
package edu.colorado.phet.eatingandexercise;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationLauncher;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnFrame;
import com.aimxcel.abclearn.common.abclearncommon.view.AbcLearnFrameWorkaround;

import edu.colorado.phet.common.piccolophet.PiccoloAbcLearnApplication;
import edu.colorado.phet.eatingandexercise.developer.DeveloperFrame;
import edu.colorado.phet.eatingandexercise.module.eatingandexercise.EatingAndExerciseModule;

public class EatingAndExerciseApplication extends PiccoloAbcLearnApplication {

    private EatingAndExerciseModule eatingAndExerciseModule;

    public EatingAndExerciseApplication( AbcLearnApplicationConfig config ) {
        super( config );
        eatingAndExerciseModule = new EatingAndExerciseModule( getAbcLearnFrame() );
        addModule( eatingAndExerciseModule );
        initMenubar();
    }

    protected AbcLearnFrame createAbcLearnFrame() {
        return new AbcLearnFrameWorkaround( this );
    }

    private void initMenubar() {
        final AbcLearnFrame frame = getAbcLearnFrame();

        // Developer menu
        JMenu developerMenu = getAbcLearnFrame().getDeveloperMenu();
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
        AbcLearnApplicationConfig config = new AbcLearnApplicationConfig( args, EatingAndExerciseConstants.PROJECT_NAME );
        config.setLookAndFeel( new EatingAndExerciseLookAndFeel() );
        new AbcLearnApplicationLauncher().launchSim( config, EatingAndExerciseApplication.class );
    }
}