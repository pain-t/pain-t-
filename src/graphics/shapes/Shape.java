package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.HashMap;

import graphics.shapes.attributes.Attributes;
import graphics.ui.Observable;
import graphics.ui.Observer;

public abstract class Shape implements Observable{

	/** Map of attributes between the attributes and their ids. */
	private Map<String, Attributes> attributes;

	/** Size (in pixels) of the corner handlers. */
	protected final int handlerSize = 8;
	
	// --------------------------------------------------------------------
	
	/**
	 * Constructs the body of a shape by initializing an empty map of attributes.
	 */
	public Shape() {
		this.attributes = new HashMap<String, Attributes>();
	}

	// --------------------------------------------------------------------
	
	/**
	 * Returns the attribute from the specified id in the map of attributes.
	 * @param id Id of the wanted attribute.
	 * @return The attribute from the specified id in the map of attributes.
	 */
	public Attributes getAttributes(String id) {
		return this.attributes.get(id);
	}
	
	/**
	 * Appends the specified attribute to the end of the map of attributes.
	 * @param attr Attribute to be appened to the end of the map.
	 */
	public void addAttributes(Attributes attr) {
		this.attributes.put(attr.getId(), attr);
	}
	
	/**
	 * Returns the map of attributs.
	 * @return The map of attributes.
	 */
	public Map<String,Attributes> getAttributes(){
		return this.attributes;
	}
	
	/**
	 * Sets the observer for the shapes and all of its attributes.
	 * @param The observer.
	 */
	public void register(Observer o) {
		observ.add(o);
		for(Attributes a : this.getAttributes().values()) {
			if(a != null) a.register(o);
		}
	}
	
	/**
	 * Gets the location of the Shape.
	 * @return The location of the Shape.
	 */
	public abstract Point getLoc();
	/**
	 * Updates the location of the Shape with the specifie location. 
	 * @param New location.
	 */
	public abstract void setLoc(Point p);
	/**
	 * Translates the Shape by the specified x and y offset.
	 * @param dx x offset.
	 * @param dy y offset.
	 */
	public abstract void translate(int dx, int dy);
	/**
	 * Gets the bounding Rectangle for this Shape.
	 */
	public abstract Rectangle getBounds();
	/**
	 * Resizes the Shape by the specified handler, by the specified x and x offset.
	 * @param cursor The position of the cursor.
	 * @param dx x offset.
	 * @param dy y offset.
	 * @param handler Which corner is selected (0: top left, 1: top right, 2: bottom left, 3: bottom right).
	 */
	public abstract void resize(Point cursor, int dx, int dy, int handler);

	/**
	 * Rotates the shape.
	 */
	public abstract void rotate();
	/**
	 * Accepts the visit of the ShapeVisitor.
	 * @param sv ShapeVisitor to be accepted.
	 */
	public abstract void accept(ShapeVisitor sv);
	/**
	 * Clones the Shape and its attributes.
	 */
	@Override
	public abstract Shape clone();
	
	/**
	 * Returns the location of the top right corner handler.
	 * @param bounds Bounds of the shape.
	 * @return The location of the top right corner handler.
	 */
	public Rectangle getTRhandler(Rectangle bounds) {
		return new Rectangle(bounds.x + bounds.width - this.handlerSize / 2, bounds.y - this.handlerSize / 2, this.handlerSize, this.handlerSize);
	}
	/**
	 * Returns the location of the top left corner handler.
	 * @param bounds Bounds of the shape.
	 * @return The location of the top left corner handler.
	 */
	public Rectangle getTLhandler(Rectangle bounds) {
		return new Rectangle(bounds.x - this.handlerSize / 2, bounds.y - this.handlerSize / 2, this.handlerSize, this.handlerSize);
	}
	/**
	 * Returns the location of the bottom right corner handler.
	 * @param bounds Bounds of the shape.
	 * @return The location of the bottom right corner handler.
	 */
	public Rectangle getBRhandler(Rectangle bounds) {
		return new Rectangle(bounds.x + bounds.width - this.handlerSize / 2, bounds.y + bounds.height - this.handlerSize / 2, this.handlerSize, this.handlerSize);
	}
	/**
	 * Returns the location of the bottom left corner handler.
	 * @param bounds Bounds of the shape.
	 * @return The location of the bottom left corner handler.
	 */
	public Rectangle getBLhandler(Rectangle bounds) {
		return new Rectangle(bounds.x - this.handlerSize / 2, bounds.y + bounds.height - this.handlerSize / 2, this.handlerSize, this.handlerSize);
	}
}
