package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.text.AttributeSet.FontAttribute;

import graphics.shapes.SOval;
import graphics.shapes.SCollection;
import graphics.shapes.SLine;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.ShapeModel;
import graphics.shapes.attributes.Attributes;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.ui.component.ColorChooser;
import graphics.shapes.ui.component.TextEntry;
import graphics.ui.Controller;

public class BannerController extends Controller {

	public BannerController(Object newModel) {
		super(newModel);
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
				ColorChooser c = (ColorChooser)((Component)e.getSource()).getParent().getParent().getParent().getParent();
				if(c.equals(((BannerView)getView()).getJpopupFill().getComponent(0)))
					((BannerView)getView()).getJpopupFill().setVisible(false);
				else if(c.equals(((BannerView)getView()).getJpopupStroke().getComponent(0)))
					((BannerView)getView()).getJpopupStroke().setVisible(false);
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
							co.setStrokedColor(c.getColor());
						}
					}
				}
				//TODO setColor btn & selected shape
				
			}
		};
	}
	
	public ActionListener closePopAndSetText() {
		BannerController bc = (BannerController) this;
		return new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
						//System.out.println("fermer la fenetre de texte");
						//TextEntry text = new TextEntry(bc);
						Point p = new Point (50,50);
						System.out.println(((TextEntry)((Component) e.getSource()).getParent().getComponent(0)).getText());
						SText  t= new SText (p , ((TextEntry)((Component) e.getSource()).getParent().getComponent(0)).getText());
						System.out.println(t.getText());
						Color fc;
						Color sc;
						fc = ((Color)((BannerView)getView()).getFillBtnColor());
						sc = ((Color)((BannerView)getView()).getStrokeBtnColor());
						t.addAttributes( new FontAttributes());
						t.addAttributes(new SelectionAttributes());
						t.addAttributes(new ColorAttributes(((BannerView)getView()).getFillBtnBox(),((BannerView)getView()).getStrokeBtnBox(), fc , sc));
						((ShapeModel)getModel()).add(t);
						((JPopupMenu)((Component)e.getSource()).getParent().getParent()).setVisible(false);
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
				Color fc;
				Color sc;
				fc = ((Color)((BannerView)getView()).getFillBtnColor());
				sc = ((Color)((BannerView)getView()).getStrokeBtnColor());
				c.addAttributes(new SelectionAttributes());
				c.addAttributes(new ColorAttributes(((BannerView)getView()).getFillBtnBox(),((BannerView)getView()).getStrokeBtnBox(), fc , sc));
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
				Color fc;
				Color sc;
				fc = ((Color)((BannerView)getView()).getFillBtnColor());
				sc = ((Color)((BannerView)getView()).getStrokeBtnColor());
				
				r.addAttributes(new SelectionAttributes());
				r.addAttributes(new ColorAttributes(((BannerView)getView()).getFillBtnBox(),((BannerView)getView()).getStrokeBtnBox(), fc , sc));
				((ShapeModel)getModel()).add(r);
			}
		};
	}
	
	public ActionListener createLine() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point p1 = new Point(100,100);
				Point p2 = new Point(110,200);
				SLine l = new SLine(p1.x, p1.y , p2.x - p1.x , p2.y - p1.y);
				l.addAttributes(new SelectionAttributes());
				l.addAttributes(new ColorAttributes(false, true, Color.BLACK,((Color)((BannerView)getView()).getStrokeBtnColor()) ));
				((ShapeModel)getModel()).add(l);
				
			}
		};
	}


	public ActionListener createText() {
		BannerController bc = (BannerController) this;
		return new ActionListener() {
			private JPopupMenu jPopupText;
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TextEntry text = new TextEntry(bc);
				this.jPopupText = new JPopupMenu();
				JPanel j = new JPanel();
				j.add(text);
				j.add(text.getOk());
				j.add(text.getAbort());
				this.jPopupText.add(j);			
				this.jPopupText.show((Component) e.getSource(),-100, -100);
				
				
				//System.out.println(text.getText());
			}
		};	
	}


	public ActionListener createCollection() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SCollection sc = new SCollection();
				//l.addAttributes(new SelectionAttributes());
				//l.addAttributes(new ColorAttributes(false, true, Color.BLACK,((Color)((BannerView)getView()).getStrokeBtnColor()) ));
				SCollection model = (((ShapeModel) getModel()).getData());
				System.out.println(model);
				for (ListIterator<Shape> it = model.iterator(model.size()) ; it.hasPrevious();) {
					Shape s = it.next();
					System.out.println(s);
					SelectionAttributes sa = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
					if (sa.isSelected()) {
						Shape clone = s.clone();
						model.remove(s);
						sc.add(clone);
					}
				}
				((ShapeModel)getModel()).add(sc);
				
			}
		};
	}
	
	
}
