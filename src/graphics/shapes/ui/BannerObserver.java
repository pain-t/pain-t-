package graphics.shapes.ui;

import graphics.ui.Observer;

public class BannerObserver extends Observer {
	/**
	 * Creates an observer for the banner model.
	 * @param v The banner view.
	 */
	public BannerObserver(BannerView v) {
		super(v);
	}

	@Override
	public void update() {
		this.getView().invalidate();
	}

}
