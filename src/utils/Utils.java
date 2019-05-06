package utils;

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
	public static final String DEFAULT_IMAGE = "unknow.jpg";
	
	
	
	public static ImageIcon getIcon(String s) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(s);
		ImageIcon i =null;

		try {
			 i= new ImageIcon(ImageIO.read(input));
		}
		catch(Exception e){
			try {
				i = new ImageIcon(ImageIO.read(classLoader.getResourceAsStream(DEFAULT_IMAGE)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return i;
	}
	
	
}
