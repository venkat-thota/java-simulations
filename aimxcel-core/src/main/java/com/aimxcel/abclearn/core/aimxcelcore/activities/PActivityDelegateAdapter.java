 
package com.aimxcel.abclearn.core.aimxcelcore.activities;

import edu.umd.cs.piccolo.activities.PActivity;

/**
 * Adapter pattern for PActivityDelegate.
 */
public class PActivityDelegateAdapter implements PActivity.PActivityDelegate {

    public void activityStarted( PActivity activity ) {
        // Override to change, does nothing by default.
    }

    public void activityStepped( PActivity activity ) {
        // Override to change, does nothing by default.
    }

    public void activityFinished( PActivity activity ) {
        // Override to change, does nothing by default.
    }
}
