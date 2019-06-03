package graphics.shapes.attributes;

import graphics.ui.Observable;

public abstract class Attributes implements Observable {
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
