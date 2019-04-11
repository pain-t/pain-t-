package graphics.shapes.ui;

import graphics.ui.Observer;

public class BannerObserver extends Observer {

	public BannerObserver(BannerView v) {
		super(v);
	}

	@Override
	public void update() {
		this.getView().invalidate();
	}

}
