package graphics.shapes.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import graphics.ui.Controller;
import graphics.ui.View;

public class BannerView extends View {

	public BannerView(Object model) {
		super(model);
		this.setInterface();
	}
	
	private void setInterface() {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		this.add(p1); // selection pivot ...
		this.add(p2); // creation de shape
		this.add(p3); // modif color
		p1.add(new JButton("Selection click"));
		p1.add(new JButton("Selection drag"));
		p1.add(new JButton("Pivot"));
		p2.add(new JButton("triangle"));
		p2.add(new JButton("rectangle"));
		p2.add(new JButton("circle"));
		p3.add(new JButton("Color"));
	}
	
	@Override
	public Controller defaultController(Object model) {
		return new BannerController(model);
	}
	
}
