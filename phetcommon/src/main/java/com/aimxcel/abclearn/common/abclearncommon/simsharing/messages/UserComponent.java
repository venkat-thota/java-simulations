
package com.aimxcel.abclearn.common.abclearncommon.simsharing.messages;

import com.aimxcel.abclearn.common.abclearncommon.util.AbcLearnUtilities;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;

/**
 * Class for creating custom IUserComponents without using enum.
 *
 * @author Sam Reid
 * @author Chris Malley (cmalley@pixelzoom.com)
 */
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

    // Converts a class to a user component id. For example, "edu.phet.RulerNode" becomes "rulerNode".
    private static String toId( Class theClass ) {
        String basename = AbcLearnUtilities.getBasename( theClass );
        if ( basename.length() == 1 ) {
            return basename.toLowerCase();
        }
        else {
            return Character.toLowerCase( basename.charAt( 0 ) ) + basename.substring( 1 );
        }
    }
}