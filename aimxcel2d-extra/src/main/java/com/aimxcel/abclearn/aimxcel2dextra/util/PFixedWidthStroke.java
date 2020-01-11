
package com.aimxcel.abclearn.aimxcel2dextra.util;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.io.ObjectStreamException;
import java.io.Serializable;


public class PFixedWidthStroke extends PSemanticStroke implements Serializable {

    private static final float DEFAULT_MITER_LIMIT = 10.0f;

    private static final BasicStroke DEFAULT_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_MITER, DEFAULT_MITER_LIMIT, null, 0.0f);

    private static final long serialVersionUID = 1L;

    // avoid repeated cloning:
    private final transient float[] dash;

    // avoid repeated instantiations:
    private final transient float[] tmpDash;

    /**
     * Constructs a simple PFixedWidthStroke with the default stroke.
     */
    public PFixedWidthStroke() {
        this(DEFAULT_STROKE);
    }

    /**
     * Making this constructor public would break encapsulation. Users don't
     * need to know that they are dealing with an adapter to an underlying
     * stroke.
     * 
     * @param stroke stroke being used by this PFixedWithStroke
     */
    private PFixedWidthStroke(final BasicStroke stroke) {
        super(stroke);
        dash = stroke.getDashArray();
        if (dash == null) {
            tmpDash = null;
        }
        else {
            tmpDash = new float[dash.length];
        }
    }

    /**
     * Constructs a simple PFixedWidthStroke with the width provided.
     * 
     * @param width desired width of the stroke
     */
    public PFixedWidthStroke(final float width) {
        this(width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, DEFAULT_MITER_LIMIT, null, 0.0f);
    }

    /**
     * Constructs a PFixedWidthStroke with the stroke properties provided.
     * 
     * @param width width of stroke
     * @param cap cap to use in stroke
     * @param join join to use in stroke
     */
    public PFixedWidthStroke(final float width, final int cap, final int join) {
        this(width, cap, join, DEFAULT_MITER_LIMIT, null, 0.0f);
    }

    /**
     * Constructs a PFixedWidthStroke with the stroke properties provided.
     * 
     * @param width width of stroke
     * @param cap cap to use in stroke
     * @param join join to use in stroke
     * @param miterlimit miter limit of stroke
     */
    public PFixedWidthStroke(final float width, final int cap, final int join, final float miterlimit) {
        this(width, cap, join, miterlimit, null, 0.0f);
    }

    /**
     * Constructs a PFixedWidthStroke with the stroke properties provided.
     * 
     * @param width width of stroke
     * @param cap cap to use in stroke
     * @param join join to use in stroke
     * @param miterlimit miter limit of stroke
     * @param dash array of dash lengths
     * @param dashPhase phase to use when rendering dashes
     */
    public PFixedWidthStroke(final float width, final int cap, final int join, final float miterlimit,
            final float[] dash, final float dashPhase) {
        this(new BasicStroke(width, cap, join, miterlimit, dash, dashPhase));
    }

    /**
     * Throws an exception since PFixedWidthStrokes are not serializable.
     * 
     * @return never returns anything
     */
    public Object clone() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    /**
     * Returns the array representing the lengths of the dash segments.
     * Alternate entries in the array represent the user space lengths of the
     * opaque and transparent segments of the dashes. As the pen moves along the
     * outline of the Shape to be stroked, the user space distance that the pen
     * travels is accumulated. The distance value is used to index into the dash
     * array. The pen is opaque when its current cumulative distance maps to an
     * even element of the dash array and transparent otherwise.
     * 
     * @return the dash array
     */
    public float[] getDashArray() {
        return ((BasicStroke) stroke).getDashArray();
    }

    /**
     * Returns the current dash phase. The dash phase is a distance specified in
     * user coordinates that represents an offset into the dashing pattern. In
     * other words, the dash phase defines the point in the dashing pattern that
     * will correspond to the beginning of the stroke.
     * 
     * @return the dash phase as a float value.
     */
    public float getDashPhase() {
        return ((BasicStroke) stroke).getDashPhase();
    }

    /**
     * Returns the end cap style. 
     * 
     * @return the end cap style of this BasicStroke as one of the static int values that define possible end cap styles.
     */
    public int getEndCap() {
        return ((BasicStroke) stroke).getEndCap();
    }

    /**
     * Returns the line join style.
     * 
     * @return the line join style of the <code>PFixedWidthStroke</code> as one
     *         of the static <code>int</code> values that define possible line
     *         join styles.
     */
    public int getLineJoin() {
        return ((BasicStroke) stroke).getLineJoin();
    }

    /**
     * Returns the line width. Line width is represented in user space, which is
     * the default-coordinate space used by Java 2D. See the Graphics2D class
     * comments for more information on the user space coordinate system.
     * 
     * @return the line width of this BasicStroke.
     */
    public float getLineWidth() {
        return ((BasicStroke) stroke).getLineWidth();
    }

    /**
     * Returns the miter limit of this node.
     * 
     * @return the limit of miter joins of the PFixedWidthStroke
     */
    public float getMiterLimit() {
        return ((BasicStroke) stroke).getMiterLimit();
    }

    /**
     * Returns a stroke equivalent to this one, but scaled by the scale
     * provided.
     * 
     * @param activeScale scale to apply to the new stoke
     * @return scaled stroke
     */
    protected Stroke newStroke(final float activeScale) {
        if (tmpDash != null) {
            for (int i = dash.length - 1; i >= 0; i--) {
                tmpDash[i] = dash[i] / activeScale;
            }
        }
        final float ml = getMiterLimit() / activeScale;
        final float sanitizedMiterLimit;
        if (ml < 1.0f) {
            sanitizedMiterLimit = 1f;
        }
        else {
            sanitizedMiterLimit = ml;
        }

        return new BasicStroke(getLineWidth() / activeScale, getEndCap(), getLineJoin(), sanitizedMiterLimit, tmpDash,
                getDashPhase() / activeScale);
    }

    /**
     * Is it really necessary to implement {@link Serializable}?
     * 
     * @throws ObjectStreamException doesn't actually throw this at all, why's
     *             this here?
     * @return the resolved stroke
     */
    protected Object readResolve() throws ObjectStreamException {
        return new PFixedWidthStroke((BasicStroke) stroke);
    }
}
