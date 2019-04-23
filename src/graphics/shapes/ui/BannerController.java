package graphics.shapes.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import graphics.shapes.ui.component.ColorChooser;
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
				ColorChooser c = (ColorChooser)((Component)e.getSource()).getParent().getParent().getParent().getParent();
				if(c.equals(((BannerView)getView()).getJpopupFill().getComponent(0))) {
					((BannerView)getView()).getJpopupFill().setVisible(false);
					((BannerView)getView()).getFillBtn().setColor(((ColorChooser)((BannerView)getView()).getJpopupFill().getComponent(0)).getColor());

				}
				else if(c.equals(((BannerView)getView()).getJpopupStroke().getComponent(0))) {
					((BannerView)getView()).getJpopupStroke().setVisible(false);
					((BannerView)getView()).getStrokeBtn().setColor(((ColorChooser)((BannerView)getView()).getJpopupStroke().getComponent(0)).getColor());
				}
				//TODO setColor btn & selected shapes
			}
		};
	}
}
