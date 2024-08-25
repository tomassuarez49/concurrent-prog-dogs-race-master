package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeThreads extends Thread {
    private int a;
    private int b;
    private List<Integer> primes = new LinkedList<Integer>();
    private boolean paused = false;

    public PrimeThreads(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        for (int i = a; i <= b; i++) {
            synchronized (this) {
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (isPrime(i)) {
                primes.add(i);
                System.out.println(i);
            }
        }
    }

    boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return this.primes;
    }

    public synchronized void pauseThread() {
        paused = true;
    }

    public synchronized void resumeThread() {
        paused = false;
        notify();
    }
}