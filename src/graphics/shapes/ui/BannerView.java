package graphics.shapes.ui;

import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import graphics.shapes.ui.component.ButtonColor;
import graphics.ui.Controller;
import graphics.ui.View;

public class BannerView extends View {

	private ClassLoader classLoader;
	private ButtonColor btnc;
	private ButtonColor btnc2;
	

	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	
	
	public BannerView(Object model) {
		super(model);
		classLoader = Thread.currentThread().getContextClassLoader();
		try {
			this.setInterface();
		} catch (Exception e) {

		}
	}
	
	private void setInterface() throws Exception {
		
		this.p1 = new JPanel();
		this.p2 = new JPanel();
		this.p3 = new JPanel();
		
		this.add(this.p1); // selection pivot ...
		
		this.p1.add(new JButton("Selection drag"));
		this.p1.add(new JButton("Pivot"));
		
		this.add(this.p2); // creation de shape
		
		this.p2.add(new JButton("triangle"));
		this.p2.add(new JButton("rectangle"));
		this.p2.add(new JButton("circle"));
		
		this.add(this.p3); // modif color
		
		this.btnc = new ButtonColor(getIcon("fill.png"),((BannerController)this.getController()).doPrint());
		this.btnc2 = new ButtonColor(getIcon("bounds.jpg"),((BannerController)this.getController()).doPrint());
	
		this.p3.add(this.btnc);
		this.p3.add(this.btnc2);
		
		
	}
	
	@Override
	public Controller defaultController(Object model) {
		return new BannerController(model);
	}
	
	
	public ButtonColor getFillBtn() {
		return this.btnc;
	}

	public ButtonColor getStrokeBtn() {
		return this.btnc2;
	}
	
	private ImageIcon getIcon(String s) throws Exception {
		
		InputStream input = this.classLoader.getResourceAsStream(s);
		return new ImageIcon(ImageIO.read(input));
	}
}
