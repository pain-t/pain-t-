package graphics.shapes;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import graphics.shapes.attributes.ColorAttributes;
import graphics.shapes.attributes.FontAttributes;
import graphics.shapes.attributes.SelectionAttributes;
import graphics.ui.Observer;

public class ShapeModel {
	
	private SCollection model;
	
	public ShapeModel() {
		this.buildModel();
	}
	
	private void buildModel() {
		this.model = new SCollection();
		this.model.addAttributes(new SelectionAttributes());

		SRectangle r = new SRectangle(new Point(10,10),20,30);
		r.addAttributes(new ColorAttributes(true,false,Color.BLUE,Color.BLUE));
		r.addAttributes(new SelectionAttributes());
		
		this.model.add(r);

		SCircle c = new SCircle(new Point(100,100),10);
		c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.BLUE));
		c.addAttributes(new SelectionAttributes());
		this.model.add(c);

		SText t = new SText(new Point(100,100),"hello");
		t.addAttributes(new ColorAttributes(true,true,Color.YELLOW,Color.BLUE));
		t.addAttributes(new FontAttributes());
		t.addAttributes(new SelectionAttributes());
		this.model.add(t);

		SCollection sc = new SCollection();
		sc.addAttributes(new SelectionAttributes());
		r= new SRectangle(new Point(20,30),30,30);
		r.addAttributes(new ColorAttributes(true,false,Color.MAGENTA,Color.BLUE));
		r.addAttributes(new SelectionAttributes());
		sc.add(r);
		c = new SCircle(new Point(150,100),20);
		c.addAttributes(new ColorAttributes(false,true,Color.BLUE,Color.DARK_GRAY));
		c.addAttributes(new SelectionAttributes());
		sc.add(c);
		this.model.add(sc);
	}
	
	public SCollection getData() {
		return this.model;
	}
	
	public void register(Observer o) {
		this.getData().getShapes().forEach(x->x.register(o));
		for(Shape s : this.getData().getShapes()) {
			SelectionAttributes sa = (SelectionAttributes) s.getAttributes(SelectionAttributes.ID);
			sa.register(o);
		}
	}
	
	
	
}
