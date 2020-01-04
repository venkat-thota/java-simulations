
package com.aimxcel.abclearn.common.aimxcelcommon.resources;

import java.util.Properties;
import java.util.logging.Logger;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelProperties;
import com.aimxcel.abclearn.common.aimxcelcommon.util.logging.LoggingUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.StringUtil;

/**
 * An extension of Properties that provides some convenience methods.
 */
public class AimxcelProperties extends Properties {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggingUtils.getLogger( AimxcelProperties.class.getCanonicalName() );

    public AimxcelProperties() {
        super();
    }

    public AimxcelProperties( Properties properties ) {
        super( properties );
    }

    public String getString( String propertyName ) {
        return getString( propertyName, true );
    }

    public String getString( String propertyName, boolean warnIfMissing ) {
    	String s = super.getProperty( propertyName );
        if ( s == null ) {
            if ( warnIfMissing ) {
                LOGGER.warning( "requested property not found: " + propertyName );
            }
            s = propertyName;
        }
        return s;
    }

    public int getInt( String key, int defaultValue ) {
        return StringUtil.asInt( getString( key ), defaultValue );
    }

    public double getDouble( String key, double defaultValue ) {
        return StringUtil.asDouble( getString( key ), defaultValue );
    }

    public long getLong( String key, long defaultValue ) {
        return StringUtil.asLong( getString( key ), defaultValue );
    }

    public char getChar( String key, char defaultValue ) {
        return StringUtil.asChar( getString( key ), defaultValue );
    }

    public boolean getBoolean( String key, boolean defaultValue ) {
        boolean value = defaultValue;
        String s = getString( key );
        if ( s != null ) {
            value = StringUtil.asBoolean( s );
        }
        return value;
    }
}
