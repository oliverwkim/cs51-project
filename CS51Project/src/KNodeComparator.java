import java.util.Comparator; 

/*
 * Used for comparing keys in the priority queues of LPAstar and D* Lite
 */
public class KNodeComparator implements Comparator<Node> {
	
	// Calls the key comparator implemented in LPAstar
	public int compare(Node one, Node two) {
		return LPAstar.keyCompare(one.getKScore(), two.getKScore());
	}
	
	public boolean equals(Node one, Node two){
		return one.equals(two);
	}
}
