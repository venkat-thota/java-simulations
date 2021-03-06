
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.aimxcel.abclearn.common.aimxcelcommon.util.OrderedProperties;

public class OrderedProperties {
    public static class OrderedPropertiesAdapter extends Properties {
        private OrderedProperties orderedProperties;

        public OrderedPropertiesAdapter( OrderedProperties orderedProperties ) {
            this.orderedProperties = orderedProperties;
        }

        public Enumeration propertyNames() {
            return orderedProperties.propertyNames();
        }
    }

    private Enumeration propertyNames() {
        ArrayList propertyNames = new ArrayList();
        for ( int i = 0; i < properties.size(); i++ ) {
            propertyNames.add( getEntry( i ).getKey() );
        }
        return new ListEnumeration( propertyNames );
    }

    private Entry getEntry( int i ) {
        return (Entry) properties.get( i );
    }

    private ArrayList properties = new ArrayList();

    public OrderedProperties( File propertyFile ) throws IOException {
        this( new FileInputStream( propertyFile ), new FileInputStream( propertyFile ) );
    }

    public OrderedProperties( InputStream is, InputStream copy ) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( is ) );
        Properties p = new Properties();
        p.load( copy );
        for ( String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine() ) {
            line = line.trim();
            if ( line.startsWith( "#" ) ) {//ignore comments
            }
            else {
                int equalsSignIndex = line.indexOf( '=' );
                if ( equalsSignIndex >= 1 ) {
                    String key = line.substring( 0, equalsSignIndex ).trim();
                    if ( p.containsKey( key ) ) {

                        if ( containsKey( key ) ) {
                            System.out.println( "Found duplicate key " + key + " in file: " + is );
                        }
                        else {
                            //String value = line.substring( equalsSignIndex + 1 ).trim();//fails for multiline properties, escaped characters, etc
                            properties.add( new Entry( key, p.getProperty( key ) ) );
                        }
                    }
                }
            }
        }
        int javaPropertyKeyCount = p.keySet().size();
        int ourKeyCount = properties.size();
        if ( javaPropertyKeyCount != ourKeyCount ) {
            if ( javaPropertyKeyCount < ourKeyCount ) {
                System.out.println( "Java missed " + ( ourKeyCount - javaPropertyKeyCount ) + " properties" );
                for ( int i = 0; i < properties.size(); i++ ) {
                    Entry entry = (Entry) properties.get( i );
                    if ( !p.containsKey( entry.getKey() ) ) {
                        System.out.println( "Java is missing key: " + entry.getKey() );
                    }
                }
            }
            else {
                System.out.println( "We missed " + ( javaPropertyKeyCount - ourKeyCount ) + " properties" );
                Set keys = p.keySet();
                for ( Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
                    String key = (String) iterator.next();
                    if ( !containsKey( key ) ) {
                        System.out.println( "We are missing key=" + key + " java property value=" + p.get( key ) );
                    }

                }
            }

            new RuntimeException( "Wrong key size for file: " + is + ", propertyKeys=" + javaPropertyKeyCount + ", readKeys=" + ourKeyCount ).printStackTrace();
        }
    }

    private boolean containsKey( String key ) {
        for ( int i = 0; i < properties.size(); i++ ) {
            Entry entry = (Entry) properties.get( i );
            if ( entry.getKey().equals( key ) ) {
                return true;
            }
        }
        return false;
    }

    private static class Entry {
        String key;
        String value;

        private Entry( String key, String value ) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
            return key + " = " + value;
        }
    }

    public String toString() {
        return properties.toString();
    }

    private String toStringMultiLine() {
        StringBuffer str = new StringBuffer();
        for ( int i = 0; i < properties.size(); i++ ) {
            Entry entry = (Entry) properties.get( i );
            str.append( entry );
            str.append( System.getProperty( "line.separator" ) );
        }
        return str.toString();
    }

    public static void main( String[] args ) throws IOException {
//        AimxcelProperties aimxcelProperties = new AimxcelProperties( new File( "C:\\reid\\aimxcel\\svn\\trunk\\simulations-java\\common\\aimxcelcommon\\data\\aimxcelcommon\\localization\\aimxcelcommon-strings.properties" ) );
//        AimxcelProperties aimxcelProperties = new AimxcelProperties( new File( "C:\\reid\\aimxcel\\svn\\trunk\\simulations-java\\simulations\\ohm-1d\\data\\ohm-1d\\localization\\ohm-1d-strings_es.properties" ) );
//        AimxcelProperties aimxcelProperties = new AimxcelProperties( new File( "C:\\reid\\aimxcel\\svn\\trunk\\simulations-java\\simulations\\bound-states\\data\\bound-states\\localization\\bound-states-strings_es.properties" ) );
//        System.out.println( "aimxcelProperties = " + aimxcelProperties );
//        System.out.println( "aimxcelProperties = \n" + aimxcelProperties.toStringMultiLine() );
//        testListEnumeration();
        searchAndTest( new File( "C:\\reid\\aimxcel\\svn\\trunk\\simulations-java" ) );
    }

    private static void testListEnumeration() {
        ArrayList list = new ArrayList();
        list.add( "a" );
        list.add( "b" );
        list.add( "c" );
        Enumeration e = new ListEnumeration( list );
        while ( e.hasMoreElements() ) {
            Object o = e.nextElement();
            System.out.println( "o = " + o );
        }
    }

    private static void searchAndTest( File dir ) throws IOException {
        File[] f = dir.listFiles();
        for ( int i = 0; i < f.length; i++ ) {
            File file = f[i];
            if ( file.isDirectory() ) {
                searchAndTest( file );
            }
            else {
                if ( file.getName().endsWith( ".properties" ) ) {
                    OrderedProperties aimxcelProperties = new OrderedProperties( file );
                    //assertion shouldn't fail
                    System.out.println( "Safely Loaded properties file: " + file.getAbsolutePath() );
                }
            }
        }
    }

    private static class ListEnumeration implements Enumeration {
        private AbstractList list;
        private int index = 0;

        public ListEnumeration( AbstractList list ) {
            this.list = list;
        }

        public boolean hasMoreElements() {
            return list.size() > index;
        }

        public Object nextElement() {
            return list.get( index++ );
        }
    }
}
