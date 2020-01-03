
package com.aimxcel.abclearn.common.abclearncommon.simsharing.messages;

import com.aimxcel.abclearn.common.abclearncommon.util.ObservableList;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponentChain;

/**
 * Use this class when a single user component id is ambiguous, such as when there are multiple file->save buttons in the sim (for saving different things).
 * This makes the text appear in the form: simSharingLog.fileSaveButton
 */
public class UserComponentChain implements IUserComponent {
    private final IUserComponent[] components;

    public UserComponentChain( IUserComponent... components ) {
        this.components = components;
    }

    @Override public String toString() {
        return new ObservableList<IUserComponent>( components ).mkString( "." );
    }

    //Provide an index name for a component, such as "the 3rd track" would be track.3
    public static UserComponentChain chain( IUserComponent component, int index ) {
        return chain( component, new UserComponent( index ) );
    }

    public static UserComponentChain chain( IUserComponent... components ) {
        return new UserComponentChain( components );
    }

    public static IUserComponent chain( IUserComponent userComponent, String name ) {
        return new UserComponentChain( userComponent, new UserComponent( name ) );
    }
}