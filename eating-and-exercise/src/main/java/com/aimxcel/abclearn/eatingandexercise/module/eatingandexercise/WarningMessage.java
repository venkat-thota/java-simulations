
package com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.eatingandexercise.view.LabelNode;

public class WarningMessage extends LabelNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WarningMessage( String text ) {
        super( text );
        setFont( new AimxcelFont( 30, true ) );
    }
}
