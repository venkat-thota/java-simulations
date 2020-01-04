package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.model.Bucket;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.ColorUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PText;


public class BucketView {

    // ------------------------------------------------------------------------
    // Class Data
    // ------------------------------------------------------------------------

    public static final Font DEFAULT_LABEL_FONT = new AimxcelFont( 18, true );

    // ------------------------------------------------------------------------
    // Instance Data
    // ------------------------------------------------------------------------

    // This node maintains two layers and makes those layers available via its
    // API.  This is done so that its parts can be added to different layers,
    // thus making more easy to make things look like they are in the bucket.
    private final PNode holeLayer = new PNode();
    private final PNode containerLayer = new PNode();

    // keep track of the container shape so we can position labels correctly
    // that are added later
    private Shape scaledContainerShape;

    // ------------------------------------------------------------------------
    // Constructor(s)
    // ------------------------------------------------------------------------

    public BucketView( Bucket bucket, ModelViewTransform mvt ) {
        this( bucket, mvt, Color.WHITE, DEFAULT_LABEL_FONT );
    }

    public BucketView( Bucket bucket, ModelViewTransform mvt, Color captionColor, Font labelFont ) {
        // Create a scaling transform based on the provided MVT, since we only
        // want the scaling portion and we want to avoid any translation.
        AffineTransform scaleTransform = AffineTransform.getScaleInstance( mvt.getTransform().getScaleX(),
                                                                           mvt.getTransform().getScaleY() );

        // Create the scaled shapes.
        Shape scaledHoleShape = scaleTransform.createTransformedShape( bucket.getHoleShape() );
        scaledContainerShape = scaleTransform.createTransformedShape( bucket.getContainerShape() );

        // Create and add the container node.
        Paint containerPaint = new GradientPaint(
                new Point2D.Double( scaledHoleShape.getBounds2D().getMinX(), scaledHoleShape.getBounds2D().getCenterY() ),
                ColorUtils.brighterColor( bucket.getBaseColor(), 0.5 ),
                new Point2D.Double( scaledHoleShape.getBounds2D().getMaxX(), scaledHoleShape.getBounds2D().getCenterY() ),
                ColorUtils.darkerColor( bucket.getBaseColor(), 0.5 ) );
        AimxcelPPath containerNode = new AimxcelPPath( scaledContainerShape, containerPaint );
        containerLayer.addChild( containerNode );

        // Create and add the hole node.
        Paint holePaint = new GradientPaint(
                new Point2D.Double( scaledHoleShape.getBounds2D().getMinX(), scaledHoleShape.getBounds2D().getCenterY() ),
                Color.BLACK,
                new Point2D.Double( scaledHoleShape.getBounds2D().getMaxX(), scaledHoleShape.getBounds2D().getCenterY() ),
                Color.LIGHT_GRAY );
        AimxcelPPath holeNode = new AimxcelPPath( scaledHoleShape, holePaint, new BasicStroke( 1f ), Color.GRAY );
        holeLayer.addChild( holeNode );

        // Create and add the caption (if provided).
        if ( bucket.getCaptionText() != null ) {
            PText caption = new PText( bucket.getCaptionText() );
            caption.setFont( labelFont );
            caption.setTextPaint( captionColor );
            addLabelToContainer( caption );
        }

        setOffset( mvt.modelToView( bucket.getPosition() ) );
    }

    // ------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------

    public void addLabelToContainer( PNode label ) {
        if ( label.getFullBoundsReference().getWidth() > scaledContainerShape.getBounds().getWidth() * 0.8 ) {
            // The caption must be scaled in order to fit on the container.
            label.scale( scaledContainerShape.getBounds().getWidth() * 0.8 / label.getFullBoundsReference().getWidth() );
        }
        label.setOffset(
                scaledContainerShape.getBounds2D().getCenterX() - label.getFullBoundsReference().getWidth() / 2,
                scaledContainerShape.getBounds2D().getCenterY() - label.getFullBoundsReference().getHeight() / 2 );
        containerLayer.addChild( label );
    }

    public PNode getHoleNode() {
        return holeLayer;
    }

    public PNode getFrontNode() {
        return containerLayer;
    }

    private void setOffset( double x, double y ) {
        holeLayer.setOffset( x, y );
        containerLayer.setOffset( x, y );
    }

    private void setOffset( Point2D point ) {
        setOffset( point.getX(), point.getY() );
    }
}
