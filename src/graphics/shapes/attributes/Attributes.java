package graphics.shapes.attributes;

import java.io.Serializable;

import graphics.ui.Observable;

public abstract class Attributes implements Observable , Serializable{
	/**
	 * Returns the id of the attribute.
	 * @return The id of the attribute.
	 */
	public abstract String getId();
	
	/**
	 * Clone the attribute.
	 */
	public abstract Attributes clone();
}
