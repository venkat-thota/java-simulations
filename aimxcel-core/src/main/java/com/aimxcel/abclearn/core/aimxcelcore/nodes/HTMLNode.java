

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;


public class HTMLNode extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    private static final Font DEFAULT_FONT = new AimxcelFont( Font.BOLD, 12 );
    private static final Color DEFAULT_COLOR = Color.BLACK;

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private String html;
    private Color color;
    private Font font;
    private final JLabel label;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public HTMLNode() {
        this( null, DEFAULT_COLOR, DEFAULT_FONT );
    }

    public HTMLNode( String html ) {
        this( html, DEFAULT_COLOR, DEFAULT_FONT );
    }

    public HTMLNode( String html, Color color ) {
        this( html, color, DEFAULT_FONT );
    }

    public HTMLNode( String html, Color color, Font font ) {
        this.html = HTMLUtils.toHTMLString( html );
        this.color = color;
        this.font = font;
        label = new JLabel();
        update();
    }

    //----------------------------------------------------------------------------
    // Accessors
    //----------------------------------------------------------------------------

    /**
     * Gets the HTML string.
     *
     * @return HTML string
     */
    public String getHTML() {
        return html;
    }

    /**
     * Sets the HMTL string.
     * If you provide plain text or an HTML fragment, it will automatically be converted to HTML.
     *
     * @param text
     */
    public void setHTML( String text ) {
        String html = HTMLUtils.toHTMLString( text );
        if ( ( html == null && this.html == null ) || ( html != null && html.equals( this.html ) ) ) {
            return;
        }
        this.html = html;
        update();
    }

    /**
     * Gets the font.
     *
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the font.
     *
     * @param font
     */
    public void setFont( Font font ) {
        this.font = font;
        update();
    }

    /**
     * Gets the color used to render the HTML.
     * If you want to get the paint used for the node, use getPaint.
     *
     * @return the color used to render the HTML.
     */
    public Color getHTMLColor() {
        return color;
    }

    /**
     * Sets the color used to render the HTML.
     * If you want to set the paint used for the node, use setPaint.
     *
     * @param color
     */
    public void setHTMLColor( Color color ) {
        this.color = color;
        update();
    }

    //----------------------------------------------------------------------------
    // Update handler
    //----------------------------------------------------------------------------

    /*
     * Updates everything that is involved in rendering the HTML string.
     * This method is called when one the HTML-related properties is modified.
     */
    private void update() {
        label.setText( html );
        label.setFont( font );
        label.setForeground( color );
        label.setSize( label.getPreferredSize() );
        setBounds( 0, 0, label.getPreferredSize().getWidth(), label.getPreferredSize().getHeight() );
        repaint();
    }

    //----------------------------------------------------------------------------
    // PNode overrides
    //----------------------------------------------------------------------------

    /*
     * Paints the node.
     * The HTML is painted last, so it appears on top of any child nodes.
     * 
     * @param paintContext
     */
    @Override
    protected void paint( PPaintContext paintContext ) {
        super.paint( paintContext );
        Graphics2D g2 = paintContext.getGraphics();

        //save the old RenderingHints for restoring afterwards
        RenderingHints renderingHints = g2.getRenderingHints();

        //disable fractional metrics, if causes bounds to be computed incorrectly for some font sizes, see #2178
        g2.setRenderingHint( RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF );
        label.paint( g2 );

        //restore rendering hints so other painting systems are undisturbed
        g2.setRenderingHints( renderingHints );
    }
}
