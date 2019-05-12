package utils;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Utils {
	
	public final static String RECTANGLE = "rectangle.png";
	public final static String CIRCLE = "circle.png";
	public final static String TEXT = "text.png";
	public final static String LINE = "line.png";
	public final static String FILL_IMG = "fill.png";
	public final static String BOUNDS_IMG = "bounds.jpg";
	public final static String MOUSE = "mouse.png";
	public final static String SELECTION = "selection.png";
	public static final String DEFAULT_IMAGE = "unknown.jpg";
	
	
	
	public static ImageIcon getIcon(String s) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(s);
		ImageIcon i = null;

		try {
			 i = new ImageIcon(ImageIO.read(input));
		}
		catch(Exception e){
			try {
				input = classLoader.getResourceAsStream(DEFAULT_IMAGE);
				i = new ImageIcon(ImageIO.read(input));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return i;
	}
	
	public static Rectangle getAbsoluteBounds(Rectangle rect) {
		if(rect == null) return null;
				
		Rectangle r = (Rectangle) rect.clone();

		int width = r.width;
		int height = r.height;
		int x = r.x;
		int y = r.y;
		if(width < 0) {
			width *= -1;
			x -= width;
		}
		if(height < 0) {
			height *= -1;
			y -= height;
		}
		r.setBounds(x, y, width, height);
		return r;
	}
}
