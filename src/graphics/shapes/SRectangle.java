package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

import graphics.shapes.attributes.Attributes;

public class SRectangle extends Shape {

	/**The bounds. */
	private Rectangle rect;

	// --------------------------------------------------------------------

	/**
	 * Construct a Rectangle.
	 * @param rect Bounds of the Rectangle.
	 */
	public SRectangle(Rectangle rect) {
		super();
		this.rect = rect;
	}
	/**
	 * Construct a Rectangle.
	 * @param p Location of the bounds.
	 * @param width Width of the bounds.
	 * @param height Height of the bounds.
	 */
	public SRectangle(Point p, int width, int height) {
		this(p.x, p.y, width, height);
	}

	/**
	 * Construct a Rectangle.
	 * @param x X position of the bounds.
	 * @param y Y position of the bounds.
	 * @param width Width of the bounds.
	 * @param height Height of the bounds.
	 */
	public SRectangle(int x, int y, int width, int height) {
		this(new Rectangle(x, y, width, height));
	}
	
	// --------------------------------------------------------------------

	/**
	 * Returns the rectangle (bounds).
	 * @return
	 */
	public Rectangle getRect() {
		return this.rect;
	}

	@Override
	public Point getLoc() {
		return this.rect.getLocation();
	}

	@Override
	public void setLoc(Point p) {
		this.rect.setLocation(p);
		this.notifyObserver();
	}

	@Override
	public void translate(int dx, int dy) {
		this.rect.translate(dx, dy);
		this.notifyObserver();
	}

	@Override
	public Rectangle getBounds() {
		return this.rect;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.rect.setBounds(x, y, width, height);
		this.notifyObserver();
	}
	
	/**
	 * Setthe size of the bounds with the specified width and height.
	 * @param width New width of the bounds.
	 * @param height New height of the bounds.
	 */
	public void setSize(int width, int height) {
		this.rect.setSize(width, height);
		this.notifyObserver();
	}

	@Override
	public void resize(Point cursor, int dx, int dy, int handler) {
		if(handler == 0) resizeTL(cursor, dx, dy);
		else if(handler == 1) resizeTR(cursor, dx, dy);
		else if(handler == 2) resizeBL(cursor, dx, dy);
		else if(handler == 3) resizeBR(cursor, dx, dy);
	}
	
	/**
	 * Resizes the Shape by specified x and x offset, by its top left corner.
	 * @param cursor The position of the cursor.
	 * @param dx x offset.
	 * @param dy y offset.
	 */
	private void resizeTL(Point cursor, int dx, int dy) {
		int x = this.rect.x;
		int y = this.rect.y;
		int width = this.rect.width;
		int height = this.rect.height;
		if(cursor.x < x + width && width - dx > 0) {
			x += dx;
			width -= dx;
		}
		if(cursor.y < y + height && height - dy > 0) {
			y += dy;
			height -= dy;
		}
		
		this.setBounds(x, y, width, height);
	}
	
	/**
	 * Resizes the Shape by specified x and x offset, by its top right corner.
	 * @param cursor The position of the cursor.
	 * @param dx x offset.
	 * @param dy y offset.
	 */
	private void resizeTR(Point cursor, int dx, int dy) {
		int x = this.rect.x;
		int y = this.rect.y;
		int width = this.rect.width;
		int height = this.rect.height;
		if(cursor.x > x && width + dx > 0) {
			width += dx;
		}
		if(cursor.y < y + height && height - dy > 0) {
			y += dy;
			height -= dy;
		}
		
		this.setBounds(x, y, width, height);
	}
	/**
	 * Resizes the Shape by specified x and x offset, by its bottom left corner.
	 * @param cursor The position of the cursor.
	 * @param dx x offset.
	 * @param dy y offset.
	 */
	private void resizeBL(Point cursor, int dx, int dy) {
		int x = this.rect.x;
		int y = this.rect.y;
		int width = this.rect.width;
		int height = this.rect.height;
		if(cursor.x < x + width && width - dx > 0) {
			x += dx;
			width -= dx; 
		}
		if(cursor.y > y && height + dy > 0) {
			height += dy;
		}
		
		this.setBounds(x, y, width, height);
	}
	/**
	 * Resizes the Shape by specified x and x offset, by its bottom right corner.
	 * @param cursor The position of the cursor.
	 * @param dx x offset.
	 * @param dy y offset.
	 */
	private void resizeBR(Point cursor, int dx, int dy) {
		int x = this.rect.x;
		int y = this.rect.y;
		int width = this.rect.width;
		int height = this.rect.height;
		if(cursor.x > x&& width + dx > 0) {
			width += dx;
		}
		if(cursor.y > y && height + dy > 0) {
			height += dy;
		}
		this.setBounds(x, y, width, height);
	}
	
		
	@Override
	public void rotate() {
		this.setBounds(this.rect.x + this.rect.width / 2 - this.rect.height / 2, this.rect.y + this.rect.height / 2 - this.rect.width / 2, this.rect.height, this.rect.width);
	}
	
	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitRectangle(this);
	}
	
	@Override
	public Shape clone() {
		SRectangle sr = new SRectangle((Rectangle) this.rect.clone());
		
		for(Attributes a : this.getAttributes().values())
			sr.addAttributes(a.clone());
		
		return sr;
	}
}
