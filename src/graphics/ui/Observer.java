package graphics.ui;

public abstract class Observer {

	private View v;

	public Observer(View v) {
		this.v = v;
	}

	protected View getView() {
		return this.v;
	}

	public abstract void update();

}
