package grafy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Plik 
{
	
	private String title;
	
	public Plik(String title)
	{
		this.title = title;
	}
	
	
	public void czytajPlik()
	{
		Scanner sc = null;
		try 
		{
			sc = new Scanner(new File(title));
			while(sc.hasNextLine())
			{
				String line = sc.nextLine();
				String vertex1 = null;
				String vertex2 = null;
				int distance = 0;
				
				Object[] tab = line.split(",");
				vertex1 = (String)tab[0];
				vertex2 = (String)tab[1];
				distance =  (int)tab[2];
								
				
			}
			
			
			
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	


}
