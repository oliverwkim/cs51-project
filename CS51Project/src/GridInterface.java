import java.awt.Point;
public interface GridInterface{
	// Used to get specific Nodes from inside a grid
	Node getNode(Point c);
	
	// Checks for existence at that point
	boolean hasNode(Point c);
	
	// Returns array containing all of a node's neighbors that it's connected to
	Node[] getNeighbors(Node n);
	
	// Returns length of edge connecting two nodes
	int getEdgeLength(Node a, Node b);
	
	// Methods for creating standard and random grids
	void createStandard(int size);
	void createRandom(int size);
}