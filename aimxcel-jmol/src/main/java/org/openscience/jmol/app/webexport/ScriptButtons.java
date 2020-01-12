package org.openscience.jmol.app.webexport;


import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jmol.api.JmolViewer;
import org.jmol.i18n.GT;
import org.jmol.util.TextFormat;

class ScriptButtons extends WebPanel {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

ScriptButtons(JmolViewer viewer, JFileChooser fc, WebPanel[] webPanels,
      int panelIndex) {
    super(viewer, fc, webPanels, panelIndex);
    panelName = "script_button";
    listLabel = GT._("These names will be used for button labels");
    //description = "Create a web page containing a text and button pane that scrolls next to a resizable Jmol applet";
  }

  @Override
  JPanel appletParamPanel() {
    SpinnerNumberModel appletSizeModel = new SpinnerNumberModel(WebExport.getScriptButtonPercent(), //initial value
        20, //min
        100, //max
        5); //step size
    appletSizeSpinnerP = new JSpinner(appletSizeModel);
    //panel to hold spinner and label
    JPanel appletSizePPanel = new JPanel();
    appletSizePPanel.add(new JLabel(GT._("% of window for applet width:")));
    appletSizePPanel.add(appletSizeSpinnerP);
    return (appletSizePPanel);
  }

  @Override
  String fixHtml(String html) {
    int size = ((SpinnerNumberModel) (appletSizeSpinnerP.getModel()))
        .getNumber().intValue();
    int leftpercent = 100 - size;
    int appletheightpercent = 100;
    int nbuttons = getInstanceList().getModel().getSize();
    if (!allSelectedWidgets().isEmpty())
      appletheightpercent = 85;
    html = TextFormat.simpleReplace(html, "@WIDTHPERCENT@", "" + size);
    html = TextFormat.simpleReplace(html, "@LEFTPERCENT@", "" + leftpercent);
    html = TextFormat.simpleReplace(html, "@NBUTTONS@", "" + nbuttons);
    html = TextFormat.simpleReplace(html, "@HEIGHT@", "" + appletheightpercent);
    return html;
  }

  @Override
  String getAppletDefs(int i, String html, StringBuffer appletDefs,
                       JmolInstance instance) {
    //TODO add widgets  Could have pure javascript update of widgets for each view.
    //The widgets should appear below the applet as in Angel's example.  The best
    //way to do this would be to build the widget div as a hidden div in the
    //scrolling region and then just copy it to the display area?
    String name = instance.name;
    String buttonname = instance.javaname;
    String widgetDefs = "";
    int row = 0;
    if (!instance.whichWidgets.isEmpty()) {
      widgetDefs += "<table border = \"0\" width=\"100%\"><tbody><tr>";
      for (int j = 0; j < nWidgets; j++) {
        if (instance.whichWidgets.get(j)) {
          if (row == 3) {
            widgetDefs += "</tr><tr>";
            row = 0;
          }
          widgetDefs += "<td>"
              + theWidgets.widgetList[j].getJavaScript(0, instance)
                  .replace("'", "\'") + "</td>";
          row = row + 1;
        }
      }
      widgetDefs += "</tr></tbody></table>";
    }
    if (i == 0)
      html = TextFormat.simpleReplace(html, "@APPLETNAME0@", GT.escapeHTML(buttonname));
    if (useAppletJS) {
      String info = "info for " + name;
      appletDefs.append("\naddAppletButton(" + i + ",'" + buttonname + "',\""
          + name + "\",\"" + info + "\");");
    } else {
      String s = htmlAppletTemplate;
      s = TextFormat.simpleReplace(s, "@APPLETNAME0@", GT.escapeHTML(buttonname));
      s = TextFormat.simpleReplace(s, "@NAME@", GT.escapeHTML(name));
      s = TextFormat.simpleReplace(s, "@LABEL@", GT.escapeHTML(name));
      s = TextFormat.simpleReplace(s, "@I@", ""+i);
      s = TextFormat.simpleReplace(s, "@WIDGETSTR@", widgetDefs);
      appletDefs.append(s);
    }
    return html;
  }
}
