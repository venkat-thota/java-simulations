
package org.jmol.adapter.readers.more;

import java.util.Properties;

import org.jmol.adapter.smarter.*;

public abstract class ForceFieldReader extends AtomSetCollectionReader {
  //private final static String sybylTypes = " Any C.1 C.2 C.3 C.ar C.cat Co.oh Cr.oh Cr.th Cu Du Du.C Fe H.spc H.t3p Hal Het Hev LP N.1 N.2 N.3 N.4 N.am N.ar N.pl3 O.2 O.3 O.co2 O.spc O.t3p P.3 S.2 S.3 S.O S.O2 ";
  // some duplication below...
  private final static String ffTypes = 
      /* AMBER   */ " CA CB CC CD CE CF CG CH CI CJ CK CM CN CP CQ CR CT CV CW HA HP HC HO HS HW LP NA NB NC NT OH OS OW SH AH BH HT HY AC BC CS OA OB OE OT "
    + /* CFF     */ " dw hc hi hn ho hp hs hw hscp htip ca cg ci cn co coh cp cr cs ct c3h c3m c4h c4m na nb nh nho nh+ ni nn np npc nr nt nz oc oe oh op oscp otip sc sh sp br cl ca+ ar si lp nu sz oz az pz ga ge tioc titd li+ na+ rb+ cs+ mg2+ ca2+ ba2+ cu2+ cl- br- so4 sy oy ay ayt nac+ mg2c fe2c mn4c mn3c co2c ni2c lic+ pd2+ ti4c sr2c ca2c cly- hocl py vy nh4+ so4y lioh naoh koh foh cloh beoh al "
    + /* CHARMM  */ " CE1 CF1 CF2 CF3 CG CD2 CH1E CH2E CH3E CM CP3 CPH1 CPH2 CQ66 CR55 CR56 CR66 CS66 CT CT3 CT4 CUA1 CUA2 CUA3 CUY1 CUY2 HA HC HMU HO HT LP NC NC2 NO2 NP NR1 NR2 NR3 NR55 NR56 NR66 NT NX OA OAC OC OE OH2 OK OM OS OSH OSI OT OW PO3 PO4 PT PUA1 PUY1 SE SH1E SK SO1 SO2 SO3 SO4 ST ST2 ST2 "
    + /* COMPASS */ " br br- br1 cl cl- cl1 cl12 cl13 cl14 cl1p ca+ cu+2 fe+2 mg+2 zn+2 cs+ li+ na+ rb+ al4z si si4 si4c si4z ar he kr ne xe "
    + /* ESFF    */ " dw hi hw ca cg ci co coh cp cr cs ct ct3 na nb nh nho ni no np nt nt2 nz oa oc oh op os ot sp bt cl' si4l si5l si5t si6 si6o si' "
    + /* GAFF    */ " br ca cc cd ce cf cl cp cq cu cv cx cy ha hc hn ho hp hs na nb nc nd nh oh os pb pc pd pe pf px py sh ss sx sy "
    + /* PCFF    */ " hn2 ho2 cz oo oz si sio hsi osi ";
  private final static String twoChar = " al al4z ar ba2+ beoh br br- br1 ca+ ca2+ ca2c cl cl' cl- cl1 cl12 cl13 cl14 cl1p cloh cly- co2c cs+ cu+2 cu2+ fe+2 fe2c ga ge he kr li+ lic+ lioh lp LP mg+2 mg2+ mg2c mn3c mn4c na+ nac+ naoh ne ni2c nu pd2+ rb+ si si' si4 si4c si4l si4z si5l si5t si6 si6o sio sr2c ti4c tioc titd xe zn+2 ";  
  private final static String specialTypes = " IM IP sz az sy ay ayt ";
  private final static String secondCharOnly = " AH BH AC BC ";

  private String userAtomTypes;
  protected void setUserAtomTypes() {
    userAtomTypes = (String) htParams.get("atomTypes");
    if (userAtomTypes != null)
      userAtomTypes = ";" + userAtomTypes + ";";
  }
  
  // cache atom types
  private Properties atomTypes = new Properties();
  
  protected boolean getElementSymbol(Atom atom, String atomType) {
    String elementSymbol = (String) atomTypes.get(atomType);
    if (elementSymbol != null) {
      atom.elementSymbol = elementSymbol;
      return true;
    }
    int nChar = atomType.length();
    boolean haveSymbol = (nChar < 2);
    int ptType;
    if (userAtomTypes != null
        && (ptType = userAtomTypes.indexOf(";" + atomType + "=>")) >= 0) {
      ptType += nChar + 3;
      elementSymbol = userAtomTypes.substring(ptType,
          userAtomTypes.indexOf(";", ptType)).trim();
      haveSymbol = true;
    } else if (nChar == 1) {
      elementSymbol = atomType.toUpperCase();
      haveSymbol = true;
    } else {
      char ch0 = atomType.charAt(0);
      char ch1 = atomType.charAt(1);
      boolean isXx = (Character.isUpperCase(ch0) && Character.isLowerCase(ch1));
      if (specialTypes.indexOf(atomType) >= 0) {
        // zeolite Si or Al, ions IM, IP
        if (ch0 == 'I') {
          elementSymbol = atom.atomName.substring(0,2);
          if (!Character.isLowerCase(elementSymbol.charAt(1)))
            elementSymbol = elementSymbol.substring(0,1);
        } else {
          elementSymbol = (ch0 == 's' ? "Si" : "Al");
        }
      } else if (nChar == 2 && isXx) {
        // Generic Xx
      } else if (Character.isLetter(ch0) && !Character.isLetter(ch1)) {
        // Xn... or xn...
        elementSymbol = "" + Character.toUpperCase(ch0);
      } else if (nChar > 2 && isXx && !Character.isLetter(atomType.charAt(2))) {
        // Xxn.... (but not XXn... or xxn....)
        elementSymbol = "" + ch0 + ch1;
      } else {
        // must check list
        ch0 = Character.toUpperCase(ch0);
        String check = " " + atomType + " ";
        if (ffTypes.indexOf(check) < 0) {
          // not on the list
        } else if (secondCharOnly.indexOf(check) >= 0) {
          // AH BH AC BC 
          elementSymbol = "" + ch1;
        } else if (twoChar.indexOf(check) >= 0) {
          // LP lp al cl12 etc.
          elementSymbol = "" + ch0 + ch1;
        } else {
          // all others 
          elementSymbol = "" + ch0;
        }
      }
      if (elementSymbol == null) {
        elementSymbol = "" + ch0 + Character.toLowerCase(ch1);
      } else {
        haveSymbol = true;
      }
    }
    atom.elementSymbol = elementSymbol;
    if (haveSymbol)
      atomTypes.put(atomType, elementSymbol);
    return haveSymbol;
  }
  
  protected static String deducePdbElementSymbol(boolean isHetero, String XX,
                                                 String group3) {
    // short of having an entire table,
    int i = XX.indexOf('\0');
    String atomType = null;
    if (i >= 0) {
      atomType = XX.substring(i + 1);
      XX = XX.substring(0, i);
      if (atomType != null && atomType.length() == 1)
        return atomType;
    }
    if (XX.equalsIgnoreCase(group3))
      return XX; // Cd Mg etc.
    int len = XX.length();
    char ch1 = ' ';
    i = 0;
    while (i < len && (ch1 = XX.charAt(i++)) <= '9') {
      // find first nonnumeric letter
    }

    char ch2 = (i < len ? XX.charAt(i) : ' ');
    String full = group3 + "." + ch1 + ch2;
    // Cd Nd Ne are not in complex hetero; Ca is in these:
    if (("OEC.CA ICA.CA OC1.CA OC2.CA OC4.CA").indexOf(full) >= 0)
      return "Ca";
    if (XX.indexOf("'") > 0 || XX.indexOf("*") >= 0 || "HCNO".indexOf(ch1) >= 0
        && ch2 <= 'H' || XX.startsWith("CM"))
      return "" + ch1;
    if (isHetero && Atom.isValidElementSymbolNoCaseSecondChar(ch1, ch2))
      return ("" + ch1 + ch2).trim();
    if (Atom.isValidElementSymbol(ch1))
      return "" + ch1;
    if (Atom.isValidElementSymbol(ch2))
      return "" + ch2;
    return "Xx";
  }
        

}
