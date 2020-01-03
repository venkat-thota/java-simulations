

package com.aimxcel.abclearn.common.abclearncommon.application;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.aimxcel.abclearn.common.abclearncommon.dialogs.CreditsDialog;
import com.aimxcel.abclearn.common.abclearncommon.resources.AbcLearnCommonResources;
import com.aimxcel.abclearn.common.abclearncommon.view.VerticalLayoutPanel;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;
import com.aimxcel.abclearn.common.abclearncommon.view.util.SwingUtils;

import com.aimxcel.abclearn.common.abclearncommon.application.KSUCreditsWindow;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;

public class KSUCreditsWindow extends JWindow {

    private static final String TRANSLATED_BY = AbcLearnCommonResources.getString( "Common.About.CreditsDialog.TranslationCreditsTitle" );

    // Constructor is private, creation and display is handled by static methods.
    private KSUCreditsWindow( Frame parent ) {
        super( parent );

        JLabel label = new JLabel( TRANSLATED_BY );
        label.setFont( new AbcLearnFont( 18 ) );

        JLabel logo = new JLabel( new ImageIcon( AbcLearnCommonResources.getImage( "logos/ECSME-KSU-logos.jpg" ) ) );

        VerticalLayoutPanel panel = new VerticalLayoutPanel();
        int margin = 12;
        panel.setBorder( new CompoundBorder( new LineBorder( Color.BLACK, 1 ), new EmptyBorder( margin, margin, margin, margin ) ) );
        panel.add( label );
        panel.add( Box.createVerticalStrut( 5 ) );
        panel.add( logo );

        setContentPane( panel );
        pack();

        // click on the window to make it go away
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent event ) {
                dispose();
            }
        } );
    }

    /*
     * If the string file contains a special KSU translation credit (inserted by Translation Utility),
     * then show a KSU-specific "splash" screen with credits.
     */
    public static JWindow show( Frame parent ) {
        final JWindow window = new KSUCreditsWindow( parent );
        SwingUtils.centerInParent( window );
        window.setVisible( true );

        /*
        *  Dispose of ksuCreditsWindow after N seconds.
        *  Take care to call dispose in the Swing thread.
        */
        Timer timer = new Timer( 4000, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                if ( window.isDisplayable() ) {
                    window.dispose();
                }
            }
        } );
        timer.setRepeats( false );
        timer.start();

        return window;
    }

    // The KSU credits window should be shown if there's a credits entry in the strings file.
    public static boolean shouldShow( AbcLearnApplicationConfig config ) {
        String credits = config.getResourceLoader().getLocalizedProperties().getString( CreditsDialog.KSU_CREDITS_KEY, false /* warnIfMissing */ );
        return !credits.equals( CreditsDialog.KSU_CREDITS_KEY );
    }

    public static void main( String[] args ) {
        JWindow window = new KSUCreditsWindow( null );
        SwingUtils.centerWindowOnScreen( window );
        window.setVisible( true );
    }
}
