
package com.aimxcel.abclearn.lwjgl.contrib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aimxcel.abclearn.lwjgl.StartupUtils;

/**
 * LWJGL (slightly modified) startup implementation, so we can be compatible with Aimxcel's simulation startup through JNLP
 */
public class LWJGLStartupImplementation {
    public static enum Platform {

        /**
         * Microsoft Windows 32 bit
         */
        Windows32,
        /**
         * Microsoft Windows 64 bit
         */
        Windows64,
        /**
         * Linux 32 bit
         */
        Linux32,
        /**
         * Linux 64 bit
         */
        Linux64,
        /**
         * Apple Mac OS X 32 bit
         */
        MacOSX32,
        /**
         * Apple Mac OS X 64 bit
         */
        MacOSX64,
        /**
         * Apple Mac OS X 32 bit PowerPC
         */
        MacOSX_PPC32,
        /**
         * Apple Mac OS X 64 bit PowerPC
         */
        MacOSX_PPC64,
    }

    public static Platform getPlatform() {
        String os = System.getProperty( "os.name" ).toLowerCase();
        String arch = System.getProperty( "os.arch" ).toLowerCase();
        boolean is64 = is64Bit( arch );
        if ( os.contains( "windows" ) ) {
            return is64 ? Platform.Windows64 : Platform.Windows32;
        }
        else if ( os.contains( "linux" ) || os.contains( "freebsd" ) || os.contains( "sunos" ) ) {
            return is64 ? Platform.Linux64 : Platform.Linux32;
        }
        else if ( os.contains( "mac os x" ) || os.contains( "darwin" ) ) {
            if ( arch.startsWith( "ppc" ) ) {
                return is64 ? Platform.MacOSX_PPC64 : Platform.MacOSX_PPC32;
            }
            else {
                return is64 ? Platform.MacOSX64 : Platform.MacOSX32;
            }
        }
        else {
            throw new UnsupportedOperationException( "The specified platform: " + os + " is not supported." );
        }
    }

    private static boolean is64Bit( String arch ) {
        if ( arch.equals( "x86" ) ) {
            return false;
        }
        else if ( arch.equals( "amd64" ) ) {
            return true;
        }
        else if ( arch.equals( "x86_64" ) ) {
            return true;
        }
        else if ( arch.equals( "ppc" ) || arch.equals( "PowerPC" ) ) {
            return false;
        }
        else if ( arch.equals( "ppc64" ) ) {
            return true;
        }
        else if ( arch.equals( "i386" ) || arch.equals( "i686" ) ) {
            return false;
        }
        else if ( arch.equals( "universal" ) ) {
            return false;
        }
        else {
            throw new UnsupportedOperationException( "Unsupported architecture: " + arch );
        }
    }

    private static final Logger logger = Logger.getLogger( StartupUtils.class.getName() );
    private static final byte[] buf = new byte[1024];

    protected static void extractNativeLib( File extractionDir, String sysName, String name ) throws IOException {
        extractNativeLib( extractionDir, sysName, name, false, true );
    }

    protected static void extractNativeLib( File extractionDir, String sysName, String name, boolean load, boolean warning ) throws IOException {
        String fullname = System.mapLibraryName( name );

        String path = "native/" + sysName + "/" + fullname;
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream( path );
        if ( in == null ) {
            if ( fullname.endsWith( ".dylib" ) ) {
                path = path.replaceFirst( ".dylib", ".jnilib" );
                in = Thread.currentThread().getContextClassLoader().getResourceAsStream( path );
            }
            if ( in == null ) {
                if ( !warning ) {
                    logger.log( Level.WARNING, "Cannot locate native library: {0}/{1}",
                                new String[] { sysName, fullname } );
                }
                return;
            }
        }
        File targetFile = new File( extractionDir, fullname );
        try {
            OutputStream out = new FileOutputStream( targetFile );
            int len;
            while ( ( len = in.read( buf ) ) > 0 ) {
                out.write( buf, 0, len );
            }
            in.close();
            out.close();
        }
        catch( FileNotFoundException ex ) {
            if ( ex.getMessage().contains( "used by another process" ) ) {
                return;
            }

            throw new RuntimeException( ex );
        }
        finally {
            if ( load ) {
                System.load( targetFile.getAbsolutePath() );
            }
        }
        logger.log( Level.INFO, "Copied {0} to {1}", new Object[]{fullname, targetFile} );
        System.out.println( targetFile );
    }

    public static void extractNativeLibs( File extractionDir, Platform platform, boolean loadSoundSupport, boolean loadJoystickSupport ) throws IOException {
        System.out.println( "Extraction Directory: " + extractionDir.getAbsolutePath() );

        // tell LWJGL to load libraries from this path
        System.setProperty( "org.lwjgl.librarypath", extractionDir.getAbsolutePath() );

        System.out.println( "Platform guess: " + platform );

        switch( platform ) {
            case Windows64:
                extractNativeLib( extractionDir, "windows", "lwjgl64" );
                if ( loadSoundSupport ) {
                    extractNativeLib( extractionDir, "windows", "OpenAL64" );
                }
                if ( loadJoystickSupport ) {
                    extractNativeLib( extractionDir, "windows", "jinput-dx8_64" );
                    extractNativeLib( extractionDir, "windows", "jinput-raw_64" );
                }
                break;
            case Windows32:
                extractNativeLib( extractionDir, "windows", "lwjgl" );
                if ( loadSoundSupport ) {
                    extractNativeLib( extractionDir, "windows", "OpenAL32" );
                }
                if ( loadJoystickSupport ) {
                    extractNativeLib( extractionDir, "windows", "jinput-dx8" );
                    extractNativeLib( extractionDir, "windows", "jinput-raw" );
                }
                break;
            case Linux64:
                extractNativeLib( extractionDir, "linux", "lwjgl64" );
                if ( loadJoystickSupport ) {
                    extractNativeLib( extractionDir, "linux", "jinput-linux64" );
                }
                if ( loadSoundSupport ) {
                    extractNativeLib( extractionDir, "linux", "openal64" );
                }
                break;
            case Linux32:
                extractNativeLib( extractionDir, "linux", "lwjgl" );
                if ( loadJoystickSupport ) {
                    extractNativeLib( extractionDir, "linux", "jinput-linux" );
                }
                if ( loadSoundSupport ) {
                    extractNativeLib( extractionDir, "linux", "openal" );
                }
                break;
            case MacOSX_PPC32:
            case MacOSX32:
            case MacOSX_PPC64:
            case MacOSX64:
                extractNativeLib( extractionDir, "macosx", "lwjgl" );
                //                if (needOAL)
//                    extractNativeLib("macosx", "openal");
                if ( loadJoystickSupport ) {
                    extractNativeLib( extractionDir, "macosx", "jinput-osx" );
                }
                break;
        }
    }
}
