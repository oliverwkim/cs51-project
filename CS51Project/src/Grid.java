import java.awt.Point;
import java.util.ArrayList;

public class Grid implements GridInterface {

	protected Node[][] grid;
	protected int xLength;
	protected int yLength;
	protected Node pos;
	
	// Path lengths for moving in cardinal and diagonal directions
	private final int cardinal = 10;
	private final int diagonal = 14;
	
	public Grid(int x, int y){
		xLength = x;
		yLength = y;
		grid = new Node[xLength][yLength];
	}
	
	public int getX(){
		return xLength;
	}
	
	public int getY(){
		return yLength;
	}
	
	public Node getPos(){
		return pos;
	}
	
	// Sets the position of the current marker. Used only for graphical display purposes
	public void setPos(Node s){
		pos = s;
	}
	
	
	public Node getNode(Point c) {
		int tempX = (int) c.getX();
		int tempY = (int) c.getY();
		if(tempX >= 0 && tempX < xLength && tempY >= 0 && tempY < yLength){
			return grid[tempX][tempY];
		}
		return null;
	}

	public Node getNode(int x, int y){
		if(x >= 0 && x < xLength && y >= 0 && y < yLength)
			return grid[x][y];
		return null;
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
		if(a == null || b == null)
			return 0;
		for(Edge e: a.getEdges()){
			if(e == null)
				continue;
			if(e.getEnd(a).equals(b))
				return e.getLength();
		}
		return 0;
	}
	
	public boolean linkNodes(Node a, Node b, int length){
		if(a.connectionExists(b) || b.connectionExists(a) || a == null || b == null)
			return false;
		else{
			a.addConnection(b, length);
			b.addConnection(a, length);
			return true;
		}
	}
	
	public Node[] getVision(Node n, int sight){
		int x = (int) n.getPosition().getX();
		int y = (int) n.getPosition().getY();
		ArrayList<Node> visible = new ArrayList<Node>();
		
		// Gets all the nodes within sight range of the current position
		for(int addX = -1 * sight; addX <= sight; addX++){
			for(int addY = -1 * sight; addY <= sight; addY++){
				Node temp = getNode(x + addX, y + addY);
				if(temp != null && (temp.getDistance(n) <= sight)){
					visible.add(temp);
				}
			}
		}

		Node[] update = visible.toArray(new Node[visible.size()]);
		visible.clear();
				
		for(Node i: update){
			if(i.getVisibility())
				continue;
			i.setVisibility(true);
			// If new vision uncovers that some connections do not exist,
			// change the nonvisible boundary nodes to reflect this, so there are no inconsistencies
			for(Node j: getAdjacent(i))
				if(!i.connectionExists(j))
					j.removeShadow(i);
			visible.add(i);
		}
		if(visible.size() == 0)
			return null;
		return visible.toArray(new Node[visible.size()]);
	}
	
	// Returns the adjacent nodes, regardless of whether a connection actually exists
	public Node[] getAdjacent(Node n){
		int x = (int) n.getPosition().getX();
		int y = (int) n.getPosition().getY();
		ArrayList<Node> neighbors = new ArrayList<Node>();
		for(int addX = -1; addX <= 1; addX++){
			for(int addY = -1; addY <= 1; addY++){
				Node temp = getNode(x + addX, y + addY);
				if(temp != null && (addX != 0 || addY != 0))
					neighbors.add(temp);
			}
		}
		return neighbors.toArray(new Node[neighbors.size()]);
	}

	
	
	/* Turns on the visibility fog for the entire grid
	 * Returns the nodes that are visible from the current position. All other nodes are now not visible
	 */
	public Node[] turnOnFog(Node current, int sight){
		for(int x = 0; x < xLength; x++){
			for(int y = 0; y < yLength; y++){
				grid[x][y].setVisibility(false);
			}
		}
		return getVision(current, sight);
	}
	
	/* Turns off the visibility fog for the entire grid
	 * All nodes are made visible
	 */
	public void turnOffFog(){
		for(int x = 0; x < xLength; x++){
			for(int y = 0; y < yLength; y++){
				grid[x][y].setVisibility(true);
			}
		}
	}
	
	// Set all of the shadow connections for a fresh grid
	public void setAllShadows(){
		for(int x = 0; x < xLength; x++){
			for(int y = 0; y < yLength; y++){
				Node current = grid[x][y];
				current.setShadows(getAdjacent(current));
			}
		}
	}
	
	// Standard grid with all possible links present, and all nodes passable
	public void createStandard() {
		for(int x = 0; x < xLength; x++){
			for(int y = 0; y < yLength; y++){
				grid[x][y] = new SquareNode(x, y, true);
			}
		}
		for(int x = 0; x < xLength; x++){
			for(int y = 0; y < yLength; y++){
				Node current = getNode(x, y);
				Node[] neighbors = getAdjacent(current);
				for(Node n: neighbors){
					if(n.getPosition().getX() == x || n.getPosition().getY() == y)
						linkNodes(current, n, cardinal);
					else
						linkNodes(current, n, diagonal);
				}
				current.setShadows(neighbors);
			}
		}
	}
	
	public void createRandom() {
		double linkProb = 0.3; // Probability of a link being formed between two nodes
		for(int x = 0; x < xLength; x++){
			for(int y = 0; y < yLength; y++){
				grid[x][y] = new SquareNode(x, y, true);
			}
		}

		for(int x = 0; x < xLength; x++){
			for(int y = 0; y < yLength; y++){
				Node current = grid[x][y];
				Node[] neighbors = getAdjacent(current);
				for(Node n: neighbors){
					if(Math.random() < linkProb){
						if(n.getPosition().getX() == current.getPosition().getX()
								|| n.getPosition().getY() == current.getPosition().getY())
							linkNodes(current, n, cardinal);
						else
							linkNodes(current, n, diagonal);
					}
				}
				// Set the shadow connections to all neighbors for each node
				current.setShadows(neighbors);
			}
		}
	}

}
