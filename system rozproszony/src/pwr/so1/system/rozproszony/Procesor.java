package pwr.so1.system.rozproszony;

import java.util.ArrayList;

public class Procesor
{
	
	int obciazenie;
	ArrayList<Zadanie> procesy;
	double srednieObciazenie;
	
	public Procesor()
	{
		obciazenie = 0;
		procesy = new ArrayList<Zadanie>();
		srednieObciazenie = 0;
	}
	
	

}
