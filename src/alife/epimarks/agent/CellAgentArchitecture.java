/**
 * 
 */
package alife.epimarks.agent;

import alife.epimarks.Protein;
import alife.epimarks.Protein.PType;
import alife.epimarks.agent.CellAgentProgram.EAction;
import alife.epimarks.function.MaxOnes;
import alife.epimarks.operator.Marking;
import alife.epimarks.operator.Reader;
import alife.epimarks.types.Graph;
import alife.epimarks.types.MarkedBitArray;
import unalcol.agents.Action;
import unalcol.agents.Agent;
import unalcol.agents.AgentArchitecture;
import unalcol.agents.Percept;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.OptimizationGoal;
import unalcol.search.Goal;
import unalcol.search.solution.Solution;
import unalcol.types.collection.vector.Vector;

/**
 * @author lifeth
 *
 */
public class CellAgentArchitecture implements AgentArchitecture {

	private Marking marking;
	private Reader reader;
	private OptimizationFunction<MarkedBitArray> function;
	private Goal<MarkedBitArray, Double> goal;
	private Solution<MarkedBitArray> localSolution;
	private Percept percepts = new Percept();

	/**
	 * 
	 */
	public CellAgentArchitecture() {

		this.marking = new Marking(0.8);
		this.reader = new Reader();
		// Optimization Function
		this.function = new MaxOnes().new Extended();
		this.goal = new OptimizationGoal<MarkedBitArray>(function, false);
	}

	@Override
	public Percept sense(Agent agent) {

		percepts.setAttribute("MARKING", Simulator.tagging);

		return percepts;
	}

	@Override
	public boolean act(Agent agent, Action action) {

		Cellula cell = (Cellula) agent;

		// Interpreter of the chromosome with/without tags added, active genes
		// only are read and returned with no tags.
		MarkedBitArray x = reader.readMarks(cell.getSolution().object());

		localSolution = new Solution<MarkedBitArray>(x, goal);
		cell.setFitness((double) localSolution.info(Goal.class.getName()));

		if (cell.energy <= 0) {
			buildProteins(x, cell);
		}

		if (action.getCode().contains(EAction.MARK.name())) {
			
			if(Simulator.poor && cell.getFitness() <= 30){
				return true;
			}
			
			if(Simulator.average && cell.getFitness() >= 100){
				return true;
			}

			marking.apply(cell.getSolution().object());

			// Update the epigenome
			Graph epigeno = cell.getEpigenotype();
			epigeno.update(cell.getSolution().object());
		}

		return false;
	}

	@Override
	public void init(Agent agent) {
	}

	@Override
	public Vector<Action> actions() {
		return null;
	}

	public void buildProteins(MarkedBitArray x, Cellula cell) {

		int length = 0; //cell.getSolution().object().getGeneLength();
		int n = x.size() / length;

		String sequence = "";

		for (int i = 0; i < n; i++) {
			int count = getValue(x, length * i, length);
			sequence += String.format("%04d", Integer.parseInt(Integer.toBinaryString(count)));
		}

		switch (cell.getIdentifier()) {

		case METABOLIC:
			buildProteins(cell, n, sequence);
			break;

		case TRANSPORTER:
			buildProteins(cell, n, sequence, PType.RECEPTOR);
			buildProteins(cell, n, sequence, PType.TRANSPORTER);
			break;

		case CONSUMPTION:
		case REPRODUCTIVE:
			buildProteins(cell, n, sequence, PType.RECEPTOR);
			break;
		}
	}

	public void buildProteins(Cellula cell, int n, String sequence) {

		Simulator.food = (int) (Simulator.food * 0.7);

		while (Simulator.food > 0) {

			for (int i = 0; i < n; i++) {

				String code = getCode(sequence, i * 4, 4 + (i * 4));
				int nr = getValue(code);

				if (Simulator.food >= nr) {
					Simulator.food -= nr;
					cell.getProteoma().get(0).add(new Protein(code, nr, PType.RECEPTOR));

					if (Simulator.food == 0)
						break;
				} else {

					cell.getProteoma().get(0).add(new Protein(code, Simulator.food, PType.RECEPTOR));
					Simulator.food = 0;
					break;
				}
			}
		}
	}

	public void buildProteins(Cellula cell, int n, String sequence, PType type) {

		if (type.equals(PType.TRANSPORTER)) {

			for (int k = 0; k < 8; k++) {
				for (int i = 0; i < n; i++) {
					String code = getCode(sequence, i * 4, 4 + (i * 4));
					cell.getProteoma().get(1).add(new Protein(code, getValue(code), type));
				}
			}
			return;
		}

		for (int i = 0; i < n; i++) {
			String code = getCode(sequence, i * 4, 4 + (i * 4));
			cell.getProteoma().get(0).add(new Protein(code, getValue(code), type));
		}
	}

	public String getCode(String sequence, int start, int end) {
		return sequence.substring(start, end);
	}

	/*
	 * public static void main(String[] args) { CellAgentArchitecture ca = new
	 * CellAgentArchitecture(); ca.percepts.setAttribute("FOOD", 150);
	 * ca.buildProteins(new BitArray(true, 300, 15), null); }
	 */

	public int getValue(MarkedBitArray genes, int start, int length) {
		int count = 0;
		length += start;
		for (int i = start; i < length; i++) {
			if (genes.get(i)) {
				count++;
			}
		}
		return count;
	}

	public int getValue(String code) {
		int count = 0;
		for (int i = 0; i < code.length(); i++) {
			count += code.charAt(i) == '1' ? 1 : 0;
		}
		return count;
	}
}