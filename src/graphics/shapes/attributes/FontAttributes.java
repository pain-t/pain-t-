package graphics.shapes.attributes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class FontAttributes extends Attributes{

	public static final String ID = "FontAttributes";
	public static final Font DEFAULT_FONT = new Font("Arial", 0, 12);
	public static final Color DEFAULT_COLOR = Color.BLACK;

	private Font font;
	private Color fontColor;

	// --------------------------------------------------------------------

	public FontAttributes() {
		this(DEFAULT_FONT, DEFAULT_COLOR);
	}
	
	public FontAttributes(Font font, Color fontColor) {
		this.font = font;
		this.fontColor = fontColor;
	}

	// --------------------------------------------------------------------

	public Font font() {
		return this.font;
	}
	
	public Color fontColor() {
		return this.fontColor;
	}
	
	public Rectangle getBounds(String str) {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		FontMetrics fm = img.getGraphics().getFontMetrics(this.font);
		return new Rectangle(0, 0, fm.stringWidth(str), font.getSize());
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Attributes clone() {
		return new FontAttributes(this.font, this.fontColor);
	}
}
