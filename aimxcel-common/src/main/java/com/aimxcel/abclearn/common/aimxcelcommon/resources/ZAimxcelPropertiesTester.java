
package com.aimxcel.abclearn.common.aimxcelcommon.resources;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelProperties;

import junit.framework.TestCase;

/**
 * The test suite for AimxcelProperties.
 */
public class ZAimxcelPropertiesTester extends TestCase {
    AimxcelProperties properties;

    public void setUp() {
        properties = new AimxcelProperties();

        properties.put( "string", "yoda" );
        properties.put( "double", "3.1415" );
        properties.put( "int", "1" );
        properties.put( "char", "a" );
    }

    public void testGetStringThatExists() {
        assertEquals( "yoda", properties.getString( "string" ) );
    }

    public void testGetStringThatDoesNotExistEqualsPropertyName() {
        assertEquals( "yoda", properties.getString( "yoda" ) );
    }

    public void testGetInt() {
        assertEquals( 1, properties.getInt( "int", -1 ) );
    }

    public void testGetDouble() {
        assertEquals( 3.1415, properties.getDouble( "double", 0.0 ), 0.0 );
    }

    public void testGetChar() {
        assertEquals( 'a', properties.getChar( "char", 'b' ) );
    }
}