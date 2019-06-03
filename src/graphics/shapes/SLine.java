package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

import graphics.shapes.attributes.Attributes;

public class SLine extends SRectangle{
	
	/**
	 * Creates a line within specified bounds.
	 * @param rect Bounds of the line.
	 */
	public SLine(Rectangle rect) {
		super(rect);
	}
	
	/**
	 * Construct an Line within specified bounds.
	 * @param p Location of the bounds.
	 * @param width Width of the bounds.
	 * @param height Height of the bounds.
	 */
	public SLine(Point p, int width, int height) {
		super(p, width, height);
	}
	
	/**
	 * Construct a Line within specified bounds.
	 * @param x X position of the bounds.
	 * @param y Y position of the bounds.
	 * @param width Width of the bounds.
	 * @param height Height of the bounds.
	 */
	public SLine(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	// --------------------------------------------------------------------
	
	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitLine(this);
	}
	
}
