package utilities;

import	java.awt.geom.Point2D;
import	java.io.Serializable;


/**
* This class represents a vector on the plane (two dimensions). It provides 
* methods for tasks such as dot product, subtraction, scalar multiplication
* and many others.
*
* @author	Johan Liesén
* @author	Jimmy Stridh
* @version	1.3, 2004-04-28
*/

//	http://mathworld.wolfram.com/Vector.html

public class Vector2D implements Cloneable, Serializable
{
	private double 	x,	// X composant
					y;	// Y composant
	
	
	
	/**
	* Constructs a two dimensional vector.
	*
	* @param	x			x-coord
	* @param	y			y-coord
	*/
	public Vector2D(double x, double y)
	{
		this.x	= x;
		this.y	= y;
	}
	
	
	/**
	* Constructs a vector from two points on the plane.
	*
	* @param	tail		first position
	* @param	head		second position
	*/
	public Vector2D(Point2D to, Point2D from)
	{		
		this(to.getX() - from.getX(), to.getY() - from.getY());
	}
	
	
	/**
	 * Contstructs a vector that is a clone of the specified one.
	 *
	 * @param	v
	 */
	public Vector2D(Vector2D v)
	{
		this.x = v.getX();
		this.y = v.getY();
	}
	
	
	/**
	* Add a vector.
	*
	* @param	v			other vector
	* @return				sum of the two vectors
	*/
	public Vector2D add(Vector2D v)
	{
		return new Vector2D(x + v.getX(), y + v.getY());
	}
	
	
	/**
	* Subtract a vector.
	*
	* @param	v			other vector
	* @return				difference between the two vectors
	*/
	public Vector2D subtract(Vector2D v)
	{
		return new Vector2D(x - v.getX(), y - v.getY());
	}
	
	
	/**
	* Multiply this vector with a scalar number.
	*
	* @param	scalar		scalar
	* @return				a vector with <code>scalar</code> times longer 
	*						composants
	*/
	public Vector2D multiply(double scalar)
	{
		return new Vector2D(x * scalar, y * scalar);
	}
	
	
	/**
	* Multiply vector with another vector.
	*
	* @param	v			other vector
	* @return				this vector multiplied with <code>v</code>.			
	*/
	public Vector2D multiply(Vector2D v)
	{
		return new Vector2D(x * v.getX(), y * v.getY());
	}
	
	
	/**
	* Mirror vector in another vector (normal).
	*
	* @param	v			normal vector
	*/
	
	//	http://mathworld.wolfram.com/Reflection.html
	
	public Vector2D mirror(Vector2D v)
	{
		double	dotV	= dot(v);
		
		return new Vector2D(x - 2 * dotV * v.getX(),
							y - 2 * dotV * v.getY());
	}
	
	
	/**
	* Project vector onto another vector
	*
	* @param	v			vector to project onto
	* @return				This vector projected onto vector <code>v</code>			
	*/
	
	//	http://mathworld.wolfram.com/Projection.html
	
	public Vector2D project(Vector2D v)
	{
		return multiply(this.dot(v) / Math.pow(v.getLength(), 2));
	}
	
	
	
	/**
	* Negates the vector.
	*
	* @return				vector pointing in the opposite direction
	*/
	public Vector2D negate()
	{
		return new Vector2D(-x, -y);
	}
	
	
	/**
	* Dot product.
	*
	* @param	v			other vector
	* @return				dot product of this and the other vector
	*/
	public double dot(Vector2D v)
	{
		return x * v.getX() + y * v.getY();
	}
		
	
	/**
	* Get norm of vector.
	*
	* @return				length of vector
	* @see					Vector2D#getLength
	*/
	public double norm()
	{
		return Math.sqrt(x * x + y * y);
	}
	
	
	/**
	* Change length of vector.
	*
	* @param	length		new length
	*/
	public Vector2D toLength(double length)
	{
		double	vectorLength	= this.norm();
	
		return (vectorLength == 0) ? this : multiply(length / vectorLength);
	}
	
	
	/**
	* Rotate the vector <code>angle</code> radians counter clockwise.
	*
	* @param	angle		radians to rotate
	* @return				rotated vector
	*/
	public Vector2D rotate(double radians)
	{
		return new Vector2D(Math.cos(radians) * x - Math.sin(radians) * y,
							Math.sin(radians) * x + Math.cos(radians) * y);
	}
	
	
	/**
	* Get the angle between the positive x-axis and the vector.
	*
	* @return				radians from the positive x-axis
	*/
	public double getAngle()
	{
		// atan2(Vector height, Vector width)
		return Math.atan2(y, x);
	}
	
	
	/**
	* Get length of vector.
	*
	* @return				length of vector
	* @see					Vector2D#norm
	*/
	public double getLength()
	{
		return norm();
	}
	
	
	/**
	* Get x composant.
	* 
	* @return				x composant
	*/
	public double getX()
	{
		return x;
	}
	
	
	/**
	* Set x composant.
	*
	* @param	x			new x composant
	*/
	public void setX(double x)
	{
		this.x	= x;
	}
	
	
	/**
	* Get y composant.
	*
	* @return				y composant
	*/
	public double getY()
	{
		return y;
	}
	
	
	/**
	* Set y composant.
	*
	* @param	y			new y composant
	*/
	public void setY(double y)
	{
		this.y	= y;
	}
	
	
	/**
	* Clone this vector.
	*
	* @return				cloned vector
	*/
	public Object clone()
	{
		return new Vector2D(x, y);
	}
	
	
	/**
	* Test vector equality.
	*
	* @param	o			other vector
	* @return				<code>true</code> if the vectors are equal, 
	*						<code>false</code> otherwise
	*/
	public boolean equals(Object o)
	{
		if (o instanceof Vector2D)
		{
			Vector2D	v	= (Vector2D) o;
			
			return ((x == v.getX()) && (y == v.getY()));
		}
		
		return false;
	}
	
	
	/**
	* Represent vector as text.
	*
	* @return				text representation of the vector
	*/
	public String toString()
	{
		return "Vector2D[" + x + ", " + y + "]";
	}
} //end Vector2D
