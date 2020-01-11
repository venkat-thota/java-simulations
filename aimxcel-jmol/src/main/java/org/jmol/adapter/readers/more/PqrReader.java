
package org.jmol.adapter.readers.more;

import org.jmol.adapter.readers.cifpdb.PdbReader;
import org.jmol.adapter.smarter.Atom;


public class PqrReader extends PdbReader {

  @Override
  protected void setAdditionalAtomParameters(Atom atom) {

    String[] tokens = getTokens();
    
    atom.radius = parseFloat(tokens[tokens.length - 1]);
    atom.partialCharge = parseFloat(tokens[tokens.length - 2]);

  }
  
}

