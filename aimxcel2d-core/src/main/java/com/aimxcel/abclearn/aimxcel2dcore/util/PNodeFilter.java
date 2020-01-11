package com.aimxcel.abclearn.aimxcel2dcore.util;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;

public interface PNodeFilter {

    /**
     * Return true if the filter should accept the given node.
     * 
     * @param aNode node under test
     * @return true if node should be accepted
     */
    boolean accept(PNode aNode);

    /**
     * Return true if the filter should test the children of the given node for
     * acceptance.
     * 
     * @param aNode parent being tested
     * @return true if children should be tested for acceptance
     */
    boolean acceptChildrenOf(PNode aNode);
}
