package org.jmol.console;


import java.util.Map;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.AbstractButton;

public class KeyJCheckBoxMenuItem extends JCheckBoxMenuItem implements GetKey {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String key;
    public String getKey() {
      return key;
    }

  public KeyJCheckBoxMenuItem(String key, String label, Map<String, AbstractButton> menuMap,
      boolean isChecked) {
    super(KeyJMenuItem.getLabelWithoutMnemonic(label), isChecked);
    this.key = key;
    KeyJMenuItem.map(this, key, label, menuMap);
  }

}

