package graphics.shapes.ui.component;

import javax.swing.JButton;
import javax.swing.JTextField;

import graphics.shapes.ui.BannerController;

public class TextEntry extends JTextField {

	private JButton ok;
	private JButton abort;
	private static final String OK = "Ok";
	private static final String ABORT = "Abort";

	public TextEntry(BannerController contr) {
		super();
		this.ok = new JButton(OK);
		this.abort = new JButton(ABORT);
		init(contr);
	}

	private void init(BannerController contr) {

		this.setColumns(20);
		this.ok.addActionListener(contr.closePopAndSetText());
		this.abort.addActionListener(contr.closePop());

	}

	public JButton getOk() {
		return this.ok;
	}

	public JButton getAbort() {
		return this.abort;
	}

}
