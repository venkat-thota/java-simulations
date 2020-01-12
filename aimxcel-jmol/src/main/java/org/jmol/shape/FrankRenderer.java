package org.jmol.shape;
import org.jmol.g3d.Graphics3D;

public class FrankRenderer extends ShapeRenderer {

   
  @Override
  protected void render() {
    Frank frank = (Frank) shape;
    boolean allowKeys = viewer.getBooleanProperty("allowKeyStrokes");
    boolean modelKitMode = viewer.isModelKitMode();
    colix = (modelKitMode ? Graphics3D.MAGENTA 
        : viewer.isSignedApplet() ? (allowKeys ? Graphics3D.ORANGE : Graphics3D.RED) : allowKeys ? Graphics3D.BLUE : Graphics3D.GRAY);
    if (exportType != Graphics3D.EXPORT_NOT || !viewer.getShowFrank()
        || !g3d.setColix(Graphics3D.getColixTranslucent(colix,
            g3d.haveTranslucentObjects(), 0.5f)))
      return;
    float imageFontScaling = viewer.getImageFontScaling();
    frank.getFont(imageFontScaling);
    int dx = (int) (frank.frankWidth + Frank.frankMargin * imageFontScaling);
    int dy = frank.frankDescent;
    g3d.drawStringNoSlab(frank.frankString, frank.font3d,
        g3d.getRenderWidth() - dx, g3d.getRenderHeight() - dy, 0);
    if (modelKitMode) {
     //g3d.setColix(Graphics3D.GRAY);
      g3d.fillRect(0, 0, 0, 0, dy * 2, dx * 3 / 2);      
    }
  }
}
