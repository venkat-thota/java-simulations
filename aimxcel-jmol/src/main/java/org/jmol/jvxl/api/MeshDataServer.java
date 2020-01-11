package org.jmol.jvxl.api;

import java.io.OutputStream;
import java.util.BitSet;

import javax.vecmath.Point3f;

import org.jmol.jvxl.data.MeshData;
import org.jmol.shapesurface.IsosurfaceMesh;
import org.jmol.util.BinaryDocument;



public interface MeshDataServer extends VertexDataServer {
  

  
  public abstract void invalidateTriangles();
  public abstract void fillMeshData(MeshData meshData, int mode, IsosurfaceMesh mesh);
  public abstract void notifySurfaceGenerationCompleted();
  public abstract void notifySurfaceMappingCompleted();
  public abstract Point3f[] calculateGeodesicSurface(BitSet bsSelected, float envelopeRadius);
  public abstract void addRequiredFile(String fileName);
  public abstract void setOutputStream(BinaryDocument binaryDoc, OutputStream os);
}
