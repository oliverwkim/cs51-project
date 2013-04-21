
public class Edge {
	private int length;
	private Node begin;
	private Node end;
	
	public Edge(Node first, Node last){
		begin = first;
		end = last;
		length = 1;
	}
	
	public Edge(Node first, Node last, int s){
		begin = first;
		end = last;
		length = s;
	}
	
	public int getLength(){
		return length;
	}
	
	// Takes in one end of the edge and returns the other end.
	// Returns null if the passed-in node is not part of this edge
	public Node getEnd(Node a){
		if(a.equals(begin))
			return end;
		else if(a.equals(end))
			return begin;
		else
			return null;
	}
}
