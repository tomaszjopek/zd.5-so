package pwr.so1.system.rozproszony;

import java.util.ArrayList;
import java.util.Random;

public class Symulacja
{
	static final int LICZBA_WSZYSTKICH_PROCESOW = 10000;
	static final int LICZBA_PRZEJEC = 3;
	int liczbaProcesorow;
	int maxCzasWykonania; 
	int prog;
	int zakres;
	int minProcent;
	int maxProcent;
	int migracje;
	int iloscZapytan;
	int zegar;
	int minimalnyProgProcesora;
	ArrayList<Procesor> procesory;
	ArrayList<Zadanie> kopiaZadania;
	ArrayList<Zadanie> zadania;
	ArrayList<Zadanie> zadania2;
	ArrayList<Zadanie> zadania3;
	
	
	public Symulacja(int liczbaProcesorow, int maxCzasWykonania,int prog, int zakres, int minProcent, int maxProcent, int minimalnyProgProcesora)
	{
		this.liczbaProcesorow = liczbaProcesorow;
		this.maxCzasWykonania = maxCzasWykonania;
		this.prog = prog;
		this.zakres = zakres;
		this.minProcent = minProcent;
		this.maxProcent = maxProcent;
		this.minimalnyProgProcesora = minimalnyProgProcesora;
		migracje = 0;
		iloscZapytan = 0;
		zegar = 0;
		procesory = new ArrayList<Procesor>();
		zadania = new ArrayList<Zadanie>();
		zadania2 = new ArrayList<Zadanie>();
		zadania3 = new ArrayList<Zadanie>();
		kopiaZadania = new ArrayList<Zadanie>();
	}
	
	public void zalozeniaSymulacji()
	{
		System.out.println("Liczba wszystkich zadan: " + Symulacja.LICZBA_WSZYSTKICH_PROCESOW);
		System.out.println("Liczba procesorow: " + liczbaProcesorow);
		System.out.println("Prog(p): " + prog);
		System.out.println("Zakres(z): " + zakres);
		System.out.println("Minimalny procent obciazenia procesora: " + minProcent);
		System.out.println("Maksymalny procent obciazenia procesora: " + maxProcent);
		System.out.println("Maksymalny czas wykonania zadania: " + maxCzasWykonania);
		System.out.println("______________________________________________________________________________________");
	}
	
	public void start()
	{
		utworzSystem();
		generujZadania();
	}
		
	private void utworzSystem()
	{
		
		procesory.clear();
		for(int i=0;i < liczbaProcesorow;i++)
		{
			procesory.add(new Procesor());
		}			
	}
	
	private void generujZadania()
	{
		Random rand = new Random();
		int clock = 0;
		for(int i=0;i<LICZBA_WSZYSTKICH_PROCESOW;i++)
		{
			int licznik = rand.nextInt(10);
			for(int j=0;j<licznik;j++)
			{
				int czas = rand.nextInt(maxCzasWykonania)+1;
				int tmpObciazenie = rand.nextInt(maxProcent - minProcent + 1) + minProcent;
											
				zadania.add(new Zadanie(tmpObciazenie, czas,clock));
				zadania2.add(new Zadanie(tmpObciazenie, czas,clock));
				zadania3.add(new Zadanie(tmpObciazenie, czas,clock));
			}
			
			clock += rand.nextInt(3);			//losowanie czasu przybywania procesow
		}
	}
	
	
	public void pierwszyAlgorytm()
	{
		zegar = 0;
		
		migracje = 0;
		iloscZapytan = 0;
				
		ArrayList<Zadanie> kolejka = new ArrayList<Zadanie>();
				
		while(!zadania.isEmpty())
		{
			for(int k=0;k<zadania.size();k++)
			{
				if(zadania.get(k).czasZgloszenia == zegar)
				{
					kolejka.add(zadania.get(k));					
				}
			}
			
			for(int j=0;j<kolejka.size();j++)
			{
				zadania.remove(kolejka.get(j));
			}
			
					
			while(!kolejka.isEmpty())
			{				
				pierwszy(kolejka.get(0));	
				
				for(int j=0;j<procesory.size();j++)
				{					
					procesory.get(j).aktualizujObciazenie();
				}
				
				kolejka.remove(0);
			}
			
			for(int j=0;j<procesory.size();j++)
			{
				procesory.get(j).obnizCzas();
				procesory.get(j).obciazenie();
			}
			
			++zegar;
		}
		
		for(int j=0;j<procesory.size();j++)
		{
			if(zegar != 0)
				procesory.get(j).obciazenie = procesory.get(j).calkowiteObciazenie/zegar;
		}
		
			
		
	}
	
	private void pierwszy(Zadanie zadanie)
	{
		Random rand = new Random();
		
		Procesor koordynator = procesory.get(rand.nextInt(procesory.size()));
		int i=0;
		
		while(i<zakres)
		{
			Procesor tmpProcesor = null;
			while(tmpProcesor == null || tmpProcesor.equals(koordynator))
			{
				tmpProcesor = procesory.get(rand.nextInt(procesory.size()));
			}
			
			if(tmpProcesor.obciazenie < prog)
			{
				++migracje;
				++iloscZapytan;
				tmpProcesor.procesy.add(zadanie);
				break;
			}
			else
			{
				++i;
				++iloscZapytan;
			}			
		}
		
		if(i == zakres)
		{
			koordynator.procesy.add(zadanie);
		}
		
	}	
	
	//################################################################################################################
	
	
	public void drugiAlgorytm()
	{
		utworzSystem();
		zegar = 0;
		
		migracje = 0;
		iloscZapytan = 0;
		
				
		ArrayList<Zadanie> kolejka = new ArrayList<Zadanie>();
				
		while(!zadania2.isEmpty())
		{
			for(int k=0;k<zadania2.size();k++)
			{
				if(zadania2.get(k).czasZgloszenia == zegar)
				{
					kolejka.add(zadania2.get(k));					
				}
			}
			
			for(int j=0;j<kolejka.size();j++)
			{
				zadania2.remove(kolejka.get(j));
			}
			
					
			while(!kolejka.isEmpty())
			{				
				drugi(kolejka.get(0));	
				
				for(int j=0;j<procesory.size();j++)
				{					
					procesory.get(j).aktualizujObciazenie();
				}
				
				kolejka.remove(0);
			}
			
			for(int j=0;j<procesory.size();j++)
			{
				procesory.get(j).obnizCzas();
				procesory.get(j).obciazenie();
			}
			
			++zegar;
		}
		
		for(int j=0;j<procesory.size();j++)
		{
			if(zegar != 0)
				procesory.get(j).obciazenie = procesory.get(j).calkowiteObciazenie/zegar;
		}
		
			
		
	}
	
	private void drugi(Zadanie zadanie)
	{
		Random rand = new Random();
		
		Procesor koordynator = procesory.get(rand.nextInt(procesory.size()));
		int i = 0;
		
		if(koordynator.obciazenie < prog)
		{
			koordynator.procesy.add(zadanie);
		}
		else
		{
			
			
				Procesor tmpProcesor = koordynator;
				while(tmpProcesor.equals(koordynator) || tmpProcesor.obciazenie > prog)
				{
					tmpProcesor = procesory.get(rand.nextInt(procesory.size()));
					++iloscZapytan;
					++i;
					if(i>=procesory.size())
					{
						for(int j=0;j<procesory.size();j++)
						{
							procesory.get(j).obnizCzas();
							procesory.get(j).aktualizujObciazenie();
						}
						
						i = 0;
						++zegar;
					}
				}
				
				tmpProcesor.procesy.add(zadanie);
				++migracje;									
						
		}	
	}
	
	
	
	//################################################################################################################
	
	public void trzeciAlgorytm()
	{
		utworzSystem();
		zegar = 0;
		
		migracje = 0;
		iloscZapytan = 0;
		
				
		ArrayList<Zadanie> kolejka = new ArrayList<Zadanie>();
				
		while(!zadania3.isEmpty())
		{
			for(int k=0;k<zadania3.size();k++)
			{
				if(zadania3.get(k).czasZgloszenia == zegar)
				{
					kolejka.add(zadania3.get(k));					
				}
			}
			
			for(int j=0;j<kolejka.size();j++)
			{
				zadania3.remove(kolejka.get(j));
			}
			
					
			while(!kolejka.isEmpty())
			{				
				trzeci(kolejka.get(0));	
				
				for(int j=0;j<procesory.size();j++)
				{					
					procesory.get(j).aktualizujObciazenie();
				}
				
				kolejka.remove(0);
			}
			
			for(int j=0;j<procesory.size();j++)
			{
				procesory.get(j).obnizCzas();
				procesory.get(j).obciazenie();
			}
			
			++zegar;
		}
		
		for(int j=0;j<procesory.size();j++)
		{
			if(zegar != 0)
				procesory.get(j).obciazenie = procesory.get(j).calkowiteObciazenie/zegar;
		}
				
	}
	
	private void trzeci(Zadanie zadanie)
	{
		Random rand = new Random();
		
		Procesor koordynator = procesory.get(rand.nextInt(procesory.size()));
		int i = 0;
		
		if(koordynator.obciazenie < prog)
		{
			koordynator.procesy.add(zadanie);
		}
		else
		{
			Procesor tmpProcesor = koordynator;
			while(tmpProcesor.equals(koordynator) || tmpProcesor.obciazenie > prog)
			{
				tmpProcesor = procesory.get(rand.nextInt(procesory.size()));
				++iloscZapytan;
				++i;
				if(i>=procesory.size())
				{
					for(int j=0;j<procesory.size();j++)
					{
						procesory.get(j).obnizCzas();
						procesory.get(j).aktualizujObciazenie();
					}
					
					i = 0;
					++zegar;
				}
			}
			
			tmpProcesor.procesy.add(zadanie);
			++migracje;													
		}
		
		for(int j = 0;j<procesory.size();j++)
		{
			Procesor tmp = procesory.get(j);
			if(tmp.obciazenie < minimalnyProgProcesora)
			{
				int licznik = LICZBA_PRZEJEC;
				while(licznik > 0)
				{
					
					
					
				}
				
				
			}
			
			
		}
		
		
		
	}
	
	
	//################################################################################################################
	
	
	
	public void wypisz()
	{
		double suma = 0;
		for(int i=0;i<procesory.size();i++)
		{
			suma += procesory.get(i).obciazenie;
		}
		
		suma = suma/procesory.size();
		
		double odchylenie = odchylenieStandardowe(suma);
		
		for(int i=0;i<procesory.size();i++)
			System.out.printf("Procesor %3d: obciazenie srednie: %5.2f%n",i+1,procesory.get(i).obciazenie);
		
		System.out.printf("Obciazenie srednie calego systemu: %5.2f%n",suma);
		System.out.printf("Odchylenie standardowe: %5.2f%n",odchylenie);
		System.out.printf("Migracje: %d, Ilosc zapytan: %d%n",migracje, iloscZapytan);
		
		System.out.println("_______________________________________________________________________________");
	}
	
	public double odchylenieStandardowe(double srednia)
	{
		double tmp =0;
		for(int i=0;i<procesory.size();i++)
		{
			tmp += Math.pow(procesory.get(i).obciazenie - srednia,2);
		}
		
		tmp = tmp/(procesory.size()*(procesory.size()-1));
		
		
		return Math.sqrt(tmp);
	}
	

}
