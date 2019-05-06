package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import graphics.shapes.SCircle;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.ShapeVisitor;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;

public class ShapeDraftman implements ShapeVisitor {

	public static final ColorAttributes DEFAULTCOLORATTRIBUTES = new ColorAttributes();
	public static final FontAttributes DEFAULTFONTATTRIBUTES = new FontAttributes();
	public static final int DEFAULTSELECTIONRECTSIZE = 5;
	public static final Color DEFUALTCOLORSELECTIONRECT = Color.black;
	
	private Graphics2D g2d;

	public ShapeDraftman(Graphics g) {
		this.g2d =(Graphics2D)g;
	}

	@Override
	public void visitRectangle(SRectangle rect) {
		Rectangle r = rect.getRect();
		ColorAttributes fc = (ColorAttributes)rect.getAttributes(ColorAttributes.ID);
		if(fc == null) fc = DEFAULTCOLORATTRIBUTES;

		if(fc.filled())	{
			g2d.setColor(fc.filledColor());
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}

		if(fc.stroked()) {
			g2d.setColor(fc.strokedColor());
			g2d.drawRect(r.x, r.y, r.width,r.height);
		}

		SelectionAttributes sa = (SelectionAttributes)(rect.getAttributes(SelectionAttributes.ID));
		if(sa.isSelected()) this.drawSelection(rect);
	}

	@Override
	public void visitCircle(SCircle circle) {
		ColorAttributes fc = (ColorAttributes)circle.getAttributes(ColorAttributes.ID);

		if(fc == null) fc = DEFAULTCOLORATTRIBUTES;
		if(fc.filled())	{
			this.g2d.setColor(fc.filledColor());
			this.g2d.fillOval(circle.getLoc().x,circle.getLoc().y, circle.getRadius()*2, circle.getRadius()*2);
		}

		if(fc.stroked()) {
			this.g2d.setColor(fc.strokedColor());
			this.g2d.drawOval(circle.getLoc().x,circle.getLoc().y, circle.getRadius()*2, circle.getRadius()*2);
		}
		SelectionAttributes sa = (SelectionAttributes)(circle.getAttributes(SelectionAttributes.ID));
		if(sa.isSelected()) this.drawSelection(circle);

	}

	@Override
	public void visitText(SText text) {

		ColorAttributes fc = (ColorAttributes)text.getAttributes(ColorAttributes.ID);
		if(fc == null) fc = DEFAULTCOLORATTRIBUTES;

		FontAttributes fa = (FontAttributes)text.getAttributes(FontAttributes.ID);
		if(fa == null) fa = DEFAULTFONTATTRIBUTES;
		Rectangle r = fa.getBounds(text.getText());

		if(fc.filled())	{
			g2d.setColor(fc.filledColor());
			g2d.fillRect(text.getLoc().x, text.getLoc().y, r.width, r.height);
		}
		if(fc.stroked())	{
			this.g2d.setColor(fc.strokedColor());
			this.g2d.drawRect(text.getLoc().x, text.getLoc().y, r.width, r.height);
		}

		this.g2d.setFont(fa.font());
		this.g2d.setColor(fa.fontColor());
		this.g2d.drawString(text.getText(), text.getLoc().x, text.getLoc().y+r.height);
		SelectionAttributes sa = (SelectionAttributes)(text.getAttributes(SelectionAttributes.ID));
		if(sa.isSelected()) this.drawSelection(text);
	}

	@Override
	public void visitCollection(SCollection collection) {
		for(Shape s : collection.getShapes()) {
			s.accept(this);
		}
		SelectionAttributes sa = (SelectionAttributes)(collection.getAttributes(SelectionAttributes.ID));
		if(sa.isSelected()) this.drawSelection(collection);
	}

	private void drawSelection(Shape s) {
		this.g2d.setColor(DEFUALTCOLORSELECTIONRECT);
		this.g2d.drawRect(s.getBounds().x-DEFAULTSELECTIONRECTSIZE, s.getBounds().y-DEFAULTSELECTIONRECTSIZE, DEFAULTSELECTIONRECTSIZE, DEFAULTSELECTIONRECTSIZE);
		this.g2d.drawRect(s.getBounds().x+s.getBounds().width, s.getBounds().y+s.getBounds().height, DEFAULTSELECTIONRECTSIZE, DEFAULTSELECTIONRECTSIZE);
	}
}
