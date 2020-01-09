package com.aimxcel.abclearn.forcesandmotionbasics.motion;

import com.aimxcel.abclearn.common.aimxcelcommon.model.property.BooleanProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.doubleproperty.DoubleProperty;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;


public interface StackableNodeContext {
    void stackableNodeDropped( StackableNode stackableNode );

    void stackableNodePressed( StackableNode stackableNode );

    DoubleProperty getAppliedForce();

    BooleanProperty getUserIsDraggingSomething();

    boolean isInStackButNotInTop( StackableNode stackableNode );

    void addStackChangeListener( SimpleObserver observer );

    int getStackSize();

    boolean isInStack( StackableNode node );
}