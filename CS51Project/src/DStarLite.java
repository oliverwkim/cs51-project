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
		if (!u.equals(start))
		{
			u.setRhsScore(findRhs(u)); 
		}

		if (open_set.contains(u))
			open_set.remove(u);
		
		if (u.getGScore() != u.getRhsScore())
		{
			u.setKScore(calculateKey(u));
			if(!(open_set.contains(u)))
				open_set.add(u);
		}
	}
	
	public static void computeShortestPath(PriorityQueue<Node> open_set)
	{
		while(keyCompare(calculateKey(open_set.peek()), calculateKey(goal)))
		{
			Node u = open_set.poll();
			if ((u.getGScore() > u.getRhsScore()))
			{
				u.setGScore(u.getRhsScore());
				for (Node s : u.getConnections())
					updateVertex(s);
			}
			else
			{
				u.setGScore(1000);
				for (Node s : u.getConnections()) // This was g.getAdjacent(u) before
					updateVertex(s);
				updateVertex(u);
			}
		}	
	}
	
	public static Node[] algorithm()
	{
		initialize(g, goal, start);
		
	}

	

	
}
