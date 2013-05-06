public class SquareNode extends Node {

	// Maximum number of nodes this can be connected to
	private static final int maxConnections = 8;

	public SquareNode(int x, int y, boolean pass){
		super(x, y, pass, maxConnections);
	}
	
	public int getMaxConnections(){
		return maxConnections;
	}
	
	public boolean addConnection(Node n, int length) {
		// Check if connection already exists
		if(connectionExists(n))
			return false;
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
		if(!visible){
			for (Node t: shadowConnections){
				if(t != null && t.equals(n))
					return true;
			}
			return false;
		}
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
	
	public void setShadows(Node[] neighbors){
		shadowConnections.clear();
		shadowEdges.clear();
		int length;
		for(Node n: neighbors){
			if(n.getPosition().getX() == position.getX() || n.getPosition().getY() == position.getY())
				length = 10;
			else
				length = 14;
			shadowConnections.add(n);
			shadowEdges.add(new Edge(this, n, length));
		}
	}
	
	public void removeShadow(Node n){
		shadowConnections.remove(n);
		shadowEdges.remove(n);
	}
}
