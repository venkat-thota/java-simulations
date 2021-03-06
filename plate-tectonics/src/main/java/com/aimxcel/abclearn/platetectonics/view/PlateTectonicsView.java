package com.aimxcel.abclearn.platetectonics.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.aimxcel.abclearn.platetectonics.model.PlateTectonicsModel;
import com.aimxcel.abclearn.platetectonics.model.Terrain;
import com.aimxcel.abclearn.platetectonics.model.regions.CrossSectionStrip;
import com.aimxcel.abclearn.platetectonics.tabs.PlateTectonicsTab;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.event.UpdateListener;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.lwjgl.GLOptions;
import com.aimxcel.abclearn.lwjgl.GLOptions.RenderPass;
import com.aimxcel.abclearn.lwjgl.nodes.GLNode;
import com.aimxcel.abclearn.lwjgl.shapes.UnitMarker;

public class PlateTectonicsView extends GLNode {

    // keep track of which object corresponds to which node, so we can remove them later
    protected final Map<Object, GLNode> nodeMap = new HashMap<Object, GLNode>();
    protected final Map<CrossSectionStrip, UpdateListener> sortListenerMap = new HashMap<CrossSectionStrip, UpdateListener>();
    private final PlateTectonicsModel model;
    private final PlateTectonicsTab tab;
    private final Property<Boolean> showWater;

    // by default, show water
    public PlateTectonicsView( final PlateTectonicsModel model, final PlateTectonicsTab tab ) {
        this( model, tab, new Property<Boolean>( true ) );
    }

    public PlateTectonicsView( final PlateTectonicsModel model, final PlateTectonicsTab tab, final Property<Boolean> showWater ) {
        this.model = model;
        this.tab = tab;
        this.showWater = showWater;

        // add in the initial main nodes for the model
        for ( final Terrain terrain : model.getTerrains() ) {
            addTerrain( terrain );
        }

        for ( CrossSectionStrip strip : model.getCrossSectionStrips() ) {
            addCrossSectionStrip( strip );
        }

        // handle changes to the model
        model.crossSectionStripAdded.addListener( new VoidFunction1<CrossSectionStrip>() {
            public void apply( CrossSectionStrip strip ) {
                addCrossSectionStrip( strip );
            }
        } );
        model.crossSectionStripRemoved.addListener( new VoidFunction1<CrossSectionStrip>() {
            public void apply( CrossSectionStrip strip ) {
                removeCrossSectionStrip( strip );
            }
        } );
        model.terrainAdded.addListener( new VoidFunction1<Terrain>() {
            public void apply( Terrain terrain ) {
                addTerrain( terrain );
            }
        } );
        model.terrainRemoved.addListener( new VoidFunction1<Terrain>() {
            public void apply( Terrain terrain ) {
                removeTerrain( terrain );
            }
        } );

        // add a marker when debugPing is notified
        model.debugPing.addListener( new VoidFunction1<Vector3F>() {
            public void apply( final Vector3F location ) {
                addChild( new UnitMarker() {{
                    Vector3F viewLocation = tab.getModelViewTransform().transformPosition( PlateTectonicsModel.convertToRadial( location ) );
                    translate( viewLocation.x, viewLocation.y, viewLocation.z );
                    scale( 3 );
                }} );
            }
        } );
    }

    private void addCrossSectionStrip( final CrossSectionStrip strip ) {
        addWrappedChild( strip, new CrossSectionStripNode( tab.getModelViewTransform(), tab.colorMode, strip ) );

        // if this fires, add the node to the front of the list
        UpdateListener sortOrderListener = new UpdateListener() {
            public void update() {
                GLNode node = nodeMap.get( strip );
                if ( node != null && node.getParent() != null ) {
                    removeChild( node );
                    addChild( node );
                }
            }
        };
        strip.moveToFrontNotifier.addUpdateListener( sortOrderListener, false );

        // store this listener for future reference, so we can remove it when the node is removed
        sortListenerMap.put( strip, sortOrderListener );
    }

    private void removeCrossSectionStrip( CrossSectionStrip strip ) {
        removeChild( nodeMap.get( strip ) );
        nodeMap.remove( strip );

        // remove the listener
        strip.moveToFrontNotifier.removeListener( sortListenerMap.get( strip ) );
    }

    private void addTerrain( final Terrain terrain ) {
        addWrappedChild( terrain, new GLNode() {{
            final TerrainNode terrainNode = new TerrainNode( terrain, tab.getModelViewTransform() );
            addChild( terrainNode );

            if ( terrain.hasWater() ) {
                final WaterStripNode waterNode = new WaterStripNode( terrain, model, tab );

                // how to set our visibility of the water
                final SimpleObserver visibilityObserver = new SimpleObserver() {
                    public void update() {
                        if ( showWater.get() && terrain.isWaterValid.get() ) {
                            addChild( waterNode );
                        }
                        else {
                            if ( waterNode.getParent() != null ) {
                                removeChild( waterNode );
                            }
                        }
                    }
                };

                // listen to the properties
                showWater.addObserver( visibilityObserver );
                terrain.isWaterValid.addObserver( visibilityObserver );
                // and stop listening once the terrain is disposed, to prevent memory leaks
                terrain.disposed.addUpdateListener( new UpdateListener() {
                    public void update() {
                        showWater.removeObserver( visibilityObserver );
                        terrain.isWaterValid.removeObserver( visibilityObserver );
                    }
                }, false );
            }
        }} );
    }

    private void removeTerrain( Terrain terrain ) {
        removeChild( nodeMap.get( terrain ) );
        nodeMap.remove( terrain );
    }

    // record the added children in the node map so we can remove them later
    public void addWrappedChild( Object object, GLNode node ) {
        assert nodeMap.get( object ) == null;
        addChild( node );
        nodeMap.put( object, node );
    }

    @Override
    protected void renderChildren( GLOptions options ) {
        // render children with a normal pass
        super.renderChildren( options );

        // then render them with the transparency pass
        GLOptions transparencyOptions = options.getCopy();
        transparencyOptions.renderPass = RenderPass.TRANSPARENCY;

        for ( GLNode child : new ArrayList<GLNode>( getChildren() ) ) {
            child.render( transparencyOptions );
        }
    }
}
