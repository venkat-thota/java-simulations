package org.jmol.console;


import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JMenuItem;

public class KeyJMenuItem extends JMenuItem implements GetKey {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String key;
  public String getKey() {
    return key;
  }
  
  public static String getKey(Object obj) {
    return (((GetKey)obj).getKey());
  }
  
  public KeyJMenuItem(String key, String label, Map<String, AbstractButton> menuMap) {
    super(getLabelWithoutMnemonic(label));
    this.key = key;
    map(this, key, label, menuMap);
  }
  
  public static void setAbstractButtonLabels(Map<String, AbstractButton> menuMap,
                               Map<String, String> labels) {
    Iterator<String> e = menuMap.keySet().iterator();
    while (e.hasNext()) {
      String key = e.next();
      AbstractButton m = menuMap.get(key);
      String label = labels.get(key);
      if (key.indexOf("Tip") == key.length() - 3) {
        m.setToolTipText(labels.get(key));
      } else {
        char mnemonic = KeyJMenuItem.getMnemonic(label);
        if (mnemonic != ' ')
          m.setMnemonic(mnemonic);
        label = KeyJMenuItem.getLabelWithoutMnemonic(label);
        m.setText(label);
      }
    }
  }
  
  static String getLabelWithoutMnemonic(String label) {
    if (label == null) {
      return null;
    }
    int index = label.indexOf('&');
    if (index == -1) {
      return label;
    }
    return label.substring(0, index) +
      ((index < label.length() - 1) ? label.substring(index + 1) : "");
  }
  
  static char getMnemonic(String label) {
    if (label == null) {
      return ' ';
    }
    int index = label.indexOf('&');
    if ((index == -1) || (index == label.length() - 1)){
      return ' ';
    }
    return label.charAt(index + 1);
  }

  static void map(AbstractButton button, String key, String label,
                         Map<String, AbstractButton> menuMap) {
    char mnemonic = KeyJMenuItem.getMnemonic(label);
    if (mnemonic != ' ')
      button.setMnemonic(mnemonic);
    menuMap.put(key, button);
  }

}


