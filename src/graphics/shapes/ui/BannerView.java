package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import graphics.shapes.ui.component.ButtonColor;
import graphics.shapes.ui.component.ButtonShape;
import graphics.shapes.ui.component.PanelCreate;
import graphics.shapes.ui.component.PanelColor;
import graphics.ui.Controller;
import graphics.ui.View;
import utils.Utils;

public class BannerView extends View {


	private JPanel panelSelect;
	private JPanel panelCreate;
	private JPanel panelColor;
	
	private static final Dimension JSEP_DIM = new Dimension(3,50);
	private static final Color JSEP_COLOR = Color.black;
	
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
        js.setPreferredSize(JSEP_DIM);
        js.setForeground(JSEP_COLOR);
        return js;
	}
	
	private void initPanelColor(){
		this.panelColor = new PanelColor((BannerController)getController());
	}

	private void initPanelSelect(){
		this.panelSelect = new JPanel();
		this.panelSelect.add(new ButtonShape(Utils.getIcon("mouse.png"),((BannerController)this.getController()).doPrint()));
		this.panelSelect.add(new ButtonShape(Utils.getIcon("selection.png"),((BannerController)this.getController()).doPrint()));
	}
	
	private void initPanelCreate(){
		this.panelCreate = new PanelCreate((BannerController)getController());
	}
	
	@Override
	public Controller defaultController(Object model) {
		return new BannerController(model);
	}
	
	
	public ButtonColor getFillBtn() {
		return ((PanelColor)this.panelColor).getBtnc();
	}

	public ButtonColor getStrokeBtn() {
		return ((PanelColor)this.panelColor).getBtnc2();
	}
	
	public JPopupMenu getJPopupFill() {
		return ((PanelColor)this.panelColor).getJpopupFill();
	}

	public JPopupMenu getJPopupStroke() {
		return ((PanelColor)this.panelColor).getJpopupStroke();
	}

}
