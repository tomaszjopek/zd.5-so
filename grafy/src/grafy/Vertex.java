package grafy;

import java.util.*;

public class Vertex<T> implements Comparable<Vertex<T>>
{
	    private final T name;
	    private List<Edge<T>> list;
	    private int minDistance = Integer.MAX_VALUE;
	    private Vertex<T> previous;
	    
	    public Vertex(T name)
	    {
	    	list = new ArrayList<Edge<T>>();
	    	this.name = name;
	    }
	    
	    public int getMinDistance()
	    {
	    	return minDistance;
	    }
	    
	    public void setMinDistance(int newDistance)
	    {
	    	this.minDistance = newDistance;
	    }
	    
	    public String toString()
	    {
	    	return (String)name;
	    }
	    	    
	    public int compareTo(Vertex<T> v)
	    {
	        return Integer.compare(this.minDistance, v.getMinDistance());
	    }


		public List<Edge<T>> getList() {
			return list;
		}


		public void setList(List<Edge<T>> list) {
			this.list = list;
		}


		public Vertex<T> getPrevious() {
			return previous;
		}


		public void setPrevious(Vertex<T> previous) {
			this.previous = previous;
		}

		
}
