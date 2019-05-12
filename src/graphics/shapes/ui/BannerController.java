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
				System.out.println(((Component)e.getSource()).getParent().getClass());
				System.out.println(((Component)e.getSource()).getParent().getParent().getClass());
				System.out.println(((Component)e.getSource()).getParent().getParent().getParent().getClass());
				System.out.println(((Component)e.getSource()).getParent().getParent().getParent().getParent().getClass());

				if(c.equals(getView().getJPopupFill().getComponent(0)))
					getView().getJPopupFill().setVisible(false);
				else if(c.equals(getView().getJPopupStroke().getComponent(0)))
					getView().getJPopupStroke().setVisible(false);
			}
		};
	}
	
	public ActionListener closePopAndSetColor() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorChooser c = (ColorChooser)((Component)e.getSource()).getParent().getParent().getParent().getParent();
				if(c.equals(getView().getJPopupFill().getComponent(0))) {
					getView().getJPopupFill().setVisible(false);
					getView().getFillBtn().setColor(((ColorChooser)getView().getJPopupFill().getComponent(0)).getColor());

				}
				else if(c.equals(getView().getJPopupStroke().getComponent(0))) {
					getView().getJPopupStroke().setVisible(false);
					getView().getStrokeBtn().setColor(((ColorChooser)getView().getJPopupStroke().getComponent(0)).getColor());
				}
				//TODO setColor btn & selected shape
			}
		};
	}
}
