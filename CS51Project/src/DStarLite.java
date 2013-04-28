import java.util.ArrayList;
import java.util.PriorityQueue;


public class DStarLite extends LPAstar {

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
		g.setPos(start);
		
		open_set = new PriorityQueue<Node>(11, kNodeComparator);
		goal.setKScore(calculateKey(goal));
		goal.setRhsScore(0);
		open_set.add(goal);
	}
	
	public static void updateVertex(Node u)
	{
		if (!u.equals(goal))
			u.setRhsScore(minimize(u.getConnections()).getRhsScore()); 

		if (open_set.contains(u))
			open_set.remove(u);
		
		if (u.getGScore() != u.getRhsScore())
		{
			u.setKScore(calculateKey(u));
			if(!(open_set.contains(u)))
				open_set.add(u);
		}
	}
	
	public static void computeShortestPath()
	{
		while(keyCompare(calculateKey(open_set.peek()), calculateKey(goal)) || start.getRhsScore() != start.getGScore())
		{
			ArrayList<Integer> kOld = open_set.peek().getKScore();
			Node u = open_set.poll();
			
			if(keyCompare(kOld, calculateKey(u)))
			{
				u.setKScore(calculateKey(u));
				open_set.add(u);
			}
			else if ((u.getGScore() > u.getRhsScore()))
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
	
	public static Node[] algorithm(Grid gInput, Node goalInput, Node startInput)
	{
		Node last = null;
		initialize();
		computeShortestPath();
		initialize(gInput, goalInput, startInput);
		
		while(!start.equals(goal))
		{
			if (start.getGScore() == 10000) return null;
			start = minimize(start.getConnections());
			g.setPos(start);
			
			/* 
			 * Scan for changed edge costs
			 * If any edge costs changed
			 * 	km = km + h(slast, sstart)
			 * 	slast = sstart
			 * 	for all directed edges (u, v) with changed edge costs
			 * 		update the edge cost c(u,v)
			 * 		update vertex (u)
			 * 	compute shortestpath()
			 */
			
		}
		return null;
	}
	
	private static Node minimize (Node[] nodeList)
	{
		Node min = nodeList[0];
		
		for(Node s : nodeList)
		{
			if (g.getEdgeLength(s, start) + s.getGScore() < g.getEdgeLength(min, start) + min.getGScore())
				min = s;
		}
		return min;
	}
}
