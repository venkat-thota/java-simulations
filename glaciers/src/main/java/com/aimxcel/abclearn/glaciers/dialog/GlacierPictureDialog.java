
package com.aimxcel.abclearn.glaciers.dialog;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.aimxcel.abclearn.glaciers.GlaciersImages;
import com.aimxcel.abclearn.glaciers.GlaciersStrings;

import com.aimxcel.abclearn.common.aimxcelcommon.application.PaintImmediateDialog;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.EasyGridBagLayout;
public class GlacierPictureDialog extends PaintImmediateDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------

    /**
     * Sole constructor.
     * 
     * @param owner
     */
    public GlacierPictureDialog( Frame owner ) {
        super( owner, false /* nonmodal */);
        setResizable( false );

        // picture
        BufferedImage image = GlaciersImages.GLACIER_PICTURE;
        JLabel picture = new JLabel( new ImageIcon( image ) );
        picture.setSize( (int)image.getWidth(), (int)image.getHeight() );
        
        // text
        JTextArea text = new JTextArea( GlaciersStrings.TEXT_GLACIER_PICTURE );
        text.setColumns( 50 );
        text.setLineWrap( true );
        text.setWrapStyleWord( true );
        text.setEditable( false );
        text.setOpaque( false );

        // panel
        JPanel panel = new JPanel();
        panel.setBorder( BorderFactory.createEmptyBorder( 10, 15, 10, 15 ) ); // top, left, bottom, right
        EasyGridBagLayout layout = new EasyGridBagLayout( panel );
        layout.setInsets( new Insets( 5, 0, 5, 0 ) ); // top, left, bottom, right
        layout.setAnchor( GridBagConstraints.CENTER );
        panel.setLayout( layout );
        layout.addComponent( picture, 0, 0 );
        layout.addComponent( text, 1, 0 );

        // Add to the dialog
        getContentPane().add( panel );
        pack();
    }
    
    public void setVisible( boolean visible ) {
        super.setVisible( visible );
        if ( visible ) {
            pack(); // pack after making visible because this dialog contains a JTextArea
        }
    }
}
