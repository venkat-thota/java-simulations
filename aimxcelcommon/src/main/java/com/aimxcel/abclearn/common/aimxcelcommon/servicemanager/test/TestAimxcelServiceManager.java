
package com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;

/**
 * User: Sam Reid
 * Date: Jun 22, 2004
 * Time: 12:53:20 AM
 */
public class TestAimxcelServiceManager {
    public static boolean showURL( URL url ) {
        try {
            // Lookup the javax.jnlp.BasicService object
            BasicService bs = (BasicService) ServiceManager.lookup( "javax.jnlp.BasicService" );
            // Invoke the showDocument method
            return bs.showDocument( url );
        }
        catch ( UnavailableServiceException ue ) {
            // Service is not supported
            return false;
        }
    }

    public static void main( String[] args ) throws MalformedURLException, UnavailableServiceException {
        URL url = new URL( "http://www.google.com" );
        boolean ok = showURL( url );
        System.out.println( "ok = " + ok );
        AimxcelServiceManager.getBasicService().showDocument( url );
    }
}
