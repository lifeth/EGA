package alife.epimarks.function;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.*;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author lifeth
 *
 */
public class Deceptive_{
	
	public class Classic extends OptimizationFunction<BitArray> {
		  /**
		   * Return the integer value codified by the bits in a section of the array
		   * @param genes Bitarray source
		   * @param start Index of the first bit in the section to extract the index
		   * @param length Size of the section from which the integer is extracted
		   * @return The integer value codified by the bits in a section of the array
		   */
			public int getValue(BitArray genes, int start, int length) {
				int s = 0;
				int b = 1;
				length += start;
				for (int i = start; i < length; i++) {
					if (genes.get(i)) {
						s += b;
					}
					b *= 2;
				}
				return s;
			}

			/**
			 * Deceptive four trap function 
			 * 
			 * f(0000) = 5 f(0001) = 1 f(0010) = 1
			 * f(0011) = 2 f(0100) = 1 f(0101) = 2
			 * f(0110) = 2 f(0111) = 3 f(1000) = 1
			 * f(1001) = 2 f(1010) = 2 f(1011) = 3
			 * f(1100) = 2 f(1101) = 3 f(1110) = 3
			 * f(1111) = 4
			**/
		  @Override
		  public Double apply( BitArray x ){
			  
		    int geneSize = 4;
		    double f=0.0;
		    int n = x.size() / geneSize;
		    
		    for( int i=0; i<n; i++ ){
		    	
		      int k = getValue(x, geneSize*i, geneSize);
		      
		      switch( k ){
		        case 0: f += 5; break;
		        case 1: f += 1; break;
		        case 2: f += 1; break;
		        case 3: f += 2; break;
		        case 4: f += 1; break;
		        case 5: f += 2; break;
		        case 6: f += 2; break;
		        case 7: f += 3; break;
		        case 8: f += 1; break;
		        case 9: f += 2; break;
		        case 10: f += 2; break;
		        case 11: f += 3; break;
		        case 12: f += 2; break;
		        case 13: f += 3; break;
		        case 14: f += 3; break;
		        case 15: f += 4; break;
		      }
		    }
		    return f;
		  }
	}
	
	public class Extended extends OptimizationFunction<MarkedBitArray> {

		private Reader reader = new Reader();
		
		  /**
		   * Return the integer value codified by the bits in a section of the array
		   * @param genes Bitarray source
		   * @param start Index of the first bit in the section to extract the index
		   * @param length Size of the section from which the integer is extracted
		   * @return The integer value codified by the bits in a section of the array
		   */
			public int getValue(MarkedBitArray genes, int start, int length) {
				int s = 0;
				int b = 1;
				length += start;
				for (int i = start; i < length; i++) {
					if (genes.get(i)) {
						s += b;
					}
					b *= 2;
				}
				return s;
			}

			/**
			 * Deceptive four trap function 
			 * 
			 * f(0000) = 5 f(0001) = 1 f(0010) = 1
			 * f(0011) = 2 f(0100) = 1 f(0101) = 2
			 * f(0110) = 2 f(0111) = 3 f(1000) = 1
			 * f(1001) = 2 f(1010) = 2 f(1011) = 3
			 * f(1100) = 2 f(1101) = 3 f(1110) = 3
			 * f(1111) = 4
			**/
		  @Override
		  public Double apply( MarkedBitArray x ){
			
			  MarkedBitArray xx = reader.readMarks(x);
			  
		    int geneSize = 4;
		    double f=0.0;
		    int n = xx.size() / geneSize;
		    
		    for( int i=0; i<n; i++ ){
		    	
		      int k = getValue(xx, geneSize*i, geneSize);
		      
		      switch( k ){
		        case 0: f += 5; break;
		        case 1: f += 1; break;
		        case 2: f += 1; break;
		        case 3: f += 2; break;
		        case 4: f += 1; break;
		        case 5: f += 2; break;
		        case 6: f += 2; break;
		        case 7: f += 3; break;
		        case 8: f += 1; break;
		        case 9: f += 2; break;
		        case 10: f += 2; break;
		        case 11: f += 3; break;
		        case 12: f += 2; break;
		        case 13: f += 3; break;
		        case 14: f += 3; break;
		        case 15: f += 4; break;
		      }
		    }
		    return f;
		  }
	}
}
