package graphics.shapes.ui.component;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import graphics.shapes.ui.BannerController;
import utils.Utils;

public class PanelCreate extends JPanel {

	/**Number of row in the gridlayout.*/
	private final static int GRIDROW = 2;
	/**Number of columns in the gridlayout.*/
	private final static int GRIDCOLUMN = 2;
	/**The color of the background.*/
	private final static Color WHITE = Color.white;
	/**The color of the border.*/
	private final static Color BORDERCOLOR = Color.black;
	/**The size of the padding for buttonshapes.*/
	private final static int PADDING = 5;
	/**The spaces between each buttonsshapes.*/
	private final static int GAP = 5;
	
	/**
	 * Creates the panel which can creates the shapes.
	 * @param contr The banner controller which have the function to create shapes. 
	 */
	public PanelCreate(BannerController controller) {
		super();
		init(controller);
	}
	/**
	 * Initializes the shape creator panel.
	 * @param contr The banner controller which have the function to create shapes. 
	 */
	private void init(BannerController controller) {
		GridLayout grid = new GridLayout(GRIDROW,GRIDCOLUMN);
		grid.setHgap(GAP);
		grid.setVgap(GAP);
		this.setLayout(grid);
		this.setBackground(WHITE);
		this.setBorder(new CompoundBorder(BorderFactory.createLineBorder(BORDERCOLOR), BorderFactory.createEmptyBorder(PADDING,PADDING,PADDING,PADDING)));
		this.add(new ButtonShape(Utils.getIcon(Utils.RECTANGLE),controller.createRectangle()));
		this.add(new ButtonShape(Utils.getIcon(Utils.CIRCLE),controller.createCircle()));
		this.add(new ButtonShape(Utils.getIcon(Utils.TEXT),controller.doPrint()));
		this.add(new ButtonShape(Utils.getIcon(Utils.LINE),controller.doPrint()));
		this.add(new ButtonShape(Utils.getIcon(Utils.DEFAULT_IMAGE),controller.doPrint()));
		this.add(new ButtonShape(Utils.getIcon(Utils.DEFAULT_IMAGE),controller.doPrint()));
		this.add(new ButtonShape(Utils.getIcon(Utils.DEFAULT_IMAGE),controller.doPrint()));
		this.add(new ButtonShape(Utils.getIcon(Utils.DEFAULT_IMAGE),controller.doPrint()));
	}
}
