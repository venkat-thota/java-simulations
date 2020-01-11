package org.jmol.adapter.readers.xml;

import org.jmol.adapter.smarter.*;

import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.Map;

import netscape.javascript.JSObject;

import org.jmol.util.Logger;
import org.xml.sax.*;


public class XmlChem3dReader extends XmlReader {

  /*
   * Enter any implemented field names in the 
   * implementedAttributes array. It is for when the XML 
   * is already loaded in the DOM of an XML page.
   * 
   */

  String[] chem3dImplementedAttributes = { "id", //general 
      "symbol", "cartCoords", //atoms
      "bondAtom1", "bondAtom2", "bondOrder", //bond
      "gridDatXDim", "gridDatYDim", "gridDatZDim",    
      "gridDatXSize", "gridDatYSize", "gridDatZSize",    
      "gridDatOrigin", "gridDatDat",   // grid cube data
      "calcPartialCharges", "calcAtoms" // electronicStructureCalculation 
  };

  //String modelName = null;
  //String formula = null;
  //String phase = null;

  XmlChem3dReader() {
  }
  @Override
  protected void processXml(XmlReader parent,
                           AtomSetCollection atomSetCollection,
                           BufferedReader reader, XMLReader xmlReader) {
    this.parent = parent;
    this.reader = reader;
    this.atomSetCollection = atomSetCollection;
    new Chem3dHandler(xmlReader);
    parseReaderXML(xmlReader);
  }

  @Override
  protected void processXml(XmlReader parent,
                            AtomSetCollection atomSetCollection,
                            BufferedReader reader, JSObject DOMNode) {
    this.parent = parent;
    this.atomSetCollection = atomSetCollection;
    implementedAttributes = chem3dImplementedAttributes;
    (new Chem3dHandler()).walkDOMTree(DOMNode);
  }

  @Override
  public void processStartElement(String namespaceURI, String localName, String qName,
                                  Map<String, String> atts) {
    String[] tokens;
    //System.out.println("xmlchem3d: start " + localName);
    if ("model".equals(localName)) {
      atomSetCollection.newAtomSet();
      return;
    }

    if ("atom".equals(localName)) {
      atom = new Atom();
      atom.atomName = atts.get("id");
      atom.elementSymbol = atts.get("symbol");
      if (atts.containsKey("cartCoords")) {
        String xyz = atts.get("cartCoords");
        tokens = getTokens(xyz);
        atom.set(parseFloat(tokens[0]), parseFloat(tokens[1]), parseFloat(tokens[2]));
      }
      return;
    }
    if ("bond".equals(localName)) {
      String atom1 = atts.get("bondAtom1");
      String atom2 = atts.get("bondAtom2");
      int order = 1;
      if (atts.containsKey("bondOrder"))
        order = parseInt(atts.get("bondOrder"));
      atomSetCollection.addNewBond(atom1, atom2, order);
      return;
    }

    if ("electronicStructureCalculation".equals(localName)) {
      tokens = getTokens(atts.get("calcPartialCharges"));
      String[] tokens2 = getTokens(atts.get("calcAtoms"));
      for (int i = parseInt(tokens[0]); --i >= 0;)
        atomSetCollection.mapPartialCharge(tokens2[i + 1],
            parseFloat(tokens[i + 1]));
    }

    if ("gridData".equals(localName)) {
      atomSetCollection.newVolumeData();
      int nPointsX = parseInt(atts.get("gridDatXDim"));
      int nPointsY = parseInt(atts.get("gridDatYDim"));
      int nPointsZ = parseInt(atts.get("gridDatZDim"));
      atomSetCollection.setVoxelCounts(nPointsX, nPointsY, nPointsZ);
      float xStep = parseFloat(atts.get("gridDatXSize"))
          / (nPointsX);
      float yStep = parseFloat(atts.get("gridDatYSize"))
          / (nPointsY);
      float zStep = parseFloat(atts.get("gridDatZSize"))
          / (nPointsZ);
      atomSetCollection.setVolumetricVector(0, xStep, 0, 0);
      atomSetCollection.setVolumetricVector(1, 0, yStep, 0);
      atomSetCollection.setVolumetricVector(2, 0, 0, zStep);

      tokens = getTokens(atts.get("gridDatOrigin"));
      atomSetCollection.setVolumetricOrigin(parseFloat(tokens[0]), parseFloat(tokens[1]), parseFloat(tokens[2]));
      
      tokens = getTokens(atts.get("gridDatData"));
      int nData = parseInt(tokens[0]);
      int pt = 1;
      float[][][] voxelData = new float[nPointsX][nPointsY][nPointsZ];
      // this is pure speculation for now.
      // seems to work for one test case.
      // could EASILY be backward.

      /* from adeptscience:
       *
       * 
"Here is what we can tell you:

In Chem3D, all grid data in following format:

  for (int z = 0; z < ZDim; z++)
  for (int y = 0; y < YDim; y++)
  for (int x = 0; x < XDim; x++)"
  
       */
      
      for (int z = 0; z < nPointsZ; z++)
        for (int y = 0; y < nPointsY; y++)
          for (int x = 0; x < nPointsX; x++)
            voxelData[x][y][z] = parseFloat(tokens[pt++]);
      atomSetCollection.setVoxelData(voxelData);
      Map<String, Object> surfaceInfo = new Hashtable<String, Object>();
      surfaceInfo.put("surfaceDataType", "mo");
      surfaceInfo.put("defaultCutoff", Float.valueOf((float) 0.01));
      surfaceInfo.put("nCubeData", Integer.valueOf(nData));
      surfaceInfo.put("volumeData", atomSetCollection.getVolumeData());
      atomSetCollection.setAtomSetAuxiliaryInfo("jmolSurfaceInfo", surfaceInfo);
      Logger.debug("Chem3D molecular orbital data displayable using:  isosurface sign \"\" ");
      return;
    }
  }

  @Override
  public void processEndElement(String uri, String localName, String qName) {
    //System.out.println("xmlchem3d: end " + localName);
    if ("atom".equals(localName)) {
      if (atom.elementSymbol != null && !Float.isNaN(atom.z)) {
        parent.setAtomCoord(atom);
        atomSetCollection.addAtomWithMappedName(atom);
      }
      atom = null;
      return;
    }
    keepChars = false;
    chars = null;
  }

  class Chem3dHandler extends JmolXmlHandler {

    Chem3dHandler() {
    }

    Chem3dHandler(XMLReader xmlReader) {
      setHandler(xmlReader, this);
    }
  }
}
