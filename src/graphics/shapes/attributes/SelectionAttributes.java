package graphics.shapes.attributes;

public class SelectionAttributes extends Attributes {

	/** Id of the SelectionAttributes. */
	public final static String ID = "SelectionAttributes";
	/** True if the shape is selected, false otherwise. */
	private boolean selected;

	// --------------------------------------------------------------------

	/**
	 * Constructs a SelectionAttribute.
	 * 
	 * @param selected
	 *            True if the shape is selected, false otherwise.
	 */
	public SelectionAttributes(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Constructs a default SelectionAttribute (selection set to false).
	 */
	public SelectionAttributes() {
		this(false);
	}

	// --------------------------------------------------------------------

	/**
	 * Returns true if the shape is selected, false otherwise.
	 * 
	 * @return True if the shape is selected, false otherwise.
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * Set the selection to true.
	 */
	public void select() {
		this.selected = true;
		this.notifyObserver();
	}

	/**
	 * Set the selection to false.
	 */
	public void unselect() {
		this.selected = false;
		this.notifyObserver();
	}

	/**
	 * Toggle the selection.
	 */
	public void toggleSelection() {
		this.selected = !this.selected;
		this.notifyObserver();
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public Attributes clone() {
		return new SelectionAttributes(this.selected);
	}
}
