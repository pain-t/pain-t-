package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import graphics.shapes.attributes.Attributes;

public class SCollection extends Shape {

	private ArrayList<Shape> shapes;
	private Point loc;
	private int height;
	private int width;

	// --------------------------------------------------------------------
	
	public SCollection() {
		super();
		this.shapes = new ArrayList<Shape>();
		this.loc = new Point();
	}
	
	// --------------------------------------------------------------------
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public Shape getShape(int i) {
		Shape s = null;
		if(i > -1 && i < this.size()) {
			s = this.shapes.get(i);
		}
		
		return s;
	}
	
	public ListIterator<Shape> iterator() {
		return this.shapes.listIterator();
	}

	public ListIterator<Shape> iterator(int index) {
		return this.shapes.listIterator(index);
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
	
	public void empty() {
		this.shapes.clear();
		this.updateContainer();
		this.notifyObserver();
	}
	
	public void closer(Shape s) {
		if(s != null) {
			int i = this.shapes.indexOf(s);
			if(i < this.shapes.size() - 1) {
				this.shapes.remove(i);
				this.shapes.add(i + 1, s);
			}
		}

		this.notifyObserver();
	}
	public void farther(Shape s) {
		if(s != null) {
			int i = this.shapes.indexOf(s);
			if(i > 0) {
				this.shapes.remove(i);
				this.shapes.add(i - 1, s);
			}
		}
		
		this.notifyObserver();
	}
	public void first(Shape s) {
		if(s != null) {
			int i = this.shapes.indexOf(s);
			if(i < this.shapes.size() - 1) {
				this.shapes.remove(i);
				this.shapes.add(this.shapes.size(), s);
			}
		}
		
		this.notifyObserver();
	}

	public int size() {
		return shapes.size();
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
		this.loc.translate(dx, dy);
		this.notifyObserver();
	}
	
	@Override
	public void rotate() {
		for(Shape s : shapes)
			s.rotate();
		
		this.updateContainer();
		this.notifyObserver();
	}
	
	@Override
	public void resize(Point cursor, int dx, int dy, int handler) {
		Rectangle bounds = getBounds();
		for(Shape s : this.shapes) {
			Rectangle b = s.getBounds();
			Point c = (Point) cursor.clone();

			if(handler == 0) 		// top left corner
				c.translate(b.x - bounds.x, b.y - bounds.y);
			else if(handler == 1)	// top right corner
				c.translate(b.x + b.width - bounds.x - bounds.width, b.y - bounds.y);
			else if(handler == 2)	// bottom left corner
				c.translate(b.x - bounds.x, b.y + b.height - bounds.y - bounds.height);
			else					// bottom right corner
				c.translate(b.x + b.width - bounds.x - bounds.width, b.y + b.height - bounds.y - bounds.height);

			s.resize(c, dx, dy, handler);
		}
		
		this.updateContainer();
		this.notifyObserver();
	}

	@Override
	public Rectangle getBounds() {
		 return new Rectangle(this.loc.x, this.loc.y, this.width, this.height);
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitCollection(this);
	}

	private void updateContainer() {
		Point top = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point bottom = new Point(0, 0);
		
		for(Shape s : this.shapes) {
			if (s.getLoc().y < top.y) {
				top.y = s.getLoc().y;
			}
			if (s.getLoc().x < top.x) {
				top.x = s.getLoc().x;
			}
			if(s.getLoc().y + s.getBounds().width > bottom.y) {
				bottom.y = s.getLoc().y + s.getBounds().height;
			}
			if (s.getLoc().x + s.getBounds().width > bottom.x) {
				bottom.x = s.getLoc().x + s.getBounds().width;
			}
			this.loc.setLocation(top);
			this.width = (int) top.distance(bottom.x, top.y);
			this.height = (int) top.distance(top.x, bottom.y);
		}
	}

	@Override
	public Shape clone() {
		SCollection sc = new SCollection();
		
		for(Shape s : this.shapes)
			sc.add(s.clone());
		
		for(Attributes a : this.getAttributes().values())
			sc.addAttributes(a.clone());
		
		return sc;
	}
	
	/*
	 @Override
	public Rectangle getBounds() {
		Iterator<Shape> it = this.iterator();
		
		if(it.hasNext()) {
			Rectangle r = it.next().getBounds();
			int tlx = r.x;				// top left corner	
			int tly = r.y;
			int brx = r.width + tlx;	// bottom right corner
			int bry = r.height + tly;
			
			int tlxbis, tlybis, brxbis, brybis;

			while(it.hasNext()) {
				r = it.next().getBounds();
				tlxbis = r.x;
				tlybis = r.y;
				brxbis = r.width + tlxbis;
				brybis = r.height + tlybis;
				if(tlxbis < tlx) tlx = tlxbis;
				if(tlybis < tly) tly = tlybis;
				if(brxbis > brx) brx = brxbis;
				if(brybis > bry) bry = brybis;
			}
			
			return new Rectangle(tlx, tly, brx - tlx, bry - tly);
		}
			
		return null;
	}
	
	@Override
	public Point getLoc() {
		Iterator<Shape> it = this.iterator();
		
		if(it.hasNext()) {
			Shape s = it.next(); 
			int x = s.getBounds().x;		// top left corner
			int y = s.getBounds().y;
			
			int xbis, ybis;
			
			while(it.hasNext()) {
				s = it.next();
				xbis = s.getBounds().x;
				ybis = s.getBounds().y;
				if(xbis > x) x = xbis;
				if(ybis > y) y = ybis;
			}
			
			return new Point(x, y);
		}
			
		return null;
	}
	 */
}
