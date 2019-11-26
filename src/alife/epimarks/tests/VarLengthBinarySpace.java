/**
 * 
 */
package alife.epimarks.tests;

import unalcol.optimization.real.BinaryToRealVector;
import unalcol.optimization.real.HyperCube;
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
	protected BinaryToRealVector p; 
	
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
	
	public VarLengthBinarySpace(int minLength, int maxLength, int DIM, double min[], double max[] ){
	  this( minLength, maxLength);
	  this.fromReal = true;
      this.p = new BinaryToRealVector((this.minLength/DIM), min, max);
      this.space = new HyperCube( min, max );
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
		int maxLength = minLength + maxVarGenes * gene_size;
		if( x.size() > maxLength ){
			x = x.subBitArray(0,maxLength);
		}else{
			if( x.size() < minLength )
			x = new BitArray(minLength, true);
			for( int i=0; i<minLength;i++)
				x.set(i,x.get(i));
		}
		return x;
	}

	@Override
	public BitArray pick() {
		
		if(this.fromReal){  
	        return this.p.code( this.space.pick());
		}
		
		return (maxVarGenes>0)?new BitArray(minLength+RawGenerator.integer(this, maxVarGenes*gene_size), true):new BitArray(minLength, true);
	}

}
