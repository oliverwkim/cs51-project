import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class LPAStar extends AStar {

	public static int[] calculateKey(Node s, Node goal) 
	{
		return [min(s.getG(), s.getRhs() + hscoreH(s,goal)); min(s.getG(), s.getRhs())];
	}

	public static PriorityQueue<Node> initialize(Grid g, Node start)
	{
		PriorityQueue<Pair> open_set = new PriorityQueue<Pair>(11, comparator); 
		for (Node s : g.getNodes()) 
		{
			s.setGScore(-1); 
			s.setRhsScore(-1);
		}
		start.setRhsScore(0);
		open_set.add(s,[s.hscore();0]);
		return open_set;
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
				for (Node s : g.getAdjacent(u)) 
					updateVertex(s, g, open_set);
			}
			else
			{
				u.setGScore(-1);
				for (Node s : g.getAdjacent(u)) 
					updateVertex(s, g, open_set);
				updateVertex(u);
			}
		}	
	}

	public static void algorithm(Grid g, Node goal, Node start)
	{
		open_set = initialize(g, start);
		forever
			computeShortestPath(open_set, goal, g)
			//Wait for changes in edge costs
			//for all edges (u,v) with changed edge costs
				//update edge cost c(u,v)
				//updateVertex(v)
	}

	public static Node[] reconstructPath(Node goal, Node start)
	{
		
	}

	public static int findRhs(Node s, Node start, Grid g)
	{
		if(s.equals(start))
		{
			s.setRhsScore(0);
		}
		else
		{
			PriorityQueue<int> values = new PriorityQueue<int>;
			for (Node s : g.getAdjacent(s)) 
				values.add(s.getG() + g.edgelength(s,u));
			s.setRhsScore(values.peek());
		}
	}
}