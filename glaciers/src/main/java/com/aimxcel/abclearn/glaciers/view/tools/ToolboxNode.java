
package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.aimxcel.abclearn.glaciers.GlaciersConstants;
import com.aimxcel.abclearn.glaciers.GlaciersStrings;
import com.aimxcel.abclearn.glaciers.model.IToolProducer;
import com.aimxcel.abclearn.glaciers.view.GlaciersModelViewTransform;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ToolTipNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;


public class ToolboxNode extends PNode {
    
    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
     * true: show a trash can icon, dispose of tools by dragging them to this icon
     * false: no trash can icon, dispose of tools by dragging them back to the toolbox
     */
    private static final boolean SHOW_TRASH_CAN = false;
    
    // spacing properties
    private static final int HORIZONTAL_ICON_SPACING = 15; // horizontal space between icons
    private static final Insets BACKGROUND_INSETS = new Insets( 5, 15, 5, 15 ); // top, left, bottom, right
    private static final int TAB_MARGIN = 5; // margin between the tab and its title text
    
    // background properties
    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY; // toolbox background
    private static final Color BACKGROUND_STROKE_COLOR = new Color( 82, 126, 90 ); // green
    private static final Stroke BACKGROUND_STROKE = new BasicStroke( 2f );
    private static final double BACKGROUND_CORNER_RADIUS = 10;
    
    // tab properties
    private static final Font TAB_LABEL_FONT = new AimxcelFont( 12 );
    private static final Color TAB_LABEL_COLOR = Color.BLACK;
    private static final Color TAB_COLOR = BACKGROUND_COLOR;
    private static final Color TAB_STROKE_COLOR = BACKGROUND_STROKE_COLOR;
    private static final Stroke TAB_STROKE = BACKGROUND_STROKE;
    private static final double TAB_CORNER_RADIUS = BACKGROUND_CORNER_RADIUS;
    
    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    // handles the specifics of trashing tools, independent of the trash can representation
    private final TrashCanDelegate _trashCanDelegate;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    /**
     * Constructor.
     * 
     * @param toolProducer
     * @param mvt
     */
    public ToolboxNode( IToolProducer toolProducer, GlaciersModelViewTransform mvt ) {
        super();
        
        // list of node to be passed to layout method
        ArrayList layoutNodeList = new ArrayList();
        
        // create icons, under a common parent
        PNode iconsParentNode = new PNode();
        PNode toolTipsParentNode = new PNode();
        {
            PNode toolNode = null;
            PNode toolTipNode = null;
            
            // Thermometer
            toolNode = new ThermometerIconNode( toolProducer, mvt );
            toolTipNode = new ToolboxToolTipNode( GlaciersStrings.TOOLTIP_THERMOMETER, toolNode );
            toolTipsParentNode.addChild( toolTipNode );
            layoutNodeList.add( toolNode );
            
            // Glacial Budget Meter
            toolNode = new GlacialBudgetMeterIconNode( toolProducer, mvt );
            toolTipNode = new ToolboxToolTipNode( GlaciersStrings.TOOLTIP_GLACIAL_BUDGET_METER, toolNode );
            toolTipsParentNode.addChild( toolTipNode );
            layoutNodeList.add( toolNode );
            
            // Tracer Flag
            toolNode = new TracerFlagIconNode( toolProducer, mvt );
            toolTipNode = new ToolboxToolTipNode( GlaciersStrings.TOOLTIP_TRACER_FLAG, toolNode );
            toolTipsParentNode.addChild( toolTipNode );
            layoutNodeList.add( toolNode );
            
            // Ice Thickness Tool
            toolNode = new IceThicknessToolIconNode( toolProducer, mvt );
            toolTipNode = new ToolboxToolTipNode( GlaciersStrings.TOOLTIP_ICE_THICKNESS_TOOL, toolNode );
            toolTipsParentNode.addChild( toolTipNode );
            layoutNodeList.add( toolNode );
            
            // Borehole Drill
            toolNode = new BoreholeDrillIconNode( toolProducer, mvt );
            toolTipNode = new ToolboxToolTipNode( GlaciersStrings.TOOLTIP_BOREHOLE_DRILL, toolNode );
            toolTipsParentNode.addChild( toolTipNode );
            layoutNodeList.add( toolNode );
            
            // GPS Receiver
            toolNode = new GPSReceiverIconNode( toolProducer, mvt );
            toolTipNode = new ToolboxToolTipNode( GlaciersStrings.TOOLTIP_GPS_RECEIVER, toolNode );
            toolTipsParentNode.addChild( toolTipNode );
            layoutNodeList.add( toolNode );
            
            if ( SHOW_TRASH_CAN ) {
                // Spacer
                PPath spacerNode = new PPath( new Rectangle( 0, 0, 20, 5 ) );
                spacerNode.setPaint( null );
                spacerNode.setStroke( null );
                layoutNodeList.add( spacerNode );

                // Trash Can icon
                TrashCanIconNode trashCanNode = new TrashCanIconNode( toolProducer );
                toolTipNode = new ToolboxToolTipNode( GlaciersStrings.TOOLTIP_TRASH_CAN, trashCanNode );
                toolTipsParentNode.addChild( toolTipNode );
                layoutNodeList.add( trashCanNode );
                
                // use the trash can icon's delegate, drop tools on the trash can icon to delete them
                _trashCanDelegate = trashCanNode.getTrashCanDelegate();
            }
            else {
                // use the toolbox's delegate, drop tools on the toolbox to delete them
                _trashCanDelegate = new TrashCanDelegate( this, toolProducer );
            }
            
            layoutNodes( layoutNodeList, iconsParentNode );
        }
        
        // create the background
        PPath backgroundNode = new PPath();
        {
            final double backgroundWidth = iconsParentNode.getFullBoundsReference().getWidth() + BACKGROUND_INSETS.left + BACKGROUND_INSETS.right;
            final double backgroundHeight = iconsParentNode.getFullBoundsReference().getHeight() + BACKGROUND_INSETS.top + BACKGROUND_INSETS.bottom;
            RoundRectangle2D r = new RoundRectangle2D.Double( 0, 0, backgroundWidth, backgroundHeight, BACKGROUND_CORNER_RADIUS, BACKGROUND_CORNER_RADIUS );
            backgroundNode.setPathTo( r );
            backgroundNode.setPaint( BACKGROUND_COLOR );
            backgroundNode.setStroke( BACKGROUND_STROKE );
            backgroundNode.setStrokePaint( BACKGROUND_STROKE_COLOR );
        }
        
        // create the title tab
        PComposite tabNode = new PComposite();
        final double tabOverlap = 10;
        {
            PText titleNode = new PText( GlaciersStrings.TITLE_TOOLBOX );
            titleNode.setFont( TAB_LABEL_FONT );
            titleNode.setTextPaint( TAB_LABEL_COLOR );
            
            final double tabWidth = titleNode.getFullBoundsReference().getWidth() + ( 2 * TAB_MARGIN );
            final double tabHeight = titleNode.getFullBoundsReference().getHeight() + ( 2 * TAB_MARGIN ) + tabOverlap;
            RoundRectangle2D r = new RoundRectangle2D.Double( 0, 0, tabWidth, tabHeight, TAB_CORNER_RADIUS, TAB_CORNER_RADIUS );
            PPath pathNode = new PPath( r );
            pathNode.setPaint( TAB_COLOR );
            pathNode.setStroke( TAB_STROKE );
            pathNode.setStrokePaint( TAB_STROKE_COLOR );
            
            tabNode.addChild( pathNode );
            tabNode.addChild( titleNode );
            
            pathNode.setOffset( 0, 0 );
            titleNode.setOffset( TAB_MARGIN, TAB_MARGIN );
        }
       
        addChild( tabNode );
        addChild( backgroundNode );
        addChild( iconsParentNode );
        addChild( toolTipsParentNode );
        
        // origin at upper left corner of tab
        tabNode.setOffset( 0, 0 );
        backgroundNode.setOffset( tabNode.getFullBounds().getX(), tabNode.getFullBounds().getMaxY() - tabOverlap );
        iconsParentNode.setOffset( backgroundNode.getFullBounds().getX() + BACKGROUND_INSETS.left, backgroundNode.getFullBounds().getY() + BACKGROUND_INSETS.top );
        
        // only the tools are interactive
        this.setPickable( false );
        iconsParentNode.setPickable( false );
        backgroundNode.setPickable( false );
        backgroundNode.setChildrenPickable( false );
        tabNode.setPickable( false );
        tabNode.setChildrenPickable( false );
    }
    
    /*
     * Sets the positions of the icons.
     */
    private static void layoutNodes( ArrayList nodes, PNode parentNode ) {
        
        // add all icons to parent, calculate max height
        Iterator i = nodes.iterator();
        while ( i.hasNext() ) {
            PNode currentNode = (PNode) i.next();
            parentNode.addChild( currentNode );
        }
        final double maxToolHeight = parentNode.getFullBoundsReference().getHeight();
        
        // arrange icons in the toolbox from left to right, vertically centered
        double x, y;
        PNode previousNode = null;
        Iterator j = nodes.iterator();
        while ( j.hasNext() ) {
            PNode currentNode = (PNode) j.next();
            if ( previousNode == null ) {
                x = 0;
                y = ( maxToolHeight - currentNode.getFullBoundsReference().getHeight() ) / 2;
            }
            else {
                x = previousNode.getFullBoundsReference().getMaxX() + HORIZONTAL_ICON_SPACING;
                y = ( maxToolHeight - currentNode.getFullBoundsReference().getHeight() ) / 2; 
            }
            currentNode.setOffset( x, y );
            previousNode = currentNode;
        }
    }
    
    public TrashCanDelegate getTrashCanDelegate() {
        return _trashCanDelegate;
    }
    
    private static final class ToolboxToolTipNode extends ToolTipNode {
        
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ToolboxToolTipNode( String text, PNode associatedNode ) {
            super( text, associatedNode, GlaciersConstants.TOOLTIPS_INITIAL_DELAY );
            setLocationStrategy( new LeftAlignedAboveMouseCursor() );
        }
    }
}
