import java.util.ArrayList;

public class Pair {
	
	private Node s;
	private ArrayList<Integer> k;

	public Pair(Node sInput, ArrayList<Integer> kInput)
	{
		s = sInput;
		k = kInput;
	}

	public Node getNode()
	{
		return s;
	}

	public ArrayList<Integer> getKey()
	{
		return k;
	}
}
