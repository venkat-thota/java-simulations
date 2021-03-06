
package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class NetworkUtils {

    public static void download( String urlAddress, File file ) throws FileNotFoundException {
        file.getParentFile().mkdirs();
        try {
            OutputStream outputStream = new BufferedOutputStream( new FileOutputStream( file ) );
            InputStream inputStream = new URL( urlAddress ).openConnection().getInputStream();
            byte[] data = new byte[2048];
            int read = 0;
            while ( ( read = inputStream.read( data ) ) != -1 ) {
                outputStream.write( data, 0, read );
            }
            inputStream.close();
            outputStream.close();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
