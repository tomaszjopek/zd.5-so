package grafy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class Graph
{
	
	public List<Vertex> vertexes;
	public List<Edge> edges;
	
	public Graph()
	{
		this.vertexes = new ArrayList<Vertex>();
		this.edges = new ArrayList<Edge>();
	}
		
	
	public void readGraph(String fileName)
	{
		Scanner sc = null;
		
		try
		{
			sc = new Scanner(new File(fileName));
			
			while(sc.hasNextLine())
			{
				String line = sc.nextLine();
				String tab[] = line.split(",");
				String v1 = tab[0];
				String v2 = tab[1];
								
				if(!isExisting(v1))
				{
					vertexes.add(new Vertex(v1));
				}
				if(!isExisting(v2))
				{
					vertexes.add(new Vertex(v2));
				}							
			}
			
			sc = new Scanner(new File(fileName));
			while(sc.hasNextLine())
			{				
				String line = sc.nextLine();
				String tab[] = line.split(",");
				String v1 = tab[0];
				String v2 = tab[1];
				int distance = Integer.parseInt(tab[2]);
				
				edges.add(new Edge(getVertex(v1),getVertex(v2),distance));				
			}			
			
			createNeighbourhood();
			
		}
		catch(FileNotFoundException f)
		{
			System.out.println(f.getMessage());
		}
		catch(@SuppressWarnings("hiding") IOException e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			if(sc!=null) sc.close();
		}
	}
	
	public void print()
	{					
		for(Edge e : edges)
		{
			System.out.println(e);
		}
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
	
	private boolean isExisting(String name)
	{
		for(Vertex v : vertexes)
		{
			if(v.name.equals(name))
				return true;
		}
		
		return false;
	}
	
	private Vertex getVertex(String name)
	{
		for(Vertex v : vertexes)
		{
			if(v.name.equals(name))
				return v;
		}
		
		return null;
	}
	
}
