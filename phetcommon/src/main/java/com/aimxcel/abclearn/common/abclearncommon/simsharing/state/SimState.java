
package com.aimxcel.abclearn.common.abclearncommon.simsharing.state;

import java.io.Serializable;

import com.aimxcel.abclearn.common.abclearncommon.util.IProguardKeepClass;

/**
 * @author Sam Reid
 */
public interface SimState extends Serializable, IProguardKeepClass {
    long getTime();

    SerializableBufferedImage getImage();

    //Zero-based index that indicates the frame number for this state
    int getIndex();
}