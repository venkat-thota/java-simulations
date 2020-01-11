
package org.jmol.export.dialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

/**
 * JFileChooser with possibility to fix size and location
 */
public class FileChooser extends JFileChooser {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Point dialogLocation = null;
  private Dimension dialogSize = null;
  private JDialog dialog = null;

  @Override
  protected JDialog createDialog(Component parent) {
    dialog = super.createDialog(parent);
    if (dialog != null) {
      if (dialogLocation != null) {
        dialog.setLocation(dialogLocation);
      }
      if (dialogSize != null) {
        dialog.setSize(dialogSize);
      }
    }
    return dialog;
  }

  /**
   * @param p Location of the JDialog
   */
  public void setDialogLocation(Point p) {
  	dialogLocation = p;
  }
  
  /**
   * @param d Size of the JDialog
   */
  public void setDialogSize(Dimension d) {
    dialogSize = d;
  }
  
  /**
   * @return Dialog containing the JFileChooser
   */
  public JDialog getDialog() {
    return dialog;
  }
}
