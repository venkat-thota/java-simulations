package jass.engine;

public class UnsupportedAudioFileFormatException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnsupportedAudioFileFormatException( String s ) {
        super( s );
    }

    public UnsupportedAudioFileFormatException() {
        super();
    }
}
