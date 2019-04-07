package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

public class SCircle extends Shape {

	private int radius;
	private Point loc;

	public SCircle(Point loc, int radius) {
		super();
		this.radius = radius;
		this.loc = loc;
	}
	
	public int getRadius() {
		return this.radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
		this.notifyObserver();
	}

	@Override
	public Point getLoc() {
		return this.loc;
	}

	@Override
	public void setLoc(Point p) {
		this.loc.setLocation(p);
		this.notifyObserver();
	}

	@Override
	public void translate(int dx, int dy) {
		this.loc.translate(dx, dy);
		this.notifyObserver();
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.loc.x,this.loc.y,this.radius*2,this.radius*2);
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitCircle(this);
	}

}
