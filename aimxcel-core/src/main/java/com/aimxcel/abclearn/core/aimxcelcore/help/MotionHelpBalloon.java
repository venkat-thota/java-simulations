 
package com.aimxcel.abclearn.core.aimxcelcore.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.MutableVector2D;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.activities.PTransformActivity;
import edu.umd.cs.piccolo.util.PAffineTransform;


public class MotionHelpBalloon extends HelpBalloon {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_DURATION = 3000;

    private PCanvas canvas;
    private boolean started;

    /**
     * Constructor.
     * By default, there is no arrow, no ballon behind the text,
     * and the location is relative to the upper-left corner.
     *
     * @param canvas
     * @param s
     */
    public MotionHelpBalloon( PCanvas canvas, String s ) {
        super( canvas, s, HelpBalloon.TOP_LEFT, 100, 0 );
        setBalloonVisible( false );
        setArrowVisible( false );
        setEnabled( true );
        this.canvas = canvas;
        this.started = false;
    }

    /**
     * Animates to a point using the default duration.
     * Animation begins immediately.
     * The animation path is a straight line.
     *
     * @param x
     * @param y
     * @return the PActivity that was scheduled, or null if none was scheduled
     * @throws IllegalStateException if this node is not yet in the Piccolo tree
     */
    public PActivity animateTo( final double x, final double y ) {
        return animateTo( x, y, DEFAULT_DURATION );
    }

    /**
     * Animates to a point using a specified duration.
     * Animation begins immediately.
     * The animation path is a straight line.
     *
     * @param x
     * @param y
     * @param duration duration in milliseconds
     * @return the PActivity that was scheduled, or null if none was scheduled
     * @throws IllegalStateException if this node is not yet in the Piccolo tree
     */
    public PActivity animateTo( final double x, final double y, final long duration ) {
        if ( !started ) {
            if ( getRoot() == null ) {
                throw new IllegalStateException( "node has no root" );
            }
            PActivity activity = animateToPositionScaleRotation( x, y, 1 /*scale*/, 0 /*theta*/, duration );
            return activity;
        }
        else {
            return null;
        }
    }

    /**
     * Animates to a node using the default duration.
     * Animation begins immediately.
     * The animation path is a straight line, but will be adjusted
     * if the destination node is moving.
     *
     * @param node
     * @return the PActivity that was scheduled, or null if none was scheduled
     * @throws IllegalStateException if this node is not yet in the Piccolo tree
     */
    public PActivity animateTo( final PNode node ) {
        return animateTo( node, DEFAULT_DURATION );
    }

    /**
     * Animates to a node using a specified duration.
     * Animation begins immediately.
     * The animation path is a straight line, but will be adjusted
     * if the destination node is moving.
     *
     * @param node
     * @param duration duration in milliseconds
     * @return the PActivity that was scheduled, or null if none was scheduled
     * @throws IllegalStateException if this node is not yet in the Piccolo tree
     */
    public PActivity animateTo( final PNode node, long duration ) {
        if ( !started ) {
            if ( getRoot() == null ) {
                throw new IllegalStateException( "node has no root" );
            }
            Point2D loc = mapLocation( node, canvas );
            final PTransformActivity activity = animateToPositionScaleRotation( loc.getX(), loc.getY(), 1 /*scale*/, 0 /*theta*/, duration );
            node.addPropertyChangeListener( PNode.PROPERTY_FULL_BOUNDS, new PropertyChangeListener() {
                public void propertyChange( PropertyChangeEvent evt ) {
                    PAffineTransform t = getTransform();
                    Point2D loc = mapLocation( node, canvas );
                    t.setOffset( loc.getX(), loc.getY() );
                    t.setScale( 1.0 );
                    t.setRotation( 0.0 );
                    double[] m = new double[6];
                    t.getMatrix( m );
                    activity.setDestinationTransform( m );
                }
            } );
            return activity;
        }
        else {
            return null;
        }
    }

    /**
     * Animates this help item towards the specified JComponent at the specified speed.  The animation ceases
     * when the help item is close to the target JComponent.
     *
     * @param component the JComponent to move towards.
     * @param speed     the speed at which to move
     */
    public void animateTo( JComponent component, final double speed ) {
        IFollower follower = getFollower();
        if ( follower != null ) {
            follower.setFollowEnabled( false );
        }
        follower = new JComponentFollower( this, component ) {
            private Timer timer;

            /* Turns following on and off. */
            public void setFollowEnabled( boolean enabled ) {
                super.setFollowEnabled( enabled );
                if ( enabled ) {
                    if ( timer != null ) {
                        timer.stop();
                    }
                    timer = new Timer( 30, new ActionListener() {
                        public void actionPerformed( ActionEvent e ) {
                            updatePosition();
                        }
                    } );
                    timer.start();
                }
            }

            /* Synchronizes position. */
            public void updatePosition() {
                AbstractHelpItem _helpItem = super.getHelpItem();
                if ( _helpItem.getVisible() ) {
                    Point2D target = _helpItem.mapLocation( super.getComponent() );
                    Point2D source = _helpItem.getOffset();
                    MutableVector2D vec = new MutableVector2D( source, target );
                    if ( source.distance( target ) > speed ) {
                        Point2D newLoc = vec.getInstanceOfMagnitude( speed ).getDestination( source );
                        _helpItem.setOffset( newLoc );
                    }
                    else {
                        if ( timer != null ) {
                            timer.stop();
                        }
                    }
                }
            }
        };
        follower.setFollowEnabled( isEnabled() );
        super.setFollower( follower );

    }

    /**
     * Override setVisible to also control pickability.
     * If this node is visible, then it (and its children) are pickable.
     * The rationale for doing this is:
     * Normally if something is higher in the Z-Ordering and obscuring something
     * below then mouse events should not pass through.  This is certainly true
     * for opaque things, but also a true for semi-transparent nodes.
     * Or you might want to add a MouseListener to this node
     * that makes it disappear when the user clicks on it.
     */
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        setPickable( visible );
        setChildrenPickable( visible );
    }
}