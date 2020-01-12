
package org.jmol.smiles;

/**
 * Exception thrown for invalid SMILES String
 */
public class InvalidSmilesException extends Exception {

  private static String lastError;

  public static String getLastError() {
    return lastError;
  }
  
  public static void setLastError(String message) {
    lastError = message;
  }
  
  /**
   * Constructs a <code>InvalideSmilesException</code> without any detail.
   */
  public InvalidSmilesException() {
    super();
  }

  /**
   * Constructs a <code>InvalidSmilesException</code> with a detail message.
   * 
   * @param message The detail message.
   */
  public InvalidSmilesException(String message) {
    super(message);
    lastError = message;
    printStackTrace();
  }

  /**
   * Contructs a <code>InvalidSmilesException</code> with the specified cause and
   * a detail message of <tt>(cause == null ? null : cause.toString())</tt>
   * (which typically contains the class and detail message of <tt>cause</tt>).
   * 
   * @param cause The cause.
   */
  public InvalidSmilesException(Throwable cause) {
    super(cause);
    lastError = cause.getMessage();
  }

  /**
   * Construcst a <code>InvalidSmilesException</code> with the specified detail
   * message and cause.
   * 
   * @param message The detail message.
   * @param cause The cause.
   */
  public InvalidSmilesException(String message, Throwable cause) {
    super(message, cause);
    lastError = message + "\n" + cause.getCause();
  }
}
