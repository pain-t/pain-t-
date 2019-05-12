package graphics.shapes.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import graphics.shapes.SCollection;
import graphics.shapes.Shape;
import graphics.shapes.ShapeModel;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Controller;


public class ShapesController extends Controller {

	private Point lastPoint;
	
	public ShapesController(Object model) {
		super(model);
		this.lastPoint = new Point();
	}

    public ShapeModel getModel() {
    	return (ShapeModel)super.getModel();
    }
	
	@Override
	public void mouseDragged(MouseEvent evt) {
		translateSelected(evt.getPoint());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.lastPoint.setLocation(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Shape s = this.getTarget(e.getPoint());
		if(!e.isShiftDown())this.unselectAll();
		if(s != null) this.getAttributes(s).toggleSelection();
	}


	private SelectionAttributes getAttributes(Shape s) {
		return (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
	}

	private void translateSelected(Point p) {
		for(Shape s: this.getModel().getData().getShapes()) {
			SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
			if (sa.isSelected()) {
				s.translate(p.x-this.lastPoint.x, p.y-this.lastPoint.y);
			}
		}
		this.lastPoint.setLocation(p);
	}

	private Shape getTarget(Point p) {
		for(Shape s: this.getModel().getData().getShapes()) {
			if(s.getBounds().contains(p.getX(),p.getY())) {
				return s;
			}
		}
		return null;
	}

	private void unselectAll() {
		for(Shape s: this.getModel().getData().getShapes()) {
			SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
			sa.unselect();
		}
	}
}
