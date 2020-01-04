
package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.SwingUtils;
import com.aimxcel.abclearn.core.aimxcelcore.RichPNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.pswing.PSwing;


public class ControlPanelNode extends RichPNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_ARC = 20;

    private static final Color DEFAULT_BACKGROUND_COLOR = new Color( 238, 238, 238 );
    private static final Color DEFAULT_BORDER_COLOR = Color.gray;
    private static final BasicStroke DEFAULT_STROKE = new BasicStroke( 2 );
    public static final int DEFAULT_INSET = 9;

    protected final AimxcelPPath background;

    public ControlPanelNode( JComponent jComponent ) {
        this( new PSwing( jComponent ) );
    }

    public ControlPanelNode( JComponent jComponent, Color backgroundColor ) {
        this( new PSwing( jComponent ), backgroundColor );
    }

    public ControlPanelNode( final PNode content ) {
        this( content, DEFAULT_BACKGROUND_COLOR );
    }

    public ControlPanelNode( final PNode content, Color backgroundColor ) {
        this( content, backgroundColor, DEFAULT_STROKE, DEFAULT_BORDER_COLOR );
    }

    public ControlPanelNode( final PNode content, Color backgroundColor, BasicStroke borderStroke, Color borderColor ) {
        this( content, backgroundColor, borderStroke, borderColor, DEFAULT_INSET );
    }

    public ControlPanelNode( final PNode content, Color backgroundColor, BasicStroke borderStroke, Color borderColor, final int inset ) {
        this( content, backgroundColor, borderStroke, borderColor, inset, DEFAULT_ARC, true );
    }

    public ControlPanelNode( final PNode content, Color backgroundColor, BasicStroke borderStroke, Color borderColor, final int inset, final int arc, boolean transparifySwing ) {
        //Make sure the background resizes when the content resizes
        background = new AimxcelPPath( backgroundColor, borderStroke, borderColor ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            final PropertyChangeListener updateSize = new PropertyChangeListener() {
                public void propertyChange( PropertyChangeEvent evt ) {
                    final PBounds layoutSize = getControlPanelBounds( content );
                    //Set the size of the border, subtracting out any local offset of the content node
                    setPathTo( new RoundRectangle2D.Double( 0, 0, layoutSize.width + inset * 2, layoutSize.height + inset * 2, arc, arc ) );
                }
            };
            content.addPropertyChangeListener( PROPERTY_FULL_BOUNDS, updateSize );
            updateSize.propertyChange( null );
        }};
        // Create a node that puts the contents at the inset location
        // regardless of its initial offset.
        PNode insetContentNode = new ZeroOffsetNode( content ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( inset, inset );
        }};
        addChild( background );
        background.addChild( insetContentNode );
        if ( transparifySwing ) {
            transparifySwing( this );
        }
    }

    /**
     * Determine the bounds of the control panel.  This implementation uses the bounds of the content.
     * This may be overridden if subclasses don't want control panel size to change when the contents
     * change.
     *
     * @param content the content PNode in this ControlPanelNode
     * @return the PBounds which the ControlPanelNode should occupy
     */
    protected PBounds getControlPanelBounds( PNode content ) {
        return content.getFullBounds();
    }

    /**
     * Make it so you can see through all the swing components, so the panel background color shows through.
     *
     * @param node
     */
    private void transparifySwing( PNode node ) {
        for ( int i = 0; i < node.getChildrenCount(); i++ ) {
            PNode child = node.getChild( i );
            if ( child instanceof PSwing ) {
                SwingUtils.setBackgroundDeep( ( (PSwing) child ).getComponent(), new Color( 0, 0, 0, 0 ),
                                              new Class[] { JTextComponent.class,
                                                      JComboBox.class//have to ignore this one or the drop down button color changes (usually for the worse)
                                              }, false );
            }
            transparifySwing( child );
        }
    }
}