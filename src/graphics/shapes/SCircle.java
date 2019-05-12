package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SCircle extends SOval {

	public SCircle(Point loc, int diameter) {
		super(loc, diameter, diameter);
	}
	
	public SCircle(int x, int y, int diameter) {
		this(new Point(x, y), diameter);
	}
	
	// --------------------------------------------------------------------
	
	@Override
	public void resize(Point cursor, int dx, int dy, int handler) {
		if(handler == 0) resizeTL(cursor, dx, dy);
		else if(handler == 1) resizeTR(cursor, dx, dy);
		else if(handler == 2) resizeBL(cursor, dx, dy);
		else if(handler == 3) resizeBR(cursor, dx, dy);
	}
	
	private void resizeTL(Point cursor, int dx, int dy) {
		Rectangle bounds = this.getBounds();
		int off;
		
		if(cursor.y < bounds.y || cursor.x < bounds.x) off = Math.max(-dx, -dy);
		else off = Math.min(-dx, -dy);
		
		if(cursor.x > bounds.x + bounds.width && cursor.y > bounds.y + bounds.height) off = 0;
			
		this.setBounds(bounds.x - off, bounds.y - off, bounds.width + off, bounds.height + off);
	}
	private void resizeTR(Point cursor, int dx, int dy) {
		Rectangle bounds = this.getBounds();
		int off;
		
		if(cursor.y < bounds.y || cursor.x > bounds.x + bounds.width) off = Math.max(dx, -dy);
		else off = Math.min(dx,  -dy);
		
		if(cursor.x < bounds.x && cursor.y > bounds.y + bounds.height) off = 0;
			
		this.setBounds(bounds.x, bounds.y - off, bounds.width + off, bounds.height + off);
	}
	private void resizeBL(Point cursor, int dx, int dy) {
		Rectangle bounds = this.getBounds();
		int off;
		
		if(cursor.y > bounds.y + bounds.height || cursor.x < bounds.x) off = Math.max(-dx, dy);
		else off = Math.min(-dx,  dy);
		
		if(cursor.x > bounds.x + bounds.width && cursor.y < bounds.y) off = 0;
			
		this.setBounds(bounds.x - off, bounds.y, bounds.width + off, bounds.height + off);
	}
	private void resizeBR(Point cursor, int dx, int dy) {
		Rectangle bounds = this.getBounds();
		int off;
		
		if(cursor.y > bounds.y + bounds.height || cursor.x > bounds.x + bounds.width) off = Math.max(dx, dy);
		else off = Math.min(dx,  dy);
		
		if(cursor.x < bounds.x && cursor.y < bounds.y) off = 0;
			
		this.setBounds(bounds.x, bounds.y, bounds.width + off, bounds.height + off);
	}
}
