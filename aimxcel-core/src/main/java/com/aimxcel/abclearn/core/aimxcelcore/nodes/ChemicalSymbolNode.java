

package com.aimxcel.abclearn.core.aimxcelcore.nodes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.HTMLUtils;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.nodes.PComposite;


public class ChemicalSymbolNode extends PComposite {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double DEFAULT_SUPER_SCRIPT_SCALE = 0.8;
    private static final double DEFAULT_SUB_SCRIPT_SCALE = DEFAULT_SUPER_SCRIPT_SCALE;
    private static final double X_SPACING = 0; // horizontal spacing between FragmentNodes

    private static final String HTML_BEGIN_TAG = "<html>";
    private static final String HTML_END_TAG = "</html>";
    private static final String SUPERSCRIPT_BEGIN_TAG = "<sup>";
    private static final String SUPERSCRIPT_END_TAG = "</sup>";
    private static final String SUBSCRIPT_BEGIN_TAG = "<sub>";
    private static final String SUBSCRIPT_END_TAG = "</sub>";

    private final double capHeight;

    public ChemicalSymbolNode( String html, Font font, Color color ) {
        this( html, font, color, DEFAULT_SUPER_SCRIPT_SCALE, DEFAULT_SUB_SCRIPT_SCALE );
    }

    /**
     * Constructor.
     *
     * @param html             HTML fragment for the symbol
     * @param font             font used for all parts of the symbol
     * @param color            color used for all parts of the symbol
     * @param superscriptScale how much the superscript is scaled relative to the normal portion of the symbol
     * @param subscriptScale   how much the subscript is scaled relative to the normal portion of the symbol
     */
    public ChemicalSymbolNode( String html, Font font, Color color, double superscriptScale, double subscriptScale ) {
        super();

        // determine the height of a capital letter
        NormalNode capNode = new NormalNode( "A", font, color );
        capHeight = capNode.getFullBoundsReference().getHeight();

        // convert HTML to nodes
        ArrayList<FragmentNode> nodes = htmlToNodes( html, font, color, superscriptScale, subscriptScale );

        // layout the nodes
        Iterator<FragmentNode> i = nodes.iterator();
        double xOffset = 0;
        while ( i.hasNext() ) {
            FragmentNode node = i.next();
            addChild( node );
            if ( node instanceof NormalNode ) {
                node.setOffset( xOffset, -capHeight );
            }
            else if ( node instanceof SuperscriptNode ) {
                node.setOffset( xOffset, -capHeight - ( node.getFullBoundsReference().getHeight() / 2 ) );
            }
            else if ( node instanceof SubscriptNode ) {
                node.setOffset( xOffset, -capHeight + ( node.getFullBoundsReference().getHeight() / 2 ) );
            }
            else {
                throw new UnsupportedOperationException( "unsupported node type: " + node.getClass().getName() );
            }
            xOffset += node.getFullBoundsReference().getWidth() + X_SPACING;
        }
    }

    /**
     * Gets the height of capital (uppercase) letters.
     *
     * @return
     */
    public double getCapHeight() {
        return capHeight;
    }

    /**
     * Sets the symbol color.
     *
     * @param color
     */
    public void setSymbolColor( Color color ) {
        for ( int i = 0; i < getChildrenCount(); i++ ) {
            PNode child = getChild( i );
            if ( child instanceof FragmentNode ) {
                ( (FragmentNode) child ).setHTMLColor( color );
            }
        }
    }

    /*
     * Converts HTML into an ordered list of nodes.
     * Each node will be responsible for rendering a fragment of the HTML.
     */
    private static ArrayList<FragmentNode> htmlToNodes( final String html, Font font, Color color, double superscriptScale, double subscriptScale ) {

        ArrayList<FragmentNode> nodeList = new ArrayList<FragmentNode>();

        String s = new String( html ); // operate on a copy

        // strip off html tags
        if ( s.startsWith( HTML_BEGIN_TAG ) ) {
            s = s.substring( HTML_BEGIN_TAG.length() );
        }
        if ( s.endsWith( HTML_END_TAG ) ) {
            s = s.substring( 0, s.length() - HTML_END_TAG.length() );
        }

        // convert html to nodes
        boolean done = false;
        String token = null;
        while ( !done ) {

            // look for the next tag of interest
            int index;
            final int subIndex = s.indexOf( SUBSCRIPT_BEGIN_TAG );
            final int supIndex = s.indexOf( SUPERSCRIPT_BEGIN_TAG );
            if ( subIndex == -1 ) {
                index = supIndex;
            }
            else if ( supIndex == -1 ) {
                index = subIndex;
            }
            else {
                index = Math.min( supIndex, subIndex );
            }

            if ( index == -1 ) {
                // if no tag, any remaining text appears on the normal line of text
                if ( s.length() > 0 ) {
                    nodeList.add( new NormalNode( s, font, color ) );
                }
                done = true;
            }
            else {

                // anything before the tag of interest appears on the normal line of text
                token = s.substring( 0, index );
                if ( token.length() > 0 ) {
                    nodeList.add( new NormalNode( token, font, color ) );
                }
                s = s.substring( index, s.length() );

                if ( s.startsWith( SUPERSCRIPT_BEGIN_TAG ) ) {
                    // superscript
                    index = s.indexOf( SUPERSCRIPT_END_TAG );
                    if ( index == -1 ) {
                        throw new UnsupportedOperationException( "malformed HTML, missing " + SUPERSCRIPT_END_TAG + ": " + s );
                    }
                    else {
                        token = s.substring( SUPERSCRIPT_BEGIN_TAG.length(), index );
                        nodeList.add( new SuperscriptNode( token, font, color, superscriptScale ) );
                        s = s.substring( index + SUPERSCRIPT_END_TAG.length(), s.length() );
                    }
                }
                else if ( s.startsWith( SUBSCRIPT_BEGIN_TAG ) ) {
                    // subscript
                    index = s.indexOf( SUBSCRIPT_END_TAG );
                    if ( index == -1 ) {
                        throw new UnsupportedOperationException( "malformed HTML, missing " + SUBSCRIPT_END_TAG + ": " + s );
                    }
                    else {
                        token = s.substring( SUBSCRIPT_BEGIN_TAG.length(), index );
                        nodeList.add( new SubscriptNode( token, font, color, subscriptScale ) );
                        s = s.substring( index + SUBSCRIPT_END_TAG.length(), s.length() );
                    }
                }
                else {
                    // unrecognized tags (eg, <i>) are skipped and included in the next token
                    s = s.substring( index + 1, s.length() );
                }
            }
        }

        return nodeList;
    }

    /*
     * Base class for rendering an HTML fragment.
     */
    private static abstract class FragmentNode extends HTMLNode {

        public FragmentNode( String text, Font font, Color color ) {
            super( HTMLUtils.toHTMLString( text ) );
            setFont( font );
            setHTMLColor( color );
        }
    }

    /*
     * A fragment that that appears on the normal line of type.
     * Also serves as a marker type for making layout decisions.
     */
    private static class NormalNode extends FragmentNode {

        public NormalNode( String text, Font font, Color color ) {
            super( text, font, color );
            // verify that text contains at least one uppercase char
            if ( !text.matches( ".*[A-Z]+.*" ) ) {
                throw new IllegalArgumentException( "text must contain at least one uppercase char: " + text );
            }
        }
    }

    /*
     * A fragment that is a superscript, appears above the normal line of type.
     * Also serves as a marker type for making layout decisions.
     */
    private static class SuperscriptNode extends FragmentNode {

        public SuperscriptNode( String text, Font font, Color color, double scale ) {
            super( text, font, color );
            scale( scale );
        }
    }

    /*
     * A fragment that is a subscript, appears below the normal line of type.
     * Also serves as a marker type for making layout decisions.
     */
    private static class SubscriptNode extends FragmentNode {

        public SubscriptNode( String text, Font font, Color color, double scale ) {
            super( text, font, color );
            scale( scale );
        }
    }

    /* test */
    public static void main( String[] args ) {

        Font font = new AimxcelFont( Font.BOLD, 22 );
        ArrayList<PNode> nodes = new ArrayList<PNode>();
        nodes.add( new ChemicalSymbolNode( "H<sub>3</sub>O<sup>+</sup>", font, Color.RED ) );
        nodes.add( new ChemicalSymbolNode( "H<i>A</i>", font, Color.GREEN ) );
        nodes.add( new ChemicalSymbolNode( "H<sub>2</sub>O", font, Color.BLUE ) );
        nodes.add( new ChemicalSymbolNode( "C<sub>5</sub>H<sub>5</sub>NH<sup>+</sup>", font, Color.BLACK ) );
        nodes.add( new ChemicalSymbolNode( "HCl", font, Color.MAGENTA ) );

        AimxcelPCanvas canvas = new AimxcelPCanvas();
        canvas.setPreferredSize( new Dimension( 600, 400 ) );

        double xOffset = 20;
        double yOffset = 100;
        Iterator<PNode> i = nodes.iterator();
        while ( i.hasNext() ) {
            PNode node = i.next();
            canvas.getLayer().addChild( node );
            node.setOffset( xOffset, yOffset );
            xOffset = node.getFullBoundsReference().getMaxX() + 10;
        }

        JFrame frame = new JFrame();
        frame.getContentPane().add( canvas );
        frame.pack();
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
}
