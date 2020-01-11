//  Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.buildamolecule.tests;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

//REVIEW Move to piccolo-phet/tests and reference in #2780?

/**
 * See #2780.
 * Resizing of HTMLNodes is jittery, very much like Flex text.
 * Probably because HTMLNode does rendering using JLabel, which renders HTML using BasicHTML,
 * which probably uses integer font sizes.
 */
public class HtmlNodeIssueTest {
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "HtmlNodeIssueTest" );
        frame.setContentPane( new AimxcelPCanvas() {{
            Dimension size = new Dimension( 550, 100 );
            setPreferredSize( size );

            final double xOffset = 120;
            final double yOffset = 30;

            setWorldTransformStrategy( new AimxcelPCanvas.CenteredStage( this, size ) );

            addWorldChild( new PNode() {{
                addChild( new PText( "PText" ) {{
                    setFont( new AimxcelFont( 20, true ) );
                }} );
                addChild( new HTMLNode( "HTMLNode" ) {{
                    setFont( new AimxcelFont( 20, true ) );
                    setOffset( 0, yOffset );
                }} );
                addChild( new PText( "Resize the window and watch my width!" ) {{
                    setFont( new AimxcelFont( 20, true ) );
                    setOffset( xOffset, 0 );
                }} );
                addChild( new HTMLNode( "Resize the window and watch my width!" ) {{
                    setFont( new AimxcelFont( 20, true ) );
                    setOffset( xOffset, yOffset );
                }} );
                setOffset( 20, 20 );
            }} );
        }} );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible( true );
    }
}
