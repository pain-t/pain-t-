package graphics.shapes.ui;

import java.awt.Point;
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

	@Override
	public void mouseDragged(MouseEvent evt) {
		super.mouseDragged(evt);
		translateSelected(evt.getPoint());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		this.lastPoint.setLocation(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Shape s = this.getTarget(e);
		if(!e.isShiftDown())this.unselectAll();
		if(s != null) this.getAttributes(s).toggleSelection();
	}

	private SelectionAttributes getAttributes(Shape s) {
		return (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
	}

	private void translateSelected(Point p) {
		for(Shape s: ((SCollection)((ShapeModel) this.getModel()).getData()).getShapes()) {
			SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
			if (sa.isSelected()) {
				s.translate(p.x-this.lastPoint.x, p.y-this.lastPoint.y);
			}
		}
		this.lastPoint.setLocation(p);
	}

	private Shape getTarget(MouseEvent e) {
		for(Shape s: ((SCollection)((ShapeModel) this.getModel()).getData()).getShapes()) {
			if(s.getBounds().contains(e.getX(),e.getY())) {
				return s;
			}
		}
		return null;
	}

	private void unselectAll() {
		for(Shape s: ((SCollection)((ShapeModel) this.getModel()).getData()).getShapes()) {
			SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
			sa.unselect();
		}
	}
}
