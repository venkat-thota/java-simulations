package com.aimxcel.abclearn.lwjgl;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;

import com.aimxcel.abclearn.lwjgl.utils.LWJGLUtils;


public class LWJGLCanvas extends Canvas {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String LWJGL_THREAD_NAME = "Aimxcel LWJGL Main Loop Thread";

    private Thread renderThread;
    private boolean running;

    private boolean tabDirty = true;
    private LWJGLTab activeTab = null;

    private static LWJGLCanvas instance = null;
    private static ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<Runnable>();
    private final Frame parentFrame;

    public static synchronized LWJGLCanvas getCanvasInstance( Frame parentFrame ) {
        if ( instance == null ) {
            instance = new LWJGLCanvas( parentFrame );
        }
        return instance;
    }

    public static void addTask( Runnable runnable ) {
        taskQueue.add( runnable );
    }

    private LWJGLCanvas( Frame parentFrame ) {
        this.parentFrame = parentFrame;
        setFocusable( true );
        requestFocus();
        setIgnoreRepaint( true );

        System.setProperty( "org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true" );
//        System.setProperty( "org.lwjgl.util.Debug", "true" );
    }

    @Override public void addNotify() {
        super.addNotify();
        initialize();
    }

    @Override public final void removeNotify() {
        running = false;
        try {
            renderThread.join();
        }
        catch( InterruptedException e ) {
            e.printStackTrace();
        }
        super.removeNotify();
    }

    public void switchToTab( final LWJGLTab tab ) {
        addTask( new Runnable() {
            public void run() {
                if ( activeTab != null ) {
                    activeTab.stop();
                }
                activeTab = tab;
                tabDirty = true;
            }
        } );
    }

    public void initialize() {
        renderThread = new Thread( LWJGLCanvas.LWJGL_THREAD_NAME ) {
            public void run() {
                running = true;
                try {
                    int maxAntiAliasingSamples = StartupUtils.getMaximumAntialiasingSamples();
                    Display.setParent( LWJGLCanvas.this );
                    Display.create( new PixelFormat( 24, // bpp, excluding alpha
                                                     8, // alpha
                                                     24, // depth
                                                     0, // stencil
                                                     Math.min( 4, maxAntiAliasingSamples ) ) // antialiasing samples
                    );

                    // main loop
                    while ( running ) {
                        while ( !taskQueue.isEmpty() ) {
                            taskQueue.poll().run();
                        }
                        if ( activeTab != null ) {
                            if ( tabDirty ) {
                                activeTab.start();
                                tabDirty = false;
                            }
                            activeTab.loop();
                        }
                    }

                    Display.destroy();

                }
                catch( LWJGLException e ) {
                    LWJGLUtils.showErrorDialog( parentFrame, e );
                    e.printStackTrace();
                }

            }
        };
        renderThread.start();
    }
}
