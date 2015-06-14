package grafy;

import java.util.*;

public class Vertex implements Comparable<Vertex>
{
	public String name;
	public List<Vertex> neighbours;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;
	
	public Vertex(String name)
	{
		this.name = name;
		neighbours = new ArrayList<Vertex>();
		previous = null;
	}
 

	@Override
	public String toString()
	{
		return name;
	}

	@Override
	public int compareTo(Vertex v)
	{
		return this.name.compareTo(v.name);
	}
	
	public static Comparator<Vertex> distanceComparator()
	{
		return new Comparator<Vertex>()
				{

					@Override
					public int compare(Vertex o1, Vertex o2) 
					{
						if(o1.minDistance < o2.minDistance)
							return -1;
						else if(o1.minDistance == o2.minDistance)
							return 0;
						else
							return 1;
						
					}
			
				};
	}
		
}
