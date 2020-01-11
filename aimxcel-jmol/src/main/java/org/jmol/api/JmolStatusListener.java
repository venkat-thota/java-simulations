
package org.jmol.api;

import java.applet.Applet;
import java.util.Map;

public interface JmolStatusListener extends JmolCallbackListener {


  public String eval(String strEval);
  
  /**
   * for isosurface FUNCTIONXY 
   * 
   * @param functionName
   * @param x
   * @param y
   * @return 2D array or null
   */
  public float[][] functionXY(String functionName, int x, int y);
  
  /**
   * for isosurface FUNCTIONXYZ 
   * 
   * @param functionName
   * @param nx
   * @param ny
   * @param nz
   * @return 3D array or null
   */
  public float[][][] functionXYZ(String functionName, int nx, int ny, int nz);

  /**
   * Starting with Jmol 11.8.RC5, for a context where the Jmol application
   * is embedded in another application simply to send the returned message
   * to the application. In this way any application can have access to the WRITE
   * command.
   * 
   * @param fileName
   * @param type
   * @param text_or_bytes information or null indicates message AFTER Jmol creates the image
   * @param quality
   * @return          null (canceled) or a message starting with OK or an error message
   */
  public String createImage(String fileName, String type, Object text_or_bytes, int quality);

  public Map<String, Applet> getRegistryInfo();

  public void showUrl(String url);

}
