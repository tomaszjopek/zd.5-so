package pwr.so1.system.rozproszony;

import java.util.ArrayList;
import java.util.Random;

public class Symulacja
{
	static final int LICZBA_WSZYSTKICH_PROCESOW = 10000;		//liczba wszystkich procesow w systemie
	static final int LICZBA_PRZEJEC = 3;						//liczba przejec uzyta w trzecim algorytmie
	int liczbaProcesorow;					//liczba procesorow dzialajacych w systemie
	int maxCzasWykonania; 					//maksymalny czas wykonania procesu na procesorze
	int prog;								//maksymalny prog obciazenia procesora (p)
	int zakres;								//ilosc losowañ nowego procesora dla procesu (z)
	int minProcent;							//minimalny procent obciazenia procesora przez proces
	int maxProcent;							//maksymalny procent obciazenia procesora przez proces
	int migracje;							//ilosc migracji procesow miedzy procesorami
	int iloscZapytan;						//ilosc zapytan o aktualne obciazenie procesora
	int zegar;								//zegar systemowy
	int minimalnyProgProcesora;				//minimalny prog obciazenia procesora (r)
	ArrayList<Procesor> procesory;			//lista procesorow dzialajacych w systemie
	ArrayList<Zadanie> zadania;
	ArrayList<Zadanie> zadania2;			// trzy listy identycznych zadan dla kazdego algorytmu
	ArrayList<Zadanie> zadania3;
	
	
	public Symulacja(int liczbaProcesorow, int maxCzasWykonania,int prog, int zakres, int minProcent, int maxProcent, int minimalnyProgProcesora)
	{
		this.liczbaProcesorow = liczbaProcesorow;
		this.maxCzasWykonania = maxCzasWykonania;
		this.prog = prog;
		this.zakres = zakres;
		this.minProcent = minProcent;
		this.maxProcent = maxProcent;
		this.minimalnyProgProcesora = minimalnyProgProcesora;		//konstruktor
		migracje = 0;
		iloscZapytan = 0;
		zegar = 0;
		procesory = new ArrayList<Procesor>();
		zadania = new ArrayList<Zadanie>();
		zadania2 = new ArrayList<Zadanie>();
		zadania3 = new ArrayList<Zadanie>();		
	}
	
	/**
	 * Wydruk zalozen symulacji
	 */
	public void zalozeniaSymulacji()
	{
		System.out.println("Liczba wszystkich zadan: " + Symulacja.LICZBA_WSZYSTKICH_PROCESOW);
		System.out.println("Liczba procesorow: " + liczbaProcesorow);
		System.out.println("Prog(p): " + prog);
		System.out.println("Zakres(z): " + zakres);
		System.out.println("Minimalny procent obciazenia procesora: " + minProcent);
		System.out.println("Maksymalny procent obciazenia procesora: " + maxProcent);
		System.out.println("Maksymalny czas wykonania zadania: " + maxCzasWykonania);
		System.out.println("Minimalny prog dla procesora(r): " + minimalnyProgProcesora);
		System.out.println("Liczba przejec procesow od obciazonego procesora: " + LICZBA_PRZEJEC);
		System.out.println("______________________________________________________________________________________");
	}
	
	public void start()
	{
		utworzSystem();					//utworzenie systemu symulujacego dzialanie algorytmow
		generujZadania();
	}
		
	private void utworzSystem()
	{
		
		procesory.clear();
		for(int i=0;i < liczbaProcesorow;i++)
		{
			procesory.add(new Procesor());			//tworzy liste procesorow
		}			
	}
	
	private void generujZadania()					//generowanie ciagu losowych procesow
	{
		Random rand = new Random();				
		int clock = 0;							//zmienna bedaca zegarem, reprezentuje moment pojawienia sie procesu w systemie		
		for(int i=0;i<LICZBA_WSZYSTKICH_PROCESOW;i++)
		{
			int licznik = rand.nextInt(10);		//losowanie ile procesow przybedzie w tym samym takcie zegara
			for(int j=0;j<licznik;j++)
			{
				int czas = rand.nextInt(maxCzasWykonania)+1;		//losowanie czasu wykonania sie procesu
				int tmpObciazenie = rand.nextInt(maxProcent - minProcent + 1) + minProcent;   //losowanie wymaganej mocy obliczeniowej procesora
											
				zadania.add(new Zadanie(tmpObciazenie, czas,clock));
				zadania2.add(new Zadanie(tmpObciazenie, czas,clock));	//tworzenie nowego zadania
				zadania3.add(new Zadanie(tmpObciazenie, czas,clock));
			}
			
			clock += rand.nextInt(3);			//losowanie czasu przybywania procesow
		}
	}
	
	/**
	 * metoda implementujaca nastepujacy algorytm przydzialu procesora:
	 * x pyta losowo wybr. procesor y o aktualne obci¹¿enie. Jeœli jest mniejsze od progu p,
	 * proces jest tam wysy³any. Jeœli nie, losujemy i pytamy nastêpny, próbuj¹c co najwy¿ej z razy.
	 * Jeœli wszystkie wylosowane s¹ obci¹¿one powy¿ej p, proces wykonuje siê na x
	 */
	public void pierwszyAlgorytm()
	{
		zegar = 0;		
		migracje = 0;
		iloscZapytan = 0;
				
		ArrayList<Zadanie> kolejka = new ArrayList<Zadanie>();		//kolejka procesow do przydzielenia
				
		while(!zadania.isEmpty())
		{
			for(int k=0;k<zadania.size();k++)
			{
				if(zadania.get(k).czasZgloszenia <= zegar)
				{
					kolejka.add(zadania.get(k));				//dodawanie do kolejki zadan juz przybylych
				}
			}			
			for(int j=0;j<kolejka.size();j++)
			{
				zadania.remove(kolejka.get(j));				//usuwanie zadan bedacych juz w kolejce
			}			
					
			while(!kolejka.isEmpty())
			{				
				pierwszy(kolejka.get(0));				//przydzial wedlug danego algorytmu dla wszystkich algorytmow w kolejce
				
				for(int j=0;j<procesory.size();j++)
				{					
					procesory.get(j).aktualizujObciazenie();	//aktualizowanie obciazenia procesorow po dodaniu nowego procesu
				}				
				kolejka.remove(0);						//usuniecie z kolejki procesu, ktoremu juz zostal przydzielony procesor
			}
			
			for(int j=0;j<procesory.size();j++)
			{
				procesory.get(j).obnizCzas();			//obnizanie czasu przebywania w procesorze 
				procesory.get(j).obciazenie();			//dodanie obciazenia po takcie zegara w celu dalszych obliczen
			}
			
			++zegar;									//inkrementacja zega
		}
		
		for(int j=0;j<procesory.size();j++)
		{
			if(zegar != 0)
				procesory.get(j).obciazenie = procesory.get(j).calkowiteObciazenie/zegar; //obliczanie sredniego obciazenia dla kazdego procesora
		}		
	}
	
	/**
	 * Przydziela przekazane zadanie wedlug pierwszej strategii
	 * @param zadanie do przydzielenia 
	 */
	private void pierwszy(Zadanie zadanie)		
	{
		Random rand = new Random();
		
		Procesor koordynator = procesory.get(rand.nextInt(procesory.size())); //wybranie procesora x 
		int i=0;
		
		while(i<zakres)			//poki nie przekroszy zakresu losuj procesory i pytaj o obciazenie
		{
			Procesor tmpProcesor = null;
			while(tmpProcesor == null || tmpProcesor.equals(koordynator))	//poki wylosowany nie jest rozny od koordynatora i null 
			{
				tmpProcesor = procesory.get(rand.nextInt(procesory.size()));
			}
			
			if(tmpProcesor.obciazenie < prog)	//jesli wylosowany procesor ma mniejsze obciazenie
			{									//od zalozonego progu wyslij tam proces		
				++migracje;
				++iloscZapytan;						
				tmpProcesor.procesy.add(zadanie);
				break;
			}
			else							//w przeciwnym wypadku zwieksz licznik 'i' oraz iloscZapytan
			{
				++i;
				++iloscZapytan;
			}			
		}
		
		if(i == zakres)
		{
			koordynator.procesy.add(zadanie);		//jesli przekroczymy zakres proces wysylamy do koordynatora(x)
		}
		
	}	
	
	//################################################################################################################
	
	/**
	 * Metoda realizujaca drugi algorytm przydzialu procesu do procesora:
	 * Jesli obci¹¿enie x przekracza wartoœæ progow¹ p,
	 * proces zostaje wys³any na losowo wybrany procesor y o obci¹¿eniu mniejszym od p 
	 * (jeœli wylosowany y ma obc.>p, losowanie powtarza siê do skutku).
	 * Jeœli nie przekracza - proces wykonuje siê na x.
	 */
	public void drugiAlgorytm()
	{
		utworzSystem();
		zegar = 0;		
		migracje = 0;						//wyzerowanie pol po poprzedniej symulacji
		iloscZapytan = 0;		
				
		ArrayList<Zadanie> kolejka = new ArrayList<Zadanie>();
				
		while(!zadania2.isEmpty())
		{
			for(int k=0;k<zadania2.size();k++)
			{
				if(zadania2.get(k).czasZgloszenia <= zegar)
				{
					kolejka.add(zadania2.get(k));		//dodanie do kolejki przybylych procesow				
				}
			}
			
			for(int j=0;j<kolejka.size();j++)
			{
				zadania2.remove(kolejka.get(j));		//usuniecie zadan dodanych do kolejki
			}		
					
			while(!kolejka.isEmpty())		//poki kolejka nie jest pusta wykonuj drugi algorytm
			{				
				drugi(kolejka.get(0));						
				for(int j=0;j<procesory.size();j++)
				{					
					procesory.get(j).aktualizujObciazenie(); //aktualizuj obciazenie
				}				
				kolejka.remove(0);					//usun z kolejki przetworzony proces
			}
			
			for(int j=0;j<procesory.size();j++)
			{
				procesory.get(j).obnizCzas();		//obniz czas wykonania kazdego procesu na procesorach
				procesory.get(j).obciazenie();		//dodaj obciazenie za obecny takt zegara
			}
			
			++zegar;		//inkrementacja zegara
		}
		
		for(int j=0;j<procesory.size();j++)
		{
			if(zegar != 0)
				procesory.get(j).obciazenie = procesory.get(j).calkowiteObciazenie/zegar;	//wylicza srednie obciazenie dla kazdego procesora
		}		
	}
	
	private void drugi(Zadanie zadanie)
	{
		Random rand = new Random();
		
		Procesor koordynator = procesory.get(rand.nextInt(procesory.size())); //losuj koordynatora
		int i = 0;
		
		if(koordynator.obciazenie < prog)			//jesli obciazenie koordynatora mniejsze od progu, zadanie wysylane do koordynatora
		{
			koordynator.procesy.add(zadanie);
		}
		else
		{		
				Procesor tmpProcesor = koordynator;
				while(tmpProcesor.obciazenie > prog)		//poki obciazenie jest wieksze od progu losuj procesory
				{
					tmpProcesor = procesory.get(rand.nextInt(procesory.size()));
					++iloscZapytan;
					++i;
					if(i>=procesory.size())
					{
						for(int j=0;j<procesory.size();j++)
						{
							procesory.get(j).obnizCzas();
							procesory.get(j).aktualizujObciazenie();	//jesli spradzono cala ilosc procesow obniz czas i aktualizuj obciazenia
						}
						
						i = 0;   //zeruj i
						++zegar; //zwieksz zegar
					}
				}
				
				tmpProcesor.procesy.add(zadanie);   
				++migracje;									
						
		}	
	}
	
	
	
	//################################################################################################################
	
	/**
	 * Metoda implementujaca trzeci algorytm przydzialu procesow:
	 * Jak w pkt 2, z tym ¿e procesory o obci¹¿eniu mniejszym od minimalnego progu r pytaj¹
	 * losowo wybrane procesory i jesli obc. zapytanego jest wiêksze od p,
	 * pytaj¹cy przejmuje czêœæ jego zadañ (za³o¿yæ jak¹).
	 */
	public void trzeciAlgorytm()
	{
		utworzSystem();
		zegar = 0;		
		migracje = 0;					//zerowanie pol po ostatniej symulacji
		iloscZapytan = 0;	
				
		ArrayList<Zadanie> kolejka = new ArrayList<Zadanie>();
				
		while(!zadania3.isEmpty())
		{
			for(int k=0;k<zadania3.size();k++)
			{
				if(zadania3.get(k).czasZgloszenia <= zegar)
				{
					kolejka.add(zadania3.get(k));			//dodawanie przybylych procesow do kolejki			
				}
			}
			
			for(int j=0;j<kolejka.size();j++)
			{
				zadania3.remove(kolejka.get(j));		//usun procesy ktore zostaly juz dodane do kolejki
			}					
			while(!kolejka.isEmpty())
			{				
				trzeci(kolejka.get(0));			//poki kolejka nie jest pusta przydzielaj procesory
				
				for(int j=0;j<procesory.size();j++)
				{					
					procesory.get(j).aktualizujObciazenie();	//aktualizuj obciazenie
				}				
				kolejka.remove(0);				//usun przetworzone procesy
			}
			
			for(int j=0;j<procesory.size();j++)
			{
				procesory.get(j).obnizCzas();		//obniz czas wykonania na procesorach
				procesory.get(j).obciazenie();		// policz obciazenie w danym takcie zegara
			}
			
			++zegar;		//zwieksz zegar
		}
		
		for(int j=0;j<procesory.size();j++)
		{
			if(zegar != 0)
				procesory.get(j).obciazenie = procesory.get(j).calkowiteObciazenie/zegar;	//policz srednie obciazenie dla kazdego procesora
		}				
	}
	
	private void trzeci(Zadanie zadanie)
	{
		Random rand = new Random();
		
		Procesor koordynator = procesory.get(rand.nextInt(procesory.size()));	//wybieramy koordynatora
		int i = 0;
		
		if(koordynator.obciazenie < prog)		//jesli obciazenie koordynatora mniejsze od progu proces wykonuje sie na nim
		{
			koordynator.procesy.add(zadanie);
		}
		else								//w przeciwnym wypadku szukamy do skutku
		{
			Procesor tmpProcesor = koordynator;
			while(tmpProcesor.obciazenie > prog)
			{
				tmpProcesor = procesory.get(rand.nextInt(procesory.size()));
				++iloscZapytan;
				++i;
				if(i>=procesory.size())						//jesli przeszlismy wiekszosc listy zerujemy 'i' 
				{
					for(int j=0;j<procesory.size();j++)
					{
						procesory.get(j).obnizCzas();				//obnizamy czas wykonania na procesorach
						procesory.get(j).aktualizujObciazenie();	//aktualizujemy obciazenie na procesorze
					}
					
					i = 0;
					++zegar;									//zwiekszamy zegar
				}
			}
			
			tmpProcesor.procesy.add(zadanie);				//przesylamy proces na znaleziony procesor
			++migracje;													
		}
		
		//druga czesc trzeciego algorytmu
		
		for(int j = 0;j<procesory.size();j++)
		{
			Procesor tmp = procesory.get(j);
			iloscZapytan++;
			Procesor tmpObciazony = null;
			if(tmp.obciazenie < minimalnyProgProcesora)	//jesli procesor ma mniejsze obciazenie od progu minimalnego, przejmuje 3 zadania od procesora obciazonego powyzej progu
			{
				int licznik = LICZBA_PRZEJEC;			//przypisanie licznikowi liczby przejec
				
				for(int k=0;k<procesory.size();k++)
				{
					iloscZapytan++;
					if(procesory.get(k).obciazenie > prog && !procesory.get(k).equals(tmp))
					{
						tmpObciazony = procesory.get(k);		//szukanie procesora obciazonego powyzej progu
						break;
					}					
				}
				
				if(tmpObciazony != null)			//jesli znaleziono obciazony procesor
				{
					while(licznik > 0)
					{											
						if(!tmpObciazony.procesy.isEmpty())
						{
							Zadanie tmpZadanie = tmpObciazony.procesy.remove(0);
							++migracje;					//przeniesc ustalona liczbe procesow
							tmp.procesy.add(tmpZadanie);
						}
						licznik--;
					}
					
					for(int k=0;k<procesory.size();k++)
					{
						procesory.get(k).aktualizujObciazenie();	//aktualizujemy obciazenie na procesorach
					}
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
			suma += procesory.get(i).obciazenie;		//suma wszystkich obciazen
		}
		
		suma = suma/procesory.size();				//srednie obciazenie calego systemu
		
		double odchylenie = odchylenieStandardowe(suma);	//liczenie odchylenia standardowego
		
		//wypisanie otrzymanych wynikow
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
		
		tmp = tmp/(procesory.size()*(procesory.size()-1));  //liczenie odchylenia standardowego 
		
		
		return Math.sqrt(tmp);
	}
	

}
