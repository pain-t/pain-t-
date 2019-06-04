package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import graphics.shapes.attributes.Attributes;

public class SCollection extends Shape {

	/** List of shapes. */
	private ArrayList<Shape> shapes;

	public static final String ID = "Collection";

	// private Point loc;
	// private int height;
	// private int width;

	// --------------------------------------------------------------------

	/**
	 * Constructs an empty collection of shapes.
	 */
	public SCollection() {
		super();
		this.shapes = new ArrayList<Shape>();
		// this.loc = new Point();
	}

	// --------------------------------------------------------------------

	/**
	 * Returns the list of shapes.
	 * 
	 * @return The list of shapes.
	 */
	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	/**
	 * Returns the shape at the specified position in the list of shapes.
	 * 
	 * @param i
	 *            The position of the wanted shape.
	 * @return The shape at the specified position in the list of shapes.
	 */
	public Shape getShape(int i) {
		Shape s = null;
		if (i > -1 && i < this.size()) {
			s = this.shapes.get(i);
		}

		return s;
	}

	/**
	 * Returns a list iterator over the elements in the list of shapes.
	 * 
	 * @return A list iterator over the elements in the list of shapes.
	 */
	public ListIterator<Shape> iterator() {
		return this.shapes.listIterator();
	}

	/**
	 * Returns a list iterator over the elements in the list of shapes, starting at
	 * the specified index. An initial call to previous would return the element
	 * with the specified index minus one.
	 * 
	 * @param index
	 *            Theindex of the first element to be returned from the lit
	 *            iterator.
	 * @return A list iterator over the elements in the list of shapes, starting at
	 *         the specified index.
	 */
	public ListIterator<Shape> iterator(int index) {
		return this.shapes.listIterator(index);
	}

	/**
	 * Appends the specified shape to the end of the list of shapes.
	 * 
	 * @param s
	 *            Shape to be appened to the end of the list.
	 */
	public void add(Shape s) {
		this.shapes.add(s);
		this.notifyObserver();
	}

	/**
	 * Removes all the elements from the list of shapes.
	 */
	public void remove(Shape s) {
		this.shapes.remove(s);
		this.notifyObserver();
	}

	public void empty() {
		this.shapes.clear();
		this.notifyObserver();
	}

	/**
	 * Puts the specified shape closer (in the UI) by setting it farther in the list
	 * of shapes.
	 * 
	 * @param s
	 *            Shape to get closer (on the UI).
	 */
	public void closer(Shape s) {
		if (s != null) {
			int i = this.shapes.indexOf(s);
			if (i < this.shapes.size() - 1) {
				this.shapes.remove(i);
				this.shapes.add(i + 1, s);
			}
		}

		this.notifyObserver();
	}

	/**
	 * Puts the specified shape farther (in the UI) by setting it closer in the list
	 * of shapes.
	 * 
	 * @param s
	 *            Shape to get farther (on the UI).
	 */
	public void farther(Shape s) {
		if (s != null) {
			int i = this.shapes.indexOf(s);
			if (i > 0) {
				this.shapes.remove(i);
				this.shapes.add(i - 1, s);
			}
		}

		this.notifyObserver();
	}

	/**
	 * Puts the specified shape on top of all shapes (in the UI) by setting it in
	 * the lat position in the list of shapes.
	 * 
	 * @param s
	 *            Shape to get on top of all shapes (in the UI).
	 */
	public void first(Shape s) {
		if (s != null) {
			int i = this.shapes.indexOf(s);
			if (i < this.shapes.size() - 1) {
				this.shapes.remove(i);
				this.shapes.add(this.shapes.size(), s);
			}
		}

		this.notifyObserver();
	}

	/**
	 * Returns the number of shapes in the list of shapes.
	 * 
	 * @return The number of shapes in the list of shapes.
	 */
	public int size() {
		return shapes.size();
	}

	@Override
	public void translate(int dx, int dy) {
		for (Shape s : this.shapes) {
			s.translate(dx, dy);
		}
		this.notifyObserver();
	}

	@Override
	public void rotate() {
		for (Shape s : shapes) {
			s.rotate();
		}

		this.notifyObserver();
	}

	@Override
	public void resize(Point cursor, int dx, int dy, int handler) {
		Rectangle bounds = getBounds();
		for (Shape s : this.shapes) {
			Rectangle b = s.getBounds();
			Point c = (Point) cursor.clone();

			if (handler == 0) // top left corner
				c.translate(b.x - bounds.x, b.y - bounds.y);
			else if (handler == 1) // top right corner
				c.translate(b.x + b.width - bounds.x - bounds.width, b.y - bounds.y);
			else if (handler == 2) // bottom left corner
				c.translate(b.x - bounds.x, b.y + b.height - bounds.y - bounds.height);
			else // bottom right corner
				c.translate(b.x + b.width - bounds.x - bounds.width, b.y + b.height - bounds.y - bounds.height);

			s.resize(c, dx, dy, handler);
		}

		this.notifyObserver();
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitCollection(this);
	}

	@Override
	public Shape clone() {
		SCollection sc = new SCollection();

		for (Shape s : this.shapes)
			sc.add(s.clone());

		for (Attributes a : this.getAttributes().values())
			sc.addAttributes(a.clone());

		return sc;
	}

	@Override
	public Rectangle getBounds() {
		Iterator<Shape> it = this.iterator();

		if (it.hasNext()) {
			Rectangle r = it.next().getBounds();
			int tlx = r.x; // top left corner
			int tly = r.y;
			int brx = r.width + tlx; // bottom right corner
			int bry = r.height + tly;

			int tlxbis, tlybis, brxbis, brybis;

			while (it.hasNext()) {
				r = it.next().getBounds();
				tlxbis = r.x;
				tlybis = r.y;
				brxbis = r.width + tlxbis;
				brybis = r.height + tlybis;
				if (tlxbis < tlx)
					tlx = tlxbis;
				if (tlybis < tly)
					tly = tlybis;
				if (brxbis > brx)
					brx = brxbis;
				if (brybis > bry)
					bry = brybis;
			}

			return new Rectangle(tlx, tly, brx - tlx, bry - tly);
		}

		return null;
	}

	@Override
	public Point getLoc() {
		Iterator<Shape> it = this.iterator();

		if (it.hasNext()) {
			Shape s = it.next();
			int x = s.getBounds().x; // top left corner
			int y = s.getBounds().y;

			int xbis, ybis;

			while (it.hasNext()) {
				s = it.next();
				xbis = s.getBounds().x;
				ybis = s.getBounds().y;
				if (xbis > x)
					x = xbis;
				if (ybis > y)
					y = ybis;
			}

			return new Point(x, y);
		}

		return null;
	}

	@Override
	public void setLoc(Point p) {
		Point loc = getLoc();
		for (Shape s : shapes) {
			Point locShape = s.getLoc();
			s.setLoc(p);
			s.translate(locShape.x - loc.x, locShape.y - loc.y);
		}
	}

	@Override
	public String getId() {
		return ID;
	}
}
