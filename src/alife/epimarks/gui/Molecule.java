package alife.epimarks.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

public class Molecule implements Runnable{

	private String code;
	private MType type;
	private Image image;
	private Point position;
	private Dimension dimension;
	public int signal = 0;


	public Molecule() {
	}

	/**
	 * @param code
	 */
	public Molecule(String code) {
		this.code = code;
	}

	/**
	 * @param code
	 * @param type
	 */
	public Molecule(String code, MType type) {
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
		this.code = code;
		this.type = type;
		this.image = image;
		this.position = position;
		this.dimension = dimension;
		Thread thread = new Thread(this);
		//mo.setThread(thread);
		thread.start();
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
		// TODO MOVING	calculations
		
		//receptor molecules must notify to cells when getting units to increment the energy
		if(MType.RECEPTOR.equals(type)){
			//last time this caught units was xxxxxx, this may be a reason to mark the chromosome. 
			signal = 2;//more TODO  this will be improved with ISignal commands
		}
	}
}
