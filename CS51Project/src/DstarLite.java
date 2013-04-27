import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class DstarLite extends LPAstar {
	public static int[] calculateKey(Node s, Node goal) 
	{
		return [min(s.getG(), s.getRhs() + hScore(s,goal) + kscore); min(s.getG(), s.getRhs())];
	}

	public static void initialize(Grid g, Node goal)
	{
		PriorityQueue<Pair> open_set = new PriorityQueue<Pair>(11, comparator); 
		kscore = 0;
		for (Node s : goal.getAllVisible()) 
		{
			s.setGScore(1000); 
			s.setRhsScore(1000);
		}
		goal.setRhsScore(0);
		open_set.add(goal, calculateKey(goal, goal));
	}

	public static void updateVertex(Node u, Grid g, PriorityQueue open_set, Mode start)
	{
		if(!u.equals(start))
		{
			u.setRhsScore(findNodeRhs(u, start, g));
		}

		if (open_set.contains(u))
			open_set.remove(u));
		
		if (u.getG() <> u.getRhs())
			open_set.add(u,calculateKey(u,goal));
	}

	public static void computeShortestPath(PriorityQueue open_set, Node goal, Node start, Grid g)
	{
		while((open_set.peek() < calculateKey(start, goal)) || (goal.getRhs() <> goal.getG()))
		{
			kscore =
			Node u = open_set.pop();
			if (u.getG() > u.getRhs())
			{
				u.setGScore(u.getRhs());
				for (Node s : u.getConnections())
					updateVertex(s, g, open_set);
			}
			else
			{
				u.setGScore(1000);
				for (Node s : g.getAdjacent(u)) 
					updateVertex(s, g, open_set);
				updateVertex(u, g, open_set);
			}
		}	
	}

	public static void algorithm(Grid g, Node goal, Node start)
	{
		last = start;
		initialize(g, start);
		computeShortestPath(open_set, goal, g);
		while (!start.equals(goal))
			{
				start = 
			} 
		
	}

	public static Node[] reconstructPath(Node goal, Node start, Grid g)
	{
		ArrayList<Node> path = new ArrayList<Node> ();

		if(goal.Equals(start))
		{
			Node[] result = path.toArray(new Node[path.size()]);
			return result;
		}
		else
		{
			PriorityQueue<int> values = new PriorityQueue<int>;
			for (Node s : goal.getConnections))
				values.add(s.getG() + g.edgelength);
			minval =values.peek();
			path.add(minval);
			reconstructPath(goal, minval, g);
		}
	}

	public static int findRhs(Node u, Node start, Grid g)
	{
		if(u.equals(start))
		{
			u.setRhsScore(0);
		}
		else
		{
			PriorityQueue<int> values = new PriorityQueue<int>;
			for (Node s : u.getConnections())) 
				values.add(s.getG() + g.edgelength(s,u));
			s.setRhsScore(values.peek());
		}
	}
}