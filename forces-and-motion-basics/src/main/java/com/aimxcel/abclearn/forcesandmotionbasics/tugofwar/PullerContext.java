package com.aimxcel.abclearn.forcesandmotionbasics.tugofwar;

import com.aimxcel.abclearn.common.aimxcelcommon.util.function.VoidFunction0;

public interface PullerContext {
    void drag( PullerNode pullerNode );

    void endDrag( PullerNode pullerNode );

    void startDrag( PullerNode pullerNode );

    boolean isCartInCenter();

    void addCartPositionChangeListener( VoidFunction0 voidFunction0 );
}