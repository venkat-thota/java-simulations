
package org.jmol.shapebio;

public class Trace extends BioShapeCollection {

  @Override
  public void initShape() {
    super.initShape();
    madOn = 600;
    madHelixSheet = 1500;
    madTurnRandom = 500;
    madDnaRna = 1500;
  }

}