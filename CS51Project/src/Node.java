import java.awt.Point;
public abstract class Node {
	protected Point position;
	protected boolean passable;
	protected int cost;

	// Array of all Nodes this Node is connected to
	protected Node[] connections;
	
	// Array of all this Node's edges
	protected Edge[] edges;

	public Node(int maxConnections){
		position = new Point(0,0);
		passable = true;
		cost = 1;
		connections = new Node[maxConnections];
	}

	public Node(int x, int y, boolean pass, int maxConnections){
		position = new Point(x, y);
		passable = pass;
		cost = 1;
		connections = new Node[maxConnections];
	}

	public Node(int x, int y, int c, boolean pass, int maxConnections){
		position = new Point(x, y);
		passable = pass;
		cost = c;
		connections = new Node[maxConnections];
	}

	// Links this node to another node, with path length of length between them. 
	// Returns true if successful, false otherwise
	abstract boolean linkNode(Node n, int length);

	Node[] getConnections(){
		return connections;
	}
	Edge[] getEdges(){
		return edges;
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
	
	boolean equals(Node n){
		return (position.equals(n.getPosition())) && (passable == n.isPassable())
				&& (cost == n.getCost());
	}

}
