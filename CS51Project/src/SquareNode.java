public class SquareNode extends Node {

	// Maximum number of nodes this can be connected to
	private static final int maxConnections = 8;

	public SquareNode(int x, int y, boolean pass){
		super(x, y, pass, maxConnections);
	}

	public SquareNode(int x, int y, int cost, boolean pass){
		super(x, y, cost, pass, maxConnections);
	}
	
	public int getMaxConnections(){
		return maxConnections;
	}

	public boolean addConnection(Node n, int length) {
		// Checks for empty spots, and fills them in if present
		for (int i = 0; i < connections.size(); i++){
			if(connections.get(i) == null){
				connections.set(i, n);
				edges.set(i, new Edge(this, n, length));
				return true;
			}
		}
		// If there were no empty spots and the array is at max size, cannot add any more
		if (connections.size() >= maxConnections){
			return false;
		}
		// Expand array size to include new element, if it is not yet max size
		connections.add(n);
		edges.add(new Edge(this, n, length));
		return true;
	}
	
	public boolean connectionExists(Node n){
		for (Node t: connections){
			if(t != null && t.equals(n))
				return true;
		}
		return false;
	}
	
	public Edge deleteConnection(Node n){
		for (int i = 0; i < connections.size(); i++){
			if (connections.get(i).equals(n)){
				connections.remove(i);
				return edges.remove(i);
			}
		}
		return null;
	}
}
