package pwr.so1.system.rozproszony;

public class Main
{
	
	public static void main(String[] args)
	{
		Symulacja symulacja = new Symulacja(70,20,50,10,1,90,25);
		symulacja.start();
		symulacja.zalozeniaSymulacji();
		
		symulacja.pierwszyAlgorytm();
		symulacja.wypisz();
		
		symulacja.drugiAlgorytm();
		symulacja.wypisz();
		
		
		
		
	}
	
	
	
	
	

}
