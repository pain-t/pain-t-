package graphics.shapes.attributes;

import java.util.ArrayList;

import graphics.ui.Observer;

public abstract class Attributes {

	private ArrayList<Observer> obs;

	public Attributes() {
		this.obs = new ArrayList<Observer>();
	}
	
	public void register(Observer o) {
		this.obs.add(o);
	}
	
	public void notifyObserver() {
		for(Observer o : this.obs) {
			o.update();
		}
	}
	public abstract String getId();
	
	
}
