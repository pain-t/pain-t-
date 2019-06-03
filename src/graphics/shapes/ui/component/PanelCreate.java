package graphics.shapes.ui.component;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

import graphics.shapes.ui.BannerController;
import utils.Utils;

public class PanelCreate extends JPanel {
	
	private final static int GRIDROW = 2;
	private final static int GRIDCOLUMN = 2;
	private final static Color WHITE = Color.white;
	private final static Color BORDERCOLOR = Color.black;
	private final static int PADDING = 5;
	private final static int GAP = 5;
	private JPopupMenu jPopupText;
	
	
	public PanelCreate(BannerController controller) {
		super();
		init(controller);
	}
	
	private void init(BannerController controller) {
		this.jPopupText = new JPopupMenu();
		this.jPopupText.add(new TextEntry(controller));
		
		GridLayout grid = new GridLayout(GRIDROW,GRIDCOLUMN);
		grid.setHgap(GAP);
		grid.setVgap(GAP);
		this.setLayout(grid);
		this.setBackground(WHITE);
		this.setBorder(new CompoundBorder(BorderFactory.createLineBorder(BORDERCOLOR), BorderFactory.createEmptyBorder(PADDING,PADDING,PADDING,PADDING)));
		this.add(new ButtonShape(Utils.getIcon(Utils.RECTANGLE),controller.createRectangle()));
		this.add(new ButtonShape(Utils.getIcon(Utils.CIRCLE),controller.createCircle()));
		this.add(new ButtonShape(Utils.getIcon(Utils.TEXT),controller.createText()));
		this.add(new ButtonShape(Utils.getIcon(Utils.LINE),controller.createLine()));
		this.add(new ButtonShape(Utils.getIcon(Utils.DEFAULT_IMAGE),controller.createCollection()));
		this.add(new ButtonShape(Utils.getIcon(Utils.DEFAULT_IMAGE),controller.doPrint()));
		this.add(new ButtonShape(Utils.getIcon(Utils.DEFAULT_IMAGE),controller.doPrint()));
		this.add(new ButtonShape(Utils.getIcon(Utils.DEFAULT_IMAGE),controller.doPrint()));
	}
}
