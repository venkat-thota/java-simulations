
package com.aimxcel.abclearn.glaciers.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.model.Glacier;
import com.aimxcel.abclearn.glaciers.model.Glacier.GlacierAdapter;
import com.aimxcel.abclearn.glaciers.model.Glacier.GlacierListener;

import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;


public class EquilibriumLineNode extends AimxcelPNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------
    
    private static final Color STROKE_COLOR = Color.RED;
    private static final Stroke STROKE = 
        new BasicStroke( 2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {3,3}, 0 );
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    private final Glacier _glacier;
    private final GlacierListener _glacierListener;
    private final GlaciersModelViewTransform _mvt;
    private final Point2D _pModel, _pView; // reusable points
    private final PPath _pathNode;
    private final GeneralPath _path;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    public EquilibriumLineNode( Glacier glacier, GlaciersModelViewTransform mvt ) {
        super();
        
        setPickable( false );
        setChildrenPickable( false );
        
        _glacier = glacier;
        _glacierListener = new GlacierAdapter() {
            public void iceThicknessChanged() {
                update();
            }
        };
        _glacier.addGlacierListener( _glacierListener );
        
        _mvt = mvt;
        _pModel = new Point2D.Double();
        _pView = new Point2D.Double();
        
        _path = new GeneralPath();
        _pathNode = new PPath();
        _pathNode.setStroke( STROKE );
        _pathNode.setStrokePaint( STROKE_COLOR );
        addChild( _pathNode );
        
        // initialize
        update();
    }
    
    public void cleanup() {
        _glacier.removeGlacierListener( _glacierListener );
    }
    
    //----------------------------------------------------------------------------
    // Updaters
    //----------------------------------------------------------------------------
    
    private void update() {
        
        _path.reset();
        
        // start drawing at the left edge of the birds-eye view bounds
        double ela = _glacier.getClimate().getELA();
        _pModel.setLocation( GlaciersPlayArea.getBirdsEyeViewportOffset().getX(), ela );
        _mvt.modelToView( _pModel, _pView );
        _path.moveTo( (float)_pView.getX(), (float)_pView.getY() );
        
        if ( ela > _glacier.getHeadwallY() ) {
            // if the ELA is above the top of the headwall, then stop drawing at the headwall
            _pModel.setLocation( _glacier.getHeadwallX(), ela );
            _mvt.modelToView( _pModel, _pView );
            _path.lineTo( (float) _pView.getX(), (float) _pView.getY() );
        }
        else {
            Point2D surfaceAtELA = _glacier.getSurfaceAtELAReference();
            if ( surfaceAtELA != null ) {
                // terminus is at or below the ELA
                
                // draw a line to the ice-air interface at the ELA
                _pModel.setLocation( surfaceAtELA.getX(), ela );
                _mvt.modelToView( _pModel, _pView );
                _path.lineTo( (float) _pView.getX(), (float) _pView.getY() );
                
                // draw a vertical line across the surface of the ice
                _pModel.setLocation( surfaceAtELA.getX() + GlaciersConstants.YAW_X_OFFSET, ela + GlaciersConstants.PITCH_Y_OFFSET );
                _mvt.modelToView( _pModel, _pView );
                _path.lineTo( (float) _pView.getX(), (float) _pView.getY() );
            }
            else {
                // terminus is above the ELA
                
                // draw a line to where the ELA meets the valley floor
                double x = _glacier.getValley().getX( ela );
                _pModel.setLocation( x, ela );
                _mvt.modelToView( _pModel, _pView );
                _path.lineTo( (float) _pView.getX(), (float) _pView.getY() );
                
                // draw a vertical line across the valley floor
                _pModel.setLocation( x + GlaciersConstants.YAW_X_OFFSET, ela + GlaciersConstants.PITCH_Y_OFFSET );
                _mvt.modelToView( _pModel, _pView );
                _path.lineTo( (float) _pView.getX(), (float) _pView.getY() );
            }
        }
        
        _pathNode.setPathTo( _path );
    }
    
    //----------------------------------------------------------------------------
    // Icon
    //----------------------------------------------------------------------------
    
    /**
     * Creates an icon to represent this node.
     * Used in control panels.
     * @return Icon
     */
    public static Icon createIcon() {
        final double width = 30;
        PPath pathNode = new PPath( new Line2D.Double( 0, 0, width, 0 ) );
        pathNode.setStroke( STROKE );
        pathNode.setStrokePaint( STROKE_COLOR );
        Image image = pathNode.toImage();
        return new ImageIcon( image );
    }
}
