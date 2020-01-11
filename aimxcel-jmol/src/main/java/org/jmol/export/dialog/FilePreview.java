package org.jmol.export.dialog;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.jmol.api.JmolAdapter;
import org.jmol.api.JmolViewer;
import org.jmol.i18n.GT;
import org.jmol.viewer.FileManager;

public class FilePreview extends JPanel implements PropertyChangeListener {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JCheckBox active = null;
  JCheckBox append = null;
  JFileChooser chooser = null;
  private FPPanel display = null;

  /**
   * Constructor
   * @param fileChooser File chooser
   * @param modelAdapter Model adapter
   * @param allowAppend 
   * @param appletContext 
   */
  public FilePreview(JFileChooser fileChooser, JmolAdapter modelAdapter,
      boolean allowAppend, String appletContext) {
    super();
    chooser = fileChooser;

    // Create a box to do the layout
    Box box = Box.createVerticalBox();

    // Add a checkbox to activate / deactivate preview
    active = new JCheckBox(GT._("Preview"), false);
    active.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (active.isSelected()) {
          updatePreview(chooser.getSelectedFile());
        } else {
          updatePreview(null);
        }
      }
    });
    box.add(active);

    // Add a preview area
    display = new FPPanel(modelAdapter, appletContext);
    display.setPreferredSize(new Dimension(80, 80));
    display.setMinimumSize(new Dimension(50, 50));
    box.add(display);

    if (allowAppend) {
      // Add a checkbox to append date
      append = new JCheckBox(GT._("Append models"), false);
      box.add(append);
    }

    // Add the preview to the File Chooser
    add(box);
    fileChooser.setAccessory(this);
    fileChooser.addPropertyChangeListener(this);
  }

  /**
   * @return Indicates if Append is selected.
   */
  public boolean isAppendSelected() {
    if (append != null) {
      return append.isSelected();
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
   */
  public void propertyChange(PropertyChangeEvent evt) {
    if (active.isSelected()) {
      String prop = evt.getPropertyName();
      if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
        updatePreview((File) evt.getNewValue());
      }
    }
  }

  /**
   * Update preview
   * 
   * @param file File selected
   */
  void updatePreview(File file) {
    String script;
    if (file == null) {
      script = "zap";
    } else {
      String fileName = file.getAbsolutePath();
      //System.out.println("updatePreview "+ fileName + " " + chooser.getSelectedFile());
      String url = FileManager.getLocalUrl(file);
      //System.out.println("updatePreview + " + fileName + " " + url);
      if (url != null)
        fileName = url;
      //doesn't update from use input?
      script = " \"" + fileName + "\"";
      if (fileName.indexOf(".spt") >= 0) 
        script = "script " + script;
      else
        script = "zap;set zoomlarge false;load " + script
            + " 1;select protein or dna;cartoons only;color structure;"; 
    }
    display.getViewer().evalStringQuiet(script);
    //display.repaint();
  }

  private static class FPPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JmolViewer viewer;

    FPPanel(JmolAdapter modelAdapter, String appletContext) {
      viewer = JmolViewer.allocateViewer(this, modelAdapter,
          "", null, null, "#previewOnly " + appletContext, null);
    }

    public JmolViewer getViewer() {
      return viewer;
    }

    final Dimension currentSize = new Dimension();

    @Override
    public void paint(Graphics g) {
      viewer.setScreenDimension(getSize(currentSize));
      Rectangle rectClip = new Rectangle();
      g.getClipBounds(rectClip);
      viewer.renderScreenImage(g, currentSize, rectClip);
    }
  }
}
