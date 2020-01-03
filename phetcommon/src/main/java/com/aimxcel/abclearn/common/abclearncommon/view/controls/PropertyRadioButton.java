
package com.aimxcel.abclearn.common.abclearncommon.view.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.aimxcel.abclearn.common.abclearncommon.model.property.SettableProperty;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.components.SimSharingJRadioButton;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.UserComponent;
import com.aimxcel.abclearn.common.abclearncommon.util.SimpleObserver;

/**
 * PropertyRadioButton wires up a JRadioButton to a property of type T in an enum-style Property<T>.
 * Includes data-collection feature.
 *
 * @param <T> the type of object to be selected from
 * @author Sam Reid
 * @author Chris Malley
 */
public class PropertyRadioButton<T> extends SimSharingJRadioButton {

    private final SettableProperty<T> property;
    private final SimpleObserver propertyObserver;

    /**
     * @deprecated use sim-sharing version
     */
    public PropertyRadioButton( final String text, final SettableProperty<T> property, final T value ) {
        this( new UserComponent( PropertyRadioButton.class ), text, property, value );
    }

    public PropertyRadioButton( IUserComponent userComponent, final String text, final SettableProperty<T> property, final T value ) {
        super( userComponent, text );

        this.property = property;

        // update the model when the check box changes
        addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                property.set( value );
                propertyObserver.update();//make sure radio buttons don't toggle off, in case they're not in a button group
            }
        } );

        // update the check box when the model changes
        propertyObserver = new SimpleObserver() {
            public void update() {
                setSelected( property.get() == value );
            }
        };
        property.addObserver( propertyObserver );
    }

    public void cleanup() {
        property.removeObserver( propertyObserver );
    }
}