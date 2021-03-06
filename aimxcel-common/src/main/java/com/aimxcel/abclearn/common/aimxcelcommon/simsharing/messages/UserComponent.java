
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.util.AimxcelUtilities;


public class UserComponent implements IUserComponent {

    private final String id;

    public UserComponent( String id ) {
        this.id = id;
    }

    public UserComponent( int id ) {
        this( String.valueOf( id ) );
    }

    // Converts a Class name to an IUserComponent by getting the Class' basename and converting first char to lowercase.
    public UserComponent( Class theClass ) {
        this( toId( theClass ) );
    }

    // Converts an Object to a UserComponent by using is class name, ala UserComponentId(Class)
    public UserComponent( Object object ) {
        this( object.getClass() );
    }

    @Override public String toString() {
        return id;
    }

    // Converts a class to a user component id. For example, "edu.aimxcel.RulerNode" becomes "rulerNode".
    private static String toId( Class theClass ) {
        String basename = AimxcelUtilities.getBasename( theClass );
        if ( basename.length() == 1 ) {
            return basename.toLowerCase();
        }
        else {
            return Character.toLowerCase( basename.charAt( 0 ) ) + basename.substring( 1 );
        }
    }
}