public class SquareNode extends Node {

	// Maximum number of nodes this can be connected to
	private static final int maxConnections = 8;
	private int marker;

	
	public SquareNode(){
		super(maxConnections);
		marker = 0;
	}

	public SquareNode(int x, int y, boolean pass){
		super(x, y, pass, maxConnections);
		marker = 0;
	}

	public SquareNode(int x, int y, int cost, boolean pass){
		super(x, y, cost, pass, maxConnections);
		marker = 0;
	}
	
	public int getMaxConnections(){
		return maxConnections;
	}

	public boolean addConnection(Node n, int length) {
		if (marker < maxConnections){
			connections[marker] = n;
			edges[marker++] = new Edge(this, n, length);
			return true;
		}
		else
			return false;
	}
	
	public boolean connectionExists(Node n){
		for (Node t: connections){
			if(t.equals(n))
				return true;
		}
		return false;
	}

}
