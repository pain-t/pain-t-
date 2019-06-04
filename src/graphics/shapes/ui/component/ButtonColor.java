package graphics.shapes.ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonColor extends JButton {

	/** The displayed color. */
	private Color current;
	/** The default color of the displayed color. */
	private static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
	/** Spaces for the paintComponent method. */
	private static final int SPACE5 = 5;
	/** Spaces for the paintComponent method. */
	private static final int SPACE10 = 10;
	/** Spaces for the paintComponent method. */
	private static final int SPACE20 = 20;
	/** Spaces for the paintComponent method. */
	private static final int SPACE40 = 40;
	/** Spaces for the paintComponent method. */
	private static final int DEFAULTPOS = 0;
	/** The name of the cursor. */
	private static final String CURS_NAME = "CURSOR";

	/**
	 * 
	 * @param icon
	 *            The displayed icon.
	 * @param c
	 *            The action to do when it is pressed.
	 */
	public ButtonColor(Icon icon, ActionListener c) {
		super(icon);
		this.current = DEFAULT_COLOR;
		init(c);
	}

	/**
	 * Initializes the button.
	 * 
	 * @param c
	 *            The action to do when it is pressed.
	 */
	private void init(ActionListener c) {
		this.setPreferredSize(new Dimension(SPACE40, this.getPreferredSize().height + SPACE20));
		this.addActionListener(c);
		this.cursor();

	}

	/**
	 * Sets the icon on the cursor when over.
	 */
	private void cursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.setCursor(toolkit.createCustomCursor(((ImageIcon) this.getIcon()).getImage(),
				new Point(DEFAULTPOS, DEFAULTPOS), CURS_NAME));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.clearRect(DEFAULTPOS, DEFAULTPOS, (int) this.getPreferredSize().getWidth(),
				(int) this.getPreferredSize().getHeight());
		g2.setColor(Color.black);
		// draws the limits of the button.
		g2.drawRect(DEFAULTPOS, DEFAULTPOS, getWidth(), getHeight());

		final int centerX = this.getWidth() / 2;
		final int widthColorRectangle = this.getWidth() - (SPACE10);
		final int posXColorRectangle = centerX - (widthColorRectangle / 2);
		final int posYColorRectangle = this.getHeight() - SPACE20;
		final int heightColorRectangle = this.getWidth() - SPACE20 - SPACE10;

		// draws the current color.
		g2.setColor(current);
		g2.fillRect(posXColorRectangle, posYColorRectangle, widthColorRectangle, heightColorRectangle);
		// draws the bounds of the current color.
		g2.setColor(Color.black);
		g2.drawRect(posXColorRectangle, posYColorRectangle, widthColorRectangle, heightColorRectangle);
		// to not crash if there isn't Icon set.
		if (this.getIcon() != null) {
			this.getIcon().paintIcon(this, g2, centerX - (this.getIcon().getIconWidth() / 2), SPACE5);
		}

		g2.dispose();
	}

	/**
	 * Sets the displayed color.
	 * 
	 * @param c
	 *            The displayed color.
	 */
	public void setColor(Color c) {
		this.current = c;
	}

}
