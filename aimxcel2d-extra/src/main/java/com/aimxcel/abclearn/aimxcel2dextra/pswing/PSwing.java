package com.aimxcel.abclearn.aimxcel2dextra.pswing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.RepaintManager;

import com.aimxcel.abclearn.aimxcel2dcore.PCamera;
import com.aimxcel.abclearn.aimxcel2dcore.PLayer;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dcore.util.PPaintContext;


public class PSwing extends PNode implements Serializable, PropertyChangeListener {
    /** Default serial version UID. */
    private static final long serialVersionUID = 1L;

    /** Key for this object in the Swing component's client properties. */
    public static final String PSWING_PROPERTY = "PSwing";

    /** Temporary repaint bounds. */
    private static final PBounds TEMP_REPAINT_BOUNDS2 = new PBounds();

    /** Default Greek threshold, <code>0.3d</code>. */
    private static final double DEFAULT_GREEK_THRESHOLD = 0.3d;

    /** The cutoff at which the Swing component is rendered greek. */
    private double greekThreshold = DEFAULT_GREEK_THRESHOLD;

    /** Swing component for this Swing node. */
    private JComponent component = null;

    /** Minimum font size. */
    private double minFontSize = Double.MAX_VALUE;

    /**
     * Default stroke, <code>new BasicStroke()</code>. Cannot be made static
     * because BasicStroke is not serializable.  Should not be null.
     */
    private Stroke defaultStroke = new BasicStroke();

    /**
     * Default font, 12 point <code>"SansSerif"</code>. Will be made final in
     * version 2.0.
     */
    // public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF,
    // Font.PLAIN, 12); jdk 1.6+
    private static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 12);

    /** Swing canvas for this swing node. */
    private PSwingCanvas canvas;

    /**
     * Used to keep track of which nodes we've attached listeners to since no
     * built in support in PNode.
     */
    private final ArrayList listeningTo = new ArrayList();

    /** The parent listener for camera/canvas changes. */
    private final PropertyChangeListener parentListener = new PropertyChangeListener() {
        /** {@inheritDoc} */
        public void propertyChange(final PropertyChangeEvent evt) {
            final PNode parent = (PNode) evt.getNewValue();
            clearListeners((PNode) evt.getOldValue());
            if (parent == null) {
                updateCanvas(null);
            }
            else {
                listenForCanvas(parent);
            }
        }

        /**
         * Clear out all the listeners registered to make sure there are no
         * stray references.
         * 
         * @param fromParent Parent to start with for clearing listeners
         */
        private void clearListeners(final PNode fromParent) {
            if (fromParent != null && isListeningTo(fromParent)) {
                fromParent.removePropertyChangeListener(PNode.PROPERTY_PARENT, parentListener);
                listeningTo.remove(fromParent);
                clearListeners(fromParent.getParent());
            }
        }

    };

    /**
     * Listens to container nodes for changes to its contents. Any additions
     * will automatically have double buffering turned off.
     */
    private final ContainerListener doubleBufferRemover = new ContainerAdapter() {
        public void componentAdded(final ContainerEvent event) {
            Component childComponent = event.getChild();
            if (childComponent != null && childComponent instanceof JComponent) {
                disableDoubleBuffering(((JComponent) childComponent));
            }
        };

        /**
         * Disables double buffering on every component in the hierarchy of the
         * targetComponent.
         * 
         * I'm assuming that the intent of the is method is that it should be
         * called explicitly by anyone making changes to the hierarchy of the
         * Swing component graph.
         */
        private void disableDoubleBuffering(final JComponent targetComponent) {
            targetComponent.setDoubleBuffered(false);
            for (int i = 0; i < targetComponent.getComponentCount(); i++) {
                final Component c = targetComponent.getComponent(i);
                if (c instanceof JComponent) {
                    disableDoubleBuffering((JComponent) c);
                }
            }
        }
    };

    /**
     * Create a new visual component wrapper for the specified Swing component.
     * 
     * @param component Swing component to be wrapped
     */
    public PSwing(final JComponent component) {
        this.component = component;
        component.putClientProperty(PSWING_PROPERTY, this);
        initializeComponent(component);

        component.revalidate();
        updateBounds();
        listenForCanvas(this);
    }

    /**
     * @deprecated by {@link #PSwing(JComponent)}
     * 
     * @param swingCanvas canvas on which the PSwing node will be embedded
     * @param component not used
     */
    public PSwing(final PSwingCanvas swingCanvas, final JComponent component) {
        this(component);
    }

    /**
     * Ensures the bounds of the underlying component are accurate, and sets the
     * bounds of this PNode.
     */
    public void updateBounds() {
        /*
         * Need to explicitly set the component's bounds because 
         * the component's parent (PSwingCanvas.ChildWrapper) has no layout manager.
         */
        if (componentNeedsResizing()) {
            updateComponentSize();
        }
        setBounds(0, 0, component.getPreferredSize().width, component.getPreferredSize().height);
    }

    /**
     * Since the parent ChildWrapper has no layout manager, it is the responsibility of this PSwing
     * to make sure the component has its bounds set properly, otherwise it will not be drawn properly.
     * This method sets the bounds of the component to be equal to its preferred size.
     */
    private void updateComponentSize() {
        component.setBounds(0, 0, component.getPreferredSize().width, component.getPreferredSize().height);
    }

    /**
     * Determines whether the component should be resized, based on whether its actual width and height
     * differ from its preferred width and height.
     * @return true if the component should be resized.
     */
    private boolean componentNeedsResizing() {
        return component.getWidth() != component.getPreferredSize().width || component.getHeight() != component.getPreferredSize().height;
    }

    /**
     * Paints the PSwing on the specified renderContext.  Also determines if
     * the Swing component should be rendered normally or as a filled rectangle (greeking).
     * <p/>
     * The transform, clip, and composite will be set appropriately when this
     * object is rendered. It is up to this object to restore the transform,
     * clip, and composite of the Graphics2D if this node changes any of them.
     * However, the color, font, and stroke are unspecified by Core. This
     * object should set those things if they are used, but they do not need to
     * be restored.
     * 
     * @param renderContext Contains information about current render.
     */
    public void paint(final PPaintContext renderContext) {
        if (componentNeedsResizing()) {
            updateComponentSize();
            component.validate();
        }
        final Graphics2D g2 = renderContext.getGraphics();

        //Save Stroke and Font for restoring.
        Stroke originalStroke = g2.getStroke();
        Font originalFont = g2.getFont();

        g2.setStroke(defaultStroke);
        g2.setFont(DEFAULT_FONT);
        
        if (shouldRenderGreek(renderContext)) {
            paintAsGreek(g2);
        }
        else {
            paint(g2);
        }

        //Restore the stroke and font on the Graphics2D
        g2.setStroke(originalStroke);
        g2.setFont(originalFont);
    }

    /**
     * Return true if this Swing node should render as greek given the specified
     * paint context.
     * 
     * @param paintContext paint context
     * @return true if this Swing node should render as greek given the
     *         specified paint context
     */
    protected boolean shouldRenderGreek(final PPaintContext paintContext) {
        return paintContext.getScale() < greekThreshold || minFontSize * paintContext.getScale() < 0.5;
    }

    /**
     * Paints the Swing component as greek.  This method assumes that the stroke has been set beforehand.
     * 
     * @param g2 The graphics used to render the filled rectangle
     */
    public void paintAsGreek(final Graphics2D g2) {
        //Save original color for restoring painting as greek.
        Color originalColor = g2.getColor();

        if (component.getBackground() != null) {
            g2.setColor(component.getBackground());
        }
        g2.fill(getBounds());

        if (component.getForeground() != null) {
            g2.setColor(component.getForeground());
        }
        g2.draw(getBounds());

        //Restore original color on the Graphics2D
        g2.setColor(originalColor);
    }

    /** {@inheritDoc} */
    public void setVisible(final boolean visible) {
        super.setVisible(visible);

        if (component.isVisible() != visible) {
            component.setVisible(visible);
        }
    }

    /**
     * Remove from the SwingWrapper; throws an exception if no canvas is
     * associated with this PSwing.
     */
    public void removeFromSwingWrapper() {
        if (canvas != null && isComponentSwingWrapped()) {
            canvas.getSwingWrapper().remove(component);
        }
    }

    private boolean isComponentSwingWrapped() {
        return Arrays.asList(canvas.getSwingWrapper().getComponents()).contains(component);
    }

    /**
     * Renders the wrapped component to the graphics context provided.
     * 
     * @param g2 graphics context for rendering the JComponent
     */
    public void paint(final Graphics2D g2) {
        if (component.getBounds().isEmpty()) {
            // The component has not been initialized yet.
            return;
        }

        final PSwingRepaintManager manager = (PSwingRepaintManager) RepaintManager.currentManager(component);
        manager.lockRepaint(component);

        final RenderingHints oldHints = g2.getRenderingHints();

        //Disable Fractional Metrics on Mac OS because when FRACTIONAL_METRICS are enabled on Mac OS, spurious ellipses
        // are rendered on PSwing text components. Disabling the FRACTIONAL_METRICS rendering hint on Windows causes
        // incorrect rendering of adjacent text PSwing nodes.
        if (System.getProperty("os.name").startsWith( "Mac OS X" )) {
            g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        }
        component.paint(g2);

        g2.setRenderingHints(oldHints);

        manager.unlockRepaint(component);
    }

    /**
     * Repaints the specified portion of this visual component. Note that the
     * input parameter may be modified as a result of this call.
     * 
     * @param repaintBounds bounds that need repainting
     */
    public void repaint(final PBounds repaintBounds) {
        final Shape sh = getTransform().createTransformedShape(repaintBounds);
        TEMP_REPAINT_BOUNDS2.setRect(sh.getBounds2D());
        repaintFrom(TEMP_REPAINT_BOUNDS2, this);
    }

    /**
     * Returns the Swing component that this visual component wraps.
     * 
     * @return The Swing component wrapped by this PSwing node
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * We need to turn off double buffering of Swing components within Core
     * since all components contained within a native container use the same
     * buffer for double buffering. With normal Swing widgets this is fine, but
     * for Swing components within Core this causes problems. This function
     * recurses the component tree rooted at c, and turns off any double
     * buffering in use. It also updates the minimum font size based on the font
     * size of c and adds a property change listener to listen for changes to
     * the font.
     * 
     * @param c The Component to be recursively unDoubleBuffered
     */
    private void initializeComponent(final Component c) {

        if (c.getFont() != null) {
            minFontSize = Math.min(minFontSize, c.getFont().getSize());
        }
        c.addPropertyChangeListener("font", this);

        if (c instanceof Container) {
            initializeChildren((Container) c);
            ((Container) c).addContainerListener(doubleBufferRemover);
        }

        if (c instanceof JComponent) {
            ((JComponent) c).setDoubleBuffered(false);
        }
    }

    private void initializeChildren(final Container c) {
        final Component[] children = c.getComponents();
        if (children != null) {
            for (int j = 0; j < children.length; j++) {
                initializeComponent(children[j]);
            }
        }
    }

    /**
     * Listens for changes in font on components rooted at this PSwing.
     * 
     * @param evt property change event representing the change in font
     */
    public void propertyChange(final PropertyChangeEvent evt) {
        final Component source = (Component) evt.getSource();
        if (source.getFont() != null && component.isAncestorOf(source)) {
            minFontSize = Math.min(minFontSize, source.getFont().getSize());
        }
    }

    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initializeComponent(component);
    }

    /**
     * Attaches a listener to the specified node and all its parents to listen
     * for a change in the PSwingCanvas. Only PROPERTY_PARENT listeners are
     * added so this code wouldn't handle if a PLayer were viewed by a different
     * PCamera since that constitutes a child change.
     * 
     * @param node The child node at which to begin a parent-based traversal for
     *            adding listeners.
     */
    private void listenForCanvas(final PNode node) {
        // need to get the full tree for this node
        PNode p = node;
        while (p != null) {
            listenToNode(p);

            final PNode parent = p;
            // System.out.println( "parent = " + parent.getClass() );
            if (parent instanceof PLayer) {
                final PLayer player = (PLayer) parent;
                // System.out.println( "Found player: with " +
                // player.getCameraCount() + " cameras" );
                for (int i = 0; i < player.getCameraCount(); i++) {
                    final PCamera cam = player.getCamera(i);
                    if (cam.getComponent() instanceof PSwingCanvas) {
                        updateCanvas((PSwingCanvas) cam.getComponent());
                        break;
                    }
                }
            }
            p = p.getParent();
        }
    }

    /**
     * Attach a property change listener to the specified node, if one has not
     * already been attached.
     * 
     * @param node the node to listen to for parent/pcamera/pcanvas changes
     */
    private void listenToNode(final PNode node) {
        if (!isListeningTo(node)) {
            listeningTo.add(node);
            node.addPropertyChangeListener(PNode.PROPERTY_PARENT, parentListener);
        }
    }

    /**
     * Determine whether this PSwing is already listening to the specified node
     * for camera/canvas changes.
     * 
     * @param node the node to check
     * @return true if this PSwing is already listening to the specified node
     *         for camera/canvas changes
     */
    private boolean isListeningTo(final PNode node) {
        for (int i = 0; i < listeningTo.size(); i++) {
            final PNode pNode = (PNode) listeningTo.get(i);
            if (pNode == node) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes this PSwing from previous PSwingCanvas (if any), and ensure that
     * this PSwing is attached to the new PSwingCanvas.
     * 
     * @param newCanvas the new PSwingCanvas (may be null)
     */
    private void updateCanvas(final PSwingCanvas newCanvas) {
        if (newCanvas == canvas) {
            return;
        }

        if (canvas != null) {
            canvas.removePSwing(this);
        }

        if (newCanvas == null) {
            canvas = null;
        }
        else {
            canvas = newCanvas;
            canvas.addPSwing(this);
            updateBounds();
            repaint();
            canvas.invalidate();
            canvas.revalidate();
            canvas.repaint();
        }

    }

    /**
     * Return the Greek threshold scale. When the scale will be below this
     * threshold the Swing component is rendered as 'Greek' instead of painting
     * the Swing component. Defaults to {@link #DEFAULT_GREEK_THRESHOLD}.
     * 
     * @see PSwing#paintAsGreek(Graphics2D)
     * @return the current Greek threshold scale
     */
    public double getGreekThreshold() {
        return greekThreshold;
    }

    /**
     * Set the Greek threshold in scale to <code>greekThreshold</code>. When the
     * scale will be below this threshold the Swing component is rendered as
     * 'Greek' instead of painting the Swing component..
     * 
     * @see PSwing#paintAsGreek(Graphics2D)
     * @param greekThreshold Greek threshold in scale
     */
    public void setGreekThreshold(final double greekThreshold) {
        this.greekThreshold = greekThreshold;
        invalidatePaint();
    }
}