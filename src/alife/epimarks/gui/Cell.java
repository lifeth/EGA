package alife.epimarks.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import alife.epimarks.types.MarkedBitArray;
import unalcol.search.solution.Solution;

/**
 * @author lifeth
 *
 */
public class Cell implements Runnable{
	
	private Type identifier;	
	public Solution<MarkedBitArray> solution;
	private ArrayList<ArrayList<Molecule>> molecules;
	public int energy;
	private Image image;
	private Point position;
	private Dimension dimension;
	public int signal = 0;

	/**
	 * 
	 */
	public Cell() {
	}
	
	/**
	 * @param identifier
	 * @param image
	 * @param position
	 * @param dimension
	 */
	public Cell(Type identifier, Image image, Point position, Dimension dimension) {
		this.identifier = identifier;
		this.image = image;
		this.position = position;
		this.dimension = dimension;
	}	
	
	/**
	 * @param identifier
	 * @param solution
	 * @param image
	 * @param position
	 * @param dimension
	 */
	public Cell(Type identifier, Solution<MarkedBitArray> solution, Image image, Point position, Dimension dimension) {
		this.identifier = identifier;
		this.solution = solution;
		this.image = image;
		this.position = position;
		this.dimension = dimension;
	}

	public Type getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Type identifier) {
		this.identifier = identifier;
	}

	public ArrayList<ArrayList<Molecule>> getMolecules() {
		
		if(molecules == null){
			molecules = new ArrayList<>();
		}
		return molecules;
	}

	public void setMolecules(ArrayList<ArrayList<Molecule>> molecules) {
		this.molecules = molecules;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
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

	public enum Type{
	    METABOLIC,
		TRANSPORTER,
		CONSUMPTION,
		REPRODUCTIVE
	}

	@Override
	public String toString() {
		return "\nCell [" + identifier + "]";
	}


	@Override
	public void run() {
		// TODO 
		//1. processes info about the Energy Units (eUNITS) that has been gotten, 
		//2. when is correct time to construct molecules?
		energy = 10; //if not enough energy to construct molecules?? TODO
		if( energy < 20){
			//for no METABOLIC cell
			if(!Type.METABOLIC.equals(identifier)){
				//signal to construct receptor/transporter molecules 
				signal = 1;//more TODO  this will be improved with ISignal commands
			}else if (Type.METABOLIC.equals(identifier)){
				//signal to EAT!!! and covert the food in eUNITS (they are behaving as molecules), 
				//so they can be collected by transporter molecules
				signal = 1;//more TODO this will be improved with ISignal commands
			}
		}
	}	
}