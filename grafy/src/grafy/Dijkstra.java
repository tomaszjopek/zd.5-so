package grafy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Iterator;


public class Dijkstra 
{
	 public static <T> void findPath(Vertex<T> start)
	    {
	       	start.setMinDistance(0);
	        PriorityQueue<Vertex<T>> vertexQueue = new PriorityQueue<Vertex<T>>();
	      	vertexQueue.add(start);

	      	while (!vertexQueue.isEmpty())
			{
				Vertex<T> tmpVertex = vertexQueue.poll();
		    	
				Iterator<Edge<T>> iter = tmpVertex.getList().iterator();
				while(iter.hasNext())
				{
					Edge<T> tmpEdge = iter.next();
					Vertex<T> tmp = tmpEdge.getEnd();
					int weight = tmpEdge.getWeight();
					int countDistance = tmp.getMinDistance() + weight;
					if(countDistance < tmp.getMinDistance())
					{
						vertexQueue.remove(tmp);
						tmp.setMinDistance(countDistance);
						tmp.setPrevious(tmpVertex);
						vertexQueue.add(tmp);
					}
				}
				
		    }
	    }

	    public static <T> ArrayList<Vertex<T>> resultPath(Vertex<T> search)
	    {
	        ArrayList<Vertex<T>> result = new ArrayList<Vertex<T>>();
	        
	        for (Vertex<T> vertex = search; vertex != null; vertex = vertex.getPrevious())
	        {
	        	result.add(vertex);
	        }
	        Collections.reverse(result);
	        return result;
	    }

}
