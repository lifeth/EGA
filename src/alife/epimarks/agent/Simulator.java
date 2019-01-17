package alife.epimarks.agent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import alife.epimarks.Controller;
import alife.epimarks.Utils;

/**
 * @author lifeth
 *
 */
public class Simulator extends java.applet.Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// This class holds the birds and controls their movement.
	public Flock flock;

	// Canvas that displays the birds moving around
	public SimulatorCanvas canvas;

	// These are the upper limits for the sliders
	public final int MAXIMUM_BIRDS = 50;
	public final int MAXIMUM_SPEED = 30;
	public final int MAXIMUM_HUNGER = 10;
	public final int MAXIMUM_DISTANCE = 200;

	// These are the default values for the sliders, also used for a reset
	public int DEFAULT_NUMBER_GREEN = 15;
	public int DEFAULT_NUMBER_BLUE = 15;
	public int DEFAULT_NUMBER_RED = 3;
	public int DEFAULT_GREEN_THETA = 15;
	public int DEFAULT_BLUE_THETA = 10;
	public int DEFAULT_RED_THETA = 20;
	public int DEFAULT_GREEN_SPEED = 5;
	public int DEFAULT_BLUE_SPEED = 6;
	public int DEFAULT_RED_SPEED = 7;
	public int DEFAULT_RED_HUNGER = 3;
	public int DEFAULT_OBSTACLE_SEPARATE = 30;
	public int DEFAULT_OBSTACLE_DETECT = 60;

	public static int food = 50;
	public static boolean tagging = false;
	public static boolean poor = false;
	public static boolean average = false;

	/**
	 * This is the java init function. This creates the canvas, the sliders, and
	 * the flock of birds.
	 */
	@Override
	public void init() {

		this.resize(500, 500);
		this.canvas = new SimulatorCanvas();
		this.canvas.simulator = this;

		Bird.SeparationRange =MAXIMUM_DISTANCE; //DEFAULT_OBSTACLE_SEPARATE;
		Bird.DetectionRange = MAXIMUM_DISTANCE; // DEFAULT_OBSTACLE_DETECT;

		this.flock = new Flock();

		Flock.SeparationRange = MAXIMUM_DISTANCE;// DEFAULT_OBSTACLE_SEPARATE;
		Flock.DetectionRange = MAXIMUM_DISTANCE; //DEFAULT_OBSTACLE_DETECT;

		this.setLayout(new BorderLayout());
		this.add("Center", canvas);

		// Panel containing all the sliders that control the birds
		Panel control = new Panel();
		add("West", control);

		JButton feedButton = new JButton("Feed");
		JButton resetButton = new JButton("Reset");

		Checkbox markingCheck = new Checkbox("Marking");
		Label label = new Label("Units: " + food);
	
		JRadioButton poor = new JRadioButton("Poor Fitness");

	    JRadioButton good = new JRadioButton("Average Fitness");

	    ButtonGroup group = new ButtonGroup();
	    group.add(poor);
	    group.add(good);
	    
	    poor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Simulator.poor = true;
				Simulator.average = false;
			}
		});
	    
	    good.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Simulator.average = true;
				Simulator.poor = false;
			}
		});
	    
		feedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				food = Utils.next(50, 100);
				label.setText("Units: " + food);
			}
		});
		
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				flock.getBirds().subList(flock.getBirds().size()/2, flock.getBirds().size()).clear();
			}
		});

		markingCheck.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				tagging = e.getStateChange() == 1;
				if(!tagging){
					poor.setSelected(false);
					good.setSelected(false);
				}
			}
		});

		control.setLayout(new GridLayout(26, 1, 5, 5));

		control.add(feedButton);
		control.add(label);
		control.add(markingCheck);
		control.add(poor);
		control.add(good);
		control.add(resetButton);

		this.canvas.requestFocus();
		Bird.setMapSize(canvas.getSize()); // set swarm boundaries
		Flock.setMapSize(canvas.getSize());
	}

	/**
	 * This is the java applet start function. It starts the thread that
	 * controls the brids.
	 */
	@Override
	public void start() {
		//new Controller(this);
	}

	@Override
	public String getAppletInfo() {
		return "Production and Consumption of Energy";
	}

	public class SimulatorCanvas extends Canvas {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// Image Object for the canvas
		Image canvasImage;
		// Graphics Object for the canvas
		Graphics canvasGraphics;

		// Reference back to the main applet, so we can get to the flock.
		Simulator simulator;

		/**
		 * This is the java applet update function. It calls paint directly. No
		 * need to reset anything first.
		 */
		@Override
		public synchronized void update(Graphics g) {
			paint(g);
		}

		/**
		 * This is the java applet paint function. It draws the birds on the
		 * map.
		 */
		@Override
		public synchronized void paint(Graphics g) {
			if (canvas.getWidth() == 0) {
				return;
			}
			if ((canvasImage == null) || (canvasImage.getWidth(this) != canvas.getWidth())
					|| (canvasImage.getHeight(this) != canvas.getHeight())) {
				if (canvasGraphics != null) {
					canvasGraphics.dispose();
				}
				canvasImage = createImage(canvas.getWidth(), canvas.getHeight());
				canvasGraphics = canvasImage.getGraphics();
			}

			if (canvasGraphics != null) {
				canvasGraphics.setColor(Color.white);
				canvasGraphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				simulator.flock.draw(canvasGraphics);
				canvas.getGraphics().drawImage(canvasImage, 0, 0, this);
			}
		}
	}
}