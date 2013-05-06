import java.util.ArrayList;
import java.util.PriorityQueue;


/*
 * D* Lite builds on LPA* to implement a simpler version of the D* algorithm
 * D* Lite reverses the search direction of LPA*, starting at the goal node
 * and working to reach the start node. Consequently the RHS and G score 
 * methods are different from those in LPA*.
 */

public class DStarLite extends LPAstar{

	private static int costToCurrent; // cost from start to current node
	private static PriorityQueue<Node> open_set; 
	private static Node start;
	private static Node goal;
	private static Grid g;
	private static Node last;
	
	public static ArrayList<Integer> calculateKey(Node s) 
	{
		ArrayList<Integer> key = new ArrayList<Integer>();
		key.add(Math.min(s.getGScore(), s.getRhsScore()) + hScore(start,s) + costToCurrent);
		key.add(Math.min(s.getGScore(), s.getRhsScore()));
		return key;
	}
	
	/*
	 * In our implementation of initialize, we do not need to explicitly set  
	 * the RHS and G scores of every node to infinity 
	 * (which we express as 2,000,000) since this is already the default value
	 * for those variables.
	 */
	
	public static void initialize(Grid gridInput, Node gInput, Node sInput)
	{
		g = gridInput;
		start = sInput;
		goal = gInput;
		costToCurrent = 0;
		noPath = false;
				
		kNodeComparator = new KNodeComparator();
		open_set = new PriorityQueue<Node>(11, kNodeComparator);
		goal.setRhsScore(0);
		goal.setKScore(calculateKey(goal));
		open_set.add(goal);
		
		g.getVision(sInput, 2);
	}
	
	/*
	 * If edge costs are changed (which in our implementation corresponds
	 * to finding that an edge doesn't exists) then we must update the
	 * vertices bordering that edge in order to maintain the invariants 
	 * necessitated in LPA*.
	 */
	
	public static void updateVertex(Node u)
	{
		if (!u.equals(goal))
			u.setRhsScore(findRhs(u)); 

		if (open_set.contains(u))
			open_set.remove(u); //
		
		if (u.getGScore() != u.getRhsScore()) 
		{
			u.setKScore(calculateKey(u));
			open_set.add(u);
		}
		
	}
	
	public static void computeShortestPath()
	{
		while(keyCompare(open_set.peek().getKScore(), calculateKey(start)) < 0
					|| start.getRhsScore() != start.getGScore()) 
		{
			ArrayList<Integer> oldKey = open_set.peek().getKScore();
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
		last = startInput;
		initialize(gInput, goalInput, startInput);
		computeShortestPath();
		int counter = 0;
		path = new ArrayList<Node>();
		while(!start.equals(goal))
		{
			if(counter++ > 50)
				return path.toArray(new Node[path.size()]);
			System.out.println(start.getGScore() + " Node");
			path.add(0, start);			

			if (start.getGScore() == 2000000 || noPath) // path does not exist 
				return path.toArray(new Node[path.size()]);			
			start = minimize(start);
			start.setParent(last);
			//last = start;
			//start = temp;
			Node[] newVisible = g.getVision(start, 2);			

			if(newVisible != null){
				costToCurrent = costToCurrent + hScore(last, start);  
				last = start;
				for(Node n: newVisible){
					Edge[] changedEdges = n.getNewEdges();
					if(changedEdges != null){						
						for (Edge e : changedEdges)
							updateVertex(e.getEnd(n));
						updateVertex(n);
					}					
				}
				computeShortestPath();				
				initialize(g, goal, start);
				
			}
			
					
		}
		path.add(0, goal);
		return path.toArray(new Node[path.size()]);
	}
	
	private static Node minimize (Node u)
	{
		Node min = u.getConnections()[0];
		
		for(Node s : u.getConnections())
		{
			if (g.getEdgeLength(s, u) + s.getGScore() <= g.getEdgeLength(min, u) + min.getGScore()
					&& !(u.getParent().getParent()).equals(u))
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
