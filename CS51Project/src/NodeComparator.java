
public class NodeComparator implements Comparator<Node> {
	
	public int compare(Node one, Node two) {
		if AStar.getFScore(one) < AStar.getFScore(two)
			return -1;
		else if AStar.getFScore(one) > AStar.getFScore(two)
			return 1;
		else return 0;
	}
}
