package org.jmol.jvxl.readers;

import org.jmol.util.BinaryDocument;
import org.jmol.util.Logger;

class MrcBinaryReader extends MapFileReader {


  
  MrcBinaryReader(SurfaceGenerator sg, String fileName) {
    super(sg, null);
    binarydoc = new BinaryDocument();
    binarydoc.setStream(sg.getAtomDataServer().getBufferedInputStream(fileName), true);
    // data are HIGH on the inside and LOW on the outside
    nSurfaces = 1; 
    if (params.thePlane == null)
      params.insideOut = !params.insideOut;
    allowSigma = true;
  }
  

  
    
  protected String[] labels;

  @Override
  protected void readParameters() throws Exception {

    int ispg;
    int nsymbt;
    byte[] extra = new byte[100];
    byte[] map = new byte[4];
    byte[] machst = new byte[4];
    float rmsDeviation;
    int nlabel;

    nx = binarydoc.readInt(); // CCP4 "extent[0-2]"
    if (nx < 0 || nx > 1<<8) {
      binarydoc.setIsBigEndian(false);
      nx = BinaryDocument.swapBytes(nx);
      if (params.thePlane == null)
        params.insideOut = !params.insideOut;
      if (nx < 0 || nx > 1000) {
        Logger.info("nx=" + nx + " not displayable as MRC file");
        throw new Exception("MRC file type not readable");
      }
      Logger.info("reading nonstandard little-endian MRC file");
    }
    ny = binarydoc.readInt();
    nz = binarydoc.readInt();

    mode = binarydoc.readInt();

    if (mode < 0 || mode > 6) {
      binarydoc.setIsBigEndian(false);
      nx = BinaryDocument.swapBytes(nx);
      ny = BinaryDocument.swapBytes(ny);
      nz = BinaryDocument.swapBytes(nz);
      mode = BinaryDocument.swapBytes(mode);
    }

    Logger.info("MRC header: mode: " + mode);

    nxyzStart[0] = binarydoc.readInt(); // CCP4 "nxyzstart[0-2]"
    nxyzStart[1] = binarydoc.readInt();
    nxyzStart[2] = binarydoc.readInt();

    na = binarydoc.readInt(); // CCP4 "grid[0-2]"
    nb = binarydoc.readInt();
    nc = binarydoc.readInt();

    if (na == 0)
      na = nx - 1;
    if (nb == 0)
      nb = ny - 1;
    if (nc == 0)
      nc = nz - 1;
    
    a = binarydoc.readFloat();
    b = binarydoc.readFloat();
    c = binarydoc.readFloat();
    alpha = binarydoc.readFloat();
    beta = binarydoc.readFloat();
    gamma = binarydoc.readFloat();

    mapc = binarydoc.readInt(); // CCP4 "crs2xyz[0-2]
    mapr = binarydoc.readInt();
    maps = binarydoc.readInt();

    if (mapc != 1 && params.thePlane == null)
      params.dataXYReversed = true;

    dmin = binarydoc.readFloat(); 
    dmax = binarydoc.readFloat();
    dmean = binarydoc.readFloat();

    Logger.info("MRC header: dmin,dmax,dmean: " + dmin + "," + dmax + ","
        + dmean);

    ispg = binarydoc.readInt();
    nsymbt = binarydoc.readInt();

    Logger.info("MRC header: ispg,nsymbt: " + ispg + "," + nsymbt);

    binarydoc.readByteArray(extra);

    origin.x = binarydoc.readFloat();  // CCP4 "origin2k"
    origin.y = binarydoc.readFloat();
    origin.z = binarydoc.readFloat();

    binarydoc.readByteArray(map);
    binarydoc.readByteArray(machst);

    rmsDeviation = binarydoc.readFloat();

    Logger.info("MRC header: rms: " + rmsDeviation);

    nlabel = binarydoc.readInt();

    Logger.info("MRC header: labels: " + nlabel);

    labels = new String[nlabel];
    if (nlabel > 0)
      labels[0] = "Jmol MrcBinaryReader";

    for (int i = 0; i < 10; i++) {
      String s = binarydoc.readString(80).trim();
      if (i < nlabel) {
        labels[i] = s;
        Logger.info(labels[i]);
      }
    }
    
    for (int i = 0; i < nsymbt; i++) {
      long position = binarydoc.getPosition();
      String s = binarydoc.readString(80).trim();
      if (s.indexOf('\0') != s.lastIndexOf('\0')) {
        // must not really be symmetry info!
        Logger.error("File indicates " + nsymbt + " symmetry lines, but "  + i + " found!");
        binarydoc.seek(position);
        break;
      }
      Logger.info("MRC file symmetry information: " + s);
    }

    Logger.info("MRC header: bytes read: " + binarydoc.getPosition()
        + "\n");

    // setting the cutoff to mean + 2 x RMS seems to work
    // reasonably well as a default.

    getVectorsAndOrigin();
    
    if (params.thePlane == null && (params.cutoffAutomatic || !Float.isNaN(params.sigma))) {
      float sigma = (Float.isNaN(params.sigma) ? 1 : params.sigma);
      params.cutoff = rmsDeviation * sigma + dmean;
      Logger.info("Cutoff set to (mean + rmsDeviation*" + sigma + ")\n");
    }

    jvxlFileHeaderBuffer = new StringBuffer();
    jvxlFileHeaderBuffer.append("MRC DATA ").append(nlabel > 0 ? labels[0]: "").append("\n");
    jvxlFileHeaderBuffer.append("see http://ami.scripps.edu/software/mrctools/mrc_specification.php\n");
  }
  
  @Override
  protected float nextVoxel() throws Exception {
    float voxelValue;
    /*
     *     4 MODE     data type :
         0        image : signed 8-bit bytes range -128 to 127
         1        image : 16-bit halfwords
         2        image : 32-bit reals
         3        transform : complex 16-bit integers
         4        transform : complex 32-bit reals
         6        image : unsigned 16-bit range 0 to 65535

     */
    switch(mode) {
    case 0:
      voxelValue = binarydoc.readByte();
      break;
    case 1:
      voxelValue = binarydoc.readShort();
      break;
    default:
    case 2:
      voxelValue = binarydoc.readFloat();
      break;
    case 3:
      //read first component only
      voxelValue = binarydoc.readShort();
      binarydoc.readShort();
      break;
    case 4:
      //read first component only
      voxelValue = binarydoc.readFloat();
      binarydoc.readFloat();
      break;
    case 6:
      voxelValue = binarydoc.readUnsignedShort();
      break;
    }
    nBytes = binarydoc.getPosition();
    return voxelValue;
  }

  private static byte[] b8 = new byte[8];
  
  @Override
  protected void skipData(int nPoints) throws Exception {
    for (int i = 0; i < nPoints; i++)
      switch(mode) {
      case 0:
        binarydoc.readByte();
        break;
      case 1:
      case 6:
        binarydoc.readByteArray(b8, 0, 2);
        break;
      default:
      case 2:
      case 3:
        binarydoc.readByteArray(b8, 0, 4);
        break;
      case 4:
        binarydoc.readByteArray(b8);
        break;
      }
  }
}
