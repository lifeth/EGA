/**
 * 
 */
package alife.epimarks.tests;

import java.util.Arrays;

import alife.epimarks.Utils;
import unalcol.random.raw.RawGenerator;
import unalcol.search.space.Space;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author lifeth
 *
 */
public class VarLengthBinarySpace extends Space<BitArray> {
	protected int minLength;
	protected int maxVarGenes;
	protected int gene_size;
	protected boolean fromReal;
	protected Space<double[]> space;
	
	public VarLengthBinarySpace( int minLength, int maxLength ){
		this.minLength = minLength;
		this.maxVarGenes = maxLength - minLength;
		this.gene_size = 1;
	}

	public VarLengthBinarySpace( int minLength, int maxLength, int gene_size ){
		this.minLength = minLength;
		this.gene_size = gene_size;
		this.maxVarGenes = (maxLength-minLength)/gene_size;		
	}
	
	public VarLengthBinarySpace(int minLength, int maxLength, Space<double[]> space){
	  this( minLength, maxLength);
	  this.fromReal = true;
      this.space = space;
	}

	@Override
	public boolean feasible(BitArray x) {
		return minLength <= x.size() && x.size()<=minLength+maxVarGenes*gene_size;
	}

	@Override
	public double feasibility(BitArray x) {
		return feasible(x)?1:0;
	}

	@Override
	public BitArray repair(BitArray x) {
		
	 if(this.fromReal){
			
		 double [] genome =  Utils.binaryToDouble(x.toString());
		 
		 double [] rgenome = this.space.repair(genome);
		 
		 if (!Arrays.equals(rgenome, genome)){
			 
			  boolean[] bits = Utils.doubleToBinary(rgenome);
			  
			  for (int i = 0; i < bits.length; i++) {
				  x.set(i, bits[i]);
			  }
		  }
		}
		 
		int maxLength = minLength + maxVarGenes * gene_size;
		if( x.size() > maxLength ){
			x = x.subBitArray(0,maxLength);
		}else{
			if( x.size() < minLength )
				x.add(new BitArray(minLength-x.size(), true));
		}
		
		return x;
	}

	@Override
	public BitArray pick() {
		
		if(this.fromReal){  
			
	        return new BitArray(Utils.doubleToBinary(this.space.pick()));
		}
		
		return (maxVarGenes>0)?new BitArray(minLength+RawGenerator.integer(this, maxVarGenes*gene_size), true):new BitArray(minLength, true);
	}

}
