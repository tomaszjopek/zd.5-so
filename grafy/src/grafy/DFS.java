package grafy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFS 
{
	public List<Vertex> vertexes;
	public List<Edge> edges;
	public List<Vertex> finalPath;
		
	public DFS(Graph graph)
	{
		this.vertexes = graph.vertexes;
		this.edges = graph.edges;
		finalPath = new ArrayList<Vertex>();
	}
	
	
	public void findPath(String start, String end)
	{
		double totalDistance = 0;
		Vertex tmpVertex = null;
		Vertex destination = null;
		for(Vertex v : vertexes)
		{
			if(v.name.equals(start))
				tmpVertex = v;
			if(v.name.equals(end))
				destination = v;
			v.previous = null;
			v.visited = false;
		}
		
		Vertex tmp = tmpVertex;
		
		ArrayList<Vertex> pomocnicza = new ArrayList<Vertex>(vertexes.size());
		
		while(!tmpVertex.neighbours.isEmpty())
		{
			pomocnicza.add(0,tmp);
			
			if(tmp.name.equals(destination.name))
			{
				double t = countDistance(pomocnicza);
				if(t > totalDistance)
				{
					totalDistance = t;
					finalPath.clear();
					finalPath.addAll(pomocnicza);
				}
				
				pomocnicza.remove(0);
				pomocnicza.get(0).neighbours.remove(tmp);
			}
			
			while(pomocnicza.get(0).neighbours.isEmpty())
			{
				Vertex delete = pomocnicza.remove(0);
				if(tmpVertex.neighbours.isEmpty())
					break;
				
				if(!pomocnicza.isEmpty())
					pomocnicza.get(0).neighbours.remove(delete);
			}
			
			if(!pomocnicza.isEmpty())
				tmp = pomocnicza.get(0).neighbours.get(0);
			for(Vertex v : pomocnicza)
			{
				tmp.neighbours.remove(v);
			}
			
		}		
		
		System.err.println(totalDistance);
				
	}
	
	private double countDistance(ArrayList<Vertex> list)
	{
		double totalDistance = 0;
		for(int i=0;i<list.size()-1;i++)
		{
			totalDistance += distance(list.get(i),list.get(i+1));
		}
		
		return totalDistance;
	}
	
	private double distance(Vertex v1, Vertex v2)
	{		
		for(Edge e : edges)
		{
			if(e.start.compareTo(v1) == 0 && e.end.compareTo(v2) == 0)
			{
				return e.weight;
			}
		}
		
		return -1;
	}
	
	
	

}
