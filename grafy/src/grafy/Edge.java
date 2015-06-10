package grafy;


public class Edge 
{
	private Vertex first;
	private Vertex second;
	
	public Edge(Vertex first, Vertex second)
	{
		this.setFirst(first);
		this.setSecond(second);
	}

	public Vertex getFirst() {
		return first;
	}

	public void setFirst(Vertex first) {
		this.first = first;
	}

	public Vertex getSecond() {
		return second;
	}

	public void setSecond(Vertex second) {
		this.second = second;
	}
	

}
