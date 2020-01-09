package alife.epimarks.function;

import alife.epimarks.Utils;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.types.collection.bitarray.BitArray;

/**
 * <p>Title:  Bohachevsky</p>
 * <p>Description: The Bohachevsky function</p>
 * @author lifeth
 */
public class Bohachevsky {
	
  /**
   * True if it is the first Bohachevsky function , false if it is the second
   */
  public boolean one;
   
/**
 * Constructor: Creates a Bohachevsky function
 *[-100, 100] interval
 * @param _one True if it is the Bohachevsky I function, false if it is the Bohachevsky II function
 */
  public Bohachevsky( boolean _one){
	this.one = _one;
  }
  
  /**
   * Evaluates the Bohachevsky I function over two real values
   * @param x1 the first real value argument of the Bohachevsky function
   * @param x2 the second real value argument of the Bohachevsky function
   * @return the Bohachevsky value for the given values
   */
  public double evalI( double x1, double x2 ){
    return ( x1*x1 + 2*x2*x2 - 0.3*Math.cos(3.0*Math.PI*x1) - 0.4*Math.cos(4.0*Math.PI*x2) + 0.7 );
    //(X .^ 2) + (2 * Y .^ 2) - (0.3 * cos(3 * pi * X)) - (0.4 * cos(4 * pi * Y)) + 0.7;
  }

  /**
   * Evaluates the Bohachevsky II function over two real values
   * @param x1 the first real value argument of the Bohachevsky function
   * @param x2 the second real value argument of the Bohachevsky function
   * @return the Bohachevsky value for the given values
   */
  public double evalII( double x1, double x2 ){
    return ( x1*x1 + 2*x2*x2 - 0.3*Math.cos(3.0*Math.PI*x1)*Math.cos(4.0*Math.PI*x2) + 0.3 );
    //(X .^ 2) + (2 * Y .^ 2) - (0.3 * cos(3 * pi * X)) .* (cos(4 * pi * Y)) + 0.3;
  }
  
  public class Classic extends OptimizationFunction<BitArray> {
	  
	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( BitArray x ){
	    
		double [] genome =  Utils.decode(x.toString(), -100, 99);
		
		//System.out.println(Arrays.toString(genome));
			
	    double f = 0.0;
	    int n = genome.length - 1;
	    if( one ){
	      for( int i=0; i<n; i++ ){
	        f += evalI( genome[i], genome[i+1] );
	      }
	    }else{
	      for( int i=0; i<n; i++ ){
	        f += evalII( genome[i], genome[i+1] );
	      }
	    }
	    return f;
	  }
  }

  public class Extended extends OptimizationFunction<MarkedBitArray> {
	  
	  private Reader reader = new Reader();
	  
		/**
		 * 
		 */
		public Extended() {}

	  /**
	   * Evaluate the OptimizationFunction function over the real vector given
	   * @param x Real vector to be evaluated
	   * @return the OptimizationFunction function over the real vector
	   */
	  public Double apply( MarkedBitArray x ){
	    
		MarkedBitArray xx = reader.readMarks(x);
		double [] genome =  Utils.decode(xx.toString(), -100, 99);
		
		//System.out.println(Arrays.toString(genome));

	    double f = 0.0;
	    int n = genome.length - 1;
	    if( one ){
	      for( int i=0; i<n; i++ ){
	        f += evalI( genome[i], genome[i+1] );
	      }
	    }else{
	      for( int i=0; i<n; i++ ){
	        f += evalII( genome[i], genome[i+1] );
	      }
	    }
	    return f;
	  }
  }
}
