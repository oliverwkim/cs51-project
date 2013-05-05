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
		while(keyCompare((open_set.peek().getKScore()), calculateKey(start))
					|| start.getRhsScore() != start.getGScore()) 
		{
			ArrayList<Integer> oldKey = open_set.peek().getKScore();//calculateKey(open_set.peek());
			Node u = open_set.poll();
			u.setRhsScore(findRhs(u));
			
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
	
	public static Node[] algorithm(Grid gInput, Node goalInput, Node startInput, Node[] newVisibles)
	{
		Node last = startInput;
		initialize(gInput, goalInput, startInput);
		computeShortestPath();
		ArrayList<Node> result = new ArrayList<Node>();
		while(!start.equals(goal))
		{

			if (start.getGScore() == 2000000) //i.e. path does not exist 

			result.add(start);
			if (start.getGScore() == 2000000)//i.e. path does not exist 

				return null;
			start = minimize(start.getConnections()); 
			Node[] newVisible = g.getVision(start, 2);
			if(newVisible != null){
				costToCurrent = costToCurrent + hScore(last, start);  
				last = start;
				for(Node n: newVisible){
					Edge[] changedEdges = n.getNewEdges();
					if(changedEdges != null){
						updateVertex(n);
						/*for (Edge e : changedEdges)
						{
							Node begin = e.getBegin();
							updateVertex(begin);
						}*/
					}
					
				}
				computeShortestPath();
			}
		}
		result.add(goal);
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
	
	public static Node[] reconstructPath(Node pathGoal, Node pathStart, ArrayList<Node> deadends)
	{

		if(pathGoal.equals(pathStart))
		{
			//path.remove(path.size()-1);
			//while(!pathStart.connectionExists(path.get(path.size() - 1))){
			//	path.remove(path.size()-1);
			//}
			path.add(0, goal);
			Node[] result = path.toArray(new Node[path.size()]);
			return result;
		}
		else
		{
			PriorityQueue<Node> values = new PriorityQueue<Node>(11, kNodeComparator);
			for (Node s : pathGoal.getConnections())
			{
				s.setKScore(calculateKey(s));
				values.add(s);
			}
			
			Node def = values.peek();
			Node closestNode = values.poll();
			while(path.contains(closestNode)){
				closestNode = values.poll();
				if(deadends.contains(def))
					def = closestNode;
			}
			System.out.println();
			if(closestNode == null){
				System.out.println("It happened");
				deadends.add(pathGoal);
				return reconstructPath(def, pathStart, deadends);
				//return path.toArray(new Node[path.size()]);
			}
			path.add(closestNode);
			return reconstructPath(closestNode, pathStart, deadends);
		}
	}
}
