
package org.jmol.applet;

import org.jmol.appletwrapper.AppletWrapper;

import java.applet.Applet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import netscape.javascript.JSObject;

import org.jmol.util.Logger;
import org.jmol.util.TextFormat;

final class JmolAppletRegistry {

  static Map<String, Applet> htRegistry = new Hashtable<String, Applet>();

  synchronized static void checkIn(String name, Applet applet) {
    cleanRegistry();
    if (name != null) {
      Logger.info("AppletRegistry.checkIn(" + name + ")");
      htRegistry.put(name, applet);
    }
    if (Logger.debugging) {
      for (Map.Entry<String, Applet> entry : htRegistry.entrySet()) {
        String theApplet = entry.getKey();
        Logger.debug(theApplet + " " + entry.getValue());
      }
    }
  }

  synchronized static void checkOut(String name) {
    htRegistry.remove(name);
  }

  synchronized private static void cleanRegistry() {
    AppletWrapper app = null;
    boolean closed = true;
    for (Map.Entry<String, Applet> entry : htRegistry.entrySet()) {
      String theApplet = entry.getKey();
      try {
        app = (AppletWrapper) (entry.getValue());
        JSObject theWindow = JSObject.getWindow(app);
        //System.out.print("checking " + app + " window : ");
        closed = ((Boolean) theWindow.getMember("closed")).booleanValue();
        //System.out.println(closed);
        if (closed || theWindow.hashCode() == 0) {
          //error trap
        }
        if (Logger.debugging)
          Logger.debug("Preserving registered applet " + theApplet
              + " window: " + theWindow.hashCode());
      } catch (Exception e) {
        closed = true;
      }
      if (closed) {
        if (Logger.debugging)
          Logger.debug("Dereferencing closed window applet " + theApplet);
        htRegistry.remove(theApplet);
        app.destroy();
      }
    }
  }

  synchronized public static void findApplets(String appletName,
                                              String mySyncId,
                                              String excludeName, List<String> apps) {
    if (appletName != null && appletName.indexOf(",") >= 0) {
      String[] names = TextFormat.split(appletName, ",");
      for (int i = 0; i < names.length; i++)
        findApplets(names[i], mySyncId, excludeName, apps);
      return;
    }
    String ext = "__" + mySyncId + "__";
    if (appletName == null || appletName.equals("*") || appletName.equals(">")) {
      for (String appletName2 : htRegistry.keySet()) {
        if (!appletName2.equals(excludeName) && appletName2.indexOf(ext) > 0) {
          apps.add(appletName2);
        }
      }
      return;
    }
    if (appletName.indexOf("__") < 0)
      appletName += ext;
    if (!htRegistry.containsKey(appletName))
      appletName = "jmolApplet" + appletName;
    if (!appletName.equals(excludeName) && htRegistry.containsKey(appletName)) {
      apps.add(appletName);
    }
  }
}
