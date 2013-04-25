import java.awt.Point;
import java.util.ArrayList;

public abstract class Node {
	protected Point position;
	protected boolean passable;
	protected int cost;
	protected boolean visible;

	// Array of all Nodes this Node is connected to
	protected ArrayList<Node> connections;
	
	// Array of all this Node's edges
	protected ArrayList<Edge> edges;
	
	protected Node parent;
	
	protected int fScore = 0;
	protected int gScore = 0;
	protected int kScore = 0;

	public Node(int x, int y, boolean pass, int maxConnections){
		position = new Point(x, y);
		passable = pass;
		cost = 1;
		connections = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		visible = true;
	}

	public Node(int x, int y, int c, boolean pass, int maxConnections){
		position = new Point(x, y);
		passable = pass;
		cost = c;
		connections = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		visible = true;
	}

	/* Links this node to another node, with path length of length between them. 
	   Returns true if successful, false otherwise 
	   Linking of nodes should be done through the grid method linkNodes,
	   since it requires the connection is added to both nodes */
	abstract boolean addConnection(Node n, int length);
	
	// Checks if there is a connection to given node
	abstract boolean connectionExists(Node n);
	
	// Deletes the connection to the given node. Returns the edge that has been deleted, or null if it did not exist
	abstract Edge deleteConnection(Node n);

	Node[] getConnections(){
		Node[] result = connections.toArray(new Node[connections.size()]);
		return result;
	}
	Edge[] getEdges(){
		Edge[] result = edges.toArray(new Edge[edges.size()]);
		return result;
	}
	
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
	
	int getCost(){
		return cost;
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
	
	int getKScore(){
		return kScore;
	}
	
	void setKScore(int score){
		kScore = score;
	}
	
	boolean getVisibility(){
		return visible;
	}
	
	void setVisibility(boolean value){
		visible = value;
	}
	
	// Checks for equality of the nodes by checking their positions
	boolean equals(Node n){
		if(n == null)
			return false;
		return (position.equals(n.getPosition())) && (passable == n.isPassable())
				&& (cost == n.getCost());
	}

}
