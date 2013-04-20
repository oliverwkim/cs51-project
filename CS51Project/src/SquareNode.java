public class SquareNode extends Node {

	// Maximum number of nodes this can be connected to
	private static final int maxConnections = 4;
	private int marker = 0;

	public SquareNode(){
		super(maxConnections);
	}

	public SquareNode(int x, int y, boolean pass){
		super(x, y, pass, maxConnections);
	}

	public SquareNode(int x, int y, int cost, boolean pass){
		super(x, y, cost, pass, maxConnections);
	}

	void linkNode(Node n) {
		connections[marker++] = n;
	}

}
