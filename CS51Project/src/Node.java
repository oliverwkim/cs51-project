import java.awt.Point;
public abstract class Node {
	protected Point position;
	protected boolean passable;
	protected int cost;
	
	// Array of all Nodes this Node is connected to
	protected Node[] connections;
	
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
	
	// Adds the specified Node to connections
	abstract void linkNode(Node n);
	
	Node[] connectedNodes(){
		return connections;
	}
	void setCoordinates(int x, int y){
		position = new Point(x, y);
	}
	Point getCoordinates(){
		return position;
	}
	void setPassable(boolean value){
		passable = value;
	}
	boolean isPassable(){
		return passable;
	}

}
