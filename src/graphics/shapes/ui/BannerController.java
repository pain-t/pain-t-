package graphics.shapes.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ListIterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import graphics.shapes.SOval;
import graphics.shapes.SCollection;
import graphics.shapes.SLine;
import graphics.shapes.SRectangle;
import graphics.shapes.SText;
import graphics.shapes.Shape;
import graphics.shapes.ShapeModel;
import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.shapes.ui.component.ColorChooser;
import graphics.shapes.ui.component.TextEntry;
import graphics.ui.Controller;

public class BannerController extends Controller {
	/**
	 * Creates the banner controller.
	 * @param model The model that contains shapes.
	 */
	public BannerController(Object model) {
		super(model);
	}
	/**
	 * Avoid the cast.
	 */
	final public BannerView getView() {
		return (BannerView)super.getView();
	}
	
	final public ShapesView getSView() {
		return (ShapesView)super.getView();
	}
	/**
	 * Testing or default action listener. It will just print "print" in the console.
	 * @return The testing or the default action listener.
	 */
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
				ColorChooser c = (ColorChooser)((Component)e.getSource()).getParent().getParent().getParent().getParent();
				if(c.equals(getView().getJpopupFill().getComponent(0)))
					getView().getJpopupFill().setVisible(false);
				else if(c.equals(getView().getJpopupStroke().getComponent(0)))
					getView().getJpopupStroke().setVisible(false);
				else if (c.equals(getView().getJpopupText().getComponent(0)))
					getView().getJpopupText().setVisible(false);
			}
		};
	}
	
	/**
	 * The action to do when the OK button is clicked for color button.
	 * @return the action to do.
	 */
	public ActionListener closePopAndSetColor() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SCollection model = ((ShapeModel)getView().getModel()).getData(); 
				ColorChooser c = (ColorChooser)((Component)e.getSource()).getParent().getParent().getParent().getParent();
				
				// if it is the fill color picker.
				if(c.equals((getView()).getJpopupFill().getComponent(0))) {
					getView().getJpopupFill().setVisible(false);
					getView().getFillBtn().setColor(((ColorChooser)getView().getJpopupFill().getComponent(0)).getColor());
					
					for(Shape s : model.getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							ColorAttributes co = (ColorAttributes)s.getAttributes(ColorAttributes.ID);
							if(co!=null)
								co.setFilledColor(c.getColor());
							
						}
					}

				}
				// if it is the stroke color picker.
				else if(c.equals(getView().getJpopupStroke().getComponent(0))) {
					getView().getJpopupStroke().setVisible(false);
					getView().getStrokeBtn().setColor(((ColorChooser)getView().getJpopupStroke().getComponent(0)).getColor());
					
					for(Shape s : model.getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							ColorAttributes co = (ColorAttributes)s.getAttributes(ColorAttributes.ID);
							if(co!=null)
								co.setStrokedColor(c.getColor());
						}
					}
				}
				// if it is the text color picker.
				else if(c.equals(getView().getJpopupText().getComponent(0))) {
					getView().getJpopupText().setVisible(false);
					getView().getTextBtn().setColor(((ColorChooser)getView().getJpopupText().getComponent(0)).getColor());
					
					for(Shape s : model.getShapes() ) {
						SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
						if(sa.isSelected()) {
							FontAttributes fa = (FontAttributes)s.getAttributes(FontAttributes.ID);
							if(fa!=null)
								fa.setFontColor(c.getColor());
						}
					}
				}
				
			}
		};
	}
	
	/**
	 * The action to do when the OK button is clicked for the text button.
	 * @return the action to do.
	 */
	public ActionListener closePopAndSetText() {
		return new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
						Point p = new Point (50,50);
						System.out.println(((TextEntry)((Component) e.getSource()).getParent().getComponent(0)).getText());
						SText  t= new SText (p , ((TextEntry)((Component) e.getSource()).getParent().getComponent(0)).getText());
						System.out.println(t.getText());
						Color fc;
						Color sc;
						fc = ((Color)((BannerView)getView()).getFillBtnColor());
						sc = ((Color)((BannerView)getView()).getStrokeBtnColor());
						FontAttributes fa = new FontAttributes(new Font((String)getView().getFontFamilyBox().getSelectedItem(),0,(Integer)getView().getFontSizeBox().getSelectedItem()),Color.black);
						t.addAttributes(fa);
						t.addAttributes(new SelectionAttributes());
						t.addAttributes(new ColorAttributes(((BannerView)getView()).getFillBtnBox(),((BannerView)getView()).getStrokeBtnBox(), fc , sc));
						((ShapeModel)getModel()).add(t);
						((JPopupMenu)((Component)e.getSource()).getParent().getParent()).setVisible(false);
			}
		};
	}
	
	/**
	 * Returns the shapes when we clicked.Null if there isn't.
	 * @param e The mouse event.
	 * @return The shapes. Null if there isn't.
	 */
	private Shape getTarget(MouseEvent e) {
		for(Shape s: ((SCollection)((ShapeModel) this.getModel()).getData()).getShapes()) {
			if(s.getBounds().contains(e.getX(),e.getY())) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Update buttons, checkbox in the banner if we clicked on a shape.
	 */
	public void mouseClicked (MouseEvent e) {
		Shape s = getTarget(e);
		
		if (s!= null) {
			ColorAttributes co = (ColorAttributes) s.getAttributes(ColorAttributes.ID);
			if(co!=null) {
			    this.getView().getFillBtn().setColor(co.filledColor());
			    this.getView().getStrokeBtn().setColor(co.strokedColor());
			    this.getView().getFillBtnBox();
			    this.getView().getStrokeBtnBox();

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
	
	/**
	 * The actionListener which creates a shapes.
	 * @return The actionListener which creates a shapes.
	 */
	public ActionListener createOval() {
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
	/**
	 * The actionListener which creates a rectangle.
	 * @return The actionListener which creates a rectangle.
	 */
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
	
	/**
	 * Returns the actionListener which creates a line.
	 * @return The actionListener which creates a line.
	 */
	public ActionListener createLine() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point p1 = new Point(100,100);
				Point p2 = new Point(110,200);
				SLine l = new SLine(p1.x, p1.y , p2.x - p1.x , p2.y - p1.y);
				l.addAttributes(new SelectionAttributes());
				l.addAttributes(new ColorAttributes(false, true, Color.BLACK,((BannerView)getView()).getStrokeBtnColor()));
				((ShapeModel)getModel()).add(l);
				
			}
		};
	}

	/**
<<<<<<< HEAD
<<<<<<< HEAD
	 * Creates une mini fen�tre interactive qui permet de rentrer le texte contenu dans le nouveau SText.
	 * @return 
=======
=======
>>>>>>> 4136d2e1cbc2293edfb3ab1ead9906db692b98b0
	 * Returns the actionListener which creates a text.
	 * @return The actionListener which creates a text.
	 */
	public ActionListener createText() {
		BannerController bc = (BannerController) this;
		return new ActionListener() {
			private JPopupMenu jPopupText;
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TextEntry text = new TextEntry(bc);
				this.jPopupText = new JPopupMenu();
				text.getAbort().addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						jPopupText.setVisible(false);
						
					}
				});
				JPanel j = new JPanel();
				j.add(text);
				j.add(text.getOk());
				j.add(text.getAbort());
				this.jPopupText.add(j);			
				this.jPopupText.show((Component) e.getSource(),-100, -100);
			}
		};	
	}
	
	/**
	 * The actionListener which saves the current shapes.
	 * @return The actionListener which saves the current shapes.
	 */
	public ActionListener saveShapes() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filename = JOptionPane.showInputDialog("Nom du fichier ?");
				if(filename != null) {
					System.out.println("Sauvegarde de votre dessin dans le fichier : "+filename);
					((ShapeModel) getModel()).serializeShapes(filename);
				}
				else {
					System.out.println("Annulation de la sauvegarde");
				}
			}
		};
	}
	
	/**
	 * The actionListener which opens a save files.
	 * @return The actionListener which opens a save file.
	 */
	public ActionListener openShapes() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				
				int returnVal = fc.showOpenDialog(null);
				if(returnVal==JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
					((ShapeModel) getModel()).deserializeShapes(file);
					System.out.println("Chargement du fichier de sauvegarde : "+file.getAbsolutePath());
				}
				else {
					System.out.println("Ouverture de fichier de sauvegarde annulée");
				}
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
	
	/**
	 * Update the text when the combobox have a new item selected.
	 * @return The ActionListener which update the text when the combobox have a new item selected.
	 */
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
