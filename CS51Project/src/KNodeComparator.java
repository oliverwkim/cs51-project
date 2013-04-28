import java.util.Comparator; 

public class KNodeComparator implements Comparator<Node> {
	
	public int compare(Node one, Node two) {
		if (one.getKScore().get(0) == two.getKScore().get(1))
		{
			if (one.getKScore().get(1) > two.getKScore().get(1))
				return 1;
			else if (one.getKScore().get(1) < two.getKScore().get(1))
				return -1;
			return 0;
		}
		else
		{
			if (one.getKScore().get(0) > two.getKScore().get(0))
				return 1;
			else if (one.getKScore().get(0) < two.getKScore().get(0))
				return -1;
			return 0;
		}
	}
}
