

package com.aimxcel.abclearn.common.abclearncommon.view.controls;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.abclearncommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.components.SimSharingJCheckBoxMenuItem;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponent;
import com.aimxcel.abclearn.common.abclearncommon.util.SimpleObserver;

/**
 * JCheckBoxMenuItem that is wired to a Property<Boolean>, includes data-collection feature.
 *
 * @author Sam Reid
 */
public class PropertyCheckBoxMenuItem extends SimSharingJCheckBoxMenuItem {

    private final SettableProperty<Boolean> property;
    private final SimpleObserver propertyObserver;

    /**
     * @deprecated use sim-sharing version
     */
    public PropertyCheckBoxMenuItem( String text, final SettableProperty<Boolean> property ) {
        this( new UserComponent( PropertyCheckBoxMenuItem.class ), text, property );
    }

    public PropertyCheckBoxMenuItem( IUserComponent userComponent, String text, final SettableProperty<Boolean> property ) {
        super( userComponent, text );

        this.property = property;

        // update the model when the menu item changes
        this.addChangeListener( new ChangeListener() {
            public void stateChanged( ChangeEvent e ) {
                property.set( isSelected() );
            }
        } );

        // update the menu item when the model changes
        propertyObserver = new SimpleObserver() {
            public void update() {
                setSelected( property.get() );
            }
        };
        property.addObserver( propertyObserver );
    }

    public void cleanup() {
        property.removeObserver( propertyObserver );
    }
}
