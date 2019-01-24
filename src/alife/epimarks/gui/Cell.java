package alife.epimarks.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

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
	private Map<Integer, Integer> historyEL;
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

		if (!Type.METABOLIC.equals(this.identifier)) {
			// Signal to construct receptor/transporter molecules
			// When is the correct time to construct molecules?
			// Is current energy level enough to construct molecules?
			// Marking due to lack of energy levels ???
			Timer timer = new Timer(3000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});
			timer.start();

		} else if (Type.METABOLIC.equals(this.identifier)) {
			// Signal to EAT!!! and transform the food in eUNITS (they are
			// behaving as molecules),
			// so they can be collected by transporter molecules
			if (this.energy < 20) {
				this.parent.processSignal(0, null);
			}
		}

		// check history of energy levels, if starvation mark, if unstable
		// mark, if too much food mark

		// Last time that a Receptor got eUnits was xxxxxx millisecs,
		// this may be a reason to mark the chromosome.
		// Have been xxxxx iterations/millisecs and no food.
	}

	@Override
	public void processSignal(int command, Integer param) {

		// Energy Units (eUNITS) that have been got from Receptors.
		if (command == 1) {
			this.energy = param;
			// Save energy level to keep history.
			this.historyEL.put(count++, param);
			// Adding timer to count
		}
	}
}