
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.state;

import java.io.Serializable;

import com.aimxcel.abclearn.common.aimxcelcommon.util.IProguardKeepClass;


public interface SimState extends Serializable, IProguardKeepClass {
    long getTime();

    SerializableBufferedImage getImage();

    //Zero-based index that indicates the frame number for this state
    int getIndex();
}