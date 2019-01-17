/**
 * 
 */
package alife.epimarks.agent;

import java.awt.Color;
import java.util.Vector;

import alife.epimarks.Protein;
import alife.epimarks.Protein.PType;
import alife.epimarks.agent.Cellula.Type;
import unalcol.agents.Agent;
import unalcol.agents.Percept;
import unalcol.agents.Visualizer;

/**
 * @author lifeth
 *
 */
public class IndividualVisualizer implements Visualizer {

	private Simulator simulator;
	private int size = 200;
	private int a = size / 2;
	private int b = a;
	private int m = Math.min(a, b);
	private int r = 4 * m / 5;
	private int r2 = Math.abs(m - r) / 2;

	private Obstacle cone;
	private Obstacle ctwo;
	private Obstacle cthree;
	private Obstacle cfour;

	/**
	 * @param simu
	 * 
	 */
	public IndividualVisualizer(Simulator simu) {

		this.simulator = simu;

		this.cone = new Obstacle((a + 250) - r, (b + 60) - r, 2 * r, 2 * r, "0.1");
		this.ctwo = new Obstacle((a + 450) - r, (b + 460) - r, 2 * r, 2 * r, "0.2");
		this.cthree = new Obstacle((a + 650) - r, (b + 60) - r, 2 * r, 2 * r, "0.3");
		this.cfour = new Obstacle((a + 450) - r, (b + 240) - r, 2 * r, 2 * r, "0.4");

		this.simulator.flock.addBird(cone);
		this.simulator.flock.addBird(ctwo);
		this.simulator.flock.addBird(cthree);
		this.simulator.flock.addBird(cfour);
	}

	@Override
	public void show(Agent agent, Percept percept) {

		Cellula cell = (Cellula) agent;

		if (this.simulator != null) {

			switch (cell.getIdentifier()) {

			case METABOLIC:
				proteinShape(cell, cone);
				 System.out.println("Free Energy: "+cell.energy);
				break;

			case TRANSPORTER:
				// System.out.println("TRANSPORT: "
				// +cell.getProteoma().get(1).size());
				proteinShape(cell, ctwo);
				 System.out.println("Controller: "+cell.energy);
				break;

			case CONSUMPTION:
				proteinShape(cell, cthree);
				 System.out.println("Work: "+cell.energy);
				break;

			case REPRODUCTIVE:
				proteinShape(cell, cfour);
				 System.out.println("Reproductive: "+cell.energy);
				break;
			}
		}

		repaint();
	}

	public void proteinShape(Cellula cell, Obstacle shape) {

		shape.setText("" + cell.getFitness());

		if ((cell.getIdentifier().equals(Type.METABOLIC) && cell.energy != 0)) {
			return;
		}

		int length;

		if (cell.getIdentifier().equals(Type.TRANSPORTER)) {

			length = cell.getProteoma().get(1).size();

			for (int i = 0; i < length; i++) {

				Protein p = cell.getProteoma().get(1).get(i);
				int x = (int) (shape.getLocation().x + (shape.getDimension().width / 2));
				int y = (int) (shape.getLocation().y + (shape.getDimension().height / 2));
				Bird bird = new Bird(x, y, 16, 16, 0, getColor(p.getType()));
				bird.setSpeed(3);
				bird.setMaxTurnTheta(this.simulator.DEFAULT_BLUE_THETA);
				bird.setText(p.getCode());
				bird.setNr(p.nr);
				this.simulator.flock.addBird(bird);
			}

			cell.getProteoma().get(1).clear();
		}

		length = cell.getProteoma().get(0).size() <= 16 ? cell.getProteoma().get(0).size() : 16;

		for (int i = 0; i < length; i++) {

			double t = 2 * Math.PI * i / length;
			int x = (int) Math.round(shape.getLocation().x + r * Math.cos(t)) + r;
			int y = (int) Math.round(shape.getLocation().y + r * Math.sin(t)) + r;

			Protein p = cell.getProteoma().get(0).get(i);

			cell.energy += p.nr;

			this.simulator.flock
					.addBird(new Food(x - r2, y - r2, 2 * r2, 2 * r2, p.getCode(), p.nr, cell, getColor(p.getType())));
		}

		cell.getProteoma().get(0).subList(0, length).clear();
	}

	public Color getColor(PType type) {

		switch (type) {

		case TRANSPORTER:
			return Color.BLUE;

		case RECEPTOR:
			return Color.GREEN;

		default:
			return null;
		}
	}

	public void repaint() {

		Bird.setMapSize(this.simulator.canvas.getSize());
		Flock.setMapSize(this.simulator.canvas.getSize());

		Vector<?> removedBirds = this.simulator.flock.move();

		for (int i = 0; i < removedBirds.size(); i++) {

			// Bird bird = (Bird)
			removedBirds.elementAt(i);
			/*
			 * if (bird.getColor().equals(Color.magenta)) {
			 * System.out.println("anclas ......"); } else if
			 * (bird.getColor().equals(Color.blue)) {
			 * System.out.println("Transporte ......"); } else if
			 * (bird.getColor().equals(Color.green)) {
			 * System.out.println("receptor ......"); }
			 */
		}

		this.simulator.canvas.validate();
		this.simulator.canvas.setVisible(true);
		this.simulator.canvas.repaint();
		this.simulator.canvas.invalidate();
		this.simulator.invalidate();
		this.simulator.repaint();
		this.simulator.repaint(this.simulator.canvas.getLocation().x, this.simulator.canvas.getLocation().y,
				this.simulator.canvas.getSize().width, this.simulator.canvas.getSize().height);

		try {
			Thread.sleep(50); // interval between steps
		} catch (InterruptedException e) {
		}
	}
}
