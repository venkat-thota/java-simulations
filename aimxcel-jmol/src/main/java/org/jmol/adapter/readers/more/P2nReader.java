
package org.jmol.adapter.readers.more;

import java.util.ArrayList;
import java.util.List;

import org.jmol.adapter.readers.cifpdb.PdbReader;
import org.jmol.adapter.smarter.Atom;
public class P2nReader extends PdbReader {

  private List<String> altNames = new ArrayList<String>();
  
  @Override
  protected void setAdditionalAtomParameters(Atom atom) {
    String altName = line.substring(69, 72).trim();
    if (altName.length() == 0)
      altName = atom.atomName;
    if (useAltNames)
      atom.atomName = altName;
    else
      altNames.add(altName);
  }
  
  @Override
  protected void finalizeReader() throws Exception {
    super.finalizeReader();
    if (useAltNames)
      return;
    String[] names = new String[altNames.size()];
    for (int i = 0; i < names.length; i++)
      names[i] = altNames.get(i);
    atomSetCollection.setAtomSetAuxiliaryInfo("altName", names);
  }

}

