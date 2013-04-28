import java.util.ArrayList;
import java.util.PriorityQueue;


public class DStarLite extends LPAStar {

	private static int k;
	private static PriorityQueue<Node> open_set = null;
	private static Node start;
	private static Node goal;
	private static Grid g;
	
	public static ArrayList<Integer> calculateKey(Node s) 
	{
		ArrayList<Integer> key = new ArrayList<Integer>();
		key.add(Math.min(s.getGScore(), s.getRhsScore()) + hScore(start,s) + k);
		key.add(Math.min(s.getGScore(), s.getRhsScore()));
		return key;
	}
	
	public static void initialize(Grid gridInput, Node gInput, Node sInput)
	{
		g = gridInput;
		start = sInput;
		goal = gInput;
		k = 0;
		
		open_set = new PriorityQueue<Node>(11, kNodeComparator);
		goal.setKScore(calculateKey(goal));
		goal.setRhsScore(0);
		open_set.add(goal);
	}
	
	public static void updateVertex(Node u)
	{
		if (!u.equals(goal))
		{
			u.setRhsScore(); // add stuff here
		}
	}
	
	public static void computeShortestPath()
	{
		while(keyCompare(open_set.peek(), calculateKey(start)) || start.getRhsScore() != start.getGScore())
		{
			
		}
	}
	
	public static Node[] algorithm()
	{
		initialize();
		
	}

	
}
