package graphics.shapes.ui.component;

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

	private JPopupMenu jpopupFill;
	private JPopupMenu jpopupStroke;
	private ButtonColor btnc;
	private ButtonColor btnc2;
	
	public PanelColor(BannerController controller) {
		super();
		init(controller);
	}
	
	private void init(BannerController contr) {
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
		JCheckBox fillBox = new JCheckBox("Fill");
		JCheckBox strokeBox = new JCheckBox("Stroke");
		this.add(fillBox);
		this.add(this.btnc);
		this.add(strokeBox);
		this.add(this.btnc2);
	}

	public JPopupMenu getJpopupFill() {
		return jpopupFill;
	}

	public JPopupMenu getJpopupStroke() {
		return jpopupStroke;
	}

	public ButtonColor getBtnc() {
		return btnc;
	}

	public ButtonColor getBtnc2() {
		return btnc2;
	}
	
}
