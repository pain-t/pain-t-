package graphics.shapes.ui.component;


import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import graphics.shapes.ui.BannerController;

public class ColorChooser extends JColorChooser {
	
	//TODO add LABEL 
	private JButton ok;
	private JButton abort;
	
	
	private static final String OK =  "Ok";
	private static final String ABORT =  "Abort";

	
	public ColorChooser(BannerController contr) {
		super();
		this.ok = new JButton(OK);
		this.abort = new JButton(ABORT);
		init(contr);
	}
	
	
	private void init(BannerController contr) {
		//TODO a preview Panel ? 
		this.setPreviewPanel(new JPanel());
		//Only get the wanted color panel
		AbstractColorChooserPanel[] tab = this.getChooserPanels();
		this.removeChooserPanel(tab[4]);
		this.removeChooserPanel(tab[3]);
		this.removeChooserPanel(tab[2]);
		this.removeChooserPanel(tab[0]);
		//remove sliding bar
		this.getChooserPanels()[0].getComponent(0).setVisible(false);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(0,50,10,0);

		// add our components
		this.getChooserPanels()[0].add(this.ok,c);
		c.gridx = 3;
		c.gridy = 2;
		c.insets = new Insets(0,0,10,50);
		this.getChooserPanels()[0].add(this.abort,c);
		
		this.ok.addActionListener(contr.closePopAndSetColor());
		this.abort.addActionListener(contr.closePop());
		
	}
	
}