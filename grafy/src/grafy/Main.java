package grafy;

public class Main 
{	
	public static void main(String[] args)
	{
		
		Graph graf = new Graph();
		graf.readGraph("mapa.txt");
		graf.print();
		/*
		Dijkstra dijkstra = new Dijkstra(graf);
		dijkstra.findPath("Krak�w");
		dijkstra.printPath("Warszawa");
		*/
		
		DFS dfs = new DFS(graf);
		dfs.findPath("Warszawa", "Krak�w");
		
		
	}
	
	
	
	

}
