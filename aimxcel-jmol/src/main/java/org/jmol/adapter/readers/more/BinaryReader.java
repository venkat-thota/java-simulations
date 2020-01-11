
package org.jmol.adapter.readers.more;

import org.jmol.adapter.smarter.AtomSetCollectionReader;
import org.jmol.util.BinaryDocument;

public abstract class BinaryReader extends AtomSetCollectionReader {
  protected BinaryDocument doc;
  
  BinaryReader() {
    isBinary = true;  
  }
  
  @Override
  public void processBinaryDocument(BinaryDocument doc) throws Exception {
    this.doc = doc;
    readDocument();
  }
  
  abstract protected void readDocument() throws Exception;
  
}

