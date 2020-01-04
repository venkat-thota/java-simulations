
package com.aimxcel.abclearn.core.aimxcelcore.nodes.kit;

import com.aimxcel.abclearn.common.aimxcelcommon.util.Option;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option.None;
import com.aimxcel.abclearn.common.aimxcelcommon.util.Option.Some;
import edu.umd.cs.piccolo.PNode;


public class Kit<T extends PNode> {
    public final Option<PNode> title;
    public final T content;

    public Kit( T content ) {
        this( new None<PNode>(), content );
    }

    public Kit( PNode title, T content ) {
        this( new Some<PNode>( title ), content );
    }

    public Kit( Option<PNode> title, T content ) {
        this.title = title;
        this.content = content;
    }
}
