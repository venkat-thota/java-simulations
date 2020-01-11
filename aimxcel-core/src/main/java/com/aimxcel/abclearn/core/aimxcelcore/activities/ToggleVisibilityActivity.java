

package com.aimxcel.abclearn.core.aimxcelcore.activities;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.activities.PActivity;


public class ToggleVisibilityActivity extends PActivity {

    private final PNode node;
    private boolean wasVisible;

    /**
     * Constructor.
     *
     * @param node     the node whose visibility will be toggled
     * @param duration duration of this activity, in milliseconds
     * @param stepRate amount of time that this activity should delay between steps, in milliseconds
     */
    public ToggleVisibilityActivity( PNode node, long duration, long stepRate ) {
        super( duration );
        this.node = node;
        setStepRate( stepRate );
    }

    @Override
    protected void activityStarted() {
        super.activityStarted();
        wasVisible = node.getVisible();
    }

    @Override
    protected void activityStep( long time ) {
        super.activityStep( time );
        node.setVisible( !node.getVisible() );
    }

    @Override
    protected void activityFinished() {
        super.activityFinished();
        node.setVisible( wasVisible );
    }

}
