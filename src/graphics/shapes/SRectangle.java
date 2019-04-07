package graphics.shapes;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class SRectangle extends Shape {

	private Rectangle rect;

	public SRectangle(Point p, int width, int height) {
		super();
		this.rect = new Rectangle(p,new Dimension(width,height));
	}

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
		return this.rect.getBounds();
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitRectangle(this);
	}

}
