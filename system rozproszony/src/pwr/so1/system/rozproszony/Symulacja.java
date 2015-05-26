package pwr.so1.system.rozproszony;

import java.util.ArrayList;
import java.util.Random;

public class Symulacja
{
	static final int LICZBA_WSZYSTKICH_PROCESOW = 10000;
	int liczbaProcesorow;
	int prog;
	int zakres;
	int minProcent;
	int maxProcent;
	ArrayList<Procesor> procesory;
	
	
	public Symulacja(int liczbaProcesorow, int prog, int zakres, int minProcent, int maxProcent)
	{
		this.liczbaProcesorow = liczbaProcesorow;
		this.prog = prog;
		this.zakres = zakres;
		this.minProcent = minProcent;
		this.maxProcent = maxProcent;
		procesory = new ArrayList<Procesor>();
	}
		
	public void generujZadania()
	{
		for(int i=0;i < liczbaProcesorow;i++)
		{
			procesory.add(new Procesor());
		}
		
		Random rand = new Random();
		
		for(int i=0;i<LICZBA_WSZYSTKICH_PROCESOW;i++)
		{
			int tmp = rand.nextInt(maxProcent - minProcent + 1) + minProcent;
			int index = rand.nextInt(procesory.size());
			procesory.get(index).procesy.add(new Zadanie(tmp));
		}		
	}
	
	
	public void wyswietl()
	{
		for(int i=0;i<procesory.size();i++)
		{
			System.out.println(procesory.get(i).procesy.size());
		}
	}
	
	

}
