package game;

public class ThreadAwakener extends Thread {

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			notifyAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
