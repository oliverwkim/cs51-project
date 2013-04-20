import java.awt.Point;
public interface gridInterface {
	// Used to get specific Nodes from inside a grid
	Node getNode(Point c);

	void createStandard(int size);
	void createRandom(int size);
}