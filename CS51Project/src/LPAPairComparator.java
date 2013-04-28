import java.util.Comparator;

public class LPAPairComparator implements Comparator<Pair> {
	
	public int compare(Pair pair1, Pair pair2){
		Comparator<Node> comparator = new NodeComparator();
		return comparator.compare(pair1.getNode(), pair2.getNode());
	}
}