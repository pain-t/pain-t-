package graphics.shapes.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import graphics.shapes.SOval;
import graphics.shapes.SCollection;
import graphics.shapes.SLine;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.ShapeVisitor;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;

public class ShapeDraftman implements ShapeVisitor {
	/** Default colors. */
	public static final ColorAttributes DEFAULTCOLORATTRIBUTES = new ColorAttributes();
	/** Default font. */
	public static final FontAttributes DEFAULTFONTATTRIBUTES = new FontAttributes();
	/** Object allowing us to draw on the canvas. */
	private Graphics2D g2d;

	// --------------------------------------------------------------------
	/**
	 * Constructs a ShapeDraftman object with a specified Graphics object.
	 * 
	 * @param g
	 *            The specified Graphics object (allowing us to draw on the canvas).
	 */
	public ShapeDraftman(Graphics g) {
		this.g2d = (Graphics2D) g;
	}

	// --------------------------------------------------------------------

	/**
	 * Draws the specified SRectangle according to its attributes.
	 * 
	 * @param rect
	 *            The specified SRectangle.
	 */
	@Override
	public void visitRectangle(SRectangle rect) {
		Rectangle r = rect.getBounds();
		ColorAttributes fc = (ColorAttributes) rect.getAttributes(ColorAttributes.ID);
		if (fc == null)
			fc = DEFAULTCOLORATTRIBUTES;

		if (fc.filled()) {
			g2d.setColor(fc.filledColor());
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}

		if (fc.stroked()) {
			g2d.setColor(fc.strokedColor());
			g2d.drawRect(r.x, r.y, r.width, r.height);
		}

		SelectionAttributes sa = (SelectionAttributes) (rect.getAttributes(SelectionAttributes.ID));
		if (sa.isSelected())
			this.drawSelection(rect);
	}

	/**
	 * Draws the specified SOval according to its attributes.
	 * 
	 * @param rect
	 *            The specified SOval.
	 */
	@Override
	public void visitOval(SOval oval) {
		Rectangle r = oval.getBounds();
		ColorAttributes fc = (ColorAttributes) oval.getAttributes(ColorAttributes.ID);

		if (fc == null)
			fc = DEFAULTCOLORATTRIBUTES;
		if (fc.filled()) {
			this.g2d.setColor(fc.filledColor());
			this.g2d.fillOval(r.x, r.y, r.width, r.height);
		}

		if (fc.stroked()) {
			this.g2d.setColor(fc.strokedColor());
			this.g2d.drawOval(r.x, r.y, r.width, r.height);
		}

		SelectionAttributes sa = (SelectionAttributes) oval.getAttributes(SelectionAttributes.ID);
		if (sa.isSelected())
			this.drawSelection(oval);
	}

	/**
	 * Draws the specified SText according to its attributes.
	 * 
	 * @param rect
	 *            The specified SText.
	 */
	@Override
	public void visitText(SText text) {
		Rectangle r = text.getBounds();
		ColorAttributes fc = (ColorAttributes) text.getAttributes(ColorAttributes.ID);

		if (fc == null)
			fc = DEFAULTCOLORATTRIBUTES;

		FontAttributes fa = (FontAttributes) text.getAttributes(FontAttributes.ID);
		if (fa == null)
			fa = DEFAULTFONTATTRIBUTES;

		if (fc.filled()) {
			g2d.setColor(fc.filledColor());
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}
		if (fc.stroked()) {
			this.g2d.setColor(fc.strokedColor());
			this.g2d.drawRect(r.x, r.y, r.width, r.height);
		}

		this.g2d.setFont(fa.font());
		this.g2d.setColor(fa.fontColor());
		this.g2d.drawString(text.getText(), r.x, r.y + r.height);

		SelectionAttributes sa = (SelectionAttributes) text.getAttributes(SelectionAttributes.ID);
		if (sa.isSelected())
			this.drawSelection(text);
	}

	/**
	 * Draws the specified SCollection according to its attributes.
	 * 
	 * @param rect
	 *            The specified SCollection.
	 */
	@Override
	public void visitCollection(SCollection collection) {
		for (Shape s : collection.getShapes()) {
			s.accept(this);
		}
		SelectionAttributes sa = (SelectionAttributes) collection.getAttributes(SelectionAttributes.ID);
		if (sa.isSelected())
			this.drawSelection(collection);
	}

	@Override
	public void visitLine(SLine line) {
		Rectangle r = line.getBounds();
		// line.getAttributes(ColorAttributes.ID) = ColorAttributes(false, true,
		// Color.BLACK, Color.BLACK);
		line.addAttributes(new ColorAttributes(false, true, Color.BLACK, Color.BLACK));

		this.g2d.drawLine(line.getLoc().x, line.getLoc().y, line.getLoc().x + line.getRect().width,
				line.getLoc().y + line.getRect().height);

		SelectionAttributes sa = (SelectionAttributes) line.getAttributes(SelectionAttributes.ID);
		if (sa.isSelected())
			this.drawSelection(line);

	}

	/**
	 * Draws the selection part of the specified shape.
	 * 
	 * @param s
	 *            Shape from which to draw the selection.
	 */
	private void drawSelection(Shape s) {
		Rectangle r = s.getBounds();

		// Draws the rectangle selection
		Stroke tmp = g2d.getStroke();
		g2d.setColor(Color.LIGHT_GRAY);
		float[] dash = { 5F, 5F };
		g2d.setStroke(new BasicStroke(1F, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 3F, dash, 0F));
		g2d.drawRect(r.x - 1, r.y - 1, r.width + 1, r.height + 1);
		g2d.setStroke(tmp);

		// Draws the corners
		Rectangle corner = s.getTLhandler(r);
		g2d.fillRect(corner.x, corner.y, corner.width, corner.height);
		corner = s.getTRhandler(r);
		g2d.fillRect(corner.x, corner.y, corner.width, corner.height);
		corner = s.getBLhandler(r);
		g2d.fillRect(corner.x, corner.y, corner.width, corner.height);
		corner = s.getBRhandler(r);
		g2d.fillRect(corner.x, corner.y, corner.width, corner.height);
	}

	/**
	 * Draws the lasso selector.
	 * 
	 * @param lasso
	 *            The bounds of the lasso selector.
	 */
	public void drawLasso(Rectangle lasso) {
		g2d.setColor(new Color(50, 50, 255, 128));
		g2d.fillRect(lasso.x, lasso.y, lasso.width, lasso.height);
		g2d.setColor(Color.BLUE);
		g2d.drawRect(lasso.x, lasso.y, lasso.width, lasso.height);
	}

}
