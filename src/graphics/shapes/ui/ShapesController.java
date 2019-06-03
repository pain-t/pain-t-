package graphics.shapes.ui;

import java.awt.Point;
import java.awt.Rectangle;
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

	private boolean upDown;
	private boolean downDown;
	private boolean leftDown;
	private boolean rightDown;
	
	private Point lastPoint;
	private ArrayList <Shape> selectedShape;
	
	private Shape current;
	
	private Rectangle lasso;
	
	private static enum Handler { TL, TR, BL, BR, NULL };
	private static Handler handler = Handler.NULL;
	
	// -----------------------------------------------------------------------------------------------------------------------
	
	public ShapesController(Object model) {
		super(model);
		this.lastPoint = new Point();
		selectedShape = new ArrayList<Shape>();
	}
	

    // -----------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void mouseDragged(MouseEvent evt) {
		if(this.lasso == null) {
			int dx = evt.getX() - this.lastPoint.x;
			int dy = evt.getY() - this.lastPoint.y;
			
			this.lastPoint = evt.getPoint();
			
			if(!handler.equals(Handler.NULL))
				resizeSelected(dx, dy, handler.ordinal());
			
			else
				this.translateSelected(dx, dy);
		}
		
		else {
			this.lasso.setSize(this.lastPoint.x - this.lasso.x, this.lastPoint.y - this.lasso.y);
			this.lastPoint = evt.getPoint();
			Rectangle r = (Rectangle)this.lasso.clone();
			r = Utils.getAbsoluteBounds(r);
			
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
				if(!selection(this.current).isSelected()) {
					unselectAll();
					selection(this.current).select();;
					getModel().getData().first(this.current);
				}
			}
			else {
				unselectAll();
				setLasso(e.getX(), e.getY(), 0, 0);
			}
					
		}
		else {
			if(this.current != null)
				selection(this.current).toggleSelection();
			else
				setLasso(e.getX(), e.getY(), 0, 0);
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.lasso != null) {
			removeLasso();
			this.getView().invalidate();		
		}
		if(!handler.equals(Handler.NULL)) {
			handler = Handler.NULL;
		}
	}
	

	public void keyPressed(KeyEvent evt) {
  		SCollection model = this.getModel().getData();
		 if(evt.getKeyCode() == KeyEvent.VK_DELETE)
			deleteSelected();
		
		else if(evt.getKeyCode() == KeyEvent.VK_P)
			rotateSelected();
		
		else if(evt.getKeyCode() == KeyEvent.VK_UP) {
			if(this.leftDown) translateSelected(-1, -1);
			else if(this.rightDown) translateSelected(1, -1);
			else translateSelected(0, -1);
			this.upDown = true;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
			if(this.leftDown) translateSelected(-1, 1);
			else if(this.rightDown) translateSelected(1, 1);
			else translateSelected(0, 1);
			this.downDown = true;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(this.upDown) translateSelected(1, -1);
			else if(this.downDown) translateSelected(1, 1);
			else translateSelected(1, 0);
			this.rightDown = true;
		}
		else if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
			if(this.upDown) translateSelected(-1, -1);
			else if(this.downDown) translateSelected(-1, 1);
			else translateSelected(-1, 0);
			this.leftDown = true;
		}
		
		 
		//version Manon Ctrl-A
		//else if ((evt.getKeyChar() == KeyEvent.VK_A) && ((evt.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0))
		//	selectAll();
		 
		else if (evt.getKeyCode() == KeyEvent.VK_A && evt.getModifiers() == KeyEvent.CTRL_MASK) {
			selectAll();
		}
		 
		
		else if (evt.getKeyChar() == KeyEvent.VK_1) {
			closer(this.current);
		}
		else if (evt.getKeyChar() == KeyEvent.VK_2) {
			farther(this.current);
		}
		
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
			this.upDown = false;
		else if(evt.getKeyCode() == KeyEvent.VK_DOWN)
			this.downDown = false;
		else if(evt.getKeyCode() == KeyEvent.VK_RIGHT)
			this.rightDown = false;
		else if(evt.getKeyCode() == KeyEvent.VK_LEFT)
			this.leftDown = false;
	}

	// ACTIONS PRIMAIRES -----------------------------------------------------------------------------------------------------
	
    public ShapeModel getModel() {
    	return (ShapeModel)super.getModel();
    }
    
	private SelectionAttributes selection(Shape s) {
		return (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
	}
    
    
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
			else if(s.getBounds().contains(p))
				return s;
		}
		
		handler = Handler.NULL;
		return null;
	}

	private void selectAll() {
		for(Shape s : this.getModel().getData().getShapes()) {
			selection(s).select();
		}
	}
	
	private void unselectAll() {
		for(Shape s : this.getModel().getData().getShapes()) {
			selection(s).unselect();
		}
	}
	
	private void deleteSelected() {
		SCollection model = this.getModel().getData();
		for(int i = model.getShapes().size()-1 ; i > -1  ;i--) {
    		SelectionAttributes sa =(SelectionAttributes)model.getShape(i).getAttributes(SelectionAttributes.ID);
			if(sa.isSelected()) {
				model.remove(model.getShape(i));
			}
			
    	}
	}


	private void translateSelected(int dx, int dy) {
		for(Shape s : this.getModel().getData().getShapes()) {
			if (selection(s).isSelected()) {
				s.translate(dx, dy);
			}
		}
	}
	
	
	private void resizeSelected(int dx, int dy, int handler) {
		for(Shape s : this.getModel().getData().getShapes()) {
			if(selection(s).isSelected())
				s.resize(this.lastPoint, dx, dy, handler);
		}
	}
	
	private void rotateSelected() {
		for(Shape s : this.getModel().getData().getShapes()) {
			if(selection(s).isSelected())
				s.rotate();
		}
	}
	
	private void closer(Shape s) {
		if(s != null) this.getModel().getData().closer(s);
	}
	
	private void farther(Shape s) {
		if(s != null) this.getModel().getData().farther(s);
	}
	
	// LASSO -----------------------------------------------------------------------------------------------------------------
	
	private void setLasso(int x, int y, int width, int height) {
		this.lasso = new Rectangle(x, y, width, height);
	}
	
	private void removeLasso() {
		this.lasso = null;
	}
	
	public Rectangle getLasso() {
		return Utils.getAbsoluteBounds(this.lasso);
	}
}
