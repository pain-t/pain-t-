package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

import graphics.shapes.attributes.Attributes;

public class SLine extends SRectangle{

	public SLine(Rectangle rect) {
		super(rect);
	}
	
	public SLine(Point p, int width, int height) {
		super(p, width, height);
	}
	
	public SLine(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	// --------------------------------------------------------------------
	
	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitLine(this);
	}
	
}
