import java.util.Comparator; 

/*
 * Used for comparing node scores in the Astar algorithm
 */
public class NodeComparator implements Comparator<Node> {
	
	public int compare(Node one, Node two) {
		if (one.getFScore() < two.getFScore())
			return -1;
		else if (one.getFScore() > two.getFScore())
			return 1;
		else return 0;
	}
}
