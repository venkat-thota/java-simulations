
package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.geom.Point2D;

import javax.swing.plaf.basic.BasicHTML;

import com.aimxcel.abclearn.glaciers.model.AbstractTool;
import com.aimxcel.abclearn.glaciers.model.IToolProducer;
import com.aimxcel.abclearn.glaciers.view.GlaciersModelViewTransform;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PDragEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
public abstract class AbstractToolIconNode extends PNode {
    
    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int VERTICAL_SPACING = 2; // vertical space between a tool's icon and label
    private static final Font LABEL_FONT = new AimxcelFont( 12 );
    private static final Color LABEL_COLOR = Color.BLACK;
    private static final Point2D DEFAULT_DRAG_OFFSET = new Point2D.Double( 0, 0 );
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    /**
     * Constructor.
     * 
     * @param image image displayed on the icon
     */
    public AbstractToolIconNode( Image image ) {
        this( image, null /* text */ );
    }
    
    /**
     * Constructor.
     * 
     * @param image image displayed on the icon
     * @param text optional HTML or plain text, centered under image
     */
    public AbstractToolIconNode( Image image, String text ) {
        super();
        
        PImage imageNode = new PImage( image );
        imageNode.setOffset( 0, 0 );
        addChild( imageNode );
        
        if ( text != null ) {

            PNode labelNode = null;
            
            if ( BasicHTML.isHTMLString( text ) ) {
                HTMLNode htmlNode = new HTMLNode( text );
                htmlNode.setFont( LABEL_FONT );
                htmlNode.setHTMLColor( LABEL_COLOR );
                labelNode = htmlNode;
            }
            else {
                PText ptextNode = new PText( text );
                ptextNode.setFont( LABEL_FONT );
                ptextNode.setTextPaint( LABEL_COLOR );
                labelNode = ptextNode;
            }
            addChild( labelNode );

            if ( imageNode.getWidth() > labelNode.getWidth() ) {
                imageNode.setOffset( 0, 0 );
                labelNode.setOffset( imageNode.getX() + ( imageNode.getWidth() - labelNode.getWidth() ) / 2, imageNode.getY() + imageNode.getHeight() + VERTICAL_SPACING );
            }
            else {
                labelNode.setOffset( 0, imageNode.getY() + imageNode.getHeight() + VERTICAL_SPACING );
                imageNode.setOffset( labelNode.getX() + ( labelNode.getWidth() - imageNode.getWidth() ) / 2, 0 );
            }
        }
    }
    
    //----------------------------------------------------------------------------
    // Subclass that adds interactivity
    //----------------------------------------------------------------------------
    
    /**
     * InteractiveToolIconNode adds interactivity to ToolIconNode.
     * When an interactive tool icon receives a mouse press, it asks a specified tool producer
     * to create a tool model element. As long as the mouse remains pressed, drag events 
     * are used to change the new tool's position.
     */
    protected static abstract class InteractiveToolIconNode extends AbstractToolIconNode {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final IToolProducer _toolProducer;
        private final GlaciersModelViewTransform _mvt;
        private final Point2D _pModel, _pView; // reusable point for model-view transforms

        /**
         * Constructor.
         * 
         * @param image image displayed on the icon
         * @param toolProducer object capable of creating tools
         * @param mvt model-view transform, used to convert mouse position to tool position
         */
        public InteractiveToolIconNode( Image image, IToolProducer toolProducer, GlaciersModelViewTransform mvt ) { 
            this( image, null /* text */, toolProducer, mvt );
        }
        
        /**
         * Constructor.
         * 
         * @param image image displayed on the icon
         * @param text optional HTML or plain text, centered under image
         * @param toolProducer object capable of creating tools
         * @param mvt model-view transform, used to convert mouse position to tool position
         */
        public InteractiveToolIconNode( Image image, String text, IToolProducer toolProducer, GlaciersModelViewTransform mvt ) {
            super( image, text );

            _toolProducer = toolProducer;
            _mvt = mvt;
            _pModel = new Point2D.Double();
            _pView = new Point2D.Double();

            addInputEventListener( new CursorHandler() );
            addInputEventListener( new PDragEventHandler() {

                private AbstractTool _tool = null; // tool model element created when drag starts

                /* When the drag starts, create the new tool. */
                protected void startDrag( PInputEvent event ) {
                    Point2D offset = getDragOffsetReference();
                    _pView.setLocation( event.getPosition().getX() + offset.getX(), event.getPosition().getY() + offset.getY() );
                    _mvt.viewToModel( _pView, _pModel );
                    _tool = createTool( _pModel );
                    _tool.setDragging( true );
                    super.startDrag( event );
                }

                /* During the drag, set the position of the new tool. */
                protected void drag( PInputEvent event ) {
                    Point2D offset = getDragOffsetReference();
                    _pView.setLocation( event.getPosition().getX() + offset.getX(), event.getPosition().getY() + offset.getY() );
                    _mvt.viewToModel( _pView, _pModel );
                    _tool.setPosition( _pModel );
                    // do not call super.drag, or the icon in the toolbox will move!
                }

                /* When the drag ends, release control of the tool and activate it. */
                protected void endDrag( PInputEvent event ) {
                    super.endDrag( event );
                    _tool.setDragging( false );
                    _tool = null;
                }
            } );
        }

        /*
         * Provides access to tool producer for subclasses.
         * The tool producer is responsible for creating the tool model element.
         */
        protected IToolProducer getToolProducer() {
            return _toolProducer;
        }

        /*
         * Creates the appropriate tool at the specified position.
         * 
         * @param position position in model coordinates
         */
        protected abstract AbstractTool createTool( Point2D position );
        
        /*
         * This hook is provided to allow subclasses to specify a drag offset.
         * This is useful when the part of the icon that the user should be "holding"
         * is not at the tool's origin.  For example, for the borehole drill, the
         * origin is at the tip of the bit, but we want the user to drag the drill
         * out of the toolbox by its handle.
         * <p>
         * The default behavior is to drag the tool from its origin.
         */
        protected Point2D getDragOffsetReference() {
            return DEFAULT_DRAG_OFFSET;
        }
    }
}
