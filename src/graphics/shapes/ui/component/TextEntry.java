package graphics.shapes.ui.component;

import javax.swing.JButton;
import javax.swing.JTextField;

import graphics.shapes.ui.BannerController;

public class TextEntry extends JTextField  {
	/** the button to validate*/
	private JButton ok;
	/** the button to abort  */
	private JButton abort;
	
	/**The displayed value of the valid button.*/
	private static final String OK =  "Ok";
	/**The displayed value of the abort button.*/
	private static final String ABORT =  "Abort";
	
	
	/**
	 * Creates a TextEntry.
	 * @param contr The banner controller which have the function for the OK and ABORT button. 
	 */
	public TextEntry(BannerController contr) {
		super();
		this.ok = new JButton(OK);
		this.abort = new JButton(ABORT);
		init(contr);
	}
	
	
	/**
	 * Initializes the Text entry.
	 * @param contr The banner controller which have the function for the OK and ABORT button. 
	 */
	private void init(BannerController contr) {
		this.setColumns(20);
		this.ok.addActionListener(contr.closePopAndSetText());
	}
	
	
	/**
	 * returns the JButton ok.
	 * @return the JButton ok.
	 */
	public JButton getOk( ) {
		return this.ok;
	}
	
	/**
	 * returns the JButton abort.
	 * @return the JButton abort.
	 */
	public JButton getAbort( ) {
		return this.abort;
	}

}
