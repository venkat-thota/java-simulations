package com.aimxcel.abclearn.buildamolecule.tests;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import com.aimxcel.abclearn.buildamolecule.control.CollectionAreaNode;
import com.aimxcel.abclearn.buildamolecule.control.CollectionPanel;
import com.aimxcel.abclearn.buildamolecule.control.GeneralLayoutNode;
import com.aimxcel.abclearn.buildamolecule.control.GeneralLayoutNode.HorizontalAlignMethod.Align;
import com.aimxcel.abclearn.buildamolecule.model.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;

import static com.aimxcel.abclearn.chemistry.model.Element.*;
public class GeneralLayoutTest {
    public static void main( String[] args ) {
//        PDebug.debugBounds = true;
        JFrame frame = new JFrame( "GeneralLayoutTest" );
        frame.setContentPane( new AimxcelPCanvas() {{
            Dimension size = new Dimension( 480, 900 );
            setPreferredSize( size );

            setWorldTransformStrategy( new CenteredStage( this, size ) );

            GeneralLayoutNode layoutNode = new GeneralLayoutNode() {{
                setOffset( 50, 10 );
                LayoutMethod method = new CompositeLayoutMethod( new VerticalLayoutMethod(), new HorizontalAlignMethod( Align.Centered ) );
                addChild( new LayoutElement( AimxcelPPath.createRectangle( 0, 0, 100, 50 ), method ) );
                addChild( new LayoutElement( AimxcelPPath.createRectangle( 0, 0, 50, 30 ), method ) );
                addChild( new LayoutElement( AimxcelPPath.createRectangle( 0, 0, 100, 30 ), method, 20, 0, 10, 0 ) );
                addChild( new LayoutElement( AimxcelPPath.createRectangle( 0, 0, 70, 50 ), method ) );
                addChild( new LayoutElement( AimxcelPPath.createRectangle( 0, 0, 70, 50 ),
                                             new CompositeLayoutMethod( new VerticalLayoutMethod(), new HorizontalAlignMethod( Align.Left ) ) {
                                                 @Override public void layout( LayoutElement element, int index, LayoutElement previousElement, LayoutProperties layoutProperties ) {
                                                     System.out.println( "maxWidth: " + layoutProperties.maxWidth );
                                                     super.layout( element, index, previousElement, layoutProperties );
                                                     System.out.println( "bounds: " + element.node.getFullBounds() );
                                                     System.out.println( "placement: " + element.node.getOffset() );
                                                 }
                                             } ) );
                addChild( new LayoutElement( AimxcelPPath.createRectangle( 0, 0, 70, 50 ),
                                             new CompositeLayoutMethod( new VerticalLayoutMethod(), new HorizontalAlignMethod( Align.Right ) ) ) );

                final IClock clock = new ConstantDtClock( 30 );
                final LayoutBounds bounds = new LayoutBounds( false, CollectionPanel.getCollectionPanelModelWidth( false ) );
                addChild( new CollectionAreaNode( new KitCollection() {{
                    addKit( new Kit( bounds,
                                     new Bucket( new PDimension( 400, 200 ), clock, H, 2 ),
                                     new Bucket( new PDimension( 450, 200 ), clock, O, 2 )
                    ) );

                    addKit( new Kit( bounds,
                                     new Bucket( new PDimension( 500, 200 ), clock, C, 2 ),
                                     new Bucket( new PDimension( 600, 200 ), clock, O, 4 ),
                                     new Bucket( new PDimension( 500, 200 ), clock, N, 2 )
                    ) );
                    addKit( new Kit( bounds,
                                     new Bucket( new PDimension( 600, 200 ), clock, H, 12 ),
                                     new Bucket( new PDimension( 600, 200 ), clock, O, 4 ),
                                     new Bucket( new PDimension( 500, 200 ), clock, N, 2 )
                    ) );
                    addCollectionBox( new CollectionBox( MoleculeList.CO2, 2 ) );
                    addCollectionBox( new CollectionBox( MoleculeList.O2, 2 ) );
                    addCollectionBox( new CollectionBox( MoleculeList.getMoleculeByName( "Trifluoroborane" ), 4 ) );
                    addCollectionBox( new CollectionBox( MoleculeList.NH3, 2 ) );
                }}, false, new Function1<PNode, Rectangle2D>() {
                    public Rectangle2D apply( PNode pNode ) {
                        return pNode.getFullBounds();
                    }
                }
                ), method );
            }};
//            addWorldChild( new AimxcelPPath( layoutNode.getFullBounds() ) {{
//                setStrokePaint( Color.BLUE );
//                setPaint( Color.RED.darker() );
//            }} );
            addWorldChild( layoutNode );
        }} );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible( true );
    }
}
