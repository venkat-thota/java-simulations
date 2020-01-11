package org.jmol.adapter.readers.xml;

import org.jmol.adapter.smarter.*;



import java.io.BufferedReader;
import java.util.Map;

import javax.vecmath.Point3f;

import netscape.javascript.JSObject;

import org.jmol.api.JmolAdapter;
import org.jmol.util.Logger;
import org.xml.sax.*;

public class XmlOdysseyReader extends XmlReader {

  /*
   * Enter any implemented field names in the 
   * implementedAttributes array. It is for when the XML 
   * is already loaded in the DOM of an XML page.
   * 
   */

  String[] odysseyImplementedAttributes = { "id", "label", //general 
      "xyz", "element", "hybrid", //atoms
      "a", "b", "order", //bond
      "boundary"
  };

  String modelName = null;
  String formula = null;
  String phase = null;

  XmlOdysseyReader() {
  }

  @Override
  protected void processXml(XmlReader parent,
                           AtomSetCollection atomSetCollection,
                           BufferedReader reader, XMLReader xmlReader) {
    this.parent = parent;
    this.reader = reader;
    this.atomSetCollection = atomSetCollection;
    new OdysseyHandler(xmlReader);
    parseReaderXML(xmlReader);
  }

  @Override
  protected void processXml(XmlReader parent,
                            AtomSetCollection atomSetCollection,
                            BufferedReader reader, JSObject DOMNode) {
    this.parent = parent;
    this.atomSetCollection = atomSetCollection;
    implementedAttributes = odysseyImplementedAttributes;
    (new OdysseyHandler()).walkDOMTree(DOMNode);
  }

  @Override
  protected void processStartElement(String namespaceURI, String localName, String qName,
                                     Map<String, String> atts) {

    if ("structure".equals(localName)) {
      atomSetCollection.newAtomSet();
      return;
    }

    if ("atom".equals(localName)) {
      atom = new Atom();
      if (atts.containsKey("label"))
        atom.atomName = atts.get("label");
      else
        atom.atomName = atts.get("id");
      if (atts.containsKey("xyz")) {
        String xyz = atts.get("xyz");
        String[] tokens = getTokens(xyz);
        atom.set(parseFloat(tokens[0]), parseFloat(tokens[1]),
            parseFloat(tokens[2]));
      }
      if (atts.containsKey("element")) {
        atom.elementSymbol = atts.get("element");
      }

      return;
    }
    if ("bond".equals(localName)) {
      String atom1 = atts.get("a");
      String atom2 = atts.get("b");
      int order = 1;
      if (atts.containsKey("order"))
        order = parseBondToken(atts.get("order"));
      atomSetCollection.addNewBond(atom1, atom2, order);
      return;
    }

    if ("boundary".equals(localName)) {
      String[] boxDim = getTokens(atts.get("box"));
      float x = parseFloat(boxDim[0]);
      float y = parseFloat(boxDim[1]);
      float z = parseFloat(boxDim[2]);
      parent.setUnitCellItem(0, x);
      parent.setUnitCellItem(1, y);
      parent.setUnitCellItem(2, z);
      parent.setUnitCellItem(3, 90);
      parent.setUnitCellItem(4, 90);
      parent.setUnitCellItem(5, 90);
      Point3f pt = new Point3f(-x / 2, -y / 2, -z / 2);
      atomSetCollection.setAtomSetAuxiliaryInfo("periodicOriginXyz", pt);
      Atom[] atoms = atomSetCollection.getAtoms();
      for (int i = atomSetCollection.getAtomCount(); --i >= 0;) {
        atoms[i].sub(pt);
        parent.setAtomCoord(atoms[i]);
      }
      if (parent.latticeCells[0] == 0)
        parent.latticeCells[0] = parent.latticeCells[1] = parent.latticeCells[2] = 1;
      parent.setSymmetryOperator("x,y,z");
      parent.setSpaceGroupName("P1");
      applySymmetryAndSetTrajectory();
      return;
    }

    if ("odyssey_simulation".equals(localName)) {
      if (modelName != null && phase != null)
        modelName += " - " + phase;
      if (modelName != null)
        atomSetCollection.setAtomSetName(modelName);
      if (formula != null)
        atomSetCollection.setAtomSetAuxiliaryInfo("formula", formula);
    }
    if ("title".equals(localName) || "formula".equals(localName)
        || "phase".equals(localName))
      keepChars = true;
  }

  @Override
  public void applySymmetryAndSetTrajectory() {
    try {
      parent.applySymmetryAndSetTrajectory();
    } catch (Exception e) {
      e.printStackTrace();
      Logger.error("applySymmetry failed: " + e);
    }
  }

  int parseBondToken(String str) {
    if (str.length() >= 1) {
      switch (str.charAt(0)) {
      case 's':
        return 1;
      case 'd':
        return 2;
      case 't':
        return 3;
      case 'a':
        return JmolAdapter.ORDER_AROMATIC;
      }
      return parseInt(str);
    }
    return 1;
  }

  @Override
  protected void processEndElement(String uri, String localName, String qName) {
    if ("atom".equals(localName)) {
      if (atom.elementSymbol != null && !Float.isNaN(atom.z)) {
        atomSetCollection.addAtomWithMappedName(atom);
      }
      atom = null;
      return;
    }
    if ("title".equals(localName)) {
      modelName = chars;
    }
    if ("formula".equals(localName)) {
      formula = chars;
    }
    if ("phase".equals(localName)) {
      phase = chars;
    }
    keepChars = false;
    chars = null;
  }

  class OdysseyHandler extends JmolXmlHandler {

    OdysseyHandler() {
    }

    OdysseyHandler(XMLReader xmlReader) {
      setHandler(xmlReader, this);
    }
  }
}
