
package org.jmol.smiles;

import java.util.BitSet;
import java.util.List;

import org.jmol.api.JmolNode;
import org.jmol.api.SmilesMatcherInterface;

public class SmilesMatcher implements SmilesMatcherInterface {

  private final static int MODE_BITSET = 1;
  private final static int MODE_ARRAY = 2;
  private final static int MODE_MAP = 3;

  public String getLastException() {
    return InvalidSmilesException.getLastError();
  }

  public String getMolecularFormula(String pattern, boolean isSmarts) {
    InvalidSmilesException.setLastError(null);
    try {
     
      SmilesSearch search = SmilesParser.getMolecule(pattern, isSmarts);
      search.createTopoMap(null);
      search.nodes = search.jmolAtoms;
      return search.getMolecularFormula(!isSmarts);
    } catch (InvalidSmilesException e) {
      if (InvalidSmilesException.getLastError() == null)
        InvalidSmilesException.setLastError(e.getMessage());
      return null;
    }
  }

  public String getSmiles(JmolNode[] atoms, int atomCount, BitSet bsSelected,
                          boolean asBioSmiles, boolean allowUnmatchedRings, boolean addCrossLinks, String comment) {    
    InvalidSmilesException.setLastError(null);
    try {
      if (asBioSmiles)
        return (new SmilesGenerator()).getBioSmiles(atoms, atomCount,
            bsSelected, allowUnmatchedRings, addCrossLinks, comment);
      return (new SmilesGenerator()).getSmiles(atoms, atomCount, bsSelected);
    } catch (InvalidSmilesException e) {
      if (InvalidSmilesException.getLastError() == null)
        InvalidSmilesException.setLastError(e.getMessage());
      return null;
    }
  }

  public int areEqual(String smiles1, String smiles2) {
    BitSet[] result = find(smiles1, smiles2, false, false);
    return (result == null ? -1 : result.length);
  }

  /**
   * for JUnit test, mainly
   * 
   * @param smiles
   * @param molecule
   * @return        true only if the SMILES strings match and there are no errors
   */
  public boolean areEqual(String smiles, SmilesSearch molecule) {
    BitSet[] ret = find(smiles, molecule, false, true, true);
    return (ret != null && ret.length == 1);
  }

  /**
   * 
   * Searches for all matches of a pattern within a SMILES string.
   * If SMILES (not isSmarts), requires that all atoms be part of the match.
   * 
   * 
   * @param pattern
   *          SMILES or SMARTS pattern.
   * @param smiles
   * @param isSmarts TRUE for SMARTS strings, FALSE for SMILES strings
   * @param firstMatchOnly 
   * @return number of occurances of pattern within smiles
   */
  public BitSet[] find(String pattern, String smiles, boolean isSmarts,
                       boolean firstMatchOnly) {

    InvalidSmilesException.setLastError(null);
    try {
      SmilesSearch search = SmilesParser.getMolecule(smiles, false);
      return find(pattern, search, isSmarts, !isSmarts, firstMatchOnly);
    } catch (Exception e) {
      if (InvalidSmilesException.getLastError() == null)
        InvalidSmilesException.setLastError(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Returns a bitset matching the pattern within atoms.
   * 
   * @param pattern
   *          SMILES or SMARTS pattern.
   * @param atoms
   * @param atomCount
   * @param bsSelected
   * @param isSmarts
   * @param firstMatchOnly
   * @return BitSet indicating which atoms match the pattern.
   */

  public BitSet getSubstructureSet(String pattern, JmolNode[] atoms,
                                   int atomCount, BitSet bsSelected,
                                   boolean isSmarts, boolean firstMatchOnly) {
    return (BitSet) match(pattern, atoms, atomCount, bsSelected, null, isSmarts,
        false, firstMatchOnly, MODE_BITSET);
  }

  /**
   * Returns a vector of bitsets indicating which atoms match the pattern.
   * 
   * @param pattern
   *          SMILES or SMARTS pattern.
   * @param atoms 
   * @param atomCount 
   * @param bsSelected 
   * @param bsAromatic 
   * @param isSmarts 
   * @param firstMatchOnly
   * @return BitSet Array indicating which atoms match the pattern.
   */
  public BitSet[] getSubstructureSetArray(String pattern, JmolNode[] atoms,
                                          int atomCount, BitSet bsSelected,
                                          BitSet bsAromatic, boolean isSmarts,
                                          boolean firstMatchOnly) {
    return (BitSet[]) match(pattern, atoms, atomCount, bsSelected, bsAromatic,
        isSmarts, false, firstMatchOnly, MODE_ARRAY);
  }

  /**
   * Rather than returning bitsets, this method returns the
   * sets of matching atoms in array form so that a direct 
   * atom-atom correlation can be made.
   * 
   * @param pattern 
   *          SMILES or SMARTS pattern.
   * @param atoms 
   * @param atomCount 
   * @param bsSelected 
   * @param isSmarts 
   * @param firstMatchOnly
   * @return      a set of atom correlations
   * 
   */
  public int[][] getCorrelationMaps(String pattern, JmolNode[] atoms,
                                    int atomCount, BitSet bsSelected,
                                    boolean isSmarts, boolean firstMatchOnly) {
    return (int[][]) match(pattern, atoms, atomCount, bsSelected, null, isSmarts,
        false, firstMatchOnly, MODE_MAP);
  }

  
  /////////////// private methods ////////////////
  
  private BitSet[] find(String pattern, SmilesSearch search, boolean isSmarts,
                        boolean matchAllAtoms, boolean firstMatchOnly) {
    // create a topological model set from smiles
    // do not worry about stereochemistry -- this
    // will be handled by SmilesSearch.setSmilesCoordinates
    BitSet bsAromatic = new BitSet();
    search.createTopoMap(bsAromatic);
    return (BitSet[]) match(pattern, search.jmolAtoms,
        -search.jmolAtoms.length, null, bsAromatic, isSmarts, matchAllAtoms,
        firstMatchOnly, MODE_ARRAY);
  }

  @SuppressWarnings({ "unchecked", "cast" })
  private Object match(String pattern, JmolNode[] atoms, int atomCount,
                       BitSet bsSelected, BitSet bsAromatic, boolean isSmarts,
                       boolean matchAllAtoms, boolean firstMatchOnly,
                       int mode) {
    InvalidSmilesException.setLastError(null);
    try {
      SmilesSearch search = SmilesParser.getMolecule(pattern, isSmarts);
      search.jmolAtoms = atoms;
      search.jmolAtomCount = Math.abs(atomCount);
      if (atomCount < 0)
        search.isSmilesFind = true;
      search.setSelected(bsSelected);
      search.bsRequired = null;//(bsRequired != null && bsRequired.cardinality() > 0 ? bsRequired : null);
      search.setRingData(bsAromatic);
      List<Object> vSubstructures;
      search.firstMatchOnly = firstMatchOnly;
      search.matchAllAtoms = matchAllAtoms;
      switch (mode) {
      case MODE_BITSET:
        search.asVector = false;
        return (BitSet) search.search(false);
      case MODE_ARRAY:
        search.asVector = true;
        vSubstructures = (List<Object>) search.search(false);
        BitSet[] bitsets = new BitSet[vSubstructures.size()];
        for (int i = 0; i < bitsets.length; i++)
          bitsets[i] = (BitSet) vSubstructures.get(i);
        return bitsets;
      case MODE_MAP:
        search.getMaps = true;
        vSubstructures = (List<Object>) search.search(false);
        int[][] maps = new int[vSubstructures.size()][];
        for (int i = 0; i < maps.length; i++)
          maps[i] = (int[]) vSubstructures.get(i);
        return maps;
      }
    } catch (Exception e) {
      if (InvalidSmilesException.getLastError() == null)
        InvalidSmilesException.setLastError(e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

}
