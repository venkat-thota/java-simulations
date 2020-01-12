package com.aimxcel.abclearn.platetectonics.control;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ControlPanelNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.kit.ZeroOffsetNode;
import com.aimxcel.abclearn.lwjgl.nodes.OrthoCoreNode;
import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

import static com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils.swingObserver;
import static com.aimxcel.abclearn.platetectonics.PlateTectonicsConstants.PANEL_TITLE_FONT;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;
import com.aimxcel.abclearn.platetectonics.control.RulerNode3D.RulerNode2D;
import com.aimxcel.abclearn.platetectonics.model.ToolboxState;
import com.aimxcel.abclearn.platetectonics.tabs.PlateTectonicsTab;
public class ToolboxNode extends OrthoCoreNode {
    private static final double INSET = 5;

    public ToolboxNode( final PlateTectonicsTab tab, final ToolboxState toolboxState ) {
        super( new ControlPanelNode( new PNode() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            final float kmToViewUnit = 0.75f;
            final ZeroOffsetNode rulerNode2D = new ZeroOffsetNode( new RulerNode2D( kmToViewUnit, tab ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                // make the ruler visible when it is in the toolbox
                toolboxState.rulerInToolbox.addObserver( swingObserver( new Runnable() {
                    public void run() {
                        setVisible( toolboxState.rulerInToolbox.get() );
                    }
                } ) );

                // remove it from the toolbox when pressed
                addInputEventListener( new PBasicInputEventHandler() {
                    @Override public void mousePressed( PInputEvent event ) {
                        LWJGLUtils.invoke( new Runnable() {
                            public void run() {
                                toolboxState.rulerInToolbox.set( false );
                            }
                        } );
                    }
                } );

                scale( 0.5 );
            }} ); // wrap it in a zero-offset node, since we are rotating and scaling it (bad origin)

            addChild( rulerNode2D ); // approximate scaling to get the size right

            final PNode thermometer = new ZeroOffsetNode( new com.aimxcel.abclearn.platetectonics.control.ThermometerNode3D.ThermometerNode2D( kmToViewUnit ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{

                //Move it to the right of the ruler
                setOffset( rulerNode2D.getFullBounds().getWidth() + INSET, rulerNode2D.getFullBounds().getMaxY() - getFullBounds().getHeight() );

                // make it visible when it is in the toolbox
                toolboxState.thermometerInToolbox.addObserver( swingObserver( new Runnable() {
                    public void run() {
                        setVisible( toolboxState.thermometerInToolbox.get() );
                    }
                } ) );

                // remove it from the toolbox when pressed
                addInputEventListener( new PBasicInputEventHandler() {
                    @Override public void mousePressed( PInputEvent event ) {
                        LWJGLUtils.invoke( new Runnable() {
                            public void run() {
                                toolboxState.thermometerInToolbox.set( false );
                            }
                        } );
                    }
                } );
            }};
            addChild( thermometer );

            final PNode densitySensor = new ZeroOffsetNode( new com.aimxcel.abclearn.platetectonics.control.DensitySensorNode3D.DensitySensorNode2D( kmToViewUnit, tab ) ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setOffset( thermometer.getFullBounds().getMaxX() + INSET, rulerNode2D.getFullBounds().getMaxY() - getFullBounds().getHeight() );

                // make it visible when it is in the toolbox
                toolboxState.densitySensorInToolbox.addObserver( swingObserver( new Runnable() {
                    public void run() {
                        setVisible( toolboxState.densitySensorInToolbox.get() );
                    }
                } ) );

                // remove it from the toolbox when pressed
                addInputEventListener( new PBasicInputEventHandler() {
                    @Override public void mousePressed( PInputEvent event ) {
                        LWJGLUtils.invoke( new Runnable() {
                            public void run() {
                                toolboxState.densitySensorInToolbox.set( false );
                            }
                        } );
                    }
                } );
            }};

            addChild( densitySensor );

            addChild( new PText( Strings.TOOLBOX ) {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
                setFont( PANEL_TITLE_FONT );
                setOffset( rulerNode2D.getFullBounds().getWidth() + 10, 0 ); // TODO: change positioning once we have added other toolbox elements
            }} );
        }} ), tab, tab.getCanvasTransform(), new Property<Vector2D>( new Vector2D() ), tab.mouseEventNotifier );
    }
}