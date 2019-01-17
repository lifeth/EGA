package alife.epimarks.agent;

import alife.epimarks.operator.Marking;
import alife.epimarks.types.MarkedBitArray;
import unalcol.search.Goal;
import unalcol.search.solution.Solution;

/**
 * @author lifeth
 *
 */
public class Cellula implements Runnable {
	
	private Type identifier;	
	private Solution<MarkedBitArray> solution;
	//private ArrayList<ArrayList<Protein>> molecules;

	 
	/**
	 * @param identifier
	 * @param solution
	 */
	public Cellula(Type identifier, Solution<MarkedBitArray> solution) {
		this.identifier = identifier;
		this.solution = solution;
	}

	public Type getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Type identifier) {
		this.identifier = identifier;
	}

	public Solution<MarkedBitArray> getSolution() {
		return solution;
	}

	public void setSolution(Solution<MarkedBitArray> solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		return "\nCell [" + identifier + "]\n" +this.getSolution().object().toStringTags();
	}
	
	@Override
	public void run() {
		
		Marking marking = new Marking();
		//vamos marcando en la mitad si no se recibe comida durante 1000 iteraciones
		//se debe definir que marcas son buenas y cuales malas para castigar cuando no se recibe comida
		while (true) {
			solution.set(marking.apply(solution.object()));
			System.out.println(this.toString()+": "+solution.info(Goal.class.getName()));
		}
	}
	
	public enum Type{
	    METABOLIC,
		TRANSPORTER,
		CONSUMPTION,
		REPRODUCTIVE
	}	
}