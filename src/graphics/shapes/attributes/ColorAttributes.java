package graphics.shapes.attributes;

import java.awt.Color;


public class ColorAttributes extends Attributes {

	public static final String ID = "ColorAttributes";
	public static final Color FILLED_DEFAULT_COLOR = Color.RED;
	public static final Color STROKED_DEFAULT_COLOR = Color.BLACK;

	private boolean filled;
	private boolean stroked;
	private Color filledColor;
	private Color strokedColor;

	// --------------------------------------------------------------------
	
	public ColorAttributes(boolean filled, boolean stroked, Color filledColor, Color strokedColor) {

		this.filled = filled;
		this.stroked = stroked;
		this.filledColor = filledColor;
		this.strokedColor = strokedColor;
	}

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

	public boolean filled() {
		return this.filled;
	}

	public boolean stroked() {
		return this.stroked;
	}

	public Color filledColor() {
		return this.filledColor;
	}

	public Color strokedColor() {
		return this.strokedColor;
	}

	public void setFilledColor(Color color) {
		this.filledColor = color;
		this.notifyObserver();
	}
	
	@Override
	public Attributes clone() {
		return new ColorAttributes(this.filled, this.stroked, this.filledColor, this.strokedColor);
	}
}
