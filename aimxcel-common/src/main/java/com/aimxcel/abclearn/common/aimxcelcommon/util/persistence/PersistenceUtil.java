

package com.aimxcel.abclearn.common.aimxcelcommon.util.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class PersistenceUtil {

   
    public static Serializable copy( Serializable object ) throws CopyFailedException {
        return copy( object, null );
    }

    public static interface CopyObjectReplacementStrategy {
        public Object replaceObject( Object obj );
    }

    public static Serializable copy( Serializable object, CopyObjectReplacementStrategy copyObjectReplacementStrategy ) throws CopyFailedException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOut = copyObjectReplacementStrategy == null ? new ObjectOutputStream( byteOut ) : new MyObjectOutputStream( byteOut, copyObjectReplacementStrategy );

            objectOut.writeObject( object );
            objectOut.flush();

            ByteArrayInputStream byteIn = new ByteArrayInputStream( byteOut.toByteArray() );
            return (Serializable) new ObjectInputStream( byteIn ).readObject();
        }
        catch ( IOException e ) {
            throw new CopyFailedException( e );
        }
        catch ( ClassNotFoundException e ) {
            throw new CopyFailedException( e );
        }
    }

    private static class MyObjectOutputStream extends ObjectOutputStream {
        private CopyObjectReplacementStrategy copyObjectReplacementStrategy;

        public MyObjectOutputStream( OutputStream out, CopyObjectReplacementStrategy copyObjectReplacementStrategy ) throws IOException {
            super( out );
            this.copyObjectReplacementStrategy = copyObjectReplacementStrategy;
//            enableReplaceObject( true );//fails under web start due to security restrictions
        }

        protected Object replaceObject( Object obj ) throws IOException {
            return copyObjectReplacementStrategy.replaceObject( obj );
        }
    }

    public static class CopyFailedException extends Exception {
        public CopyFailedException( Throwable cause ) {
            super( cause );
        }
    }
}
