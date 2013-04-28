import java.util.ArrayList;
import java.util.PriorityQueue;


public class DStarLite extends LPAStar {

	private static int k;
	private static PriorityQueue<Node> open_set = null;
	
	public static ArrayList<Integer> calculateKey(Node s, Node start) 
	{
		ArrayList<Integer> key = new ArrayList<Integer>();
		key.add(Math.min(s.getGScore(), s.getRhsScore() + hScore(s,start) + k));
		key.add(Math.min(s.getGScore(), s.getRhsScore()));
		return key;
	}
	
	public static void initialize(Grid g, Node goal, Node start)
	{
		open_set = new PriorityQueue<Node>(11, kNodeComparator); 
		k = 0;
		goal.setKScore(calculateKey(goal, start));
		goal.setRhsScore(0);
		open_set.add(goal);
	}
	
	public static void updateVertex(Node u, Grid g, Node start, Node goal)
	{
	}
	
	public static Node[] algorithm(Grid g, Node goal, Node start)
	{
	}

	
}
