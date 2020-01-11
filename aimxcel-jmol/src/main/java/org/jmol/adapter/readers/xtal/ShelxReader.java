package org.jmol.adapter.readers.xtal;

import org.jmol.adapter.smarter.*;

import org.jmol.util.ArrayUtil;
import org.jmol.util.Logger;


public class ShelxReader extends AtomSetCollectionReader {

  private String[] sfacElementSymbols;
  private boolean isCmdf;
  String[] tokens;
  
  @Override
  public void initializeReader() {
      setFractionalCoordinates(true);
  }
  
  @Override
  protected boolean checkLine() throws Exception {

    int lineLength ;
    // '=' as last char of line means continue on next line
    while ((lineLength = (line = line.trim()).length()) > 0 
        && line.charAt(lineLength - 1) == '=') 
      line = line.substring(0, lineLength - 1) + readLine();
    
    tokens = getTokens();
    if (tokens.length == 0)
      return true;
    String command = tokens[0].toUpperCase();
    if (command.equals("TITL")) {
      if (!doGetModel(++modelNumber))
        return checkLastModel();
      sfacElementSymbols = null;
      applySymmetryAndSetTrajectory();
      setFractionalCoordinates(true);
      atomSetCollection.newAtomSet();
      atomSetCollection.setAtomSetName(line.substring(4).trim());
      return true;
    }

    if (!doProcessLines || lineLength < 3)
      return true;

    if (unsupportedRecordTypes.indexOf(";" + command + ";") >= 0)
      return true;
    for (int i = supportedRecordTypes.length; --i >= 0;)
      if (command.equals(supportedRecordTypes[i])) {
        processSupportedRecord(i);
        return true;
      }
    if (!isCmdf)
      assumeAtomRecord();
    return true;
  }

  private final static String unsupportedRecordTypes = 
    ";ZERR;DISP;UNIT;LAUE;REM;MORE;TIME;" +
    "HKLF;OMIT;SHEL;BASF;TWIN;EXTI;SWAT;HOPE;MERG;" +
    "SPEC;RESI;MOVE;ANIS;AFIX;HFIX;FRAG;FEND;EXYZ;" +
    "EXTI;EADP;EQIV;" +
    "CONN;PART;BIND;FREE;" +
    "DFIX;DANG;BUMP;SAME;SADI;CHIV;FLAT;DELU;SIMU;" +
    "DEFS;ISOR;NCSY;SUMP;" +
    "L.S.;CGLS;BLOC;DAMP;STIR;WGHT;FVAR;" +
    "BOND;CONF;MPLA;RTAB;HTAB;LIST;ACTA;SIZE;TEMP;" +
    "WPDB;" +
    "FMAP;GRID;PLAN;MOLE;";
  
  final private static String[] supportedRecordTypes = { "TITL", "CELL", "SPGR",
      "SFAC", "LATT", "SYMM", "NOTE", "ATOM", "END" };

  private void processSupportedRecord(int recordIndex) throws Exception {
    //Logger.debug(recordIndex+" "+line);
    switch (recordIndex) {
    case 0: // TITL
    case 8: // END
      break;
    case 1: // CELL
      cell();
      setSymmetryOperator("x,y,z");
      break;
    case 2: // SPGR
      setSpaceGroupName(parseTrimmed(line, 4));
      break;
    case 3: // SFAC
      parseSfacRecord();
      break;
    case 4: // LATT
      parseLattRecord();
      break;
    case 5: // SYMM
      parseSymmRecord();
      break;
    case 6: // NOTE
      isCmdf = true;
      break;
    case 7: // ATOM
      isCmdf = true;
      processCmdfAtoms();
      break;
    }
  }

  private void parseLattRecord() throws Exception {
    parseToken(line);
    int latt = parseInt();
    atomSetCollection.setLatticeParameter(latt);
  }

  private void parseSymmRecord() throws Exception {
    setSymmetryOperator(line.substring(4).trim());
  }

  private void cell() throws Exception {
    /* example:
     * CELL   wavelngth    a        b         c       alpha   beta   gamma
     * CELL   1.54184   7.11174  21.71704  30.95857  90.000  90.000  90.000
     * 
     * or CrystalMaker file:
     * 
     * CELL       a        b         c       alpha   beta   gamma
     * CELL   7.11174  21.71704  30.95857  90.000  90.000  90.000
     */

    int ioff = tokens.length - 6;
    if (ioff == 2)
      atomSetCollection.setAtomSetCollectionAuxiliaryInfo("wavelength",
          new Float(parseFloat(tokens[1])));
    for (int ipt = 0; ipt < 6; ipt++)
      setUnitCellItem(ipt, parseFloat(tokens[ipt + ioff]));
  }

  private void parseSfacRecord() {
    // an SFAC record is one of two cases
    // a simple SFAC record contains element names
    // a general SFAC record contains coefficients for a single element
    boolean allElementSymbols = true;
    for (int i = tokens.length; allElementSymbols && --i >= 1;) {
      String token = tokens[i];
      allElementSymbols = Atom.isValidElementSymbolNoCaseSecondChar(token);
    }
    String[] sfacTokens = getTokens(line.substring(4));
    if (allElementSymbols)
      parseSfacElementSymbols(sfacTokens);
    else
      parseSfacCoefficients(sfacTokens);
  }

  private void parseSfacElementSymbols(String[] sfacTokens) {
    if (sfacElementSymbols == null) {
      sfacElementSymbols = sfacTokens;
    } else {
      int oldCount = sfacElementSymbols.length;
      int tokenCount = sfacTokens.length;
      sfacElementSymbols = ArrayUtil.setLength(sfacElementSymbols, oldCount + tokenCount);
      for (int i = tokenCount; --i >= 0;)
        sfacElementSymbols[oldCount + i] = sfacTokens[i];
    }
  }
  
  private void parseSfacCoefficients(String[] sfacTokens) {
    float a1 = parseFloat(sfacTokens[1]);
    float a2 = parseFloat(sfacTokens[3]);
    float a3 = parseFloat(sfacTokens[5]);
    float a4 = parseFloat(sfacTokens[7]);
    float c = parseFloat(sfacTokens[9]);
    // element # is these floats rounded to nearest int
    int z = (int) (a1 + a2 + a3 + a4 + c + 0.5f);
    String elementSymbol = getElementSymbol(z);
    int oldCount = 0;
    if (sfacElementSymbols == null) {
      sfacElementSymbols = new String[1];
    } else {
      oldCount = sfacElementSymbols.length;
      sfacElementSymbols = ArrayUtil.setLength(sfacElementSymbols, oldCount + 1);
      sfacElementSymbols[oldCount] = elementSymbol;
    }
    sfacElementSymbols[oldCount] = elementSymbol;
  }

  private void assumeAtomRecord() throws Exception {
    // this line gives an atom, because any line not starting with
    // a SHELX command is an atom
    String atomName = tokens[0];
    int elementIndex = parseInt(tokens[1]);
    float x = parseFloat(tokens[2]);
    float y = parseFloat(tokens[3]);
    float z = parseFloat(tokens[4]);
    if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z)) {
      Logger.error("skipping line " + line);
      return;
    }
      
    elementIndex--;
    Atom atom = atomSetCollection.addNewAtom();
    atom.atomName = atomName;
    if (sfacElementSymbols != null && elementIndex >= 0 && elementIndex < sfacElementSymbols.length)
        atom.elementSymbol = sfacElementSymbols[elementIndex];
    setAtomCoord(atom, x, y, z);
    
    if (tokens.length == 12) {
      float[] data = new float[8];
      data[0] = parseFloat(tokens[6]);  //U11
      data[1] = parseFloat(tokens[7]);  //U22
      data[2] = parseFloat(tokens[8]);  //U33
      data[3] = parseFloat(tokens[11]); //U12
      data[4] = parseFloat(tokens[10]); //U13
      data[5] = parseFloat(tokens[9]);  //U23
      for (int i = 0; i < 6; i++)
        if (Float.isNaN(data[i])) {
            Logger.error("Bad anisotropic Uij data: " + line);
            return;
        }
      atomSetCollection.setAnisoBorU(atom, data, 8);
      // Ortep Type 8: D = 2pi^2, C = 2, a*b*  
    }
  }

  private void processCmdfAtoms() throws Exception {
    while (readLine() != null && line.length() > 10) {
      Atom atom = atomSetCollection.addNewAtom();
      tokens = getTokens();
      atom.elementSymbol = getSymbol(tokens[0]);
      atom.atomName = tokens[1];
      setAtomCoord(atom, parseFloat(tokens[2]), parseFloat(tokens[3]),
          parseFloat(tokens[4]));
    }
  }

  private String getSymbol(String sym) {
    if (sym == null)
      return "Xx";
    int len = sym.length();
    if (len < 2)
      return sym;
    char ch1 = sym.charAt(1);
    if (ch1 >= 'a' && ch1 <= 'z')
      return sym.substring(0, 2);
    return "" + sym.charAt(0);
  }

}
