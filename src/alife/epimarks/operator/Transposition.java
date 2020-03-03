package alife.epimarks.operator;

import unalcol.random.integer.*;
import unalcol.search.variation.Variation_1_1;
import alife.epimarks.types.MarkedBitArray;
import unalcol.clone.*;

/**
 * <p>Title: Transposition</p>
 * <p>Description: The simple transposition operator (without flanking)</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author Jonatan Gomez
 * @version 1.0
 */

public class Transposition extends Variation_1_1<MarkedBitArray>{
    public Transposition(){}
    
  /**
   * Interchange the bits between two positions randomly chosen
   * Example:      genome = 100011001110
   * Transposition 2-10:    101100110010
   * @param _genome Genome to be modified
   */
    @Override
  public MarkedBitArray apply(MarkedBitArray _genome) {
      try{
    	  MarkedBitArray genome = (MarkedBitArray) Clone.create(_genome);

          IntUniform gen = new IntUniform(genome.size());
          int start = gen.next();
          int end = gen.next();

          if (start > end) {
              int t = start;
              start = end;
              end = t;
          }
          boolean tr;

          while (start < end) {
              tr = genome.get(start);
              genome.set(start, genome.get(end));
              genome.set(end, tr);
              start++;
              end--;
          }
          return genome;
      }catch( Exception e ){}
      return null;
  }
}
