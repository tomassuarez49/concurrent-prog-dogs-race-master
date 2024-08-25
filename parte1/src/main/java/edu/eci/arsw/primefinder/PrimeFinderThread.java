package edu.eci.arsw.primefinder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PrimeFinderThread {

    int a, b;

    private List<Integer> primes = new LinkedList<Integer>();

    public PrimeFinderThread(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public static int[] dividirEnPartes(int m, int n) {
        int[] partes = new int[n];
        int parteBasica = m / n;
        int resto = m % n;

        Arrays.fill(partes, parteBasica);
        for (int i = 0; i < resto; i++) {
            partes[i]++;
        }

        return partes;
    }

    public void start() {
        int r = 0;
        int n = 3;
        int[] partes = dividirEnPartes((b - a), n);

        // Crear los n hilos
        PrimeThreads[] threads = new PrimeThreads[n];

        // Usar este for para crear los hilos
        for (int i = 0; i < n; i++) {
            threads[i] = new PrimeThreads(r, r + partes[i]);
            r = r + partes[i];
        }

        // Ciclo para ejecutar y pausar los hilos
        while (true) {
            // Iniciar los hilos
            for (int i = 0; i < n; i++) {
                if (!threads[i].isAlive()) {
                    threads[i].start();
                } else {
                    threads[i].resumeThread();
                }
            }

            // Esperar 5 segundos
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Pausar los hilos
            for (int i = 0; i < n; i++) {
                threads[i].pauseThread();
            }

            // Mostrar números primos encontrados hasta ahora
            for (int i = 0; i < n; i++) {
                System.out.println("Primos encontrados por el hilo " + i + ": " + threads[i].getPrimes());
            }

            // Esperar a que el usuario presione ENTER
            System.out.println("Presiona ENTER para continuar...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
    }
}

