package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;

import graphics.shapes.SCollection;
import graphics.shapes.ShapeModel;
import graphics.shapes.ShapeVisitor;
import graphics.ui.Controller;
import graphics.ui.View;

public class ShapesView extends View{

	private ShapeVisitor sv;

	public ShapesView(ShapeModel model) {
		super(model);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	public Controller defaultController(Object model) {
		return new ShapesController(model);
	}

	@Override
	public boolean isFocusTraversable() {
		return true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		sv = new ShapeDraftman(g);
		((SCollection)((ShapeModel)this.getModel()).getData()).accept(sv);
	}

	public void invalidate() {
		this.paintImmediately(getBounds());
	}
}
