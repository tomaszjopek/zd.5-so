package grafy;

import java.util.Scanner;

public class Main 
{	
	public static void main(String[] args)
	{
		
		Graph graf = new Graph();
		graf.readGraph("mapa.txt");
		graf.print();
		
		graf.printVertexes();
		
		Scanner sc = new Scanner(System.in);
				
		int wybor;
		while(true)
		{
			System.out.println("Zadanie pierwsze Dijkstra: 1");
			System.out.println("Zadanie drugie najd³u¿sza droga: 2");
			System.out.println("Zadanie trzecie cykl Hamiltona: 3");
			System.out.println("Wyjscie: 10");		
			System.out.println("\nDokonaj wyboru:");
			
			wybor = sc.nextInt();
			
			switch(wybor)
			{
				case 1 : System.out.println("Podaj poczatkowy i koncowy wierzcholek");
						 int start = sc.nextInt();
						 int end = sc.nextInt();
						 Dijkstra dijkstra = new Dijkstra(graf);
						 String tmp = null;
						 
						 for(Vertex v : graf.vertexes)
						 {
							 if(start == v.id)
								 dijkstra.findPath(v.name);
							 if(end == v.id)
								 tmp = v.name;
						 }					 
						 dijkstra.printPath(tmp);
						 break;
						 
				case 2:	System.out.println("Podaj poczatkowy i koncowy wierzcholek");
						int start1 = sc.nextInt();
						int end1 = sc.nextInt();
					
						DFS dfs = new DFS(graf);
						String tmp1 = null;
						String tmp2 = null;
						
						 for(Vertex v : graf.vertexes)
						 {
							 if(start1 == v.id)
								 tmp1 = v.name;
							 if(end1 == v.id)
								 tmp2 = v.name;
						 }					
						dfs.findPath(tmp1, tmp2);
						dfs.printPath();
						break;
						
				case 3:	System.out.println("Podaj wierzcholek startowy");
						int start2 = sc.nextInt();
						String tmp3 = null;
				 		for(Vertex v : graf.vertexes)
				 		{
				 			if(start2 == v.id)
				 				tmp3 = v.name;
				 		}
						HamiltonCycle hamilton = new HamiltonCycle(graf);
						hamilton.findCycle(tmp3);
						hamilton.printCycle();
						break;	
				default: break;		
			}
			
			if(wybor == 10)
			{
				System.out.println("KONIEC PROGRAMU");
				break;
			}
		}
				
	}	

}
