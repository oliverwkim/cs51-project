import java.util.Comparator; 

// hello Roger

public class NodeComparator implements Comparator<Node> {
	
	public int compare(Node one, Node two) {
		if (one.getFScore() < two.getFScore())
			return -1;
		else if (one.getFScore() > two.getFScore())
			return 1;
		else return 0;
	}
}
