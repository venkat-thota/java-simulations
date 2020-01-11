
package com.aimxcel.abclearn.aimxcel2dextra;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import com.aimxcel.abclearn.aimxcel2dcore.PCanvas;


public class PApplet extends JApplet {
    /** Used to allow versioned binary streams for serializations. */
    private static final long serialVersionUID = 1L;

    /** Canvas being displayed by this applet. */
    private PCanvas canvas;

    /**
     * Initializes the applet with a canvas and no background.
     */
    public void init() {
        setBackground(null);

        canvas = createCanvas();
        getContentPane().add(canvas);
        validate();
        canvas.requestFocus();
        beforeInitialize();

        // Manipulation of Core's scene graph should be done from Swings
        // event dispatch thread since Core is not thread safe. This code
        // calls initialize() from that thread once the PFrame is initialized,
        // so you are safe to start working with Core in the initialize()
        // method.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PApplet.this.initialize();
                repaint();
            }
        });
    }

    /**
     * Returns the canvas this PApplet is displaying.
     * 
     * @return canvas this applet is displaying
     */
    public PCanvas getCanvas() {
        return canvas;
    }

    /**
     * Provides an extension point for subclasses so that they can control
     * what's on the canvas by default.
     * 
     * @return a built canvas
     */
    public PCanvas createCanvas() {
        return new PCanvas();
    }

    /**
     * This method will be called before the initialize() method and will be
     * called on the thread that is constructing this object.
     */
    public void beforeInitialize() {
    }

    /**
     * Subclasses should override this method and add their Core2d
     * initialization code there. This method will be called on the swing event
     * dispatch thread. Note that the constructors of PFrame subclasses may not
     * be complete when this method is called. If you need to initailize some
     * things in your class before this method is called place that code in
     * beforeInitialize();
     */
    public void initialize() {
    }
}