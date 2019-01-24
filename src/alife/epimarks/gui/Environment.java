/**
 * 
 */
package alife.epimarks.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * @author lifeth
 *
 */
public class Environment extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(Vector<Individual> individuals) {

		this.setTitle("Epigenetic Algorithm");

		WindowListener l = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};

		this.addWindowListener(l);
		this.pack();
		this.setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize.width, screenSize.height);

		DisplayStructure structure = new DisplayStructure();
		JPanel panel = new JPanel();

		panel.setBackground(Color.BLACK);
		panel.setLayout(new FlowLayout());
		panel.setSize(screenSize.width / 2, screenSize.height);

		for (Individual individual : individuals) {

			JLabel label = new JLabel();
			// Image image = individual.getImage().getImage();
			// Icon icon = new ImageIcon(image.getScaledInstance(62, 132,
			// Image.SCALE_SMOOTH));
			label.setIcon(individual.getImage());

			label.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					structure.init(individual);
					structure.repaint();
				}
			});

			panel.add(label);
		}

		JLabel label;
		panel.add(label = new JLabel("Click ON the individual"));
		label.setForeground(Color.white);

		this.add(panel, BorderLayout.WEST);
		this.add(structure, BorderLayout.CENTER);
	}

	public class DisplayStructure extends JPanel {

		private static final long serialVersionUID = 1L;
		private Individual individual;

		/**
		 * @param individual
		 */
		public void init(Individual individual) {
			TitledBorder border = new TitledBorder(individual.getName());
			// border.setTitleJustification(TitledBorder.CENTER);
			// border.setTitlePosition(TitledBorder.TOP);
			this.setBorder(border);
			this.individual = individual;
		}

		@Override
		public void paint(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;

			if (this.individual != null) {

				drawMolecules(g2, this.individual.getStructure().metabolicCell);
				drawMolecules(g2, this.individual.getStructure().transportCell);
				drawMolecules(g2, this.individual.getStructure().consumptionCell);
				drawMolecules(g2, this.individual.getStructure().reproductiveCell);

				// cell 1
				g2.drawImage(this.individual.getStructure().metabolicCell.getImage(),
						this.individual.getStructure().metabolicCell.getPosition().x,
						this.individual.getStructure().metabolicCell.getPosition().y,
						this.individual.getStructure().metabolicCell.getDimension().width,
						this.individual.getStructure().metabolicCell.getDimension().height, null);

				// cell 2
				g2.drawImage(this.individual.getStructure().transportCell.getImage(),
						this.individual.getStructure().transportCell.getPosition().x,
						this.individual.getStructure().transportCell.getPosition().y,
						this.individual.getStructure().transportCell.getDimension().width,
						this.individual.getStructure().transportCell.getDimension().height, null);

				// cell 3
				g2.drawImage(this.individual.getStructure().consumptionCell.getImage(),
						this.individual.getStructure().consumptionCell.getPosition().x,
						this.individual.getStructure().consumptionCell.getPosition().y,
						this.individual.getStructure().consumptionCell.getDimension().width,
						this.individual.getStructure().consumptionCell.getDimension().height, null);

				// cell 4
				g2.drawImage(this.individual.getStructure().reproductiveCell.getImage(),
						this.individual.getStructure().reproductiveCell.getPosition().x,
						this.individual.getStructure().reproductiveCell.getPosition().y,
						this.individual.getStructure().reproductiveCell.getDimension().width,
						this.individual.getStructure().reproductiveCell.getDimension().height, null);

				///////////////////////////////////////////////////////////////////////////
				// Clone
				ImageIcon clone = new ImageIcon(getClass().getResource("img/clone1.png"));
				g2.drawImage(clone.getImage(), 450, 550, 150, 150, null);

				ImageIcon mCell = new ImageIcon(getClass().getResource("img/gcell1.png"));
				g2.drawImage(mCell.getImage(), 500, 585, 30, 30, null);

				ImageIcon tCell = new ImageIcon(getClass().getResource("img/ocell2.png"));
				g2.drawImage(tCell.getImage(), 530, 610, 30, 30, null);

				ImageIcon cCell = new ImageIcon(getClass().getResource("img/bcell3.png"));
				g2.drawImage(cCell.getImage(), 490, 620, 30, 30, null);

				ImageIcon rCell = new ImageIcon(getClass().getResource("img/rcell4.png"));
				g2.drawImage(rCell.getImage(), 510, 650, 30, 30, null);

				///////////////////////////////////////////////////////////////////////////
			}

			this.setOpaque(false);
			this.setBackground(Color.GRAY);
			super.paint(g);

			/*
			 * g2.setPaint(Color.LIGHT_GRAY); g2.setStroke(new
			 * BasicStroke(1.0f)); g2.fill3DRect(newpoint.x, newpoint.y,
			 * square.width, square.height, true); //g2.draw(new
			 * Rectangle2D.Float());
			 * 
			 * g2.setPaint(Color.BLACK);
			 * g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			 * RenderingHints.VALUE_ANTIALIAS_ON); g2.fillOval(bpoint.x,
			 * bpoint.y, ball.width, ball.height);
			 */
		}

		public void drawMolecules(Graphics2D g2, Cell cell) {

			for (int i = 0; i < cell.getMolecules().size(); i++) {
				Molecule mo = cell.getMolecules().get(i);
				g2.drawImage(mo.getImage(), mo.getPosition().x, mo.getPosition().y, mo.getDimension().width,
						mo.getDimension().height, null);
			}
		}
	}
}
