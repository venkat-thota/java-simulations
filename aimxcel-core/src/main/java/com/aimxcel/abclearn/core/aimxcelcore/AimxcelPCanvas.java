

package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

import javax.swing.BorderFactory;

import com.aimxcel.abclearn.common.aimxcelcommon.patterns.Updatable;
import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.activities.PActivity;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDebug;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwingCanvas;


public class AimxcelPCanvas extends PSwingCanvas implements Updatable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger( AimxcelPCanvas.class.getCanonicalName() );

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private TransformStrategy worldTransformStrategy;
    private AimxcelRootPNode aimxcelRootNode;
    private AffineTransform transform;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Constructs a AimxcelPCanvas that uses an identify transform for world nodes.
     */
    public AimxcelPCanvas() {
        this( new ConstantTransformStrategy( new AffineTransform() ) );
    }

    /**
     * Constructs a AimxcelPCanvas with the size that will be used as the
     * reference coordinate frame size for world nodes.
     * When the canvas is resized, world nodes will appear to be scaled
     * to fit the new canvas size.
     *
     * @param renderingSize the reference coordinate frame size
     */
    public AimxcelPCanvas( Dimension2D renderingSize ) {
        this( new ConstantTransformStrategy( new AffineTransform() ) ); //HACK
        setWorldTransformStrategy( new RenderingSizeStrategy( this, renderingSize ) );
    }

    /**
     * Constructs a AimxcelPCanvas with a specified viewport for world nodes.
     *
     * @param modelViewport
     */
    public AimxcelPCanvas( Rectangle2D modelViewport ) {
        this( new ConstantTransformStrategy( new AffineTransform() ) ); //HACK
        setWorldTransformStrategy( new ViewportStrategy( this, modelViewport ) );
    }

    /**
     * Constructs a AimxcelPCanvas with a specified world transform strategy.
     *
     * @param worldTransformStrategy
     */
    public AimxcelPCanvas( AimxcelPCanvas.TransformStrategy worldTransformStrategy ) {

        // things look lousy while interacting & animating unless we set these
        setAnimatingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );
        setInteractingRenderQuality( PPaintContext.HIGH_QUALITY_RENDERING );

        this.worldTransformStrategy = worldTransformStrategy;

        this.aimxcelRootNode = new AimxcelRootPNode();
        getLayer().addChild( aimxcelRootNode );

        removeInputEventListener( getZoomEventHandler() );
        removeInputEventListener( getPanEventHandler() );

        addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                requestFocus();
            }
        } );

        /*
        * By default, a AimxcelPCanvas is opaque, that is, no components should be visible underneath this panel.
        * This allows for usage of performance improving facilities, such as immediate painting.
        */
        setOpaque( true );
        setBorder( BorderFactory.createLineBorder( Color.black ) );
        requestFocus();
    }

    /**
     * See #2015, ensure that scaling and layout are updated when bounds change.
     * This must happen synchronously; if you schedule it in a ComponentEvent,
     * you will see the scaling and layout.
     */
    @Override
    public void setBounds( int x, int y, int w, int h ) {
        if ( getBounds().getX() != x || getBounds().getY() != y || getBounds().getWidth() != w || getBounds().getHeight() != h ) {
            super.setBounds( x, y, w, h );
            updateWorldScale();
            updateLayout();
        }
    }

    /**
     * Updates the layout when the canvas is resized.
     * Default implementation does nothing.
     * Subclasses should override this method.
     */
    protected void updateLayout() {
    }

    //----------------------------------------------------------------------------
    // World transform management
    //----------------------------------------------------------------------------

    /**
     * Sets the transform strategy for world nodes.
     *
     * @param transformStrategy
     */
    public void setWorldTransformStrategy( TransformStrategy transformStrategy ) {
        this.worldTransformStrategy = transformStrategy;
        updateWorldScale();
    }

    /*
     * Updates the scale for world nodes.
     */
    protected void updateWorldScale() {
        aimxcelRootNode.setWorldTransform( worldTransformStrategy.getTransform() );
    }

    protected TransformStrategy getWorldTransformStrategy() {
        return worldTransformStrategy;
    }

    /**
     * Sets the scale for world nodes.
     *
     * @param scale
     */
    public void setWorldScale( double scale ) {
        aimxcelRootNode.setWorldScale( scale );
    }

    //----------------------------------------------------------------------------
    // Updatable implementation
    //----------------------------------------------------------------------------

    public void update() {
        update( aimxcelRootNode );
    }

    /*
     * PLEASE DOCUMENT ME.
     */
    private void update( PNode node ) {
        if ( node instanceof Updatable ) {
            Updatable updatable = (Updatable) node;
            updatable.update();
        }

        for ( int i = 0; i < node.getChildrenCount(); i++ ) {
            update( node.getChild( i ) );
        }
    }

    //----------------------------------------------------------------------------

    /**
     * Gets the AimxcelRootPNode associated with the PCanvas.
     *
     * @return AimxcelRootPNode
     */
    public AimxcelRootPNode getAimxcelRootNode() {
        return aimxcelRootNode;
    }

    /**
     * Sets the AimxcelRootPNode associated with the PCanvas.
     *
     * @param aimxcelRootNode
     */
    public void setAimxcelRootNode( AimxcelRootPNode aimxcelRootNode ) {
        if ( this.aimxcelRootNode != null ) {
            getLayer().removeChild( this.aimxcelRootNode );
        }
        this.aimxcelRootNode = aimxcelRootNode;
        getLayer().addChild( this.aimxcelRootNode );
    }

    //----------------------------------------------------------------------------
    // Convenience methods for adding and removing nodes
    //----------------------------------------------------------------------------

    public void addScreenChild( PNode node ) {
        aimxcelRootNode.addScreenChild( node );
    }

    public void addScreenChild( int index, PNode node ) {
        aimxcelRootNode.addScreenChild( index, node );
    }

    public void removeScreenChild( PNode node ) {
        aimxcelRootNode.removeChild( node );
    }

    public void addWorldChild( PNode node ) {
        aimxcelRootNode.addWorldChild( node );
    }

    public void addWorldChild( int index, PNode node ) {
        aimxcelRootNode.addWorldChild( index, node );
    }

    /**
     * This may become deprecated (just use aimxcelRootNode.removeChild)
     * <p/>
     * cmalley note: I don't think deprecating this is a good idea.
     * Nodes that are added via addWorldChild should be removed via
     * removeWorldChild. This method should be cleaned up, and it
     * should fail if we tried to remove a node that doesn't exist.
     *
     * @param node
     */
    public void removeWorldChild( PNode node ) {
        try {
            aimxcelRootNode.removeWorldChild( node );
        }
        catch ( ArrayIndexOutOfBoundsException e ) {
            // Hack because Core can't be modified
            // It doesn't expose world children so we can't
            // safely check for their presence
        }
    }

    /**
     * Adds a world child that represents a rectangular boundary.
     * This is intended primarily for debugging, to check how the transform strategy is affecting scenegraph rendering.
     * For example, if you're using CenteredStrategy, call this method with the stage size and you'll see how the stage looks after transformation.
     *
     * @param size
     */
    public void addBoundsNode( Dimension2D size ) {
        addWorldChild( new PPath( new Rectangle2D.Double( 0, 0, size.getWidth(), size.getHeight() ) ) {{
            setStroke( new BasicStroke( 3f ) );
            setStrokePaint( Color.RED );
            setPickable( false );
        }} );
    }

    //----------------------------------------------------------------------------
    // Convenience methods for setting Core debug flags
    //----------------------------------------------------------------------------

    public static void setDebugRegionManagement( boolean debugRegionManagement ) {
        PDebug.debugRegionManagement = debugRegionManagement;
    }

    public static void setDebugFrameRateToConsole( boolean frameRateToConsole ) {
        PDebug.debugPrintFrameRate = frameRateToConsole;
    }

    public static void setDebugFullBounds( boolean debugFullBounds ) {
        PDebug.debugFullBounds = debugFullBounds;
    }

    //----------------------------------------------------------------------------

    /**
     * Gets the size of the canvas in screen coordinates.
     *
     * @return Dimension2D
     */
    public Dimension2D getScreenSize() {
        return new PDimension( getWidth(), getHeight() );
    }

    /**
     * Gets the size of the canvas in world coordinates.
     *
     * @return Dimension2D
     */
    public Dimension2D getWorldSize() {
        Dimension2D dim = getScreenSize();
        getAimxcelRootNode().screenToWorld( dim ); // modifies dim!
        return dim;
    }

    /*
     * Gets the bounds of the canvas in world coordinates.
     * @return PBounds
     */
    public PBounds getWorldBounds() {
        Point2D origin = new Point2D.Double( 0, 0 );
        getAimxcelRootNode().screenToWorld( origin ); // modifies origin!
        Dimension2D worldSize = getWorldSize();
        return new PBounds( origin.getX(), origin.getY(), worldSize.getWidth(), worldSize.getHeight() );
    }

    /**
     * Gets the transform that was used for the most recent paintComponent call.
     * TODO: WHY WOULD WE NEED THIS?
     *
     * @return AffineTransform, null if paintComponent hasn't been called yet
     */
    public AffineTransform getTransform() {
        return transform;
    }

    /**
     * Remembers the AffineTransform that was used to paint the canvas.
     * TODO: BUT WHY?!?!
     *
     * @param g
     */
    public void paintComponent( Graphics g ) {
        transform = ( (Graphics2D) g ).getTransform();
        super.paintComponent( g );
    }

    /**
     * Adds an activity to the root node.
     */
    public void addActivity( PActivity activity ) {
        getRoot().addActivity( activity );
    }

    /**
     * Removes an activity from the root node.
     *
     * @param activity
     */
    public void removeActivity( PActivity activity ) {
        getRoot().getActivityScheduler().removeActivity( activity );
    }

    //----------------------------------------------------------------------------
    // Transform strategies
    //----------------------------------------------------------------------------

    /**
     * TransformStrategy is the interface implemented by all transform strategies.
     */
    public static interface TransformStrategy {
        AffineTransform getTransform();
    }

    /**
     * ConstantTransformStrategy implements a constant transform
     * that doesn't vary with the canvas size.
     */
    public static class ConstantTransformStrategy implements TransformStrategy {
        private AffineTransform affineTransform;

        public ConstantTransformStrategy( AffineTransform affineTransform ) {
            this.affineTransform = affineTransform;
        }

        public AffineTransform getTransform() {
            return new AffineTransform( affineTransform );
        }
    }

    /**
     * RenderingSizeStrategy implements a transform strategy that varies
     * with the canvas size.  As the canvas is resized, the transform is
     * varied based on a reference rendering size.
     * <p/>
     * NOTE: This should be implemented as an extension of ViewportStrategy,
     * with viewport (x,y)=(0,0). It's not implemented that way because
     * the current implementation of ViewportStrategy flips the y axis,
     * and this may break some sims.
     */
    public static class RenderingSizeStrategy implements TransformStrategy {
        private AimxcelPCanvas aimxcelPCanvas;
        private Dimension2D renderingSize;

        public RenderingSizeStrategy( AimxcelPCanvas aimxcelPCanvas, Dimension2D renderingSize ) {
            this.aimxcelPCanvas = aimxcelPCanvas;
            this.renderingSize = renderingSize;
            aimxcelPCanvas.addComponentListener( new ComponentAdapter() {
                public void componentShown( ComponentEvent e ) {
                    if ( RenderingSizeStrategy.this.renderingSize == null ) {
                        setRenderingSize();
                    }
                }
            } );
        }

        public void setAimxcelPCanvas( AimxcelPCanvas aimxcelPCanvas ) {
            this.aimxcelPCanvas = aimxcelPCanvas;
        }

        public AffineTransform getTransform() {
            if ( renderingSize == null && aimxcelPCanvas.isVisible() ) {
                setRenderingSize();
            }
            double sx = getScaleX();
            double sy = getScaleY();

            //use the smaller
            double scale = sx < sy ? sx : sy;
            scale = scale <= 0 ? 1.0 : scale;//if scale is negative or zero, just use scale=1

            AffineTransform transform = getPreprocessedTransform();

            transform.scale( scale, scale );

            return transform;
        }

        /**
         * This method returns the transform for this canvas, and is intended
         * to be overridden in subclasses that need to perform transforms other
         * than straight scaling.
         *
         * @return The current affine transform.
         */
        protected AffineTransform getPreprocessedTransform() {
            return new AffineTransform();
        }

        private void setRenderingSize() {
            setRenderingSize( aimxcelPCanvas.getSize() );
        }

        public void setRenderingSize( Dimension dim ) {
            this.renderingSize = new Dimension( dim );
        }

        public void setRenderingSize( int width, int height ) {
            setRenderingSize( new Dimension( width, height ) );
        }

        private double getScaleY() {
            return ( (double) aimxcelPCanvas.getHeight() ) / renderingSize.getHeight();
        }

        private double getScaleX() {
            return ( (double) aimxcelPCanvas.getWidth() ) / renderingSize.getWidth();
        }
    }

    /**
     * CenteringBoxStrategy implements a transform strategy that varies with
     * the canvas size and that keeps center of the world in the middle of the
     * canvas.  This is as opposed to other transform strategies that may keep
     * the relative distance from an edge of the canvas to items in the canvas
     * as a constant.
     * <p/>
     * NOTE: This should be implemented as an extension of ViewportStrategy,
     * with viewport (x,y)=(0,0). It's not implemented that way because
     * the current implementation of ViewportStrategy flips the y axis,
     * and this may break some sims.
     */
    public static class CenteringBoxStrategy implements TransformStrategy {
        private AimxcelPCanvas aimxcelPCanvas;
        private Dimension2D renderingSize;

        public CenteringBoxStrategy( AimxcelPCanvas aimxcelPCanvas, Dimension2D renderingSize ) {
            this.aimxcelPCanvas = aimxcelPCanvas;
            this.renderingSize = renderingSize;
            aimxcelPCanvas.addComponentListener( new ComponentAdapter() {
                public void componentShown( ComponentEvent e ) {
                    if ( CenteringBoxStrategy.this.renderingSize == null ) {
                        setRenderingSize();
                    }
                }
            } );
        }

        public void setAimxcelPCanvas( AimxcelPCanvas aimxcelPCanvas ) {
            this.aimxcelPCanvas = aimxcelPCanvas;
        }

        public AffineTransform getTransform() {
            AffineTransform transform;
            if ( renderingSize == null && aimxcelPCanvas.isVisible() ) {
                setRenderingSize();
            }
            if ( aimxcelPCanvas.getWidth() > 0 && aimxcelPCanvas.getHeight() > 0 ) {

                double sx = getScaleX();
                double sy = getScaleY();

                //use the smaller
                double scale = sx < sy ? sx : sy;
                scale = scale <= 0 ? 1.0 : scale;//if scale is negative or zero, just use scale=1

                Rectangle2D outputBox;

                if ( scale == sx ) {
                    outputBox = new Rectangle2D.Double( 0, ( aimxcelPCanvas.getHeight() - aimxcelPCanvas.getWidth() ) / 2,
                                                        aimxcelPCanvas.getWidth(), aimxcelPCanvas.getWidth() );
                }
                else {
                    outputBox = new Rectangle2D.Double( ( aimxcelPCanvas.getWidth() - aimxcelPCanvas.getHeight() ) / 2, 0,
                                                        aimxcelPCanvas.getHeight(), aimxcelPCanvas.getHeight() );
                }
                transform = new ModelViewTransform2D( new Rectangle2D.Double( 0, 0, renderingSize.getWidth(), renderingSize.getHeight() ),
                                                      outputBox, false ).getAffineTransform();
            }
            else {
                // Use a basic 1 to 1 transform in this case.
                transform = new AffineTransform();
            }

            return transform;
        }

        /**
         * This method returns the transform for this canvas, and is intended
         * to be overridden in subclasses that need to perform transforms other
         * than straight scaling.
         *
         * @return The current affine transform.
         */
        protected AffineTransform getPreprocessedTransform() {
            return new AffineTransform();
        }

        private void setRenderingSize() {
            setRenderingSize( aimxcelPCanvas.getSize() );
        }

        public void setRenderingSize( Dimension dim ) {
            this.renderingSize = new Dimension( dim );
        }

        public void setRenderingSize( int width, int height ) {
            setRenderingSize( new Dimension( width, height ) );
        }

        private double getScaleY() {
            return ( (double) aimxcelPCanvas.getHeight() ) / renderingSize.getHeight();
        }

        private double getScaleX() {
            return ( (double) aimxcelPCanvas.getWidth() ) / renderingSize.getWidth();
        }
    }

    /**
     * CenterWidthScaleHeight implements a transform strategy that varies with
     * the canvas size and that keeps center of the world in the middle of the
     * canvas, makes things get bigger when the world is enlarged height-wise,
     * and maintains the aspect ratio of items on the canvas.  This means that
     * if the user enlarges the window in the x direction only, they will see
     * more of the world and no sizes will change.  If they enlarge only the y
     * dimension, things will grow in both dimensions (in order to maintain
     * the aspect ratio).
     * <p/>
     * NOTE: This should be implemented as an extension of ViewportStrategy,
     * with viewport (x,y)=(0,0). It's not implemented that way because
     * the current implementation of ViewportStrategy flips the y axis,
     * and this may break some sims.
     */
    public static class CenterWidthScaleHeight implements TransformStrategy {
        private AimxcelPCanvas aimxcelPCanvas;
        private final Dimension2D renderingSize;

        public CenterWidthScaleHeight( AimxcelPCanvas aimxcelPCanvas, Dimension2D renderingSize ) {
            this.aimxcelPCanvas = aimxcelPCanvas;
            this.renderingSize = renderingSize;
        }

        public void setAimxcelPCanvas( AimxcelPCanvas aimxcelPCanvas ) {
            this.aimxcelPCanvas = aimxcelPCanvas;
        }

        public AffineTransform getTransform() {
            AffineTransform transform;
            if ( aimxcelPCanvas.getWidth() > 0 && aimxcelPCanvas.getHeight() > 0 ) {

                // Scale based only on growth/shrinkage in the y dimension,
                // i.e. height.
                double scale = getScaleY();

                // Translate in order to keep things centered in the x direction.
                double translationX = ( aimxcelPCanvas.getWidth() / 2 ) - ( ( renderingSize.getWidth() * scale ) / 2 );

                // Create the target rectangle.
                Rectangle2D outputBox;
                outputBox = new Rectangle2D.Double( translationX, 0, renderingSize.getWidth() * scale, renderingSize.getHeight() * scale );

                // Create the transform from the rendering size to the
                // (presumably new) canvas size.
                transform = new ModelViewTransform2D( new Rectangle2D.Double( 0, 0, renderingSize.getWidth(), renderingSize.getHeight() ),
                                                      outputBox, false ).getAffineTransform();
            }
            else {
                // Use a basic 1 to 1 transform in this case.
                transform = new AffineTransform();
            }

            return transform;
        }

        /**
         * This method returns the transform for this canvas, and is intended
         * to be overridden in subclasses that need to perform transforms other
         * than straight scaling.
         *
         * @return The current affine transform.
         */
        protected AffineTransform getPreprocessedTransform() {
            return new AffineTransform();
        }

        private double getScaleY() {
            return ( (double) aimxcelPCanvas.getHeight() ) / renderingSize.getHeight();
        }

        private double getScaleX() {
            return ( (double) aimxcelPCanvas.getWidth() ) / renderingSize.getWidth();
        }
    }

    /**
     * ViewportStrategy implements a transform strategy that varies
     * with the canvas size.  As the canvas is resized, the transform is
     * varied based on a reference viewport.
     * <p/>
     * Note that this implementation flips the y axis.
     */
    public static class ViewportStrategy implements TransformStrategy {
        private Rectangle2D modelViewport;
        private AimxcelPCanvas aimxcelPCanvas;

        public ViewportStrategy( AimxcelPCanvas aimxcelPCanvas, Rectangle2D modelViewport ) {
            this.aimxcelPCanvas = aimxcelPCanvas;
            this.modelViewport = modelViewport;
        }

        protected double getScaleY() {
            return aimxcelPCanvas.getHeight() / modelViewport.getHeight();
        }

        protected double getScaleX() {
            return aimxcelPCanvas.getWidth() / modelViewport.getWidth();
        }

        public void componentShown( ComponentEvent e ) {
        }

        public AffineTransform getTransform() {
            double sx = getScaleX();
            double sy = getScaleY();

            //use the smaller
            double scale = sx < sy ? sx : sy;
            if ( scale == 0 ) {
                // Ignore this case. The canvas size (and therefore the scale) will be zero on startup, before the canvas is realized.
                return new AffineTransform();
            }
            else if ( scale < 0 ) {
                LOGGER.warning( "ignoring negative canvas size" );
                return new AffineTransform();
            }
            else {
                AffineTransform worldTransform = new AffineTransform();
                worldTransform.translate( 0, aimxcelPCanvas.getHeight() );
                worldTransform.scale( scale, -scale );
                worldTransform.translate( modelViewport.getX(), -modelViewport.getY() );
                return worldTransform;
            }
        }
    }

    /**
     * Centers a "stage" area, of a specified size, within the AimxcelPCanvas, scaling it up and down so it is always
     * entirely visible, and always at an aspect ratio of 1.
     *
     * @author Sam Reid
     * @author John Blanco
     */
    public static class CenteredStage implements TransformStrategy {

        // The default stage size is for a sim with menu bar, tabs, and no
        // control panel.  It was determined by running a sim that was set up
        // in this way and measuring (programmatically) the resulting canvas
        // dimensions.
        public static final Dimension2D DEFAULT_STAGE_SIZE = new PDimension( 1008, 679 );

        private AimxcelPCanvas canvas;
        private Dimension2D stageSize;

        public CenteredStage( AimxcelPCanvas canvas ) {
            // Create this strategy using the default stage size.
            this( canvas, DEFAULT_STAGE_SIZE );

        }

        public CenteredStage( AimxcelPCanvas canvas, Dimension2D stageSize ) {
            this.canvas = canvas;
            this.stageSize = stageSize;
        }

        public AffineTransform getTransform() {
            double sx = ( (double) canvas.getWidth() ) / stageSize.getWidth();
            double sy = ( (double) canvas.getHeight() ) / stageSize.getHeight();

            //use the smaller and maintain aspect ratio so that circles don't become ellipses
            double scale = sx < sy ? sx : sy;
            scale = scale <= 0 ? 1.0 : scale;//if scale is negative or zero, just use scale=1

            AffineTransform transform = new AffineTransform();
            double scaledStageWidth = scale * stageSize.getWidth();
            double scaledStageHeight = scale * stageSize.getHeight();
            //center it in width and height
            transform.translate( canvas.getWidth() / 2 - scaledStageWidth / 2, canvas.getHeight() / 2 - scaledStageHeight / 2 );
            transform.scale( scale, scale );

            return transform;
        }
    }


}