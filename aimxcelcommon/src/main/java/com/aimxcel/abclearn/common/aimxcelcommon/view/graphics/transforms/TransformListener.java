

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms;

import com.aimxcel.abclearn.common.aimxcelcommon.view.graphics.transforms.ModelViewTransform2D;

/**
 * Listens for changes in the model or view viewport.
 *
 * @author ?
 * @version $Revision$
 */
public interface TransformListener {
    public void transformChanged( ModelViewTransform2D mvt );
}
