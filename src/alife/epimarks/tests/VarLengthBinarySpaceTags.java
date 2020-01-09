package alife.epimarks.tests;

import java.util.Arrays;

import alife.epimarks.Utils;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.real.HyperCube;
import unalcol.random.raw.RawGenerator;
import unalcol.search.space.Space;

public class VarLengthBinarySpaceTags extends Space<MarkedBitArray> {
	protected int minLength;
	protected int maxVarGenes;
	protected int gene_size;
	protected int tagsLength = 8;
	protected boolean fromReal;
	protected HyperCube space;
	
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
	
	public VarLengthBinarySpaceTags(int minLength, int maxLength, HyperCube space){
		this( minLength, maxLength);
		this.fromReal = true;
	    this.space = space;
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
		
	 if(this.fromReal){
			
		 double [] genome = Utils.decode(x.toString(),this.space.min()[0], this.space.max()[0]);
		 
		 double [] rgenome = this.space.repair(genome);
		 
		 if (!Arrays.equals(rgenome, genome)){
			 
			  boolean[] bits =  Utils.encodeBool(rgenome, this.space.min()[0], this.space.max()[0]);
			  
			  for (int i = 0; i < bits.length; i++) {
				  x.set(i, bits[i]);
			  }
		  }
		}
		 
		//TODO keep the tags
		int maxLength = minLength + maxVarGenes * gene_size;
		if( x.size() > maxLength ){
			x = x.subMarkedBitArray(0,maxLength);
		}else{
			
			/*if( x.size() < minLength )
				x = new MarkedBitArray(minLength, true);
				
			for( int i=0; i<minLength;i++)
					x.set(i,x.get(i));*/
			
			if( x.size() < minLength )
				x.add(new MarkedBitArray(minLength-x.size(), true));
		}
		   
		return x;
	}

	@Override
	public MarkedBitArray pick() {
		
		/*if(this.fromReal){  
			
	        return new MarkedBitArray(Utils.doubleToBinary(this.space.pick()), this.tagsLength);
		}*/
		
		return (maxVarGenes>0)?new MarkedBitArray(minLength+RawGenerator.integer(this, maxVarGenes*gene_size), this.tagsLength, true):new MarkedBitArray(minLength, this.tagsLength, true);
	}
}
