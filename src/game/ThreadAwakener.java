package game;

import environment.Cell;

public class ThreadAwakener extends Thread {
    
    private Cell c;
    
    public ThreadAwakener(Cell c) {
	this.c = c;
    }

    @Override
    public synchronized void run() {
	try {
	    Thread.sleep(2000);
	    synchronized(c) {
		c.notify();
	    }
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    

}
