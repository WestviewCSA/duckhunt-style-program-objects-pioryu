import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

// The Duck class represents a picture of a duck that can be drawn on the screen.
public class Butterfree {
    // Instance variables (data that belongs to each Duck object)
    private Image img;               // Stores the picture of the duck
    
    private Image normal; //normal look
    private Image dead; //dead look
    private Image FireAnimation; // fire
    private Dog Dog = new Dog("myCharmander.gif");
    
    private AffineTransform tx;      // Used to move (translate) and resize (scale) the image

    // Variables to control the size (scale) of the duck image
    private double scaleX;           
    private double scaleY;           

    // Variables to control the location (x and y position) of the duck
    private double x;                
    private double y;        
    
    //variables for speed
    private int vx;
    private int vy;

    boolean debugging = true;
    // Constructor: runs when you make a new Duck object
    public Butterfree() {
    	
        normal = getImage("/imgs/Alive_Butterfree.gif"); // Load the image file
        dead = getImage("/imgs/DeadButterfree.gif"); //load the image file
        FireAnimation = getImage("/myFireAnimation.gif"); //fire animation
        
        
        //img will point to current state object for image
        img = normal;
        
        tx = AffineTransform.getTranslateInstance(0, 0); // Start with image at (0,0)
        
        // Default values
        scaleX = .2;
        scaleY = .2;
        x = -25;
        y = -25;

        //initialize the vx and vy variables with non-zero values
        vx = 5;
        vy = 3;
        
        init(x, y); // Set up the starting location and size
    }
    
    //2nd constructor to initialize location and scale!
    public Butterfree(int x, int y, int scaleX, int scaleY) {
    	this();
    	this.x 		= x;
    	this.y 		= y;
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	init(x,y);
    }
    
    //2nd constructor to initialize location and scale!
    public Butterfree(int x, int y, int scaleX, int scaleY, int vx, int vy) {
    	this();
    	this.x 		= x;
    	this.y 		= y;
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	this.vx 	= vx; 
    	this.vy 	= vy;
    	init(x,y);
    }
    
    public void setVelocityVariables(int vx, int vy) {
    	this.vx = vx;
    	this.vy = vy;
    }
    
    
    // Changes the picture to a new image file
    public void changePicture(String imageFileName) {
        img = getImage("/imgs/"+imageFileName);
        init(x, y); // keep same location when changing image
    }
    
    //update any variables for the object such as x, y, vx, vy
    public void update() {
    	
    	//x position based on vx
    	 x += vx;
    	 y += vy;
    	 
    	 //right-side bounce
    	 if(x >= 1000) {
    		 vx *= -1;
    	 }
    	 
    	 //left-side bounce
    	 if(x < -50) {
    		 vx *= -1;
    	 }
    	 
    	 //down bounce
    	 if(y > 820) {
    		 vy *= -1;
    	 }
    	 
    	 //up bounce
    	 if(y < -50) {
    		 vy *= -1;
    	 }
    	 
    	 //if the object is falling from the sky -- no vx and vy is positive
    	 if(vx == 0 && vy > 6) {
    		 if(y >= 800) {
    			 x = (int)(Math.random()*(1000) + 5);
    			 vy = -(int)(Math.random()*(8) + 5);
    			 vx = (int)(Math.random()*(8) + 5);
    			 
    			 if(Math.random() < .5) {
    				 vx *= -1;
    			 }
    			 img = normal;
    		 }
    	 }
    }
    
    
    
    // Draws the duck on the screen
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;   // Graphics2D lets us draw images
        g2.drawImage(img, tx, null);      // Actually draw the duck image
        update();
        init(x,y);
        
        //create a green hitbox
   //     if(debugging) {
   //     	g.setColor(Color.green);
   //         g.drawRect((int) x + 50, (int) y + 50, 100, 100);
        }
        
   // }
    
    // Setup method: places the duck at (a, b) and scales it
    private void init(double a, double b) {
        tx.setToTranslation(a, b);        // Move the image to position (a, b)
        tx.scale(scaleX, scaleY);         // Resize the image using the scale variables
    }

    // Loads an image from the given file path
    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Butterfree.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }

    // NEW: Method to set scale
    public void setScale(double sx, double sy) {
        scaleX = sx;
        scaleY = sy;
        init(x, y);  // Keep current location
    }

    // NEW: Method to set location
    public void setLocation(double newX, double newY) {
        x = newX;
        y = newY;
        init(x, y);  // Keep current scale
    }
    //Collision and collision logic
    public boolean checkCollision(int mX, int mY) {
    	
    	Rectangle mouse = new Rectangle(mX - 40, mY - 140, 50, 50);
    	
    	//represent this object as a Rectangle
    	Rectangle thisObject = new Rectangle((int) x + 50, (int) y, 50, 50);
    	
    	//use built-in method for Rectangle to check if the intersect! AKA collision
    	if(mouse.intersects(thisObject)) {
    		vx = 0; //turn of vx to fall from the sky
    		vy = 10; //fall y - gravity
    		
    		
    		img = dead;
    		
    		
    		//dog code
    		this.Dog.x = (int) x;
    		this.Dog.y = 820;
    		this.Dog.vy = -3;
    		
    		//logic if colliding
    		return true;
    	} else {
    		
    		//logic if not colliding
    		return false;
    	}
    }
    
    
    
    
    
    
}
