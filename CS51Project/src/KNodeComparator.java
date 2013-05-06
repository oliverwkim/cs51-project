import java.util.Comparator; 

public class KNodeComparator implements Comparator<Node> {
	
	public int compare(Node one, Node two) {
		if (one.getKScore()[0]== two.getKScore()[0])
		{
			if (one.getKScore()[1] > two.getKScore()[1])
				return 1;
			else if (one.getKScore()[1] < two.getKScore()[1])
				return -1;
			return 0;
		}
		else
		{
			if (one.getKScore()[0] > two.getKScore()[0])
				return 1;
			else if (one.getKScore()[0] < two.getKScore()[0])
				return -1;
			return 0;
		}
	}
	
	public boolean equals(Node one, Node two){
		return one.equals(two);
	}
}
