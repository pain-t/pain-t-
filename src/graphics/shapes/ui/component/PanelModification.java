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

public class PanelModification extends JPanel {

	/** The combobox which contains all font family. */
	private JComboBox<String> fontFamily;
	/** The combobox which contains all font size. */
	private JComboBox<Integer> fontSize;
	/** The button which control the color of the font. */
	private ButtonColor btnc;
	/** The popup which will contains the Color picker. */
	private JPopupMenu jpopupText;
	/** The minimal size displayed in the font size combobox. */
	private final int MINSIZE = 10;
	/** The maximal size displayed in the font size combobox. */
	private final int MAXSIZE = 40;
	/** The step */
	private final int STEP = 2;

	/**
	 * Creates the modification panel.
	 * 
	 * @param contr
	 *            The controller which contains the action to do for the button
	 *            color.
	 */
	public PanelModification(BannerController contr) {
		super();
		this.btnc = new ButtonColor(Utils.getIcon(Utils.TEXT), contr.doPrint());
		this.jpopupText = new JPopupMenu();
		this.fontFamily = new JComboBox<String>();
		this.fontSize = new JComboBox<Integer>();
		this.setInterface(contr);
	}

	/**
	 * Sets the modification panel.
	 * 
	 * @param contr
	 *            The controller which contains the action to do for the button
	 *            color.
	 */
	private void setInterface(BannerController contr) {
		// get all font
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts();
		for (Font f : fonts) {
			this.fontFamily.addItem(f.getFontName());
		}

		// set all fontsize
		for (int i = MINSIZE; i < MAXSIZE; i += STEP) {
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
		JPanel text = new JPanel(new GridLayout(2, 1));
		text.add(this.fontFamily);
		text.add(this.fontSize);
		this.fontFamily.addActionListener(contr.updateBox());
		this.fontSize.addActionListener(contr.updateBox());

		// add components to the panel
		this.add(text);
		this.add(color);
	}

	/**
	 * Returns the Popup which contains the color picker.
	 * 
	 * @return The popup which contains the color picker.
	 */
	public JPopupMenu getJpopupText() {
		return this.jpopupText;
	}

	/**
	 * Returns the button which contains the color of the selected shapes.
	 * 
	 * @return
	 */
	public ButtonColor getBtnc() {
		return this.btnc;
	}

	/**
	 * Returns the combobox which contains all the font family.
	 * 
	 * @return The combobox which contains all the font family.
	 */
	public JComboBox<String> getFontFamilyBox() {
		return this.fontFamily;
	}

	/**
	 * Returns the combobox which contains all the font size.
	 * 
	 * @return The combobox which contains all the font size.
	 */
	public JComboBox<Integer> getFontSizeBox() {
		return this.fontSize;
	}

}