package graphics.shapes;

import java.awt.Point;
import java.awt.Rectangle;

import graphics.shapes.attributes.Attributes;
import graphics.shapes.attributes.FontAttributes;

public class SText extends Shape {
	/** Content of the text. */
	private String text;
	/** Location of the text. */
	private Point loc;
	
	public static final String ID = "Text";

	
	// --------------------------------------------------------------------
	
	/**
	 * Constructs the text with the specified location and text. 
	 * @param loc Location of the text.
	 * @param text Content of the text.
	 */
	public SText(Point loc, String text) {
		super();
		this.text = text;
		this.loc = loc;
	}

	// --------------------------------------------------------------------
	
	/**
	 * Returns the content of the text.
	 * @return
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Set the content of the text with the given string.
	 * @param text New content of the text.
	 */
	public void setText(String text) {
		this.text = text;
		this.notifyObserver();
	}
	
	@Override
	public Point getLoc() {
		return this.loc;
	}

	@Override
	public void setLoc(Point p) {
		this.loc = p;
		this.notifyObserver();
	}

	@Override
	public void translate(int dx, int dy) {
		this.loc.translate(dx, dy);
		this.notifyObserver();
	}
	
	@Override
	public void resize(Point cursor, int dx, int dy, int handler) {}
	
	@Override
	public void rotate() {}
	
	@Override
	public Rectangle getBounds() {
		FontAttributes fa = (FontAttributes) this.getAttributes(FontAttributes.ID);
		return new Rectangle(this.loc.x,this.loc.y,fa.getBounds(this.text).width,fa.getBounds(this.text).height);
	}

	@Override
	public void accept(ShapeVisitor sv) {
		sv.visitText(this);
	}

	@Override
	public Shape clone() {
		SText st = new SText((Point) this.loc.clone(), this.text);
		
		for(Attributes a : this.getAttributes().values())
			st.addAttributes(a.clone());
		
		return st;
	}

	@Override
	public String getId() {
		return ID;
	}
}
