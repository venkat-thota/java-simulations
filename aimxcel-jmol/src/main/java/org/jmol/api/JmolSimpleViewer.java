package org.jmol.api;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.jmol.viewer.Viewer;

/**
 * This is the high-level API for the JmolViewer for simple access.
 **/

abstract public class JmolSimpleViewer {

  /**
   *  This is the main access point for creating an application
   *  or applet viewer. 
   *    
   * @param container
   * @param jmolAdapter
   * @return              a JmolViewer object
   */
  static public JmolSimpleViewer
    allocateSimpleViewer(Container container, JmolAdapter jmolAdapter) {
    return Viewer.allocateViewer(container, jmolAdapter, 
        null, null, null, null, null);
  }

  abstract public void renderScreenImage(Graphics g, Dimension size,
                                         Rectangle clip);

  abstract public String evalFile(String strFilename);
  abstract public String evalString(String strScript);

  abstract public String openStringInline(String strModel);
  abstract public String openDOM(Object DOMNode);
  abstract public String openFile(String fileName);
  abstract public String openFiles(String[] fileNames);
  // File reading now returns the error directly.
  // The following was NOT what you think it was:
  //   abstract public String getOpenFileError();
  // Somewhere way back when, "openFile" became a method that did not create
  // the model set, but just an intermediary AtomSetCollection called the "clientFile"
  // (and did not necessarily close the file)
  // then "getOpenFileError()" actually created the model set, deallocated the file open thread,
  // and closed the file.
  //
  // For Jmol 11.7.14, the openXXX methods in this interface do everything --
  // open the file, create the intermediary atomSetCollection, close the file,
  // deallocate the file open thread, create the ModelSet, and return any error message.
  // so there is no longer any need for getOpenFileError().
  
  /**
   * @param returnType "JSON", "string", "readable", and anything else returns the Java object.
   * @param infoType 
   * @param paramInfo  
   * @return            property data -- see org.jmol.viewer.PropertyManager.java
   */
  abstract public Object getProperty(String returnType, String infoType, Object paramInfo);
}
