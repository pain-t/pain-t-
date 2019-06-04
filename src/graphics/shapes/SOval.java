package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

import graphics.shapes.attributes.Attributes;

public class SOval extends SRectangle {

	public static final String ID = "Oval";

	/**
	 * Construct an Oval.
	 * 
	 * @param rect
	 *            Bounds of the Rectangle.
	 */
	public SOval(Rectangle rect) {
		super(rect);
	}

	/**
	 * Construct an Oval.
	 * 
	 * @param p
	 *            Location of the bounds.
	 * @param width
	 *            Width of the bounds.
	 * @param height
	 *            Height of the bounds.
	 */
	public SOval(Point p, int width, int height) {
		super(p, width, height);
	}

	/**
	 * Construct an Oval.
	 * 
	 * @param x
	 *            X position of the bounds.
	 * @param y
	 *            Y position of the bounds.
	 * @param width
	 *            Width of the bounds.
	 * @param height
	 *            Height of the bounds.
	 */
	public SOval(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	// --------------------------------------------------------------------

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitOval(this);
	}

	@Override
	public String getId() {
		return SOval.ID;
	}
	
	@Override
	public Shape clone() {
		SOval so = new SOval((Rectangle) this.getBounds().clone());
		
		for(Attributes a : this.getAttributes().values())
			so.addAttributes(a.clone());
		
		return so;
	}
}
