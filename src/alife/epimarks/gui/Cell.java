package alife.epimarks.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import alife.epimarks.Controller;
import alife.epimarks.types.MarkedBitArray;
import unalcol.search.solution.Solution;

/**
 * @author lifeth
 *
 */
public class Cell implements Runnable, ISignal {

	private Type identifier;
	private ISignal parent;
	private ArrayList<Molecule> molecules;
	private Image image;
	private Point position;
	private Dimension dimension;
	private Map<Integer, Long[]> historyEL;
	private int count = 0;

	public Solution<MarkedBitArray> solution;
	public int energy;

	/**
	 * 
	 */
	public Cell() {

		if (this.molecules == null) {
			this.molecules = new ArrayList<>();
		}

		if (this.historyEL == null) {
			this.historyEL = new HashMap<>();
		}

		Thread thread = new Thread(this);
		// this.setThread(thread);
		thread.start();
	}

	/**
	 * @param identifier
	 * @param solution
	 * @param image
	 * @param position
	 * @param dimension
	 * @param parent
	 */
	public Cell(Type identifier, Solution<MarkedBitArray> solution, Image image, Point position, Dimension dimension,
			ISignal parent) {
		this();
		this.identifier = identifier;
		this.solution = solution;
		this.image = image;
		this.position = position;
		this.dimension = dimension;
		this.parent = parent;
	}

	public Type getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Type identifier) {
		this.identifier = identifier;
	}

	public ArrayList<Molecule> getMolecules() {
		return molecules;
	}

	public void setMolecules(ArrayList<Molecule> molecules) {
		this.molecules = molecules;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public enum Type {
		METABOLIC, TRANSPORTER, CONSUMPTION, REPRODUCTIVE
	}

	@Override
	public String toString() {
		return "\nCell [" + identifier + "]";
	}

	@Override
	public void run() {

		this.energy = 20;// init value
		Cell cell = this;

		if (!Type.METABOLIC.equals(this.identifier)) {
			// Signal to construct receptor/transporter molecules
			// When is the correct time to construct molecules?
			// Is current energy level enough to construct molecules?
			// Marking due to lack of energy levels ???
			Timer timer = new Timer(3000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (energy >= 20) {
						Controller.buildMolecules(cell, 0);
						energy -= energy * 0.5;
					}
					// check history of energy levels, if starvation mark, if
					// unstable
					// mark, if too much food mark

					// Last time that a cell got eUnits was xxxx millisecs,
					// this may be a reason to mark the chromosome.
					// Controller.mark(cell);
				}
			});
			timer.start();

		} else if (Type.METABOLIC.equals(this.identifier)) {

			Timer timer = new Timer(3000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (energy < 20) {
						// Signal to EAT!!!
						parent.processSignal(0, null);
					} else {
						// Transforms the food in eUNITS, so they can be
						// collected by transporter molecules.
						// Reserves energy (15%) each time the individual eats.
						// Frees eUnits each 3000ml time. The 80% of energy is
						// used.
						// Energy spent, WORK 5%
						int eu = energy;
						energy -= energy * 0.8;
						Controller.buildMolecules(cell, eu - energy);
						energy -= energy * 0.05;
					}
				}
			});

			timer.start();
		}
	}

	@Override
	public void processSignal(int command, Integer param) {

		if (command == 1) {
			// Input, food eaten by the individual, Type.METABOLIC.
			this.energy += param;

		} else if (command == 2) {

			// Energy Units (eUNITS) that have been gotten from Receptors.
			this.energy += param;

			Instant start = Instant.now();

			if (!historyEL.isEmpty()) {

				Long[] data = this.historyEL.get(count - 1);
				start = Instant.ofEpochSecond(data[1]);
			}

			Instant end = Instant.now();
			// Save energy level to keep history.
			// Adding timer to count for how long this will happen again.
			this.historyEL.put(count++, new Long[] { param.longValue(), Duration.between(start, end).getSeconds() });
			  
			//System.out.println(this.historyEL);
			//for (int i = 0; i < historyEL.size(); i++) {
				//System.out.println(this.toString() + Arrays.toString(this.historyEL.get(i)));
			//}		 
		}
	}
}