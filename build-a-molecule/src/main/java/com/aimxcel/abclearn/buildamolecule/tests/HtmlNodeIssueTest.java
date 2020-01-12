package com.aimxcel.abclearn.buildamolecule.tests;

import java.awt.*;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

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
