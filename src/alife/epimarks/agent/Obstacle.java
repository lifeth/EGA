package alife.epimarks.agent;

import java.awt.*;

public class Obstacle extends Bird {
    
    /**
     * This is the constructor for the obstacle.
     *
     * @param  x The X coordinate of the Obstacle
     * @param  y The Y coordinate of the Obstacle
     */
    public Obstacle(int x, int y)
    {
        super(x, y, 0, 0, 0, Color.black); 
    }
    
    /**
     * This is the constructor for the obstacle.
     *
     * @param  x The X coordinate of the Obstacle
     * @param  y The Y coordinate of the Obstacle
     */
    public Obstacle(int x, int y, int w, int h, String text)
    {
        super(x, y, w, h, 0, Color.black); 
        this.text = text;
    }

    /**
     * The obstacle class overrides the move function to do nothing.
     *
     * @param    angle   not used
     */
    public void move(int angle)
    {
        // obstacles do not move
    }

    /**
     * Draws the Obstacle as a simple circle
     *
     * @param    g   The graphics to draw the obstacle on
     */
    public void draw(Graphics g)
    {
    	Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    	 
        g.setColor(Color.lightGray);
       // g.fillOval(location.x-8, location.y-8, 16, 16);
        g.drawOval(location.x, location.y, dimension.width, dimension.height);  
        
        g.setColor(Color.black);
		g.drawString(text, (int) (location.x + (dimension.width / 2)), (int) (location.y + (dimension.height / 2)));
    }
}
