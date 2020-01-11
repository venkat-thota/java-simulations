package com.aimxcel.abclearn.aimxcel2dcore.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
public class PObjectOutputStream extends ObjectOutputStream {

    private boolean writingRoot;
    private final HashMap unconditionallyWritten;

    /**
     * Transform the given object into an array of bytes.
     * 
     * @param object the object to be transformed
     * @return array of bytes representing the given object
     * @throws IOException when serialization system throws one
     */
    public static byte[] toByteArray(final Object object) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final PObjectOutputStream zout = new PObjectOutputStream(out);
        zout.writeObjectTree(object);
        return out.toByteArray();
    }

    /**
     * Constructs a PObjectOutputStream that wraps the provided OutputStream.
     * 
     * @param out underlying outputstream that will receive the serialized
     *            objects
     * 
     * @throws IOException when underlying subsystem throws one
     */
    public PObjectOutputStream(final OutputStream out) throws IOException {
        super(out);
        unconditionallyWritten = new HashMap();
    }

    /**
     * Writes the provided object to the underlying stream like an ordination
     * ObjectOutputStream except that it does not record duplicates at all.
     * 
     * @param object object to be serialized
     * 
     * @throws IOException when underlying subsystem throws one
     */
    public void writeObjectTree(final Object object) throws IOException {
        writingRoot = true;
        recordUnconditionallyWritten(object); // record pass
        writeObject(object); // write pass
        writingRoot = false;
    }

    /**
     * Writes the given object, but only if it was not in the object tree
     * multiple times.
     * 
     * @param object object to write to the stream.
     * @throws IOException when underlying subsystem throws one
     */
    public void writeConditionalObject(final Object object) throws IOException {
        if (!writingRoot) {
            throw new RuntimeException(
                    "writeConditionalObject() may only be called when a root object has been written.");
        }

        if (unconditionallyWritten.containsKey(object)) {
            writeObject(object);
        }
        else {
            writeObject(null);
        }
    }

    /**
     * Resets the ObjectOutputStream clearing any memory about objects already
     * being written while it's at it.
     * 
     * @throws IOException when underlying subsystem throws one
     */
    public void reset() throws IOException {
        super.reset();
        unconditionallyWritten.clear();
    }

    /**
     * Performs a scan of objects that can be serialized once.
     * 
     * @param aRoot Object from which to start the scan
     * @throws IOException when serialization fails
     */
    protected void recordUnconditionallyWritten(final Object aRoot) throws IOException {
        class ZMarkObjectOutputStream extends PObjectOutputStream {
            public ZMarkObjectOutputStream() throws IOException {
                super(NULL_OUTPUT_STREAM);
                enableReplaceObject(true);
            }

            public Object replaceObject(final Object object) {
                unconditionallyWritten.put(object, Boolean.TRUE);
                return object;
            }

            public void writeConditionalObject(final Object object) throws IOException {
            }
        }
        new ZMarkObjectOutputStream().writeObject(aRoot);
    }

    private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() {
        public void close() {
        }

        public void flush() {
        }

        public void write(final byte[] b) {
        }

        public void write(final byte[] b, final int off, final int len) {
        }

        public void write(final int b) {
        }
    };
}
