package alife.epimarks.gui;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import alife.epimarks.Controller;

public class Individual implements Runnable, ISignal {

	private ImageIcon image;
	private Point position;
	private String name;
	private Structure structure;
	public int input;

	public Individual() {
	}

	/**
	 * @param image
	 * @param position
	 * @param dimension
	 * @param structure
	 */
	public Individual(ImageIcon image, Point position, Structure structure) {
		this.image = image;
		this.position = position;
		this.structure = structure;
	}

	/**
	 * @param structure
	 */
	public Individual(Structure structure) {
		this.structure = structure;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void startThread() {
		Thread thread = new Thread(this);
		// this.setThread(thread);
		thread.start();
	}

	/**
	 * Defines the individual parts.
	 * 
	 * @author lifeth
	 *
	 */
	public class Structure {

		public Cell metabolicCell;
		public Cell transportCell;
		public Cell consumptionCell;
		public Cell reproductiveCell;
		public ArrayList<ArrayList<Molecule>> molecules;// moving molecules
		public Structure clone;

		public Structure() {
		}

		/**
		 * @param metabolicCell
		 * @param transportCell
		 * @param consumptionCell
		 * @param reproductiveCell
		 * @throws Exception
		 */
		public Structure(Cell metabolicCell, Cell transportCell, Cell consumptionCell, Cell reproductiveCell)
				throws Exception {
			this.metabolicCell = metabolicCell;
			this.transportCell = transportCell;
			this.consumptionCell = consumptionCell;
			this.reproductiveCell = reproductiveCell;
		}
	}

	@Override
	public void run() {
		Controller.checksOn(this);
	}

	@Override
	public void processSignal(int command, Integer param) {

		// Eat.
		if (command == 0) {
			this.input = 10; // Utils.next(0, 50);
			this.structure.metabolicCell.processSignal(1, this.input);
			this.input = 0;
		}
	}
}
