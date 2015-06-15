package pwr.so1.system.rozproszony;

public class Main
{
	
	public static void main(String[] args)
	{
		Symulacja symulacja = new Symulacja(70,20,80,5,10,70,15);
		symulacja.start();
		symulacja.zalozeniaSymulacji();
		
		symulacja.pierwszyAlgorytm();
		symulacja.wypisz();
		
		symulacja.drugiAlgorytm();
		symulacja.wypisz();
		
		symulacja.trzeciAlgorytm();
		symulacja.wypisz();
		
		
	}
	
	
	
	
	

}
