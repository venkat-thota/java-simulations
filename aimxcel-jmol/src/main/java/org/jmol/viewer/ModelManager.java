package org.jmol.viewer;

import java.util.BitSet;

import org.jmol.modelset.ModelLoader;
import org.jmol.modelset.ModelSet;

class ModelManager {

  private final Viewer viewer;
  private ModelLoader modelLoader;

  private String fullPathName;
  private String fileName;

  ModelManager(Viewer viewer) {
    this.viewer = viewer;
  }

  ModelSet zap() {
    fullPathName = fileName = null;
    modelLoader = new ModelLoader(viewer, viewer.getZapName());
    return modelLoader;
  }
  
  String getModelSetFileName() {
    return (fileName != null ? fileName : viewer.getZapName());
  }

  String getModelSetPathName() {
    return fullPathName;
  }

  ModelSet createModelSet(String fullPathName, String fileName,
                          StringBuffer loadScript, Object atomSetCollection,
                          BitSet bsNew, boolean isAppend) {
    String modelSetName = null;
    if (isAppend) {
      modelSetName = modelLoader.getModelSetName();
      if (modelSetName.equals("zapped"))
        modelSetName = null;
      else if (modelSetName.indexOf(" (modified)") < 0)
        modelSetName += " (modified)";
    } else if (atomSetCollection == null) {
      return zap();
    } else {
      this.fullPathName = fullPathName;
      this.fileName = fileName;
    }
    if (atomSetCollection != null) {
      if (modelSetName == null) {
        modelSetName = viewer.getModelAdapter().getAtomSetCollectionName(
            atomSetCollection);
        if (modelSetName != null) {
          modelSetName = modelSetName.trim();
          if (modelSetName.length() == 0)
            modelSetName = null;
        }
        if (modelSetName == null)
          modelSetName = reduceFilename(fileName);
      }
      modelLoader = new ModelLoader(viewer, loadScript, atomSetCollection,
          (isAppend ? modelLoader : null), modelSetName, bsNew);
    }
    if (modelLoader.getAtomCount() == 0)
      zap();
    return modelLoader;
  }

  private static String reduceFilename(String fileName) {
    if (fileName == null)
      return null;
    int ichDot = fileName.indexOf('.');
    if (ichDot > 0)
      fileName = fileName.substring(0, ichDot);
    if (fileName.length() > 24)
      fileName = fileName.substring(0, 20) + " ...";
    return fileName;
  }

}
