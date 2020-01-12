package org.openscience.jmol.app.webexport;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jmol.i18n.GT;

class LogPanel {

  private static JTextArea logArea;
  private static JTextArea miniLogArea;
  private static boolean resetFlag;

  static JPanel getPanel() {
    //Now layout the LogPanel.  It will be added to the tabs in the main class.

    //Create the log first, because the action listeners
    //need to refer to it.
    logArea = new JTextArea(30,20);
    logArea.setMargin(new Insets(5, 5, 5, 5));
    logArea.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane(logArea);

    //Create a panel of the log and its label
    JPanel logPanel = new JPanel();
    logPanel.setLayout(new BorderLayout());
    logPanel.setBorder(BorderFactory.createTitledBorder(GT._("Log and Error Messages:")));
    logPanel.add(logScrollPane);
    return logPanel;
  }

  static JPanel getMiniPanel() {
    JPanel miniPanel = new JPanel();
    miniPanel.setLayout(new BorderLayout());
    miniPanel.setBorder(BorderFactory.createTitledBorder(GT._("Messages (see Log tab for full history):")));
    miniLogArea = new JTextArea(2,20);
    miniLogArea.setEditable(false);
    JScrollPane miniScrollPane = new JScrollPane(miniLogArea);
    miniPanel.add(miniScrollPane);
    return miniPanel;
  }

  static void log(String message) {
    if (resetFlag){
      logArea.setText("");
      miniLogArea.setText("");
    }
    resetFlag = (message.length() == 0);
    logArea.append(message + "\n");
    miniLogArea.append(message + "\n");
    logArea.setCaretPosition(logArea.getDocument().getLength());
    miniLogArea.setCaretPosition(miniLogArea.getDocument().getLength());
  }

  static String getText() {
    return logArea.getText();
  }  
}
