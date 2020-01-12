package com.aimxcel.abclearn.greenhouse.util;

public class MessageFormatter {

    public static String format( String msg ) {
        StringBuffer outString = new StringBuffer( "<html>" );
        int lastIdx = 0;
        for ( int nextIdx = msg.indexOf( "\n", lastIdx );
              nextIdx != -1;
              nextIdx = msg.indexOf( "\n", lastIdx ) ) {
            outString.append( msg.substring( lastIdx, nextIdx ) );
            if ( nextIdx < msg.length() ) {
                outString.append( "<br>" );
            }
            lastIdx = nextIdx + 1;
        }
        outString.append( msg.substring( lastIdx, msg.length() ) );
        outString.append( "</html>" );
        return outString.toString();
    }
}
