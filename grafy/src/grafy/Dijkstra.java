package grafy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class Dijkstra 
{
	public List<Vertex> vertexes;
	public List<Edge> edges;
	public List<Vertex> unvisited;
	
	public Dijkstra(Graph graph)
	{
		this.vertexes = graph.vertexes;
		this.edges = graph.edges;
		unvisited = new ArrayList<Vertex>(vertexes.size());
	}
		
	public void findPath(String start)
	{
		
		for(Vertex v : vertexes)
			v.minDistance = Double.POSITIVE_INFINITY;
		
		unvisited.addAll(vertexes);	
				
		for(Vertex v : unvisited)
		{
			if(v.name.equals(start))
			{
				v.minDistance = 0;
			}
		}
				
		while(!unvisited.isEmpty())
		{
			Collections.sort(unvisited, Vertex.distanceComparator());
			Vertex tmp = unvisited.get(0);
			unvisited.remove(tmp);
			
			for(Vertex tmp2 : tmp.neighbours)
			{	
				double w = distance(tmp,tmp2);				
				if(w >=0)
				{
					if(tmp2.minDistance > tmp.minDistance + w)
					{
						tmp2.minDistance = tmp.minDistance + w;
						tmp2.previous = tmp;						
					}
				}				
		
			}			
		}		
	}
	
	public void printPath(String target)
	{
		Vertex destination = null;
		for(Vertex v : vertexes)
		{
			if(v.name.equals(target))
			{
				destination = v;
			}
		}
		
		class Result
		{
			public Vertex v;
			public double distance;
			
			public Result(Vertex v, double distance)
			{
				this.v = v;
				this.distance = distance;
			}
			
			@Override
			public String toString()
			{
				return v + "[" + distance + "] ";
			}
						
		}
		
		ArrayList<Result> wynik = new ArrayList<Result>();
		while(destination!=null)
		{
			wynik.add(new Result(destination,destination.minDistance));
			destination = destination.previous;
		}
		
		for(int i = wynik.size()-1;i>=0;i--)
		{
			System.out.printf("-> %s ",wynik.get(i));
		}
		
		System.out.println();
		
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
