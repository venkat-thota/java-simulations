package com.aimxcel.abclearn.aimxcel2dextra.activities;

import com.aimxcel.abclearn.aimxcel2dcore.activities.PInterpolatingActivity;


public abstract class PPathActivity extends PInterpolatingActivity {

    /**
     * The "knots" that define this path's activity timing through its activity
     * and should be an monotonously increasing array starting where each value
     * is >=0 and ending at 1f.
     */
    protected float[] knots;

    /**
     * Constructs a PPathActivity that will last the specified duration, will
     * animate every stepRate and will progress according to the knots provided.
     * 
     * @param duration duration in milliseconds that this activity should last
     * @param stepRate interval in milliseconds between animation steps
     * @param knots array defining the speed of the animation alongs it's
     *            animation
     */
    public PPathActivity(final long duration, final long stepRate, final float[] knots) {
        this(duration, stepRate, 0, PInterpolatingActivity.SOURCE_TO_DESTINATION, knots);
    }

    /**
     * Constructs a PPathActivity that will repeat the specified number of
     * times, last the specified duration, will animate every stepRate and will
     * progress according to the knots provided.
     * 
     * @param duration duration in milliseconds that this activity should last
     * @param stepRate interval in milliseconds between animation steps
     * @param knots array defining the speed of the animation alongs it's
     *            animation
     * @param loopCount # of times activity should repeat
     * @param mode controls easing of the activity
     */
    public PPathActivity(final long duration, final long stepRate, final int loopCount, final int mode,
            final float[] knots) {
        super(duration, stepRate, loopCount, mode);
        setKnots(knots);
    }

    /**
     * Returns the number of knots that define the timing of this activity.
     * 
     * @return # of knots
     */
    public int getKnotsLength() {
        return knots.length;
    }

    /**
     * Changes the knots that define the timing of this activity.
     * 
     * @param newKnots the new knots to assign to this activity
     */
    public void setKnots(final float[] newKnots) {
        if (newKnots == null) {
            this.knots = null;
        }
        else {
            this.knots = (float[]) newKnots.clone();
        }
    }

    /**
     * Return the knots that define the timing of this activity.
     * 
     * @return new knots
     */
    public float[] getKnots() {
        if (knots == null) {
            return null;
        }
        return (float[]) knots.clone();
    }

    /**
     * Changes the knot at the given index.
     * 
     * @param index index of knot to change
     * @param knot new value to assign to the knot
     */
    public void setKnot(final int index, final float knot) {
        knots[index] = knot;
    }

    /**
     * Returns the value of the knot at the given index.
     * 
     * @param index index of desired knot
     * @return value of knot at given index
     */
    public float getKnot(final int index) {
        return knots[index];
    }

    /**
     * Sets the target's value taking knot timing into account.
     * 
     * @param zeroToOne how much of this activity has elapsed 0=none,
     *            1=completed
     */
    public void setRelativeTargetValue(final float zeroToOne) {
        int currentKnotIndex = 0;

        while (zeroToOne > knots[currentKnotIndex]) {
            currentKnotIndex++;
        }

        int startKnot = currentKnotIndex - 1;
        int endKnot = currentKnotIndex;

        if (startKnot < 0) {
            startKnot = 0;
        }
        if (endKnot > getKnotsLength() - 1) {
            endKnot = getKnotsLength() - 1;
        }

        final float currentRange = knots[endKnot] - knots[startKnot];
        final float currentPointOnRange = zeroToOne - knots[startKnot];
        float normalizedPointOnRange = currentPointOnRange;

        if (currentRange != 0) {
            normalizedPointOnRange = currentPointOnRange / currentRange;
        }

        setRelativeTargetValue(normalizedPointOnRange, startKnot, endKnot);
    }

    /**
     * An abstract method that allows subclasses to define what target value
     * matches the given progress and knots.
     * 
     * @param zeroToOne how far between the knots the activity is
     * @param startKnot knot that defines the start of this particular interpolation
     * @param endKnot knot that defines the end of this particular interpolation
     */
    public abstract void setRelativeTargetValue(float zeroToOne, int startKnot, int endKnot);
}
