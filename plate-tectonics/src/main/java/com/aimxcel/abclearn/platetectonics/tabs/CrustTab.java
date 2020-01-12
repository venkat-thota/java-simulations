package com.aimxcel.abclearn.platetectonics.tabs;

import static com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings.CONTINENTAL_CRUST;
import static com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings.OCEANIC_CRUST;

import java.awt.*;
import java.util.ArrayList;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing;
import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;
import com.aimxcel.abclearn.platetectonics.control.DraggableTool2D;
import com.aimxcel.abclearn.platetectonics.control.LegendPanel;
import com.aimxcel.abclearn.platetectonics.control.MyCrustPanel;
import com.aimxcel.abclearn.platetectonics.control.ResetPanel;
import com.aimxcel.abclearn.platetectonics.control.ViewOptionsPanel;
import com.aimxcel.abclearn.platetectonics.control.ZoomPanel;
import com.aimxcel.abclearn.platetectonics.model.CrustModel;
import com.aimxcel.abclearn.platetectonics.model.PlateTectonicsModel;
import com.aimxcel.abclearn.platetectonics.view.ColorMode;
import com.aimxcel.abclearn.platetectonics.view.PlateTectonicsView;
import com.aimxcel.abclearn.platetectonics.view.labels.RangeLabelNode;

import com.aimxcel.abclearn.common.aimxcelcommon.math.Bounds3F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.UpdateListener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.Function1;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ControlPanelNode;
import com.aimxcel.abclearn.lwjgl.LWJGLCanvas;
import com.aimxcel.abclearn.lwjgl.nodes.GLNode;
import com.aimxcel.abclearn.lwjgl.nodes.OrthoCoreNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;

public class CrustTab extends PlateTectonicsTab {

    // relative scale multiplier of how large items at the origin appear to be
    private Property<Float> scaleProperty = new Property<Float>( 1f );

    private final Property<Boolean> showLabels = new Property<Boolean>( false );
    private OrthoCoreNode optionsCoreNode;
    private MyCrustPanel myCrustPanel;
    private ZoomPanel zoomPanel;

    public CrustTab( LWJGLCanvas canvas ) {
        super( canvas, Strings.CRUST_TAB, 2 ); // 0.5 km => 1 distance in view

        zoomRatio.addObserver( new SimpleObserver() {
            public void update() {
                scaleProperty.set( getSceneDistanceZoomFactor() );
            }
        } );
    }

    @Override public void initialize() {
        super.initialize();

        getClock().start();

        // grid centered X, with front Z at 0
        Bounds3F bounds = Bounds3F.fromMinMax( -1500000, 1500000,
                                               -150000, 15000,
                                               -2000000, 0 );

        // create the model and terrain
        setModel( new CrustModel( bounds ) );

        sceneLayer.addChild( new PlateTectonicsView( getModel(), this ) );

        final Function1<Vector3F, Vector3F> flatModelToView = new Function1<Vector3F, Vector3F>() {
            public Vector3F apply( Vector3F v ) {
                return getModelViewTransform().transformPosition( PlateTectonicsModel.convertToRadial( v ) );
            }
        };

        GLNode layerLabels = new GLNode() {{
            showLabels.addObserver( new SimpleObserver() {
                public void update() {
                    setVisible( showLabels.get() );
                }
            } );
        }};
        sceneLayer.addChild( layerLabels );


        // TODO: since in the other tab labels are handled in the model, should we consolidate them and handle these labels in CrustModel?

        /*---------------------------------------------------------------------------*
        * cross-section labels
        *----------------------------------------------------------------------------*/

        // crust label
        final int crustLabelX = -10000;
        layerLabels.addChild( new RangeLabelNode( null, new Property<Vector3F>( new Vector3F() ) {{
            beforeFrameRender.addUpdateListener( new UpdateListener() {
                public void update() {
                    set( flatModelToView.apply( new Vector3F( crustLabelX, (float) getCrustModel().getCenterCrustElevation(), 0 ) ) );
                }
            }, true );
        }}, new Property<Vector3F>( new Vector3F() ) {{
            beforeFrameRender.addUpdateListener( new UpdateListener() {
                public void update() {
                    set( flatModelToView.apply( new Vector3F( crustLabelX, (float) getCrustModel().getCenterCrustBottomY(), 0 ) ) );
                }
            }, true );
        }}, Strings.CRUST, scaleProperty, colorMode, true
        ) );

        final Property<Vector3F> upperMantleTop = new Property<Vector3F>( new Vector3F() ) {{
            beforeFrameRender.addUpdateListener( new UpdateListener() {
                public void update() {
                    set( flatModelToView.apply( new Vector3F( 0, (float) getCrustModel().getCenterCrustBottomY(), 0 ) ) );
                }
            }, true );
        }};
        final Property<Vector3F> upperMantleBottom = new Property<Vector3F>( flatModelToView.apply( new Vector3F( 0, CrustModel.UPPER_LOWER_MANTLE_BOUNDARY_Y, 0 ) ) );

        // mantle
        layerLabels.addChild( new RangeLabelNode(
                null,
                upperMantleTop,
                upperMantleBottom,
                Strings.MANTLE, scaleProperty,
                colorMode, true,
                getLabelPosition( upperMantleTop, upperMantleBottom, scaleProperty )
        ) );

        final int lowerMantleLabelX = 150000;
        Property<Vector3F> lowerMantleTop = new Property<Vector3F>( flatModelToView.apply( new Vector3F( lowerMantleLabelX, CrustModel.UPPER_LOWER_MANTLE_BOUNDARY_Y, 0 ) ) );
        Property<Vector3F> lowerMantleBottom = new Property<Vector3F>( flatModelToView.apply( new Vector3F( lowerMantleLabelX, CrustModel.MANTLE_CORE_BOUNDARY_Y, 0 ) ) );

        // lower mantle
        layerLabels.addChild( new RangeLabelNode(
                null,
                lowerMantleTop,
                lowerMantleBottom,
                Strings.LOWER_MANTLE, scaleProperty,
                colorMode, true,
                getLabelPosition( lowerMantleTop, lowerMantleBottom, scaleProperty )
        ) );

        final int outerCoreLabelX = -250000;
        Property<Vector3F> outerCoreTop = new Property<Vector3F>( flatModelToView.apply( new Vector3F( outerCoreLabelX, CrustModel.MANTLE_CORE_BOUNDARY_Y, 0 ) ) );
        Property<Vector3F> outerCoreBottom = new Property<Vector3F>( flatModelToView.apply( new Vector3F( outerCoreLabelX, CrustModel.INNER_OUTER_CORE_BOUNDARY_Y, 0 ) ) );

        // outer core
        layerLabels.addChild( new RangeLabelNode(
                null,
                outerCoreTop,
                outerCoreBottom,
                Strings.OUTER_CORE, scaleProperty,
                colorMode, false,
                getLabelPosition( outerCoreTop, outerCoreBottom, scaleProperty )
        ) );

        Property<Vector3F> innerCoreTop = new Property<Vector3F>( flatModelToView.apply( new Vector3F( 250000, CrustModel.INNER_OUTER_CORE_BOUNDARY_Y, 0 ) ) );
        Property<Vector3F> innerCoreBottom = new Property<Vector3F>( flatModelToView.apply( new Vector3F( 250000, -PlateTectonicsModel.EARTH_RADIUS, 0 ) ) );

        // inner core
        layerLabels.addChild( new RangeLabelNode(
                null,
                innerCoreTop,
                innerCoreBottom,
                Strings.INNER_CORE, scaleProperty,
                colorMode, false,
                getLabelPosition( innerCoreTop, innerCoreBottom, scaleProperty )
        ) );

        /*---------------------------------------------------------------------------*
        * my crust
        *----------------------------------------------------------------------------*/
        myCrustPanel = new MyCrustPanel( getCrustModel() );
        addGuiNode( new OrthoCoreNode( new ControlPanelNode( myCrustPanel ), CrustTab.this, getCanvasTransform(), new Property<Vector2D>( new Vector2D() ), mouseEventNotifier ) {{
            // layout the panel if its size changes (and on startup)
            canvasSize.addObserver( new SimpleObserver() {
                public void update() {
                    position.set( new Vector2D(
                            Math.ceil( ( getStageSize().width - getComponentWidth() ) / 2 ), // center horizontally
                            10 ) ); // offset from top
                }
            } );

            updateOnEvent( beforeFrameRender );

            zoomRatio.addObserver( new SimpleObserver() {
                public void update() {
                    setVisible( zoomRatio.get() == 1 );
                }
            } );
        }} );

        /*---------------------------------------------------------------------------*
        * zoom control
        *----------------------------------------------------------------------------*/
        zoomPanel = new ZoomPanel( zoomRatio );
        addGuiNode( new OrthoCoreNode( new ControlPanelNode( zoomPanel ), CrustTab.this, getCanvasTransform(), new Property<Vector2D>( new Vector2D() ), mouseEventNotifier ) {{
            // top right
            canvasSize.addObserver( new SimpleObserver() {
                public void update() {
                    position.set( new Vector2D(
                            getStageSize().width - getComponentWidth() - 10,
                            10
                    ) );
                }
            } );

            updateOnEvent( beforeFrameRender );
        }} );

        /*---------------------------------------------------------------------------*
         * options panel
         *----------------------------------------------------------------------------*/
        optionsCoreNode = new OrthoCoreNode(
                new ControlPanelNode( new ViewOptionsPanel( showLabels, colorMode ) ),
                CrustTab.this, getCanvasTransform(),
                new Property<Vector2D>( new Vector2D() ), mouseEventNotifier ) {{
            canvasSize.addObserver( new SimpleObserver() {
                public void update() {
                    position.set( new Vector2D( getStageSize().width - getComponentWidth() - 10,
                                                getStageSize().height - getComponentHeight() - 10 ) );
                }
            } );
            updateOnEvent( beforeFrameRender );
        }};
        addGuiNode( optionsCoreNode );

        final OrthoCoreNode resetPanelNode = new OrthoCoreNode( new ResetPanel( this, new Runnable() {
            public void run() {
                resetAll();
            }
        } ), this, getCanvasTransform(), new Property<Vector2D>( new Vector2D() ), mouseEventNotifier ) {{
            onResize.addUpdateListener( new UpdateListener() {
                public void update() {

                    final double toolboxRightEdge = toolboxNode.position.get().getX() + toolboxNode.getComponentWidth();

                    position.set( new Vector2D(
                            (int) ( toolboxRightEdge + 10 ),
                            getStageSize().height - getComponentHeight() - 25 ) ); // extra padding
                }
            }, true );
            updateOnEvent( beforeFrameRender );
        }};
        addGuiNode( resetPanelNode );

        /*---------------------------------------------------------------------------*
        * legend
        *----------------------------------------------------------------------------*/

        addGuiNode( new LegendCoreNode( ColorMode.DENSITY, (float) optionsCoreNode.position.get().getX() ) );
        addGuiNode( new LegendCoreNode( ColorMode.TEMPERATURE, (float) optionsCoreNode.position.get().getX() ) );

        /*---------------------------------------------------------------------------*
        * crust labels labels
        *----------------------------------------------------------------------------*/

        // "oceanic crust" label
        guiLayer.addChild(
                new OrthoCoreNode( new PText( OCEANIC_CRUST ) {{
                    setFont( new AimxcelFont( 16, true ) );
                }},
                                      this,
                                      getCanvasTransform(),
                                      new Property<Vector2D>( new Vector2D( 30, getStageSize().getHeight() * 0.38 ) ),
                                      mouseEventNotifier ) {{
                    zoomRatio.addObserver( new SimpleObserver() {
                        public void update() {
                            setVisible( zoomRatio.get() == 1 );
                        }
                    } );
                }} );

        // "continental crust" label
        guiLayer.addChild( new OrthoCoreNode( new PText( CONTINENTAL_CRUST ) {{
            setFont( new AimxcelFont( 16, true ) );
        }}, this, getCanvasTransform(), new Property<Vector2D>( new Vector2D() ), mouseEventNotifier ) {{
            // TODO: improve positioning to handle i18n?
            position.set( new Vector2D( getStageSize().getWidth() - getComponentWidth() - 30,
                                        getStageSize().getHeight() * 0.38 ) );
            zoomRatio.addObserver( new SimpleObserver() {
                public void update() {
                    setVisible( zoomRatio.get() == 1 );
                }
            } );
        }} );

        guiLayer.addChild( createFPSReadout( Color.BLACK ) );

        // if we zoom in such a way that a tool is not visible, move it into the toolbox
        zoomRatio.addObserver( new SimpleObserver() {
            public void update() {
                for ( GLNode glNode : new ArrayList<GLNode>( toolLayer.getChildren() ) ) {
                    DraggableTool2D tool = (DraggableTool2D) glNode;
                    if ( !isToolInBounds( tool ) ) {
                        toolDragHandler.putToolBackInToolbox( tool );
                    }
                }
            }
        } );
    }

    @Override public void resetAll() {
        super.resetAll();

        showLabels.reset();
        myCrustPanel.resetAll();
        zoomPanel.resetAll();
    }

    @Override public boolean isWaterVisible() {
        // always show water on this tab
        return true;
    }

    public CrustModel getCrustModel() {
        return (CrustModel) getModel();
    }

    public IUserComponent getUserComponent() {
        return PlateTectonicsSimSharing.UserComponents.crustTab;
    }

    private class LegendCoreNode extends OrthoCoreNode {
        public LegendCoreNode( final ColorMode myColorMode, final float optionsRightX ) {
            super( new ControlPanelNode( new LegendPanel( myColorMode ) ), CrustTab.this, CrustTab.this.getCanvasTransform(), new Property<Vector2D>( new Vector2D() ), CrustTab.this.mouseEventNotifier );

            // NOTE: no updating is required on this node, since it doesn't change
            canvasSize.addObserver( new SimpleObserver() {
                public void update() {
                    position.set( new Vector2D( optionsRightX - getComponentWidth() - 20,
                                                getStageSize().height - getComponentHeight() - 10 ) );
                }
            } );
            colorMode.addObserver( new SimpleObserver() {
                public void update() {
                    setVisible( colorMode.get() == myColorMode );
                }
            } );
        }
    }
}
