package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private boolean paused = false; // Estado de pausa

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}

	/**
	 * The 'pause' function sets a boolean variable `paused` to true in a synchronized manner.
	 */
	public synchronized void pause(){
		paused = true;
	}

	/**
	 * The 'keepRunning' function in Java is synchronized and sets a boolean variable to false before
	 * notifying all waiting threads.
	 */
	public synchronized void keepRunning(){
		paused = false;
		notifyAll();
	}

	/**
	 * The 'isPaused' function waits until the `paused` flag is false in a synchronized manner.
	 */
	private synchronized void isPaused() throws InterruptedException{
		while(paused){
			wait();
		}
	}

	/**
	 * The 'corra' method simulates a race by moving a participant along a track, updating their position,
	 * and determining the winner.
	 */
	public void corra() throws InterruptedException {
		while (paso < carril.size()) {	
			isPaused();

			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			
			if (paso == carril.size()) {						
				carril.finish();
				
				synchronized (regl) {
					int ubicacion=regl.getUltimaPosicionAlcanzada();
					regl.setUltimaPosicionAlcanzada(ubicacion+1);
					System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
					if (ubicacion==1){
						regl.setGanador(this.getName());
					}
				}
			}
		}
	}

	/**
	 * The 'run' method overrides the 'run' method of the 'Runnable' interface and calls the 'corra'
	 * method within a try-catch block to handle 'InterruptedException'.
	 */
	@Override
	public void run() {
		try {		
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
