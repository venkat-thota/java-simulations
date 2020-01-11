package org.jmol.console;


import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JMenu;

public class KeyJMenu extends JMenu implements GetKey {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String key;
  public String getKey() {
    return key;
  }
  
  public KeyJMenu(String key, String label, Map<String, AbstractButton> menuMap) {
    super(KeyJMenuItem.getLabelWithoutMnemonic(label));
    this.key = key;
    KeyJMenuItem.map(this, key, label, menuMap);
  }
}

