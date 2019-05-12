package graphics.shapes;

public interface ShapeVisitor {
	public void visitRectangle(SRectangle rect);
	public void visitOval(SOval oval);
	public void visitText(SText text);
	public void visitCollection(SCollection collection);
}
