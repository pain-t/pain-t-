package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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

	public static final ColorAttributes DEFAULTCOLORATTRIBUTES = new ColorAttributes();
	public static final FontAttributes DEFAULTFONTATTRIBUTES = new FontAttributes();
	public static final int DEFAULTSELECTIONRECTSIZE = 5;
	public static final Color DEFAULTCOLORSELECTIONRECT = Color.black;
	
	private Graphics2D g2d;

	// --------------------------------------------------------------------

	public ShapeDraftman(Graphics g) {
		this.g2d =(Graphics2D)g;
	}
	
	// --------------------------------------------------------------------

	
	@Override
	public void visitRectangle(SRectangle rect) {
		Rectangle r = rect.getBounds();
		ColorAttributes fc = (ColorAttributes) rect.getAttributes(ColorAttributes.ID);
		if(fc == null) fc = DEFAULTCOLORATTRIBUTES;

		if(fc.filled())	{
			g2d.setColor(fc.filledColor());
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}

		if(fc.stroked()) {
			g2d.setColor(fc.strokedColor());
			g2d.drawRect(r.x, r.y, r.width, r.height);
		}

		SelectionAttributes sa = (SelectionAttributes)(rect.getAttributes(SelectionAttributes.ID));
		if(sa.isSelected()) this.drawSelection(rect);
	}

	@Override
	public void visitOval(SOval oval) {
		Rectangle r = oval.getBounds();
		ColorAttributes fc = (ColorAttributes) oval.getAttributes(ColorAttributes.ID);

		if(fc == null) fc = DEFAULTCOLORATTRIBUTES;
		if(fc.filled())	{
			this.g2d.setColor(fc.filledColor());
			this.g2d.fillOval(r.x, r.y, r.width, r.height);
		}

		if(fc.stroked()) {
			this.g2d.setColor(fc.strokedColor());
			this.g2d.drawOval(r.x, r.y, r.width, r.height);
		}
		
		SelectionAttributes sa = (SelectionAttributes) oval.getAttributes(SelectionAttributes.ID);
		if(sa.isSelected()) this.drawSelection(oval);
	}

	@Override
	public void visitText(SText text) {
		Rectangle r = text.getBounds();
		ColorAttributes fc = (ColorAttributes)text.getAttributes(ColorAttributes.ID);

		if(fc == null) fc = DEFAULTCOLORATTRIBUTES;

		FontAttributes fa = (FontAttributes)text.getAttributes(FontAttributes.ID);
		if(fa == null) fa = DEFAULTFONTATTRIBUTES;

		if(fc.filled())	{
			g2d.setColor(fc.filledColor());
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}
		if(fc.stroked())	{
			this.g2d.setColor(fc.strokedColor());
			this.g2d.drawRect(r.x, r.y, r.width, r.height);
		}

		this.g2d.setFont(fa.font());
		this.g2d.setColor(fa.fontColor());
		this.g2d.drawString(text.getText(), r.x, r.y + r.height);
		
		SelectionAttributes sa = (SelectionAttributes) text.getAttributes(SelectionAttributes.ID);
		if(sa.isSelected()) this.drawSelection(text);
	}

	@Override
	public void visitCollection(SCollection collection) {
		for(Shape s : collection.getShapes()) {
			s.accept(this);
		}
		SelectionAttributes sa = (SelectionAttributes) collection.getAttributes(SelectionAttributes.ID);
		if(sa.isSelected()) this.drawSelection(collection);
	}
	
	@Override
	public void visitLine(SLine line) {
		Rectangle r = line.getBounds();
		ColorAttributes fc = (ColorAttributes) line.getAttributes(ColorAttributes.ID);
		if(fc == null) fc = DEFAULTCOLORATTRIBUTES;
		//line.getAttributes(ColorAttributes.ID) = ColorAttributes(false, true,  Color.BLACK, Color.BLACK);
		line.addAttributes(new ColorAttributes(false,true,Color.BLACK,Color.BLACK));
		if(fc.stroked()) {
			g2d.setColor(fc.strokedColor());
			this.g2d.drawLine(line.getLoc().x , line.getLoc().y , line.getLoc().x + line.getRect().width , line.getLoc().y + line.getRect().height);
		}
		
		else {
			this.g2d.drawLine(line.getLoc().x , line.getLoc().y , line.getLoc().x + line.getRect().width , line.getLoc().y + line.getRect().height);
			
		}
		
		SelectionAttributes sa = (SelectionAttributes) line.getAttributes(SelectionAttributes.ID);
		if(sa.isSelected()) this.drawSelection(line);
		
	}

	private void drawSelection(Shape s) {
	
		Rectangle r = s.getBounds();
//		Stroke tmp = g2d.getStroke();
		g2d.setColor(Color.LIGHT_GRAY);
//		float[] dash = { 5F, 5F };
//		g2d.setStroke( new BasicStroke( 1F, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 3F, dash, 0F));
		g2d.drawRect(r.x - 1, r.y - 1, r.width + 1, r.height + 1);
//		g2d.setStroke(tmp);
		
		Rectangle corner = s.getTLhandler(r);
		g2d.fillRect(corner.x, corner.y, corner.width, corner.height);
		corner = s.getTRhandler(r);
		g2d.fillRect(corner.x, corner.y, corner.width, corner.height);
		corner = s.getBLhandler(r);
		g2d.fillRect(corner.x, corner.y, corner.width, corner.height);
		corner = s.getBRhandler(r);
		g2d.fillRect(corner.x, corner.y, corner.width, corner.height);
	}
	
	public void drawLasso(Rectangle lasso) {
		g2d.setColor(new Color(50, 50, 255, 128));
		g2d.fillRect(lasso.x, lasso.y, lasso.width, lasso.height);
		g2d.setColor(Color.BLUE);
		g2d.drawRect(lasso.x, lasso.y, lasso.width, lasso.height);
	}

	
}
