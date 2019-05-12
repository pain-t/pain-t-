package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SOval extends SRectangle {
		
	public SOval(Rectangle rect) {
		super(rect);
	}
	
	public SOval(Point p, int width, int height) {
		super(p, width, height);
	}
	
	public SOval(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	// --------------------------------------------------------------------
	
	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitOval(this);
	}
}
