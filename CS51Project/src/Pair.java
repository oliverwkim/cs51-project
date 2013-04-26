public class Pair<Node,int> {
	public final Node s;
	public final int n;
	public pair(Node s, int n)
	{
		this.s = s;
		this.n = n;
	}

	public static Node getNode(Pair pair)
	{
		return pair.s;
	}

	public static int getInt(Pair pair)
	{
		return pair.n;
	}
}
