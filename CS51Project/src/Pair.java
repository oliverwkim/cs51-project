public class Pair {
	
	private Node s;
	private ArrayList<integer> k;
	
	public void pair(Node sInput, ArrayList<integer> kInput)
	{
		s = sInput;
		k = kInput;
	}

	public Node getNode()
	{
		return s;
	}

	public ArrayList<integer> getKey()
	{
		return k;
	}
}
