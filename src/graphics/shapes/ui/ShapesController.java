package graphics.shapes.ui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import graphics.shapes.SCollection;
import graphics.shapes.Shape;
import graphics.shapes.ShapeModel;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Controller;


public class ShapesController extends Controller {

	private Point lastPoint;
	private ArrayList <Shape> selectedShape;
	
	public ShapesController(Object model) {
		super(model);
		this.lastPoint = new Point();
		selectedShape = new ArrayList<Shape>();
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
		Shape s = this.getTarget(e);
		if(!e.isShiftDown())this.unselectAll();
		if(s != null) this.getAttributes(s).toggleSelection();
	}

	
	
	@Override
	// TODO invalidate 
	public void keyTyped(KeyEvent evt) {
		
		//System.out.println(evt.getKeyChar());
		if (evt.getKeyChar() == KeyEvent.VK_DELETE) {
			System.out.println("supp");
			SCollection model = (SCollection)((ShapeModel) this.getModel()).getData();
			for (ListIterator<Shape> it = model.iterator() ; it.hasNext();) {
				SelectionAttributes sa = (SelectionAttributes)it.next().getAttributes(SelectionAttributes.ID);
				if (sa.isSelected()) {
					it.remove();
				}
				this.getView().invalidate();
			}
		}
	}
	
	
	public void keyPressed(KeyEvent evt) {
		SCollection model = (SCollection)((ShapeModel) this.getModel()).getData();
		if(evt.getKeyCode() == KeyEvent.VK_C ){
			if(evt.isControlDown()) {
				System.out.println("ctrl c");
				for (ListIterator<Shape> it = model.iterator() ; it.hasNext();) {
					Shape s = it.next();
					SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
					if(sa.isSelected()) {
						selectedShape.add(s);
						//System.out.println(s);
					}
				}
			}
		}
		if(evt.getKeyCode() == KeyEvent.VK_X){
			if(evt.isControlDown()) {
				for (ListIterator<Shape> it = model.iterator() ; it.hasNext();) {
					Shape s = it.next();
					SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
					if(sa.isSelected()) {
						selectedShape.add(s);
						it.remove();
					}
				}
				this.getView().invalidate();
			}
		}
		if(evt.getKeyCode() == KeyEvent.VK_V){
			if(evt.isControlDown()) {
				for (int i = 0 ; i < selectedShape.size() ; i++) {
					model.add(selectedShape.get(i));
					int tmp  = model.getShapes().indexOf(selectedShape.get(i));
					model.getShapes().get(tmp).register(new ShapesObserver((ShapesView) this.getView()));
				}
			}
		}
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
