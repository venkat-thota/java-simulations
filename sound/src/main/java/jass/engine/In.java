package jass.engine;

import java.util.Vector;


public abstract class In extends Thread implements Sink {
    protected Vector<Source> sourceContainer;

    public In() {
        sourceContainer = new Vector<Source>();
    }

    
    public Object addSource( Source s ) throws SinkIsFullException {
        sourceContainer.addElement( s );
        return null;
    }

   
    public void removeSource( Source s ) {
        sourceContainer.removeElement( s );
    }

    
    public Object[] getSources() {
        return sourceContainer.toArray();
    }
}

