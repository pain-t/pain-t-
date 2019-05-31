package graphics.shapes.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


import graphics.shapes.SOval;
import graphics.shapes.SCollection;
import graphics.shapes.SRectangle;
import graphics.shapes.Shape;
import graphics.shapes.ShapeModel;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
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
	
	
	/**
	 * The action to do when the ABORT button is clicked.
	 * @return the action to do.
	 */
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
				if(c.equals(getView().getJpopupFill().getComponent(0)))
					getView().getJpopupFill().setVisible(false);
				else if(c.equals(getView().getJpopupStroke().getComponent(0)))
					getView().getJpopupStroke().setVisible(false);
			}
		};
	}
	
	/**
	 * The action to do when the OK button is clicked.
	 * @return the action to do.
	 */
	public ActionListener closePopAndSetColor() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SCollection model = ((ShapeModel)getView().getModel()).getData(); 
				ColorChooser c = (ColorChooser)((Component)e.getSource()).getParent().getParent().getParent().getParent();
				if(c.equals((getView()).getJpopupFill().getComponent(0))) {
					getView().getJpopupFill().setVisible(false);
					getView().getFillBtn().setColor(((ColorChooser)getView().getJpopupFill().getComponent(0)).getColor());
					
					for(Shape s : model.getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							ColorAttributes co = (ColorAttributes)s.getAttributes(ColorAttributes.ID);
							co.setFilledColor(c.getColor());
						}
					}

				}
				else if(c.equals(getView().getJpopupStroke().getComponent(0))) {
					getView().getJpopupStroke().setVisible(false);
					getView().getStrokeBtn().setColor(((ColorChooser)getView().getJpopupStroke().getComponent(0)).getColor());
					
					for(Shape s : model.getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							ColorAttributes co = (ColorAttributes)s.getAttributes(ColorAttributes.ID);
							co.setFilledColor(c.getColor());
						}
					}
				}
				else if(c.equals(getView().getJpopupText().getComponent(0))) {
					getView().getJpopupText().setVisible(false);
					getView().getTextBtn().setColor(((ColorChooser)getView().getJpopupText().getComponent(0)).getColor());
					
					for(Shape s : model.getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							FontAttributes fa = (FontAttributes)s.getAttributes(FontAttributes.ID);
							fa.setFontColor(c.getColor());
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
			if(co!=null) {
			    this.getView().getFillBtn().setColor(co.filledColor());
			    this.getView().getStrokeBtn().setColor(co.strokedColor());

			}
			FontAttributes fa = (FontAttributes)s.getAttributes(FontAttributes.ID);
			if(fa!=null) {
				this.getView().getTextBtn().setColor(fa.fontColor());
				this.getView().getFontSizeBox().getModel().setSelectedItem(fa.font().getSize());
				this.getView().getFontFamilyBox().getModel().setSelectedItem(fa.font().getFontName());
			}
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

	public ActionListener updateBox() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(getView().getFontFamilyBox())) {
					for(Shape s : ((ShapeModel)getModel()).getData().getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							FontAttributes fa = (FontAttributes)s.getAttributes(FontAttributes.ID);
							fa.setFont(new Font((String)getView().getFontFamilyBox().getSelectedItem(),0,fa.font().getSize()));
						}
					}
				}
				else if(e.getSource().equals(getView().getFontSizeBox())) {
					for(Shape s : ((ShapeModel)getModel()).getData().getShapes()) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							FontAttributes fa = (FontAttributes)s.getAttributes(FontAttributes.ID);
							fa.setFont(new Font(fa.font().getFontName(),0,(int) getView().getFontSizeBox().getSelectedItem()));
						}
					}
				}
			}
		};
	}
}
