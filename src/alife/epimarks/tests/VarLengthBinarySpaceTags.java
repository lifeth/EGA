package alife.epimarks.tests;

import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.real.BinaryToRealVector;
import unalcol.optimization.real.HyperCube;
import unalcol.random.raw.RawGenerator;
import unalcol.search.space.Space;
import unalcol.types.real.array.DoubleArray;

public class VarLengthBinarySpaceTags extends Space<MarkedBitArray> {
	protected int minLength;
	protected int maxVarGenes;
	protected int gene_size;
	protected int tagsLength = 7;
	protected boolean fromReal;
	protected Space<double[]> space;
	protected BinaryToRealVector p;
	
	public VarLengthBinarySpaceTags( int minLength, int maxLength){
		this.minLength = minLength;
		this.maxVarGenes = maxLength - minLength;
		this.gene_size = 1;
	}

	public VarLengthBinarySpaceTags( int minLength, int maxLength, int gene_size ){
		this.minLength = minLength;
		this.gene_size = gene_size;
		this.maxVarGenes = (maxLength-minLength)/gene_size;		
	}
	
	public VarLengthBinarySpaceTags(int minLength, int maxLength, int DIM, double min, double max ){
		this( minLength, maxLength);
		this.fromReal = true;
		double[] minArray = DoubleArray.create(DIM, min);
	    double[] maxArray = DoubleArray.create(DIM, max);
	    this.p = new BinaryToRealVector((this.minLength/DIM), maxArray, maxArray);
	    this.space = new HyperCube( minArray, maxArray );
	}

	@Override
	public boolean feasible(MarkedBitArray x) {
		return minLength <= x.size() && x.size()<=minLength+maxVarGenes*gene_size;
	}

	@Override
	public double feasibility(MarkedBitArray x) {
		return feasible(x)?1:0;
	}

	@Override
	public MarkedBitArray repair(MarkedBitArray x) {
		int maxLength = minLength + maxVarGenes * gene_size;
		if( x.size() > maxLength ){
			x = x.subMarkedBitArray(0,maxLength);
		}else{
			if( x.size() < minLength )
			x = new MarkedBitArray(minLength, true);
			for( int i=0; i<minLength;i++)
				x.set(i,x.get(i));
		}
		return x;
	}

	@Override
	public MarkedBitArray pick() {
		
		if(this.fromReal){
	       return new MarkedBitArray(this.p.code(this.space.pick()).toString(), this.tagsLength, 0);
		}
		
		return (maxVarGenes>0)?new MarkedBitArray(minLength+RawGenerator.integer(this, maxVarGenes*gene_size), this.tagsLength, true):new MarkedBitArray(minLength, this.tagsLength, true);
	}
}
