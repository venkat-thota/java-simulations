

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package com.aimxcel.abclearn.common.aimxcelcommon.model;

import java.util.Vector;

import com.aimxcel.abclearn.common.aimxcelcommon.model.Command;

/**
 * A synchronized list of Commands to be executed in the BaseModel's update.
 *
 * @author ?
 * @version $Revision$
 */
public class CommandQueue implements Command {
    private Vector al = new Vector();

    public int size() {
        return al.size();
    }

    public void doIt() {
        while ( !al.isEmpty() ) {
            commandAt( 0 ).doIt();
            al.remove( 0 );
        }
    }

    private Command commandAt( int i ) {
        return (Command) al.get( i );
    }

    public void addCommand( Command c ) {
        al.add( c );
    }

}
