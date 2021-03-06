
package com.aimxcel.abclearn.core.aimxcelcore.simsharing;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.components.SimSharingDragPoints;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserAction;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponentType;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.Parameter;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.aimxcel2dcore.event.PDragSequenceEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;


public class SimSharingDragHandler extends PDragSequenceEventHandler {

    public interface DragFunction {
        public void apply( IUserComponent userComponent, IUserComponentType componentType, IUserAction action, ParameterSet parameters, PInputEvent event );
    }

    protected final IUserComponent userComponent;
    protected final IUserComponentType componentType;
    private final SimSharingDragPoints dragPoints; // canvas coordinates, accumulated during a drag sequence
    private DragFunction startDragFunction, dragFunction, endDragFunction; // functions called for various events

    //Auxiliary constructor for sprites, provided for convenience because many SimSharingDragHandler target PNodes are sprites
    public SimSharingDragHandler( IUserComponent userComponent, boolean sendMessages ) {
        this( userComponent, UserComponentTypes.sprite, sendMessages );
    }

    // Sends a message on startDrag and endDrag, but not drag
    public SimSharingDragHandler( IUserComponent userComponent, IUserComponentType componentType ) {
        this( userComponent, componentType, false );
    }

    // Sends a message on drag if reportDrag=true
    public SimSharingDragHandler( IUserComponent userComponent, IUserComponentType componentType, final boolean sendDragMessages ) {

        this.userComponent = userComponent;
        this.componentType = componentType;
        this.dragPoints = new SimSharingDragPoints();

        // default functions
        this.startDragFunction = new DragFunction() {
            public void apply( IUserComponent userComponent, IUserComponentType componentType, IUserAction action, ParameterSet parameterSet, PInputEvent event ) {
                SimSharingManager.sendUserMessage( userComponent, componentType, action, parameterSet );
            }
        };
        this.dragFunction = new DragFunction() {
            public void apply( IUserComponent userComponent, IUserComponentType componentType, IUserAction action, ParameterSet parameterSet, PInputEvent event ) {
                if ( sendDragMessages ) {
                    SimSharingManager.sendUserMessage( userComponent, componentType, action, parameterSet );
                }
            }
        };
        this.endDragFunction = new DragFunction() {
            public void apply( IUserComponent userComponent, IUserComponentType componentType, IUserAction action, ParameterSet parameterSet, PInputEvent event ) {
                SimSharingManager.sendUserMessage( userComponent, componentType, action, parameterSet );
            }
        };
    }

    @Override protected void startDrag( final PInputEvent event ) {
        clearDragPoints();
        addDragPoint( event );
        startDragFunction.apply( userComponent, componentType, UserActions.startDrag, getStartDragParameters( event ), event );
        super.startDrag( event );
    }

    @Override protected void drag( PInputEvent event ) {
        addDragPoint( event );
        dragFunction.apply( userComponent, componentType, UserActions.drag, getDragParameters( event ), event );
        super.drag( event );
    }

    @Override protected void endDrag( PInputEvent event ) {
        addDragPoint( event );
        endDragFunction.apply( userComponent, componentType, UserActions.endDrag, getEndDragParameters( event ), event );
        clearDragPoints();
        super.endDrag( event );
    }

    // Call this to replace the sim-sharing function that is called on startDrag.
    public void setStartDragFunction( DragFunction f ) {
        startDragFunction = f;
    }

    // Call this to replace the sim-sharing function that is called on drag.
    public void setDragFunction( DragFunction f ) {
        dragFunction = f;
    }

    // Call this to replace the sim-sharing function that is called on endDrag.
    public void setEndDragFunction( DragFunction f ) {
        endDragFunction = f;
    }

    // Gets parameters for startDrag. Override to provide different parameters, chain with super to add parameters.
    protected ParameterSet getStartDragParameters( PInputEvent event ) {
        return getParametersForAllEvents( event );
    }

    // Gets parameters for drag. Override to provide different parameters, chain with super to add parameters.
    protected ParameterSet getDragParameters( PInputEvent event ) {
        return getParametersForAllEvents( event );
    }

    // Gets parameters for endDrag. Override to provide different parameters, chain with super to add parameters.
    protected ParameterSet getEndDragParameters( PInputEvent event ) {
        return getParametersForAllEvents( event ).with( dragPoints.getParameters() ); // includes summary of drag points
    }

    // Return parameters that are used by default for startDrag, endDrag, and drag
    protected ParameterSet getParametersForAllEvents( PInputEvent event ) {
        return new ParameterSet().with( getXParameter( event ) ).with( getYParameter( event ) );
    }

    private void addDragPoint( PInputEvent event ) {
        dragPoints.add( getPosition( event ) );
    }

    private void clearDragPoints() {
        dragPoints.clear();
    }

    private static Parameter getXParameter( PInputEvent event ) {
        return new Parameter( ParameterKeys.canvasPositionX, getPosition( event ).getX() );
    }

    private static Parameter getYParameter( PInputEvent event ) {
        return new Parameter( ParameterKeys.canvasPositionY, getPosition( event ).getY() );
    }

    // Gets the interpretation of the position used throughout this class.
    private static Point2D getPosition( PInputEvent event ) {
        return event.getCanvasPosition();
    }
}