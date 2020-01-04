
package edu.colorado.phet.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;

import edu.colorado.phet.eatingandexercise.view.LabelNode;

/**
 * Created by: Sam
 * Aug 11, 2008 at 1:14:23 PM
 */
public class WarningMessage extends LabelNode {
    public WarningMessage( String text ) {
        super( text );
        setFont( new AimxcelFont( 30, true ) );
    }
}
