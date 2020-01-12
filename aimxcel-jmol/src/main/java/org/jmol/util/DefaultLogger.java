
package org.jmol.util;

import java.io.PrintStream;


/**
 * Default implementation of the logger.
 */
public class DefaultLogger implements LoggerInterface {

  /**
   * Method to output a log.
   * 
   * @param out Output stream.
   * @param level Log level.
   * @param txt Text to log.
   * @param e Exception.
   */
  protected void log(PrintStream out, int level, String txt, Throwable e) {
    if (out == System.err)
      System.out.flush();
    if ((out != null) && ((txt != null) || (e != null))) {
      txt = (txt != null ? txt : "");
      out.println(
          (Logger.logLevel() ? "[" + Logger.getLevel(level) + "] " : "") +
          txt +
          (e != null ? ": " + e.getMessage() : ""));
      if (e != null) {
        StackTraceElement[] elements = e.getStackTrace();
        if (elements != null) {
          for (int i = 0; i < elements.length; i++) {
            out.println(
                elements[i].getClassName() + " - " +
                elements[i].getLineNumber() + " - " +
                elements[i].getMethodName());
          }
        }
      }
    }
    if (out == System.err)
      System.err.flush();
  }

  /* (non-Javadoc)
   * @see org.jmol.util.LoggerInterface#debug(java.lang.String)
   */
  public void debug(String txt) {
    log(System.out, Logger.LEVEL_DEBUG, txt, null);
  }

  /* (non-Javadoc)
   * @see org.jmol.util.LoggerInterface#info(java.lang.String)
   */
  public void info(String txt) {
    log(System.out, Logger.LEVEL_INFO, txt, null);
  }

  /* (non-Javadoc)
   * @see org.jmol.util.LoggerInterface#warn(java.lang.String)
   */
  public void warn(String txt) {
    log(System.out, Logger.LEVEL_WARN, txt, null);
  }

  /* (non-Javadoc)
   * @see org.jmol.util.LoggerInterface#warn(java.lang.String, java.lang.Throwable)
   */
  public void warn(String txt, Throwable e) {
    log(System.out, Logger.LEVEL_WARN, txt, e);
  }

  /* (non-Javadoc)
   * @see org.jmol.util.LoggerInterface#error(java.lang.String)
   */
  public void error(String txt) {
    log(System.err, Logger.LEVEL_ERROR, txt, null);
  }

  /* (non-Javadoc)
   * @see org.jmol.util.LoggerInterface#error(java.lang.String, java.lang.Exception)
   */
  public void error(String txt, Throwable e) {
    log(System.err, Logger.LEVEL_ERROR, txt, e);
  }

  /* (non-Javadoc)
   * @see org.jmol.util.LoggerInterface#fatal(java.lang.String)
   */
  public void fatal(String txt) {
    log(System.err, Logger.LEVEL_FATAL, txt, null);
  }

  /* (non-Javadoc)
   * @see org.jmol.util.LoggerInterface#fatal(java.lang.String, java.lang.Exception)
   */
  public void fatal(String txt, Throwable e) {
    log(System.err, Logger.LEVEL_FATAL, txt, e);
  }
}
