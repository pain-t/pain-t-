package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import graphics.shapes.ui.component.ButtonColor;
import graphics.shapes.ui.component.ButtonShape;
import graphics.shapes.ui.component.ColorChooser;
import graphics.ui.Controller;
import graphics.ui.View;
import utils.Utils;

public class BannerView extends View {


	private ButtonColor btnc;
	private ButtonColor btnc2;
	private JPanel panelSelect;
	private JPanel panelCreate;
	private JPanel panelColor;
	private JPopupMenu jpopupFill;
	private JPopupMenu jpopupStroke;
	
	private final static int GRIDROW = 2;
	private final static int GRIDCOLUMN = 2;

	
	public BannerView(Object model) {
		super(model);
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
		jpopupFill = new JPopupMenu("Fill Color");
		jpopupStroke = new JPopupMenu("StrokeColor");
		jpopupFill.add(new ColorChooser((BannerController)getController()));
		jpopupStroke.add(new ColorChooser((BannerController)getController()));
		ButtonColor b =new ButtonColor(Utils.getIcon("fill.png"),((BannerController)this.getController()).doPrint());
		ButtonColor b2 =new ButtonColor(Utils.getIcon("bounds.jpg"),((BannerController)this.getController()).doPrint());
		this.btnc =b;
		this.btnc2 = b2;
		this.btnc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jpopupFill.show((Component) e.getSource(), 0, -100);
			}
		});
		this.btnc2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jpopupStroke.show((Component) e.getSource(), 0, -100);
			}
		});
		this.panelColor.add(this.btnc);
		this.panelColor.add(this.btnc2);
	}

	private void initPanelSelect(){
		this.panelSelect = new JPanel();
		this.panelSelect.add(new ButtonShape(Utils.getIcon("mouse.png"),((BannerController)this.getController()).doPrint()));
		this.panelSelect.add(new ButtonShape(Utils.getIcon("selection.png"),((BannerController)this.getController()).doPrint()));
	}
	
	private void initPanelCreate(){
		GridLayout grid = new GridLayout(GRIDROW,GRIDCOLUMN);
		final int gap = 5;
		grid.setHgap(gap);
		grid.setVgap(gap);
		this.panelCreate = new JPanel(grid);
		this.panelCreate.setBackground(Color.white);
		this.panelCreate.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(5,5,5,5)));
		this.panelCreate.add(new ButtonShape(Utils.getIcon("rectangle.png"),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(Utils.getIcon("circle.png"),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(Utils.getIcon("text.png"),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(Utils.getIcon("line.png"),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(Utils.getIcon(""),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(Utils.getIcon(""),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(Utils.getIcon(""),((BannerController)this.getController()).doPrint()));
		this.panelCreate.add(new ButtonShape(Utils.getIcon(""),((BannerController)this.getController()).doPrint()));

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
	
	public JPopupMenu getJpopupFill() {
		return jpopupFill;
	}

	public JPopupMenu getJpopupStroke() {
		return jpopupStroke;
	}

}
