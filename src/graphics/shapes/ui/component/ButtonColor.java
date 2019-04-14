package graphics.shapes.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonColor extends JButton {
	
	
	private Color current;

	private static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
	private static final int SPACE5 = 5;
	private static final int SPACE10 = 10;
	private static final int SPACE20 = 20;
	private static final int SPACE40 = 40;
	private static final int DEFAULTPOS = 0;

	
	public ButtonColor(Icon icon,ActionListener c) {
		super(icon);
		this.current = DEFAULT_COLOR;
		init(c);
	}
	
	private void init(ActionListener c) {
		this.setPreferredSize(new Dimension(SPACE40,this.getPreferredSize().height+SPACE20));
		this.addActionListener(c);
		this.cursor();
		
	}
	
	private void cursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.setCursor(toolkit.createCustomCursor(((ImageIcon)this.getIcon()).getImage(), new Point(DEFAULTPOS,DEFAULTPOS), "CURSOR"));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.clearRect(DEFAULTPOS, DEFAULTPOS,(int) this.getPreferredSize().getWidth(),(int) this.getPreferredSize().getHeight());
	    g2.setColor(Color.black);
	    g2.drawRect(DEFAULTPOS, DEFAULTPOS, getWidth(),getHeight());
	    
	    int centerX = this.getWidth()/2;
	    
	    int widthColorRectangle = this.getWidth()-(SPACE10); 
    	int posXColorRectangle = centerX-(widthColorRectangle/2);
    	int posYColorRectangle  = this.getHeight()-SPACE20;
    	int heightColorRectangle = this.getWidth()-SPACE20-SPACE10;
    	g2.setColor(current);
    	g2.fillRect(posXColorRectangle, posYColorRectangle, widthColorRectangle, heightColorRectangle );
	    
	    if (this.getIcon()!=null) {
	    	this.getIcon().paintIcon(this, g2, centerX-(this.getIcon().getIconWidth()/2), SPACE5);
	    }
	   
	    g2.dispose();
	}
	
	
	public void setColor(Color c) {
		this.current = c;
	}
	
	
}
