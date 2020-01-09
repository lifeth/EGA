package alife.epimarks.tests;

import unalcol.optimization.real.HyperCube;

public class BinarySpace {
	
	public static VarLengthBinarySpaceTags getVarLengthBinarySpaceTags (int n){
		return new VarLengthBinarySpaceTags(n, n);
	}
	
	public static VarLengthBinarySpaceTags getVarLengthBinarySpaceTags (int n, HyperCube space){
		return new VarLengthBinarySpaceTags(n, n, space);
	}
	
	public static VarLengthBinarySpace  getVarLengthBinarySpace(int n, HyperCube space){
		return new VarLengthBinarySpace(n, n, space);
	}
	
	public static VarLengthBinarySpace  getVarLengthBinarySpace(int n){
		return new VarLengthBinarySpace(n, n);
	}
}
