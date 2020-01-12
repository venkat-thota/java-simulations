package jass.engine;



public abstract class FilterUG extends InOut {

    public FilterUG( int bufferSize ) {
        super( bufferSize );
    }

    
    public Object addSource( Source s ) throws SinkIsFullException {
        if( getSources().length > 0 ) {
            throw new SinkIsFullException();
        }
        else {
            return super.addSource( s );
        }
    }
}
