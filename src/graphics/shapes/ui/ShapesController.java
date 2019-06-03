package graphics.shapes.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import graphics.shapes.SCollection;
import graphics.shapes.Shape;
import graphics.shapes.ShapeModel;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Controller;
import utils.Utils;


public class ShapesController extends Controller {

	/** True if the up arrow is pressed, false otherwise. */
	private boolean upPressed;
	/** True if the down arrow is pressed, false otherwise. */
	private boolean downPressed;
	/** True if the left arrow is pressed, false otherwise. */
	private boolean leftPressed;
	/** True if the right arrow is pressed, false otherwise. */
	private boolean rightPressed;
	
	/** Last cursor location. */
	private Point lastPoint;
	/** List of selected shapes for the copy and paste action. */
	private ArrayList <Shape> selectedShape;
	/** Last shape to be currently selected. */
	private Shape current;
	/** Lasso to select several shapes. */
	private Rectangle lasso;
	
	/** Enumeration of possible corner handlers.
	 * TP: Top Left, TR: Top Right, BL: Bottom Left, BR: Bottom Right, NULL: None. */
	private static enum Handler { TL, TR, BL, BR, NULL };
	/** Current corner handler to be selected. */
	private static Handler handler = Handler.NULL;
	
	// -----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Constructs a ShapesController with an empty last cursor location, and an empty list of selected shapes (for the copy and paste action).
	 * @param model Exhausitive list of all the existing shapes. 
	 */
	public ShapesController(Object model) {
		super(model);
		this.lastPoint = new Point();
		selectedShape = new ArrayList<Shape>();
	}
	

    // -----------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void mouseEntered(MouseEvent e) {
		getView().requestFocus();
	}
	
	@Override
	public void mouseDragged(MouseEvent evt) {
		if(this.lasso == null) {
			int dx = evt.getX() - this.lastPoint.x;
			int dy = evt.getY() - this.lastPoint.y;
			
			this.lastPoint = evt.getPoint();
			
			// Resizing of the selected shapes by the selected corner handler 
			if(!handler.equals(Handler.NULL))
				resizeSelected(dx, dy, handler.ordinal());
			// Translation of the selected shapes
			else
				this.translateSelected(dx, dy);
		}
		
		else {
			// Resizing of the lasso
			this.lasso.setSize(this.lastPoint.x - this.lasso.x, this.lastPoint.y - this.lasso.y);
			this.lastPoint = evt.getPoint();
			Rectangle r = (Rectangle)this.lasso.clone();
			r = Utils.getAbsoluteBounds(r);
			// Selection of the shapes intersecting with the lasso
			for(Shape s : this.getModel().getData().getShapes()) {
				if(s.getBounds().intersects(r))	selection(s).select();
				else selection(s).unselect();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.lastPoint = e.getPoint();
		this.current = getTarget(e.getPoint());
		
		if(!e.isShiftDown()) {
			if(this.current != null) {
				// Selection of one shape and setting it in the foreground
				if(!selection(this.current).isSelected()) {
					unselectAll();
					selection(this.current).select();;
					getModel().getData().first(this.current);
				}
			}
			// Clicking elsewhere
			else {
				unselectAll();
				setLasso(e.getX(), e.getY(), 0, 0);
			}
					
		}
		// Grouped selection
		else {
			if(this.current != null) {
				selection(this.current).toggleSelection();
			}
			// Clicking elsewhere : starting the lasso selection
			else {
				setLasso(e.getX(), e.getY(), 0, 0);
			}
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// Release of the lasso
		if(this.lasso != null) {
			removeLasso();
			this.getView().invalidate();		
		}
		// Deselection of the corner handler
		if(!handler.equals(Handler.NULL)) {
			handler = Handler.NULL;
		}
	}
	

	public void keyPressed(KeyEvent evt) {
		SCollection model = this.getModel().getData();
		// Deletion
		if(evt.getKeyCode() == KeyEvent.VK_DELETE)
			deleteSelected();
		// Fivot
		else if(evt.getKeyCode() == KeyEvent.VK_P)
			rotateSelected();
		// Up arrow
		else if(evt.getKeyCode() == KeyEvent.VK_UP) {
			// With left arrow
			if(this.leftPressed) {
				translateSelected(-1, -1);
			}
			// With right arrow
			else if(this.rightPressed) {
				translateSelected(1, -1);
			}
			else {
				translateSelected(0, -1);
			}
			this.upPressed = true;
		}
		// Down arrow
		else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
			// With left arrow
			if(this.leftPressed) {
				translateSelected(-1, 1);
			}
			// With right arrow
			else if(this.rightPressed) {
				translateSelected(1, 1);
			}
			else {
				translateSelected(0, 1);
			}
			this.downPressed = true;
		}
		// Right arrow
		else if(evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			// With up arrow
			if(this.upPressed) {
				translateSelected(1, -1);
			}
			// With down arrow
			else if(this.downPressed) {
				translateSelected(1, 1);
			}
			else {
				translateSelected(1, 0);
			}
			this.rightPressed = true;
		}
		// Left arrow
		else if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
			// With up arrow
			if(this.upPressed) {
				translateSelected(-1, -1);
			}
			// With down arrow
			else if(this.downPressed) {
				translateSelected(-1, 1);
			}
			else {
				translateSelected(-1, 0);
			}
			this.leftPressed = true;
		}

		// Selection of all shapes
		else if (evt.getKeyCode() == KeyEvent.VK_A && evt.getModifiers() == KeyEvent.CTRL_MASK) {
			selectAll();
		}
		// More in the foreground
		else if (evt.getKeyChar() == KeyEvent.VK_1) {
			closer(this.current);
		}
		// More in the background
		else if (evt.getKeyChar() == KeyEvent.VK_2) {
			farther(this.current);
		}
		// Navigation among the shapes
		else if(evt.getKeyCode() == KeyEvent.VK_TAB) {
			unselectAll();
		       
			Shape si = model.getShape(0); 
			selection(si).select();
			model.first(si);
		}


		else if(evt.getKeyCode() == KeyEvent.VK_ESCAPE)
			unselectAll();
		 
		 
		else if(evt.getKeyCode() == KeyEvent.VK_C && evt.getModifiers() == KeyEvent.CTRL_MASK) {
			System.out.println();
				for (ListIterator<Shape> it = model.iterator() ; it.hasNext();) {
					Shape s = it.next();
					SelectionAttributes sa = (SelectionAttributes)s.getAttributes(SelectionAttributes.ID);
					if(sa.isSelected()) {
						selectedShape.add(s.clone());
					}
			}
		}
		else if(evt.getKeyCode() == KeyEvent.VK_X && evt.getModifiers() == KeyEvent.CTRL_MASK){
        	
    		for(int i = model.getShapes().size()-1 ; i > -1  ;i--) {
        		SelectionAttributes sa =(SelectionAttributes)model.getShape(i).getAttributes(SelectionAttributes.ID);
    			if(sa.isSelected()) {
    	            selectedShape.add(model.getShape(i).clone());
    				model.remove(model.getShape(i));
    			}
    			

			}
		}
		 
		 else if(evt.getKeyCode() == KeyEvent.VK_V && evt.getModifiers() == KeyEvent.CTRL_MASK){
	          for (int i = 0 ; i < selectedShape.size() ; i++) {
	        	  Shape sh = selectedShape.get(i).clone();
	            model.add(sh);
	            int tmp  = model.getShapes().indexOf(sh);
	            model.getShapes().get(tmp).register(new ShapesObserver((ShapesView) this.getView()));
	          }
	      }
		 else if(evt.getKeyCode()==KeyEvent.VK_S) {
			if(evt.isControlDown()) {
				String filename = JOptionPane.showInputDialog("Nom du fichier ?");
				if(filename != null) {
					System.out.println("Sauvegarde de votre dessin dans le fichier : "+filename);
					this.getModel().serializeShapes(filename);
				}
				else {
					System.out.println("Annulation de la sauvegarde");
				}
			}
		}
		 else if(evt.getKeyCode()==KeyEvent.VK_O) {
			if(evt.isControlDown()) {
				JFileChooser fc = new JFileChooser();
				
				int returnVal = fc.showOpenDialog(null);
				if(returnVal==JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
					this.getModel().deserializeShapes(file);
					System.out.println("Chargement du fichier de sauvegarde : "+file.getAbsolutePath());
				}
				else {
					System.out.println("Ouverture de fichier de sauvegarde annulée");
				}
			}
		} 
	}
		

	@Override
	public void keyReleased(KeyEvent evt)
	{
		if(evt.getKeyCode() == KeyEvent.VK_UP)
			this.upPressed = false;
		else if(evt.getKeyCode() == KeyEvent.VK_DOWN)
			this.downPressed = false;
		else if(evt.getKeyCode() == KeyEvent.VK_RIGHT)
			this.rightPressed = false;
		else if(evt.getKeyCode() == KeyEvent.VK_LEFT)
			this.leftPressed = false;
	}

	// PRIMARY ACTIONS  -----------------------------------------------------------------------------------------------------
	
	
    public ShapeModel getModel() {
    	return (ShapeModel)super.getModel();
    }
    
    /**
     * Returns the selection attribute of the specified shape.
     * @param s Shape from which you want the selection attribute.
     * @return The selection attribute of the specified shape.
     */
	private SelectionAttributes selection(Shape s) {
		return (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
	}
    
    /**
     * If a shape is selected, returns it, else returns null. If a shape is selected by one of its corner, indicates it in <em>handler</em>.
     * @param p Position of the cursor.
     * @return The shape selected, null otherwise.
     */
	private Shape getTarget(Point p) {
	SCollection model =this.getModel().getData();
	for(ListIterator<Shape> it = model.iterator(model.size()) ; it.hasPrevious();) {
			Shape s = it.previous();
			Rectangle bounds = s.getBounds();
			
			if(s.getTLhandler(bounds).contains(p)) {
				handler = Handler.TL;
				return s;
			}
			else if(s.getTRhandler(bounds).contains(p)) {
				handler = Handler.TR;
				return s;
			}
			else if(s.getBLhandler(bounds).contains(p)) {
				handler = Handler.BL;
				return s;
			}
			else if(s.getBRhandler(bounds).contains(p)) {
				handler = Handler.BR;
				return s;
			}
			else if(s.getBounds().contains(p)) {
				return s;
			}
		}
		
		handler = Handler.NULL;
		return null;
	}

	/**
	 * Selects all shapes from the model collection.
	 */
	private void selectAll() {
		for(Shape s : this.getModel().getData().getShapes()) {
			selection(s).select();
		}
	}
	
	/**
	 * Deselects all shapes from the model collection.
	 */
	private void unselectAll() {
		for(Shape s : this.getModel().getData().getShapes()) {
			selection(s).unselect();
		}
	}
	
	/**
	 * Deletes all selected shapes from the model collection.
	 */
	private void deleteSelected() {
		SCollection model = this.getModel().getData();
		for(int i = model.getShapes().size()-1 ; i > -1  ;i--) {
    		SelectionAttributes sa =(SelectionAttributes)model.getShape(i).getAttributes(SelectionAttributes.ID);
			if(sa.isSelected()) {
				model.remove(model.getShape(i));
			}
			
    	}
	}

	/**
	 * Translates all selected shapes from the model collection by an x and an y offset.
	 * @param dx x offset.
	 * @param dy y offset.
	 */
	private void translateSelected(int dx, int dy) {
		for(Shape s : this.getModel().getData().getShapes()) {
			if (selection(s).isSelected()) {
				s.translate(dx, dy);
			}
		}
	}
	
	/**
	 * Resizes all selected shapes from the model collection by an x and an y offset, by a specified corner handler.
	 * @param dx x offset.
	 * @param dy y offset.
	 * @param handler Corner handler by which the selected forms are resized.
	 */
	private void resizeSelected(int dx, int dy, int handler) {
		for(Shape s : this.getModel().getData().getShapes()) {
			if(selection(s).isSelected())
				s.resize(this.lastPoint, dx, dy, handler);
		}
	}
	
	/**
	 * Rotates all selected shapes from the model collection.
	 */
	private void rotateSelected() {
		for(Shape s : this.getModel().getData().getShapes()) {
			if(selection(s).isSelected())
				s.rotate();
		}
	}
	
	/**
	 * Puts the specified shape one step closer in the foreground. 
	 * @param s Shape to put one step closer in the foreground.
	 */
	private void closer(Shape s) {
		if(s != null) this.getModel().getData().closer(s);
	}
	
	/**
	 * Puts the specified shape one step farther in the background. 
	 * @param s Shape to put one step farther in the background.
	 */
	private void farther(Shape s) {
		if(s != null) this.getModel().getData().farther(s);
	}
	
	// LASSO -----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Creates a lasso selector, whose upper-left corner is specified as a (x, y) coordinates and whose width and height are specified by the arguments of the same name.
	 * @param x The specified x coordinate. 
	 * @param y The specified y coordinate.
	 * @param width The width of the lasso.
	 * @param height The height of the lasso.
	 */
	private void setLasso(int x, int y, int width, int height) {
		this.lasso = new Rectangle(x, y, width, height);
	}
	
	/**
	 * Remove the lasso selector.
	 */
	private void removeLasso() {
		this.lasso = null;
	}
	
	/**
	 * Returns the absolute bounds of the lasso selector.
	 * @return The absolute bounds of the lasso selector.
	 */
	public Rectangle getLasso() {
		return Utils.getAbsoluteBounds(this.lasso);
	}
}
