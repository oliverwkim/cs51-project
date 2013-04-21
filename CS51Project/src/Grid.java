import java.awt.Point;

public class Grid implements GridInterface {

	protected Node[][] grid;
	protected int xLength;
	protected int yLength;
	
	public Node getNode(Point c) {
		int tempX = (int) c.getX();
		int tempY = (int) c.getY();
		if(tempX < xLength && tempY < yLength){
			return grid[tempX][tempY];
		}
		return null;
		/*for(Node n: grid){
			if(n.getPosition().equals(c))
				return n;
		}
		return null; */
	}

	public boolean hasNode(Point c) {
		return (getNode(c) != null);
	}

	public Node[] getNeighbors(Node n) {
		return n.getConnections();
	}

	// Returns length of the edge connecting 2 nodes
	// Returns 0 if there is no edge connecting them
	public int getEdgeLength(Node a, Node b) {
		for(Edge e: a.getEdges()){
			if(e.getEnd(a).equals(b))
				return e.getLength();
		}
		return 0;
	}
	
	public boolean linkNodes(Node a, Node b, int length){
		if(!(a.connectionExists(b) || b.connectionExists(a)))
			return false;
		else{
			a.addConnection(b, length);
			b.addConnection(a, length);
			return true;
		}
	}

	@Override
	public void createStandard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void createRandom() {
		// TODO Auto-generated method stub

	}

}
