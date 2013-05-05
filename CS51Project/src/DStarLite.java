import java.util.ArrayList;
import java.util.PriorityQueue;


public class DStarLite extends LPAstar{

	private static int costToCurrent;
	private static PriorityQueue<Node> open_set;
	private static Node start;
	private static Node goal;
	private static Grid g;
	
	public static ArrayList<Integer> calculateKey(Node s) 
	{
		ArrayList<Integer> key = new ArrayList<Integer>();
		key.add(Math.min(s.getGScore(), s.getRhsScore()) + hScore(start,s) + costToCurrent);
		key.add(Math.min(s.getGScore(), s.getRhsScore()));
		return key;
	}
	
	public static void initialize(Grid gridInput, Node gInput, Node sInput)
	{
		g = gridInput;
		start = sInput;
		goal = gInput;
		costToCurrent = 0;
		g.setPos(start);
		
		kNodeComparator = new KNodeComparator();
		open_set = new PriorityQueue<Node>(11, kNodeComparator);
		goal.setKScore(calculateKey(goal));
		goal.setRhsScore(0);
		open_set.add(goal);
	}
	
	public static void updateVertex(Node u)
	{
		if (!u.equals(goal)){
			u.setRhsScore(findRhsDstar(u)); 
		}

		if (open_set.contains(u))
			open_set.remove(u);
		
		if (u.getGScore() != u.getRhsScore())
		{
			u.setKScore(calculateKey(u));
			open_set.add(u);
		}
	}
	
	public static void computeShortestPath()
	{
		while(keyCompare(calculateKey(open_set.peek()), calculateKey(goal)) 
					|| start.getRhsScore() != start.getGScore()) 
		{
			ArrayList<Integer> oldKey = calculateKey(open_set.peek());
			Node u = open_set.poll();
			u.setRhsScore(findRhsDstar(u));
			
			if(keyCompare(oldKey, calculateKey(u)))
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
				u.setGScore(2000000);
				for (Node s : u.getConnections())
					updateVertex(s);
				updateVertex(u);
			}
		}	
	}
	
	public static Node[] algorithm(Grid gInput, Node goalInput, Node startInput)
	{
		Node last = startInput;
		initialize(gInput, goalInput, startInput);
		computeShortestPath();
		while(!start.equals(goal))
		{
			if (start.getGScore() == 2000000)//i.e. path does not exist 
				return null;
			System.out.println(start.getPosition().getX() + ", " + start.getPosition().getY());
			start = minimize(start.getConnections()); 
			
			for(Node n: g.getVision(startInput, 2)){
				Edge[] changedEdges = n.getNewEdges();
				if(changedEdges != null){
					costToCurrent = costToCurrent + hScore(last, start);  
					last = start;
					for (Edge e : changedEdges)
					{
						Node begin = e.getBegin();
						updateVertex(begin);
					}
				}
				computeShortestPath();
			}
		}
		return reconstructPath(startInput, goal, new ArrayList<Node>());
	}
	/* 
	 * 	km = km + h(slast, sstart)
	 * 	slast = sstart
	 * 	for all directed edges (u, v) with changed edge costs
	 * 		update the edge cost c(u,v)
	 * 		update vertex (u)
	 * 
	 * 	compute shortestpath()
	 * changedEdges contains all of the Edges that were presumed to exist (when the Node's not visible),
	 * but actually do not exist.
	 * Do the rest of the stuff here.
	 * Also note getVision now returns only the nodes that are newly visible
	 * (i.e. does not return Nodes around the current position that were already visible)
	 */
	
	private static Node minimize (Node[] nodeList)
	{
		Node min = nodeList[0];
		
		for(Node s : nodeList)
		{
			if (g.getEdgeLength(s, goal) + s.getGScore() < g.getEdgeLength(min, goal) + min.getGScore())
				min = s;
		}
		return min;
	}
	
	public static int findRhsDstar(Node u)
	{		
		if(!u.equals(goal))
		{
			PriorityQueue<Integer> values = new PriorityQueue<Integer>(11);
			for (Node s : u.getConnections())
			{
				if (s == null)
					System.out.println("NULL");
				values.add(s.getGScore() + g.getEdgeLength(s,u));
			}
			if (values.size() == 0)
				return -1;
			return values.peek();			
		}
		return 0;
	}
}
