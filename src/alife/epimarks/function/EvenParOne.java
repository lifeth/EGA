package alife.epimarks.function;

import alife.epimarks.operator.Reader;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;

/**
 * @author lifeth
 *
 */
public class EvenParOne extends OptimizationFunction<MarkedBitArray> {
	
	private Reader reader = new Reader();

		/**
		 * If bit 1 is in even position then add 1
		 * 
		**/
	  @Override
	  public Double apply( MarkedBitArray x ){
		
		MarkedBitArray xx = x.isClassic() ? x : reader.readMarks(x);
		  
	    double f=0.0;
	    int n = xx.size();
	    
	    for( int i=0; i<n; i++ ){
    		if ((i+1)%2==0) {
    			if(xx.get(i))
    				f += 1;
			}else{
				   if(!xx.get(i))
	    				f += 1;
				}
	    }
	    
	    return f;
	  }
}
