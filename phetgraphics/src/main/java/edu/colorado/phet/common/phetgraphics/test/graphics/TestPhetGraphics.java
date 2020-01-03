

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package edu.colorado.phet.common.phetgraphics.test.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.abclearncommon.view.util.AbcLearnFont;
import com.aimxcel.abclearn.common.abclearncommon.view.util.RectangleUtils;

import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel;
import edu.colorado.phet.common.phetgraphics.view.graphics.mousecontrols.translation.TranslationEvent;
import edu.colorado.phet.common.phetgraphics.view.graphics.mousecontrols.translation.TranslationListener;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.CompositeAbcLearnGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnImageGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnMultiLineTextGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnShadowTextGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnShapeGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnTextGraphic;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.RepaintDebugGraphic;
import edu.colorado.phet.common.phetgraphics.view.util.BasicGraphicsSetup;

/**
 * User: Sam Reid
 * Date: Dec 6, 2004
 * Time: 5:19:19 PM
 */

public class TestAbcLearnGraphics extends JFrame {
    private ApparatusPanel panel;
    private IClock clock;

    static interface TestAbcLearnGraphicSource {
        public AbcLearnGraphic createGraphic( ApparatusPanel panel );
    }

    public TestAbcLearnGraphics() throws HeadlessException {
        super( "Test AbcLearnGraphics" );
        panel = new ApparatusPanel();
        panel.addGraphicsSetup( new BasicGraphicsSetup() );
        TestAbcLearnGraphicSource[] graphics = new TestAbcLearnGraphicSource[] {
                new TestAbcLearnGraphicSource() {
                    public AbcLearnGraphic createGraphic( ApparatusPanel panel ) {
                        return new AbcLearnTextGraphic( panel, new AbcLearnFont( Font.BOLD, 24 ), "AbcLearnGraphic Test", Color.blue, 100, 100 );
                    }
                },
                new TestAbcLearnGraphicSource() {
                    public AbcLearnGraphic createGraphic( ApparatusPanel panel ) {
                        return new AbcLearnShapeGraphic( panel, new Rectangle( 50, 50, 50, 50 ), Color.green, new BasicStroke( 1 ), Color.black );
                    }
                },
                new TestAbcLearnGraphicSource() {
                    public AbcLearnGraphic createGraphic( ApparatusPanel panel ) {
                        return new AbcLearnImageGraphic( panel, "images/AbcLearn-Flatirons-logo-3-small.gif" );
                    }
                },
                new TestAbcLearnGraphicSource() {
                    public AbcLearnGraphic createGraphic( ApparatusPanel panel ) {
                        return new AbcLearnMultiLineTextGraphic( panel, new Font( "dialog", 0, 28 ), new String[] { "PhET", "Multi-", "Line", "TextGraphic" }, Color.red, 1, 1, Color.yellow );
                    }
                },
                new TestAbcLearnGraphicSource() {
                    public AbcLearnGraphic createGraphic( ApparatusPanel panel ) {
                        return new AbcLearnShadowTextGraphic( panel, new Font( "dialog", Font.BOLD, 28 ), "Shadowed", Color.blue, 1, 1, Color.green );
                    }
                },
                new TestAbcLearnGraphicSource() {
                    public AbcLearnGraphic createGraphic( ApparatusPanel panel ) {
                        CompositeAbcLearnGraphic cpg = new CompositeAbcLearnGraphic( panel );
                        cpg.addGraphic( new AbcLearnShapeGraphic( panel, new Ellipse2D.Double( 130, 30, 30, 30 ), Color.red ) );
                        cpg.addGraphic( new AbcLearnShapeGraphic( panel, new Ellipse2D.Double( 160, 30, 30, 30 ), Color.blue ) );
                        cpg.addGraphic( new AbcLearnShadowTextGraphic( panel, new AbcLearnFont( 0, 12 ), "compositegraphic", Color.white, 1, 1, Color.black ) );
                        return cpg;
                    }
                },
                new TestAbcLearnGraphicSource() {
                    public AbcLearnGraphic createGraphic( ApparatusPanel panel ) {
//                    Stroke stroke = new BasicStroke( 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 4, new float[]{6, 6}, 0 );
                        Stroke stroke = new BasicStroke( 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 2 );//, new float[]{6, 6}, 0 );
                        final OutlineTextGraphic g = new OutlineTextGraphic( panel, "Outline Text", new AbcLearnFont( Font.ITALIC, 68 ), 0, 0, Color.yellow, stroke, Color.black );
                        g.setBorderPaint( new GradientPaint( 0, 0, Color.red, 300, 300, Color.blue ) );
                        return g;
                    }
                }
        };
        for ( int i = 0; i < graphics.length; i++ ) {
            TestAbcLearnGraphicSource graphic = graphics[i];
            final AbcLearnGraphic pg = graphic.createGraphic( panel );
            pg.setCursorHand();
            pg.setBoundsDirty();
            pg.setLocation( 0, 0 );
            Rectangle bounds = pg.getBounds();
            if ( bounds == null ) {
                System.out.println( "error" );
            }
            Point center = RectangleUtils.getCenter( pg.getBounds() );
//            pg.setRegistrationPoint( bounds.width / 2, bounds.height / 2 );
            pg.setRegistrationPoint( center.x, center.y );
            pg.setLocation( i * 50, 100 );
            pg.addMouseInputListener( new MouseInputAdapter() {
                // implements java.awt.event.MouseMotionListener
                public void mouseDragged( MouseEvent e ) {
                    if ( SwingUtilities.isRightMouseButton( e ) ) {
//                        Point ctr = RectangleUtils.getCenter( pg.getBounds() );
//                        pg.transform( AffineTransform.getRotateInstance( Math.PI / 36, ctr.x, ctr.y ) );
                        pg.rotate( Math.PI / 64 );
                    }
                }
            } );
            pg.addTranslationListener( new TranslationListener() {
                public void translationOccurred( TranslationEvent translationEvent ) {
                    if ( SwingUtilities.isLeftMouseButton( translationEvent.getMouseEvent() ) ) {
//                        pg.transform( AffineTransform.getTranslateInstance( translationEvent.getDx(), translationEvent.getDy() ) );
                        pg.setLocation( translationEvent.getX(), translationEvent.getY() );
                    }
                }
            } );
            panel.addGraphic( pg );
        }

        setContentPane( panel );
        setSize( 600, 600 );
        panel.requestFocus();
        panel.addMouseListener( new MouseAdapter() {
            public void mouseReleased( MouseEvent e ) {
                panel.requestFocus();
            }
        } );
        clock = new SwingClock( 30, 1.0 );
        panel.addGraphic( new AbcLearnShapeGraphic( panel, new Rectangle( 5, 5, 5, 5 ), Color.black ) );
        final RepaintDebugGraphic rdg = new RepaintDebugGraphic( panel, clock );
        panel.addGraphic( rdg );

        rdg.setActive( false );
        rdg.setVisible( false );

        panel.addKeyListener( new KeyListener() {
            public void keyPressed( KeyEvent e ) {
                if ( e.getKeyCode() == KeyEvent.VK_SPACE ) {
                    rdg.setActive( !rdg.isActive() );
                    rdg.setVisible( rdg.isActive() );
                }
            }

            public void keyReleased( KeyEvent e ) {
            }

            public void keyTyped( KeyEvent e ) {
            }
        } );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        final AbcLearnShapeGraphic graphic = new AbcLearnShapeGraphic( panel, new Rectangle( panel.getWidth(), panel.getHeight() ), Color.white );
        panel.addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                graphic.setShape( new Rectangle( panel.getWidth(), panel.getHeight() ) );
            }
        } );

        graphic.addMouseInputListener( new MouseInputAdapter() {
            // implements java.awt.event.MouseMotionListener
            public void mouseDragged( MouseEvent e ) {
                Point newDragPt = e.getPoint();

                int dx = lastDragPt == null ? 0 : newDragPt.x - lastDragPt.x;
                double dd = 0.01;
                double ds = dx > 0 ? 1 + dd : 1 - dd;
                panel.getGraphic().scale( ds );
                lastDragPt = newDragPt;
            }
        } );
        panel.addGraphic( graphic, Double.NEGATIVE_INFINITY );
    }

    static Point lastDragPt = null;

    public static void main( String[] args ) {
        new TestAbcLearnGraphics().start();
    }

    private void start() {
        clock.start();
        panel.requestFocus();
        setVisible( true );
    }

    public static class OutlineTextGraphic extends AbcLearnShapeGraphic {
        private String text;
        private Font font;
        private FontRenderContext fontRenderContext;

        public OutlineTextGraphic( Component component, String text, Font font, int x, int y, Color fillColor, Stroke stroke, Color strokeColor ) {
            super( component );
            this.text = text;
            this.font = font;
            setShape( createTextShape() );
            setColor( fillColor );
            setStroke( stroke );
            setBorderColor( strokeColor );
            this.fontRenderContext = new FontRenderContext( new AffineTransform(), true, false );
            component.addComponentListener( new ComponentAdapter() {
                public void componentShown( ComponentEvent e ) {
                    update();
                }
            } );
            component.addComponentListener( new ComponentAdapter() {
                public void componentResized( ComponentEvent e ) {
                    update();
                }
            } );
            setLocation( x, y );
            update();
        }

        void update() {
            setShape( createTextShape() );
        }

        private Shape createTextShape() {
//            Graphics2D g2 = (Graphics2D)getComponent().getGraphics();
//            if( g2 != null ) {
//                FontRenderContext frc = g2.getFontRenderContext();
            FontRenderContext frc = fontRenderContext;
            if ( frc != null ) {
                TextLayout textLayout = new TextLayout( text, font, frc );
                return textLayout.getOutline( new AffineTransform() );
            }
//            }
            return new Rectangle();
        }

    }

}