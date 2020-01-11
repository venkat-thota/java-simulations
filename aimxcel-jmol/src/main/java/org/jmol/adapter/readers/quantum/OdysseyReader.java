
package org.jmol.adapter.readers.quantum;


public class OdysseyReader extends SpartanInputReader {
  
  @Override
  public void initializeReader() throws Exception {
    modelName = "Odyssey file";
    readInputRecords();
    continuing = false;
  }
  
}
