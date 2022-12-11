package game;

public class ThreadAwakener extends Thread {

    private Object obj;

    public ThreadAwakener(Object obj) {
	this.obj = obj;
    }

    @Override
    public synchronized void run() {
	try {
	    Thread.sleep(2000);
	    synchronized(obj) {
		obj.notify();
	    }

	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }



}
