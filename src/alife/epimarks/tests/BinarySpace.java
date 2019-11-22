package alife.epimarks.tests;

public class BinarySpace {
	
	public static VarLengthBinarySpaceTags getVarLengthBinarySpaceTags (int n, int DIM, double min, double max){
		return new VarLengthBinarySpaceTags(n, n, DIM, min, max);
	}
	
	public static VarLengthBinarySpaceTags getVarLengthBinarySpaceTags (int n){
		return new VarLengthBinarySpaceTags(n, n);
	}
	
	public static VarLengthBinarySpace  getVarLengthBinarySpace(int n, int DIM, double min, double max){
		return new VarLengthBinarySpace(n, n, DIM, min, max );
	}
	
	public static VarLengthBinarySpace  getVarLengthBinarySpace(int n){
		return new VarLengthBinarySpace(n, n);
	}
}
