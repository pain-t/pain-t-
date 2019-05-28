package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import graphics.shapes.SOval;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.Shape;
import graphics.shapes.ShapeModel;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.ui.component.ColorChooser;
import graphics.ui.Controller;

public class BannerController extends Controller {

	public BannerController(Object newModel) {
		super(newModel);
	}
	
	final public BannerView getView() {
		return (BannerView)super.getView();
	}
	
	public ActionListener doPrint() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("print");
			}
		};
		
	}
	
	
	
	public ActionListener closePop() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO better way ?
				//1 ColorChooserPanel
				//2 gridbagpanel
				//3 tab panel
				//4 colorchooser
				ColorChooser c = (ColorChooser)((Component)e.getSource()).getParent().getParent().getParent().getParent();
				System.out.println(((Component)e.getSource()).getParent().getClass());
				System.out.println(((Component)e.getSource()).getParent().getParent().getClass());
				System.out.println(((Component)e.getSource()).getParent().getParent().getParent().getClass());
				System.out.println(((Component)e.getSource()).getParent().getParent().getParent().getParent().getClass());

				if(c.equals(getView().getJpopupFill().getComponent(0)))
					getView().getJpopupFill().setVisible(false);
				else if(c.equals(getView().getJpopupStroke().getComponent(0)))
					getView().getJpopupStroke().setVisible(false);
			}
		};
	}
	
	public ActionListener closePopAndSetColor() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SCollection model = ((ShapeModel)getView().getModel()).getData(); 
				ColorChooser c = (ColorChooser)((Component)e.getSource()).getParent().getParent().getParent().getParent();
				if(c.equals(((BannerView)getView()).getJpopupFill().getComponent(0))) {
					((BannerView)getView()).getJpopupFill().setVisible(false);
					((BannerView)getView()).getFillBtn().setColor(((ColorChooser)((BannerView)getView()).getJpopupFill().getComponent(0)).getColor());
					
					for(Shape s : model.getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							ColorAttributes co = (ColorAttributes)s.getAttributes(ColorAttributes.ID);
							co.setFilledColor(c.getColor());
						}
					}

				}
				else if(c.equals(((BannerView)getView()).getJpopupStroke().getComponent(0))) {
					((BannerView)getView()).getJpopupStroke().setVisible(false);
					((BannerView)getView()).getStrokeBtn().setColor(((ColorChooser)((BannerView)getView()).getJpopupStroke().getComponent(0)).getColor());
					
					for(Shape s : model.getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							ColorAttributes co = (ColorAttributes)s.getAttributes(ColorAttributes.ID);
							co.setFilledColor(c.getColor());
						}
					}
				}
				
			}
		};
	}
	
	private Shape getTarget(MouseEvent e) {
		for(Shape s: ((SCollection)((ShapeModel) this.getModel()).getData()).getShapes()) {
			if(s.getBounds().contains(e.getX(),e.getY())) {
				return s;
			}
		}
		return null;
	}
	
	public void mouseClicked (MouseEvent e) {
		Shape s = getTarget(e);
		
		if (s!= null) {
			ColorAttributes co = (ColorAttributes) s.getAttributes(ColorAttributes.ID);
		    ((BannerView)this.getView()).getFillBtn().setColor(co.filledColor());
		    ((BannerView)this.getView()).getStrokeBtn().setColor(co.strokedColor());

			Color newC = ((ColorChooser)((BannerView)this.getView()).getJpopupFill().getComponent(0)).getColor();
			this.getView().repaint();
			
			
		}
	}
	
	public ActionListener createCircle() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SOval c = new SOval(new Point(100,100) , 50, 50);
				c.addAttributes(new SelectionAttributes());
				c.addAttributes(new ColorAttributes());
				((ShapeModel)getModel()).add(c);
				
			}
		};
	}
	
	public ActionListener createRectangle() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point p = new Point(100,100);	
				SRectangle r = new SRectangle(p, 50, 50);
				r.addAttributes(new SelectionAttributes());
				r.addAttributes(new ColorAttributes());
				((ShapeModel)getModel()).add(r);
				
			}
		};
	}
}
