package alife.epimarks.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import alife.epimarks.Utils;

public class Molecule implements Runnable {

	private String code;
	private ISignal parent;
	private MType type;
	private Image image;
	private Point position;
	private Dimension dimension;

	public int input;

	public Molecule() {
		Thread thread = new Thread(this);
		// this.setThread(thread);
		thread.start();
	}

	/**
	 * @param code
	 */
	public Molecule(String code) {
		this();
		this.code = code;
	}

	/**
	 * @param code
	 * @param type
	 */
	public Molecule(String code, MType type) {
		this();
		this.code = code;
		this.type = type;
	}

	/**
	 * @param code
	 * @param type
	 * @param image
	 * @param position
	 * @param dimension
	 */
	public Molecule(String code, MType type, Image image, Point position, Dimension dimension) {
		this();
		this.code = code;
		this.type = type;
		this.image = image;
		this.position = position;
		this.dimension = dimension;
	}

	/**
	 * @param code
	 * @param type
	 * @param image
	 * @param position
	 * @param dimension
	 * @param parent
	 */
	public Molecule(String code, MType type, Image image, Point position, Dimension dimension, ISignal parent) {
		this();
		this.code = code;
		this.type = type;
		this.image = image;
		this.position = position;
		this.dimension = dimension;
		this.parent = parent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public MType getType() {
		return type;
	}

	public void setType(MType type) {
		this.type = type;
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

	@Override
	public String toString() {
		return code;
	}

	public enum MType {
		RECEPTOR, TRANSPORTER, eUNIT
	}

	@Override
	public void run() {

		// TODO MOVING calculations for transporters and eUnits

		// Receptor Molecules notify to cells when getting units to
		// increment their energy.
		if (MType.RECEPTOR.equals(type)) {

			Timer timer = new Timer(3000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					input = Utils.next(0, 4);// TODO number of units taken from transporters
					
					if (input > 0) {// this caught some units
						parent.processSignal(2, input);
						//input = 0;
					}
				}
			});
			
			timer.start();
		}
	}
}
