

package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import com.aimxcel.abclearn.common.aimxcelcommon.dialogs.ColorChooserFactory;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelOptionPane;
import com.aimxcel.abclearn.core.aimxcelcore.BufferedAimxcelPCanvas;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.RulerNode;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseConstants;
import com.aimxcel.abclearn.eatingandexercise.EatingAndExerciseResources;
import com.aimxcel.abclearn.eatingandexercise.control.CaloriePanel;
import com.aimxcel.abclearn.eatingandexercise.control.ChartNode;
import com.aimxcel.abclearn.eatingandexercise.control.HumanControlPanel;
import com.aimxcel.abclearn.eatingandexercise.model.Human;
import com.aimxcel.abclearn.eatingandexercise.view.EatingAndExerciseColorScheme;
import com.aimxcel.abclearn.eatingandexercise.view.HealthIndicator;
import com.aimxcel.abclearn.eatingandexercise.view.HumanNode;
import com.aimxcel.abclearn.eatingandexercise.view.ScaleNode;

import com.aimxcel.abclearn.aimxcel2dcore.PCamera;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PDragEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

public class EatingAndExerciseCanvas extends BufferedAimxcelPCanvas {

    //----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Model
    private EatingAndExerciseModel _model;

    // View
    private PNode _rootNode;

    private PSwing humanControlPanelPSwing;
    private HumanNode humanAreaNode;
    private BMIHelpButtonNode heartHealthButtonNode;
    private CaloriePanel caloriePanel;
    private BMIReadout bmiReadout;
    private TimeoutWarningMessage ageRangeMessage;

    private HumanControlPanel humanControlPanel;
    private StarvingMessage starvingMessage;
    private HeartAttackMessage heartAttackMessage;
    private HealthIndicator healthIndicator;
    private AimxcelPPath playAreaBackgroundNode;
    private boolean showColorChooser = false;

    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    public EatingAndExerciseCanvas( final EatingAndExerciseModel model, final Frame parentFrame ) {
        super( new PDimension( 15, 15 ) );

        _model = model;

        setBackground( EatingAndExerciseConstants.BACKGROUND );
        if ( showColorChooser ) {
            showColorChooser( parentFrame );
        }
        // Root of our scene graph
        _rootNode = new PNode();


        _rootNode.addChild( new ScaleNode( model, model.getHuman() ) );
        humanAreaNode = new HumanNode( model.getHuman() );
        humanAreaNode.addListener( new HumanNode.Listener() {
            public void infoButtonPressed() {
                AimxcelOptionPane.showMessageDialog( EatingAndExerciseCanvas.this, EatingAndExerciseResources.getString( "heart.health.info" ) );
            }
        } );

        playAreaBackgroundNode = new AimxcelPPath( EatingAndExerciseColorScheme.getBackgroundColor(), new BasicStroke( 2 ), Color.gray );
        addScreenChild( playAreaBackgroundNode );

        addWorldChild( _rootNode );

        _rootNode.addChild( humanAreaNode );

        bmiReadout = new BMIReadout( model.getHuman() );
//        addScreenChild( bmiReadout );

        heartHealthButtonNode = new BMIHelpButtonNode( this, model.getHuman() );
//        addScreenChild( heartHealthButtonNode );

        updateHeartHealthButtonNodeLayout();
        addComponentListener( new ComponentAdapter() {
            public void componentResized( ComponentEvent e ) {
                updateHeartHealthButtonNodeLayout();
            }
        } );
        model.getHuman().addListener( new Human.Adapter() {
            public void heightChanged() {
                updateHeartHealthButtonNodeLayout();
            }
        } );

        humanControlPanel = new HumanControlPanel( this, model, model.getHuman() );
        humanControlPanel.addListener( new HumanControlPanel.Listener() {
            public void ageManuallyChanged() {
                caloriePanel.clearAndResetDomains();
            }
        } );
//        humanControlPanel.addListener(){
        humanControlPanelPSwing = new PSwing( humanControlPanel );
        addScreenChild( humanControlPanelPSwing );

        healthIndicator = new HealthIndicator( model.getHuman() );
        addScreenChild( healthIndicator );

        caloriePanel = new CaloriePanel( model, this, parentFrame );
        addScreenChild( caloriePanel );

        addMouseListener( new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                requestFocus();
            }
        } );
        setWorldTransformStrategy( new EatingAndExerciseRenderingSizeStrategy( this ) );

        ageRangeMessage = new AgeRangeMessage( model.getHuman() );
//        addScreenChild( ageRangeMessage );

        starvingMessage = new StarvingMessage( model.getHuman() );
        addScreenChild( starvingMessage );

        heartAttackMessage = new HeartAttackMessage( model.getHuman() );
        addScreenChild( heartAttackMessage );

//        setZoomEventHandler( new PZoomEventHandler() );

        updateLayout();
    }

    private void showColorChooser( final Frame parentFrame ) {
        getCamera().addInputEventListener( new PBasicInputEventHandler() {
            public void mousePressed( PInputEvent aEvent ) {
                if ( aEvent.isLeftMouseButton() && aEvent.getPickedNode() instanceof PCamera ) {
                    ColorChooserFactory.showDialog( "background color", parentFrame, getBackground(), new ColorChooserFactory.Listener() {
                        public void colorChanged( Color color ) {
                            setBackground( color );
                        }

                        public void ok( Color color ) {
                            setBackground( color );
                        }

                        public void cancelled( Color originalColor ) {
                            setBackground( originalColor );
                        }
                    }, true );
                }
            }
        } );
    }

    private void updateHeartHealthButtonNodeLayout() {
        Point2D pt = humanAreaNode.getHeartNode().getGlobalFullBounds().getOrigin();
        pt.setLocation( pt.getX() + humanAreaNode.getHeartNode().getGlobalFullBounds().getWidth(), pt.getY() + 5 );
        bmiReadout.setOffset( pt );
        heartHealthButtonNode.setOffset( bmiReadout.getFullBounds().getX(), bmiReadout.getFullBounds().getMaxY() );
    }

    public HumanNode getHumanAreaNode() {
        return humanAreaNode;
    }
//----------------------------------------------------------------------------
    // Canvas layout
    //----------------------------------------------------------------------------

    public PNode getEditDietButton() {
        return caloriePanel.getEditDietButton();
    }

    /*
     * Updates the layout of stuff on the canvas.
     */

    protected void updateLayout() {

        Dimension2D worldSize = getWorldSize();
        if ( worldSize.getWidth() <= 0 || worldSize.getHeight() <= 0 ) {
            // canvas hasn't been sized, blow off layout
            return;
        }
        else if ( EatingAndExerciseConstants.DEBUG_CANVAS_UPDATE_LAYOUT ) {
            System.out.println( "PhysicsCanvas.updateLayout worldSize=" + worldSize );//XXX
        }

        humanControlPanelPSwing.setOffset( 0, getHeight() - humanControlPanelPSwing.getFullBounds().getHeight() );
        ageRangeMessage.setOffset( humanControlPanelPSwing.getFullBounds().getMaxX(), humanControlPanelPSwing.getFullBounds().getY() + humanControlPanel.getAgeSliderY() );
        starvingMessage.setOffset( humanAreaNode.getGlobalFullBounds().getCenterX() - starvingMessage.getFullBounds().getWidth() / 2, humanAreaNode.getGlobalFullBounds().getCenterY() );
        heartAttackMessage.setOffset( starvingMessage.getFullBounds().getX(), starvingMessage.getFullBounds().getMaxY() );
        updateHealthIndicatorLocation();

        caloriePanel.setOffset( humanControlPanelPSwing.getFullBounds().getWidth(), 0 );
        playAreaBackgroundNode.setPathToRectangle( 0, 0, (float) humanControlPanelPSwing.getFullBounds().getWidth(), getHeight() );
    }

    private void updateHealthIndicatorLocation() {
        healthIndicator.setOffset( 5, humanControlPanelPSwing.getFullBounds().getMinY() - healthIndicator.getFullBounds().getHeight() );
        Point2D center = humanAreaNode.getGlobalFullBounds().getCenter2D();
        healthIndicator.setOffset( 5, center.getY() );
    }

    public double getControlPanelWidth() {
        return humanControlPanelPSwing.getFullBounds().getWidth();
    }

    //reset any view settings
    public void resetAll() {
        caloriePanel.resetAll();
    }

    public double getHumanControlPanelHeight() {
        return humanControlPanelPSwing == null ? 0 : humanControlPanelPSwing.getFullBounds().getHeight();
    }

    public void applicationStarted() {
        caloriePanel.applicationStarted();
    }

    public void addEditorClosedListener( ActionListener actionListener ) {
        caloriePanel.addEditorClosedListener( actionListener );
    }

    public double getAvailableWorldHeight() {
        return getHeight() - humanControlPanelPSwing.getFullBounds().getHeight();
    }

    public double getAvailableWorldWidth() {
        return humanControlPanelPSwing.getFullBounds().getWidth() * 1.1;//okay to overlap by 10%
    }

    public double getControlPanelY() {
        return humanControlPanelPSwing.getFullBounds().getY();
    }

    public void addFoodPressedListener( ActionListener actionListener ) {
        caloriePanel.addFoodPressedListener( actionListener );
    }

    public PNode getPlateNode() {
        return caloriePanel.getPlateNode();
    }

    public void addExerciseDraggedListener( ActionListener actionListener ) {
        caloriePanel.addExerciseDraggedListener( actionListener );
    }

    public PNode getDiaryNode() {
        return caloriePanel.getDiaryNode();
    }

    public ChartNode getChartNode() {
        return caloriePanel.getChartNode();
    }
}