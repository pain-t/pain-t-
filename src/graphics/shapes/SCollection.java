package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class SCollection extends Shape {

	private ArrayList<Shape> shapes;
	private Point loc;
	private int height;
	private int width;

	public SCollection() {
		super();
		this.shapes = new ArrayList<Shape>();
		this.loc = new Point();
	}
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public ListIterator<Shape> iterator() {
		return this.shapes.listIterator();
	}
	
	public void add(Shape s) {
		this.shapes.add(s);
		this.updateContainer();
		this.notifyObserver();
	}
	
	public void remove(Shape s) {
		this.shapes.remove(s);
		this.updateContainer();
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
		for(Shape s  : this.shapes) {
			s.translate(dx, dy);
		}
		updateContainer();
		this.notifyObserver();
	}

	@Override
	public Rectangle getBounds() {
		 return new Rectangle(this.loc.x,this.loc.y,this.width,this.height);
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitCollection(this);
	}
	
	public int size() {
		return this.shapes.size();
	}

	private void updateContainer() {
		Point top = new Point(2000,2000);
		Point bottom = new Point(0,0);
		for(Shape s : this.shapes) {
			if (s.getLoc().y < top.y) {
				top.y = s.getLoc().y;
			}
			if (s.getLoc().x < top.x) {
				top.x = s.getLoc().x;
			}
			if(s.getLoc().y+s.getBounds().width> bottom.y) {
				bottom.y = s.getLoc().y+s.getBounds().height;
			}
			if (s.getLoc().x+s.getBounds().width > bottom.x) {
				bottom.x = s.getLoc().x+s.getBounds().width;
			}
			this.loc.setLocation(top);
			this.width = (int)top.distance(bottom.x,top.y);
			this.height = (int)top.distance(top.x, bottom.y);
		}
	}

}
