package grafy;


public class Edge<T> 
{
	private final Vertex<T> end;
    private final int weight;
    
    public Edge(Vertex<T> end, int weight)
    {
    	this.end = end;
    	this.weight = weight; 
    }

	public Vertex<T> getEnd() {
		return end;
	}

	public int getWeight() {
		return weight;
	}



}
