
package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.aimxcel.abclearn.glaciers.GlaciersImages;
import com.aimxcel.abclearn.glaciers.GlaciersStrings;
import com.aimxcel.abclearn.glaciers.model.GPSReceiver;
import com.aimxcel.abclearn.glaciers.model.Movable.MovableAdapter;
import com.aimxcel.abclearn.glaciers.model.Movable.MovableListener;
import com.aimxcel.abclearn.glaciers.util.UnitsConverter;
import com.aimxcel.abclearn.glaciers.view.GlaciersModelViewTransform;
import com.aimxcel.abclearn.glaciers.view.tools.AbstractToolOriginNode.LeftToolOriginNode;

import com.aimxcel.abclearn.common.aimxcelcommon.util.DefaultDecimalFormat;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ArrowNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class GPSReceiverNode extends AbstractToolNode {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final NumberFormat DISTANCE_FORMAT = new DefaultDecimalFormat( "0" );
    private static final NumberFormat ELEVATION_FORMAT = new DefaultDecimalFormat( "0" );
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private final GPSReceiver _gps;
    private final MovableListener _movableListener;
    private final ValueNode _valueNode;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    public GPSReceiverNode( GPSReceiver gps, GlaciersModelViewTransform mvt, TrashCanDelegate trashCan, boolean englishUnits ) {
        super( gps, mvt, trashCan );
        
        _gps = gps;
        _movableListener = new MovableAdapter() {
            public void positionChanged() {
                update();
            }
        };
        _gps.addMovableListener( _movableListener );
        
        // arrow that points to the left
        PNode arrowNode = new LeftToolOriginNode();
        addChild( arrowNode );
        arrowNode.setOffset( 0, 0 ); // this node identifies the origin
        
        // GPS receiver image
        PNode receiverNode = new ReceiverNode();
        addChild( receiverNode );
        receiverNode.setOffset( arrowNode.getFullBounds().getMaxX() + 2, -22 );
        
        // display to the right of arrow, vertically centered
        _valueNode = new ValueNode( getValueFont(), getValueBorder(), englishUnits );
        addChild( _valueNode );
        _valueNode.setOffset( receiverNode.getFullBounds().getMaxX() + 2, -arrowNode.getFullBounds().getHeight() / 2 );
        
        // initial state
        update();
    }
    
    public void cleanup() {
        _gps.removeMovableListener( _movableListener );
        super.cleanup();
    }
    
    public void setEnglishUnits( boolean englishUnits ) {
        _valueNode.setEnglishUnits( englishUnits );
        update();
    }
    
    //----------------------------------------------------------------------------
    // Inner classes
    //----------------------------------------------------------------------------
    
    /*
     * Image of the GPS receiver.
     */
    private static class ReceiverNode extends PComposite {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ReceiverNode() {
            super();
            PImage imageNode = new PImage( GlaciersImages.GPS_RECEIVER );
            addChild( imageNode );
        }
    }
    
    /*
     * Displays the position coordinates.
     */
    private static class ValueNode extends PComposite {
        
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel _distanceLabel;
        private JLabel _elevationLabel;
        private PSwing _pswing;
        private boolean _englishUnits;
        private String _units;
        
        public ValueNode( Font font, Border border, boolean englishUnits ) {
            super();
            
            _englishUnits = englishUnits;
            _units = ( englishUnits ? GlaciersStrings.UNITS_FEET : GlaciersStrings.UNITS_METERS );
            
            ArrowNode xArrowNode = new ArrowNode( new Point2D.Double( 0, 0 ), new Point2D.Double( 10, 0 ), 5, 8, 2 );
            xArrowNode.setStroke( null );
            xArrowNode.setPaint( Color.BLACK );
            JLabel xArrowLabel = new JLabel( new ImageIcon( xArrowNode.toImage() ) );
            
            ArrowNode yArrowNode = new ArrowNode( new Point2D.Double( 0, 0 ), new Point2D.Double( 0, -10 ), 5, 8, 2 );
            yArrowNode.setStroke( null );
            yArrowNode.setPaint( Color.BLACK );
            JLabel yArrowLabel = new JLabel( new ImageIcon( yArrowNode.toImage() ) );
            
            _distanceLabel = new JLabel( "?" );
            _distanceLabel.setFont( font );
            
            _elevationLabel = new JLabel( "?" );
            _elevationLabel.setFont( font );
            
            JPanel panel = new JPanel();
            panel.setBackground( Color.WHITE );
            panel.setBorder( border );
            EasyGridBagLayout layout = new EasyGridBagLayout( panel );
            layout.setAnchor( GridBagConstraints.EAST );
            panel.setLayout( layout );
            layout.addComponent( xArrowLabel, 0, 0 );
            layout.addComponent( _distanceLabel, 0, 1 );
            layout.addComponent( yArrowLabel, 1, 0 );
            layout.addComponent( _elevationLabel, 1, 1 );
            
            _pswing = new PSwing( panel );
            addChild( _pswing );
        }
        
        public void setCoordinates( Point2D position ) {
            double x = position.getX();
            double elevation = position.getY();
            if ( _englishUnits ) {
                x = UnitsConverter.metersToFeet( x );
                elevation = UnitsConverter.metersToFeet( elevation );
            }
            _distanceLabel.setText( GlaciersStrings.LABEL_DISTANCE + ": " +  DISTANCE_FORMAT.format( x ) + " " + _units );
            _elevationLabel.setText( GlaciersStrings.LABEL_ELEVATION + ": " +  ELEVATION_FORMAT.format( elevation ) + " " + _units );
        }
        
        public void setEnglishUnits( boolean englishUnits ) {
            _englishUnits = englishUnits;
            _units = ( englishUnits ? GlaciersStrings.UNITS_FEET : GlaciersStrings.UNITS_METERS );
        }
    }
    
    //----------------------------------------------------------------------------
    // Updaters
    //----------------------------------------------------------------------------
    
    /*
     * Updates the displayed coordinates to match the model.
     */
    private void update() {
        _valueNode.setCoordinates( _gps.getPositionReference() );
    }
    
    //----------------------------------------------------------------------------
    // Utilities
    //----------------------------------------------------------------------------
    
    /**
     * Creates a sample image of this node type, for use as an icon.
     */
    public static Image createImage() {
        return new ReceiverNode().toImage();
    }
}
