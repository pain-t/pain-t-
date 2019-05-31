package graphics.shapes.attributes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class FontAttributes extends Attributes{
	
	/** Id of the FontAttributes. */
	public static final String ID = "FontAttributes";
	/** Default font. */
	public static final Font DEFAULT_FONT = new Font("Arial", 0, 12);
	/** Default color font. */
	public static final Color DEFAULT_COLOR = Color.BLACK;

	/** Font. */
	private Font font;
	/** Color font. */
	private Color fontColor;

	// --------------------------------------------------------------------

	/**
	 * Constructs a default FontAttribute.
	 */
	public FontAttributes() {
		this(DEFAULT_FONT, DEFAULT_COLOR);
	}
	
	/**
	 * Constructs a FontAttribute.
	 * @param font Font.
	 * @param fontColor Font color.
	 */
	public FontAttributes(Font font, Color fontColor) {
		this.font = font;
		this.fontColor = fontColor;
	}

	// --------------------------------------------------------------------

	/**
	 * Returns the font.
	 * @return The font.
	 */
	public Font font() {
		return this.font;
	}
	
	/**
	 * Return the color font.
	 * @return The color font.
	 */
	public Color fontColor() {
		return this.fontColor;
	}
	
	/**
	 * Return the bounds with the specified text with this font configuration.
	 * @param str Text with which to compute the bounds.
	 * @return The bounds with the specified text with this font configuration.
	 */
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

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
		this.notifyObserver();
	}
	
	public void setFont(Font font) {
		this.font = font;
		this.notifyObserver();
	}
}
