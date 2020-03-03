package alife.epimarks.tests;

import unalcol.optimization.real.HyperCube;

public class BinarySpace {
	
	public static VarLengthBinarySpaceTags getVarLengthBinarySpaceTags (int n, boolean isClassic){
		return new VarLengthBinarySpaceTags(n, n, isClassic);
	}
	
	public static VarLengthBinarySpaceTags getVarLengthBinarySpaceTags (int n, HyperCube space, boolean isClassic){
		return new VarLengthBinarySpaceTags(n, n, space, isClassic);
	}
}
