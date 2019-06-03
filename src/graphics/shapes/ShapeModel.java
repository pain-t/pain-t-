package graphics.shapes;

import java.awt.Color;
import java.awt.Point;

import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Observer;

public class ShapeModel{
	private SCollection model;
	
	public ShapeModel() {
		this.buildModel();
	}
	
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

	public void add(Shape s) {
		this.model.add(s);
		
	}
	
	
	
}
