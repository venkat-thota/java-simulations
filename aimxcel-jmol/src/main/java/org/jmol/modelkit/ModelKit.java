package org.jmol.modelkit;

import org.jmol.api.*;
import org.jmol.i18n.GT;
import org.jmol.util.Logger;
import org.jmol.viewer.Viewer;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class ModelKit extends JDialog implements JmolModelKitInterface {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JmolViewer viewer;
  ModelKitPopup modelkitMenu;

  public ModelKit() {
    // necessary for reflection
  }

  
  public JmolModelKitInterface getModelKit(Viewer viewer, Component display) {
    return new ModelKit(viewer,  display instanceof JmolFrame ? ((JmolFrame) display).getFrame() 
        : display instanceof JFrame ? (JFrame) display : null);
  }

  /**
   * @param parentFrame
   * @param viewer
   */
  private ModelKit(JmolViewer viewer, JFrame parentFrame) {
    super(parentFrame, "", false);
    this.viewer = viewer;
    
  }



  /* (non-Javadoc)
   * @see org.jmol.modelkit.JmolModelKitInterface#getMenus(boolean)
   */
  public void getMenus(boolean doTranslate) {
    GT.setDoTranslate(true);
    try {
      modelkitMenu = new ModelKitPopup(viewer, "modelkitMenu",
          new ModelKitPopupResourceBundle());
    } catch (Exception e) {
      Logger.error("Modelkit menus not loaded");
    }
    GT.setDoTranslate(doTranslate);
    
  }
  
  /* (non-Javadoc)
   * @see org.jmol.modelkit.JmolModelKitInterface#show(int, int, java.lang.String)
   */
  public void show(int x, int y, char type) {
      modelkitMenu.show(x, y);
  }  
}
