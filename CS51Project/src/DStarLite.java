import java.util.ArrayList;
import java.util.PriorityQueue;


public class DStarLite extends LPAstar{

	private static int costToCurrent;
	private static PriorityQueue<Node> open_set;
	private static Node start;
	private static Node goal;
	private static Grid g;
	private static boolean noPath;
	
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
		noPath = false;
		
		path = new ArrayList<Node>();
		
		kNodeComparator = new KNodeComparator();
		open_set = new PriorityQueue<Node>(11, kNodeComparator);
		goal.setRhsScore(0);
		goal.setKScore(calculateKey(goal));
		open_set.add(goal);
	}
	
	public static void updateVertex(Node u)
	{
		if (!u.equals(goal))
			u.setRhsScore(findRhs(u)); 

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
		
		while(keyCompare((open_set.peek().getKScore()), calculateKey(start)) < 0
					|| start.getRhsScore() != start.getGScore()) 
		{
			ArrayList<Integer> oldKey = open_set.peek().getKScore();//calculateKey(open_set.peek());
			Node u = open_set.poll();
			u.setRhsScore(findRhs(u));
			
			if(keyCompare(oldKey, calculateKey(u)) < 0)
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
			if(open_set.size() == 0){
				noPath = true;
				break;
			}
		}	
	}
	
	public static Node[] algorithm(Grid gInput, Node goalInput, Node startInput)
	{
		Node last = startInput;
		initialize(gInput, goalInput, startInput);
		computeShortestPath();
		ArrayList<Node> result = new ArrayList<Node>();
		while(!start.equals(goal))
		{
			result.add(0, start);
			if (start.getGScore() == 2000000 || noPath) //i.e. path does not exist 
				return result.toArray(new Node[result.size()]);
			start = minimize(start.getConnections(), start); 
			Node[] newVisible = g.getVision(start, 2);
			if(newVisible != null){
				costToCurrent = costToCurrent + g.getEdgeLength(last, start);  
				last = start;
				for(Node n: newVisible){
					Edge[] changedEdges = n.getNewEdges();
					if(changedEdges != null){
						updateVertex(n);
						for (Edge e : changedEdges)
						{
							updateVertex(e.getEnd(n));
						}
					}
					
				}
				computeShortestPath();
			}
		}
		result.add(0, goal);
		return result.toArray(new Node[result.size()]);
		//return reconstructPath(startInput, goal, new ArrayList<Node>());
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
	
	private static Node minimize (Node[] nodeList, Node u)
	{
		Node min = nodeList[0];
		
		for(Node s : nodeList)
		{
			if (g.getEdgeLength(s, u) + s.getGScore() < g.getEdgeLength(min, u) + min.getGScore())
				min = s;
		}
		return min;
	}
	
	public static int findRhs(Node u)
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
