package graphics.ui;

import java.util.ArrayList;

public interface Observable {

	public ArrayList<Observer> observ = new ArrayList<Observer>();

	public default void notifyObserver() {
		observ.forEach(o -> o.update());
	}

	public default void register(Observer o) {
		observ.add(o);
	}

	public default void unregister() {
		observ.forEach(o -> observ.remove(o));
	}
}
