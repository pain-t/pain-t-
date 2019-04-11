package graphics.shapes.ui;

import graphics.ui.Observer;

public class ShapesObserver extends Observer {

	public ShapesObserver(ShapesView v) {
		super(v);
	}

	@Override
	public void update() {
		this.getView().invalidate();
	}

}
