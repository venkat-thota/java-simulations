

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;


public class RectangularBackgroundNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final PNode _foregroundNode;
    private final Insets _insets;
    private final Rectangle2D _rectangle;
    private final PPath _pathNode;

    /**
     * Constructs a background with no stroke.
     *
     * @param foregroundNode
     * @param insets
     * @param fillPaint
     */
    public RectangularBackgroundNode( PNode foregroundNode, Insets insets, Paint fillPaint ) {
        this( foregroundNode, insets, fillPaint, null, null );
    }

    /**
     * Fully-qualified constructor.
     *
     * @param foregroundNode
     * @param insets
     * @param fillPaint
     * @param strokePaint
     * @param stroke
     */
    public RectangularBackgroundNode( PNode foregroundNode, Insets insets, Paint fillPaint, Paint strokePaint, Stroke stroke ) {
        super();

        _foregroundNode = foregroundNode;
        _insets = new Insets( insets.top, insets.left, insets.bottom, insets.right );

        // when the foreground node's full bounds change, update the background
        _foregroundNode.addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent event ) {
                if ( event.getPropertyName().equals( PNode.PROPERTY_FULL_BOUNDS ) ) {
                    update();
                }
            }
        } );

        _rectangle = new Rectangle2D.Double();
        _pathNode = new PPath();
        _pathNode.setPaint( fillPaint );
        _pathNode.setStrokePaint( strokePaint );
        _pathNode.setStroke( stroke );

        addChild( _pathNode );
        addChild( _foregroundNode );

        update();
    }


    public PNode getForegroundNode() {
        return _foregroundNode;
    }

    /*
    * Adjusts the background to fit the foreground node.
    */
    private void update() {
        PBounds b = _foregroundNode.getFullBoundsReference();
        double x = b.getMinX() - _insets.left;
        double y = b.getMinY() - _insets.top;
        double w = b.getWidth() + _insets.left + _insets.right;
        double h = b.getHeight() + _insets.top + _insets.bottom;
        _rectangle.setRect( x, y, w, h );
        _pathNode.setPathTo( _rectangle );
    }

    /**
     * Example.
     */
    public static void main( String[] args ) {

        PPath pathNode = new PPath( new Ellipse2D.Double( 0, 0, 100, 100 ) );
        pathNode.setPaint( Color.GREEN );

        Insets insets = new Insets( 10, 10, 10, 10 );
        Color backgroundColor = new Color( 255, 0, 0, 100 ); // translucent red
        RectangularBackgroundNode backgroundNode = new RectangularBackgroundNode( pathNode, insets, backgroundColor );
        backgroundNode.setOffset( 100, 100 );

        PCanvas canvas = new PCanvas();
        canvas.getLayer().addChild( backgroundNode );

        JFrame frame = new JFrame();
        frame.getContentPane().add( canvas );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( new Dimension( 400, 400 ) );
        frame.setVisible( true );
    }

}
