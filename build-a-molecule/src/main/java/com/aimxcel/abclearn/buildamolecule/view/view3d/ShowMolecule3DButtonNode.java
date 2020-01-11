
package com.aimxcel.abclearn.buildamolecule.view.view3d;

import static com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing.UserComponent.jmol3DButton;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeConstants;
import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeResources;
import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeSimSharing;
import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;
import com.aimxcel.abclearn.buildamolecule.model.CompleteMolecule;

import com.aimxcel.abclearn.aimxceljmol.JmolDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PText;
import com.aimxcel.abclearn.aimxcel2dcore.util.PAffineTransform;

/**
 * A '3d' button that shows a 3d molecule view when pressed. Only allows one instance of the dialog to be present, so it communicates via a
 * dialog-option property.
 */
public class ShowMolecule3DButtonNode extends PNode {
    public final Property<Option<JmolDialog>> dialog;

    public ShowMolecule3DButtonNode( final JmolDialogProperty dialog, final CompleteMolecule completeMolecule ) {
        this.dialog = dialog;

        // create our text for the '3D' button first. We will need its width later on
        PText text = new PText( BuildAMoleculeStrings.ICON_3D ) {{
            setFont( new AimxcelFont( 12, true ) );
            setTextPaint( Color.WHITE );
        }};

        // if it is too wide, override it
        if ( text.getWidth() > 35 ) {
            text.setText( "3D" );
        }

        // parts of the icon background
        PImage left = new PImage( BuildAMoleculeResources.getImage( BuildAMoleculeConstants.IMAGE_GREEN_LEFT ) );
        PImage middle = new PImage( BuildAMoleculeResources.getImage( BuildAMoleculeConstants.IMAGE_GREEN_MIDDLE ) );
        PImage right = new PImage( BuildAMoleculeResources.getImage( BuildAMoleculeConstants.IMAGE_GREEN_RIGHT ) );

        // natural (square 19x19) width
        double combinedWidth = left.getWidth() + middle.getWidth() + right.getWidth();

        // padding between text and the left/right sides
        double textPadding = 2;

        // get width with padding. minimum is combinedWidth, and take ceiling (so our middle tile fits nicely
        double textWidth = text.getFullBounds().getWidth() + 2 * textPadding; // padding
        if ( textWidth < combinedWidth ) {
            textWidth = combinedWidth;
        }
        textWidth = Math.ceil( textWidth );

        // center it in our bounds
        text.setOffset( ( textWidth - text.getFullBounds().getWidth() ) / 2, ( left.getFullBounds().getHeight() - text.getFullBounds().getHeight() ) / 2 );

        // position the parts of the icon background
        double xScale = textWidth - combinedWidth + 1;
        middle.setTransform( new PAffineTransform(
                xScale, 0, // widen the middle to take up all of the extra space
                0, 1, // same height
                left.getWidth(), 0 // offset to the right
        ) );
        right.setOffset( left.getWidth() + middle.getFullBounds().getWidth(), 0 );

        // background
        addChild( left );
        addChild( middle );
        addChild( right );

        // text over the icon background
        addChild( text );

        addInputEventListener( new CursorHandler() {
            @Override
            public void mouseClicked( PInputEvent event ) {
                SimSharingManager.sendUserMessage( jmol3DButton, UserComponentTypes.button, UserActions.pressed, ParameterSet.parameterSet( BuildAMoleculeSimSharing.ParameterKey.completeMoleculeCommonName, completeMolecule.getCommonName() ) );
                // if the 3D dialog is not shown, show it
                if ( dialog.get().isNone() ) {
                    // set our reference to it ("disables" this button)
                    dialog.set( new Option.Some<JmolDialog>( JmolDialog.displayMolecule3D( AimxcelApplication.getInstance().getAimxcelFrame(), completeMolecule, BuildAMoleculeStrings.JMOL_3D_SPACE_FILLING, BuildAMoleculeStrings.JMOL_3D_BALL_AND_STICK, BuildAMoleculeStrings.JMOL_3D_LOADING ) ) );
                    System.out.println( "Showing 3D dialog for " + completeMolecule.getDisplayName() + " PubChem CID #" + completeMolecule.getCID() );

                    // listen to when it closes so we can re-enable the button
                    dialog.get().get().addWindowListener( new WindowAdapter() {
                        @Override public void windowClosed( WindowEvent e ) {
                            dialog.set( new Option.None<JmolDialog>() );
                        }
                    } );
                }
                else {
                    dialog.get().get().requestFocus();
                }
            }
        } );

        // change overall transparency based on dialog existence
        dialog.addObserver( new SimpleObserver() {
            public void update() {
                setTransparency( dialog.get().isSome() ? 0.5f : 1f );
            }
        } );

        /*---------------------------------------------------------------------------*
        * gray "disabled" overlay
        *----------------------------------------------------------------------------*/
        addChild( new AimxcelPPath( new Rectangle2D.Double( 0, 0, getFullBounds().getWidth(), getFullBounds().getHeight() ) ) {{
            setPaint( new Color( 128, 128, 128, 64 ) );
            setStroke( null );
            dialog.addObserver( new SimpleObserver() {
                public void update() {
                    setVisible( dialog.get().isSome() );
                }
            } );
        }} );
    }
}
