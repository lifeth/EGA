package alife.epimarks.agent;

import java.awt.*;

public class Food extends Bird {
	
	/**
	 * This is the constructor for the food.
	 *
	 * @param x
	 *            The X coordinate of the Food
	 * @param y
	 *            The Y coordinate of the Food
	 */
	public Food(int x, int y) {
		super(x, y, 0, Color.magenta);
	}

	/**
	 * This is the constructor for the food.
	 *
	 * @param x
	 *            The X coordinate of the Food
	 * @param y
	 *            The Y coordinate of the Food
	 */
	public Food(int x, int y, int w, int h, String text, int nr, Cellula cell, Color color) {
		super(x, y, w, h, 0, color);
		this.text = text;
		this.nr = nr;
		this.parent = cell;
	}

	/**
	 * The food class overrides the move function to do nothing.
	 *
	 * @param angle
	 *            not used
	 */
	public void move(int angle) {
		// food does not move
	}

	/**
	 * Draws the Food as a simple circle
	 *
	 * @param g
	 *            The graphics to draw the food on
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		// g.fillOval(location.x-3, location.y-3, 6, 6);
		g.fillOval(location.x, location.y, dimension.width, dimension.height);
		g.setColor(Color.black);
		g.drawString(text, (int) (location.x + (dimension.width / 2)), (int) (location.y + (dimension.height / 2)));
	}
}
