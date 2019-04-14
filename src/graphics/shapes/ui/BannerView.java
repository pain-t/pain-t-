package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import graphics.shapes.ui.component.ButtonColor;
import graphics.shapes.ui.component.ButtonShape;
import graphics.ui.Controller;
import graphics.ui.View;

public class BannerView extends View {

	private ClassLoader classLoader;

	private ButtonColor btnc;
	private ButtonColor btnc2;
	private JPanel panelSelect;
	private JPanel panelCreate;
	private JPanel panelColor;
	
	private final static int GRIDROW = 2;
	private final static int GRIDCOLUMN = 2;

	
	public BannerView(Object model) {
		super(model);
		this.classLoader = Thread.currentThread().getContextClassLoader();
		try {
			this.setInterface();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setInterface(){
		
		this.initPanelSelect();
		this.initPanelCreate();
		this.initPanelColor();
		this.add(this.panelSelect); 
        this.add(getSeparator());
		this.add(this.panelCreate); 
        this.add(getSeparator());
		this.add(this.panelColor); 
		
	}
	
	private JSeparator getSeparator() {
		JSeparator js = new JSeparator(SwingConstants.VERTICAL);
        js.setPreferredSize(new Dimension(3,50));
        js.setForeground(Color.black);
        return js;
	}
	
	private void initPanelColor(){
		this.panelColor = new JPanel();
		this.btnc = new ButtonColor(getIcon("fill.png"),((BannerController)this.getController()).doPrint());
		this.btnc2 = new ButtonColor(getIcon("bounds.jpg"),((BannerController)this.getController()).doPrint());
		this.panelColor.add(this.btnc);
		this.panelColor.add(this.btnc2);
	}

	private void initPanelSelect(){
		this.panelSelect = new JPanel();
		this.panelSelect.add(new ButtonShape(getIcon("mouse.png"),((BannerController)this.getController()).doPrint()));
		this.panelSelect.add(new ButtonShape(getIcon("selection.png"),((BannerController)this.getController()).doPrint()));
	}
	
	private void initPanelCreate(){
		GridLayout grid = new GridLayout(GRIDROW,GRIDCOLUMN);
		final int gap = 5;
		grid.setHgap(gap);
		grid.setVgap(gap);
		this.panelCreate = new JPanel(grid);
		this.panelCreate.setBackground(Color.white);
		this.panelCreate.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5,5,5,5)));
		this.panelCreate.add(new ButtonShape(getIcon("rectangle.png"),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(getIcon("circle.png"),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(getIcon("text.png"),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(getIcon("line.png"),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(getIcon(""),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(getIcon(""),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(getIcon(""),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(getIcon(""),((BannerController)this.getController()).doPrint()));

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
	
	private ImageIcon getIcon(String s) {
		InputStream input = this.classLoader.getResourceAsStream(s);
		ImageIcon i =null;

		try {
			 i= new ImageIcon(ImageIO.read(input));
		}
		catch(Exception e){
			try {
				i = new ImageIcon(ImageIO.read(this.classLoader.getResourceAsStream("unknow.jpg")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return i;
	}
	
	
}
