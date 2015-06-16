package pwr.so1.system.rozproszony;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main
{
	
	static int N;
	static int p;
	static int z;
	static int r;
	
	public static void main(String[] args)
	{
		
		Scanner sc = null;
		
		System.out.println("Podaj ilosc procesorow, prog, zakres, i minimalne obciazenie:");
						
		try
		{
			sc = new Scanner(System.in);
			N = sc.nextInt();
			p = sc.nextInt();
			z = sc.nextInt();
			r = sc.nextInt();
			
			Symulacja symulacja = new Symulacja(N,20,p,z,5,70,r);
			
			symulacja.start();
			symulacja.zalozeniaSymulacji();
			
			symulacja.pierwszyAlgorytm();
			symulacja.wypisz();
			
			symulacja.drugiAlgorytm();
			symulacja.wypisz();
			
			symulacja.trzeciAlgorytm();
			symulacja.wypisz();			
			
		}
		catch(InputMismatchException f)
		{
			System.out.println("BLAD");
		}
		catch(IllegalArgumentException e)
		{
			System.out.println("BLAD");
		}	
		
	}

}
