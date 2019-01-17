package alife.epimarks.agent;

import java.awt.*;

public class Bird
{
    // The range at which birds will separate from each other to avoid something.
    // With low values flocks will be tighter, and birds will not separate to avoid
    // obstacles and other birds. With higher values, ther flock will be spread
    // apart and a flock will be more likely split in two when faced with an obstacle.
    protected static int SeparationRange;
    
    // The range at which birds can detect the birds, predators, and obstacles
    protected static int DetectionRange;

    // Display the separation and detection ranges on the map.
    static boolean showRanges = false;
    
    // The current location of this bird.
    protected Point location = new Point(0,0);
    
    //The dimension of this Bird
    protected Dimension dimension = new Dimension(0, 0);
    
    protected String text;
    protected int nr;
    
    // The direction that the bird is facing in degrees.
    private int currentTheta;
    
    // The color of this bird.
    protected Color color;

    // The size of the map containing the birds.
    protected static Dimension map = new Dimension( 500, 500 );
    
    // The speed of the bird. This is set by the simulator, and can be changed on the fly.
    private double currentSpeed; // speed of Bird
    
     // maximum turningr radius in degrees. This is set by the simulator, and can be changed on the fly.
    private int maxTurnTheta;
    
    protected Cellula parent;
    
    protected boolean isfull;

    /**
     * This is the constructor for the birds.
     *
     * @param  x Starting X coordinate of the Bird
     * @param  y Starting Y coordinate of the Bird
     * @param  theta Starting angle of the Bird - the direction the bird is facing in degrees
     * @param  birdColor The color of the bird
     */
     Bird(int x, int y,int theta, Color birdColor) {
        location.x = x;
        location.y = y;
        currentTheta = theta;
        color = birdColor;
    }
     
     /**
      * This is the constructor for the birds.
      *
      * @param  x Starting X coordinate of the Bird
      * @param  y Starting Y coordinate of the Bird
      * @param  theta Starting angle of the Bird - the direction the bird is facing in degrees
      * @param  birdColor The color of the bird
      */
     public Bird(int x, int y, int w, int h, int theta, Color birdColor) {
         this(x, y, theta, birdColor);
         dimension.width = w;
         dimension.height = h;
     }

    /**
     * This constructor sets a random location and direction for the bird.
     *
     * @param  birdColor The color of the bird.
     */
    public Bird(Color birdColor) {
        // call the other constructor.
        this((int)(Math.random() * map.width), (int)(Math.random() * map.height), 0, 0, (int)(Math.random() * 360), birdColor);
    }
    
    /**
     * Causes the bird to attempt to face a new direction.
     * Based on maxTurnTheta, the bird may not be able to complete the turn.
     *
     * @param  newHeading The direction in degrees that the bird should turn toward.
     */
    public void move(int newHeading) {
        // determine if it is better to turn left or right for the new heading
        int left = (newHeading - currentTheta + 360) % 360;
        int right = (currentTheta - newHeading + 360) % 360;
        
        // after deciding which way to turn, find out if we can turn that much
        int thetaChange = 0;
        if (left < right) {
            // if left > than the max turn, then we can't fully adopt the new heading
            thetaChange = Math.min(maxTurnTheta, left);
        }
        else {
            // right turns are negative degrees
            thetaChange = -Math.min(maxTurnTheta, right);
        }
        
        // Make the turn
        currentTheta = (currentTheta + thetaChange + 360) % 360;
        
        // Now move currentSpeed pixels in the direction the bird now faces.
        // Note: Because values are truncated, a speed of 1 will result in no
        // movement unless the bird is moving exactly vertically or horizontally.
        location.x += (int)(currentSpeed * Math.cos(currentTheta * Math.PI/180)) + map.width;
        location.x %= map.width;
        location.y -= (int)(currentSpeed * Math.sin(currentTheta * Math.PI/180)) - map.height;
        location.y %= map.height;
    }
    
    /**
     * Draw the bird
     *
     * @param  g The graphics object to draw the bird on.
     */
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillArc(location.x - 12, location.y - 12, dimension.width, dimension.height, currentTheta + 180 - 20, 40);
       
        g.setColor(Color.black);    
		g.drawString(text, (int) (location.x-12 + (dimension.width / 2)), (int) (location.y-12 + (dimension.height / 2)));
   
        if (showRanges) {
            drawRanges(g);
        }
    }
    
    /**
     * Draws the range circles around a bird. In case the bird is close to one
     * of the edges of the map, it will draw up to 4 circles for each bird so
     * that the circles are visible on the other edge of the map.
     *
     * @param  g The graphics object to draw the bird on.
     */
    public void drawRanges(Graphics g) {
        drawCircles(g, location.x, location.y);
        
      //  int radius = Obstacle.DetectionRange;
        
        boolean top = (location.y < DetectionRange);
        boolean bottom = (location.y > map.height - DetectionRange);
        // if the obstacle radius overlaps an edge, then display a second obstacle
        // with a center point outside the range of the displayed area.
        // This way, the birds can react to it before wrapping, and it will also get drawn.
        if (location.x < DetectionRange) { // if left
            drawCircles(g, map.width + location.x, location.y);
            if (top) {
                drawCircles(g, map.width + location.x, map.height + location.y);
            }
            else if (bottom) {
                drawCircles(g, map.width + location.x, location.y - map.height);
            }
        } else if (location.x > map.width - DetectionRange) { // if right
            drawCircles(g, location.x - map.width, location.y);
            if (top) {
                drawCircles(g, location.x - map.width, map.height + location.y);
            }
            else if (bottom) {
                drawCircles(g, location.x - map.width, location.y - map.height);
            }
        }
        if (top) {
            drawCircles(g, location.x, map.height + location.y);
        }
        else if (bottom) {
            drawCircles(g, location.x, location.y - map.height);
        }
    }
    
    /**
     * Draws the range circles around the bird.
     * The inner circle is the point at which birds will separate to aviod the obstacle.
     * The outer circle is the range at which the birds can detect the obstacle.
     *
     * @param  g The graphics object to draw the bird on.
     * @param  x The X coordinate of the bird
     * @param  y The Y coordinate of the bird
     */
    protected void drawCircles( Graphics g, int x, int y ) {
        g.setColor(new Color((int)color.getRed()/2, (int)color.getGreen()/2, (int)color.getBlue()/2 ));
        g.drawOval(x-DetectionRange, y-DetectionRange, 2*DetectionRange, 2*DetectionRange);
        g.setColor(color);
        g.drawOval(x-SeparationRange, y-SeparationRange, 2*SeparationRange, 2*SeparationRange);
    }
 
    /**
     * Get the distance in pixels between this bird and another
     *
     * @param  otherBird The other bird to measure the distance between
     * @return The distance to the other bird
     */
    public int getDistance(Bird otherBird) {
        int dX = otherBird.getLocation().x - location.x;
        int dY = otherBird.getLocation().y - location.y;
        
        return (int)Math.sqrt( Math.pow( dX, 2 ) + Math.pow( dY, 2 ));
    }
    
    /**
     * Get the distance in pixels between this bird and a point
     *
     * @param p The point to measure the distance between
     * @return The distance between this bird and the point
     */
    public int getDistance(Point p) {
        int dX = p.x - location.x;
        int dY = p.y - location.y;
        
        return (int)Math.sqrt( Math.pow( dX, 2 ) + Math.pow( dY, 2 ));
    }
 
    /**
     * Set the new size of the map area
     *
     * @param  otherBird The other bird to measure the distance between
     */
    public static void setMapSize(Dimension newSize) {
        map = newSize;
    }
 
    /**
     * Get the current direction that the bird is facing
     *
     * @return The Maximum Theta for this bird
     */
    public int getMaxTurnTheta() {
        return maxTurnTheta;
    }
    
    /**
     * Set the maximum turn capability of the bird for each movement.
     *
     * @param  theta The new maximum turning theta in degrees
     */
    public void setMaxTurnTheta(int theta)
    {
        maxTurnTheta = theta;
    }
    
    /**
     * Get the current direction of this bird
     *
     * @return  The direction that this bird is facing
     */
    public int getTheta() {
        return currentTheta;
    }
 
    /**
     * Get the current location of this bird
     *
     * @return  The location of this bird
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Set the current speed of the bird
     *
     * @param  speed The new speed for the bird
     */
    public void setSpeed( double speed ) {
        currentSpeed = speed;
    }
    
    /**
     * Get the color of this bird
     *
     * @return  The color of this bird
     */
    public Color getColor() {
        return color;
    }
    
    /**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}
	
	/**
	 * @param dimension the dimension to set
	 */
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @param nr the nr to set
	 */
	public void setNr(int nr) {
		this.nr = nr;
	}
}
