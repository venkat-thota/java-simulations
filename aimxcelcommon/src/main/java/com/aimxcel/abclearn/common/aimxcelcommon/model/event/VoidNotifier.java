
package com.aimxcel.abclearn.common.aimxcelcommon.model.event;

/**
 * Notifier where no data needs to be passed to listeners
 *
 * @author Jonathan Olson
 */
public class VoidNotifier extends ValueNotifier<Void> {
    public VoidNotifier() {
        super( null );
    }
}
