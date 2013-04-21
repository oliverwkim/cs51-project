import java.awt.Point;
public interface GridInterface{
	// Used to get specific Nodes from inside a grid
	Node getNode(Point c);
	Bool hasNode(Point c);
	void createStandard(int size);
	void createRandom(int size);
}