package pwr.so1.system.rozproszony;

import java.util.ArrayList;

public class Procesor
{
	
	double obciazenie;
	ArrayList<Zadanie> procesy;
	double calkowiteObciazenie;
	
	public Procesor()
	{
		obciazenie = 0;
		procesy = new ArrayList<Zadanie>();
		calkowiteObciazenie = 0;
	}	
	
	public void aktualizujObciazenie()
	{
		obciazenie = 0;
		
		for(Zadanie z : procesy)
		{
			obciazenie += z.zapotrzebowanie;
		}
					
	}
	
	public void obciazenie()
	{
		obciazenie = 0;
		
		for(int i =0;i<procesy.size();i++)
		{
			obciazenie += procesy.get(i).zapotrzebowanie;
		}
		
		calkowiteObciazenie += obciazenie;
	}
	
	
	public void obnizCzas()
	{
		
		ArrayList<Zadanie> toDelete = new ArrayList<Zadanie>();
		
		for(int i=0;i<procesy.size();i++)
		{
			if(procesy.get(i).czas == 0)
			{
				obciazenie -= procesy.get(i).zapotrzebowanie;
				toDelete.add(procesy.get(i));
			}			
		}
		
		int i=0;
		while(!procesy.isEmpty() && i<toDelete.size())
		{
			procesy.remove(toDelete.get(i));
			++i;
		}
		
		
		for(int k=0;k<procesy.size();k++)
		{
			procesy.get(k).czas--;
		}		
	}


}
