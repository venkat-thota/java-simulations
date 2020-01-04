
package com.aimxcel.abclearn.core.aimxcelcore.nodes.periodictable;

import static com.aimxcel.abclearn.core.aimxcelcore.nodes.periodictable.PeriodicTableNode.CELL_DIMENSION;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;

import edu.umd.cs.piccolo.nodes.PText;


public class BasicElementCell extends ElementCell {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Font LABEL_FONT = new AimxcelFont( 12 );
    private final PText text;
    private final AimxcelPPath box;

    public BasicElementCell( final int atomicNumber, final Color backgroundColor ) {
        super( atomicNumber );

        box = new AimxcelPPath( new Rectangle2D.Double( 0, 0, CELL_DIMENSION, CELL_DIMENSION ), backgroundColor, new BasicStroke( 1 ), Color.black );
        addChild( box );

        String abbreviation = SymbolTable.getSymbol( atomicNumber );
        text = new PText( abbreviation ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{ setFont( LABEL_FONT ); }};
        text.setOffset( box.getFullBounds().getCenterX() - text.getFullBounds().getWidth() / 2,
                        box.getFullBounds().getCenterY() - text.getFullBounds().getHeight() / 2 );
        addChild( text );
    }

    protected PText getText() {
        return text;
    }

    protected AimxcelPPath getBox() {
        return box;
    }
}