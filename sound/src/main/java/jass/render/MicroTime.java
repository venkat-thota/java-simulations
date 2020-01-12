package jass.render;

public class MicroTime {
    private static boolean useNative = false;

    static {
        try {
            System.loadLibrary( "MicroTime" );
            useNative = true;
        }
        catch( java.lang.UnsatisfiedLinkError e ) {
            System.out.println( "Using Java Time function in MicroTime" );
            useNative = false;
        }
    }

    /**
     * Return the time with microsecond accuracy if possible
     */
    public static double getTime() {
        if( useNative ) {
            return getMicroTime();
        }
        else {
            return System.currentTimeMillis() / 1000.;
        }
    }

    /**
     * Return the time in seconds accurate to microseconds ar least
     */
    private static native double getMicroTime();

}

