package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import graphics.shapes.attributes.Attributes;
import graphics.ui.Observer;

public abstract class Shape {

	private Map<String,Attributes> attributes;
	private ArrayList<Observer> obs;

	public Shape() {
		attributes = new HashMap<String,Attributes>();
		this.obs = new ArrayList<Observer>();
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
		this.obs.add(o);
		for(Attributes a : this.getAttributes().values()) {
			if(a!=null)a.register(o);
		}
	}
	
	public void notifyObserver() {
		for(Observer o : this.obs) {
			o.update();
		}
	}
	public abstract Point getLoc();
	public abstract void setLoc(Point p);
	public abstract void translate(int dx, int dy);
	public abstract Rectangle getBounds();
	public abstract void accept(ShapeVisitor sv);
}
