package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.HashMap;

import graphics.shapes.attributes.Attributes;
import graphics.ui.Observable;
import graphics.ui.Observer;

public abstract class Shape implements Observable{

	private Map<String,Attributes> attributes;

	protected final int handlerSize = 8;
	
	// --------------------------------------------------------------------
	
	public Shape() {
		attributes = new HashMap<String,Attributes>();
	}

	// --------------------------------------------------------------------
	
	public Attributes getAttributes(String id) {
		return this.attributes.get(id);
	}
	
	public void addAttributes(Attributes attr) {
		this.attributes.put(attr.getId(), attr);
	}
	
	public Map<String,Attributes> getAttributes(){
		return this.attributes;
	}
	
	public void register(Observer o) {
		observ.add(o);
		for(Attributes a : this.getAttributes().values()) {
			if(a != null) a.register(o);
		}
	}
	
	public abstract Point getLoc();
	public abstract void setLoc(Point p);
	public abstract void translate(int dx, int dy);
	public abstract Rectangle getBounds();
	public abstract void resize(Point cursor, int dx, int dy, int handler);
	public abstract void rotate();
	public abstract void accept(ShapeVisitor sv);
	@Override
	public abstract Shape clone();
	
	
	public Rectangle getTRhandler(Rectangle bounds) {
		return new Rectangle(bounds.x + bounds.width - this.handlerSize / 2, bounds.y - this.handlerSize / 2, this.handlerSize, this.handlerSize);
	}
	public Rectangle getTLhandler(Rectangle bounds) {
		return new Rectangle(bounds.x - this.handlerSize / 2, bounds.y - this.handlerSize / 2, this.handlerSize, this.handlerSize);
	}
	public Rectangle getBRhandler(Rectangle bounds) {
		return new Rectangle(bounds.x + bounds.width - this.handlerSize / 2, bounds.y + bounds.height - this.handlerSize / 2, this.handlerSize, this.handlerSize);
	}
	public Rectangle getBLhandler(Rectangle bounds) {
		return new Rectangle(bounds.x - this.handlerSize / 2, bounds.y + bounds.height - this.handlerSize / 2, this.handlerSize, this.handlerSize);
	}
}
