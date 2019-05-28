package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;

import graphics.shapes.SCollection;
import graphics.shapes.ShapeModel;
import graphics.shapes.ShapeVisitor;
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
		setFocusTraversalKeysEnabled(false);	// to retrieve the TAB key event (https://stackoverflow.com/questions/8275204/how-can-i-listen-to-a-tab-key-pressed-typed-in-java) 
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
}
