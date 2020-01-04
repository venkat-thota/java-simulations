package com.aimxcel.abclearn.common.aimxcelcommon.model;

import java.util.Vector;

import com.aimxcel.abclearn.common.aimxcelcommon.model.Command;

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
