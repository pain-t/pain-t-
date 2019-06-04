package graphics.shapes.attributes;

import graphics.shapes.ui.ShapesView;
import graphics.ui.Observer;

public class AttributesObserver extends Observer {

	public AttributesObserver(ShapesView v) {
		super(v);
	}

	@Override
	public void update() {
		this.getView().invalidate();
	}

	protected ShapesView getView() {
		return (ShapesView) this.getView();
	}

}
