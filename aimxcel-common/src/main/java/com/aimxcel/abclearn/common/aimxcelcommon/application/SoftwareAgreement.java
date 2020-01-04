
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.aimxcel.abclearn.common.aimxcelcommon.application.SoftwareAgreement;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelProperties;

public class SoftwareAgreement {

    private static final String RESOURCE_NAME = "software-agreement.properties";
    private static final String HTML_RESOURCE_NAME = "software-agreement.htm";
    private static final String KEY_VERSION = "version";
    private static final int DEFAULT_VERSION = -1;

    // singleton instance
    private static SoftwareAgreement instance;

    private final int version;
    private String content;

    // singleton
    private SoftwareAgreement() {
        AimxcelProperties p = readProperties();
        version = p.getInt( KEY_VERSION, DEFAULT_VERSION );
        try {
            content = readContent();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private String readContent() throws IOException {
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( HTML_RESOURCE_NAME );
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inStream ) );
        String s = "";
        String line = bufferedReader.readLine();
        while ( line != null ) {
            s += line + "\n";
            line = bufferedReader.readLine();
        }
        return s;
    }

    public static SoftwareAgreement getInstance() {
        if ( instance == null ) {
            instance = new SoftwareAgreement();
        }
        return instance;
    }

    /**
     * Version number is a monotonically-increasing integer.
     *
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     * Content of the agreement is an HTML string.
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    // TODO: use a standard resource loader
    private AimxcelProperties readProperties() {
        AimxcelProperties p = new AimxcelProperties();
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( RESOURCE_NAME );
        if ( inStream != null ) {
            try {
                p.load( inStream );
            }
            catch ( IOException e ) {
                System.err.println( "exception reading software agreement: " + RESOURCE_NAME );
                e.printStackTrace();
            }
        }
        else {
            System.err.println( "missing software agreement: " + RESOURCE_NAME );
        }
        return p;
    }
}
