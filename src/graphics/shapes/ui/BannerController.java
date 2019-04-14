package graphics.shapes.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
}
