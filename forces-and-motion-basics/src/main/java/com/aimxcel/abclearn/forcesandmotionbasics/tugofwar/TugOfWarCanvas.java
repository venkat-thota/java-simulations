package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import fj.Effect;
import fj.F;
import fj.F2;
import fj.data.List;
import fj.data.Option;
import fj.function.Doubles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Images;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsResources.Strings;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.ParameterKeys;
import com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.UserComponents;
import com.aimxcel.abclearn.forcesandmotionbasics.common.AbstractForcesAndMotionBasicsCanvas;

import com.aimxcel.abclearn.common.aimxcelcommon.audio.AimxcelAudioClip;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.Resettable;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockAdapter;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ClockEvent;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;
import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction1;
import com.aimxcel.abclearn.common.aimxcelcommon.util.functionaljava.FJUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.Dimension2DDouble;
import com.aimxcel.abclearn.common.aimxcelcommon.view.controls.PropertyCheckBox;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.RectangleUtils;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ControlPanelNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ResetAllButtonNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.TextButtonNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.background.SkyNode;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.HBox;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.layout.VBox;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.util.PBounds;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;

import static com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsApplication.BROWN;
import static com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsApplication.TOOLBOX_COLOR;
import static com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.ModelComponents.forceModel;
import static com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.ModelComponents.tugOfWarGame;
import static com.aimxcel.abclearn.forcesandmotionbasics.ForcesAndMotionBasicsSimSharing.ParameterKeys.sumOfForces;
import static com.aimxcel.abclearn.forcesandmotionbasics.tugofwar.KnotNode.*;
import static com.aimxcel.abclearn.forcesandmotionbasics.tugofwar.PullerNode.*;
import static com.aimxcel.abclearn.forcesandmotionbasics.tugofwar.TugOfWarCanvas.PullerColor.BLUE;
import static com.aimxcel.abclearn.forcesandmotionbasics.tugofwar.TugOfWarCanvas.PullerColor.RED;
import static com.aimxcel.abclearn.forcesandmotionbasics.tugofwar.TugOfWarCanvas.PullerSize.*;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager.sendModelMessage;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ModelActions.changed;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ModelActions.ended;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ModelComponentTypes.modelElement;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet.parameterSet;
import static com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform.createIdentity;


public class TugOfWarCanvas extends AbstractForcesAndMotionBasicsCanvas implements PullerContext {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final long ANIMATION_DURATION = 300;
    private final List<KnotNode> blueKnots;
    private final List<KnotNode> redKnots;
    private final ForcesNode forcesNode;
    private final ArrayList<VoidFunction0> forceListeners = new ArrayList<VoidFunction0>();
    private final PImage cartNode;
    private final Property<Boolean> showSumOfForces = new Property<Boolean>( false );
    private final Property<Boolean> showValues = new Property<Boolean>( false );
    private final Property<Boolean> sound = new Property<Boolean>( true );
    private final Property<Mode> mode = new Property<Mode>( Mode.WAITING );
    private final Cart cart = new Cart();
    private final ArrayList<PullerNode> pullers = new ArrayList<PullerNode>();
    private final PImage rope;
    private final double initialRopeX;
    private final ArrayList<VoidFunction0> cartPositionListeners = new ArrayList<VoidFunction0>();
    private final ImageButtonNodeWithText pauseButton;
    private final ImageButtonNodeWithText goButton;
    private final PNode knotLayer;
    private final PNode flagLayer;

    public static enum Mode {WAITING, GOING, PAUSED, COMPLETE}

    public TugOfWarCanvas( final Resettable moduleContext, final IClock clock ) {

        setBackground( BROWN );
        //use view coordinates since nothing compex happening in model coordinates.

        //for a canvas height of 710, the ground is at 452 down from the top
        final int width = 10000;

        //Reverse bottom and top because using view coordinates
        final int grassY = 452;
        addChild( new SkyNode( createIdentity(), new Rectangle2D.Double( -width / 2, -width / 2 + grassY, width, width / 2 ), grassY, SkyNode.DEFAULT_TOP_COLOR, SkyNode.DEFAULT_BOTTOM_COLOR ) );

        final PNode grassNode = new HBox(

                //Align so the "seam" is in the middle
                -4,

                new PImage( Images.GRASS ), new PImage( Images.GRASS ), new PImage( Images.GRASS ) );
        grassNode.setOffset( -grassNode.getFullBounds().getWidth() / 2, grassY - 2 );
        addChild( grassNode );

        final JCheckBox sumOfForcesCheckBox = new PropertyCheckBox( UserComponents.sumOfForcesCheckBox, Strings.SUM_OF_FORCES, showSumOfForces ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setFont( DEFAULT_FONT );
        }};
        final JCheckBox showValuesCheckBox = new PropertyCheckBox( UserComponents.valuesCheckBox, Strings.VALUES, showValues ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setFont( DEFAULT_FONT );
        }};
        final JCheckBox soundCheckBox = new PropertyCheckBox( UserComponents.soundCheckBox, Strings.SOUND, sound ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setFont( DEFAULT_FONT );
        }};
        final ControlPanelNode controlPanelNode = new ControlPanelNode(
                new VBox( 2, VBox.LEFT_ALIGNED,

                          //Nudge "show" to the right so it will align with checkboxes
                          new PSwing( sumOfForcesCheckBox ), new PSwing( showValuesCheckBox ), new PSwing( soundCheckBox ) ), new Color( 227, 233, 128 ), new BasicStroke( 2 ), Color.black );
        controlPanelNode.setOffset( STAGE_SIZE.width - controlPanelNode.getFullWidth() - INSET, INSET );
        addChild( controlPanelNode );

        addChild( new ResetAllButtonNode( new Resettable() {
            public void reset() {
                moduleContext.reset();
            }
        }, this, DEFAULT_FONT, Color.black, Color.orange ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( controlPanelNode.getFullBounds().getCenterX() - getFullBounds().getWidth() / 2, controlPanelNode.getMaxY() + INSET );
            setConfirmationEnabled( false );
        }} );

        cartNode = new PImage( Images.CART );
        cartNode.setOffset( STAGE_SIZE.width / 2 - cartNode.getFullBounds().getWidth() / 2, grassY - cartNode.getFullBounds().getHeight() + 4 );

        rope = new PImage( Images.ROPE );
        initialRopeX = STAGE_SIZE.width / 2 - rope.getFullBounds().getWidth() / 2;
        rope.setOffset( initialRopeX, cartNode.getFullBounds().getCenterY() - rope.getFullBounds().getHeight() / 2 );

        blueKnots = RopeImageMetrics.blueKnots.map( new F<Double, KnotNode>() {
            @Override public KnotNode f( final Double knotLocation ) {
                return new KnotNode( knotLocation, rope.getFullBounds() );
            }
        } );
        redKnots = RopeImageMetrics.redKnots.map( new F<Double, KnotNode>() {
            @Override public KnotNode f( final Double knotLocation ) {
                return new KnotNode( knotLocation, rope.getFullBounds() );
            }
        } );

        knotLayer = new PNode() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            addChildren( blueKnots.append( redKnots ).toCollection() );
        }};
        addChild( knotLayer );

        addChild( rope );
        addChild( cartNode );

        final double IMAGE_SCALE = 0.75;
        //Move toolboxes so they are vertically centered in the earth
        double dy = -6;
        Vector2D largePosition = Vector2D.v( 88.38995568685374, 488 + 1 + dy );
        Vector2D mediumPosition = Vector2D.v( 155.66912850812423, 515 + 1 + 1 + dy );
        Vector2D smallPosition1 = Vector2D.v( 215.9527326440175, 559 - 1 + dy );
        Vector2D smallPosition2 = Vector2D.v( 263.1610044313148, 559 - 1 + dy );

        //Blue characters on the left, right characters on the right.  Color is specified with an enum flag instead of by the location of the characters.
        final PullerNode largeBluePuller = puller( UserComponents.largeBluePuller, BLUE, LARGE, IMAGE_SCALE, largePosition );
        addPuller( largeBluePuller );
        addPuller( puller( UserComponents.mediumBluePuller, BLUE, MEDIUM, IMAGE_SCALE, mediumPosition ) );
        addPuller( puller( UserComponents.smallBluePuller1, BLUE, SMALL, IMAGE_SCALE, smallPosition1 ) );
        addPuller( puller( UserComponents.smallBluePuller2, BLUE, SMALL, IMAGE_SCALE, smallPosition2 ) );

        final double offset = largeBluePuller.getFullBounds().getWidth();
        addPuller( puller( UserComponents.largeRedPuller, RED, LARGE, IMAGE_SCALE, reflect( largePosition, offset ) ) );
        addPuller( puller( UserComponents.mediumRedPuller, RED, MEDIUM, IMAGE_SCALE, reflect( mediumPosition, offset ) ) );
        addPuller( puller( UserComponents.smallRedPuller1, RED, SMALL, IMAGE_SCALE, reflect( smallPosition1, offset ) ) );
        addPuller( puller( UserComponents.smallRedPuller2, RED, SMALL, IMAGE_SCALE, reflect( smallPosition2, offset ) ) );

        final AimxcelPPath blueToolbox = new AimxcelPPath( getBounds( _isBlue ), TOOLBOX_COLOR, new BasicStroke( 1 ), Color.black );
        addChild( blueToolbox );
        final AimxcelPPath redToolbox = new AimxcelPPath( getBounds( _isRed ), TOOLBOX_COLOR, new BasicStroke( 1 ), Color.black );
        addChild( redToolbox );
        blueToolbox.moveToBack();
        redToolbox.moveToBack();

        forcesNode = new ForcesNode();
        addChild( forcesNode );

        goButton = new ImageButtonNodeWithText( UserComponents.goButton, Images.GO_UP, Images.GO_HOVER, Images.GO_PRESSED, Strings.GO, new VoidFunction0() {
            public void apply() {
                mode.set( Mode.GOING );
                pauseButton.hover();
            }
        } ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( getButtonLocation( this ) );

            final VoidFunction0 update = new VoidFunction0() {
                public void apply() {
                    boolean visible = redKnots.append( blueKnots ).filter( new F<KnotNode, Boolean>() {
                        @Override public Boolean f( final KnotNode knotNode ) {
                            return knotNode.getPullerNode() != null;
                        }
                    } ).length() > 0 && ( mode.get() == Mode.WAITING || mode.get() == Mode.PAUSED );
                    setVisible( visible );
                    setChildrenPickable( visible );
                }
            };
            forceListeners.add( update );
            update.apply();
        }};
        addChild( goButton );

        pauseButton = new ImageButtonNodeWithText( UserComponents.stopButton, Images.STOP_UP, Images.STOP_HOVER, Images.STOP_PRESSED, Strings.PAUSE, new VoidFunction0() {
            public void apply() {
                mode.set( Mode.PAUSED );
                goButton.hover();
            }
        } ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( getButtonLocation( this ) );
            mode.addObserver( new VoidFunction1<Mode>() {
                public void apply( final Mode mode ) {
                    boolean visible = mode == Mode.GOING;
                    setVisible( visible );
                    setChildrenPickable( visible );
                }
            } );
        }};
        addChild( pauseButton );

        addChild( new TextButtonNode( Strings.RETURN, DEFAULT_FONT, Color.orange ) {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setUserComponent( UserComponents.returnButton );
            setOffset( pauseButton.getFullBounds().getCenterX() - getFullBounds().getWidth() / 2, pauseButton.getFullBounds().getMaxY() + INSET );
            final SimpleObserver update = new SimpleObserver() {
                public void update() {
                    //leave "restart" button showing after "stop" pressed
                    Mode m = mode.get();
                    boolean visible = m == Mode.GOING || m == Mode.COMPLETE || ( m == Mode.WAITING && !isCartInCenter() ) || m == Mode.PAUSED;
                    setVisible( visible );
                    setPickable( visible );
                    setChildrenPickable( visible );
                }
            };
            mode.addObserver( update );
            cart.position.addObserver( update );
            addActionListener( new ActionListener() {
                public void actionPerformed( final ActionEvent e ) {
                    restart();
                }
            } );
        }} );

        mode.addObserver( new VoidFunction1<Mode>() {
            public void apply( final Mode mode ) {
                updateForceListeners();
            }
        } );

        clock.addClockListener( new ClockAdapter() {
            @Override public void simulationTimeChanged( final ClockEvent clockEvent ) {

                //all motion is done through deltas
                if ( mode.get() == Mode.GOING ) {
                    double originalCartPosition = cart.getPosition();
                    final double dt = clockEvent.getSimulationTimeChange();
                    double acceleration = getSumOfForces() / ( cart.weight + getAttachedPullers().map( PullerNode._weight ).foldLeft( Doubles.add, 0.0 ) );
                    cart.stepInTime( dt, acceleration );
                    final double delta = cart.getPosition() - originalCartPosition;
                    moveSystem( delta );
                    notifyCartPositionListeners();

                    //stop when the opposite rope passes the middle of the screen
                    if ( cart.getPosition() > 180 || cart.getPosition() < -180 ) {
                        mode.set( Mode.COMPLETE );
                        if ( cart.getPosition() > 180 ) {
                            addFlagNode( new FlagNode( Color.red, Strings.RED_WINS ), "red" );
                        }
                        else {
                            addFlagNode( new FlagNode( Color.blue, Strings.BLUE_WINS ), "blue" );
                        }
                    }
                }
            }
        } );

        addChild( new CaretNode() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		{
            setOffset( STAGE_SIZE.width / 2, grassY + 9 );
        }} );

        showSumOfForces.addObserver( new VoidFunction1<Boolean>() {
            public void apply( final Boolean showSumOfForces ) {
                updateForceArrows();
            }
        } );
        showValues.addObserver( new VoidFunction1<Boolean>() {
            public void apply( final Boolean showValues ) {
                updateForceArrows();
            }
        } );

        flagLayer = new PNode();
        addChild( flagLayer );
    }

    private void addFlagNode( final FlagNode flagNode, String simSharingMessageText ) {
        flagNode.setTransparency( 0.0f );
        flagLayer.addChild( flagNode );
        flagNode.setOffset( STAGE_SIZE.width / 2 - flagNode.getFullBounds().getWidth() / 2, 0 - flagNode.getFullBounds().getHeight() );
        flagNode.animateToTransparency( 1, 200 );
        flagNode.animateToPositionScaleRotation( STAGE_SIZE.width / 2 - flagNode.getFullBounds().getWidth() / 2, 10, 1, 0, 200 );
        if ( sound.get() ) {
            new AimxcelAudioClip( "forces-and-motion-basics/audio/golf-clap.wav" ).play();
        }
        sendModelMessage( tugOfWarGame, modelElement, ended, parameterSet( ParameterKeys.winningTeam, simSharingMessageText ) );
    }

    private Shape getBounds( final F<PullerNode, Boolean> color ) {
        final PBounds bounds = List.iterableList( pullers ).filter( color ).map( _getFullBounds ).foldLeft( new F2<PBounds, PBounds, PBounds>() {
            @Override public PBounds f( final PBounds a, final PBounds b ) {
                if ( a == null ) { return b; }
                if ( b == null ) { return a; }
                return new PBounds( a.createUnion( b ) );
            }
        }, null );
        Rectangle2D expanded = RectangleUtils.expand( bounds, 15, 10 );
        return new RoundRectangle2D.Double( expanded.getX(), expanded.getY(), expanded.getWidth(), expanded.getHeight(), 20, 20 );
    }

    private void notifyCartPositionListeners() {
        for ( VoidFunction0 listener : cartPositionListeners ) {
            listener.apply();
        }
    }

    private void moveSystem( final double delta ) {
        cartNode.translate( delta, 0 );
        rope.translate( delta, 0 );
        knotLayer.translate( delta, 0 );
        getAttachedPullers().foreach( new Effect<PullerNode>() {
            @Override public void e( final PullerNode pullerNode ) {
                pullerNode.translate( delta / pullerNode.getScale(), 0 );
            }
        } );
    }

    private void restart() {
        mode.set( Mode.WAITING );
        cart.restart();
        double ropeOffset = rope.getOffset().getX() - initialRopeX;
        moveSystem( -ropeOffset );
        updateForceListeners();
        notifyCartPositionListeners();
        for ( Object child : flagLayer.getChildrenReference() ) {
            if ( child instanceof FlagNode ) {
                FlagNode flagNode = (FlagNode) child;
                flagNode.dispose();
            }
        }
        flagLayer.removeAllChildren();
    }

    private void addPuller( final PullerNode puller ) {
        addChild( puller );
        pullers.add( puller );
    }

    List<PullerNode> getAttachedPullers() {
        return blueKnots.append( redKnots ).bind( new F<KnotNode, List<PullerNode>>() {
            @Override public List<PullerNode> f( final KnotNode k ) {
                return k.getPullerNode() == null ? List.<PullerNode>nil() : List.single( k.getPullerNode() );
            }
        } );
    }

    private Point2D getButtonLocation( PNode buttonNode ) {
        return new Point2D.Double( STAGE_SIZE.width / 2 - buttonNode.getFullBounds().getWidth() / 2, cartNode.getFullBounds().getMaxY() + INSET * 2 );
    }

    private Vector2D reflect( final Vector2D position, final double width ) {
        double distanceFromCenter = STAGE_SIZE.width / 2 - position.x;
        double newX = STAGE_SIZE.width / 2 + distanceFromCenter - width;
        return new Vector2D( newX, position.y );
    }

    PullerNode puller( IUserComponent component, PullerColor color, PullerSize size, final double scale, final Vector2D offset ) {
        return new PullerNode( component, color, size, scale, offset, this, mode );
    }

    public void drag( final PullerNode pullerNode ) {
        //find closest knot node
        List<KnotNode> knots = pullerNode.color == BLUE ? blueKnots : redKnots;
        knots.foreach( _removeHighlight );
        Option<KnotNode> attachNode = getAttachNode( pullerNode );
        attachNode.foreach( new Effect<KnotNode>() {
            @Override public void e( final KnotNode knotNode ) {
                knotNode.setHighlighted( true );
            }
        } );
    }

    public void endDrag( final PullerNode pullerNode ) {
        blueKnots.append( redKnots ).foreach( _removeHighlight );
        Option<KnotNode> attachNode = getAttachNode( pullerNode );
        if ( attachNode.isSome() ) {
            Point2D hands = pullerNode.getGlobalAttachmentPoint();
            Point2D knot = attachNode.some().getGlobalFullBounds().getCenter2D();
            Vector2D delta = new Vector2D( hands, knot );
            Dimension2D localDelta = rootNode.globalToLocal( new Dimension2DDouble( delta.x, delta.y ) );
            pullerNode.animateToPositionScaleRotation( pullerNode.getOffset().getX() + localDelta.getWidth(), pullerNode.getOffset().getY() + localDelta.getHeight(), pullerNode.scale, 0,

                                                       //if the rope is moving, automatically translate to the right location otherwise the target will be puller arrives
                                                       mode.get() == Mode.GOING ? 0 : ANIMATION_DURATION );

            //attach everything
            attachNode.some().setPullerNode( pullerNode );
            pullerNode.setKnot( attachNode.some() );
            updateForceListeners();
        }
        else {
            detach( pullerNode );
            pullerNode.animateHome();
        }
    }

    private void detach( final PullerNode pullerNode ) {
        KnotNode node = pullerNode.getKnot();
        if ( node != null ) {
            node.setPullerNode( null );
        }
        pullerNode.setKnot( null );
        updateForceListeners();
    }

    private void updateForceListeners() {
        updateForceArrows();

        for ( VoidFunction0 forceListener : forceListeners ) {
            forceListener.apply();
        }

        sendModelMessage( forceModel, modelElement, changed, parameterSet( sumOfForces, getSumOfForces() ) );
    }

    private void updateForceArrows() {
        forcesNode.setForces( mode.get() == Mode.PAUSED || mode.get() == Mode.WAITING || mode.get() == Mode.COMPLETE, getLeftForce(), getRightForce(), showSumOfForces.get(), showValues.get() );
    }

    private double getRightForce() {
        return redKnots.map( _force ).foldLeft( Doubles.add, 0.0 );
    }

    private double getLeftForce() {
        return -blueKnots.map( _force ).foldLeft( Doubles.add, 0.0 );
    }

    private double getSumOfForces() {return getRightForce() + getLeftForce();}

    public void startDrag( final PullerNode pullerNode ) {
        detach( pullerNode );
    }

    public boolean isCartInCenter() {
        return Math.abs( cart.getPosition() ) < 1;
    }

    public void addCartPositionChangeListener( final VoidFunction0 voidFunction0 ) {
        cartPositionListeners.add( voidFunction0 );
    }

    private Option<KnotNode> getAttachNode( final PullerNode pullerNode ) {
        List<KnotNode> knots = pullerNode.color == BLUE ? blueKnots : redKnots;
        List<KnotNode> free = knots.filter( _free ).filter( new F<KnotNode, Boolean>() {
            @Override public Boolean f( final KnotNode knotNode ) {
                return knotPullerDistance( knotNode, pullerNode ) < 80;
            }
        } );
        if ( free.length() > 0 ) {
            KnotNode closest = free.minimum( FJUtils.ord( new F<KnotNode, Double>() {
                @Override public Double f( final KnotNode k ) {
                    return knotPullerDistance( k, pullerNode );
                }
            } ) );
            return Option.some( closest );
        }
        else { return Option.none(); }
    }

    private double knotPullerDistance( final KnotNode k, final PullerNode p ) {
        return k.getGlobalFullBounds().getCenter2D().distance( p.getGlobalAttachmentPoint() );
    }

    public static enum PullerColor {BLUE, RED}

    public static enum PullerSize {SMALL, MEDIUM, LARGE}
}