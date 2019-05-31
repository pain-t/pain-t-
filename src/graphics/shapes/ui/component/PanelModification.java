package graphics.shapes.ui.component;

import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import graphics.shapes.ui.BannerController;
import utils.Utils;

public class PanelModification extends JPanel{
	
	private JComboBox<String> fontFamily;
	private JComboBox<Integer> fontSize;
	private ButtonColor btnc;
    private JPopupMenu jpopupText;
	private final String DISPLAY_FONT_UNIT = " pt";
	private final int MINSIZE = 10;
	private final int MAXSIZE = 40;
	private final int STEP = 2;

	
	public PanelModification(BannerController contr) {
		super();
		this.btnc = new ButtonColor(Utils.getIcon(Utils.TEXT),contr.doPrint());
	    this.jpopupText = new JPopupMenu();
		this.fontFamily = new JComboBox<String>();
		this.fontSize = new JComboBox<Integer>();
		this.setInterface(contr);
	}

	private void setInterface(BannerController contr) {
		// get all font
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] fonts = e.getAllFonts(); 
		for(Font f: fonts) {
			this.fontFamily.addItem(f.getFontName());
		}
		
		//set all fontsize
		for(int i = MINSIZE ; i < MAXSIZE; i+=STEP) {
			this.fontSize.addItem(i);
		}
		jpopupText.add(new ColorChooser(contr));
		this.btnc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jpopupText.show((Component) e.getSource(), 0, 0);
			}
		});
		
		// Panel to display correctly
		JPanel color = new JPanel();
		color.add(this.btnc);
		JPanel text = new JPanel(new GridLayout(2,1));
		text.add(this.fontFamily);
		text.add(this.fontSize);
		this.fontFamily.addActionListener(contr.updateBox());
		this.fontSize.addActionListener(contr.updateBox());

		//add components to the panel
		this.add(text);
		this.add(color);
	}

	public JPopupMenu getJpopupText() {
		return this.jpopupText;
	}

	public ButtonColor getBtnc() {
		return this.btnc;
	}

	public JComboBox<String> getFontFamilyBox() {
		return this.fontFamily;
	}

	public JComboBox<Integer> getFontSizeBox() {
		return this.fontSize;
	} 
	
}