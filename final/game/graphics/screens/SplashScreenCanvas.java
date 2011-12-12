package game.graphics.screens;

import	java.awt.Color;
import	java.awt.Dimension;
import	java.awt.Font;
import	java.awt.Graphics2D;
import	java.awt.Image;
import	java.awt.Toolkit;
import	java.awt.font.TextLayout;
import	javax.swing.ImageIcon;

import	game.graphics.BufferedCanvas;
import	game.graphics.ProgressBar;
import	utilities.DataPreLoader;

/**
 * The canvas of the splashscreen. What is displayed.
 */
public class SplashScreenCanvas extends BufferedCanvas
{
	/** The main progress bar of the splash screen */
	private ProgressBar	progressBar;
	
	/** The message to be displayed above the progress bar*/
	private String		progressMessage = " ";
	
	/** Font size of the message to be displayed above the progress bar*/
	private int			fontSize = 14;

	/** Font type of the message to be displayed above the progress bar*/
	private Font		textFont;
	
	/** Screen width */
	private int			width;
	
	/** Screen height */
	private int			height;
	
	/** The background image to be displayed */
	private Image		backgroundImage;
	
	/**
	*	Constructs a canvas for the splash screen
	*	@param	width	Width of the splash screen
	*	@param	height	Height of the splash screen
	*/
	public SplashScreenCanvas(int width, int height)
	{
		super(new Dimension(width, height));
		this.width = width;
		this.height = height;
		
		// Create a progress bar
		progressBar	= new ProgressBar(getCenteredCoord(width, 400), 
									  400,
									  400,
									  20,
									  true);
		
		textFont	= new Font("MONOSPACED", Font.PLAIN, fontSize);
		
		//progressBar.setMax(1);
		//progressBar.setAmount(0);
		
		//Set the colors for the progress bar
		progressBar.setBorderColor(new Color(0x33, 0x33, 0x33));
		progressBar.setStartColor(Color.WHITE);
		progressBar.setEndColor(new Color(0x99, 0x99, 0x99));
		
		backgroundImage	= Toolkit.getDefaultToolkit().
						     getImage("resources/images/splash_background.jpg");
		
		//Make sure the whole image is loaded
		backgroundImage = new ImageIcon(backgroundImage).getImage();		

	}
	
	/**
	*	Render the canvas to the specified graphics object
	*	@param g	The <code>Graphics2D</code>
	*/
	public void render(Graphics2D g)
	{
		g.drawImage(backgroundImage, 0, 0, width, height, this);
		
		g.setColor(Color.RED);
		TextLayout tl = new TextLayout(progressMessage, textFont, 
						    g.getFontRenderContext());
		int textXpos = getCenteredCoord(width, (int) tl.getBounds().getWidth());
		int textYpos = 390;
		
		tl.draw(g, (float) textXpos, (float) textYpos);
		progressBar.paint(g);
	}
	


	public void setMessage(String text)
	{
		progressMessage = text;
		repaint();
	}
	
	/**
	*	Retrieve the progress bar of the Splash Screen's canvas.
	*	@return				The main progress bar of the splash screen
	*/
	public ProgressBar getProgressBar()
	{
		return progressBar;
	}

	/**
	*	Get the coordinate to center an object on the screen
	*
	*	@param	screenWidth		The width of the screen
	*	@param	objectWidth		The width of the object
	*/
	private int getCenteredCoord(int screenWidth,int objectWidth)
	{
		return screenWidth/2 - objectWidth/2;
	}
} //end SplashScreenCanvas class