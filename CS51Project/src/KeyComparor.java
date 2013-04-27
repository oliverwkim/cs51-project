import java.util.Comparator;

public class KeyComparator implements Comparator<Key>{

	public int compare(Key one, Key two){
		if (one[0] < two[0])
			return -1;
		else if (one[0] > two[0])
			return 1;
		else 
			if (one[1] < two[1])
				return -1
			else if (one[1] > two[1])
				return 1;
			else return 0;
	}
}