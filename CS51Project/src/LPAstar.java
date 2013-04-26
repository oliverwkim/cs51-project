import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class LPAStar extends Astar { // for the hScore method

	public static int[] calculateKey(Node s, Node goal) 
	{
		return [min(s.getG(), s.getRhs() + hScore(s,goal)); min(s.getG(), s.getRhs())];
	}

	public static void initialize(Grid g, Node start)
	{
		PriorityQueue<Pair> open_set = new PriorityQueue<Pair>(11, comparator); 
		for (Node s : start.getAllVisible()) 
		{
			s.setGScore(1000); 
			s.setRhsScore(1000);
		}
		start.setRhsScore(0);
		open_set.add(s,[s.hscore();0]);
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
			open_set.add(u,calculateKey(u));
	}

	public static void computeShortestPath(PriorityQueue open_set, Node goal, Grid g)
	{
		while((open_set.peek() < calculateKey()) || (goal.getRhs() <> goal.getG()))
		{
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
		initialize(g, start);
		computeShortestPath(open_set, goal, g);
		//for our implementation edge costs don't actually change
		//instead nodes simply go from passable to impassable, and back
		//so for our implementation thusfar, LPA* doesn't actually add 
		//any unique functionality, since we filter out the impassable
		//nodes from our search in the first place.
		//in order for LPA* to be relevant, we would need to 
		
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