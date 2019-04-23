package utils;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Utils {

	public static ImageIcon getIcon(String s) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(s);
		ImageIcon i =null;

		try {
			 i= new ImageIcon(ImageIO.read(input));
		}
		catch(Exception e){
			try {
				i = new ImageIcon(ImageIO.read(classLoader.getResourceAsStream("unknow.jpg")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return i;
	}
	
	
}
