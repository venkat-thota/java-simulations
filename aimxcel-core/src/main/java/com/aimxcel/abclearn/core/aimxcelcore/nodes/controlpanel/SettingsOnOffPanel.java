 
package com.aimxcel.abclearn.core.aimxcelcore.nodes.controlpanel;

import fj.F;
import fj.P2;
import fj.data.List;
import java.awt.geom.Line2D;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentChain;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyRadioButton;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.AimxcelPNode;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.HBox;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.VBox;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

import static com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources.getString;


public class SettingsOnOffPanel extends PNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final AimxcelFont FONT = new AimxcelFont( 20, true );

    //Class that encapsulates the information for a settings feature (such as audio on/off).
    public static final class Feature {

        //The icon to show when the feature is off
        public final PNode off;

        //The icon to show when the feature is on
        public final PNode on;

        //Property that views/sets the state of whether the feature is enabled.
        public final BooleanProperty onProperty;

        //Sim sharing component
        public final IUserComponent component;

        public Feature( PNode off, PNode on, BooleanProperty onProperty, IUserComponent component ) {
            this.off = off;
            this.on = on;
            this.onProperty = onProperty;
            this.component = component;
        }
    }

    public SettingsOnOffPanel( final List<Feature> icons ) {
        final List<PNode> all = icons.bind( nodes );
        List<Feature> padded = icons.map( new F<Feature, Feature>() {
            @Override public Feature f( final Feature element ) {
                return new Feature( new PaddedNode( AimxcelPNode.getMaxSize( all ), element.off ),
                                    new PaddedNode( AimxcelPNode.getMaxSize( all ), element.on ), element.onProperty, element.component );
            }
        } );
        VBox box = new VBox( 4 );

        //For each feature, wire it up and add to the panel
        for ( final P2<Feature, Integer> e : padded.zipIndex() ) {
            final Feature feature = e._1();

            //Wire up to the property
            final PBasicInputEventHandler toggle = new PBasicInputEventHandler() {
                @Override public void mouseReleased( final PInputEvent event ) {
                    feature.onProperty.toggle();
                }
            };

            //Add mouse listeners
            feature.on.addInputEventListener( toggle );
            feature.on.addInputEventListener( new CursorHandler() );
            feature.off.addInputEventListener( toggle );
            feature.off.addInputEventListener( new CursorHandler() );

            //Add to the layout
            box.addChild( createRowNode( feature ) );

            //Set initial transparency.  When feature is enabled/disabled it will cross-fade.
            feature.on.setTransparency( feature.onProperty.get() ? 1 : 0 );
            feature.off.setTransparency( feature.onProperty.get() ? 0 : 1 );

            //Show a separator line if there is another element after this.
            if ( e._2() < padded.length() - 1 ) {
                box.addChild( new AimxcelPPath( new Line2D.Double( 0, 0, box.getFullWidth(), 0 ) ) );
            }
        }
        addChild( new ZeroOffsetNode( box ) );
    }

    //Create the HBox for a single feature that will show the on/off icon and "on"/"off" radio buttons.
    private static HBox createRowNode( final Feature feature ) {
        PNode offButton = new PSwing( new PropertyRadioButton<Boolean>( UserComponentChain.chain( feature.component, "off" ), getString( "Games.radioButton.off" ), feature.onProperty, false ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setFont( FONT );
            setOpaque( false );
        }} );
        PNode onButton = new PSwing( new PropertyRadioButton<Boolean>( UserComponentChain.chain( feature.component, "on" ), getString( "Games.radioButton.on" ), feature.onProperty, true ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setFont( FONT );
            setOpaque( false );
        }} );
        return new HBox( 4, new PNode() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addChild( feature.on );
            addChild( feature.off );

            //Cross fade when toggled on/off
            feature.onProperty.addObserver( new VoidFunction1<Boolean>() {
                public void apply( final Boolean on ) {
                    if ( on ) {
                        feature.on.animateToTransparency( 1, 300 );
                        feature.off.animateToTransparency( 0, 300 );
                    }
                    else {
                        feature.on.animateToTransparency( 0, 300 );
                        feature.off.animateToTransparency( 1, 300 );
                    }
                }
            } );
        }}, offButton, onButton );
    }

    //Get the nodes for an element.
    public static final F<Feature, List<PNode>> nodes = new F<Feature, List<PNode>>() {
        @Override public List<PNode> f( final Feature feature ) {
            return List.list( feature.on, feature.off );
        }
    };
}