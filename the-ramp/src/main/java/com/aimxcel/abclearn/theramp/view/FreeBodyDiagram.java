package com.aimxcel.abclearn.theramp.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.AbstractVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.Arrow;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.theramp.RampModule;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;



public class FreeBodyDiagram extends PNode {
    private RampModule module;
    private PPath background;
    private AxesGraphic axes;
    private Rectangle rect;

    private ForceArrow mg;
    private ForceArrow normal;
    private ForceArrow appliedForce;
    private ForceArrow wallForce;
    private ForceArrow frictionForce;
    private ForceArrow netForce;

    private double scale = 1.0 / 20.0;
    private RampLookAndFeel laf;
    private boolean userClicked = false;
    private RampPhysicalModel model;
    private RampPanel component;
    private JComponent owner;

    public FreeBodyDiagram( RampPanel component, final RampModule module, JComponent owner ) {
        this.component = component;
        this.owner = owner;
        this.model = module.getRampPhysicalModel();
        this.module = module;
        rect = new Rectangle( 0, 0, 200, 200 );
        laf = new RampLookAndFeel();

        background = new PPath( rect );
        background.setPaint( Color.white );
        background.setStroke( new BasicStroke( 1.0f ) );
        background.setStrokePaint( Color.black );
        addChild( background );
        axes = new AxesGraphic();
        addChild( axes );

        mg = new ForceArrow( this, laf.getWeightColor(), ( TheRampStrings.getString( "force.subscript.gravity" ) ), new MutableVector2D( 0, 80 ) );
        addForceArrow( mg );

        normal = new ForceArrow( this, laf.getNormalColor(), ( TheRampStrings.getString( "force.subscript.normal" ) ), new MutableVector2D( 0, 80 ) );
        addForceArrow( normal );

        appliedForce = new ForceArrow( this, laf.getAppliedForceColor(), ( TheRampStrings.getString( "force.subscript.applied" ) ), new MutableVector2D() );
        addForceArrow( appliedForce );

        frictionForce = new ForceArrow( this, laf.getFrictionForceColor(), ( TheRampStrings.getString( "force.subscript.friction" ) ), new MutableVector2D() );
        addForceArrow( frictionForce );

        netForce = new ForceArrow( this, laf.getNetForceColor(), ( TheRampStrings.getString( "force.subscript.net" ) ), new MutableVector2D() );
        addForceArrow( netForce );
        netForce.setVerticalOffset( -30 );

        wallForce = new ForceArrow( this, laf.getWallForceColor(), ( TheRampStrings.getString( "force.subscript.wall" ) ), new MutableVector2D() );
        addForceArrow( wallForce );
        wallForce.setVerticalOffset( -30 );

        PBasicInputEventHandler mia = new PBasicInputEventHandler() {
                        public void mousePressed( PInputEvent e ) {
                module.record();
                setForce( e.getPositionRelativeTo( FreeBodyDiagram.this ) );
                userClicked = true;
            }

            public void mouseDragged( PInputEvent e ) {
                setForce( e.getPositionRelativeTo( FreeBodyDiagram.this ) );
            }

                        public void mouseReleased( PInputEvent e ) {
                model.setAppliedForce( 0.0 );
            }
        };
        ThresholdedPDragAdapter listener = new ThresholdedPDragAdapter( mia, 10, 0, 1000 );
        addInputEventListener( listener );
        addInputEventListener( new CursorHandler( Cursor.HAND_CURSOR ) );
        updateAll();
        module.getModel().addModelElement( new ModelElement() {
            public void stepInTime( double dt ) {
                updateAll();
            }
        } );

        PText title = new PText( TheRampStrings.getString( "display.free-body-diagram" ) );
        addChild( title );
    }

    private void setForce( Point2D pt ) {
        double x = pt.getX();

                double dx = x - getCenter().getX();
        double appliedForceRequest = dx / scale;
        model.setAppliedForce( appliedForceRequest );
    }

    private void updateXForces() {

        MutableVector2D af = new MutableVector2D( model.getAppliedForce().times( scale ) );
        appliedForce.setVector( af );

        MutableVector2D ff = new MutableVector2D( model.getFrictionForce().times( scale ) );
        frictionForce.setVector( ff );

        Vector2D net = new Vector2D( model.getTotalForce().times( scale ) );
        netForce.setVector( net );

        MutableVector2D wf = new MutableVector2D( model.getWallForce().times( scale ) );
        wallForce.setVector( wf );
    }

    private void updateMG() {
        MutableVector2D gravity = model.getGravityForce();
        mg.setVector( gravity.times( scale ) );
        normal.setVector( model.getNormalForce().times( scale ) );
    }

    public void addForceArrow( ForceArrow forceArrow ) {
        addChild( forceArrow );
    }

    private Point2D getCenter() {
        return RectangleUtils.getCenter( rect );
    }

    public void updateAll() {
        if ( owner.isVisible() ) {
            updateXForces();
            updateMG();
            axes.update();
        }
    }

    public void setBounds( int x, int y, int width, int height ) {
        rect.setBounds( x, y, width, height );
        updateAll();
    }

    public static class ForceArrow extends PNode {
        private PPath shapeGraphic;
        private HTMLNode textGraphic;
        private FreeBodyDiagram fbd;
           private String name;
        private Arrow lastArrow;
        private double verticalOffset = 0;

        public ForceArrow( FreeBodyDiagram fbd, Color color, String name, MutableVector2D v ) {
            this.fbd = fbd;
            this.name = name;
            shapeGraphic = new PPath();
            shapeGraphic.setStroke( new BasicStroke( 1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
            shapeGraphic.setPaint( color );          
            shapeGraphic.setStrokePaint( Color.black );
            addChild( shapeGraphic );
            Font font = new Font( AimxcelFont.getDefaultFontName(), Font.BOLD, 16 );
            textGraphic = new HTMLNode( name, color, font );
            addChild( textGraphic );
            setVector( v );
            setPickable( false );
            setChildrenPickable( false );
        }

        public void setVerticalOffset( double verticalOffset ) {
            this.verticalOffset = verticalOffset;
        }

        public void setVector( AbstractVector2D v ) {
            Point2D origin = fbd.getCenter();
            SurfaceGraphic surfaceGraphic = fbd.getRampPanel().getRampWorld().getBlockGraphic().getCurrentSurfaceGraphic();
            double viewAngle = surfaceGraphic.getViewAngle();
            origin = MutableVector2D.createPolar( verticalOffset, viewAngle ).getPerpendicularVector().getPerpendicularVector().getPerpendicularVector().getDestination( origin );
            Arrow arrow = new Arrow( origin, v.getDestination( origin ), 20, 20, 8, 0.5, true );
            Shape sh = arrow.getShape();
            if ( lastArrow == null || !lastArrow.equals( arrow ) ) {
                shapeGraphic.setPathTo( sh );
            }
            lastArrow = arrow;

            Rectangle b = sh.getBounds();
            Point ctr = RectangleUtils.getCenter( b );
            if ( v.getX() > 0 ) {
                this.textGraphic.setOffset( b.x + b.width + 5, ctr.y - textGraphic.getHeight() / 2 );
                textGraphic.setVisible( true );
            }
            else if ( v.getX() < 0 ) {
                this.textGraphic.setOffset( b.x - textGraphic.getWidth() - 5, ctr.y - textGraphic.getHeight() / 2 );
                textGraphic.setVisible( true );
            }
            else if ( v.getY() > 0 ) {
                this.textGraphic.setOffset( ctr.x - textGraphic.getWidth() / 2, b.y + b.height );
                textGraphic.setVisible( true );
            }
            else if ( v.getY() < 0 ) {
                this.textGraphic.setOffset( ctr.x - textGraphic.getWidth() / 2, b.y - textGraphic.getHeight() );
                textGraphic.setVisible( true );
            }
            else {
                textGraphic.setVisible( false );
            }
            if ( v.magnitude() <= 0.05 ) {
                setVisible( false );
            }
            else {
                setVisible( true );
            }
        }
    }

    private RampPanel getRampPanel() {
        return component;
    }

    public class AxesGraphic extends PNode {
        private PPath xAxis;
        private PPath yAxis;
        private PNode xLabel;
        private PNode yLabel;
        private Line2D.Double yLine = null;
        private Line2D.Double xLine = null;

        public AxesGraphic() {
            Stroke stroke = new BasicStroke( 1.0f );
            Color color = Color.black;
            xAxis = new PPath();
            xAxis.setStroke( stroke );
            xAxis.setStrokePaint( color );

            yAxis = new PPath();
            yAxis.setStroke( stroke );
            yAxis.setStrokePaint( color );

            addChild( xAxis );
            addChild( yAxis );



            update();
        }

        public void update() {

            Line2D.Double xLine = new Line2D.Double( rect.x, rect.y + rect.height / 2, rect.x + rect.width, rect.y + rect.height / 2 );
            Line2D.Double yLine = new Line2D.Double( rect.x + rect.width / 2, rect.y, rect.x + rect.width / 2, rect.y + rect.height );
            if ( this.xLine == null || !xLine.equals( this.xLine ) ) {
                this.xLine = xLine;
                xAxis.setPathTo( xLine );
            }
            if ( this.yLine == null || !yLine.equals( this.yLine ) ) {
                this.yLine = yLine;
                yAxis.setPathTo( yLine );
            }

        }
    }
}