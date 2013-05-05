import java.awt.Point;
import java.util.ArrayList;

public abstract class Node {
	
	protected Point position;
	protected boolean passable;
	protected boolean visible;

	/* Arrays to store all Nodes this Node is connected to, as well as all the corresponding edges
	 * Note that connections and edges should always be the same size, and accessing the same index i
	 * in either of them will yield a Node n and the edge connecting this node to Node n.
	 * Consequentially, all methods modifying them must modify them together, never separately.
	 */
	protected ArrayList<Node> connections;
	protected ArrayList<Edge> edges;
	
	protected Node parent;
	
	protected int fScore = 0;
	protected int gScore = 2000000;
	protected ArrayList<Integer> kScore = null;
	protected int rhsScore = 2000000;
	
	/* These variables will contain connections to all neighbors.
	 * They will be returned in the event that the node is not visible
	 * In other words, when a nonvisible node is asked for information, it will
	 * state that it is connected to all of its neighbors, and that it is passable.
	 */
	protected ArrayList<Node> shadowConnections;
	protected ArrayList<Edge> shadowEdges;

	public Node(int x, int y, boolean pass, int maxConnections){
		position = new Point(x, y);
		passable = pass;
		connections = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		visible = true;
		shadowConnections = new ArrayList<Node>();
		shadowEdges = new ArrayList<Edge>();
	}

	/* Links this node to another node, with path length of length between them. 
	 * Returns true if successful, false otherwise 
	 * Linking of nodes should be done through the grid method linkNodes,
	 * since it requires the connection is added to both nodes 
	 */
	abstract boolean addConnection(Node n, int length);
	
	// Checks if there is a connection to given node
	abstract boolean connectionExists(Node n);
	
	// Deletes the connection to the given node. Returns the edge that has been deleted, or null if it did not exist
	abstract Edge deleteConnection(Node n);
	
	// Pass in all neighbors, so that connections can be established for the shadow variables
	abstract void setShadows(Node[] neighbors);
	
	// Removes given node from the shadow connections
	// This is used to eliminate inconsistencies in information when visible and nonvisible nodes adjacent
	abstract void removeShadow(Node n);

	// These methods return all of the connections and edges
	Node[] getAllConnections(){
		return connections.toArray(new Node[connections.size()]);
	}
	Edge[] getAllEdges(){
		return edges.toArray(new Edge[edges.size()]);
	}
	
	// These two methods return only the connections to passable nodes
	// If the node is not visible, they will return the shadow values, which assume connections to all neighbors
	public Node[] getConnections(){
		if(!visible)
			return shadowConnections.toArray(new Node[shadowConnections.size()]);
		ArrayList<Node> result = new ArrayList<Node>();
		for(int i = 0; i < connections.size(); i++){
			Node n = connections.get(i);
			if(n.isPassable() && n != null)
				result.add(n);
		}
		return result.toArray(new Node[result.size()]);
	}

	public Edge[] getEdges(){
		if(!visible)
			return shadowEdges.toArray(new Edge[shadowEdges.size()]);
		ArrayList<Edge> result = new ArrayList<Edge>();
		for(Edge e: edges.toArray(new Edge[edges.size()])){
			if(e.getEnd(this).isPassable() && e != null)
				result.add(e);
		}
		return result.toArray(new Edge[result.size()]);
	}
	
	/* Returns all the potential edge connections that this node doesn't actually have
	 * i.e. all connections assumed to exist when node is not visible, but are seen to not exist when it is visible
	 * This is all the elements in shadowConnections that are not in connections
	 * NOTE: This will only return the newly discovered non-connections that have not already been discovered.
	 *       If this is Node A, then seeing Node B first and seeing the A-B connection does not exist means that
	 *       the A-B connection will be returned through Node B's method, but not Node A's.
	 */
	public Edge[] getNewEdges(){
		ArrayList<Edge> result = new ArrayList<Edge>();
		boolean shared;
		for(int i = 0; i < shadowEdges.size(); i++){
			shared = false;
			for(int j = 0; j < edges.size(); j++){
				if((edges.get(j)).equals(shadowEdges.get(i)))
					shared = true;
			}
			if(!shared)
				result.add(shadowEdges.get(i));
		}
		if(result.size() == 0)
			return null;
		return result.toArray(new Edge[result.size()]);
	}
	
	// Checks for equality of the nodes by checking their positions and passability
	boolean equals(Node n){
		if(n == null)
			return false;
		else
			return (position.equals(n.getPosition())) && (passable == n.isPassable());
	}
	
	// Rest of these are functions for setting and getting values
	double getDistance(Node n){
		return Math.sqrt(Math.pow(position.getX() - n.getPosition().getX(), 2) + 
				Math.pow(position.getY() - n.getPosition().getY(), 2));
	}
	void setPosition(int x, int y){
		position = new Point(x, y);
	}
	Point getPosition(){
		return position;
	}
	void setPassable(boolean value){
		passable = value;
	}
	boolean isPassable(){
		return passable;
	}
	
	int getFScore(){
		return fScore;
	}
	
	void setFScore(int score){
		fScore = score;
	}
	
	int getGScore(){
		return gScore;
	}
	
	void setGScore(int score){
		gScore = score;
	}
	
	Node getParent(){
		return parent;
	}
	
	void setParent(Node n){
		parent = n;
	}
	
	ArrayList<Integer> getKScore(){
		return kScore;	
	}
	
	void setKScore(ArrayList<Integer> score){
		kScore = score;
	}
	
	int getRhsScore(){
		return rhsScore;
	}
	
	void setRhsScore(int score){
		rhsScore = score;
	}
	
	boolean getVisibility(){
		return visible;
	}
	
	void setVisibility(boolean value){
		visible = value;
	}
}
