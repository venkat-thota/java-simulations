

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */

package edu.colorado.phet.common.phetgraphics.application;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.aimxcel.abclearn.common.abclearncommon.application.Module;
import com.aimxcel.abclearn.common.abclearncommon.model.BaseModel;
import com.aimxcel.abclearn.common.abclearncommon.model.ModelElement;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.abclearncommon.model.clock.IClock;
import com.aimxcel.abclearn.common.abclearncommon.view.ControlPanel;

import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel;
import edu.colorado.phet.common.phetgraphics.view.ApparatusPanel2;
import edu.colorado.phet.common.phetgraphics.view.help.HelpManager;
import edu.colorado.phet.common.phetgraphics.view.phetcomponents.AbcLearnJComponent;
import edu.colorado.phet.common.phetgraphics.view.phetgraphics.AbcLearnGraphic;
import edu.colorado.phet.common.piccolophet.nodes.mediabuttons.PiccoloClockControlPanel;

/**
 * This class encapsulates the parts of an application that make up
 * a complete virtual experiment. This includes, but is not limited to, the
 * on-screen controls and view elements that go along with the
 * experiment. Each module has its own model.
 *
 * @author ?
 * @version $Revision$
 */
public class AbcLearnGraphicsModule extends Module {

    private ApparatusPanel apparatusPanel;
    private HelpManager helpManager;

    /**
     * @param name
     * @deprecated
     */
    protected AbcLearnGraphicsModule( String name ) {
        this( name, null );
    }

    /**
     * @param name
     * @param clock
     */
    protected AbcLearnGraphicsModule( String name, IClock clock ) {
        super( name, clock );
        helpManager = new HelpManager();
        updateHelpPanelVisible();//have to update the state in the parent class, since helpManager was null
        //when updateHelpPanelVisible was called in the super()

        // Handle redrawing while the clock is paused.
        clock.addClockListener( new ClockPausedHandler( this ) );
    }

    protected void init( ApparatusPanel apparatusPanel, ControlPanel controlPanel, JPanel monitorPanel, BaseModel baseModel ) {
        setApparatusPanel( apparatusPanel );
        setControlPanel( controlPanel );
        setMonitorPanel( monitorPanel );
        setModel( baseModel );
    }

    //-----------------------------------------------------------------
    // Setters and getters
    //-----------------------------------------------------------------

    public void setApparatusPanel( ApparatusPanel apparatusPanel ) {
        this.apparatusPanel = apparatusPanel;
        if ( helpManager != null ) {
            helpManager.setComponent( apparatusPanel );
        }
        else {
            helpManager = new HelpManager( apparatusPanel );
        }
        super.setSimulationPanel( apparatusPanel );
    }

    public ApparatusPanel getApparatusPanel() {
        return apparatusPanel;
    }

    public void addGraphic( AbcLearnGraphic graphic, double layer ) {
        getApparatusPanel().addGraphic( graphic, layer );
    }

    protected void add( ModelElement modelElement, AbcLearnGraphic graphic, double layer ) {
        this.addModelElement( modelElement );
        this.addGraphic( graphic, layer );
    }

    protected void remove( ModelElement modelElement, AbcLearnGraphic graphic ) {
        getModel().removeModelElement( modelElement );
        getApparatusPanel().removeGraphic( graphic );
    }

    //-----------------------------------------------------------------
    // Help-related methods
    //-----------------------------------------------------------------

    /**
     * Adds an onscreen help item to the module
     *
     * @param helpItem
     */
    public void addHelpItem( AbcLearnGraphic helpItem ) {
        helpManager.addGraphic( helpItem );
    }

    /**
     * Removes an onscreen help item from the module
     *
     * @param helpItem
     */
    public void removeHelpItem( AbcLearnGraphic helpItem ) {
        helpManager.removeGraphic( helpItem );
    }

    public HelpManager getHelpManager() {
        return helpManager;
    }

    //----------------------------------------------------------------
    // Rendering
    //----------------------------------------------------------------

    //----------------------------------------------------------------
    // Main loop
    //----------------------------------------------------------------

    ////////////////////////////////////////////////////////////////
    // Persistence
    //

//    public void setState( StateDescriptor stateDescriptor ) {
//        stateDescriptor.setState( this );
////        restoreState( (ModuleStateDescriptor)stateDescriptor );
//    }

//    /**
//     * Restores the state of this Module to that specificied in a ModuleStateDescriptor
//     *
//     * @param stateDesriptor
//     */
//    private void restoreState( ModuleStateDescriptor stateDescriptor ) {
//
//        // Remove and clean up the current model
//        AbstractClock clock = AbcLearnApplication.instance().getApplicationModel().getClock();
//        BaseModel oldModel = getModel();
//        oldModel.removeAllModelElements();
//        clock.removeClockTickListener( oldModel );
//
//        // Set up the restored model
//        BaseModel newModel = sd.getModel();
//        clock.addClockTickListener( newModel );
//        setMode( newModel );
//
//        // Set up the restored graphics
//        // Hook all the graphics up to the current apparatus panel
//        MultiMap graphicsMap = sd.getGraphicMap();
//        Iterator it = graphicsMap.iterator();
//        while( it.hasNext() ) {
//            Object obj = it.next();
//            if( obj instanceof AbcLearnGraphic ) {
//                AbcLearnGraphic phetGraphic = (AbcLearnGraphic)obj;
//                phetGraphic.setComponent( getApparatusPanel() );
//            }
//        }
//        getApparatusPanel().getGraphic().setGraphicMap( sd.getGraphicMap() );
//
//        // Force a repaint on the apparatus panel
//        getApparatusPanel().repaint();
//    }

    public void updateGraphics( ClockEvent event ) {
        super.updateGraphics( event );
        AbcLearnJComponent.getRepaintManager().updateGraphics();
    }

    public boolean hasHelp() {
        return helpManager != null//have to check since this method is called by superclass constructor
               && helpManager.getNumGraphics() > 0;
    }

    public void setHelpEnabled( boolean h ) {
        super.setHelpEnabled( h );
        helpManager.setHelpEnabled( apparatusPanel, h );
    }

    /**
     * Refreshes the Module, redrawing it while its clock is paused.
     */
    public void refresh() {
        // Repaint all dirty AbcLearnJComponents
        AbcLearnJComponent.getRepaintManager().updateGraphics();
        // Paint the apparatus panel
        apparatusPanel.paint();
    }

    protected void handleUserInput() {
        super.handleUserInput();
        getApparatusPanel().handleUserInput();
    }

    public void setReferenceSize() {
        JComponent panel = getSimulationPanel();
        if ( panel instanceof ApparatusPanel2 ) {
            final ApparatusPanel2 apparatusPanel = (ApparatusPanel2) panel;

            // Add the listener to the apparatus panel that will tell it to set its
            // reference size
            apparatusPanel.setReferenceSize();
        }
    }

    protected JComponent createClockControlPanel( IClock clock ) {
        return new PiccoloClockControlPanel( clock );
    }
}
