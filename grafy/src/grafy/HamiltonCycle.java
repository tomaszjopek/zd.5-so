package grafy;

import java.util.List;

public class HamiltonCycle 
{
	
	public List<Vertex> vertexes;
	public List<Edge> edges;
	public int d, tmpD;
	public Vertex stack[];
	public Vertex tmpStack[];	
	public int stackPointer;
	public int tmpPointer;
	
	public HamiltonCycle(Graph graph)
	{
		this.vertexes = graph.vertexes;
		this.edges = graph.edges;
		stack = new Vertex[vertexes.size()];
		tmpStack = new Vertex[vertexes.size()];
		stackPointer = 0;
		tmpPointer = 0;
		d = Integer.MAX_VALUE;
		tmpD = 0;		
	}
	
	public void findCycle(String name)
	{		
		createNeighbourhood();
		for(Vertex v : vertexes)
		{
			if(v.name.equals(name))
			{
				find(v);
			}
		}
	}
	
	
	private void find(Vertex v0)
	{
		tmpStack[tmpPointer++] = v0;
		
		if(tmpPointer < vertexes.size())
		{
			v0.visited = true;
			
			for(Vertex v : v0.neighbours)
			{
				if(v.visited == false)
				{
					tmpD += distance(v0,v);
					find(v);
					tmpD -= distance(v0,v);
				}
			}
			
			v0.visited = false;			
		}
		else if(distance(tmpStack[vertexes.size()-1],tmpStack[0]) != -1)
		{
			
			tmpD += distance(tmpStack[vertexes.size()-1],tmpStack[0]);
			if(tmpD < d)
			{
				d = tmpD;
				for(int i=0;i<tmpStack.length;i++)
					stack[i] = tmpStack[i];
				stackPointer = tmpPointer;
			}
			
			tmpD -= distance(tmpStack[vertexes.size()-1],tmpStack[0]);			
		}
		
		tmpPointer--;
		
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
	
	public void printCycle()
	{		
		System.out.println("\n");
		if(stackPointer > 0)
		{
			for(int i=0;i<stack.length;i++)
			{
				System.out.printf("--> %s", stack[i]);
			}
			System.out.print("--> " + stack[0]);
			System.out.println();
			System.out.println("Dlugosc cyklu: " + d);	
		}
		else
		{
			System.out.println("Nie ma cyklu hamiltona");
		}
		
		System.out.println("\n");	
	}
	
	private void createNeighbourhood()
	{
		for(Vertex v : vertexes)
		{
			v.neighbours.clear();
			for(Edge e : edges)
			{
				if(e.start.name.equals(v.name))
					v.neighbours.add(e.end);
			}
		}
	}
}
