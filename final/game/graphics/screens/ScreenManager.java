package	game.graphics.screens;

import	java.awt.Component;
import	java.awt.Container;
import	java.awt.CardLayout;
import	java.util.ArrayList;


/**
* <p>
*	ScreenManager is an extended version of CardLayout. The difference  is that 
*   it keeps track of component names internally and can show (flip to) a 
*   component given its name.
* </p>
*
* <p>
*	The class overrides the inherited methods first(), next(), previous() and 
*	last() from CardLayout. These functions are not directly useful in the
*	ScreenManager, but are nevertheless implemented for compability. The most 
*   useful method is show(), which flips the layout to the one specified when 
*   invoking the method.
* </p>
*
* </p>
* 	See the following URL's for more information
*	<ul>
*	  <li>http://java.sun.com/docs/books/tutorial/uiswing/layout/custom.html</li>
*	  <li>http://members.cox.net/leebr/sw/software/</li>
*	  <li>http://skaiste.elekta.lt/Books/O'Reilly/Bookshelfs/books/java/exp/ch12_01.htm</li>
*	</ul>
* </p>
*
* @author	Johan Liesén
*/
public class ScreenManager extends CardLayout
{
	/** A container on which components are layed out upon */
	private final	Container		container;
	
	/* List of all Components and their respective name */
	private 		ArrayList		components;
	
	/* Current position in the component-list */
	private			int				currentEntryNo;
	
	
	
	/**
	* Constructs a new ScreenManager.
	*
	* @param	parent		container which holds all components
	*/
	public ScreenManager(Container parent)
	{
		this(parent, 0, 0);
	}
	
	
	/**
	*
	* Constructs a new ScreenManager
	*
	* @param	parent		container which holds all components
	* @param	vgap		Vertical gap
	* @param	hgap		Horizontal gap
	*
	*/
	public ScreenManager(Container parent, int vgap, int hgap)
	{
		super(vgap, hgap);
		
		container		= parent;
		components		= new ArrayList();
		currentEntryNo	= 0;
	}
	
	
	/**
	* Get the name of the current component.
	*
	* @return				name of the current component
	*/
	public String getCurrentName()
	{
		return ((ComponentEntry) components.get(currentEntryNo)).getName();
	}
	
	
	/**
	* Get the current component.
	*
	* @return				current component
	*/
	public Component getCurrent()
	{
		return ((ComponentEntry) components.get(currentEntryNo)).getComponent();
	}
	
	
	/**
	* Get a component by name.
	*
	* @param	name		name of a component
	* @return				component associated with <code>name</code> or 
	*						<em>null</em> if the component doesn't exist
	*/
	public Component getComponent(String name)
	{
		int	index	= getIndexOf(name);
		
		return (index != -1) ? ((ComponentEntry) components.get(index)).getComponent() : null;
	}
	
	
	/**
	* Get index of the entry assigned to <code>name</code> in the list of 
	* component entries.
	*
	* @return				index of component with the specified name
	*/
	private int getIndexOf(String name)
	{
		int	entries	= components.size();
		
		for (int i = 0; i < entries; i++)
		{
			if (((ComponentEntry) components.get(i)).getName().equals(name))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	* Get index of the entry assigned to <code>comp</code> in the list of 
	* component entries.
	*
	* @return				index of component
	*/
	private int getIndexOf(Component comp)
	{
		int	entries	= components.size();
		
		for (int i = 0; i < entries; i++)
		{
			if (((ComponentEntry) components.get(i)).getComponent() == comp)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	* Add a new component.
	*
	* Used by the Layout to add Components to the stack. Note that 
	* <code>constraints</code> must be of type <code>String</code>.
	*
	* @param	comp		component to be added
	* @param	constraints	an identifier for this card
	* @throws	IllegalArgumentException 
	*						if constraints is not a String
	* @see					java.awt.CardLayout#addLayoutComponent
	*/
	public void addLayoutComponent(Component comp, Object constraints) 
												 throws IllegalArgumentException
	{
		// The object that identifies the component must be a string
		if (constraints instanceof String)
		{
			// Call CardLayout's add method
			super.addLayoutComponent(comp, constraints);
			
			// Create new entry
			ComponentEntry	entry	= new ComponentEntry(comp, (String) constraints);
			
			// See if the name exists
			if (components.size() == 0)
			{
				// List of components is empty so just add the new one
				components.add(entry);
			}
			else
			{
				int	index	= getIndexOf(comp);
				
				if (index != -1)	// Replace component if it exists ...
				{					
					components.set(index, entry);
				}
				else	// ... else add it
				{
					components.add(entry);
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("Constraints must be of type String");
		}
	}
	
	
	/**
	* Remove a component from the deck.
	*
	* @param	comp		Component to be removed
	* @see					java.awt.CardLayout#removeLayoutComponent
	*/
	public void removeLayoutComponent(Component comp)
	{
		super.removeLayoutComponent(comp);
		
		// Remove component entry from internal list
		int	index	= getIndexOf(comp);
		
		if (index != -1)
		{
			components.remove(index);
		}
	}
	
	
	/**
	* Switches view.
	*
	* Flips view by setting the current view to being invisible, and instead 
	* making the component given by <code>name</code> visible.
	*
	* @param	name		name of the wanted component
	*/
	public void show(String name)
	{		
		super.show(container, name);
		
		// Update internal state
		currentEntryNo		= getIndexOf(name);
		
		// Request focus
		getCurrent().requestFocus();
	}

	/**
	* Shows a component onto an external container.
	*
	* @param	parent		Container which do the layout
	* @param	name		name of the wanted component
	*/
	public void show(Container parent, String name)
	{
		if (container == parent)
		{
			// Call own show method
			show(name);
		}
		else
		{
			// Update is done on another Container, no internal updates needed
			super.show(parent, name);
		}
	}
	
	
	/**
	* Flip to the first card.
	*
	*/
	public void first()
	{
		super.first(container);
		
		// Update internal state
		currentEntryNo	= 0;
	}
	
	
	/**
	* Flip to the first card.
	*
	* @param	parent		Container which do the layout
	* @see					java.awt.CardLayout#first
	*/
	public void first(Container parent)
	{
		if (container == parent)
		{
			first();
		}
		else
		{
			super.first(parent);
		}
	}
	
	
	/**
	* Flip to the next card.
	*
	*/
	public void next()
	{
		super.next(container);
		
		// Set current to the next component
		currentEntryNo	= (currentEntryNo + 1 + components.size()) % components.size();
	}
	
	
	/**
	* Flip to the next card.
	*
	* @param	parent		Container which do the layout
	* @see					java.awt.CardLayout#next
	*/
	public void next(Container parent)
	{
		if (container == parent)
		{
			next();
		}
		else
		{
			super.next(parent);
		}
	}
	
	
	/**
	* Flip to the previous card.
	*
	*/
	public void previous()
	{
		super.previous(container);
		
		// Set current to the previous component (wrap from start to end of component list)
		currentEntryNo	= (currentEntryNo - 1 + components.size()) % components.size();
	}
	
	
	/**
	* Flip to the previous card.
	*
	* @param	parent		Container which do the layout
	* @see					java.awt.CardLayout#previous
	*/
	public void previous(Container parent)
	{
		if (container == parent)
		{
			previous();
		}
		else
		{
			super.previous(parent);
		}
	}
		
	
	/**
	* Flip to the last card.
	*
	*/
	public void last()
	{
		super.last(container);
		
		// Update internal state
		currentEntryNo	= components.size() - 1;
	}
	
	
	/**
	* Flip to the last card.
	*
	* @param	parent		Container which do the layout
	* @see					java.awt.CardLayout#last
	*/
	public void last(Container parent)
	{
		if (container == parent)
		{
			last();
		}
		else
		{
			super.last(parent);
		}
	}
	
	
	/**
	* Repaint the current component. This is only because when repainting a
	* Frame its children doesn't get repainted.
	*
	*/
	public void repaint()
	{
		getCurrent().repaint();
	}
		
	
	/**
	* Name of the class.
	*
	* @return				name of the class + additional information
	*/
	public String toString()
	{
		return getClass().getName() + "(" + currentEntryNo + ":" + getCurrentName() + ")";
	}
	
	
	
	/**
	* A key-value pair representing a component and it's name.
	*
	*/
	private final class ComponentEntry
	{
		private Component	component;	// Component
		private	String		name;		// Component name
		
		
		
		/**
		* Create an entry.
		*
		* @param	c			the component
		* @param	s			name of the component
		*/
		public ComponentEntry(Component c, String s)
		{
			name		= s;
			component	= c;
		}
		
		
		
		/**
		*
		* @return				name of this <code>ComponentEntry</code>
		*/
		public String getName()
		{
			return name;
		}
		
		
		/**
		*
		* @return				component of this <code>ComponentEntry</code>
		*/
		public Component getComponent()
		{
			return component;
		}
		
		
		/**
		* String representation.
		*
		* @return				text representation of the current entry
		*/
		public String toString()
		{
			return getClass().getName() + "[" + name + "]";
		}
	} //end ComponentEntry
} //end ScreenManager