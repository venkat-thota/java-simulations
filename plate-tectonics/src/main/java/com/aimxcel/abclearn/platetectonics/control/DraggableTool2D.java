package com.aimxcel.abclearn.platetectonics.control;

import com.aimxcel.abclearn.platetectonics.model.ToolboxState;

import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2F;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector3F;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;

public interface DraggableTool2D {

    // whether drags should be allowed to start at the specified screen point
    public boolean allowsDrag( Vector2F initialPosition );

    // actually perform a drag movement (view coordinates)
    public void dragDelta( Vector2F delta );

    // pick what part of the toolbox we are!
    public Property<Boolean> getInsideToolboxProperty( ToolboxState toolboxState );

    // when we start the drag, what point (in 3D view coordinates) should the mouse be over?
    public Vector2F getInitialMouseOffset();

    // for sim-sharing messages
    public IUserComponent getUserComponent();

    // where the tool is making a read-out, if applicable
    public Vector3F getSensorModelPosition();

    public Vector3F getSensorViewPosition();

    // for sim-sharing messages
    public ParameterSet getCustomParameters();

    // detach the tool and remove it from use
    public void recycle();
}
