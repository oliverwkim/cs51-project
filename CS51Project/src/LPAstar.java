import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class LPAstar extends AStar { 

	final static int lineOfSight = 2;
	static Comparator<Node> kNodeComparator;
	static ArrayList<Node> path;
	private static PriorityQueue<Node> open_set = null;
	static Node start;
	static Node goal;
	static Grid g;
	
	
	public static ArrayList<Integer> calculateKey(Node s) 
	{
		ArrayList<Integer> key = new ArrayList<Integer>();
		key.add(Math.min(s.getGScore(), s.getRhsScore()) + hScore(s,goal));
		key.add(Math.min(s.getGScore(), s.getRhsScore()));
		return key;
	}

	public static void initialize()
	{
		kNodeComparator = new KNodeComparator();
		path = new ArrayList<Node>();
		open_set = new PriorityQueue<Node>(11, kNodeComparator); 
		start.setRhsScore(0);
		start.setKScore(null);
		open_set.add(start);
	}

	public static void updateVertex(Node u)
	{
		if(!u.equals(start))
		{
			u.setRhsScore(findRhs(u));
		}

		if (open_set.contains(u))
			open_set.remove(u);
		
		if (u.getGScore() != u.getRhsScore())
		{
			u.setKScore(calculateKey(u));
			open_set.add(u);
		}
	}

	public static boolean keyCompare(ArrayList<Integer> one, ArrayList<Integer> two)
	{
		if (one.get(0) < two.get(0))
			return true;
		else if (one.get(0) > two.get(0))
			return false;
		else if (one.get(1) < two.get(1))
			return true;
		else if (one.get(1) > two.get(1))
			return false;
		return false;
	}
	
	public static void computeShortestPath()
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
				u.setGScore(10000);
				for (Node s : u.getConnections()) // This was g.getAdjacent(u) before
					updateVertex(s);
				updateVertex(u);
			}
		}	
	}

	public static Node[] algorithm(Grid newG, Node newGoal, Node newStart)
	{
		g = newG;
		goal = newGoal;
		start = newStart;
		initialize();
		computeShortestPath();
		return reconstructPath(goal, start, g);
	}

	public static Node[] reconstructPath(Node pathGoal, Node pathStart, Grid g)
	{

		if(pathGoal.equals(pathStart))
		{
			path.remove(path.size()-1);
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
			
			Node closestNode = values.poll();
			while(path.contains(closestNode)){
				closestNode = values.poll();
			}
			if(closestNode == null)
				return path.toArray(new Node[path.size()]);
			path.add(closestNode);
			return reconstructPath(closestNode, pathStart, g);
		}
	}

	public static int findRhs(Node u)
	{		
		if(!u.equals(start))
		{
			PriorityQueue<Integer> values = new PriorityQueue<Integer>(11);
			for (Node s : u.getConnections())
			{
				if (s == null)
					System.out.println("NUUUUUUUUUUUUULL");
				values.add(s.getGScore() + g.getEdgeLength(s,u));
			}
			if (values.size() == 0)
				return -1;
			return values.peek();			
		}
		return 0;
	}
}