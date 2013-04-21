public class SquareNode extends Node {

	// Maximum number of nodes this can be connected to
	private static final int maxConnections = 4;
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

	public void linkNode(Node n) {
		if (marker < maxConnections)
			connections[marker++] = n;
		else
			return;
	}

}
