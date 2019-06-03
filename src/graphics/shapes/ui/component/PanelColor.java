package graphics.shapes.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import graphics.shapes.ui.BannerController;
import utils.Utils;

public class PanelColor extends JPanel {
	
	/**The popup when we clicked on the fill button.*/
	private JPopupMenu jpopupFill;
	/**the popup when we clicked on the stroke btn.*/
	private JPopupMenu jpopupStroke;
	/**tThe fill button.*/
	private ButtonColor btnc;
	/**The stroke button.*/
	private ButtonColor btnc2;
	private JCheckBox fillBox;
	private JCheckBox strokeBox;
	/**The name displayed on the fill checkbox.*/
	private static final String DISPLAYEDCHECKBOXFILL = "Fill";
	/**The name displayed on the stroke checkbox.*/
	private static final String DISPLAYEDCHECKBOXSTROKE = "Stroke";
	
	/**
	 * Creates the panel with the color modification.
	 * @param contr The banner controller which have the function when we clicked on the button. 
	 */
	public PanelColor(BannerController controller) {
		super();
		init(controller);
		
	}
	
	/**
	 * Initializes the panel.
	 * @param contr The banner controller which have the function when we clicked on the button. 
	 */
	private void init(BannerController contr) {
		
		//The color chooser is displayed in a JPopupMenu.
		this.jpopupFill = new JPopupMenu();
		this.jpopupFill.add(new ColorChooser(contr));
		this.btnc = new ButtonColor(Utils.getIcon(Utils.FILL_IMG),contr.doPrint());
		this.btnc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jpopupFill.show((Component) e.getSource(), 0, 0);
			}
		});
		
		this.jpopupStroke = new JPopupMenu();
		this.jpopupStroke.add(new ColorChooser(contr));
		this.btnc2 = new ButtonColor(Utils.getIcon(Utils.BOUNDS_IMG),contr.doPrint());
		this.btnc2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jpopupStroke.show((Component) e.getSource(), 0, 0);
				
			}
		});
		this.fillBox = new JCheckBox(DISPLAYEDCHECKBOXFILL);
		this.strokeBox = new JCheckBox(DISPLAYEDCHECKBOXSTROKE);
		this.fillBox.setSelected(true);
		this.strokeBox.setSelected(true);
		this.fillBox.addActionListener(contr.toggleBox());
		this.strokeBox.addActionListener(contr.toggleBox());
		this.add(this.fillBox);
		this.add(this.btnc);
		this.add(this.strokeBox);
		this.add(this.btnc2);
		
	}
	/**
	 * Returns the popup with the fill color chooser. 
	 * @return the popup with the fill color chooser.
	 */
	public JPopupMenu getJpopupFill() {
		return jpopupFill;
	}
	/**
	 * Returns the popup with the stroke color chooser.
	 * @return the popup with the stroke color chooser.
	 */
	public JPopupMenu getJpopupStroke() {
		return jpopupStroke;
	}
	/**
	 * Returns the fill color button.
	 * @return the fill color button.
	 */
	public ButtonColor getBtnc() {
		return btnc;
	}
	/**
	 * Returns the stroke color button.
	 * @return the stroke color button.
	 */
	public ButtonColor getBtnc2() {
		return btnc2;
	}
	
	
	/**
	 * Returns the filled color.
	 * @return the filled color.
	 */
	public Color getFilledColor() {
		return ((ColorChooser)this.jpopupFill.getComponent(0)).getColor();
	}
	
	/**
	 * Returns the stroked color.
	 * @return the stroked color.
	 */
	public Color getStrokedColor () {
		return ((ColorChooser)this.jpopupStroke.getComponent(0)).getColor();
	}
	
	/**
	 * Returns the value of the fillbox.
	 * @return the value of the fillbox.
	 */
	public boolean getFillBox () {
		return this.fillBox.isSelected();
	}
	
	/**
	 * Returns the value of the strokebox.
	 * @return the value of the strokebox.
	 */
	public boolean getStrokeBox () {
		return this.strokeBox.isSelected();
	}
	
	public void setStrokeBox(boolean b) {
		this.strokeBox.setSelected(b);
	}
	
	public void setFilledBox(boolean b) {
		this.fillBox.setSelected(b);
	}
}
