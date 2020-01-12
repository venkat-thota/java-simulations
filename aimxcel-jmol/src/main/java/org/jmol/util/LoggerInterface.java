
package org.jmol.util;

/**
 * Interface used for the logging mechanism.
 */
public interface LoggerInterface {

  /**
   * Writes a log at DEBUG level.
   * 
   * @param txt String to write.
   */
  public void debug(String txt);

  /**
   * Writes a log at INFO level.
   * 
   * @param txt String to write.
   */
  public void info(String txt);

  /**
   * Writes a log at WARN level.
   * 
   * @param txt String to write.
   */
  public void warn(String txt);

  /**
   * Writes a log at WARN level with detail on exception.
   * 
   * @param txt String to write.
   * @param e Exception.
   */
  public void warn(String txt, Throwable e);

  /**
   * Writes a log at ERROR level.
   * 
   * @param txt String to write.
   */
  public void error(String txt);

  /**
   * Writes a log at ERROR level with detail on exception.
   * 
   * @param txt String to write.
   * @param e Exception.
   */
  public void error(String txt, Throwable e);

  /**
   * Writes a log at FATAL level.
   * 
   * @param txt String to write.
   */
  public void fatal(String txt);

  /**
   * Writes a log at ERROR level with detail on exception.
   * 
   * @param txt String to write.
   * @param e Exception.
   */
  public void fatal(String txt, Throwable e);
}
