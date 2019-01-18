package alife.epimarks.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Individual extends JPanel {

	private static final long serialVersionUID = 1L;
	private ImageIcon image;
	private Point position;
	private Dimension dimension;
	private Structure structure;
	public int incomes;

	public Individual() {
	}

	/**
	 * @param image
	 * @param position
	 * @param dimension
	 * @param structure
	 */
	public Individual(ImageIcon image, Point position, Dimension dimension, Structure structure) {
		this.image = image;
		this.position = position;
		this.dimension = dimension;
		this.structure = structure;
	}

	/**
	 * @param structure
	 */
	public Individual(Structure structure) {
		this.structure = structure;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		drawMolecules(g2, this.structure.metabolicCell);
		drawMolecules(g2, this.structure.transportCell);
		drawMolecules(g2, this.structure.consumptionCell);
		drawMolecules(g2, this.structure.reproductiveCell);

		// cell 1
		g2.drawImage(this.structure.metabolicCell.getImage(), this.structure.metabolicCell.getPosition().x,
				this.structure.metabolicCell.getPosition().y, this.structure.metabolicCell.getDimension().width,
				this.structure.metabolicCell.getDimension().height, null);

		// cell 2
		g2.drawImage(this.structure.transportCell.getImage(), this.structure.transportCell.getPosition().x,
				this.structure.transportCell.getPosition().y, this.structure.transportCell.getDimension().width,
				this.structure.transportCell.getDimension().height, null);

		// cell 3
		g2.drawImage(this.structure.consumptionCell.getImage(), this.structure.consumptionCell.getPosition().x,
				this.structure.consumptionCell.getPosition().y, this.structure.consumptionCell.getDimension().width,
				this.structure.consumptionCell.getDimension().height, null);

		// cell 4
		g2.drawImage(this.structure.reproductiveCell.getImage(), this.structure.reproductiveCell.getPosition().x,
				this.structure.reproductiveCell.getPosition().y, this.structure.reproductiveCell.getDimension().width,
				this.structure.reproductiveCell.getDimension().height, null);

		///////////////////////////////////////////////////////////////////////////
		// Clone
		ImageIcon clone = new ImageIcon(getClass().getResource("img/clone1.png"));
		g2.drawImage(clone.getImage(), 700, 550, 150, 150, null);

		ImageIcon mCell = new ImageIcon(getClass().getResource("img/gcell1.png"));
		g2.drawImage(mCell.getImage(), 750, 585, 30, 30, null);

		ImageIcon tCell = new ImageIcon(getClass().getResource("img/ocell2.png"));
		g2.drawImage(tCell.getImage(), 780, 610, 30, 30, null);

		ImageIcon cCell = new ImageIcon(getClass().getResource("img/bcell3.png"));
		g2.drawImage(cCell.getImage(), 740, 620, 30, 30, null);

		ImageIcon rCell = new ImageIcon(getClass().getResource("img/rcell4.png"));
		g2.drawImage(rCell.getImage(), 760, 650, 30, 30, null);

		///////////////////////////////////////////////////////////////////////////

		setOpaque(false);
		setBackground(Color.BLACK);
		super.paint(g);

		/*
		 * g2.setPaint(Color.LIGHT_GRAY); g2.setStroke(new BasicStroke(1.0f));
		 * g2.fill3DRect(newpoint.x, newpoint.y, square.width, square.height,
		 * true); //g2.draw(new Rectangle2D.Float());
		 * 
		 * g2.setPaint(Color.BLACK);
		 * g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		 * RenderingHints.VALUE_ANTIALIAS_ON); g2.fillOval(bpoint.x, bpoint.y,
		 * ball.width, ball.height);
		 */
	}
	
	public void drawMolecules(Graphics2D g2, Cell cell){
		
		if (cell.getMolecules().size() > 0) {
			for (int i = 0; i < cell.getMolecules().size(); i++) {
				Molecule mo = cell.getMolecules().get(i);
				g2.drawImage(mo.getImage(), mo.getPosition().x, mo.getPosition().y, mo.getDimension().width,
						mo.getDimension().height, null);
			}
		}
	}

	public void init() {

		repaint();

		Thread thread1 = new Thread(structure.metabolicCell);
		// cell.setThread(thread1);
		thread1.start();

		Thread thread2 = new Thread(structure.transportCell);
		// cell.setThread(thread2);
		thread2.start();

		Thread thread3 = new Thread(structure.consumptionCell);
		// cell.setThread(thread3);
		thread3.start();

		Thread thread4 = new Thread(structure.reproductiveCell);
		// cell.setThread(thread4);
		thread4.start();

		Thread thread5 = new Thread(new Runnable() {
			@Override
			public void run() {
				// structure.clone.toString();//TODO
			}
		});
		// clone.setThread(thread5);
		thread5.start();
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

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
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
}
