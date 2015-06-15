package grafy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DFS 
{
	public List<Vertex> vertexes;
	public List<Edge> edges;
	public List<Vertex> finalPath;
	public double distance;
	
	public DFS(Graph graph)
	{
		this.vertexes = graph.vertexes;
		this.edges = graph.edges;
		finalPath = new ArrayList<Vertex>();
		distance = 0;
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
			Collections.sort(v.neighbours, maxDistance(v));			
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
				restoreNeighbourhood(tmp);
			}
			
			while(pomocnicza.get(0).neighbours.isEmpty())
			{
				Vertex delete = pomocnicza.remove(0);
				if(tmpVertex.neighbours.isEmpty())
					break;
				
				if(!pomocnicza.isEmpty())
				{
					pomocnicza.get(0).neighbours.remove(delete);
					restoreNeighbourhood(delete);
				}
			}
			
			if(!pomocnicza.isEmpty())
				tmp = pomocnicza.get(0).neighbours.get(0);
			
			for(Vertex v : pomocnicza)
			{
				tmp.neighbours.remove(v);
			}
			
		}		
		
		distance = totalDistance;
				
	}
	
	private double countDistance(ArrayList<Vertex> list)
	{
		double totalDistance = 0;
		for(int i=list.size()-1;i>0;i--)
		{
			totalDistance += distance(list.get(i),list.get(i-1));
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
	
	
	private Comparator<Vertex> maxDistance(Vertex toCompare)
	{
		return new Comparator<Vertex>()
				{
					@Override
					public int compare(Vertex v1, Vertex v2)
					{
						if(distance(toCompare,v1) > distance(toCompare,v2))
							return -1;
						else if(distance(toCompare,v1) == distance(toCompare,v2))
							return 0;
						else
							return 1;						
					}
			
				};
	}
	
	private void restoreNeighbourhood(Vertex v)
	{
		v.neighbours.clear();
		for(Edge e : edges)
		{
			if(e.start.name.equals(v.name))
				v.neighbours.add(e.end);
		}		
	}
	
	
	public void printPath()
	{
		System.out.println("\n");
		System.out.println("NAJDLUZSZA SCIEZKA MIEDZY DWOMA ZADANYMI WIERZCHOLKAMI:");
		
		for(int i = finalPath.size()-1;i>=0;i--)
		{
			System.out.printf("-> %s ",finalPath.get(i));
		}
		
		System.out.println("\nCalkowity dystans: " + distance);
		System.out.println("\n");
	}
	

}
