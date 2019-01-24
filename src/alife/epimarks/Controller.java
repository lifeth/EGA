package alife.epimarks;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;

import javax.swing.ImageIcon;

import alife.epimarks.function.Deceptive;
import alife.epimarks.function.Deceptive_;
import alife.epimarks.function.MaxOnes;
import alife.epimarks.function.RoyalRoad;
import alife.epimarks.gui.Cell;
import alife.epimarks.gui.Cell.Type;
import alife.epimarks.gui.Environment;
import alife.epimarks.gui.Individual;
import alife.epimarks.gui.Molecule;
import alife.epimarks.gui.Molecule.MType;
import alife.epimarks.logic.PerformeBitOperations;
import alife.epimarks.types.MarkedBitArray;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.OptimizationGoal;
import unalcol.search.solution.Solution;

/**
 * This class serves as intermediate between the GUI and bit logic. The idea is
 * to keep these two separated for further changes on the GUI. This class
 * initialize and feed both sides.
 * 
 * @author lifeth
 *
 */
public class Controller {

	protected static PerformeBitOperations pbo = new PerformeBitOperations();

	public Controller() throws Exception {

		Vector<Individual> individuals = new Vector<>();
		Environment env = new Environment();

		// Optimization Function for each cell, each one will try to solve one
		// problem.
		OptimizationFunction<MarkedBitArray> functionD3 = new Deceptive().new Extended();
		OptimizationFunction<MarkedBitArray> functionD4 = new Deceptive_().new Extended();
		OptimizationFunction<MarkedBitArray> functionRR = new RoyalRoad().new Extended();
		OptimizationFunction<MarkedBitArray> functionMO = new MaxOnes().new Extended();

		String data[] = { "SheFemale", "gui/img/female1.png", "HeMale", "gui/img/male2.png" };

		int k = 0;
		for (int i = 0; i < 2; i++) {

			Individual individual = new Individual();
			individual.setName(data[k]);
			individual.setImage(new ImageIcon(getClass().getResource(data[++k])));
			k++;

			// same copy for every cell
			MarkedBitArray chromosome = new MarkedBitArray(320, 3, true);

			Solution<MarkedBitArray> s1 = new Solution<MarkedBitArray>(chromosome,
					new OptimizationGoal<MarkedBitArray>(functionRR, false));
			Solution<MarkedBitArray> s2 = new Solution<MarkedBitArray>(chromosome.clone(),
					new OptimizationGoal<MarkedBitArray>(functionD3, false));
			Solution<MarkedBitArray> s3 = new Solution<MarkedBitArray>(chromosome.clone(),
					new OptimizationGoal<MarkedBitArray>(functionD4, false));
			Solution<MarkedBitArray> s4 = new Solution<MarkedBitArray>(chromosome.clone(),
					new OptimizationGoal<MarkedBitArray>(functionMO, false));

			Cell one = new Cell(Type.METABOLIC, s1,
					new ImageIcon(getClass().getResource("gui/img/gcell1.png")).getImage(), new Point(250, 10),
					new Dimension(150, 150), individual);

			Cell two = new Cell(Type.TRANSPORTER, s2,
					new ImageIcon(getClass().getResource("gui/img/ocell2.png")).getImage(), new Point(350, 240),
					new Dimension(150, 150), individual);

			Cell three = new Cell(Type.CONSUMPTION, s3,
					new ImageIcon(getClass().getResource("gui/img/bcell3.png")).getImage(), new Point(150, 350),
					new Dimension(150, 150), individual);

			Cell four = new Cell(Type.REPRODUCTIVE, s4,
					new ImageIcon(getClass().getResource("gui/img/rcell4.png")).getImage(), new Point(250, 550),
					new Dimension(150, 150), individual);

			individual.setStructure(individual.new Structure(one, two, three, four));
			individuals.add(individual);
		}

		env.init(individuals);

		try {
			Thread.sleep(7000); // wait until individuals got painted.
		} catch (InterruptedException e) {
		}

		individuals.get(0).startThread();
		individuals.get(1).startThread();
	}

	/**
	 * Checks every 1000 for cell signals.
	 * 
	 * TODO this will be improved with signals messages (ISignal interface). in
	 * order to build molecules, eat, or mark.
	 * 
	 * @param individual
	 */
	public static void checksOn(Individual individual) {

		// checks TODO

		individual.input = 10;// TODO just for testing, food init
		int x;
		int y = 10;

		for (int j = 0; j < individual.input; j++) {// TODO

			individual.getStructure().metabolicCell.getMolecules()
					.add(new Molecule("1", MType.eUNIT,
							new ImageIcon(Controller.class.getResource("gui/img/unit" + j + ".png")).getImage(),
							new Point(250, y), new Dimension(10, 10)));
			y += 20;
		}

		String[] codes = pbo.readingProcess(individual.getStructure().metabolicCell.solution);// TODO
		x = 350;
		y = 200;

		// for (String code : codes) {TODO
		for (int j = 1; j <= 5; j++) {
			individual.getStructure().transportCell.getMolecules()
					.add(new Molecule(codes[0], MType.TRANSPORTER,
							new ImageIcon(Controller.class.getResource("gui/img/transporter" + j + ".png")).getImage(),
							new Point(x, y), new Dimension(70, 10)));
			x += 30;
			y += 20;
		}

		addReceptors(individual.getStructure().transportCell, codes[0], "gui/img/receptor5.png");

		codes = pbo.readingProcess(individual.getStructure().consumptionCell.solution);// TODO
		addReceptors(individual.getStructure().consumptionCell, codes[0], "gui/img/receptor2.png");

		codes = pbo.readingProcess(individual.getStructure().reproductiveCell.solution);// TODO
		addReceptors(individual.getStructure().reproductiveCell, codes[0], "gui/img/receptor1.png");

		pbo.markingProcess(individual.getStructure().reproductiveCell.solution);// marking!!!

	}

	public static void addReceptors(Cell cell, String code, String img) {

		int x, y;
		int size = 150;
		int a = size / 2;
		int b = a;
		int m = Math.min(a, b);
		int r = 4 * m / 5;
		int r2 = Math.abs(m - r) / 2;

		for (int i = 0; i < 5; i++) {

			double t = 2 * Math.PI * i / 5;
			x = (int) Math.round(cell.getPosition().x + r * Math.cos(t)) + r;
			y = (int) Math.round(cell.getPosition().y + r * Math.sin(t)) + r;

			x = x - r2;
			y = y - r2;
			// int w = 2 * r2;
			// int h = 2 * r2;

			cell.getMolecules()
					.add(new Molecule(code, MType.RECEPTOR, new ImageIcon(Controller.class.getResource(img)).getImage(),
							new Point(x, y), new Dimension(20, 30)));
		}
	}

	public static void main(String[] args) {
		try {
			new Controller();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
