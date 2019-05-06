package graphics.shapes.ui.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonShape extends JButton {
	
	private static final int DEFAULTPOS = 0;
	private static final String CURS_NAME = "CURSOR";

	
	public ButtonShape(Icon icon,ActionListener action){
		super(icon);
		init(action);
	}
	
	private void init(ActionListener action) {
		this.addActionListener(action);
		this.setPreferredSize(new Dimension(this.getIcon().getIconWidth(),this.getIcon().getIconHeight()));
		this.cursor();
	}
	
	private void cursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.setCursor(toolkit.createCustomCursor(((ImageIcon)this.getIcon()).getImage(), new Point(DEFAULTPOS,DEFAULTPOS), CURS_NAME));
	}
}
