
package com.aimxcel.abclearn.aimxcel2dcore.activities;

import java.awt.geom.AffineTransform;

import com.aimxcel.abclearn.aimxcel2dcore.util.PAffineTransform;


public class PTransformActivity extends PInterpolatingActivity {
    private static final PAffineTransform STATIC_TRANSFORM = new PAffineTransform();

    private final double[] source;
    private double[] destination;
    private final Target target;

    /**
     * <b>Target</b> Objects that want to get transformed by the transform
     * activity must implement this interface. See PNode.animateToTransform()
     * for one way to do this.
     */
    public interface Target {

        /**
         * This will be called by the transform activity for each new transform
         * that it computes while it is stepping.
         * 
         * @param aTransform the transform to be applied to the target.
         */
        void setTransform(AffineTransform aTransform);

        /**
         * This method is called right before the transform activity starts.
         * That way an object is always animated from its current position.
         * 
         * @param aSource array to be populated with the target's gurrent matrix
         */
        void getSourceMatrix(double[] aSource);
    }

    /**
     * Constructs a transform activity that will last for the specified
     * duration, will update at the given step rate and will be applied to the
     * target.
     * 
     * Requires that the developer follow up with a setDestinationTransform
     * call, otherwise the transition is undefined.
     * 
     * @param duration duration in milliseconds of the entire activity
     * @param stepRate interval in milliseconds between successive animation
     *            steps
     * @param target the target of the activity
     */
    public PTransformActivity(final long duration, final long stepRate, final Target target) {
        this(duration, stepRate, target, null);
    }

    /**
     * Constructs a activity that will change the target's transform in the
     * destination transform. It will last for the specified duration, will
     * update at the given step rate.
     * 
     * @param duration duration in milliseconds of the entire activity
     * @param stepRate interval in milliseconds between successive animation
     *            steps
     * @param target the target of the activity
     * @param destination transform that the target will be after the ativity is
     *            finished
     */

    public PTransformActivity(final long duration, final long stepRate, final Target target,
            final AffineTransform destination) {
        this(duration, stepRate, 1, PInterpolatingActivity.SOURCE_TO_DESTINATION, target, destination);
    }

    /**
     * Create a new PTransformActivity.
     * <P>
     * 
     * @param duration the length of one loop of the activity
     * @param stepRate the amount of time between steps of the activity
     * @param loopCount number of times the activity should reschedule itself
     * @param mode defines how the activity interpolates between states
     * @param target the object that the activity will be applied to and where
     *            the source state will be taken from.
     * @param destination the destination color state
     */
    public PTransformActivity(final long duration, final long stepRate, final int loopCount, final int mode,
            final Target target, final AffineTransform destination) {
        super(duration, stepRate, loopCount, mode);
        source = new double[6];
        this.destination = new double[6];
        this.target = target;
        if (destination != null) {
            destination.getMatrix(this.destination);
        }
    }

    /**
     * Whether each step invalidates paint.
     * 
     * @return true since a node transform affects it's node's display
     */
    protected boolean isAnimation() {
        return true;
    }

    /**
     * Return the final transform that will be set on the transform activities
     * target when the transform activity stops stepping.
     * 
     * @return returns the final transform as an array of doubles
     */
    public double[] getDestinationTransform() {
        if (destination == null) {
            return null;
        }
        else {
            return (double[]) destination.clone();
        }
    }

    /**
     * Set the final transform that will be set on the transform activities
     * target when the transform activity stops stepping.
     * 
     * @param newDestination an array of doubles representing the destination
     *            transform
     */
    public void setDestinationTransform(final double[] newDestination) {
        if (newDestination == null) {
            destination = null;
        }
        else {
            destination = (double[]) newDestination.clone();
        }
    }

    /**
     * Is invoked when the activity is started. Ensures that setTransform is
     * called on the target even before the first step.
     */
    protected void activityStarted() {
        if (getFirstLoop()) {
            target.getSourceMatrix(source);
        }
        super.activityStarted();
    }

    /**
     * Set's the target value to be the interpolation between the source and
     * destination transforms.
     * 
     * A value of 0 for zeroToOne means that the target should have the source
     * transform. A value of 1 for zeroToOne means that the target should have
     * the destination transform.
     * 
     * @param zeroToOne how far along the activity has progressed. 0 = not at
     *            all, 1 = completed
     */
    public void setRelativeTargetValue(final float zeroToOne) {
        super.setRelativeTargetValue(zeroToOne);

        STATIC_TRANSFORM.setTransform(source[0] + zeroToOne * (destination[0] - source[0]), source[1] + zeroToOne
                * (destination[1] - source[1]), source[2] + zeroToOne * (destination[2] - source[2]), source[3]
                + zeroToOne * (destination[3] - source[3]), source[4] + zeroToOne * (destination[4] - source[4]),
                source[5] + zeroToOne * (destination[5] - source[5]));

        target.setTransform(STATIC_TRANSFORM);
    }
}
