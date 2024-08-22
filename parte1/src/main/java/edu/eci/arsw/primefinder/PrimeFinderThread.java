package edu.eci.arsw.primefinder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	long start = System.currentTimeMillis();
	long end = start + 5 * 1000;
	
	private List<Integer> primes=new LinkedList<Integer>();
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	 public static int[] dividirEnPartes(int m, int n) {
        int[] partes = new int[n];
        int parteBasica = m / n;
        int resto = m % n;

        if (m % 2 == 0) { // Si el número es par
            Arrays.fill(partes, parteBasica);
            for (int i = 0; i < resto; i++) {
                partes[i]++;
            }
        } else { // Si el número es impar
            Arrays.fill(partes, parteBasica);
            for (int i = 0; i < resto; i++) {
                partes[i]++;
            }
        }

        return partes;
    }

	public void hola(){
		int r = 0;
		int n = 3;
		int[] partes = dividirEnPartes((b-a), n);
        //crear los n hilos
        PrimeThreads[] threads = new PrimeThreads[n];

        //usar este for para crear los hilos
        for(int i=0;i< n;i++){
            threads[i] = new PrimeThreads(r,r + partes[i]);
            r = r + partes[i];
        }		
		
		threads[0].start();
		threads[1].start();
		threads[2].start();

			if(System.currentTimeMillis() > end){
				 for(int i=0;i<n;i++){
				 	List<Integer> lista = threads[i].getPrimes();
					try {
						wait();
						lista.wait();
						threads[i].wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
        

         

	}	
}
