package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.BorderFactory;

import graphics.shapes.SCollection;
import graphics.shapes.SOval;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.ShapeModel;
import graphics.shapes.ShapeVisitor;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.ui.Controller;
import graphics.ui.View;

public class ShapesView extends View{

	private ShapeDraftman sdm;

	public ShapesView(ShapeModel model, BannerController bc) {
		super(model);
		this.addMouseListener(bc);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	public Controller defaultController(Object model) {
		return new ShapesController(model);
	}

	@Override
	public boolean isFocusTraversable() {
		// To retrieve the TAB key event
		setFocusTraversalKeysEnabled(false);
		return true;
	}
	
    public ShapeModel getModel() {
    	return (ShapeModel)super.getModel();
    }
    
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		sdm = new ShapeDraftman(g);
		getModel().getData().accept(sdm);
		
		Rectangle lasso = ((ShapesController) this.getController()).getLasso();
		if(lasso != null) this.sdm.drawLasso(lasso);
	}
	
	@Override
	public void invalidate() {
		this.paintImmediately(getBounds());
	}
	
	@Deprecated
	public void saveShapes() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("/home/balanche/AOO/shapes/saves/test1.txt", "UTF-8");
		writer.println("The first line");
		writer.println("The second line");
		saveShapeCollection((SCollection) this.getModel().getData(), writer);
		writer.close();
	}
	
	@Deprecated
	public void saveShapeCollection(SCollection collection, PrintWriter writer) {
		for(Shape s : collection.getShapes()) {
			String line = s.getLoc().getX()+" "+s.getLoc().getY()+" ";
			switch (s.getId()) {
				case SCollection.ID:
					writer.println("Collection");
					saveShapeCollection((SCollection)s,writer);
					break;
					
				case SRectangle.ID:
					SRectangle rectangle = (SRectangle)s;
					line += "Rectangle " + rectangle.getRect().getWidth() + rectangle.getRect().getHeight()
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).filled() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).stroked() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).filledColor() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).strokedColor();
					writer.println(line);
					break;
					
				case SOval.ID:
					SOval circle = (SOval)s;
					line += "Circle " + circle.getRect().getWidth()/2+" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).filled() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).stroked() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).filledColor() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).strokedColor();
					writer.println(line);
					break;
				case SText.ID:
					SText txt = (SText)s;
					line += "Text " + txt.getText()+" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).filled() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).stroked() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).filledColor() +" "
							+ ((ColorAttributes)(s.getAttributes(ColorAttributes.ID))).strokedColor()+" "
							+ ((FontAttributes)(s.getAttributes(FontAttributes.ID))).font() +" "
							+ ((FontAttributes)(s.getAttributes(FontAttributes.ID))).fontColor();
					writer.println(line);
					break;
					
				default:
					break;
			}
			
		}
	}
}
