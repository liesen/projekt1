package game.graphics;

import java.awt.Color.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;

/**
 * Draws a progress bar that will be filled depending
 * on the amount specified. Optionally, the current percentage filled
 * can be displayed. Border, text and bar color can be customized.
 *
 * @author Jimmy Stridh
 * @version 1.4
 */
public class ProgressBar {
	
	/** The x position of the bar */
	private int xPos;
	
	/** The y position of the bar */
	private int yPos;
	
	/** The y position of the bar */
	private int width;
	
	/** The height of the bar in pixels */
	private int height;
	
	/** The maximum amount the bar should have */
	int barMax;
	
	/** The current amount */
	int barAmount;

	/** How many pixels one unit should represent */
	int oneBarUnit;
	
	/** Sets wheter the % indication should be visible */
	boolean showPercentage;
	
	/** The color of the outline */
	Color borderColor;
	
	/** The start color of the inner bar */
	Color startColor;

	/** The end color of the inner bar */
	Color endColor;	
	
	/** The color of the percentage display */
	Color textColor;	

	/** The constructor, initializes variables
	 * @param xPos The x position of the bar.
	 * @param yPos The y position of the bar.
	 * @param width The weight of the bar in pixels.
	 * @param height The height of the bar in pixels.
	 * @param showPercentage Decides wheter or not to show the current
	 * percentage.
	 */
	public ProgressBar(int xPos,int yPos,int width,int height,
							boolean showPercentage)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		borderColor = Color.black;
		startColor = Color.black;
		endColor = Color.blue;
		textColor = Color.black;
		this.showPercentage = showPercentage;
		barMax = 1;
		barAmount = 0;
		oneBarUnit = 1;
	}
	
	/** Set the start color of the inner bar
	 * @param c the color, of type java.awt.Color
	 */
	public void setStartColor(Color c)
	{
		startColor = c;
	}
	
	/** Set the end color of the inner bar
	 * @param c the color, of type java.awt.Color
	 */
	public void setEndColor(Color c)
	{
		endColor = c;
	}	

	/** Set the color of the outline
	 * @param c the color, of type java.awt.Color
	 */
	public void setBorderColor(Color c)
	{
		borderColor = c;
	}	
	
	/** Set the color of the text for the percentage display
	 * @param c the color, of type java.awt.Color
	 */
	public void setTextColor(Color c)
	{
		textColor = c;
	}								
	
	/** Set the maximum amount the bar can reach
	 * @param barMax the maxiumum amount
	 */
	public void setMax(int barMax)
	{
		this.barMax = barMax;
		oneBarUnit = width/barMax;
		//reset amount
		barAmount=0;
	}
	
	/** Used to decides if the percentage should be displayed or not.
	 * @param showPercentage
	 */
	public void setPercentageDisplay(boolean showPercentage)
	{
		this.showPercentage = showPercentage;
	}
	
	/** Increase the bar by a given amount.
	 * @param amount amount to add to the bar
	 */
	public void incBar(int amount){
		//if the added amount would make the bar pass max, round off
		if((barAmount+amount)>barMax){
			barAmount = barMax;
		} else {
			barAmount = barAmount + amount;
		}
	}

	/** Decrease the bar by a given amount.
	 * @param amount amount to subtract from the bar
	 */	
	public void decBar(int amount){
		//if the added amount would make the bar go below zero,round off		
		if((barAmount+amount)<0){
			barAmount = 0;
		} else {
			barAmount = barAmount - amount;
		}
	}

	/** Set the current amount of the bar
	 * @param amount the amount to adjust the bar to
	 */	
	public void setAmount(int amount){
		if(amount>barMax){
			barAmount = barMax;
		} else {
			barAmount = amount;
		}
	}
	
	/** Gets the percentage the current amount represents
	 * @return the percentage of the bar currently filled 
	 */
	public int getPercentage()
	{
		/*special case, if either the amount or the max is zero, this
		will mean 0 % */
		if(barMax == 0 || barAmount == 0){
			return 0;
		} else {
			return (int)(((double)barAmount/(double)barMax)*100.0);
		}
	}
	/** Paints the progress bar according to the previously set options
	 * @param g the graphics object to paint to
	 */
	public void paint(Graphics2D g)
	{
		//Keep track of current color
		Color oldColor = g.getColor();

		//draw the outline
		g.setColor(borderColor);		
		g.drawRect(xPos,yPos,width,height);
		//make it double size
		g.drawRect(xPos+1,yPos+1,width-2,height-2);
		
		// A non-cyclic gradient
		GradientPaint gradient = new GradientPaint(xPos+2,yPos+2,
				    startColor, xPos+width-3, yPos+2, endColor);
		g.setPaint(gradient);
		//fill the bar with the current amount
		g.fillRect(xPos+2,yPos+2,(oneBarUnit*barAmount)-3,height-3);
	
		if(showPercentage)
		{
			//display the current percentage filled
			g.setColor(textColor);
			g.drawString(getPercentage() + " %",(width/2)+xPos-13
							   ,(height/2)+yPos+4);
		}
		
		//restore the color
		g.setColor(oldColor);	
	}
}