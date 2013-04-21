
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
	
	public Node getEnd(Node a){
		if(a==begin)
			return end;
		else
			return begin;
	}
}
