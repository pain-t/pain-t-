package graphics.shapes.attributes;

import java.awt.Color;


public class ColorAttributes extends Attributes {

	/** Id of the ColorAttributes. */
	public static final String ID = "ColorAttributes";
	/** Default color with which to fill the shape. */
	public static final Color FILLED_DEFAULT_COLOR = Color.RED;
	/** Default color with which to stroke the shape. */
	public static final Color STROKED_DEFAULT_COLOR = Color.BLACK;

	/** True if the shape is filled, false otherwise. */
	private boolean filled;
	/** True if the shape is stroked, false otherwise. */
	private boolean stroked;
	/** Color with which to fill the shape. */
	private Color filledColor;
	/** Color with which to stroke the shape. */
	private Color strokedColor;

	// --------------------------------------------------------------------
	
	/**
	 * Construct a ColorAttribute.
	 * @param filled True if the shape is filled, false otherwise.
	 * @param stroked True if the shape is stroked, false otherwise.
	 * @param filledColor Color with which to fill the shape.
	 * @param strokedColor Color with which to stroke the shape.
	 */
	public ColorAttributes(boolean filled, boolean stroked, Color filledColor, Color strokedColor) {
		this.filled = filled;
		this.stroked = stroked;
		this.filledColor = filledColor;
		this.strokedColor = strokedColor;
	}

	/**
	 * Construct a default ColorAttribute.
	 */
	public ColorAttributes() {
		this.filled = true;
		this.stroked = true;
		this.filledColor = FILLED_DEFAULT_COLOR;
		this.strokedColor = STROKED_DEFAULT_COLOR;
	}
	
	// --------------------------------------------------------------------
	

	@Override
	public String getId() {
		return ID;
	}

	/**
	 * Returns true if the shape is filled, false otherwise.
	 * @return true if the shape is filled, false otherwise.
	 */
	public boolean filled() {
		return this.filled;
	}

	/**
	 * Returns true if the shape is stroked, false otherwise.
	 * @return true if the shape is stroked, false otherwise.
	 */
	public boolean stroked() {
		return this.stroked;
	}

	/**
	 * Returns the color with which to fill the shape.
	 * @return The color with which to fill the shape.
	 */
	public Color filledColor() {
		return this.filledColor;
	}

	/**
	 * Returns the color with which to stroke the shape.
	 * @return The color with which to stroke the shape.
	 */
	public Color strokedColor() {
		return this.strokedColor;
	}

	/**
	 * Set the color with which to fill the shape, with the specified color.
	 * @return The new color with which to fill the shape.
	 */
	public void setFilledColor(Color color) {
		this.filledColor = color;
		this.notifyObserver();
	}
	
	/**
	 * Set the color with which to stroke the shape, with the specified color.
	 * @return The new color with which to stroke the shape.
	 */
	public void setStrokedColor(Color color) {
		this.strokedColor = color;
		this.notifyObserver();
	}
	
	@Override
	public Attributes clone() {
		return new ColorAttributes(this.filled, this.stroked, this.filledColor, this.strokedColor);
	}
}
