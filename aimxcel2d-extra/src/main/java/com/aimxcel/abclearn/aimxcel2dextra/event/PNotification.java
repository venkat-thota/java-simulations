package com.aimxcel.abclearn.aimxcel2dextra.event;

import java.util.Map;


public class PNotification {
    /** Name of the notification. */
    protected String name;
    /** The Object associated with this notification. */
    protected Object source;
    /** A free form map of properties to attach to this notification. */
    protected Map properties;

    /**
     * Creates a notification.
     * 
     * @param name Arbitrary name of the notification
     * @param source object associated with this notification
     * @param properties free form map of information about the notification
     */
    public PNotification(final String name, final Object source, final Map properties) {
        this.name = name;
        this.source = source;
        this.properties = properties;
    }

    /**
     * Return the name of the notification. This is the same as the name used to
     * register with the notification center.
     * 
     * @return name of notification
     */
    public String getName() {
        return name;
    }

    /**
     * Return the object associated with this notification. This is most often
     * the same object that posted the notification. It may be null.
     * 
     * @return object associated with this notification
     */
    public Object getObject() {
        return source;
    }

    /**
     * Return a property associated with the notification, or null if not found.
     * 
     * @param key key used for looking up the property
     * @return value associated with the key or null if not found
     */
    public Object getProperty(final Object key) {
        if (properties != null) {
            return properties.get(key);
        }
        return null;
    }
}
