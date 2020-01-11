package org.jmol.modelkit;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jmol.api.*;
import org.jmol.i18n.GT;
import org.jmol.popup.PopupResource;
import org.jmol.popup.SimplePopup;
import org.jmol.util.Elements;

class ModelKitPopup extends SimplePopup {

  ModelKitPopup(JmolViewer viewer, String title, PopupResource bundle) {
    super(viewer, title, bundle, false);
  }

  @Override
  protected ImageIcon getIcon(String name) {
    String imageName = "org/jmol/modelkit/images/" + name;
    URL imageUrl = this.getClass().getClassLoader().getResource(imageName);
    if (imageUrl != null) {
      return new ImageIcon(imageUrl);
    }
    return null;
  }

  @Override
  public void checkMenuClick(Object source, String script) {
    if (script.equals("clearQ")) {
      for (JMenuItem item : htCheckbox.values()) {
        if (item.getActionCommand().indexOf(":??") < 0)
          continue;
        
        setLabel(item, "??");
        item.setActionCommand("_??P!:");
        item.setSelected(false);
        item.setArmed(false);
      }
      viewer.evalStringQuiet("set picking assignAtom_C");
      return;
    }
    super.checkMenuClick(source, script);  
  }
  
  @Override
  protected String setCheckBoxOption(JMenuItem item, String name, String what) {
    // atom type
    String element = JOptionPane.showInputDialog(GT._("Element?"), "");
    if (element == null || Elements.elementNumberFromSymbol(element, true) == 0)
      return null;
    setLabel(item, element);
    item.setActionCommand("assignAtom_" + element + "P!:??");
    return "set picking assignAtom_" + element;
  }
  
}
