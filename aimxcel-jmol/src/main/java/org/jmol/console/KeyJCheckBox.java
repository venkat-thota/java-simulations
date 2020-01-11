package org.jmol.console;


import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;

public class KeyJCheckBox extends JCheckBox implements GetKey {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String key;
    public String getKey() {
      return key;
    }

  public KeyJCheckBox(String key, String label, Map<String, AbstractButton> menuMap,
      boolean isChecked) {
    super(KeyJMenuItem.getLabelWithoutMnemonic(label), isChecked);
    this.key = key;
    KeyJMenuItem.map(this, key, label, menuMap);
  }

  public static void updateLabel(JCheckBox m, String label, char mnemonic) {
    if (mnemonic != ' ')
      m.setMnemonic(mnemonic);
    label = KeyJMenuItem.getLabelWithoutMnemonic(label);
    m.setText(label);
  }
}

