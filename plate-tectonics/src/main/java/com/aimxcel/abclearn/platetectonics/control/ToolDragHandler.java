// Copyright 2002-2011, University of Colorado
package com.aimxcel.abclearn.platetectonics.control;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.Parameter;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.drag;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.endDrag;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.startDrag;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsSimSharing.UserActions;
import com.aimxcel.abclearn.platetectonics.model.ToolboxState;

/**
 * Handles tool dragging, so that we don't clutter the module/tab with this state information
 */
public class ToolDragHandler {

    private boolean dragging = false;
    private DraggableTool2D tool;
    private Vector2F lastPosition;

    private ToolboxState toolboxState;

    public ToolDragHandler( ToolboxState toolboxState ) {
        this.toolboxState = toolboxState;
    }

    public void mouseDownOnTool( DraggableTool2D tool, Vector2F viewPosition ) {
        if ( tool.allowsDrag( viewPosition ) ) {
            startDragging( tool, viewPosition );
        }
    }

    public void startDragging( DraggableTool2D tool, Vector2F viewPosition ) {
        this.tool = tool;
        lastPosition = new Vector2F( viewPosition );
        dragging = true;

        /// send an empty drag delta to hopefully synchronize any model
        tool.dragDelta( new Vector2F( 0, 0 ) );

        SimSharingManager.sendUserMessage( tool.getUserComponent(), UserComponentTypes.sprite, startDrag, getToolLocationParameterSet( tool ) );
    }

    public void mouseUp( boolean isLocationUnacceptable ) {
        if ( dragging ) {
            SimSharingManager.sendUserMessage( tool.getUserComponent(), UserComponentTypes.sprite, endDrag, getToolLocationParameterSet( tool ) );
            if ( isLocationUnacceptable ) {
                putToolBackInToolbox( tool );
            }
        }
        dragging = false;
    }

    public void mouseMove( Vector2F viewPosition ) {
        if ( dragging ) {
            tool.dragDelta( viewPosition.minus( lastPosition ) );
            lastPosition = viewPosition;

            SimSharingManager.sendUserMessage( tool.getUserComponent(), UserComponentTypes.sprite, drag, getToolLocationParameterSet( tool ) );
        }
    }

    public void putToolBackInToolbox( DraggableTool2D tool ) {
        // get rid of the tool
        tool.recycle();

        // mark the toolbox as having the tool again
        tool.getInsideToolboxProperty( toolboxState ).set( true );

        SimSharingManager.sendUserMessage( tool.getUserComponent(), UserComponentTypes.sprite, UserActions.putBackInToolbox );
    }

    public boolean isDragging() {
        return dragging;
    }

    public DraggableTool2D getDraggedTool() {
        return tool;
    }

    private static ParameterSet getToolLocationParameterSet( DraggableTool2D tool ) {
        return new ParameterSet( new Parameter[]{
                new Parameter( ParameterKeys.x, tool.getSensorModelPosition().x ),
                new Parameter( ParameterKeys.y, tool.getSensorModelPosition().y ),
                new Parameter( ParameterKeys.z, tool.getSensorModelPosition().z )
        } ).with( tool.getCustomParameters() );
    }
}
