package alife.epimarks.tests;

import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.real.HyperCube;
import unalcol.random.raw.RawGenerator;
import unalcol.search.space.Space;

public class VarLengthBinarySpaceTags extends Space<MarkedBitArray> {
	protected int minLength;
	protected int maxVarGenes;
	protected int geneSize;
	protected int tagsLength;
	protected boolean fromReal;
	protected HyperCube space;
	
	public VarLengthBinarySpaceTags( int minLength, int maxLength, boolean isClassic){
		this.minLength = minLength;
		this.maxVarGenes = maxLength - minLength;
		this.geneSize = 1;
		if(!isClassic)
			this.tagsLength = 8;
	}

	public VarLengthBinarySpaceTags( int minLength, int maxLength, int geneSize ){
		this.minLength = minLength;
		this.geneSize = geneSize;
		this.maxVarGenes = (maxLength-minLength)/geneSize;		
	}
	
	public VarLengthBinarySpaceTags(int minLength, int maxLength, HyperCube space, boolean isClassic){
		this( minLength, maxLength, isClassic);
		this.fromReal = true;
	    this.space = space;
	}

	@Override
	public boolean feasible(MarkedBitArray x) {
		return minLength <= x.size() && x.size()<=minLength+maxVarGenes*geneSize;
	}

	@Override
	public double feasibility(MarkedBitArray x) {
		return feasible(x)?1:0;
	}

	@Override
	public MarkedBitArray repair(MarkedBitArray x) {
		
	 /*if(this.fromReal){
			
		 double [] genome = Utils.decode(x.toString(),this.space.min()[0], this.space.max()[0]);
		 
		 double [] rgenome = this.space.repair(genome);
		 
		 if (!Arrays.equals(rgenome, genome)){
			 
			  boolean[] bits =  Utils.encodeBool(rgenome, this.space.min()[0], this.space.max()[0]);
			  
			  for (int i = 0; i < bits.length; i++) {
				  x.set(i, bits[i]);
			  }
		  }
		}*/
		 
		//TODO keep the tags
		int maxLength = minLength + maxVarGenes * geneSize;
		if( x.size() > maxLength ){
			x = x.subMarkedBitArray(0,maxLength);
		}else{
			if( x.size() < minLength )
				x.add(new MarkedBitArray(minLength-x.size(), true));
		}
		   
		return x;
	}

	@Override
	public MarkedBitArray pick() {

		return (maxVarGenes>0)?new MarkedBitArray(minLength+RawGenerator.integer(this, maxVarGenes*geneSize), this.tagsLength, true):new MarkedBitArray(minLength, this.tagsLength, true);
	}
}
