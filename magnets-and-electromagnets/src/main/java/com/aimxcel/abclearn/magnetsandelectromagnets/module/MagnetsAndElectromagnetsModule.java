

package com.aimxcel.abclearn.magnetsandelectromagnets.module;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.aimxcel.abclearn.aimxcelgraphics.application.SimSharingAimxcelGraphicsModule;
import com.aimxcel.abclearn.aimxcelgraphics.view.ApparatusPanel;
import com.aimxcel.abclearn.aimxcelgraphics.view.aimxcelgraphics.AimxcelGraphic;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.SwingClock;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.magnetsandelectromagnets.MagnetsAndElectromagnetsConstants;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.BFieldOutsideGraphic;
import com.aimxcel.abclearn.magnetsandelectromagnets.view.DebuggerGraphic;


public abstract class MagnetsAndElectromagnetsModule extends SimSharingAimxcelGraphicsModule implements ICompassGridModule {

    //----------------------------------------------------------------------------
    // Class data
    //----------------------------------------------------------------------------

    // Common graphics layers
    protected static final double DEBUG_LAYER = Double.MAX_VALUE - 1;
    protected static final double HELP_LAYER = Double.MAX_VALUE;

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    private BFieldOutsideGraphic _bFieldOutsideGraphic;
    private DebuggerGraphic _debuggerGraphic;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     *
     * @param title the module title
     */
    public MagnetsAndElectromagnetsModule( IUserComponent tabUserComponent, String title ) {
        super( tabUserComponent, title, new SwingClock( MagnetsAndElectromagnetsConstants.CLOCK_DELAY, MagnetsAndElectromagnetsConstants.CLOCK_STEP ) );
        setLogoPanel( null );
    }

    //----------------------------------------------------------------------------
    // Accessors
    //----------------------------------------------------------------------------

    /**
     * Sets the graphic that depicts the B-field graphic outside the magnet,
     * and that fills this module's play area.
     *
     * @param bFieldGraphic
     */
    public void setBFieldOutsideGraphic( BFieldOutsideGraphic bFieldGraphic ) {
        assert ( bFieldGraphic != null );
        _bFieldOutsideGraphic = bFieldGraphic;
    }

    /**
     * Sets visibility of the clock control panel.
     *
     * @param visible
     */
    public void setClockControlPanelVisible( boolean visible ) {
        JComponent panel = getClockControlPanel();
        if ( panel != null ) {
            panel.setVisible( visible );
        }
    }

    protected void setClockControlPanelEmpty( boolean empty ) {
        if ( empty ) {
            // #2853, Replace standard clock controls with an empty panel of the same size.
            JPanel emptyPanel = new JPanel() {{
                add( Box.createVerticalStrut( (int) getClockControlPanel().getPreferredSize().getHeight() ) );
                setPreferredSize( getClockControlPanel().getPreferredSize() );
            }};
            setClockControlPanel( emptyPanel );
        }
    }

    //----------------------------------------------------------------------------
    // Debugging
    //----------------------------------------------------------------------------

    /**
     * Causes the graphic's location and bounds to be rendered.
     *
     * @param graphic the graphic
     */
    protected void drawBounds( AimxcelGraphic graphic ) {
        ApparatusPanel apparatusPanel = getApparatusPanel();
        if ( _debuggerGraphic == null ) {
            _debuggerGraphic = new DebuggerGraphic( apparatusPanel );
            _debuggerGraphic.setLocationColor( Color.GREEN );
            _debuggerGraphic.setBoundsColor( Color.YELLOW );
        }
        _debuggerGraphic.add( graphic );
        apparatusPanel.addGraphic( _debuggerGraphic, DEBUG_LAYER );
    }

    //----------------------------------------------------------------------------
    // ICompassGridModule implementation
    //----------------------------------------------------------------------------

    /**
     * Sets the grid spacing.
     *
     * @param xSpacing space between needles in horizontal dimension, in pixels
     * @param ySpacing space between needles in the vertical dimension, in pixels
     */
    public void setGridSpacing( int xSpacing, int ySpacing ) {
        if ( _bFieldOutsideGraphic != null ) {
            _bFieldOutsideGraphic.setSpacing( xSpacing, ySpacing );
        }
    }

    /**
     * Gets the horizontal spacing between needles.
     *
     * @return the spacing, in pixels
     */
    public int getGridXSpacing() {
        int xSpacing = 0;
        if ( _bFieldOutsideGraphic != null ) {
            xSpacing = _bFieldOutsideGraphic.getXSpacing();
        }
        return xSpacing;
    }

    /**
     * Gets the vertical spacing between needles.
     *
     * @return the spacing, in pixels
     */
    public int getGridYSpacing() {
        int ySpacing = 0;
        if ( _bFieldOutsideGraphic != null ) {
            ySpacing = _bFieldOutsideGraphic.getYSpacing();
        }
        return ySpacing;
    }

    /**
     * Sets the size used for all needles in the grid.
     *
     * @param size the size, in pixels
     */
    public void setGridNeedleSize( Dimension size ) {
        if ( _bFieldOutsideGraphic != null ) {
            _bFieldOutsideGraphic.setNeedleSize( size );
        }
    }

    /**
     * Gets the size of all needles in the grid.
     *
     * @return the size, in pixels
     */
    public Dimension getGridNeedleSize() {
        Dimension size = null;
        if ( _bFieldOutsideGraphic != null ) {
            size = _bFieldOutsideGraphic.getNeedleSize();
        }
        return size;
    }

    /**
     * Tells the grid its background color.
     *
     * @param color
     */
    public void setGridBackground( Color color ) {
        if ( _bFieldOutsideGraphic != null ) {
            _bFieldOutsideGraphic.setGridBackground( color );
        }
    }
}
