package graphics.shapes;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Observer;

public class ShapeModel{
	/** SCollection containing the exhistive list of all the existing shapes. */
	private SCollection model;
	
	/**
	 * Construct a ShapeModel by creating default shapes.
	 */
	public ShapeModel() {
		this.buildModel();
	}
	
	/**
	 * Creates default shapes.
	 */
	private void buildModel() {
		this.model = new SCollection();
		this.model.addAttributes(new SelectionAttributes());

		SRectangle r = new SRectangle(new Point(20,20),20,30);
		r.addAttributes(new ColorAttributes(true,false,Color.BLUE,Color.BLUE));
		r.addAttributes(new SelectionAttributes());
		
		this.model.add(r);

		SOval c = new SOval(new Point(70,20),20, 25);
		c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.BLUE));
		c.addAttributes(new SelectionAttributes());
		this.model.add(c);

		SText t = new SText(new Point(120,20),"hello");
		t.addAttributes(new ColorAttributes(true,true,Color.YELLOW,Color.BLUE));
		t.addAttributes(new FontAttributes());
		t.addAttributes(new SelectionAttributes());
		this.model.add(t);

		SCollection sc = new SCollection();
		sc.addAttributes(new SelectionAttributes());
		r = new SRectangle(new Point(100,90),30,30);
		r.addAttributes(new ColorAttributes(true,false,Color.MAGENTA,Color.BLUE));
		r.addAttributes(new SelectionAttributes());
		sc.add(r);
		c = new SOval(new Point(150,100),20, 25);
		c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.DARK_GRAY));
		c.addAttributes(new SelectionAttributes());
		sc.add(c);
		t = new SText(new Point(200,200),"fromage");
		t.addAttributes(new ColorAttributes(true,true,Color.RED,Color.MAGENTA));
		t.addAttributes(new FontAttributes());
		t.addAttributes(new SelectionAttributes());
		sc.add(t);
		this.model.add(sc);
	}
	
	/**
	 * Returns the SCollection containing the exhaustive list of shapes.
	 * @return The SCollection containing the exhaustive list of shapes.
	 */
	public SCollection getData() {
		return this.model;
	}
	
	/**
	 * Sets the observer for all shapes.
	 * @param o The observer.
	 */
	public void register(Observer o) {
		for(Shape s:this.getData().getShapes()) {
			s.register(o);
		}
	}

	/**
	 * Adds a shape to the default list of shapes.
	 * @param s Shape to add to the default list of shapes.
	 */
	public void add(Shape s) {
		this.model.add(s);	
	}
	
	public void serializeShapes(String filename) {
		SCollection shapes = this.model;
		try
	    {    
	        //Saving of object in a file 
	        FileOutputStream file = new FileOutputStream("saves/"+filename); 
	        ObjectOutputStream out = new ObjectOutputStream(file); 
	        
	        // Method for serialization of object 
	        out.writeObject(shapes); 
	              
	        out.close(); 
	        file.close(); 
	          
	        System.out.println("Object has been serialized"); 
	
	    } 
	      
	    catch(IOException ex) 
	    { 
	        ex.printStackTrace(); 
	    } 
	}
	
	public void deserializeShapes(File f) {
		SCollection shapes = this.model;
		shapes.empty();
		SCollection newShapes;
		// Deserialization 
	    try
	    {    
	        // Reading the object from a file 
	        FileInputStream file = new FileInputStream(f.getAbsolutePath()); 
	        ObjectInputStream in = new ObjectInputStream(file); 
	          
	        // Method for deserialization of object 
	        newShapes = (SCollection)in.readObject(); 
	          
	        in.close(); 
	        file.close(); 
	          
	        System.out.println("Object has been deserialized "); 
	        
	        for(Shape s : newShapes.getShapes()) {
	        	shapes.add(s);
	        }
	    } 
	      
	    catch(IOException ex) 
	    { 
	        ex.printStackTrace(); 
	    } 
	      
	    catch(ClassNotFoundException ex) 
	    { 
	        ex.printStackTrace(); 
	    } 
	    //this.invalidate();
	}
	
	
	
}
