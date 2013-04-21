import java.awt.Point;

public class Grid implements GridInterface {

	private Node[] grid;
	public Node getNode(Point c) {
		for(Node n: grid){
			if(n.getPosition().equals(c))
				return n;
		}
		return null;
	}

	public boolean hasNode(Point c) {
		if(getNode(c) == null)
			return false;
		return true;
	}

	public Node[] getNeighbors(Node n) {
		return n.getConnections();
	}

	// Returns length of the edge connecting 2 nodes
	// Returns 0 if there is no edge connecting them
	public int getEdgeLength(Node a, Node b) {
		for(Node n: grid){
			for(Edge e: n.getEdges()){
				if(e.getEnd(a).equals(b) || e.getEnd(b).equals(a))
					return e.getLength();
			}
		}
		return 0;
	}

	@Override
	public void createStandard(int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createRandom(int size) {
		// TODO Auto-generated method stub

	}

}
