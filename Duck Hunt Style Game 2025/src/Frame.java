import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	//frame size
	private int screenWidth = 1225, screenHeight = 1800;
	private String title = "Gotta Catch em' All!";
    
	private int totalScore = 0;
	
	/**
	 * Declare and instantiate (create) your objects here
	 */
	// private Duck duckObject = new Duck();
	private Background myBackground 		= new Background();
	private Bush myBush 					= new Bush();
	private Dog dog 						= new Dog("myCharmander.gif");
	private Butterfree myButterfree 		= new Butterfree(dog);
	private Crosshair myCrosshair 			= new Crosshair();
	private MyCursor cursor 				= new MyCursor();
	private FireAnimation myFireAnimation 	= new FireAnimation();
	private threeShot myThreeShot 			= new threeShot();
	private twoShot myTwoShot 				= new twoShot();
	private oneShot myOneShot 				= new oneShot();
	private noShot myNoShot 				= new noShot();
	private Foreground myForeground 		= new Foreground();
	private Hit1 myHit1 					= new Hit1();
	private Hit2 myHit2 					= new Hit2();
	private Hit3 myHit3 					= new Hit3();
	private Hit4 myHit4 					= new Hit4();
	private Hit5 myHit5 					= new Hit5();
	private int Bullets 					= 3;
	private Music mouseClickSound 			= new Music("DragonFire.wav", false);
	private Music myBackgroundMusic 		= new Music("BackgroundMusic.wav", true);
	
	
	
	
	public void paint(Graphics pen) {
		
		//this line of code is to force redraw the entire frame
		super.paintComponent(pen);
		
		//background should be drawn before the objects or based on what you want to LAYER
		cursor.paint(pen);
		myBackground.paint(pen);
		myButterfree.paint(pen);
		myForeground.paint(pen);
		myBush.paint(pen);
		myThreeShot.paint(pen);
		myTwoShot.paint(pen);
		myOneShot.paint(pen);
		myNoShot.paint(pen);
		myHit1.paint(pen);
		myCrosshair.paint(pen);
//		dog.paint(pen);

		Font f = new Font("Segoe UI", Font.PLAIN, 40);
		pen.setFont(f);
		pen.drawString("SCORE: " + totalScore, 745, 50);
		//call paint for the object
		//for objects, you call methods on them using the dot operator
		//methods use always involve parenthesis
		// duckObject.paint(pen);
		
		
		
		if (Bullets == 3) {
			myThreeShot.paint(pen);
			this.myBackgroundMusic.play();
		}
		
		if (Bullets == 2) {
			myTwoShot.paint(pen);
			
		}
		
		if (Bullets == 1) {
			myOneShot.paint(pen);
		}
		
		if (Bullets == 0) {
			myNoShot.paint(pen);
			Font t = new Font("Segoe UI", Font.PLAIN, 100);
			pen.setFont(t);
			pen.drawString("GAME OVER", 500, 400);
		}
		
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent mouse) {
	    // Runs when the mouse is clicked (pressed and released quickly).
	    // Example: You could use this to open a menu or select an object.

	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
	    // Runs when the mouse enters the area of a component (like a button).
	    // Example: You could highlight the button when the mouse hovers over it.
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
	    // Runs when the mouse leaves the area of a component.
	    // Example: You could remove the highlight when the mouse moves away.
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
	    // Runs when a mouse button is pressed down.
	    // Example: You could start dragging an object here.
	//	System.out.println(mouse.getX() + ":" +mouse.getY());
		
		boolean hit = myButterfree.checkCollision(mouse.getX(), mouse.getY());
		Bullets--;
		if(myButterfree.checkCollision(mouse.getX(), mouse.getY())) {
			totalScore += 100;
			Bullets = 3;
		}
		
		
		
		this.mouseClickSound.play();
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
	    // Runs when a mouse button is released.
	    // Example: You could stop dragging the object or drop it in place.
	}



	/*
	 * This method runs automatically when a key is pressed down
	 */
	public void keyPressed(KeyEvent key) {
		
		System.out.println("from keyPressed method:"+key.getKeyCode());
		
	}

	/*
	 * This method runs when a keyboard key is released from a pressed state
	 * aka when you stopped pressing it
	 */
	public void keyReleased(KeyEvent key) {
		
	}

	/**
	 * Runs when a keyboard key is pressed then released
	 */
	public void keyTyped(KeyEvent key) {
		
		
	}
	
	
	/**
	 * The Timer animation calls this method below which calls for a repaint of the JFrame.
	 * Allows for our animation since any changes to states/variables will be reflected
	 * on the screen if those variables are being used for any drawing on the screen.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	/*
	 * Main method to create a Frame (the GUI that you see)
	 */
	public static void main(String[] arg) {
		new Frame();
	}
	
	
	
	public Frame() {
		JFrame f = new JFrame(title);
		f.setSize(new Dimension(screenWidth, screenHeight));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("cursor.png");
		Cursor a = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "");
		this.setCursor (a);
		
		
	}

}
