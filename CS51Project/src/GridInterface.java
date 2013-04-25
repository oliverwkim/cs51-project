import java.awt.Point;
public interface GridInterface{
	
	// Returns node at given coordinates
	// Returns null if there is no node there, or the point is outside the grid
	Node getNode(Point c);
	
	// Checks for existence at that point
	boolean hasNode(Point c);	
	
	/* Links the two nodes with the appropriate length. 
	 * Returns true on success, false on failure
	 * DO NOT directly access the Nodes' methods. This should be the method used to connect nodes 
	 */
	boolean linkNodes(Node a, Node b, int length);
	
	// Returns array containing all of a node's neighbors that it's connected to
	Node[] getNeighbors(Node n);
	
	// Returns length of edge connecting two nodes
	// Returns 0 if they're not connected
	int getEdgeLength(Node a, Node b);
	
	/* Returns an array containing all of the nodes that are within sight range of the given node
	 * Uses Euclidean distance to determine which nodes are in range
	 */
	Node[] getVision(Node n, int sight);
	
	/* Methods for creating standard and random grids
	 * A standard grid has equal x and y lengths, and has all nodes connected to all neighbors
	 */
	void createStandard();
	void createRandom(Point start, Point end);
}