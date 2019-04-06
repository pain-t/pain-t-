package graphics.shapes.ui;

import java.awt.Graphics;
import graphics.shapes.SCollection;
import graphics.shapes.Shape;
import graphics.shapes.ShapeVisitor;
import graphics.ui.Controller;
import graphics.ui.View;

public class ShapesView extends View{

	private ShapeVisitor sv;

	public ShapesView(Shape model) {
		super(model);
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
		((SCollection)this.getModel()).accept(sv);
	}

	public void invalidate() {
		this.paintImmediately(getBounds());
	}
}
