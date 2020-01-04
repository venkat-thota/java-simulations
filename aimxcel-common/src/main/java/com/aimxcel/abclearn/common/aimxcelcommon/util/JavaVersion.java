

package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.text.DecimalFormat;

import com.aimxcel.abclearn.common.aimxcelcommon.util.JavaVersion;


public abstract class JavaVersion {

    private final String version;
    private int majorNumber, minorNumber, maintenanceNumber, updateNumber;
    private String identifier;

  
    public static class JREVersion extends JavaVersion {

        public JREVersion() {
            
            super( System.getProperty( "java.runtime.version" ) );
        }
    }

   
    private JavaVersion( String s ) {

        version = s;

        String majorString = null;
        String minorString = null;
        String maintenanceString = null;
        String updateString = "0";

        int iPrev = 0;
        int i = 0;

        try {
            i = s.indexOf( '.' );
            majorString = s.substring( iPrev, i );

            iPrev = i + 1;
            i = s.indexOf( '.', iPrev );
            if ( i != -1 ) {
                minorString = s.substring( iPrev, i );
            }

            iPrev = i + 1;
            i = s.indexOf( '_', iPrev );
            if ( i != -1 ) {
                // n.n.n_nn
                maintenanceString = s.substring( iPrev, i );

                iPrev = i + 1;
                i = s.indexOf( '-', iPrev );
                if ( i != -1 ) {
                    // n.n.n_nn-identifier
                    updateString = s.substring( iPrev, i );
                    iPrev = i + 1;
                    identifier = s.substring( iPrev );
                }
                else {
                    // n.n.n_nn
                    updateString = s.substring( iPrev );
                }
            }
            else {
                updateString = null;
                // n.n.n
                i = s.indexOf( '-', iPrev );
                if ( i != -1 ) {
                    // n.n.n-identifier
                    maintenanceString = s.substring( iPrev, i );
                    iPrev = i + 1;
                    identifier = s.substring( iPrev );
                }
                else {
                    // n.n.n
                    maintenanceString = s.substring( iPrev );
                }
            }


            majorNumber = Integer.valueOf( majorString ).intValue();
            if ( minorString != null ) {
                minorNumber = Integer.valueOf( minorString ).intValue();
            }
            else {
                minorNumber = 0;
            }
            if ( maintenanceString != null ) {
                maintenanceNumber = Integer.valueOf( maintenanceString ).intValue();
            }
            else {
                maintenanceNumber = 0;
            }
            if ( updateString != null ) {
                updateNumber = Integer.valueOf( updateString ).intValue();
            }

            // if parsing worked correctly, the individual components can be reassembled into the original string
            assert ( version.equals( toString() ) );
        }
        catch ( StringIndexOutOfBoundsException e ) {
            System.err.println( "JavaVersion: StringIndexOutOfBoundsException parsing " + version );
        }
        catch ( NumberFormatException e ) {
            System.err.println( "JavaVersion: NumberFormatException parsing " + version );
        }
    }

  
    public String getVersion() {
        return version;
    }

    
    public int getMajorNumber() {
        return majorNumber;
    }

    
    public int getMinorNumber() {
        return minorNumber;
    }

   
    public int getMaintenanceNumber() {
        return maintenanceNumber;
    }

    
    public int getUpdateNumber() {
        return updateNumber;
    }

   
    public String getIdentifier() {
        return identifier;
    }

   
    public boolean isFeatureRelease() {
        return ( maintenanceNumber == 0 );
    }

   
    public boolean isMaintenanceRelease() {
        return ( maintenanceNumber != 0 );
    }

   
    public boolean isUpdateRelease() {
        return ( updateNumber != 0 );
    }

   
    public boolean isGARelease() {
        return ( identifier == null );
    }

    
    public boolean isFCSRelease() {
        return isGARelease();
    }

   
    public String toString() {
        String s = majorNumber + "." + minorNumber + "." + maintenanceNumber;
        if ( updateNumber != 0 ) {
            s += "_" + new DecimalFormat( "00" ).format( updateNumber );
        }
        if ( identifier != null ) {
            s += "-" + identifier;
        }
        return s;
    }

    // test
    public static void main( String[] args ) {

        JREVersion jre = new JREVersion();
        System.out.println( "JREVersion " + jre.getVersion() + " -> " + jre.toString() );

        // base class parser, positive tests
        String[] tests = { "1.3.0", "1.3.0_01", "1.3.0-b24", "1.3.1-beta-b09", "1.3.1_05-ea-b01", "1.4.0_03-ea-b01" };
        for ( int i = 0; i < tests.length; i++ ) {
            JavaVersion jtest = new JavaVersion( tests[i] ) {
            };
            System.out.println( "+parser " + tests[i] + " -> " + jtest.toString() );
        }

        // base class parser, negative tests
        String[] fail = { "", "a", "1", "1.3", "1.3.", "1.3.0_", "1.3.0_ea" };
        for ( int i = 0; i < fail.length; i++ ) {
            JavaVersion jtest = new JavaVersion( fail[i] ) {
            };
            System.out.println( "-parser " + fail[i] + " -> " + jtest.toString() );
        }
    }
}
