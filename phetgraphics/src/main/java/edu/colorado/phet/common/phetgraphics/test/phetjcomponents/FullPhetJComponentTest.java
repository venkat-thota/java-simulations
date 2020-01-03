
package edu.colorado.phet.common.phetgraphics.test.phetjcomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.abclearncommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.abclearncommon.view.util.ImageLoader;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;

import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;
import edu.colorado.phet.common.phetgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import edu.colorado.phet.common.phetgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import edu.colorado.phet.common.phetgraphics.view.phetcomponents.AbcLearnJComponent;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.RepaintDebugGraphic;
import edu.colorado.phet.common.phetgraphics.view.util.BasicGraphicsSetup;

/**
 * Created by IntelliJ IDEA.
 * User: Sam Reid
 * Date: Mar 8, 2005
 * Time: 8:49:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class FullAbcLearnJComponentTest {
    private JFrame frame;
    private ApparatusPanel2 ap;
    private SwingClock swingClock;

    public FullAbcLearnJComponentTest() throws IOException {

        frame = new JFrame( "Frame" );
        AbcLearnJComponent.init( frame );//todo integrate into AbcLearnFrame.

        SwingClock swingClock = new SwingClock( 30, 1.0 );
        ap = new ApparatusPanel2( swingClock );
        ap.addGraphicsSetup( new BasicGraphicsSetup() );
        JButton jb = new JButton( "JButton" );
        jb.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.out.println( "e = " + e );
            }
        } );
        AbcLearnGraphic buttonAbcLearnJ = AbcLearnJComponent.newInstance( ap, jb );
        ap.addGraphic( buttonAbcLearnJ );

        frame.setContentPane( ap );
        frame.setSize( 600, 600 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        buttonAbcLearnJ.setCursorHand();
        buttonAbcLearnJ.scale( 2 );

        JTextField text = new JTextField( 10 );
        text.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                System.out.println( "ActionEvent: Someone pressed enter: e = " + e );
            }
        } );
        text.setBorder( BorderFactory.createTitledBorder( "TextField" ) );
        AbcLearnGraphic textFieldAbcLearnJ = AbcLearnJComponent.newInstance( ap, text );
        ap.addGraphic( textFieldAbcLearnJ );
        textFieldAbcLearnJ.setLocation( 100, 300 );

        AbcLearnGraphic checkBoxAbcLearnJ = AbcLearnJComponent.newInstance( ap, new JCheckBox( "Checkbox" ) );
        ap.addGraphic( checkBoxAbcLearnJ );
        checkBoxAbcLearnJ.setLocation( 300, 50 );

        JRadioButton jRadioButton = new JRadioButton( "Option A" );
        JRadioButton jRadioButton2 = new JRadioButton( "The other Option" );
        jRadioButton.setSelected( true );

        AbcLearnGraphic radioButton1 = AbcLearnJComponent.newInstance( ap, jRadioButton );
        AbcLearnGraphic radioButton2 = AbcLearnJComponent.newInstance( ap, jRadioButton2 );
        ap.addGraphic( radioButton1 );
        ap.addGraphic( radioButton2 );
        radioButton1.scale( 1.3 );
        radioButton2.scale( 1.4 );
        radioButton1.setLocation( 300, 200 );
        radioButton2.setLocation( 300, 200 + radioButton2.getHeight() );

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add( jRadioButton );
        buttonGroup.add( jRadioButton2 );

        buttonAbcLearnJ.setLocation( 100, 100 );
        this.swingClock = swingClock;
        this.swingClock.addClockListener( new ClockAdapter() {
            public void clockTicked( ClockEvent event ) {
                ap.handleUserInput();
//                ap.paintImmediately( new Rectangle( 0, 0, ap.getWidth(), ap.getLength() ) );
                AbcLearnJComponent.getRepaintManager().updateGraphics();
                ap.paint();

            }
        } );

        final JButton pressIt = new JButton( "Play",
                                             new ImageIcon( ImageLoader.loadBufferedImage( "images/icons/java/media/Play24.gif" ) ) );
        pressIt.setFont( new AbcLearnFont( Font.BOLD, 22 ) );
        pressIt.setForeground( Color.blue );
        pressIt.setBackground( Color.green );

        final JButton pauseIt = new JButton( "Pause", new ImageIcon( ImageLoader.loadBufferedImage( "images/icons/java/media/Pause24.gif" ) ) );
        pauseIt.setFont( new AbcLearnFont( Font.BOLD, 22 ) );
        pauseIt.setForeground( Color.red );
        pauseIt.setBackground( Color.green );

        pauseIt.setEnabled( false );
        pressIt.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                pauseIt.setEnabled( true );
                pressIt.setEnabled( false );
            }
        } );
        pauseIt.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                pauseIt.setEnabled( false );
                pressIt.setEnabled( true );
            }
        } );

        AbcLearnGraphic phetJComponent = AbcLearnJComponent.newInstance( ap, pressIt );
        ap.addGraphic( phetJComponent );
        phetJComponent.setLocation( 300, 100 );


        AbcLearnGraphic pauseComponent = AbcLearnJComponent.newInstance( ap, pauseIt );
        ap.addGraphic( pauseComponent );
        pauseComponent.setLocation( phetJComponent.getX() + phetJComponent.getWidth() + 5, phetJComponent.getY() );

//        JSlider slider = new JSlider( 0, 100 );
//        JSlider slider = new JSlider( new DefaultBoundedRangeModel( 5, 0, 0, 100 ) );
        {
            final JSlider slider = new JSlider( new DefaultBoundedRangeModel() );
            slider.setBorder( BorderFactory.createTitledBorder( "Gravity (m/s^2)" ) );
            slider.setPaintTicks( true );
            slider.setPaintTrack( true );
            slider.setPaintLabels( true );

            slider.setMajorTickSpacing( 10 );
            slider.setMinorTickSpacing( 5 );
            Hashtable labels = new Hashtable();
            labels.put( new Integer( 9 ), new JLabel( "<html><italics>Earth</italics></html>" ) );
            labels.put( new Integer( 48 ), new JLabel( "phetland" ) );
            slider.addChangeListener( new ChangeListener() {
                public void stateChanged( ChangeEvent e ) {
                    int value = slider.getValue();
                    System.out.println( "value = " + value );
                }
            } );
            slider.setLabelTable( labels );
            AbcLearnGraphic zslider = AbcLearnJComponent.newInstance( ap, slider );
            ap.addGraphic( zslider );
            zslider.setLocation( 400, 300 );

        }
        {
//        zslider.rotate( Math.PI / 32, 400, 300 );
            final JSlider slider = new JSlider( new DefaultBoundedRangeModel() );
            slider.setBorder( BorderFactory.createTitledBorder( "AbcLearnGraphics can be transformed" ) );
            slider.setPaintTicks( true );
            slider.setPaintTrack( true );
            slider.setPaintLabels( true );

            slider.setMajorTickSpacing( 10 );
            slider.setMinorTickSpacing( 5 );
            Hashtable labels = new Hashtable();
            labels.put( new Integer( 9 ), new JLabel( "Whoo" ) );
            labels.put( new Integer( 48 ), new JLabel( "lala" ) );
            slider.addChangeListener( new ChangeListener() {
                public void stateChanged( ChangeEvent e ) {
                    int value = slider.getValue();
                    System.out.println( "value = " + value );
                }
            } );
            slider.setLabelTable( labels );
            AbcLearnGraphic zslider = AbcLearnJComponent.newInstance( ap, slider );
            ap.addGraphic( zslider );
            zslider.setLocation( 100, 400 );
            zslider.rotate( Math.PI / 32 );
            zslider.scale( 1.3 );
        }

        JButton draggableButton = new JButton( "DraggableButton" );
//        JButton draggableButton = new JButton( "DraggableButton", new ImageIcon( ImageLoader.loadBufferedImage( "images/x-30.png" ) ) );
        final AbcLearnGraphic phetJComponentDraggable = AbcLearnJComponent.newInstance( ap, draggableButton );
        phetJComponentDraggable.addTranslationListener( new TranslationListener() {
            public void translationOccurred( TranslationEvent translationEvent ) {
                phetJComponentDraggable.setLocation( translationEvent.getX(), translationEvent.getY() );
            }
        } );
        ap.addGraphic( phetJComponentDraggable );


        JSpinner spinner = new JSpinner();
        AbcLearnGraphic spinnerGraphic = AbcLearnJComponent.newInstance( ap, spinner );
        spinnerGraphic.setLocation( 50, 100 );
        ap.addGraphic( spinnerGraphic );

        JTextArea textArea = new JTextArea( "This land is your land\nThis land is my land.", 2, 15 );
        textArea.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.blue, 2 ), "Text Area!" ) );
        textArea.setFont( new AbcLearnFont( Font.BOLD, 22 ) );
        AbcLearnGraphic pj = AbcLearnJComponent.newInstance( ap, textArea );
        pj.setLocation( 15, 200 );
//        pj.scale( 1.45);
        pj.scale( 0.8 );
        ap.addGraphic( pj );
        //composites seem like the way to approach JSpinners (and solve other problems.)
//        JSpinner jSpinner = new JSpinner( new SpinnerNumberModel( 5, 0, 10, 1 ) );
//        jSpinner.setBorder( BorderFactory.createTitledBorder( "Spin" ) );
//        BasicSpinnerUI basicSpinnerUI = new BasicSpinnerUI();
////        basicSpinnerUI.installUI( jSpinner );
//        jSpinner.setUI( basicSpinnerUI );
//        Component[] ch = jSpinner.getComponents();
//        for( int i = 0; i < ch.length; i++ ) {
//            JComponent component = (JComponent)ch[i];
//            AbcLearnJComponent pjStar = AbcLearnJComponent.newInstance( ap, component );
//            Point loc = component.getLocation();
//            ap.addGraphic( pjStar );
//            pjStar.setLocation( loc.x+i*10,loc.y );
//        }
//        AbcLearnJComponent pj = AbcLearnJComponent.newInstance( ap, jSpinner );
//        ap.addGraphic( pj );
//        pj.setLocation( 50, 350 );

//        ap.add( jSpinner );
//        jSpinner.reshape( 100, 100, jSpinner.getPreferredSize().width, jSpinner.getPreferredSize().height );

        RepaintDebugGraphic.enable( ap, swingClock );
    }

    public static void main( String[] args ) throws IOException {
        new FullAbcLearnJComponentTest().start();
    }

    private void start() {
        swingClock.start();
        frame.setVisible( true );
    }
}
