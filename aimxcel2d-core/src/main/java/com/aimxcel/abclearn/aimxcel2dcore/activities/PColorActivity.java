package com.aimxcel.abclearn.aimxcel2dcore.activities;

import java.awt.Color;
public class PColorActivity extends PInterpolatingActivity {

    private Color source;
    private Color destination;
    private final Target target;

    /**
     * <b>Target</b> Objects that want their color to be set by the color
     * activity must implement this interface.
     */
    public interface Target {

        /**
         * This will be called by the color activity for each new interpolated
         * color that it computes while it is stepping.
         * 
         * @param color the color to assign to the target
         */
        void setColor(Color color);

        /**
         * This method is called right before the color activity starts. That
         * way an object's color is always animated from its current color.
         * 
         * @return the target's current color.
         */
        Color getColor();
    }

    /**
     * Constructs a color activity for the given target that will animate for
     * the duration provided at an interval of stepRate.
     * 
     * Destination color must be assigned later.
     * 
     * @param duration duration in milliseconds that the animation should last
     * @param stepRate the time between interpolations
     * @param aTarget the target onto which the animation is being performed
     */
    public PColorActivity(final long duration, final long stepRate, final Target aTarget) {
        this(duration, stepRate, aTarget, null);
    }

    /**
     * Constructs a color activity for the given target that will animate for
     * the duration provided at an interval of stepRate from the target's
     * starting color to the destination color.
     * 
     * @param duration duration in milliseconds that the animation should last
     * @param stepRate the time between interpolations
     * @param aTarget the target onto which the animation is being performed
     * @param aDestination the color to which the animation is aiming at
     */
    public PColorActivity(final long duration, final long stepRate, final Target aTarget, final Color aDestination) {
        this(duration, stepRate, 1, PInterpolatingActivity.SOURCE_TO_DESTINATION, aTarget, aDestination);
    }

    /**
     * Create a new PColorActivity.
     * 
     * @param duration the length of one loop of the activity
     * @param stepRate the amount of time between steps of the activity
     * @param loopCount number of times the activity should reschedule itself
     * @param mode defines how the activity interpolates between states
     * @param aTarget the object that the activity will be applied to and where
     *            the source state will be taken from.
     * @param aDestination the destination color state
     */
    public PColorActivity(final long duration, final long stepRate, final int loopCount, final int mode,
            final Target aTarget, final Color aDestination) {
        super(duration, stepRate, loopCount, mode);
        target = aTarget;
        destination = aDestination;
    }

    /**
     * Returns true since all PColorActivities animate the scene.
     * 
     * @return always returns true
     */
    protected boolean isAnimation() {
        return true;
    }

    /**
     * Return the final color that will be set on the color activities target
     * when the activity stops stepping.
     * 
     * @return the final color for this color activity
     */
    public Color getDestinationColor() {
        return destination;
    }

    /**
     * Set the final color that will be set on the color activities target when
     * the activity stops stepping.
     * 
     * @param newDestination to animate towards
     */
    public void setDestinationColor(final Color newDestination) {
        destination = newDestination;
    }

    /**
     * Overrides it's parent to ensure that the source color is the color of the
     * node being animated.
     */
    protected void activityStarted() {
        if (getFirstLoop()) {
            source = target.getColor();
        }
        super.activityStarted();
    }

    /**
     * Interpolates the target node's color by mixing the source color and the
     * destination color.
     * 
     * @param zeroToOne 0 = all source color, 1 = all destination color
     */
    public void setRelativeTargetValue(final float zeroToOne) {
        super.setRelativeTargetValue(zeroToOne);
        final float red = source.getRed() + zeroToOne * (destination.getRed() - source.getRed());
        final float green = source.getGreen() + zeroToOne * (destination.getGreen() - source.getGreen());
        final float blue = source.getBlue() + zeroToOne * (destination.getBlue() - source.getBlue());
        final float alpha = source.getAlpha() + zeroToOne * (destination.getAlpha() - source.getAlpha());
        target.setColor(new Color((int) red, (int) green, (int) blue, (int) alpha));
    }
}
