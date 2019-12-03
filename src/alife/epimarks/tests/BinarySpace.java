package alife.epimarks.tests;

import unalcol.search.space.Space;

public class BinarySpace {
	
	public static VarLengthBinarySpaceTags getVarLengthBinarySpaceTags (int n){
		return new VarLengthBinarySpaceTags(n, n);
	}
	
	public static VarLengthBinarySpaceTags getVarLengthBinarySpaceTags (int n, Space<double[]> space){
		return new VarLengthBinarySpaceTags(n, n, space);
	}
	
	public static VarLengthBinarySpace  getVarLengthBinarySpace(int n, Space<double[]> space){
		return new VarLengthBinarySpace(n, n, space);
	}
	
	public static VarLengthBinarySpace  getVarLengthBinarySpace(int n){
		return new VarLengthBinarySpace(n, n);
	}
}
