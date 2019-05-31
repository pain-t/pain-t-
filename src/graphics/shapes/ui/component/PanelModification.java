package graphics.shapes.ui.component;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class PanelModification extends JPanel{
	
	private JPanel main;
	private JPanel text;
	private JPanel rectangle;
	private JPanel scoll;

	
	public PanelModification() {
		this.main = new JPanel();
		this.setInterface();
		this.setVisible(true);
	}

	private void setInterface() {
		// get all font
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] fonts = e.getAllFonts(); 
		JComboBox<Font> police = new JComboBox<Font>();
		police.setVisible(true);
		for(Font f: fonts) {
			police.addItem(f);
		}
		this.main.add(police);
	} 
	
}