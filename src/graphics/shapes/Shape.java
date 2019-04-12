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

	public Shape() {
		attributes = new HashMap<String,Attributes>();
	}

	public void addAttributes(Attributes attr) {
		attributes.put(attr.getId(), attr);
	}

	public Attributes getAttributes(String id) {
		return this.attributes.get(id);
	}
	
	public Map<String,Attributes> getAttributes(){
		return this.attributes;
	}
	
	public void register(Observer o) {
		observ.add(o);
		for(Attributes a : this.getAttributes().values()) {
			if(a!=null)a.register(o);
		}
	}
	
	public abstract Point getLoc();
	public abstract void setLoc(Point p);
	public abstract void translate(int dx, int dy);
	public abstract Rectangle getBounds();
	public abstract void accept(ShapeVisitor sv);
}
